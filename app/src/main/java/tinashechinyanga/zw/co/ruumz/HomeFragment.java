package tinashechinyanga.zw.co.ruumz;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tinashechinyanga.zw.co.ruumz.model.RoomSummaryViewModel;

/**
 * Created by Tinashe on 1/14/2016.
 */

/**
 * A placeholder fragment containing a simple view.
 */

public class HomeFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    //static number of rooms fetched upon each network request/call
    private static final int ROOMS_FETCHED_LIMIT = 12;

    //recyclerView
    private RecyclerView recyclerView;
    private RoomCardRecyclerViewAdapter roomAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ParseObject> mRooms, mLatestRooms, mMoreRooms = new ArrayList<>();

    //swipe to refresh
    private SwipeRefreshLayout swipeRefreshLayout;

    //date to track date room last updated
    private Date lastUpdated;

    //date to track date of room last inserted onLoadMore/scrolling down
    private Date lastRoomDate;

    //progress dialog
    private ProgressDialog progressDialog;

    //viewmodel
    private RoomSummaryViewModel roomSummaryViewModel;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HomeFragment newInstance(int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        recyclerView = rootView.findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        //summary viewmodel
        roomSummaryViewModel = ViewModelProviders.of(this).get(RoomSummaryViewModel.class);
        setUpProgressDialog();
        roomSummaryViewModel.getmAllRooms().observe(this, new Observer<List<ParseObject>>() {
            @Override
            public void onChanged(List<ParseObject> rooms) {
                mRooms = rooms;
               // Log.i("Rooms", "Observed rooms: " + mRooms.get(1).getObjectId());
                //check if progress dialog is not null and running then dismiss
                if(progressDialog != null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                //intialise adapter and set it
                roomAdapter = new RoomCardRecyclerViewAdapter(mRooms);
                recyclerView.setAdapter(roomAdapter);

                progressDialog.hide();

                //add endless scrolling
                recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener((LinearLayoutManager) layoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount) {
                        //load more rooms and add to the end of the list
                        new FetchMoreRooms().execute();
                    }
                });
            }
        });


        //check if network is present, then run the query in the background
//        new DownloadRooms().execute();

        //setup the swipeToRefreshLayout i.e. onSwipeDown, fetch new rooms added
        swipeRefreshLayout.setOnRefreshListener(() -> {
            fetchUpdatedRooms();
        });
        //configure the swipe refresh colours
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        return rootView;
    }

    private void fetchUpdatedRooms() {
        //get the new data off the main thread
        /*
        * 1.
        * */
        new FetchLatestRooms().execute();
    }

    private class DownloadRooms extends AsyncTask<Void, Integer, List<ParseObject>>{

        //local variable


        //do the long running task
        //run the query
        @Override
        protected List<ParseObject> doInBackground(Void... params) {
            ParseQuery<ParseObject> getRoomQuery = ParseQuery.getQuery("Room");
            getRoomQuery.setLimit(ROOMS_FETCHED_LIMIT);
            getRoomQuery.orderByDescending("updatedAt");
            try {
                mRooms = getRoomQuery.find();
            } catch (ParseException e) {
                e.printStackTrace();
                //dismiss progress bar
//                progressDialog.dismiss();
//                Toast.makeText(getActivity(), "Unable to fetch romoms. Please check your internet", Toast.LENGTH_LONG).show();
//                Log.e("Fetch Rooms Error: ", "Unable to get rooms, " + e.getMessage());
            }
            //get value of last updated room
            if(mRooms.size() > 0) {
                //check if there are rooms fetched and update the value of lastUpdated && last room's date
                lastUpdated = mRooms.get(mRooms.size() - mRooms.size()).getUpdatedAt();
                lastRoomDate = mRooms.get(mRooms.size() - 1).getUpdatedAt();
            }else {
                //figure this portion out, but leaving blank seems ok
            }

            return mRooms;
        }

        //runs when background work is executing
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            setUpProgressDialog();
        }

        //update the UI main thread with the results returned by the doInBackground
        //also dismiss the progressbar
        @Override
        protected void onPostExecute(List<ParseObject> rooms){
            mRooms = rooms;
            //check if progress dialog is not null and running then dismiss
            if(progressDialog != null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            //intialise adapter and set it
            roomAdapter = new RoomCardRecyclerViewAdapter(mRooms);
            recyclerView.setAdapter(roomAdapter);

            //add endless scrolling
            recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener((LinearLayoutManager) layoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    //load more rooms and add to the end of the list
                    new FetchMoreRooms().execute();
                }
            });
        }
    }

    private void setUpProgressDialog() {
        //initialise and show progress bar
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Fetching rooms...");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    //fetch updated rooms off the main thread
    private class FetchLatestRooms extends AsyncTask<Void, Integer, List<ParseObject>>{

        @Override
        protected List<ParseObject> doInBackground(Void... params) {
            //fetch the latest rooms
            ParseQuery<ParseObject> getLatestRoomQuery = ParseQuery.getQuery("Room");
            getLatestRoomQuery.whereGreaterThan("updatedAt", lastUpdated);
            getLatestRoomQuery.orderByDescending("updatedAt");
            try {
                mLatestRooms = getLatestRoomQuery.find();
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "No new rooms", Toast.LENGTH_LONG).show();
            }
            //check if there are new rooms and assign the topmost elements date to lastUpdated
            if(mLatestRooms.size() == 0){
                //check if mRooms has any elements
                if(mRooms.size() > 0) {
                    lastUpdated = mRooms.get(0).getUpdatedAt();
                }
            }else {
                lastUpdated = mLatestRooms.get(0).getUpdatedAt();
            }

            return mLatestRooms;
        }

        //update main UI with the results from doInBackground
        @Override
        protected void onPostExecute(List<ParseObject> latestRooms){
            //add the latest rooms added to the adapter
            roomAdapter.addAll(latestRooms);
            roomAdapter.notifyItemInserted(0);
            //stop the refresh animator
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    //fetch more rooms off the main thread
    private class FetchMoreRooms extends AsyncTask<Void, Integer, List<ParseObject>>{

        @Override
        protected List<ParseObject> doInBackground(Void... params) {
            //fetch the latest rooms
            ParseQuery<ParseObject> getMoreRoomsQuery = ParseQuery.getQuery("Room");
            getMoreRoomsQuery.whereLessThan("updatedAt", lastRoomDate);
            getMoreRoomsQuery.setLimit(ROOMS_FETCHED_LIMIT);
            getMoreRoomsQuery.orderByDescending("updatedAt");
            try {
                mMoreRooms = getMoreRoomsQuery.find();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //check if there are new rooms and assign the bottom most element's date to lastRoomDate
            if(mMoreRooms.size() == 0){
                //check if mRooms has any elements
                if(mRooms.size() > 0) {
                    lastRoomDate = mRooms.get(mRooms.size() - 1).getUpdatedAt();
                }
            }else {
                lastRoomDate = mMoreRooms.get(mMoreRooms.size() - 1).getUpdatedAt();
            }

            return mMoreRooms;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //show progressbar

        }

        //update main UI with the results from doInBackground
        @Override
        protected void onPostExecute(List<ParseObject> moreRooms){
            //add the latest rooms added to the adapter
            int curSize = roomAdapter.getItemCount();
            //add the new rooms to the list of existing rooms
            mRooms.addAll(moreRooms);
            roomAdapter.notifyItemRangeInserted(curSize, mRooms.size() - 1);
            //dismiss progressbar

        }
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
}













