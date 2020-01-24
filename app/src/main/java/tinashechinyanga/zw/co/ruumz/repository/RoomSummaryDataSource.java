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

    private static final int ROOMS_FETCHED_LIMIT = 12;
    private Date lastRoomDate;
    //basic query
    public ParseQuery<ParseObject> getBaseQuery(){
        return ParseQuery.getQuery("Room").orderByDescending("updatedAt");
    }
/*
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<ParseObject> callback) {
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

            //return results to PagedList callback
            callback.onResult(roomsReturned, params.requestedStartPosition, numOfRooms);
        } catch (ParseException e) {
            //will need to properly handle this exception e.g. show a dialog or maybe a toast
            e.printStackTrace();
            //or retry the above logic
        }
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<ParseObject> callback) {
        ParseQuery<ParseObject> getMoreRoomsQuery = getBaseQuery();
        getMoreRoomsQuery.setLimit(params.loadSize);

        //fetch the next set of data from a different offset
        getMoreRoomsQuery.setSkip(params.startPosition);

        try {
            List<ParseObject> moreRoomReturned = getMoreRoomsQuery.find();
            Log.i("Load range", "Load range is: " + moreRoomReturned.size());
            //return info to PagedList
            callback.onResult(moreRoomReturned);
        } catch (ParseException e) {
            e.printStackTrace();
            //handle exception

        }
    }

    @Override
    public void loadAfter(@NonNull ItemKeyedDataSource.LoadParams params, @NonNull ItemKeyedDataSource.LoadCallback<ParseObject> callback){
        ParseQuery getLatestRoomsQuery = getBaseQuery();
        getLatestRoomsQuery.whereGreaterThan("updatedAt", params.key);

        try {
            List<ParseObject> latestRooms = getLatestRoomsQuery.find();
            callback.onResult(latestRooms);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
*/
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
        getLatestRoomsQuery.whereGreaterThan("updatedAt", lastRoomDate);

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
