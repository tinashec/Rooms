package tinashechinyanga.zw.co.ruumz.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.parse.ParseObject;

public class RoomSummaryDataSourceFactory extends DataSource.Factory<String, ParseObject> {

    //use to hold a reference to the Datasource
    public MutableLiveData<RoomSummaryDataSource> postLiveData;

    @NonNull
    @Override
    public DataSource<String, ParseObject> create() {
        RoomSummaryDataSource roomSummaryDataSource = new RoomSummaryDataSource();

        //keep reference to the datasource using a MutableLiveData reference
        postLiveData = new MutableLiveData<>();
        postLiveData.postValue(roomSummaryDataSource);

        return roomSummaryDataSource;
    }
}
