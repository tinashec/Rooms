package tinashechinyanga.zw.co.ruumz.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import tinashechinyanga.zw.co.ruumz.dao.RoomSummaryDao;
import tinashechinyanga.zw.co.ruumz.db.RoomSummaryDatabase;
import tinashechinyanga.zw.co.ruumz.entity.RoomSummaryEntity;

public class RoomSummaryRepository {

    /*
    * get a handle to the DB and intialise member variables
    * */
    public RoomSummaryRepository(Application application){
        RoomSummaryDatabase roomSummaryDatabase = RoomSummaryDatabase.getDatabase(application);
        roomSummaryDao = roomSummaryDatabase.roomSummaryDao();
        mAllRooms =  roomSummaryDao.getAllRooms();
    }

    private RoomSummaryDao roomSummaryDao;
    private LiveData<List<RoomSummaryEntity>> mAllRooms;

    public LiveData<List<RoomSummaryEntity>> getAllRooms(){
        return mAllRooms;
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
