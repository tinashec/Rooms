package tinashechinyanga.zw.co.ruumz.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.parse.ParseObject;

import java.util.List;

import tinashechinyanga.zw.co.ruumz.entity.RoomSummaryEntity;
import tinashechinyanga.zw.co.ruumz.repository.RoomSummaryRepository;

public class RoomSummaryViewModel extends AndroidViewModel {
    //variable to hold a reference to repository
    private RoomSummaryRepository roomSummaryRepository;
    //LiveData variable to cache list of words
    private LiveData<List<ParseObject>> mAllRooms;

    public RoomSummaryViewModel(@NonNull Application application) {
        super(application);
        roomSummaryRepository = new RoomSummaryRepository(application);
//        mAllRooms = roomSummaryRepository.getAllRooms();
    }

    //completely hides the implementation from the UI
    public LiveData<List<ParseObject>> getmAllRooms(){
        //use repository to get rooms
        mAllRooms = roomSummaryRepository.getAllRooms();
        return mAllRooms;
    }

    public void insert(RoomSummaryEntity roomSummaryEntity){
        roomSummaryRepository.insert(roomSummaryEntity);
    }
}
