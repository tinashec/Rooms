package tinashechinyanga.zw.co.ruumz;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddLocationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddLocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddLocationFragment extends Fragment {

    //add views
    //imageviews
    private EditText mCityTown, mSuburb;

    private RelativeLayout bicLayout, ownEntrancelayout, ownToiletLayout, kitchenLayout, parkingLayout,
            wifiLayout, securityLayout, furnishedLayout, boreholeLayout, prepaidZesaLayout, fittedWardrobeLayout,
            prepaidWaterLayout, smallFamilyPrefLayout, femalePrefLayout, malePrefLayout, soberHabitsPrefLayout, professionalPrefLayout,
            couplePrefLayout;

//    private ImageView mBic, mOwnEntrance, mOwnToilet, mKitchen, mParking, mWifi, mSecure, mFurnished,
//            mBorehole, mPrepaidZesa, mWardrobe, mPrepaidWater, mFam, mFemale, mMale, mSoberHabit,
//            mProfessional, mCouple;

    //labels
    private TextView mBicLabel, mOwnEntranceLabel, mOwnToiletLabel, mKitchenLabel, mParkingLabel,
            mWifiLabel, mSecureLabel, mFurnishedLabel, mBoreholeLabel, mPrepaidZesaLabel,
            mWardrobeLabel, mPrepaidWaterLabel, mFamLabel, mFemaleLabel, mMaleLabel,
            mSoberHabitLabel, mProfessionalLabel, mCoupleLabel;

    private FloatingActionButton mAddRoomStep3;

    //bitmap
    private Bitmap mFinalBitmap;

    private GetLocationAmenitiesPref mGetLocAmenitiesPrefOnNext;
    public interface GetLocationAmenitiesPref{
        void passCity(String city);
        void passSuburb(String suburb);
        void passBIC(boolean bic);
        void passOwnEntrance(boolean ownEntrance);
        void passOwnToilet(boolean ownToilet);
        void passKitchen(boolean kitchen);
        void passParkingAvail(boolean parkingAvail);
        void passWifiAvail(boolean wifiAvail);
        void passSecure(boolean secure);
        void passFurnished(boolean furnished);
        void passBoreholeAvail(boolean boreholeAvail);
        void passPrepaidZesa(boolean prepaidZesa);
        void passFittedWardrobe(boolean fittedWardrobe);
        void passPrepaidWater(boolean prepaidWater);
        void passSmallFamilyPreferred(boolean smallFamily);
        void passFemalePreferred(boolean female);
        void passMalePreferred(boolean male);
        void passSoberHabitsPreferred(boolean soberHabits);
        void passProfessionalPreferred(boolean professional);
        void passCouplePreferred(boolean couple);
    }
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddLocationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddLocationFragment newInstance(String param1, String param2) {
        AddLocationFragment fragment = new AddLocationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AddLocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_location, container, false);

        //attach the views to the fragment
        mCityTown = rootView.findViewById(R.id.add_room_town_edit_text);
        mSuburb = rootView.findViewById(R.id.add_room_suburb_edit_text);

        bicLayout = rootView.findViewById(R.id.room_bic_layoutid);
        ownEntrancelayout = rootView.findViewById(R.id.room_entrance_layoutid);
        ownToiletLayout = rootView.findViewById(R.id.room_toilet_layoutid);
        kitchenLayout = rootView.findViewById(R.id.room_kitchen_layoutid);
        parkingLayout = rootView.findViewById(R.id.room_parking_layoutid);
        wifiLayout = rootView.findViewById(R.id.room_wifi_layoutid);
        securityLayout = rootView.findViewById(R.id.room_security_layoutid);
        furnishedLayout = rootView.findViewById(R.id.room_furnished_layoutid);
        boreholeLayout = rootView.findViewById(R.id.room_borehole_layoutid);
        prepaidZesaLayout = rootView.findViewById(R.id.room_prepaidZesa_layoutid);
        fittedWardrobeLayout = rootView.findViewById(R.id.room_fittedWardrobe_layoutid);
        prepaidWaterLayout = rootView.findViewById(R.id.room_prepaidWater_layoutid);
        smallFamilyPrefLayout = rootView.findViewById(R.id.room_smallFamily_layoutid);
        femalePrefLayout = rootView.findViewById(R.id.room_female_layoutid);
        malePrefLayout = rootView.findViewById(R.id.room_male_layoutid);
        soberHabitsPrefLayout = rootView.findViewById(R.id.room_sober_layoutid);
        professionalPrefLayout = rootView.findViewById(R.id.room_professional_layoutid);
        couplePrefLayout = rootView.findViewById(R.id.room_couple_layoutid);

        //labels
        mBicLabel = rootView.findViewById(R.id.add_room_bic_label);
        mOwnEntranceLabel = rootView.findViewById(R.id.add_room_entrance_label);
        mOwnToiletLabel = rootView.findViewById(R.id.add_room_toilet_label);
        mKitchenLabel = rootView.findViewById(R.id.add_room_kitchen_label);
        mParkingLabel = rootView.findViewById(R.id.add_room_parking_label);
        mWifiLabel = rootView.findViewById(R.id.add_room_wifi_label);
        mSecureLabel = rootView.findViewById(R.id.add_room_secure_label);
        mFurnishedLabel = rootView.findViewById(R.id.add_room_furnished_label);
        mBoreholeLabel = rootView.findViewById(R.id.add_room_borehole_label);
        mPrepaidZesaLabel = rootView.findViewById(R.id.add_room_prezesa_label);
        mWardrobeLabel = rootView.findViewById(R.id.add_room_wardrobe_label);
        mPrepaidWaterLabel = rootView.findViewById(R.id.add_room_prewater_label);
        mFamLabel = rootView.findViewById(R.id.add_room_fam_label);
        mFemaleLabel = rootView.findViewById(R.id.add_room_female_label);
        mMaleLabel = rootView.findViewById(R.id.add_room_male_label);
        mSoberHabitLabel = rootView.findViewById(R.id.add_room_sober_label);
        mProfessionalLabel = rootView.findViewById(R.id.add_room_pro_label);
        mCoupleLabel = rootView.findViewById(R.id.add_room_couple_label);

        bicLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                changeLabelColour(mBicLabel);
            }
        });

        ownEntrancelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                changeLabelColour(mOwnEntranceLabel);
            }
        });
        ownToiletLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                changeLabelColour(mOwnToiletLabel);
            }
        });
        kitchenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                changeLabelColour(mKitchenLabel);
            }
        });
        parkingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                changeLabelColour(mParkingLabel);
            }
        });
        wifiLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                changeLabelColour(mWifiLabel);
            }
        });
        securityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                changeLabelColour(mSecureLabel);
            }
        });
        furnishedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                changeLabelColour(mFurnishedLabel);
            }
        });
        boreholeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                changeLabelColour(mBoreholeLabel);
            }
        });
        prepaidZesaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                changeLabelColour(mPrepaidZesaLabel);
            }
        });
        fittedWardrobeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                changeLabelColour(mWardrobeLabel);
            }
        });
        prepaidWaterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                changeLabelColour(mPrepaidWaterLabel);
            }
        });
        smallFamilyPrefLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                changeLabelColour(mFamLabel);
            }
        });
        femalePrefLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                changeLabelColour(mFemaleLabel);
            }
        });
        malePrefLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                changeLabelColour(mMaleLabel);
            }
        });
        soberHabitsPrefLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                changeLabelColour(mSoberHabitLabel);
            }
        });
        professionalPrefLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                changeLabelColour(mProfessionalLabel);
            }
        });
        couplePrefLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                changeLabelColour(mCoupleLabel);
            }
        });



        /*imageview and label actions
        mBic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                //change label colour and imageview colour
                changeLabelColour(mBicLabel);

            }
        });
        mBicLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLabelColour(mBicLabel);
            }
        });*/
