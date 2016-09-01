package tinashechinyanga.zw.co.ruumz;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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
