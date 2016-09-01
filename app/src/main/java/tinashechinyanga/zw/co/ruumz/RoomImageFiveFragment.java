package tinashechinyanga.zw.co.ruumz;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class RoomImageFiveFragment extends Fragment {

    public RoomImageFiveFragment() {
        // Required empty public constructor
    }


    public interface SetImageFiveUrl{
        public void setImageFiveUrl();
    }

    private SetImageFiveUrl setImageFiveUrl;
    //url for the image
    private String imageUrl;
    private ImageView mRoomImageFive;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_room_image_five, container, false);
        mRoomImageFive = (ImageView)rootview.findViewById(R.id.detail_roomimage_five);
        //call the interface which, in onAttach, passes control the activity that implements the public method to
        //set the imageUrl
        setImageFiveUrl.setImageFiveUrl();
        //set the image
        Glide.with(getActivity().getApplicationContext()).load(imageUrl)
                .centerCrop()
                .centerCrop()
                .thumbnail(0.6f)
                .into(mRoomImageFive);
        return rootview;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        //initiate the events that occur once the fragment is attached to the activity
        try{
            setImageFiveUrl = (SetImageFiveUrl)getActivity();

        }catch(ClassCastException cce){
            throw new ClassCastException(getActivity().toString() + "must implement SetImageFourUrlListener");
        }
    }

    //method accessed by parent activity
    public void setSetImageFiveUrl(String url){
        imageUrl = url;
    }
}
