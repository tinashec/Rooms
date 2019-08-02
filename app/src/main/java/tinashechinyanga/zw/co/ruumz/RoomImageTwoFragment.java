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
 * {@link RoomImageTwoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RoomImageTwoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoomImageTwoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //the images url
    private String imageUrl;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RoomImageOneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoomImageTwoFragment newInstance(String param1, String param2) {
        RoomImageTwoFragment fragment = new RoomImageTwoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    //interface to be implemented by the parent activity
    public interface SetImageTwoUrl{
        //the method to be accessed
        public void setImageUrl();
    }

    public RoomImageTwoFragment() {
        // Required empty public constructor
    }

    private SetImageTwoUrl mImageUrlListener;

    //the views
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_room_image_two, container, false);
        imageView = (ImageView)rootView.findViewById(R.id.details_roomimage_two);
        mImageUrlListener.setImageUrl();
        //set the imageview using the imageurl passed
        Glide.with(getActivity().getApplicationContext()).load(imageUrl).centerCrop().crossFade().into(imageView);
        return rootView;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        //calling the interface once the fragment attaches to the activity
        try{
            mImageUrlListener = (SetImageTwoUrl)getActivity();
        }catch (ClassCastException cce){
            throw new ClassCastException(getActivity().toString() + "must implement SetImageTwoUrl");
        }
    }
}