//        mOwnEntrance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideKeyBoard();
//                changeLabelColour(mOwnEntranceLabel);
//            }
//        });
//        mOwnToilet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideKeyBoard();
//                changeLabelColour(mOwnToiletLabel);
//            }
//        });
//        mKitchen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideKeyBoard();
//                changeLabelColour(mKitchenLabel);
//            }
//        });
//        mParking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideKeyBoard();
//                changeLabelColour(mParkingLabel);
//            }
//        });
//        mWifi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideKeyBoard();
//                changeLabelColour(mWifiLabel);
//            }
//        });
//        mSecure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideKeyBoard();
//                changeLabelColour(mSecureLabel);
//            }
//        });
//        mFurnished.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideKeyBoard();
//                changeLabelColour(mFurnishedLabel);
//            }
//        });
//        mBorehole.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideKeyBoard();
//                changeLabelColour(mBoreholeLabel);
//            }
//        });
//        mPrepaidZesa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideKeyBoard();
//                changeLabelColour(mPrepaidZesaLabel);
//            }
//        });
//        mWardrobe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideKeyBoard();
//                changeLabelColour(mWardrobeLabel);
//            }
//        });
//        mPrepaidWater.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideKeyBoard();
//                changeLabelColour(mPrepaidWaterLabel);
//            }
//        });
//        mFam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideKeyBoard();
//                changeLabelColour(mFamLabel);
//            }
//        });
//        mFemale.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideKeyBoard();
//                changeLabelColour(mFemaleLabel);
//            }
//        });
//        mMale.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideKeyBoard();
//                changeLabelColour(mMaleLabel);
//            }
//        });
//        mSoberHabit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideKeyBoard();
//                //call method, change label colour
//                changeLabelColour(mSoberHabitLabel);
//            }
//        });
//        mProfessional.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideKeyBoard();
//                changeLabelColour(mProfessionalLabel);
//            }
//        });
//        mCouple.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideKeyBoard();
//                changeLabelColour(mCoupleLabel);
//            }
//        });


        mAddRoomStep3 = rootView.findViewById(R.id.add_room_step3_fab);

        mAddRoomStep3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddRoomActivity) getActivity()).setCurrentItem(3, true);
                Toast.makeText(getContext().getApplicationContext(), "4 of 4", Toast.LENGTH_LONG).show();
                //pass the data captured to container activity
                //data will be accessed via the interface implemented
                /*
                * for the data captured via colour change
                * 1. check if colour of value is #ff4081
                *   if true, set property value, else set to null and return
                * */

                mGetLocAmenitiesPrefOnNext.passCity(mCityTown.getText().toString());
                mGetLocAmenitiesPrefOnNext.passSuburb(mSuburb.getText().toString());

                if(mBicLabel.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)){
                    //get value, set to true
                    mGetLocAmenitiesPrefOnNext.passBIC(true);
                    Log.i("BIC value:", "Value is true");
                }else{
                    mGetLocAmenitiesPrefOnNext.passBIC(false);
                    Log.i("BIC value:", "Value is false");
                }

                if (mOwnEntranceLabel.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                    mGetLocAmenitiesPrefOnNext.passOwnEntrance(true);
                }else{
                    mGetLocAmenitiesPrefOnNext.passOwnEntrance(false);
                }

                if (mOwnToiletLabel.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                    mGetLocAmenitiesPrefOnNext.passOwnToilet(true);
                }else{
                    mGetLocAmenitiesPrefOnNext.passOwnToilet(false);
                }

                if (mKitchenLabel.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                    mGetLocAmenitiesPrefOnNext.passKitchen(true);
                }else{
                    mGetLocAmenitiesPrefOnNext.passKitchen(false);
                }

                if (mParkingLabel.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                    mGetLocAmenitiesPrefOnNext.passParkingAvail(true);
                }else{
                    mGetLocAmenitiesPrefOnNext.passParkingAvail(false);
                }

                if (mWifiLabel.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                    mGetLocAmenitiesPrefOnNext.passWifiAvail(true);
                }else{
                    mGetLocAmenitiesPrefOnNext.passWifiAvail(false);
                }

                if (mSecureLabel.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                    mGetLocAmenitiesPrefOnNext.passSecure(true);
                }else{
                    mGetLocAmenitiesPrefOnNext.passSecure(false);
                }

                if (mFurnishedLabel.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                    mGetLocAmenitiesPrefOnNext.passFurnished(true);
                }else{
                    mGetLocAmenitiesPrefOnNext.passFurnished(false);
                }

                if (mBoreholeLabel.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                    mGetLocAmenitiesPrefOnNext.passBoreholeAvail(true);
                }else{
                    mGetLocAmenitiesPrefOnNext.passBoreholeAvail(false);
                }

                if (mPrepaidZesaLabel.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                    mGetLocAmenitiesPrefOnNext.passPrepaidZesa(true);
                }else{
                    mGetLocAmenitiesPrefOnNext.passPrepaidZesa(false);
                }

                if (mWardrobeLabel.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                    mGetLocAmenitiesPrefOnNext.passFittedWardrobe(true);
                }else{
                    mGetLocAmenitiesPrefOnNext.passFittedWardrobe(false);
                }

                if (mPrepaidWaterLabel.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                    mGetLocAmenitiesPrefOnNext.passPrepaidWater(true);
                }else{
                    mGetLocAmenitiesPrefOnNext.passPrepaidWater(false);
                }

                //preferences
                if (mFamLabel.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                    mGetLocAmenitiesPrefOnNext.passSmallFamilyPreferred(true);
                }else{
                    mGetLocAmenitiesPrefOnNext.passSmallFamilyPreferred(false);
                }

                if (mFemaleLabel.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                    mGetLocAmenitiesPrefOnNext.passFemalePreferred(true);
                }else{
                    mGetLocAmenitiesPrefOnNext.passFemalePreferred(false);
                }

                if (mMaleLabel.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                    mGetLocAmenitiesPrefOnNext.passMalePreferred(true);
                }else{
                    mGetLocAmenitiesPrefOnNext.passMalePreferred(false);
                }

                if (mSoberHabitLabel.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                    mGetLocAmenitiesPrefOnNext.passSoberHabitsPreferred(true);
                }else{
                    mGetLocAmenitiesPrefOnNext.passSoberHabitsPreferred(false);
                }

                if (mProfessionalLabel.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                    mGetLocAmenitiesPrefOnNext.passProfessionalPreferred(true);
                }else{
                    mGetLocAmenitiesPrefOnNext.passProfessionalPreferred(false);
                }

                if (mCoupleLabel.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.colorAccent)) {
                    mGetLocAmenitiesPrefOnNext.passCouplePreferred(true);
                }else{
                    mGetLocAmenitiesPrefOnNext.passCouplePreferred(false);
                }
            }
        });


        return rootView;
    }

    private void hideKeyBoard() {
        //hide the keyboard
        InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mBicLabel.getWindowToken(), 0);
    }

    private void changeLabelColour(TextView mLabel) {
        TextView label = mLabel;
        if(label.getCurrentTextColor() != Color.parseColor("#ff404d") ){
            label.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorAccent)));
        }else {
            label.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorDull)));
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        //ensure container activity implements the interface
        try{
            mGetLocAmenitiesPrefOnNext = (GetLocationAmenitiesPref)getActivity();
        }catch (ClassCastException cce){
            throw new ClassCastException(getActivity().toString() + "must implement GetLocationAmentiesPref");
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
