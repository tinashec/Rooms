package tinashechinyanga.zw.co.ruumz.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
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

    private DataSource<String, ParseObject> roomSummaryDataSource;

    private ArrayList<RoomSummaryEntity> allRooms = new ArrayList<>();
    private MutableLiveData<List<ParseObject>> mAllRooms = new MutableLiveData<>();
    private List<ParseObject> mRooms = new ArrayList<>();

    private LiveData<PagedList<ParseObject>> rooms;
    private LiveData<PagedList<ParseObject>> currentUserRooms;

    public LiveData<PagedList<ParseObject>> getAllRooms(){

        PagedList.Config pagegListConfig = new PagedList.Config.Builder().setEnablePlaceholders(false)
                .setPrefetchDistance(6)
                .setInitialLoadSizeHint(12)
                .setPageSize(8).build();

        RoomSummaryDataSourceFactory roomSummaryDataSourceFactory = new RoomSummaryDataSourceFactory();

        rooms = new LivePagedListBuilder(roomSummaryDataSourceFactory, pagegListConfig).build();

        return rooms;
    }

    public LiveData<PagedList<ParseObject>> getCurrentlyLoggedinUserRooms(String roomOwner){
        PagedList.Config pagedListConfig = new PagedList.Config.Builder().setEnablePlaceholders(true)
                .setPrefetchDistance(5)
                .setInitialLoadSizeHint(8)
                .setPageSize(6).build();

        Log.i("Current User: ", "Current userID: " + roomOwner);

        RoomSummaryDataSourceFactory roomSummaryDataSourceFactory = new RoomSummaryDataSourceFactory(roomOwner);

        currentUserRooms = new LivePagedListBuilder(roomSummaryDataSourceFactory, pagedListConfig).build();

        return currentUserRooms;
    }

    public void invalidateRoomSummaryDatasource(){
        //datasource.invalidate
        RoomSummaryDataSourceFactory roomSummaryDataSourceFactory = new RoomSummaryDataSourceFactory();
        roomSummaryDataSource =  roomSummaryDataSourceFactory.create();
        roomSummaryDataSource.invalidate();
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

}
