package tinashechinyanga.zw.co.ruumz.repository;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import tinashechinyanga.zw.co.ruumz.service.FilterRoomsWebService;

public class FilterRoomsRepository implements FilterRoomsWebService {

    @Override
    public void getFilteredRooms(String roomAmountColumn) {
        ParseQuery<ParseObject> filterQuery = ParseQuery.getQuery("Rooms");
        filterQuery.orderByDescending(roomAmountColumn);
    }
}
