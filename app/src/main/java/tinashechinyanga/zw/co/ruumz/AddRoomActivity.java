package tinashechinyanga.zw.co.ruumz;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/*
* implements interfaces for the datedialog fragment and the other three fragments used to
* capture room attributes
* */
public class AddRoomActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        AddRoomInfoFragment.GetRoomInfo, AddRoomDetailFragment.GetRoomDetail, AddLocationFragment.GetLocationAmenitiesPref {
    private ViewPager mViewPager;
    private AddRoomPagerAdapter mAddRoomPagerAdapter;

    private AddPhotosFragment addPhotosFragment;
    public String date;
    private AddRoomDetailFragment addRoomDetailFragment;

    //the room attributes captured in the other fragments
    /*
    * 1. AddRoomInfoFragment attributes
    * 2. AddDetailInfoFragment attributes
    * 3. AddLocationFragment attributes
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        //include back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //icon in appbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);      //back button in android
        getSupportActionBar().setHomeButtonEnabled(true);

        mAddRoomPagerAdapter = new AddRoomPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        //keep 3 fragments on either side of the current fragment alive in memory
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mAddRoomPagerAdapter);
    }

    //method to move fragment to activity via button click
    public void setCurrentItem(int item, boolean smoothScroll){
        mViewPager.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Bundle bundle = new Bundle();
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        //format the date
        Date theDate = c.getTime();
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this);
        String date = dateFormat.format(theDate);

        //pass it back to activity
        AddRoomDetailFragment fragment = (AddRoomDetailFragment) mAddRoomPagerAdapter.getRegisteredFragment(1);
        fragment.setMoveInDate(date);

        Log.d("Year", date);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        }
    }

    /*methods in the interface implemented to set room data supplied in AddRoomInfoFragment
    * these methods in the interface supply the information which is then
    * routed and forwarded to AddPhotosFragment, the last step in adding room attributes to
    * the backend.
    * */

    //1.
    @Override
    public void getRoomNumBedrooms(int numBeds) {
        Log.d("Number of Beds: ", Integer.toString(numBeds));
        //communicate the data to the AddPhotosFragment
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setNumBeds(numBeds);
    }

    @Override
    public void getRoomNumBaths(int numBaths) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setNumBaths(numBaths);
    }

    @Override
    public void getRoomNumToilets(int numToilets) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setNumToilets(numToilets);
    }

    @Override
    public void getRoomPropertyType(String propertyType) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setPropertyType(propertyType);
    }

    @Override
    public void getRoomSharingYesOrNo(String sharingYesOrNo) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setPropertySharedOrNot(sharingYesOrNo);
    }

    //2.
    @Override
    public void passMonthlyRent(int rentAmount) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setMonthlyRent(rentAmount);
    }

    @Override
    public void passDeposit(int deposit) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setDepost(deposit);
    }

    @Override
    public void passRentInclOrExclOfBills(String rentInclOrExlcOfBills) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setRentInclOrNotOfBills(rentInclOrExlcOfBills);
    }

    @Override
    public void passMoveInDate(String moveInDate) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setMoveInDate(moveInDate);
    }

    @Override
    public void passRoomDescription(String roomDescription) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setRoomDescription(roomDescription);
    }

    //3.
    @Override
    public void passCity(String city) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setCity(city);
    }

    @Override
    public void passSuburb(String suburb) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setSuburb(suburb);
    }

    @Override
    public void passBIC(boolean bic) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setBIC(bic);
    }

    @Override
    public void passOwnEntrance(boolean ownEntrance) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setOwnEntrance(ownEntrance);
    }

    @Override
    public void passOwnToilet(boolean ownToilet) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setOwnToilet(ownToilet);
    }

    @Override
    public void passKitchen(boolean kitchen) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setKitchen(kitchen);
    }

    @Override
    public void passParkingAvail(boolean parkingAvail) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setParking(parkingAvail);
    }

    @Override
    public void passWifiAvail(boolean wifiAvail) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setWifi(wifiAvail);
    }

    @Override
    public void passSecure(boolean secure) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setSecure(secure);
    }

    @Override
    public void passFurnished(boolean furnished) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setFurnished(furnished);
    }

    @Override
    public void passBoreholeAvail(boolean boreholeAvail) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setBorehole(boreholeAvail);
    }

    @Override
    public void passPrepaidZesa(boolean prepaidZesa) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setPrepaidZesa(prepaidZesa);
    }

    @Override
    public void passFittedWardrobe(boolean fittedWardrobe) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setFittedWardrobe(fittedWardrobe);
    }

    @Override
    public void passPrepaidWater(boolean prepaidWater) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setPrepaidWater(prepaidWater);
    }

    @Override
    public void passSmallFamilyPreferred(boolean smallFamily) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setSmallFamily(smallFamily);
    }

    @Override
    public void passFemalePreferred(boolean female) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setFemale(female);
    }

    @Override
    public void passMalePreferred(boolean male) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setMale(male);
    }

    @Override
    public void passSoberHabitsPreferred(boolean soberHabits) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setSoberHabits(soberHabits);
    }

    @Override
    public void passProfessionalPreferred(boolean professional) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setProfessional(professional);
    }

    @Override
    public void passCouplePreferred(boolean couple) {
        addPhotosFragment = (AddPhotosFragment)mAddRoomPagerAdapter.getRegisteredFragment(3);
        addPhotosFragment.setCouple(couple);
    }

    /*methods to set room attributes captured in AddRoomDetailFragment*/
    //2.

}
