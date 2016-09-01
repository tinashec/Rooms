package tinashechinyanga.zw.co.ruumz;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by MUZ0007 on 1/8/2016.
 */
public class RoomsApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        ParseObject.registerSubclass(Room.class);
        Parse.initialize(this);
    }
}
