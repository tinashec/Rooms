package tinashechinyanga.zw.co.ruumz.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import tinashechinyanga.zw.co.ruumz.dao.RoomSummaryDao;
import tinashechinyanga.zw.co.ruumz.entity.RoomSummaryEntity;

public class RoomSummaryRepository {

    private Application application;

    /*
    * get a handle to the DB and intialise member variables
    * */
    public RoomSummaryRepository(Application application){
        this.application = application;
//        RoomSummaryDatabase roomSummaryDatabase = RoomSummaryDatabase.getDatabase(application);
//        roomSummaryDao = roomSummaryDatabase.roomSummaryDao();
//        mAllRooms =  roomSummaryDao.getAllRooms();
    }

    private RoomSummaryDao roomSummaryDao;

    private ArrayList<RoomSummaryEntity> allRooms = new ArrayList<>();
    private MutableLiveData<List<ParseObject>> mAllRooms = new MutableLiveData<>();
    private List<ParseObject> mRooms = new ArrayList<>();

    private LiveData<PagedList<ParseObject>> rooms;

    public LiveData<PagedList<ParseObject>> getAllRooms(){

//        new DownloadRooms().execute();

        PagedList.Config pagegListConfig = new PagedList.Config.Builder().setEnablePlaceholders(false)
                .setPrefetchDistance(6)
                .setInitialLoadSizeHint(12)
                .setPageSize(12).build();

        RoomSummaryDataSourceFactory roomSummaryDataSourceFactory = new RoomSummaryDataSourceFactory();

//        PagedList.Config config = new PagedList.Config.Builder().setPageSize(24).build();

        rooms = new LivePagedListBuilder(roomSummaryDataSourceFactory, pagegListConfig).build();

        return rooms;
    }

    public void insert(RoomSummaryEntity roomSummaryEntity) {
        new insertAsyncTask(roomSummaryDao).execute(roomSummaryEntity);
    }

    private static class insertAsyncTask extends AsyncTask<RoomSummaryEntity, Void, Void> {
        private RoomSummaryDao roomAsyncTaskDao;

        insertAsyncTask(RoomSummaryDao roomSummaryDao){
            roomAsyncTaskDao = roomSummaryDao;
        }

        @Override
        protected Void doInBackground(final RoomSummaryEntity... params){
            roomAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

//    private class DownloadRooms extends AsyncTask<Void, Integer, List<ParseObject>>{
//
//        private static final int ROOMS_FETCHED_LIMIT = 12;
//        //local variable
//        private ProgressDialog progressDialog;
//        private Date lastUpdated, lastRoomDate;
//
//        //do the long running task
//        //run the query
//        @Override
//        protected List<ParseObject> doInBackground(Void... params) {
//            List<ParseObject> rooms = new ArrayList<>();
//            ParseQuery<ParseObject> getRoomQuery = ParseQuery.getQuery("Room");
//            getRoomQuery.setLimit(ROOMS_FETCHED_LIMIT);
//            getRoomQuery.orderByDescending("updatedAt");
//            try {
//                rooms = getRoomQuery.find();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            //get value of last updated room
//            if(rooms.size() > 0) {
//                //check if there are rooms fetched and update the value of lastUpdated && last room's date
//                lastUpdated = rooms.get(rooms.size() - rooms.size()).getUpdatedAt();
//                lastRoomDate = rooms.get(rooms.size() - 1).getUpdatedAt();
//            }else {
//                //figure this portion out, but leaving blank seems ok
//            }
//
//            Log.i("Rooms", "Execute in background rooms value: " + rooms.size());
//            return rooms;
//        }
//
//        //runs when background work is executing
//        @Override
//        protected void onPreExecute(){
//            super.onPreExecute();
//            //initialise and show progress bar
//        }
//
//        //update the UI main thread with the results returned by the doInBackground
//        //also dismiss the progressbar
////        @Override
////        protected void onPostExecute(List<ParseObject> rooms){
////            mRooms = rooms;
////            mAllRooms.setValue(mRooms);
////            Log.i("Rooms", "In postExecute rooms value: " + mRooms.size() + " " + rooms.size());
////            //check if progress dialog is not null and running then dismiss
////            if(progressDialog != null && progressDialog.isShowing()){
////                progressDialog.dismiss();
////            }
////        }
//    }
}
