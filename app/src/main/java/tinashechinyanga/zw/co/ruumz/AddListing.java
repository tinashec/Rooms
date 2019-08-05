package tinashechinyanga.zw.co.ruumz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.apache.commons.io.FilenameUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddListing extends AppCompatActivity {
    //member variables
    private ImageView mRoomImage;
    private TextView mAddImage;
    private EditText mRoomPrice;
    private EditText mRoomLocation;
    private String mPriceIncOrExcl;
    private EditText mRoomDescription;
    private EditText mNumberOfBedrooms;
    private EditText mMoveinDate;
    private EditText mNumberOfBaths;
    private EditText mNumToilets;
    private EditText mZesaAvailability;
    private EditText mZinwaAvailability;
    private TextView mPropertyType;
    private EditText mContactNumber;
    private Button mPostListing;

    private Uri mMediaUri;
    private String mExtension;

    public final static int TAKE_PIC_CHOICE_CODE = 0;
    public final static int CHOOSE_PIC_CHOICE_CODE = 1;
    public static final int MEDIA_TYPE_IMG = 3;

    private DialogInterface.OnClickListener mDialogListener = new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialog, int choice) {
            switch (choice){
                case 0: //take pic
                    Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMG);
                    if(mMediaUri == null){
                        Toast.makeText(AddListing.this, "Error", Toast.LENGTH_LONG).show();
                    }else {
                        takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, MEDIA_TYPE_IMG);
                        startActivityForResult(takePicIntent, TAKE_PIC_CHOICE_CODE);
                    }
                    break;
                case 1: //choose pic
                    Intent choosePicIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    choosePicIntent.setType("image/*");
                    startActivityForResult(choosePicIntent, CHOOSE_PIC_CHOICE_CODE);
                    break;
            }
        }

        private Uri getOutputMediaFileUri(int mediaType) {
            if (isExternalStorageAvailable()) {
                //get the external storage
                String appName = AddListing.this.getString(R.string.app_name);
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
            if(state.equals(Environment.MEDIA_MOUNTED)){
                return true;
            }else {
                return false;
            }
        }
    };
    private DialogInterface.OnClickListener mDialogPropertyListener = new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialog, int choice) {
            String[] p = getApplicationContext().getResources().getStringArray(R.array.propertyChoices);
            switch (choice){
                case 0: {
                    //cottage, set the label to the selected choice and break
                    mPropertyType.setText(p[0]);
                    break;
                }
                case 1:{
                    //flat
                    mPropertyType.setText(p[1]);
                    break;
                }
                case 2:{
                    //Full house
                    mPropertyType.setText(p[2]);
                    break;
                }

                case 3:{
                    //Sharing
                    mPropertyType.setText(p[3]);
                    break;
                }
                default:
                    mPropertyType.setText("");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //attach views to activity
        mRoomPrice = (EditText)findViewById(R.id.price_field);
        mRoomDescription = (EditText)findViewById(R.id.description_field);
        mRoomLocation = (EditText)findViewById(R.id.location_field);
        mNumberOfBedrooms = (EditText)findViewById(R.id.add_bedrooms_textfield);
        mMoveinDate = (EditText)findViewById(R.id.add_movein_textfield);
        mNumberOfBaths = (EditText)findViewById(R.id.add_baths_field);
        mNumToilets = (EditText)findViewById(R.id.add_num_toilets_field);
        mZesaAvailability = (EditText)findViewById(R.id.zesa_field);
        mZinwaAvailability = (EditText)findViewById(R.id.water_supply_field);
        mPropertyType = (TextView)findViewById(R.id.add_propertytype_textField);
        mContactNumber = (EditText)findViewById(R.id.contact_number_field);

        mPropertyType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddListing.this);
                builder.setItems(R.array.propertyChoices, mDialogPropertyListener);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        mRoomImage = (ImageView)findViewById(R.id.add_photo_imageview);
        mAddImage = (TextView)findViewById(R.id.add_photo_label);
        mRoomImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddImage.setVisibility(View.GONE);
                AlertDialog.Builder builder = new AlertDialog.Builder(AddListing.this);
                builder.setItems(R.array.pic_choices, mDialogListener);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        mPostListing = (Button)findViewById(R.id.post_button);
        mPostListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject Room = createRoom();
                if (Room == null) {
                    //error
                    Toast.makeText(AddListing.this, "Error", Toast.LENGTH_LONG).show();
                } else {
                    addRoom(Room);
                }
            }
        });
    }

    //check box selected
    public void onCheckBoxSelected(View view){
        boolean checked = ((CheckBox) view).isChecked();

        //check which checkbox was clicked
        switch (view.getId()){
            case R.id.yes_inclusive_checkbox:{
                if(checked){
                    //price inclusive, set
                    mPriceIncOrExcl = "incl. bills";
                }
                break;
            }
            case R.id.no_inclusive_checkbox:{
                if(checked){
                    //price exclusive
                    mPriceIncOrExcl = "excl. bills";
                }
                break;
            }
        }
    }

    protected void addRoom(ParseObject Room) {

        //show progress dialog
        /*final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("Uploading Room...");
        progressDialog.setCancelable(false);
        progressDialog.show();*/

        Room.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    //success
                    //check if progress dialog is not null and running then dismiss
                    /*if(progressDialog != null && progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }*/
                    Toast.makeText(AddListing.this, "Added", Toast.LENGTH_LONG).show();
                } else {
                    //error
                    Toast.makeText(AddListing.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("Error", e.getMessage().toString());
                }
            }
        });
    }

    protected ParseObject createRoom() {
        //create the room
        final ParseObject newRoom = ParseObject.create("Room");
        //add the room attributes
        newRoom.put("roomSenderID", ParseUser.getCurrentUser().getObjectId());
        newRoom.put("roomSenderName", ParseUser.getCurrentUser().getUsername());
        newRoom.put("roomDescription", mRoomDescription.getText().toString());
        newRoom.put("roomPrice", mRoomPrice.getText().toString());
        newRoom.put("roomLocation", mRoomLocation.getText().toString());
        newRoom.put("roomPriceIncOrExcl", mPriceIncOrExcl.toString());
        newRoom.put("roomPropertyType", mPropertyType.getText().toString());
        //newRoom.put("roomNumOfBedRooms", Integer.parseInt(mNumberOfBedrooms.getText().toString()));
        newRoom.put("roomNumOfBedRooms", Integer.parseInt(mNumberOfBedrooms.getText().toString()));
        newRoom.put("moveinDate", mMoveinDate.getText().toString());
        newRoom.put("roomNumOfBaths", mNumberOfBaths.getText().toString());
        newRoom.put("roomNumOfToilets", mNumToilets.getText().toString());
        //newRoom.put("zesa", mZesaAvailability);
        newRoom.put("waterAvailability", mZinwaAvailability.getText().toString());
        newRoom.put("contactNumber", mContactNumber.getText().toString());

        //newJob.put("jobImage", mMediaUri);

        //get the image from imageview
        Bitmap bitmap;
        if(mRoomImage.getDrawable() instanceof BitmapDrawable){
            //if there is a BitmapDrawable, get the bitmap
            bitmap = ((BitmapDrawable) mRoomImage.getDrawable()).getBitmap();
            //convert to byte
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //compress image to lower quality
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] image = outputStream.toByteArray();

            //create the ParseFile
            ParseFile imageFile = new ParseFile("mUploadedfile.png", image);
            //save the parsefile
            imageFile.saveInBackground();
            //associate file with the room object
            newRoom.put("roomImage", imageFile);
        }else{
            //create on or ignore
        }

        /*byte[] fileBytes = FileHelper.getByteArrayFromFile(this, mMediaUri);
        if(fileBytes == null){
            return null;
        }else {
            fileBytes = FileHelper.reduceImageForUpload(fileBytes, mExtension);
            String fileName = FileHelper.getFileName(this, mMediaUri, "image");
            ParseFile newFile = new ParseFile(fileName, fileBytes);

            newRoom.put("roomImage", newFile);
        }*/

        return newRoom;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == CHOOSE_PIC_CHOICE_CODE){
                if(data == null){
                    //error
                }else {
                    mMediaUri = data.getData();
                    mExtension = FilenameUtils.getExtension(mMediaUri.toString());
                }
                //Bitmap img = BitmapFactory.decodeFile(String.valueOf(mMediaUri));
                mRoomImage.setImageURI(mMediaUri);
                //set the selected image and scale to fit imageView
                mRoomImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }else {
                //add to gallery
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(mMediaUri);
                this.sendBroadcast(mediaScanIntent);
            }
        }
        else if(resultCode != RESULT_CANCELED){
            //error: display error message
        }
    }

}
