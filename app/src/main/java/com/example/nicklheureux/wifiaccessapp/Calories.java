package com.example.nicklheureux.wifiaccessapp;

/**
 * Created by nicklheureux on 7/13/17.
 */

public class Calories {
    private double calories;
    private double runningMinutes;
    private double bikingMinutes;
    private double walkingMinutes;
    private double percentage;
    final private double BIKING_CALORIES = 650;
    final private double WALKING_CALORIES = 415;
    final private double RUNNING_CALORIES = 557;

    public double getRunningMinutes() {
        return runningMinutes;
    }

    public double getBikingMinutes() {
        return runningMinutes;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public void setRunningMinutes(double runningMinutes) {
        this.runningMinutes = runningMinutes;
    }

    public void setBikingMinutes(double bikingMinutes) {
        this.bikingMinutes = bikingMinutes;
    }

    public void setWalkingMinutes(double walkingMinutes) {
        this.walkingMinutes = walkingMinutes;
    }

    public double getPercentage() {
        calculatePercentage();
        if (percentage > 1) {
            percentage = 1;
        }
        return percentage;
    }

    private void calculatePercentage() {
        double totalCalories = ((runningMinutes / 60) * RUNNING_CALORIES)
                + ((walkingMinutes / 60) * WALKING_CALORIES)
                + ((bikingMinutes / 60 ) * BIKING_CALORIES);
        percentage = totalCalories / calories;
    }

}
