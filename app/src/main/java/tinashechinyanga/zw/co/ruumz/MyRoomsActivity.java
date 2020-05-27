package tinashechinyanga.zw.co.ruumz;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MyRoomsActivity extends AppCompatActivity {

    private MyRoomsSectionsPagerAdapter myRoomsSectionsPagerAdapter;
    private ViewPager mMyRoomsViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rooms);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //displays the back icon

        mMyRoomsViewPager = findViewById(R.id.my_rooms_container);
        myRoomsSectionsPagerAdapter = new MyRoomsSectionsPagerAdapter(getSupportFragmentManager());

        //assign the viewpager
        mMyRoomsViewPager.setAdapter(myRoomsSectionsPagerAdapter);

        FloatingActionButton addMyRoomsFab = findViewById(R.id.addMyRoomFab);
        addMyRoomsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add new listing", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Toast.makeText(MyRoomsActivity.this, "1 of 4", Toast.LENGTH_LONG).show();
                Intent addListingIntent = new Intent(MyRoomsActivity.this, AddRoomActivity.class);
                startActivity(addListingIntent);
            }
        });
        /*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.placeholder_fragment, new RoomsFragment());
        fragmentTransaction.commit();*/
    }

}
