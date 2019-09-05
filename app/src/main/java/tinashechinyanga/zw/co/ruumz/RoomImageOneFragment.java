package tinashechinyanga.zw.co.ruumz;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RoomImageOneFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RoomImageOneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoomImageOneFragment extends Fragment {
    private String imageUrl;
    private GetImageUrlListener imageUrlListener;

    private ImageView mRoomImageOne;
    // TODO: Rename and change types and number of parameters
    public static RoomImageOneFragment newInstance(String imageUrl) {
        RoomImageOneFragment fragment = new RoomImageOneFragment();
        Bundle args = new Bundle();
        args.putString("Image Url", imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    public RoomImageOneFragment() {
        // Required empty public constructor
    }

    //interface to be implemented in the parent activity
    public interface GetImageUrlListener{
        void passImageUrl();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_room_image_one, container, false);
        imageUrlListener.passImageUrl();
        mRoomImageOne = rootView.findViewById(R.id.detail_roomimage_one);
        //setting the imageview
        if(imageUrl != null){
            Glide.with(getActivity().getApplicationContext()).load(imageUrl)
                    .centerCrop()
                    .centerCrop()
                    .thumbnail(0.6f)
                    .into(mRoomImageOne);
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try{
            imageUrlListener = (GetImageUrlListener)getActivity();
        }catch (ClassCastException cce){
            throw new ClassCastException(getActivity().toString() + "must implement GetImageUrlListener");
        }
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }
}
