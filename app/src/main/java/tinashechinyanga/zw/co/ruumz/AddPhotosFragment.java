package tinashechinyanga.zw.co.ruumz;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.apache.commons.io.FilenameUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import id.zelory.compressor.Compressor;
import id.zelory.compressor.FileUtil;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddPhotosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddPhotosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPhotosFragment extends Fragment {
    //other views
    protected ProgressDialog progressDialog;

    //picker views to be hidden onClick
    private ImageView mRoomImagePicker1;
    private ImageView mRoomImagePicker2;
    private ImageView mRoomImagePicker3;
    private ImageView mRoomImagePicker4;
    private ImageView mRoomImagePicker5;
    private ImageView mRoomImagePicker6;
    private TextView mAddPhotosLabel;

    //views
    private ImageView mRoomImage1;
    private ImageView mRoomImage2;
    private ImageView mRoomImage3;
    private ImageView mRoomImage4;
    private ImageView mRoomImage5;
    private ImageView mRoomImage6;
    private EditText mRoomOwnerContactNumber;
    private FloatingActionButton mAddRoomStep4;

    private Uri mMediaUri;
    private String mImageUri;
    private String mExtension;
    private String mContactNumber;

    public final static int TAKE_PIC_CHOICE_CODE = 0;
    public final static int CHOOSE_PIC_CHOICE_CODE = 1;
    public static final int MEDIA_TYPE_IMG = 3;

    /*room attributes/properties
    * the rooms will have a number of properties from the four fragments
    * capturing the room details. other properties will be null/should be set to null if blank
    * 1. AddRoomInfoFragment
    *    - numOfBeds
    *    - numOfBaths
    *    - numOfToilets
    *    - propertyType
    *    - propertySharedOrNot
    */
    private int mNumBeds;
    private int mNumBaths;
    private int mNumToilets;
    private String mPropertyType;
    private String mPropertySharedOrNot;

    //methods to set room attributes/properties
    public void setNumBeds(int numBeds){
        this.mNumBeds = numBeds;
    }
    public void setNumBaths(int numBaths){
        this.mNumBaths = numBaths;
    }

    public void setNumToilets(int mNumToilets) {
        this.mNumToilets = mNumToilets;
    }

    public void setPropertyType(String mPropertyType) {
        this.mPropertyType = mPropertyType;
    }

    public void setPropertySharedOrNot(String mPropertySharedOrNot) {
        this.mPropertySharedOrNot = mPropertySharedOrNot;
    }

    /*
    * 2. AddRoomDetailFragment
    *    - monthlyRent
    *    - deposit
    *    - rentIncOrNotOfBills
    *    - moveInDate
    *    - description
    * */
    private int mMonthlyRent;
    private int mDeposit;
    private String mRentInclOrNotOfBills;
    private String mMoveInDate;
    private String mRoomDescription;

    //setter methods
    public void setMonthlyRent(int mMonthlyRent) {
        this.mMonthlyRent = mMonthlyRent;
    }

    public void setDepost(int mDepost) {
        this.mDeposit = mDepost;
    }

    public void setRentInclOrNotOfBills(String mRentInclOrNotOfBills) {
        this.mRentInclOrNotOfBills = mRentInclOrNotOfBills;
    }

    public void setMoveInDate(String mMoveInDate) {
        this.mMoveInDate = mMoveInDate;
    }

    public void setRoomDescription(String mRoomDescription) {
        this.mRoomDescription = mRoomDescription;
    }

    /**
    * 3. AddLocationFragment
    *    - city
    *    - suburb
    *    - bic             - entrance          - ownToilet         - kitchen
    *    - parking         - wifi              - secure            - furnished
    *    - borehole        - prepaidZesa       - fittedWardrobe    - prepaidWater
    *    - smallFamily     - female            - male
    *    - soberHabits     - professional      - couple
    */
    private String mCity, mSuburb;
    private boolean mBIC, mOwnEntrance, mOwnToilet, mKitchen, mParking, mWifi, mSecure, mFurnished,
            mBorehole, mPrepaidZesa, mFittedWardrobe, mPrepaidWater, mSmallFamily, mFemale,
            mMale, mSoberHabits, mProfessional, mCouple;


    public void setCity(String mCity) {
        this.mCity = mCity;
    }

    public void setSuburb(String mSuburb) {
        this.mSuburb = mSuburb;
    }

    public void setBIC(boolean mBIC) {
        this.mBIC = mBIC;
    }

    public void setOwnEntrance(boolean mOwnEntrance) {
        this.mOwnEntrance = mOwnEntrance;
    }

    public void setOwnToilet(boolean mOwnToilet) {
        this.mOwnToilet = mOwnToilet;
    }

    public void setKitchen(boolean mKitchen) {
        this.mKitchen = mKitchen;
    }

    public void setParking(boolean mParking) {
        this.mParking = mParking;
    }

    public void setWifi(boolean mWifi) {
        this.mWifi = mWifi;
    }

    public void setSecure(boolean mSecure) {
        this.mSecure = mSecure;
    }

    public void setFurnished(boolean mFurnished) {
        this.mFurnished = mFurnished;
    }

    public void setBorehole(boolean mBorehole) {
        this.mBorehole = mBorehole;
    }

    public void setPrepaidZesa(boolean mPrepaidZesa) {
        this.mPrepaidZesa = mPrepaidZesa;
    }

    public void setFittedWardrobe(boolean mFittedWardrobe) {
        this.mFittedWardrobe = mFittedWardrobe;
    }

    public void setPrepaidWater(boolean mPrepaidWater) {
        this.mPrepaidWater = mPrepaidWater;
    }

    public void setSmallFamily(boolean mSmallFamily) {
        this.mSmallFamily = mSmallFamily;
    }

    public void setFemale(boolean mFemale) {
        this.mFemale = mFemale;
    }

    public void setMale(boolean mMale) {
        this.mMale = mMale;
    }

    public void setSoberHabits(boolean mSoberHabits) {
        this.mSoberHabits = mSoberHabits;
    }

    public void setProfessional(boolean mProfessional) {
        this.mProfessional = mProfessional;
    }

    public void setCouple(boolean mCouple) {
        this.mCouple = mCouple;
    }

    /**
    * 4. AddPhotosFragment
     *  already in the fragment, thus can directly manipulate the values set herein
    */

    private OnFragmentInteractionListener mListener;
    private DialogInterface.OnClickListener mDialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int choice) {
            switch (choice){
                case 0:
                    //taking a pic
                    Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMG);
                    if(mMediaUri == null){
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                    }else {
                        takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, MEDIA_TYPE_IMG);
                        startActivityForResult(takePicIntent, TAKE_PIC_CHOICE_CODE);
                    }
                    break;
                case 1:
                    //choosing a pic
                    Intent choosePicIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    choosePicIntent.setType("image/*");
                    startActivityForResult(choosePicIntent, CHOOSE_PIC_CHOICE_CODE);
                    break;
            }
        }

        private Uri getOutputMediaFileUri(int mediaTypeImg) {
            if (isExternalStorageAvailable()) {
                //get the external storage
                String appName = getActivity().getString(R.string.app_name);
                File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), appName);

                //create subdirectory
                if(!mediaStorageDir.exists()){
                    if(!mediaStorageDir.mkdirs()){
                        //failed to make dir; create a dialog to show the error
                        return null;
                    }
                }
                //create file name
                //then create the file
                File mediaFile;
                Date now = new Date();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);

                String path = mediaStorageDir.getPath() + File.pathSeparator;

                mediaFile = new File((path + "IMG" + timeStamp + ".jpg"));
                Log.d("Tag", "File: " + Uri.fromFile(mediaFile));
                return Uri.fromFile(mediaFile);
            }else {
                return null;
            }
        }

        private boolean isExternalStorageAvailable() {
            String state = Environment.getExternalStorageState();
            return state.equals(Environment.MEDIA_MOUNTED);
        }
    };

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPhotosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPhotosFragment newInstance(String param1, String param2) {
        AddPhotosFragment fragment = new AddPhotosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AddPhotosFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_add_photos, container, false);

        //views to hold the descriptive icons
        mRoomImagePicker1 = (ImageView)rootView.findViewById(R.id.roomImagePicker1);
        mRoomImagePicker2 = (ImageView)rootView.findViewById(R.id.roomImagePicker2);
        mRoomImagePicker3 = (ImageView)rootView.findViewById(R.id.roomImagePicker3);
        mRoomImagePicker4 = (ImageView)rootView.findViewById(R.id.roomImagePicker4);
        mRoomImagePicker5 = (ImageView)rootView.findViewById(R.id.roomImagePicker5);
        mRoomImagePicker6 = (ImageView)rootView.findViewById(R.id.roomImagePicker6);
        mAddPhotosLabel = (TextView)rootView.findViewById(R.id.add_rooms_image_label);

        //attach the views to house the selected images
        mRoomImage1 = (ImageView)rootView.findViewById(R.id.roomImage1);
        mRoomImage2 = (ImageView)rootView.findViewById(R.id.roomImage2);
        mRoomImage3 = (ImageView)rootView.findViewById(R.id.roomImage3);
        mRoomImage4 = (ImageView)rootView.findViewById(R.id.roomImage4);
        mRoomImage5 = (ImageView)rootView.findViewById(R.id.roomImage5);
        mRoomImage6 = (ImageView)rootView.findViewById(R.id.roomImage6);
        mRoomOwnerContactNumber = (EditText)rootView.findViewById(R.id.room_owner_contact);
        mAddRoomStep4 = (FloatingActionButton)rootView.findViewById(R.id.add_room_step4_fab);

        //set onclick listener
        /*
        * algorithm
        * 1. click imageview and show dialog
        * 2. pass imageview id
        * 3. check if its choose pic or take pic
        * 4. start intent with imageview id
        * 5. return pic
        * 6. check intent extra for imageview id
        * 7. assign relevant imageview with returned id
        * */
        mRoomImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clear background and image src so as to check whether
                //imageview drawable is set and set the selected image in
                //onActivityResult

                //call the dialog listener that calls camera/gallery activity
                showPictureChoiceDialog();
            }
        });

        mRoomImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call the dialog listener that calls camera/gallery activity
                showPictureChoiceDialog();
            }
        });

        mRoomImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call the dialog listener that calls camera/gallery activity
                showPictureChoiceDialog();
            }
        });

        mRoomImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureChoiceDialog();

            }
        });

        mRoomImage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRoomImage5.setImageDrawable(null);
                //call the dialog listener that calls camera/gallery activity
                showPictureChoiceDialog();
            }
        });

        mRoomImage6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call the dialog listener that calls camera/gallery activity
                showPictureChoiceDialog();
            }
        });

        mAddRoomStep4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((AddRoomActivity) getActivity()).setCurrentItem(3, true);
                //get all room data and add to room object
                /*
                * 1. get data from AddRoomInfoFragment
                * */
                //1.
                mContactNumber = "0" + mRoomOwnerContactNumber.getText().toString();
                if(ParseUser.getCurrentUser() == null){
                    //launch login activity
                    showLoginDialog();

                }else {

                    Toast.makeText(getContext(), Integer.toString(mNumBeds) +
                            " " + Integer.toString(mNumBaths) + " " +
                            mPropertyType + " " + mPropertySharedOrNot + " " +
                            mMonthlyRent + " " + mDeposit + " " + mMoveInDate + " " +
                            mRentInclOrNotOfBills + " " + mCity + " " + mSuburb +
                            " " + mBIC + " " + mOwnEntrance + " " + mOwnToilet + " "
                            + mContactNumber, Toast.LENGTH_LONG).show();

                    ParseObject room = createRoom();
                    new UploadRoomAsync().execute(room);
                }
            }

            //add the room to the backend
            /*
            * algorithm
            * 1. get all the room attributes (look at including a preview of the room itself)
            * 2. add them to the room object
            * 3. create the room object in the background off the main thread
            * 4. show progress dialog
            * 5. update user onFinish whether successful or not
            * 6. close add room activity
            * 7. continue to the new room listing for preview
            * */
            //
        });

        return rootView;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(progressDialog != null  && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    private void showPictureChoiceDialog() {
        //call the dialog listener that calls camera/gallery activity
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(R.array.pic_choices, mDialogListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == getActivity().RESULT_OK){
            if(requestCode == CHOOSE_PIC_CHOICE_CODE || requestCode == TAKE_PIC_CHOICE_CODE){
                if(data == null){
                    //error
                }else {
                    mMediaUri = data.getData();
                    mImageUri = mMediaUri.toString();
                    mExtension = FilenameUtils.getExtension(mMediaUri.toString());
                    Log.d("Ext. ", mExtension);
                }
                //check imageview clicked and set appropriate image
                int i = 0;

                //check if imageview 1 has imag

                /*NB: imageviews background and src must be == null
                * 1. check if mRoomImage1 has an image or not. If has no image
                *           set the image picked
                * 2. repeat 1. if has image ignore else
                *           check if mRoomImage2 has an image or not
                 *           set the current image
                * */

                if(mRoomImage1.getDrawable() == null) {//true
                    setCompressedImage(mRoomImage1, mRoomImagePicker1, mMediaUri);
                }
                else if (mRoomImage1.getDrawable() != null && mRoomImage2.getDrawable() == null){//true && true
                    setCompressedImage(mRoomImage2, mRoomImagePicker2, mMediaUri);
                }
                else if(mRoomImage1.getDrawable() != null && mRoomImage2.getDrawable() != null
                        && mRoomImage3.getDrawable() == null){

                    setCompressedImage(mRoomImage3, mRoomImagePicker3, mMediaUri);
                }
                else if(mRoomImage1.getDrawable() != null && mRoomImage2.getDrawable() != null
                        && mRoomImage3.getDrawable() != null && mRoomImage4.getDrawable() == null){

                    setCompressedImage(mRoomImage4, mRoomImagePicker4, mMediaUri);

                }else if(mRoomImage1.getDrawable() != null && mRoomImage2.getDrawable() != null
                        && mRoomImage3.getDrawable() != null && mRoomImage4.getDrawable() != null && mRoomImage5.getDrawable() == null) {

                    setCompressedImage(mRoomImage5, mRoomImagePicker5, mMediaUri);

                }else if(mRoomImage1.getDrawable() != null && mRoomImage2.getDrawable() != null
                        && mRoomImage3.getDrawable() != null && mRoomImage4.getDrawable() != null && mRoomImage5.getDrawable() != null
                        && mRoomImage6.getDrawable() == null){

                    setCompressedImage(mRoomImage1, mRoomImagePicker1, mMediaUri);

                }
                else{
                    //end
                }
            }else {
                //add to gallery
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(mMediaUri);
                getActivity().sendBroadcast(mediaScanIntent);
            }
        }
        else if(resultCode != getActivity().RESULT_CANCELED){
            //error: display error message
        }
    }

    private void setCompressedImage(ImageView roomImage, ImageView roomImagePicker, Uri mediaUri) {
        //true
        //hide the descritpive views
        roomImagePicker.setVisibility(View.GONE);
        //Bitmap img = BitmapFactory.decodeFile(String.valueOf(mMediaUri));
        /*
         *
         * 1. get the String from the imageUri and return the bitmap
         * 2. decode the sampled bitmap
         * 3. calculate the insample size
         * */

        //compress and set returned image to imageview
        File compressedImage = compressedImage(getActivity().getApplicationContext(), mediaUri);
        //set compressed image to imageview
        roomImage.setImageBitmap(BitmapFactory.decodeFile(compressedImage.getAbsolutePath()));
        //set the selected image and scale to fit imageView
        roomImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //save the image in the background

    }

    //async task
    /*AsyncTask<params, progress, result>
    * params: type passed to execute()
    * progress: the type used within the task to track progress
    * result: what is returned by doInBackground()
    * */

    private class UploadRoomAsync extends AsyncTask<ParseObject, Integer, Void>{

        @Override
        protected Void doInBackground(ParseObject... params) {
            ParseObject p = params[0];
            try {
                p.save();
//                Toast.makeText(getContext(), "Added", Toast.LENGTH_LONG).show();

            } catch (ParseException e) {
                e.printStackTrace();
//                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //initialise and show the progress bar
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Uploading Room...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        //once done
        @Override
        protected void onPostExecute(Void v){
            super.onPostExecute(v);
            //check if progress is running
            if(progressDialog != null && progressDialog.isShowing()){
                progressDialog.dismiss();

                Toast.makeText(getActivity().getApplicationContext(), "Room Added", Toast.LENGTH_LONG).show();
                //end the activity
                getActivity().finish();
            }
        }
    }

    //create the room parseobject
    private ParseObject createRoom() {
        //create the room object
        ParseObject newRoom = ParseObject.create("Room");

        //check if null
        if(mPropertyType == null){
            mPropertyType = "Not Set";
        }
        if(mPropertySharedOrNot == null){
            mPropertySharedOrNot = "Not Set";
        }
        if(mRentInclOrNotOfBills == null){
            mRentInclOrNotOfBills = "Not Set";
        }else {
            //add the attributes
            //1.
            newRoom.put("roomBedrooms", mNumBeds);
            newRoom.put("roomBathrooms", mNumBaths);
            newRoom.put("roomToilets", mNumToilets);
            newRoom.put("roomPropertyType", mPropertyType);
            newRoom.put("roomSharing", mPropertySharedOrNot);
            //2.
            newRoom.put("roomMonthlyRent", mMonthlyRent);
            newRoom.put("roomDeposit", mDeposit);
            newRoom.put("roomRentInclusiveOfBills", mRentInclOrNotOfBills);
            newRoom.put("roomMoveInDate", mMoveInDate);
            newRoom.put("roomRoomDescription", mRoomDescription);
            //3.
            newRoom.put("roomCity", mCity);
            newRoom.put("roomSuburb", mSuburb);
            newRoom.put("roomBICAvailable", mBIC);
            newRoom.put("roomOwnEntrance", mOwnEntrance);
            newRoom.put("roomOwnToilet", mOwnToilet);
            newRoom.put("roomKitchen", mKitchen);
            newRoom.put("roomParkingAvailable", mParking);
            newRoom.put("roomWifiAvailable", mWifi);
            newRoom.put("roomSecureLocation", mSecure);
            newRoom.put("roomFurnished", mFurnished);
            newRoom.put("roomBoreholeAvailable", mBorehole);
            newRoom.put("roomPrepaidZESA", mPrepaidZesa);
            newRoom.put("roomFittedWardrobe", mFittedWardrobe);
            newRoom.put("roomPrepaidWater", mPrepaidWater);
            //preferences
            newRoom.put("roomSmallFamilyPreferred", mSmallFamily);
            newRoom.put("roomFemalePreferred", mFemale);
            newRoom.put("roomMalePreferred", mMale);
            newRoom.put("roomSoberHabitsPreferred", mSoberHabits);
            newRoom.put("roomProfessionalPreferred", mProfessional);
            newRoom.put("roomYoungCouplePreferred", mCouple);
            //4. images
            //add the images
            //check whether the image is there in the imageview and create the parsefile
            ParseFile imageOne = saveParseFile(mRoomImage1, 1);
            ParseFile imageTwo = saveParseFile(mRoomImage2, 2);
            ParseFile imageThree = saveParseFile(mRoomImage3, 3);
            ParseFile imageFour = saveParseFile(mRoomImage4, 4);
            ParseFile imageFive = saveParseFile(mRoomImage5, 5);
            ParseFile imageSix = saveParseFile(mRoomImage6, 6);

            //associate file with the room image
            //check if null
            if(imageOne != null) {
                newRoom.put("roomImage1", imageOne);
            }
            if(imageTwo != null) {
                newRoom.put("roomImage2", imageTwo);
            }
            if(imageThree != null) {
                newRoom.put("roomImage3", imageThree);
            }

            if(imageFour != null){
                newRoom.put("roomImage4", imageFour);
            }
            if(imageFive != null){
                newRoom.put("roomImage5", imageFive);
            }
            if(imageSix != null){
                newRoom.put("roomImage6", imageSix);
            }

            //contact number
            newRoom.put("roomOwnerContactNum", mContactNumber);
            newRoom.put("roomOwner", ParseUser.getCurrentUser().getObjectId());
        }

        return newRoom;
    }

    @NonNull
    private ParseFile saveParseFile(ImageView mRoomImage, int i) {
        ParseFile imageFile = null;
        //check if imageview has bitmap assigned to it
        if (mRoomImage.getDrawable() instanceof BitmapDrawable) {

            Bitmap bitmap;//get the image and assign to bitmap
            bitmap = ((BitmapDrawable) mRoomImage.getDrawable()).getBitmap();

            //change to byte
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            //compress image
            if(mExtension.equals("jpg") || mExtension.equals("jpeg")){
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outputStream);
            }else {
                bitmap.compress(Bitmap.CompressFormat.PNG, 60, outputStream);
            }

            byte[] imageArray = outputStream.toByteArray();
            Log.d("Image_Size", "Size" + imageArray.length);

            //create the parse file
            imageFile = new ParseFile("Image" + i + ".png", imageArray);

            //save the file in background
            imageFile.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null) {
                        //unlock uploading rest of room
                        //success

                    }else{
                        //error

                    }
                }
            });
        }

        return imageFile;
    }

    private void showLoginDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
        loginDialogFragment.show(fragmentManager, "fragment_alert");
    }

    public String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    private File compressedImage(Context context, Uri uri){
        File actualImage = null;
        File compressedImage = null;

        try {
            actualImage = FileUtil.from(context, uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Actual_Image_Size", "Size: " + getReadableFileSize(actualImage.length()));
        //compress the actual image
        compressedImage = Compressor.getDefault(getActivity().getApplicationContext()).compressToFile(actualImage);
        Log.d("Compressed_Image_Size", "Size: " + getReadableFileSize(compressedImage.length()));

        return compressedImage;
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
        public void onFragmentInteraction(Uri uri);
    }

}
