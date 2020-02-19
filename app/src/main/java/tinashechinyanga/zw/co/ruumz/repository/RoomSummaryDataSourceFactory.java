package tinashechinyanga.zw.co.ruumz.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.parse.ParseObject;

public class RoomSummaryDataSourceFactory extends DataSource.Factory<String, ParseObject> {

    private String roomOwner;

    public RoomSummaryDataSourceFactory(){}

    public RoomSummaryDataSourceFactory(String roomOwner){
        this.roomOwner = roomOwner;
    }

    //use to hold a reference to the Datasource
    public MutableLiveData<RoomSummaryDataSource> postLiveData;
    public MutableLiveData<RoomSummaryDataSource> currentUserRoomsPostLiveData;

    @NonNull
    @Override
    public DataSource<String, ParseObject> create() {
//        Log.i("Current User: ", "Current userId: " + ParseUser.getCurrentUser().getObjectId());
        RoomSummaryDataSource roomSummaryDataSource;
        if(roomOwner != null){
            roomSummaryDataSource = new RoomSummaryDataSource(roomOwner);
            //keep reference to the datasource using a MutableLiveData reference
            currentUserRoomsPostLiveData = new MutableLiveData<>();
            currentUserRoomsPostLiveData.postValue(roomSummaryDataSource);

        }else {
            roomSummaryDataSource = new RoomSummaryDataSource();
            //keep reference to the datasource using a MutableLiveData reference
            postLiveData = new MutableLiveData<>();
            postLiveData.postValue(roomSummaryDataSource);
        }
        return roomSummaryDataSource;
    }
}
