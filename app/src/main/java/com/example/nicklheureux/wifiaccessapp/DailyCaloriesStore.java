package com.example.nicklheureux.wifiaccessapp;

/**
 * Created by nicklheureux on 7/13/17.
 */

import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;

import java.util.Date;

import java.util.ArrayList;

import actions.Actions;
import interfaces.IDailyCalorieStore;
import keys.Keys;


public class DailyCaloriesStore extends RxStore implements IDailyCalorieStore {

    public static final String ID = "DailyCalorieStore";
    private ArrayList<Calories> calories;
    private ArrayList<Date> dates;
    private static DailyCaloriesStore instance;

    public DailyCaloriesStore(Dispatcher dispatcher) {
        super(dispatcher);
    }


    public static synchronized DailyCaloriesStore get(Dispatcher dispatcher) {
        if (instance == null) instance = new DailyCaloriesStore(dispatcher);
        return instance;
    }


    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case Actions.GET_CALORIES:
                this.calories = (ArrayList<Calories>) action.getData().get(Keys.CALORIES);
                break;
            case Actions.GET_DATES:
                this.dates = (ArrayList<Date>) action.getData().get(Keys.DATE);
                break;

            default: // IMPORTANT if we don't modify the store just ignore
                return;
        }

        postChange(new RxStoreChange(ID, action));

    }

    @Override
    public ArrayList<Calories> getCalories() {
        return calories == null ? new ArrayList<Calories>() : calories;
    }

    @Override
    public ArrayList<Date> getDates() {
        return dates == null ? new ArrayList<Date>() : dates;
    }

}
