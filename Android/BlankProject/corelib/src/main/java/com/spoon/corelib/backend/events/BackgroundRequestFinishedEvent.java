package com.spoon.corelib.backend.events;

import com.spoon.corelib.backend.services.BackgroundWorkerService;

/**
 * Created by Lycha on 9/5/2015.
 */
public class BackgroundRequestFinishedEvent extends BaseEvent<BackgroundWorkerService.BaseRequest> {

    private final long mRequestId;

    public BackgroundRequestFinishedEvent(final BackgroundWorkerService.BaseRequest request) {
        super(request);

        mRequestId = request.getRequestId();
    }

    public long getRequestId() {
        return mRequestId;
    }

    public BackgroundWorkerService.BaseRequest getRequest() {
        return getValue();
    }

    public boolean success() {
        return getStatus() == BackgroundWorkerService.RequestStatus.SUCCESS;
    }

    public BackgroundWorkerService.RequestStatus getStatus() {
        return getRequest().getStatus();
    }

}
