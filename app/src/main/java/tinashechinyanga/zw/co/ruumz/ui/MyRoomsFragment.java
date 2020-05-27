package tinashechinyanga.zw.co.ruumz.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tinashechinyanga.zw.co.ruumz.R;
import tinashechinyanga.zw.co.ruumz.RoomCardRecyclerViewAdapter;
import tinashechinyanga.zw.co.ruumz.repository.RoomSummaryDataSourceFactory;
import tinashechinyanga.zw.co.ruumz.viewmodel.RoomSummaryViewModel;

/**
 * Created by Tinashe on 1/14/2016.
 */

/**
 * A placeholder fragment containing a simple view.
 */

public class MyRoomsFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    //recyclerView
    protected RecyclerView recyclerView;
    protected RoomCardRecyclerViewAdapter roomAdapter;
    protected List<ParseObject> mRooms = new ArrayList<>();
    protected List<ParseObject> mLatestRooms = new ArrayList<>();
    protected Context context;
    protected TextView emptyRoomsList;

    private RoomSummaryViewModel myRoomsSummaryViewModel;

    //swipe to refresh
    private SwipeRefreshLayout swipeRefreshLayout;
    //progress dialog
    private ProgressDialog progressDialog;

    //date to track date room last updated
    private Date lastUpdated;

    //datasource factory
    RoomSummaryDataSourceFactory roomSummaryDataSourceFactory;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MyRoomsFragment newInstance(int sectionNumber) {
        MyRoomsFragment fragment = new MyRoomsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MyRoomsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        recyclerView = rootView.findViewById(R.id.recycler_view);

        //add empty view
        emptyRoomsList = rootView.findViewById(R.id.empty_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        //intialise and set the adapter
        roomAdapter = new RoomCardRecyclerViewAdapter("My Rooms");
        recyclerView.setAdapter(roomAdapter);

        //connect the view model
        myRoomsSummaryViewModel = ViewModelProviders.of(this).get(RoomSummaryViewModel.class);
        setUpProgressDialog();
        //observe the data and update the viewmodel when changes occur in the data
        getAllCurrentUserRooms();

        //check if network is present,
        // ToDo:
        // run the query in the background

        //setup the swipeToRefreshLayout i.e. onSwipeDown, fetch new rooms added
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //invalidate datasorucefactory
                myRoomsSummaryViewModel.invalidateCurrentUserDatasource(ParseUser.getCurrentUser().getObjectId());
                getAllCurrentUserRooms();
            }
        });

        //configure the swipe refresh colours
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        return rootView;
    }

    private void getAllCurrentUserRooms() {
        myRoomsSummaryViewModel.getmAllCurrentUserRooms(ParseUser.getCurrentUser().getObjectId()).observe(this, new Observer<PagedList<ParseObject>>() {
            @Override
            public void onChanged(PagedList<ParseObject> parseObjects) {
                roomAdapter.submitList(parseObjects);
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    //method to check if internet is present or not
    public static boolean isInternetPresent(Context context){
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //network present
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivity.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivity.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        }else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if(info != null){
                for(int i = 0; i < info.length; i++){
                    if(info[i].getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void setUpProgressDialog() {
        //initialise and show progress bar
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Fetching rooms...");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }
}













