package com.bluczak.corelib.backend.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import com.bluczak.corelib.backend.events.BackgroundRequestFinishedEvent;
import com.bluczak.corelib.backend.events.BaseEventBus;
import com.bluczak.corelib.backend.utils.Logger;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by BLuczak on 2015-07-19.
 */
public class BackgroundWorkerService extends BaseService {

    //region Initialization
    private static BackgroundWorkerServiceConnection mConnection = new BackgroundWorkerServiceConnection();
    private static BackgroundWorkerService mService = null;
    private static BaseEventBus mEventBus = null;
    private static final String TAG = "BackgroundWorkerService";
    public static void initialize(final Context context, final BaseEventBus eventBus) {
        // start service
        final Intent startServiceIntent = new Intent(context, BackgroundWorkerService.class);
        context.startService(startServiceIntent);

        // and bind immediately
        context.bindService(startServiceIntent, mConnection, Context.BIND_AUTO_CREATE);

        // assign event bus
        mEventBus = eventBus;
        log.setDefaultTag(TAG);
        Logger.d(TAG, "Service initialized!");
    }

    public static boolean isConnected() {
        synchronized (BackgroundWorkerService.class) {
            return mService != null;
        }
    }

    public static BackgroundWorkerService getInstance() {
        synchronized (BackgroundWorkerService.class) {
            return mService;
        }
    }

    //endregion

    //region Queueing new requests and steps of executing
    public long executeRequest(final BaseRequest request) {
        return executeRequest(request, false);
    }

    public long executeRequest(final BaseRequest request, final boolean allowParallel) {
        if (request == null) return BaseRequest.REQUEST_NO_ID;

        request.mRequestId = getNextId();
        request.mExecutorService = this;
        request.allowParallel = allowParallel;

        runPreAction(request);

        return request.mRequestId;
    }

    protected void runPreAction(final BaseRequest request) {
        if (request == null) return;

        request.setRunning();
        mUiHandler.post(request.getPreRunnable());
    }

    protected void runMainAction(final BaseRequest request) {
        if (request == null) return;

        if(request.allowParallel) {
            mMultipleThread.execute(request.getMainRunnable());
        }
        else {
            mSingleThread.execute(request.getMainRunnable());
        }
    }

    protected void runPostAction(final BaseRequest request) {
        if (request == null) return;

        mUiHandler.post(request.getPostRunnable());
    }

    protected void requestFinished(final BaseRequest request) {
        mFinishExecutor.execute(getFinishingRunnableForRequest(request));
    }

    private static Runnable getFinishingRunnableForRequest(final BaseRequest request) {
        return new Runnable() {
            @Override
            public void run() {
                signalRequestIsFinished(request);
            }
        };
    }

    private static void signalRequestIsFinished(final BaseRequest request) {
        if(mEventBus == null) return;
        mEventBus.postEvent(new BackgroundRequestFinishedEvent(request));
    }

    //endregion

    //region Requests ID management
    // id are unique accross all background worker services (if user will want to have more than one)
    final private static AtomicLong mIdGenerator = new AtomicLong(0);

    protected static long getNextId() {
        return mIdGenerator.incrementAndGet();
    }
    //endregion

    //region Executors and UI thread handler
    final private Handler mUiHandler = UiHandler;
    final private Executor mSingleThread = Executors.newSingleThreadExecutor();
    final private Executor mMultipleThread = Executors.newCachedThreadPool();
    final private Executor mFinishExecutor = Executors.newSingleThreadExecutor();
    //endregion

    //region Service's lifecycle methods

    final private BackgroundWorkerBinder mBinder = new BackgroundWorkerBinder();
    private static boolean mIsRunning = false;

    @Override public void onCreate() {
        super.onCreate();
    }

