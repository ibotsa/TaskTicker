package com.ibotsa.android.taskticker;

/**
 * Created by Tobi__000 on 16.02.2015.
 */
public class Task {
    private Type mType;
    private long mSecondsRemaining;
    private boolean mStarted;

    public Task(Type type, long secondsRemaining) {
        mType = type;
        mSecondsRemaining = secondsRemaining;
        mStarted = false;
    }

    public Type getType() {
        return mType;
    }

    public long getSecondsRemaining() {
        return mSecondsRemaining;
    }

    public boolean isStarted() {
        return mStarted;
    }

    public void start() {
        mStarted = true;
    }
}
