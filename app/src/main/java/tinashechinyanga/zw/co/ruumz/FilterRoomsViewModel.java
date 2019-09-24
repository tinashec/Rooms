package tinashechinyanga.zw.co.ruumz;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FilterRoomsViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    /*
    * A ViewModel object provides the data for a specific UI component, such as a fragment or activity, and
     * contains data-handling business logic to communicate with the model. For example, the ViewModel can call
     * other components to load the data, and it can forward user requests to modify the data. The ViewModel
     * doesn't know about UI components, so it isn't affected by configuration changes, such as recreating an
     * activity when rotating the device.
    * */

    /*
    * contains, sortBy, budget, propertyType, amenities
    * */

    MutableLiveData<String> sortBy = new MutableLiveData<>();

}
