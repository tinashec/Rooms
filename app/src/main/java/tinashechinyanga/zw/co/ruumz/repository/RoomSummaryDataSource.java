package tinashechinyanga.zw.co.ruumz.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;
import java.util.List;

public class RoomSummaryDataSource extends ItemKeyedDataSource<String, ParseObject> {

    public RoomSummaryDataSource(){

    }

    public RoomSummaryDataSource(String roomOwnerID){
        this.roomOwner = roomOwnerID;
    }

    private String roomOwner;
    private static final int ROOMS_FETCHED_LIMIT = 12;
    private Date lastRoomDate;
    //basic query
    public ParseQuery<ParseObject> getBaseQuery(){
        ParseQuery<ParseObject> baseQuery = ParseQuery.getQuery("Room").orderByDescending("updatedAt");;
        //check if the quesry should return all rooms or rooms specific to a particular user
        if(roomOwner != null) {
            //set base query with roomOwner as whereEqualTo
            baseQuery.whereEqualTo("roomOwner", roomOwner);
        }
        return baseQuery;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback callback) {
        ParseQuery<ParseObject> getRoomsSummaryQuery = getBaseQuery();

        //use values passed when PagedList was created
        getRoomsSummaryQuery.setLimit(params.requestedLoadSize);
//        getRoomsSummaryQuery.setSkip(params.requestedStartPosition);

        try {
            //loadInitial() should run queries synchronously so initial list won't be empty
            int numOfRooms = getRoomsSummaryQuery.count();
            List<ParseObject> roomsReturned = getRoomsSummaryQuery.find();

            Log.i("Loaded rooms", "Number of rooms loaded: " + roomsReturned.size());
//            Log.i("Parameters", "params.requestedStartPosition: " + params.requestedStartPosition);
            Log.i("Parameters", "params.requestedLoadSize: " + params.requestedLoadSize);
            lastRoomDate = roomsReturned.get(roomsReturned.size() - 1).getUpdatedAt();

            //return results to PagedList callback
            callback.onResult(roomsReturned, 0, numOfRooms);
        } catch (ParseException e) {
            //will need to properly handle this exception e.g. show a dialog or maybe a toast
            e.printStackTrace();
            //or retry the above logic
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams params, @NonNull LoadCallback callback) {
        ParseQuery getLatestRoomsQuery = getBaseQuery();
        getLatestRoomsQuery.whereLessThan("updatedAt", lastRoomDate);

        Log.i("Load after query: ", "Load after query detail: " + getLatestRoomsQuery.whereGreaterThan("updatedAt", lastRoomDate));

        try {
            List<ParseObject> latestRooms = getLatestRoomsQuery.find();
            Log.i("Latest rooms", "Number of latest rooms loaded: " + latestRooms.size());
            callback.onResult(latestRooms);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams params, @NonNull LoadCallback callback) {

    }

    @NonNull
    @Override
    public String getKey(@NonNull ParseObject item) {
        return item.getObjectId();
    }
}
