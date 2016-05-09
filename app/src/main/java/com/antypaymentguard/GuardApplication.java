package com.antypaymentguard;

import com.activeandroid.app.Application;

/**
 * Created by Maciej Kozłowski on 09.05.16.
 */
public class GuardApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static GuardApplication getInstance() {
        return instance;
    }

    private static GuardApplication instance;
}
