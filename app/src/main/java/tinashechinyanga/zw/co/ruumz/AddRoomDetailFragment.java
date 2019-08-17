package tinashechinyanga.zw.co.ruumz;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddRoomDetailFragment extends Fragment {

    //views
    private EditText mMonthlyRent;
    private EditText mDeposit;
    private RadioButton mRentInclYesOfBills;
    private RadioButton mRentInclNoOfBills;
    private TextView mMoveInDate;
    private EditText mRoomDescription;
    private FloatingActionButton mStep2Fab;
    private String date;

    protected String mRent;
    private String mRentInclOrExclOfBills;
    private int mRentAmount;
    private int mDepositAmount;

    /*the interface to be implemented by the container activity
    * this interface is the only way to communicate with other fragments, by routing back
    * to the parent activity
    * */
    GetRoomDetail mGetRoomDetailOnNext;
    public interface GetRoomDetail{
        public void passMonthlyRent(int rentAmount);
        public void passDeposit(int deposit);
        public void passRentInclOrExclOfBills(String rentInclOrExclOfBills);
        public void passMoveInDate(String moveInDate);
        public void passRoomDescription(String roomDescription);
    }

    public AddRoomDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_room_detail, container, false);

        //attach the views to the fragment
        mMonthlyRent = (EditText)rootView.findViewById(R.id.add_room_detail_monthly_rent_editText);
        mDeposit = (EditText)rootView.findViewById(R.id.add_room_detail_deposit_editText4);
        mRentInclYesOfBills = (RadioButton)rootView.findViewById(R.id.radio_inclusive_yes);
        mRentInclNoOfBills = (RadioButton)rootView.findViewById(R.id.radio_inclusive_no);
        mMoveInDate = (TextView)rootView.findViewById(R.id.add_room_detail_movein_date_textview);
        mRoomDescription = (EditText)rootView.findViewById(R.id.add_room_detail_description_editText);
        mStep2Fab = (FloatingActionButton)rootView.findViewById(R.id.add_room_step2_fab);

        //check if rent and movein date are set
        //if not, disable click on fab and dull out fab, else enable fab
        /*if(mRent != ""){
            //next button disabled by default
            mStep2Fab.setEnabled(false);
            mStep2Fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorDull)));
        }

        mMonthlyRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRent = mMonthlyRent.getText().toString();
                if(Integer.parseInt(mRent) > 0){
                    mStep2Fab.setEnabled(true);
                }

            }
        });*/


        //add logic to the views
        mRentInclYesOfBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });

        mRentInclNoOfBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });

        mMoveInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getFragmentManager(), "datePicker");
            }
        });

        //fab
        mStep2Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to step 3
                ((AddRoomActivity) getActivity()).setCurrentItem(2, true);
                Toast.makeText(getActivity().getApplicationContext(), "3 of 4", Toast.LENGTH_LONG)
                        .show();
                //pass the data capture via the interface methods to the container activity
                /*
                * this is Google's best practice for communicating with other fragments
                * since the data captured will be used in AddPhotos, the last step in gathering
                * room data to create the room object
                * */
                //check whether any of the elements are null
                if((mMonthlyRent.getText().toString()).equals("")){
                    mRentAmount = 0;
                }else{
                    mRentAmount = Integer.parseInt(mMonthlyRent.getText().toString());
                }

                if((mDeposit.getText().toString()).equals("")) {
                    mDepositAmount = 0;
                }else{
                    mDepositAmount = Integer.parseInt(mDeposit.getText().toString());
                }

                mGetRoomDetailOnNext.passMonthlyRent(mRentAmount);
                mGetRoomDetailOnNext.passDeposit(mDepositAmount);
                mGetRoomDetailOnNext.passRentInclOrExclOfBills(mRentInclOrExclOfBills);
                mGetRoomDetailOnNext.passMoveInDate(mMoveInDate.getText().toString());
                mGetRoomDetailOnNext.passRoomDescription(mRoomDescription.getText().toString());
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            //make sure interface is implemented in container activity
            mGetRoomDetailOnNext = (GetRoomDetail)getActivity();
        }catch (ClassCastException cce){
            throw new ClassCastException(getActivity().toString() + " container must implement GetRoomDetail");
        }
    }

    public void setMoveInDate(String date){
        this.date = date;
        Log.d("Final date", this.date);
        mMoveInDate.setText(this.date);
    }

    //radio buttons
    public void onRadioButtonClicked(View v){
        //is the button checked?
        boolean checked = ((RadioButton)v).isChecked();
        switch (v.getId()){
            case R.id.radio_inclusive_yes:
                if(checked){
                    mRentInclOrExclOfBills = "incl. bills";
                }
                break;
            case R.id.radio_inclusive_no:
                if(checked){
                    mRentInclOrExclOfBills = "excl. bills";
                }
                break;
        }
    }

}










