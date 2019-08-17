package tinashechinyanga.zw.co.ruumz;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText resetPasswordEmail;
    private Button resetPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        resetPasswordEmail = findViewById(R.id.password_reset_email);
        resetPasswordButton = findViewById(R.id.password_reset_email_button);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        resetPasswordEmail.setError(null);

        String email = resetPasswordEmail.getText().toString();

        View focusView = null;
        boolean cancel = false;
        //check if email is valid
        if(TextUtils.isEmpty(email.trim()) || !email.contains("@")){
            resetPasswordEmail.setError("Invalid email address");
            focusView = resetPasswordEmail;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }else {
            ParseUser.requestPasswordResetInBackground(resetPasswordEmail.getText().toString().trim(), new RequestPasswordResetCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        //password reset sent to email successfully
                        Toast.makeText(ForgotPasswordActivity.this, "Password reset sent to your email.", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        //error occurred
                        Toast.makeText(ForgotPasswordActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
