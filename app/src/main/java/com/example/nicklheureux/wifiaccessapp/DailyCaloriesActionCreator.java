package com.example.nicklheureux.wifiaccessapp;

import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.action.RxActionCreator;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.util.SubscriptionManager;

import java.util.concurrent.Callable;

import actions.Actions;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import rx.Subscription;

import static keys.Keys.CALORIES;
import static keys.Keys.DATE;

/**
 * Created by nicklheureux on 7/13/17.
 */

public class DailyCaloriesActionCreator extends RxActionCreator implements Actions {

    public DailyCaloriesActionCreator(Dispatcher dispatcher, SubscriptionManager manager) {
        super(dispatcher, manager);
    }

    @Override
    public void getDailyCalories() {
        final RxAction action = newRxAction(GET_CALORIES);
        if (hasRxAction(action)) return;
        Callable<String> callable = new MyCallable();
        addRxAction(action, (Subscription) Flowable.fromCallable(callable)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(calories -> postRxAction(newRxAction(GET_CALORIES, CALORIES, calories))));
    }


    @Override
    public void getDate() {
        final RxAction action = newRxAction(GET_DATES);
        if (hasRxAction(action)) return;
        Callable<String> callable = new MyCallable();
        addRxAction(action, (Subscription) Flowable.fromCallable(callable)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(dates -> postRxAction(newRxAction(GET_DATES, DATE, dates))));
    }

    private class MyCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            Thread.sleep(100);
            return Thread.currentThread().getName();
        }

    }
}

