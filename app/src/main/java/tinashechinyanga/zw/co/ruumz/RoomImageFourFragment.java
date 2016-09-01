package tinashechinyanga.zw.co.ruumz;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class RoomImageFourFragment extends Fragment {

    public RoomImageFourFragment() {
        // Required empty public constructor
    }


    public interface SetImageFourUrl{
        public void setImageFourUrl();
    }

    private SetImageFourUrl setImageFourUrl;
    //url for the image
    private String imageUrl;
    private ImageView mRoomImageFour;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_room_image_four, container, false);
        mRoomImageFour = (ImageView)rootview.findViewById(R.id.detail_roomimage_four);
        //call the interface which, in onAttach, passes control the activity that implements the public method to
        //set the imageUrl
        setImageFourUrl.setImageFourUrl();
        //set the image
        Glide.with(getActivity().getApplicationContext()).load(imageUrl)
                .centerCrop()
                .centerCrop()
                .thumbnail(0.6f)
                .into(mRoomImageFour);
        return rootview;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        //initiate the events that occur once the fragment is attached to the activity
        try{
            setImageFourUrl = (SetImageFourUrl)getActivity();

        }catch(ClassCastException cce){
            throw new ClassCastException(getActivity().toString() + "must implement SetImageFourUrlListener");
        }
    }

    //method accessed by parent activity
    public void setSetImageFourUrl(String url){
        imageUrl = url;
    }
}
