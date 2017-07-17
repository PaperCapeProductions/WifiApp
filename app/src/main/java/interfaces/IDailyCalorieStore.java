package interfaces;

import com.example.nicklheureux.wifiaccessapp.Calories;

import java.util.Date;
import java.util.List;

/**
 * Created by nicklheureux on 7/13/17.
 */

public interface IDailyCalorieStore {
    List<Calories> getCalories();
    List<Date> getDates();
}
