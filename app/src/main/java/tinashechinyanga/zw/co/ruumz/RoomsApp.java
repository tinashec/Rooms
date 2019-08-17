package tinashechinyanga.zw.co.ruumz;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.facebook.ParseFacebookUtils;

/**
 * Created by MUZ0007 on 1/8/2016.
 */
public class RoomsApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        ParseObject.registerSubclass(Room.class);
        Parse.initialize(new Parse.Configuration.Builder(this).applicationId(getResources().getString(R.string.parse_app_id))
            .clientKey(getResources().getString(R.string.parse_client_key))
            .server(getResources().getString(R.string.parse_server_url))
            .build());

        ParseFacebookUtils.initialize(this);
    }
}
