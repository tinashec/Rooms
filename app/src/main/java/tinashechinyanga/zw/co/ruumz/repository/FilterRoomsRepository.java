package tinashechinyanga.zw.co.ruumz.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/*
* Repository modules handle data operations. They provide a clean API so that the rest of the app can retrieve
* this data easily. They know where to get the data from and what API calls to make when data is updated.
* You can consider repositories to be mediators between different data sources, such as persistent models, web services, and caches.
* */

public class FilterRoomsRepository {

    public LiveData<List<ParseObject>> getFilteredRooms(String sortyBy) {
        Log.i("SortBy: ", "sortBy Field value: " + sortyBy );
        MutableLiveData<List<ParseObject>> rooms = new MutableLiveData<>();
        ParseQuery<ParseObject> filterQuery = ParseQuery.getQuery("Room");

        if(sortyBy.equals("ascending")){
            Log.i("Sort by", "Rooms sorted in ascending order");
            filterQuery.orderByAscending("roomRentalAmount");
        }else if (sortyBy.equals("descending")){
            Log.i("Sort by", "Rooms sorted in descending order");
            filterQuery.orderByDescending("roomRentalAmount");
        }else {
            filterQuery.orderByDescending("updatedAt");
        }

        filterQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    //success
                    Log.i("Rooms", "Filtered rooms, " + objects.get(0).getObjectId());
                    rooms.setValue(objects);
                }else{
                    //error, handle accordingly
                    Log.e("Error", "Error filtering, " + e.getMessage());
                }
            }
        });
        Log.i("Rooms", "Live data rooms, " + rooms.getValue());
        return rooms;
    }
}
