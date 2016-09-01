package tinashechinyanga.zw.co.ruumz;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by MUZ0007 on 1/14/2016.
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
    protected RoomRecyclerViewAdapter roomAdapter;
    protected List<ParseObject> mRooms = new ArrayList<>();
    protected List<ParseObject> mLatestRooms = new ArrayList<>();
    protected Context context;

    //swipe to refresh
    private SwipeRefreshLayout swipeRefreshLayout;

    //date to track date room last updated
    private Date lastUpdated;

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

        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        //check if network is present, then run the query in the background
        new DownloadMyRooms().execute();

        //setup the swipeToRefreshLayout i.e. onSwipeDown, fetch new rooms added
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchUpdatedRooms();
            }
        });
        //configure the swipe refresh colours
        swipeRefreshLayout.setColorSchemeColors(R.color.colorAccent);

        return rootView;
    }

    private void fetchUpdatedRooms() {
        //get the new data off the main thread
        /*
        * 1.
        * */
        new FetchLatestRooms().execute();
    }

    private class DownloadMyRooms extends AsyncTask<Void, Integer, List<ParseObject>>{

        //local variable
        private ProgressDialog progressDialog;

        //do the long running task
        //run the query
        @Override
        protected List<ParseObject> doInBackground(Void... params) {
            ParseQuery<ParseObject> getMyRoomsQuery = ParseQuery.getQuery("Room");
            getMyRoomsQuery.orderByDescending("updatedAt");
            getMyRoomsQuery.whereEqualTo("roomOwner", ParseUser.getCurrentUser().getObjectId().toString());
            Log.d("Owner: ", ParseUser.getCurrentUser().getObjectId());

            try {
                mRooms = getMyRoomsQuery.find();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //get value of last updated room
            if(mRooms.size() > 0) {
                //check if there are rooms fetched and update the value of lastUpdated
                lastUpdated = mRooms.get(mRooms.size() - mRooms.size()).getUpdatedAt();
            }else {
                //figure this portion out, but leaving blank seems ok
            }

            return mRooms;
        }

        //runs when background work is executing
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //initialise and show progress bar
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Fetching rooms... " + ParseUser.getCurrentUser().getUsername().toString());
            progressDialog.setCancelable(false);
            progressDialog.show();
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
            Log.d("Current User: ", ParseUser.getCurrentUser().getUsername().toString());
            //intialise adapter and set it
            roomAdapter = new RoomRecyclerViewAdapter(mRooms, "My Rooms");

            recyclerView.setAdapter(roomAdapter);
        }
    }

    //fetch updated rooms off the main thread
    private class FetchLatestRooms extends AsyncTask<Void, Integer, List<ParseObject>>{

        @Override
        protected List<ParseObject> doInBackground(Void... params) {
            //fetch the latest rooms
            ParseQuery<ParseObject> getLatestRoomQuery = ParseQuery.getQuery("Room");
            getLatestRoomQuery.whereGreaterThan("updatedAt", lastUpdated);
            getLatestRoomQuery.whereEqualTo("roomOwner", ParseUser.getCurrentUser().getObjectId().toString());
            getLatestRoomQuery.orderByDescending("createdAt");
            try {
                mLatestRooms = getLatestRoomQuery.find();
            } catch (ParseException e) {
                e.printStackTrace();
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
            //recyclerView.swapAdapter(roomAdapter, false);
            //stop the refresh animator
            swipeRefreshLayout.setRefreshing(false);
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













