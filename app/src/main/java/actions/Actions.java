package actions;

/**
 * Created by nicklheureux on 7/13/17.
 */

public interface Actions {
    String GET_CALORIES = "get_calories";
    String GET_DATES = "get_date";

    void getDailyCalories();
    void getDate();
}
