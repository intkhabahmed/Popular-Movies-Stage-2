package com.intkhabahmed.popularmoviesstage2.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    private Executor diskIO;
    private Executor mainThreadIO;
    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;

    private AppExecutors(Executor diskIO, Executor mainThreadIO) {
        this.diskIO = diskIO;
        this.mainThreadIO = mainThreadIO;
    }

    public static AppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppExecutors(Executors.newSingleThreadScheduledExecutor(), new MainThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor mainThreadIO() {
        return mainThreadIO;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
