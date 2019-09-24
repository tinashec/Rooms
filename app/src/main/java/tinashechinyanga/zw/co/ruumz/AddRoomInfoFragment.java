package tinashechinyanga.zw.co.ruumz;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddRoomInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddRoomInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRoomInfoFragment extends Fragment {
    //interface
    private GetRoomInfo mNextClicked;

    public interface GetRoomInfo {
        //methods to pass back the attributes captured
        void getRoomNumBedrooms(int numBeds);
        void getRoomNumBaths(int numBaths);
        void getRoomNumToilets(int numToilets);
        void getRoomPropertyType(String propertyType);
        void getRoomSharingYesOrNo(String sharingYesOrNo);
    }

    //views
    protected ImageView mAddBeds, mSubtractBeds, mAddBaths, mSubtractBaths, mAddToilets, mSubtractToilets;
    protected TextView mNumBeds, mNumBaths, mNumToilets;
    protected RadioButton mFlatButton, mCottageButton, mFullhouseButton, mSharingYesButton, mSharingNoButton;

    protected int bedsCtr, bathsCtr, toiletsCtr;

    protected String mPropertyType = "Not Set";
    protected String mSharingProperty = "Not Set";

    public AddRoomInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_add_room_info, container, false);

        mNumBeds = rootView.findViewById(R.id.add_room_num_of_rooms_label);
        mAddBeds = rootView.findViewById(R.id.num_beds_plus);
        mSubtractBeds = rootView.findViewById(R.id.num_beds_minus);
        mNumBaths = rootView.findViewById(R.id.add_room_num_baths_label);
        mAddBaths = rootView.findViewById(R.id.plus_num_baths);
        mSubtractBaths = rootView.findViewById(R.id.minus_num_baths);
        mNumToilets = rootView.findViewById(R.id.add_room_num_toilets_label);
        mAddToilets = rootView.findViewById(R.id.plus_num_toilets);
        mSubtractToilets = rootView.findViewById(R.id.minus_num_toilets);
        //radio buttons
        mFlatButton = rootView.findViewById(R.id.radio_flat_property);
        mCottageButton = rootView.findViewById(R.id.radio_cottage_property);
        mFullhouseButton = rootView.findViewById(R.id.radio_fullhouse_property);
        mSharingYesButton = rootView.findViewById(R.id.radio_sharing_yes);
        mSharingNoButton = rootView.findViewById(R.id.radio_sharing_no);

        //adding the back button


        //logic that does the calculations
        //algorithm for adding
        /*
        * 1. on click plus; get current value
        * 2. increment value by one
        * 3. set to label value
        * 4. change label value
        * */

        mAddBeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bedsCtr = Integer.parseInt(mNumBeds.getText().toString());
                bedsCtr++;
                mNumBeds.setText(Integer.toString(bedsCtr));
            }
        });

        mSubtractBeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bedsCtr>0) {
                    bedsCtr--;
                    mNumBeds.setText(Integer.toString(bedsCtr));
                }
            }
        });

        mAddBaths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bathsCtr = Integer.parseInt(mNumBaths.getText().toString());
                bathsCtr++;
                mNumBaths.setText(Integer.toString(bathsCtr));
            }
        });

        mSubtractBaths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bathsCtr>0){
                    bathsCtr--;
                    mNumBaths.setText(Integer.toString(bathsCtr));
                }
            }
        });

        mAddToilets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toiletsCtr = Integer.parseInt(mNumToilets.getText().toString());
                toiletsCtr++;
                mNumToilets.setText(Integer.toString(toiletsCtr));
            }
        });

        mSubtractToilets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toiletsCtr>0){
                    toiletsCtr--;
                    mNumToilets.setText(Integer.toString(toiletsCtr));
                }
            }
        });

        //radio button
        mFlatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });

        mCottageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });

        mFullhouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });

        mSharingYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });

        mSharingNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });

        //done button
        /*
        * indicates user has entered sufficient information and wishes to proceed to the next phase
        * onClick/Done: capture all set information and set to room properties/attributes
        * */
        FloatingActionButton next = rootView.findViewById(R.id.add_room_step1_fab);
        //if beds == 0, onclick disabled
        //else go to next fragment
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddRoomActivity)getActivity()).setCurrentItem(1, true);
                Toast.makeText(getActivity().getApplicationContext(), "2 of 4", Toast.LENGTH_LONG).show();
                //call the interface method and pass it the data captured
                mNextClicked.getRoomNumBedrooms(bedsCtr);
                mNextClicked.getRoomNumBaths(bathsCtr);
                mNextClicked.getRoomNumToilets(toiletsCtr);
                mNextClicked.getRoomPropertyType(mPropertyType);
                mNextClicked.getRoomSharingYesOrNo(mSharingProperty);
            }
        });

        return rootView;
    }

    //the checkboxes
    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton)view).isChecked();

        //check the correct box selected
        switch (view.getId()){
            case R.id.radio_flat_property:
                if(checked){
                    //set a string to flat
                    mPropertyType = "Flat";
                }
                break;
            case R.id.radio_cottage_property:
                if(checked){
                    //set a string to cottage
                    mPropertyType = "Cottage";
                }
                break;
            case R.id.radio_fullhouse_property:
                if(checked){
                    //set a string to full house
                    mPropertyType = "Full House";
                }
                break;
            //sharing checkboxes
            case R.id.radio_sharing_yes:
                if(checked){
                    //set sharing to yes
                    mSharingProperty = "Shared";
                }
                break;
            case R.id.radio_sharing_no:
                if(checked){
                    mSharingProperty = "Not Shared";
                }
                break;
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try{
            //make sure the container activity has implemented interface,
            //else throw an exception
            mNextClicked = (GetRoomInfo)getActivity();
        }catch(ClassCastException cce){
            throw new ClassCastException(getActivity().toString() + "must implement GetRoomInfo");
        }

    }

}





















