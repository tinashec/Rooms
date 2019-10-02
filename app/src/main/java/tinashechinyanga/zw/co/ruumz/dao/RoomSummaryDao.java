package tinashechinyanga.zw.co.ruumz.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import tinashechinyanga.zw.co.ruumz.entity.RoomSummaryEntity;

public interface RoomSummaryDao {
    @Insert
    void insert(RoomSummaryEntity roomSummary);

    @Query("SELECT * from room_summary")
    LiveData<List<RoomSummaryEntity>>  getAllRooms();
}
