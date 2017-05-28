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
        Parse.initialize(new Parse.Configuration.Builder(this).applicationId("GXgvtP569LEzHZXJmuQv9ACgH7W5IQzrPaKU1pL4")
            .clientKey("ZtWq01jsDeNxSy1Xd0Htryea5VQAH107k38Bd4ob")
            .server("https://parseapi.back4app.com/")
            .build());
    }
}
