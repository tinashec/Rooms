package tinashechinyanga.zw.co.ruumz;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

public class RoomImageThreeFragment extends Fragment {

    //url for the image
    private String imageUrl;

    public RoomImageThreeFragment() {
        // Required empty public constructor
    }

    //interface
    public interface SetImageThreeUrl{
        public void setImageThreeUrl();
    }

    private SetImageThreeUrl setImageThreeUrl;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_room_image_three, container, false);
        imageView = (ImageView)rootView.findViewById(R.id.detail_roomimage_three);
        setImageThreeUrl.setImageThreeUrl();
        //set the image
        Glide.with(getActivity().getApplicationContext()).load(imageUrl).centerCrop().crossFade().into(imageView);
        return rootView;
    }


    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try{
            setImageThreeUrl = (SetImageThreeUrl)getActivity();
        }catch (ClassCastException cce){
            throw new ClassCastException(getActivity().toString() + " must implement SetImageThreeUrl");
        }
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

}
