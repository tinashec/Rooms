package tinashechinyanga.zw.co.ruumz.repository;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import tinashechinyanga.zw.co.ruumz.FilterRoomsViewModel;
import tinashechinyanga.zw.co.ruumz.service.FilterRoomsWebService;

public class FilterRoomsRepository implements FilterRoomsWebService {

    @Override
    public void getFilteredRooms(FilterRoomsViewModel filterRoomsViewModel) {
        /*
         * query rooms based on the filters selected
         * */
        ParseQuery<ParseObject> filteredRoomsQuery = ParseQuery.getQuery("Room");

    }
}
