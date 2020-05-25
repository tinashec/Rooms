package tinashechinyanga.zw.co.ruumz.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tinashechinyanga.zw.co.ruumz.dao.RoomSummaryDao;
import tinashechinyanga.zw.co.ruumz.entity.RoomSummaryEntity;

@Database(entities = {RoomSummaryEntity.class}, version = 1)
public abstract class RoomSummaryDatabase extends RoomDatabase {
    /*
    * make this a singleton
    * */
    private static RoomSummaryDatabase INSTANCE;

    public static RoomSummaryDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (RoomSummaryDatabase.class){
                if(INSTANCE == null){
                    //create database here (implement migration strategy)
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomSummaryDatabase.class, "room_summary_database").build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract RoomSummaryDao roomSummaryDao();
}
