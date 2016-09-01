package tinashechinyanga.zw.co.ruumz;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class RoomImageSixFragment extends Fragment {

    public RoomImageSixFragment() {
        // Required empty public constructor
    }


    public interface SetImageSixUrl {
        public void setImageSixUrl();
    }

    private SetImageSixUrl setImageSixUrl;
    //url for the image
    private String imageUrl;
    private ImageView mRoomImageSix;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_room_image_six, container, false);
        mRoomImageSix = (ImageView)rootview.findViewById(R.id.detail_roomimage_six);
        //call the interface which, in onAttach, passes control the activity that implements the public method to
        //set the imageUrl
        setImageSixUrl.setImageSixUrl();
        //set the image
        Glide.with(getActivity().getApplicationContext()).load(imageUrl)
                .centerCrop()
                .centerCrop()
                .thumbnail(0.6f)
                .into(mRoomImageSix);
        return rootview;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        //initiate the events that occur once the fragment is attached to the activity
        try{
            setImageSixUrl = (SetImageSixUrl)getActivity();

        }catch(ClassCastException cce){
            throw new ClassCastException(getActivity().toString() + "must implement SetImageFourUrlListener");
        }
    }

    //method accessed by parent activity
    public void setSetImageSixUrl(String url){
        imageUrl = url;
    }
}
