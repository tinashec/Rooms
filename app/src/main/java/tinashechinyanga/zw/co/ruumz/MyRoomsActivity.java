package tinashechinyanga.zw.co.ruumz;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

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

        /*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.placeholder_fragment, new RoomsFragment());
        fragmentTransaction.commit();*/
    }

}
