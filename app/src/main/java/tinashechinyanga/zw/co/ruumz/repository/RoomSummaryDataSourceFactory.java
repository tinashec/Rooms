package tinashechinyanga.zw.co.ruumz.repository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.parse.ParseObject;

public class RoomSummaryDataSourceFactory extends DataSource.Factory<Integer, ParseObject> {
    @NonNull
    @Override
    public DataSource<Integer, ParseObject> create() {
        RoomSummaryDataSource roomSummaryDataSource = new RoomSummaryDataSource();
        return roomSummaryDataSource;
    }
}
