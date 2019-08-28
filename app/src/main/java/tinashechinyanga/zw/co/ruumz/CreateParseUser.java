package tinashechinyanga.zw.co.ruumz;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

interface CreateParseUser {
    default void signUpUserInBackground(ParseUser user){
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    //no error == success
                    handleSuccessfullyCreateNewParseUser();
                }
                else{
                    handleErrorOnSignUp(e);
                }
            }
        });
    }

    void handleSuccessfullyCreateNewParseUser();
    void handleErrorOnSignUp(ParseException e);

    ParseUser user = new ParseUser();
}
