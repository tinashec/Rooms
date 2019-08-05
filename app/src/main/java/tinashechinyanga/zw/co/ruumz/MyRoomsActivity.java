package tinashechinyanga.zw.co.ruumz;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class MyRoomsActivity extends AppCompatActivity {

    private MyRoomsSectionsPagerAdapter myRoomsSectionsPagerAdapter;
    private ViewPager mMyRoomsViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rooms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //displays the back icon

        mMyRoomsViewPager = (ViewPager)findViewById(R.id.my_rooms_container);
        myRoomsSectionsPagerAdapter = new MyRoomsSectionsPagerAdapter(getSupportFragmentManager());

        //assign the viewpager
        mMyRoomsViewPager.setAdapter(myRoomsSectionsPagerAdapter);

        /*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.placeholder_fragment, new HomeFragment());
        fragmentTransaction.commit();*/
    }

}
