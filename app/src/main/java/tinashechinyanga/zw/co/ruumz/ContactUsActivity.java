package tinashechinyanga.zw.co.ruumz;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

public class ContactUsActivity extends AppCompatActivity {
    private ImageView mContactUsImage;
    private LinearLayout mLayout1;
    private LinearLayout mLayout2;
    private LinearLayout mLayoutWhatsApp;
    private LinearLayout mLayoutWebsite;
    private TextView mCall1;
    private TextView mCall2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        mContactUsImage = findViewById(R.id.contact_us_imageview);
        mLayout1 = findViewById(R.id.contact_us_llayout1);
        mLayout2 = findViewById(R.id.contact_us_llayout2);
        mLayoutWhatsApp = findViewById(R.id.contact_us_wapp_llayout);
        mLayoutWebsite= findViewById(R.id.contact_us_site_llayout);
        mCall1 = findViewById(R.id.contact_us_call1_textview);
        mCall2 = findViewById(R.id.contact_us_call2_textview);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        Glide.with(this).load(R.drawable.room_photo).centerCrop().crossFade().into(mContactUsImage);

        mLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + mCall1.getText().toString()));
                startActivity(callIntent);
            }
        });

        mLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + mCall2.getText().toString()));
                startActivity(callIntent);
            }
        });

        mLayoutWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsAppIntent = new Intent(Intent.ACTION_SEND);
                whatsAppIntent.putExtra(Intent.EXTRA_TEXT, "Hi, I've just come across your listing on the Rooms" +
                        "app and would like to see a few more pictures and possibly come see the place. " +
                        "Looking forward to hearing from you. Cheers.");
                whatsAppIntent.setType("text/plain");
                //check id there is whatsapp installed
                if(whatsAppIntent.resolveActivity(getPackageManager()) != null){
                    whatsAppIntent.setPackage("com.whatsapp");
                    startActivity(whatsAppIntent);
                }

            }
        });

        mLayoutWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent visitWebsite = new Intent(Intent.ACTION_VIEW, Uri.parse("http://rooms.co.zw"));
                if(visitWebsite.resolveActivity(getPackageManager()) != null) {
                    startActivity(visitWebsite);
                }
            }
        });


        //adding a back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);          //show the back button icon
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        super.onOptionsItemSelected(menuItem);
        switch (menuItem.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return false;
    }
}
