package com.bluczak.albumofbeers.backend.utils;

import android.util.Log;

import org.joda.time.DateTime;

/**
 * Created by BLuczak on 2015-07-03.
 */
public class Logger {

    public Logger() {
        updateIndentation();
    }

    private static boolean ACTIVE = false;
    public static void setActiveState(final boolean active) {
        ACTIVE = active;
    }

    private static final int DEFAULT_INDENTATION_WIDTH = 3;
    private static final String DEFAULT_INDENTATION = "    ";

    private String mDefaultTag = Constants.EMPTY_STRING;
    private boolean mAppendTs = false;
    private int mIndentationWidth = 4;
    private int mIndentationLevel = 0;
    private boolean mEnabled = true;
    private String mIndentation = Constants.EMPTY_STRING;
    private String mIndentationDelta = DEFAULT_INDENTATION;
    private String mPrefix = Constants.EMPTY_STRING;
    private String mSuffix = Constants.EMPTY_STRING;

    private void updateIndentation() {
        if(!ACTIVE) return;
        StringBuilder sb = new StringBuilder();

        // first update intent delta for each intent level if needed
        if(mIndentationDelta.length() != mIndentationWidth) {
            for (int i = 0; i < mIndentationWidth; ++i) {
                sb.append(Constants.SPACE_CHARACTER);
            }
            mIndentationDelta = sb.toString();
        }

        // now update current indentation level
        sb = new StringBuilder();
        for(int i = 0; i < mIndentationLevel; ++i) {
            if(sb.length() <= 0) sb.append(Constants.DOT_CHARACTER);
            sb.append(mIndentationDelta);
        }
        mIndentation = sb.toString();
    }

    private String buildFinalText(final String fmt, Object... args) {
        if(!ACTIVE) return Constants.EMPTY_STRING;

        StringBuilder sb = new StringBuilder();

        if(mAppendTs) {
            sb.append(new DateTime().toString())
                    .append(Constants.SEMICOLON_CHARACTER)
                    .append(Constants.SPACE_CHARACTER);
        }
        if(mIndentationLevel > 0) {
            sb.append(mIndentation);
        }
        if(mPrefix != Constants.EMPTY_STRING) {
            sb.append(mPrefix);
        }

        sb.append(String.format(fmt, args));

        if(mSuffix != Constants.EMPTY_STRING) {
            sb.append(mSuffix);
        }

        return sb.toString();
    }

    public Logger setEnabled(final boolean state) {
        mEnabled = state;
        return this;
    }

    public Logger setPrefix(final String prefix) {
        mPrefix = prefix;
        if(mPrefix == null) {
            mPrefix = Constants.EMPTY_STRING;
        }
        return this;
    }

    public Logger setSuffix(final String sufix) {
        mSuffix = sufix;
        if(mSuffix == null) {
            mSuffix = Constants.EMPTY_STRING;
        }
        return this;
    }

    public Logger setDefaultTag(final String tag) {
        mDefaultTag = tag;
        if(mDefaultTag == null) {
            mDefaultTag = Constants.EMPTY_STRING;
        }
        return this;
    }

    public Logger setAppendTimestampTo(final boolean addTs) {
        mAppendTs = addTs;
        return this;
    }

    public Logger setIntentationWidth(final int w) {
        mIndentationWidth = w;
        updateIndentation();
        return this;
    }

    public Logger indent() {
        ++mIndentationLevel;
        updateIndentation();
        return this;
    }

    public Logger unindent() {
        --mIndentationLevel;
        if(mIndentationLevel < 0) {
            mIndentationLevel = 0;
        }
        updateIndentation();
        return this;
    }

    public Logger resetIndentations() {
        return setIndentationLevel(0);
    }

    public Logger setIndentationLevel(final int level) {
        mIndentationLevel = level;
        if(mIndentationLevel < 0) {
            mIndentationLevel = 0;
        }
        updateIndentation();
        return this;
    }

    //region Specific level of logs

    public Logger wtf(final String tag, final String fmt, final Object... args) {
        if(!ACTIVE || !mEnabled) return this;

        final String text = buildFinalText(fmt, args);
        Log.wtf(tag != null ? tag : mDefaultTag, text);
        return this;
    }

    public Logger info(final String tag, final String fmt, final Object... args) {
        if(!ACTIVE || !mEnabled) return this;

        final String text = buildFinalText(fmt, args);
        Log.i(tag != null ? tag : mDefaultTag, text);
        return this;
    }

    public Logger debug(final String tag, final String fmt, final Object... args) {
        if(!ACTIVE || !mEnabled) return this;

        final String text = buildFinalText(fmt, args);
        Log.d(tag != null ? tag : mDefaultTag, text);
        return this;
    }

    public Logger warning(final String tag, final String fmt, final Object... args) {
        if(!ACTIVE || !mEnabled) return this;

        final String text = buildFinalText(fmt, args);
        Log.w(tag != null ? tag : mDefaultTag, text);
        return this;
    }

    public Logger error(final String tag, final String fmt, final Object... args) {
        if(!ACTIVE || !mEnabled) return this;

        final String text = buildFinalText(fmt, args);
        Log.e(tag != null ? tag : mDefaultTag, text);
        return this;
    }

    public Logger error(final String tag, final Throwable ex, final String fmt, final Object... args) {
        if(!ACTIVE || !mEnabled) return this;

        final String text = buildFinalText(fmt, args);
        Log.e(tag != null ? tag : mDefaultTag, text, ex);
        return this;
    }

    //endregion

    //region Static, quick access to global logging functionality
    private static final Logger global = new Logger();

    public static void d(final String tag, final String fmt, final Object... args) {
        global.debug(tag, fmt, args);
    }

    public static void i(final String tag, final String fmt, final Object... args) {
        global.info(tag, fmt, args);
    }

    public static void e(final String tag, final String fmt, final Object... args) {
        global.error(tag, fmt, args);
    }

    public static void e(final String tag, final Throwable ex, final String fmt, final Object... args) {
        global.error(tag, ex, fmt, args);
    }

    public static void w(final String tag, final String fmt, final Object... args) {
        global.warning(tag, fmt, args);
    }

    public static void WTF(final String tag, final String fmt, final Object... args) {
        global.wtf(tag, fmt, args);
    }
    //endregion

}
