package com.example.nicklheureux.wifiaccessapp;

/**
 * Created by nicklheureux on 7/16/17.
 */

import android.app.Application;
import android.content.Context;
import com.hardsoftstudio.rxflux.RxFlux;
import com.hardsoftstudio.rxflux.util.LogLevel;

/**
 * Created by marcel on 10/09/15.
 */
public class WifiAccessApp extends Application {

    private RxFlux rxFlux;

    /**
     * Please, note that it would be much better to use a singleton patter or DI instead of keeping
     * the variable reference here.
     */
    private DailyCaloriesActionCreator dailyCaloriesActionCreator;

//    public static WifiAccessApp get(Context context) {
//        return ((WifiAccessApp) context.getApplicationContext());
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        RxFlux.LOG_LEVEL = BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE;
        rxFlux = RxFlux.init(this);
        dailyCaloriesActionCreator =
                new DailyCaloriesActionCreator(rxFlux.getDispatcher(), rxFlux.getSubscriptionManager());
    }

    public RxFlux getRxFlux() {
        return rxFlux;
    }

    public DailyCaloriesActionCreator getGitHubActionCreator() {
        return dailyCaloriesActionCreator;
    }
}