    @Override public BackgroundWorkerBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        mIsRunning = true;
        return START_STICKY;
    }

    @Override public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override public void onDestroy() {
        mIsRunning = false;
        super.onDestroy();
    }

    public static boolean isRunning() {
        synchronized (BackgroundWorkerService.class) {
            return mIsRunning;
        }
    }

    //endregion

    //region Local Binder class
    protected class BackgroundWorkerBinder extends Binder {
        BackgroundWorkerService getService() {
            return BackgroundWorkerService.this;
        }
    }
    //endregion

    //region ServiceConnection class
    protected static class BackgroundWorkerServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            synchronized (BackgroundWorkerService.class) {
                if (!(service instanceof BackgroundWorkerBinder)) return;
                BackgroundWorkerBinder binder = (BackgroundWorkerBinder) service;
                mService = binder.getService();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            synchronized (BackgroundWorkerService.class) {
                mService = null;
            }
        }
    }
    //endregion

    //region Base Request type
    public enum RequestStatus {
        WAITING,
        RUNNING,
        SUCCESS,
        FAIL,
        CANCELED
    }

    public abstract static class BaseRequest {
        public static final long REQUEST_NO_ID = -1;
        public static final int  NO_FILTER = Integer.MIN_VALUE;

        public abstract void background_action();
        public void ui_prepare() {}
        public void ui_post() {}

        protected final String mDescription;
        protected String mStrFilter;
        protected int mIntFilter;
        public BaseRequest(final String desc, final String stringFilter, final int intFilter) {
            mDescription = desc;
            mStrFilter = stringFilter;
            mIntFilter = intFilter;
        }

        public BaseRequest(final String desc) {
            this(desc, null, NO_FILTER);
        }

        public BaseRequest() {
            this(null);
        }

        public boolean matchesFilter(final String filter) {
            if(mStrFilter == null || filter == null) return true;
            return mStrFilter.equals(filter);
        }

        public boolean matchesFilter(final int filter) {
            if(mIntFilter == NO_FILTER || filter == NO_FILTER) return true;
            return mIntFilter == filter;
        }

        public long getRequestId() {
            return mRequestId;
        }

        public boolean isParallelExecutionAllowed() {
            return allowParallel;
        }

        private boolean mIsCanceled = false;
        public void cancel() {
            mIsCanceled = true;
            mStatus = RequestStatus.CANCELED;
        }

        public boolean shouldStopFurtherWork() {
            return mIsCanceled;
        }

        private RequestStatus mStatus = RequestStatus.WAITING;
        public RequestStatus getStatus() {
            return mStatus;
        }

        public boolean wasSuccessful() {
            return getStatus() == RequestStatus.SUCCESS;
        }

        public boolean failed() {
            return getStatus() == RequestStatus.FAIL;
        }

        protected void setFail() {
            mStatus = RequestStatus.FAIL;
        }

        protected void setSuccess() {
            mStatus = RequestStatus.SUCCESS;
        }

        protected void setCanceled() {
            mStatus = RequestStatus.CANCELED;
        }

        protected void setRunning() {
            mStatus = RequestStatus.RUNNING;
        }

        protected void sleep(final long ms) {
            try {
                Thread.sleep(ms);
            } catch (Exception e) {}
        }

        protected long mRequestId = REQUEST_NO_ID;
        protected BackgroundWorkerService mExecutorService = null;
        protected boolean allowParallel = false;

        protected Runnable getPreRunnable() {
            return new Runnable() {
                @Override
                public void run() {
                    ui_prepare();
                    mExecutorService.runMainAction(BaseRequest.this);
                }
            };
        }

        protected Runnable getMainRunnable() {
            return new Runnable() {
                @Override
                public void run() {
                    background_action();
                    mExecutorService.runPostAction(BaseRequest.this);
                }
            };
        }

        protected Runnable getPostRunnable() {
            return new Runnable() {
                @Override
                public void run() {
                    ui_post();
                    mExecutorService.requestFinished(BaseRequest.this);
                }
            };
        }

        private Logger log = new Logger();
        protected Logger d(final String fmt, final Object...args) {
            log.debug(String.format("Background.%s [%d]", getClass().getSimpleName(), getRequestId()),
                    fmt, args);
            return log;
        }

        public void logThisRequest() {
            d(".         Request class : %s", getClass().getCanonicalName());
            d(".            Request ID : %d", mRequestId);
            d(".         Current state : %s", mStatus.toString());
            d(".   Request description : %s", mDescription);
            d(". Request STRING filter : %s", mStrFilter);
            d(".Request INTEGER filter : %s", (mIntFilter == NO_FILTER) ? "no filter" : String.valueOf(mIntFilter));
        }

        protected boolean runningInUiThread() {
            return Looper.myLooper() == Looper.getMainLooper();
        }

        protected boolean runningInBackgroundThread() {
            return !runningInUiThread();
        }

        public String getStrFilter() {
            return mStrFilter;
        }

        public void setStrFilter(String strFilter) {
            mStrFilter = strFilter;
        }

        public int getIntFilter() {
            return mIntFilter;
        }

        public void setIntFilter(int intFilter) {
            mIntFilter = intFilter;
        }
    }

    //endregion

}
