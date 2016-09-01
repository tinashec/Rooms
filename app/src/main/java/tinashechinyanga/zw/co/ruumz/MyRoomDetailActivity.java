package tinashechinyanga.zw.co.ruumz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyRoomDetailActivity extends AppCompatActivity implements RoomImageOneFragment.GetImageUrlListener,
        RoomImageTwoFragment.SetImageTwoUrl, RoomImageThreeFragment.SetImageThreeUrl{

    //views
    private ImageView mRoomImages;
    private ViewPager mImagesViewPager;
    private RoomImagesPagerAdapter mRoomImageAdapter;
    private TextView mPropertyType, mSharedOrNot, mSuburb, mCity;
    private ImageView mPropertyTypeImage, mLocationImage;
    private TextView mBedrooms;
    private TextView mBathrooms;
    private TextView mToilets;
    private TextView mRentAmount, mInclusiveOfRentOrNot, mDepositAmount;
    private TextView mMoveInDate;
    private boolean mBIC, mOwnEntrance, mOwnToilet, mKitchen, mParking, mWifi, mSecure, mFurnished,
                    mBorehole, mPrepaidZesa, mFittedWardrobe, mPrepaidWater;

    private LinearLayout layout2, layout3;
    private LinearLayout prefLayout2;

    private ImageView mImage1, mImage2, mImage3, mImage4, mImage5, mImage6, mImage7, mImage8, mImage9, mImage10, mImage11, mImage12;
    private TextView mText1, mText2, mText3, mText4, mText5, mText6, mText7, mText8, mText9, mText10, mText11, mText12;

    private ImageView mImage1Pref, mImage2Pref, mImage3Pref, mImage4Pref, mImage5Pref, mImage6Pref;
    private TextView mText1Pref, mText2Pref, mText3Pref, mText4Pref, mText5Pref, mText6Pref;
    private TextView mDescription;

    private ParseProxyObject roomObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_room_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //displays the back icon

        //get the object passed by the parent activity
        Intent intent = getIntent();
        roomObject = (ParseProxyObject)intent.getSerializableExtra("roomObject");

        //attach the views
        //mRoomImages = (ImageView)findViewById(R.id.details_roomImage_imageview);
        mImagesViewPager = (ViewPager)findViewById(R.id.details_images_viewpager);
        mPropertyType = (TextView) findViewById(R.id.details_propertyType_label);
        mPropertyTypeImage = (ImageView)findViewById(R.id.details_propertyType_imageview);
        mLocationImage = (ImageView)findViewById(R.id.details_location_imageview);
        mSharedOrNot = (TextView)findViewById(R.id.details_roomSharedOrNot_label);
        mSuburb = (TextView) findViewById(R.id.details_suburb_label);
        mCity = (TextView) findViewById(R.id.details_roomCity_label);

        mBedrooms = (TextView) findViewById(R.id.details_actualRooms_label);
        mBathrooms = (TextView) findViewById(R.id.details_actualBaths_label);
        mToilets = (TextView) findViewById(R.id.details_actualToilets_label);
        mRentAmount = (TextView) findViewById(R.id.details_actualRent_amount);
        mInclusiveOfRentOrNot = (TextView) findViewById(R.id.details_actualInclExcl_label);
        mDepositAmount = (TextView) findViewById(R.id.details_roomDeposit_label);
        mMoveInDate = (TextView) findViewById(R.id.details_actualDate_label);

        //amenities section
        layout2 = (LinearLayout)findViewById(R.id.details_amenities_llayout2);
        layout3 = (LinearLayout)findViewById(R.id.details_amenities_llayout3);

        mImage1 = (ImageView)findViewById(R.id.details_image_1);
        mImage2 = (ImageView)findViewById(R.id.details_image_2);
        mImage3 = (ImageView)findViewById(R.id.details_image_3);
        mImage4 = (ImageView)findViewById(R.id.details_image_4);
        mImage5 = (ImageView)findViewById(R.id.details_image_5);
        mImage6 = (ImageView)findViewById(R.id.details_image_6);
        mImage7 = (ImageView)findViewById(R.id.details_image_7);
        mImage8 = (ImageView)findViewById(R.id.details_image_8);
        mImage9 = (ImageView)findViewById(R.id.details_image_9);
        mImage10 = (ImageView)findViewById(R.id.details_image_10);
        mImage11 = (ImageView)findViewById(R.id.details_image_11);
        mImage12 = (ImageView)findViewById(R.id.details_image_12);

        mText1 = (TextView)findViewById(R.id.details_textview_1);
        mText2 = (TextView)findViewById(R.id.details_textview_2);
        mText3 = (TextView)findViewById(R.id.details_textview_3);
        mText4 = (TextView)findViewById(R.id.details_textview_4);
        mText5 = (TextView)findViewById(R.id.details_textview_5);
        mText6 = (TextView)findViewById(R.id.details_textview_6);
        mText7 = (TextView)findViewById(R.id.details_textview_7);
        mText8 = (TextView)findViewById(R.id.details_textview_8);
        mText9 = (TextView)findViewById(R.id.details_textview_9);
        mText10 = (TextView)findViewById(R.id.details_textview_10);
        mText11 = (TextView)findViewById(R.id.details_textview_11);
        mText12 = (TextView)findViewById(R.id.details_textview_12);

        //preferences section
        prefLayout2 = (LinearLayout)findViewById(R.id.details_pref_llayout2);

        mImage1Pref = (ImageView)findViewById(R.id.details_pref_image1);
        mImage2Pref = (ImageView)findViewById(R.id.details_pref_image2);
        mImage3Pref = (ImageView)findViewById(R.id.details_pref_image3);
        mImage4Pref = (ImageView)findViewById(R.id.details_pref_image4);
        mImage5Pref = (ImageView)findViewById(R.id.details_pref_image5);
        mImage6Pref = (ImageView)findViewById(R.id.details_pref_image6);

        mText1Pref = (TextView)findViewById(R.id.details_pref_text1);
        mText2Pref = (TextView)findViewById(R.id.details_pref_text2);
        mText3Pref = (TextView)findViewById(R.id.details_pref_text3);
        mText4Pref = (TextView)findViewById(R.id.details_pref_text4);
        mText5Pref = (TextView)findViewById(R.id.details_pref_text5);
        mText6Pref = (TextView)findViewById(R.id.details_pref_text6);

        mDescription = (TextView)findViewById(R.id.details_description_label);

        /*set the views using the proxy object*/
        //mRoomImages.setImageBitmap(roomObject.getParseFile("roomImage1"));
        //setting up the images fragments
        mRoomImageAdapter = new RoomImagesPagerAdapter(getSupportFragmentManager());
        mImagesViewPager.setAdapter(mRoomImageAdapter);

        if(roomObject.getString("roomPropertyType") != null){
            switch(roomObject.getString("roomPropertyType")){
                case "Flat":
                    mPropertyTypeImage.setImageResource(R.mipmap.ic_flat);
                    break;
                case "Full House":
                    mPropertyTypeImage.setImageResource(R.mipmap.ic_fullhouse);
                    break;
                case "Cottage":
                    mPropertyTypeImage.setImageResource(R.mipmap.ic_cottage);
                    break;
                default:
                    mPropertyTypeImage.setImageResource(R.mipmap.ic_cottage);
                    break;
            }
        }

        mPropertyType.setText(roomObject.getString("roomPropertyType"));
        mSharedOrNot.setText(roomObject.getString("roomSharing"));
        mSuburb.setText(roomObject.getString("roomSuburb"));
        mCity.setText(roomObject.getString("roomCity"));

        mBedrooms.setText(Integer.toString(roomObject.getInt("roomBedrooms")));
        mBathrooms.setText(Integer.toString(roomObject.getInt("roomBathrooms")));
        mToilets.setText(Integer.toString(roomObject.getInt("roomToilets")));
        mRentAmount.setText(Integer.toString(roomObject.getInt("roomMonthlyRent")));
        mInclusiveOfRentOrNot.setText(roomObject.getString("roomRentInclusiveOfBills"));
        mDepositAmount.setText(Integer.toString(roomObject.getInt("roomDeposit")));
        mMoveInDate.setText(roomObject.getString("roomMoveInDate"));

        //set the amenities
        mBIC = roomObject.getBoolean("roomBICAvailable");
        mOwnEntrance = roomObject.getBoolean("roomOwnEntrance");
        mOwnToilet = roomObject.getBoolean("roomOwnToilet");
        mKitchen = roomObject.getBoolean("roomKitchen");
        mParking = roomObject.getBoolean("roomParkingAvailable");
        mWifi = roomObject.getBoolean("roomWifiAvailable");
        mSecure = roomObject.getBoolean("roomSecureLocation");
        mFurnished = roomObject.getBoolean("roomFurnished");
        mBorehole = roomObject.getBoolean("roomBoreholeAvailable");
        mPrepaidZesa = roomObject.getBoolean("roomPrepaidZESA");
        mFittedWardrobe = roomObject.getBoolean("roomFittedWardrobe");
        mPrepaidWater = roomObject.getBoolean("roomPrepaidWater");

        //check the true values set
        ArrayList<Boolean> amenities = new ArrayList<>(12);
        amenities.add(0, mBIC);
        amenities.add(1, mOwnEntrance);
        amenities.add(2, mOwnToilet);
        amenities.add(3, mKitchen);
        amenities.add(4, mParking);
        amenities.add(5, mWifi);
        amenities.add(6, mSecure);
        amenities.add(7, mFurnished);
        amenities.add(8, mBorehole);
        amenities.add(9, mPrepaidZesa);
        amenities.add(10, mFittedWardrobe);
        amenities.add(11, mPrepaidWater);

        ArrayList<Boolean> preferences = new ArrayList<>(6);
        preferences.add(0, roomObject.getBoolean("roomSmallFamilyPreferred"));
        preferences.add(1, roomObject.getBoolean("roomFemalePreferred"));
        preferences.add(2, roomObject.getBoolean("roomMalePreferred"));
        preferences.add(3, roomObject.getBoolean("roomSoberHabitsPreferred"));
        preferences.add(4, roomObject.getBoolean("roomProfessionalPreferred"));
        preferences.add(5, roomObject.getBoolean("roomYoungCouplePreferred"));

        List<Features> features = new ArrayList<Features>();
        List<PreferencesAmenity> preferencesAmenities = new ArrayList<PreferencesAmenity>();

        //loop through and keep count of the number of true values
        for(int ctr = 0; ctr<=amenities.size()-1; ctr++){
            if(amenities.get(ctr).equals(true)){
                Log.d("Count: ", Integer.toString(ctr));
                switch (ctr){
                    case 0:
                        //bic
                        //add to the features list
                        features.add(new Features(R.mipmap.ic_bic, getString(R.string.add_room_bic_amenity)));

                        Log.d("View added ", "Size: " + Integer.toString(features.size()));

                        break;
                    case 1:
                        //ownEntrance
                        //add to the features list
                        features.add(new Features(R.mipmap.ic_entrance, getString(R.string.add_room_entrance_amenity)));
                        Log.d("View added", Integer.toString(features.size()));
                        break;
                    case 2:
                        //ownToilet
                        //add to the features list
                        features.add( new Features(R.mipmap.ic_toilet, getString(R.string.add_room_toilet_amenity)));

                        Log.d("View added", Integer.toString(features.size()));
                        break;
                    case 3:
                        //kitchen
                        //add to the features list
                        features.add(new Features(R.mipmap.ic_kitchen, getString(R.string.add_room_kitchen_amenity)));

                        Log.d("View added", Integer.toString(features.size()));
                        break;
                    case 4:
                        //parking
                        //add to the features list
                        features.add(new Features(R.mipmap.ic_parking, getString(R.string.add_room_parking_amenity)));
                        Log.d("View added", Integer.toString(features.size()));
                        break;
                    case 5:
                        //wifi
                        //add to the features list
                        features.add( new Features(R.mipmap.ic_wi_fi, getString(R.string.add_room_wifi_amenity)));
                        Log.d("View added", Integer.toString(features.size()));
                        break;
                    case 6:
                        //secure
                        //add to the features list
                        features.add(new Features(R.mipmap.ic_secure, getString(R.string.add_room_secure_amenity)));

                        Log.d("View added", Integer.toString(features.size()));
                        break;
                    case 7:
                        //furnished
                        //add to the features list
                        features.add(new Features(R.mipmap.ic_furnished, getString(R.string.add_room_furnished_amenity)));
                        Log.d("View added", Integer.toString(features.size()));
                        break;
                    case 8:
                        //borehole
                        //add to the features list
                        features.add(new Features(R.mipmap.ic_borehole, getString(R.string.add_room_borehole_amenity)));

                        Log.d("View added", Integer.toString(features.size()));
                        break;
                    case 9:
                        //prepaidZesa
                        //add to the features list
                        features.add(new Features(R.mipmap.ic_zesa, getString(R.string.add_room_zesa_amenity)));
                        Log.d("View added", Integer.toString(features.size()));
                        break;
                    case 10:
                        //fittedWardrobe
                        //add to the features list
                        features.add(new Features(R.mipmap.ic_wardrobe, getString(R.string.add_room_wardrobe_amenity)));

                        Log.d("View added", Integer.toString(features.size()));
                        break;
                    case 11:
                        //prepaidWater
                        //add to the features list
                        features.add(new Features(R.mipmap.ic_prewater, getString(R.string.add_room_water_amenity)));

                        Log.d("View added", Integer.toString(features.size()));
                        break;
                    default:
                        break;
                }
            }
        }

        //loop through and keep count of the number of true values for the preferences set
        for(int ctr = 0; ctr<=preferences.size()-1; ctr++) {
            if (preferences.get(ctr).equals(true)) {
                Log.d("Count: ", Integer.toString(ctr));
                switch (ctr) {
                    case 0:
                        //small fam
                        //add to the features list
                        preferencesAmenities.add(new PreferencesAmenity(R.mipmap.ic_fam, getString(R.string.add_room_family_amenity)));

                        Log.d("View added ", "Size: " + Integer.toString(preferencesAmenities.size()));

                        break;
                    case 1:
                        //female
                        //add to the features list
                        preferencesAmenities.add(new PreferencesAmenity(R.mipmap.ic_female, getString(R.string.add_room_female_preference)));
                        Log.d("View added", Integer.toString(features.size()));
                        break;
                    case 2:
                        //male
                        //add to the features list
                        preferencesAmenities.add(new PreferencesAmenity(R.mipmap.ic_male, getString(R.string.add_room_male_preference)));

                        Log.d("View added", Integer.toString(features.size()));
                        break;
                    case 3:
                        //sober habits
                        //add to the features list
                        preferencesAmenities.add(new PreferencesAmenity(R.mipmap.ic_sober, getString(R.string.add_room_habits_amenity)));

                        Log.d("View added", Integer.toString(features.size()));
                        break;
                    case 4:
                        //professional
                        //add to the features list
                        preferencesAmenities.add(new PreferencesAmenity(R.mipmap.ic_pro, getString(R.string.add_room_professional_amenity)));
                        Log.d("View added", Integer.toString(features.size()));
                        break;
                    case 5:
                        //couple
                        //add to the features list
                        preferencesAmenities.add(new PreferencesAmenity(R.mipmap.ic_couple, getString(R.string.add_room_couple_amenity)));
                        Log.d("View added", Integer.toString(features.size()));
                        break;
                }
            }
        }

        Log.d("Layouts in features: ", Integer.toString(features.size()));

        //add the set features to the already existing view and hide layouts not to be shown
        //if no features, hide amenities section
        if(features.size() == 0){
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.GONE);
            mText1.setText("None Set.");
        }
        else if (features.size() > 0 && features.size() <= 4){
            //hide other 2 linear layouts
            //add the features to the views
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.GONE);

            //use the items in features to set each of the views incrementally
            for (int i = 0; i <= features.size() - 1; i++){
                switch (i){
                    case 0:
                        mImage1.setImageResource(features.get(i).getFeaturesImageView());
                        mText1.setText(features.get(i).getFeaturesTextView());
                        break;
                    case 1:
                        mImage2.setImageResource(features.get(i).getFeaturesImageView());
                        mText2.setText(features.get(i).getFeaturesTextView());
                        break;
                    case 2:
                        mImage3.setImageResource(features.get(i).getFeaturesImageView());
                        mText3.setText(features.get(i).getFeaturesTextView());
                        break;
                    case 3:
                        mImage4.setImageResource(features.get(i).getFeaturesImageView());
                        mText4.setText(features.get(i).getFeaturesTextView());
                        break;
                }
            }

        }else if(features.size() > 4 && features.size() < 8){
            //hide layout3
            layout3.setVisibility(View.GONE);

            //use the items in features to set each of the views incrementally
            for (int i = 0; i <= features.size() - 1; i++) {
                switch (i) {
                    case 0:
                        mImage1.setImageResource(features.get(i).getFeaturesImageView());
                        mText1.setText(features.get(i).getFeaturesTextView());
                        break;
                    case 1:
                        mImage2.setImageResource(features.get(i).getFeaturesImageView());
                        mText2.setText(features.get(i).getFeaturesTextView());
                        break;
                    case 2:
                        mImage3.setImageResource(features.get(i).getFeaturesImageView());
                        mText3.setText(features.get(i).getFeaturesTextView());
                        break;
                    case 3:
                        mImage4.setImageResource(features.get(i).getFeaturesImageView());
                        mText4.setText(features.get(i).getFeaturesTextView());
                        break;
                    case 4:
                        mImage5.setImageResource(features.get(i).getFeaturesImageView());
                        mText5.setText(features.get(i).getFeaturesTextView());
                        break;
                    case 5:
                        mImage6.setImageResource(features.get(i).getFeaturesImageView());
                        mText6.setText(features.get(i).getFeaturesTextView());
                        break;
                    case 6:
                        mImage7.setImageResource(features.get(i).getFeaturesImageView());
                        mText7.setText(features.get(i).getFeaturesTextView());
                        break;
                    case 7:
                        mImage8.setImageResource(features.get(i).getFeaturesImageView());
                        mText8.setText(features.get(i).getFeaturesTextView());
                        break;
                }
            }
        }else{
                //use the items in features to set each of the views incrementally
            for (int i = 0; i <= features.size() - 1; i++) {
                switch (i) {
                    case 0:
                        mImage1.setImageResource(features.get(i).getFeaturesImageView());
                        mText1.setText(features.get(i).getFeaturesTextView());
                        break;
                    case 1:
                        mImage2.setImageResource(features.get(i).getFeaturesImageView());
                        mText2.setText(features.get(i).getFeaturesTextView());
                        break;
                    case 2:
                        mImage3.setImageResource(features.get(i).getFeaturesImageView());
                        mText3.setText(features.get(i).getFeaturesTextView());
                        break;
                    case 3:
                        mImage4.setImageResource(features.get(i).getFeaturesImageView());
                        mText4.setText(features.get(i).getFeaturesTextView());
                        break;
                    case 4:
                        mImage5.setImageResource(features.get(i).getFeaturesImageView());
                        mText5.setText(features.get(i).getFeaturesTextView());
                        break;
                    case 5:
                        mImage6.setImageResource(features.get(i).getFeaturesImageView());
                        mText6.setText(features.get(i).getFeaturesTextView());
                        break;
                    case 6:
                        mImage7.setImageResource(features.get(i).getFeaturesImageView());
                        mText7.setText(features.get(i).getFeaturesTextView());
                        break;
                    case 7:
                        mImage8.setImageResource(features.get(i).getFeaturesImageView());
                        mText8.setText(features.get(i).getFeaturesTextView());
                        break;
                    case 8:
                        mImage9.setImageResource(features.get(i).getFeaturesImageView());
                        mText9.setText(features.get(i).getFeaturesTextView());
                        break;
                    case 9:
                        mImage10.setImageResource(features.get(i).getFeaturesImageView());
                        mText10.setText(features.get(i).getFeaturesTextView());
                        break;
                    case 10:
                        mImage11.setImageResource(features.get(i).getFeaturesImageView());
                        mText11.setText(features.get(i).getFeaturesTextView());
                        break;
                    case 11:
                        mImage12.setImageResource(features.get(i).getFeaturesImageView());
                        mText12.setText(features.get(i).getFeaturesTextView());
                        break;
                }
            }
        }

        //set the preferences section
        if(preferencesAmenities.size() == 0){
            prefLayout2.setVisibility(View.GONE);
            mText1Pref.setText("None Set.");
        }
        else if (preferencesAmenities.size() > 0 && preferencesAmenities.size() <= 3){
            //hide other 2 linear layouts
            //add the features to the views
            prefLayout2.setVisibility(View.GONE);

            //use the items in features to set each of the views incrementally
            //loop through the preferences list and set the views
            for (int i = 0; i <= preferencesAmenities.size() - 1; i++){
                switch (i){
                    case 0:
                        mImage1Pref.setImageResource(preferencesAmenities.get(i).getPrefImageView());
                        mText1Pref.setText(preferencesAmenities.get(i).getPrefTextView());
                        break;
                    case 1:
                        mImage2Pref.setImageResource(preferencesAmenities.get(i).getPrefImageView());
                        mText2Pref.setText(preferencesAmenities.get(i).getPrefTextView());
                        break;
                    case 2:
                        mImage3Pref.setImageResource(preferencesAmenities.get(i).getPrefImageView());
                        mText3Pref.setText(preferencesAmenities.get(i).getPrefTextView());
                        break;
                }
            }

        }else {
            //loop through the preferences list and set the views
            for (int i = 0; i <= preferencesAmenities.size() - 1; i++){
                switch (i){
                    case 0:
                        mImage1Pref.setImageResource(preferencesAmenities.get(i).getPrefImageView());
                        mText1Pref.setText(preferencesAmenities.get(i).getPrefTextView());
                        break;
                    case 1:
                        mImage2Pref.setImageResource(preferencesAmenities.get(i).getPrefImageView());
                        mText2Pref.setText(preferencesAmenities.get(i).getPrefTextView());
                        break;
                    case 2:
                        mImage3Pref.setImageResource(preferencesAmenities.get(i).getPrefImageView());
                        mText3Pref.setText(preferencesAmenities.get(i).getPrefTextView());
                        break;
                    case 3:
                        mImage4Pref.setImageResource(preferencesAmenities.get(i).getPrefImageView());
                        mText4Pref.setText(preferencesAmenities.get(i).getPrefTextView());
                        break;
                    case 4:
                        mImage5Pref.setImageResource(preferencesAmenities.get(i).getPrefImageView());
                        mText5Pref.setText(preferencesAmenities.get(i).getPrefTextView());
                        break;
                    case 5:
                        mImage6Pref.setImageResource(preferencesAmenities.get(i).getPrefImageView());
                        mText6Pref.setText(preferencesAmenities.get(i).getPrefTextView());
                        break;
                }
            }
        }

        //RoomImageOneFragment fragment = (RoomImageOneFragment)getSupportFragmentManager().findFragmentById(R.layout.fragment_room_image_one);

        mDescription.setText(roomObject.getString("roomRoomDescription"));

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.edit_room_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ////edit room
                FragmentManager fm = getSupportFragmentManager();
                EditRoomDialogFragment editRoomDialogFragment = EditRoomDialogFragment.newInstance();
                editRoomDialogFragment.show(fm, "edit");
            }
        });
    }

    private void showLoginDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
        loginDialogFragment.show(fragmentManager, "fragment_alert");
    }

    private void navToLogin() {
        Intent intentLogin = new Intent(this, LoginActivity.class);
        //intentLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intentLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentLogin);
    }

    @Override
    public void passImageUrl() {
        RoomImageOneFragment fragment = (RoomImageOneFragment) mRoomImageAdapter.getRegisteredFragment(0);
        fragment.setImageUrl(roomObject.getParseFile("roomImage1"));
    }

    @Override
    public void setImageUrl() {
        //setting the image in fragment 2
        RoomImageTwoFragment fragment = (RoomImageTwoFragment)mRoomImageAdapter.getRegisteredFragment(1);
        fragment.setImageUrl(roomObject.getParseFile("roomImage2"));
    }

    @Override
    public void setImageThreeUrl() {
        RoomImageThreeFragment fragment = (RoomImageThreeFragment)mRoomImageAdapter.getRegisteredFragment(2);
        fragment.setImageUrl(roomObject.getParseFile("roomImage3"));
    }
}
