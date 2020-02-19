package tinashechinyanga.zw.co.ruumz.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.parse.ParseObject;

import tinashechinyanga.zw.co.ruumz.entity.RoomSummaryEntity;
import tinashechinyanga.zw.co.ruumz.repository.RoomSummaryRepository;

public class RoomSummaryViewModel extends AndroidViewModel {
    //variable to hold a reference to repository
    private RoomSummaryRepository roomSummaryRepository;
    //LiveData variable to cache list of words
    private LiveData<PagedList<ParseObject>> mAllRooms;
    private LiveData<PagedList<ParseObject>> mAllCurrentUserRooms;

    public RoomSummaryViewModel(@NonNull Application application) {
        super(application);
        roomSummaryRepository = new RoomSummaryRepository(application);
    }

    //completely hides the implementation from the UI
    public LiveData<PagedList<ParseObject>> getmAllRooms(){
        //use repository to get rooms
        mAllRooms = roomSummaryRepository.getAllRooms();
        return mAllRooms;
    }

    public LiveData<PagedList<ParseObject>> getmAllCurrentUserRooms(String currentUserID){
        mAllCurrentUserRooms = roomSummaryRepository.getCurrentlyLoggedinUserRooms(currentUserID);
        return mAllCurrentUserRooms;
    }

    //invalidate datasource
    public void invalidateRoomSummaryDatasource(){
        //datasource.invalidate
        roomSummaryRepository.invalidateRoomSummaryDatasource();
    }


    public void insert(RoomSummaryEntity roomSummaryEntity){
        roomSummaryRepository.insert(roomSummaryEntity);
    }
}
