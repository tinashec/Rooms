package tinashechinyanga.zw.co.ruumz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import tinashechinyanga.zw.co.ruumz.repository.FilterRoomsRepository;

public class FilterRoomsFragment extends Fragment {

    private FilterRoomsViewModel mViewModel;

    private RadioButton sortByLeastExpensive, sortByMostExpensive, sortByMostRecent;
    private EditText minAmount;
    private Button applyFiltersBtn;

    public static FilterRoomsFragment newInstance() {
        return new FilterRoomsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.filter_rooms_fragment, container, false);

        sortByLeastExpensive = rootView.findViewById(R.id.sort_by_least_expensive);
        sortByMostExpensive = rootView.findViewById(R.id.sort_by_most_expensive);
        sortByMostRecent = rootView.findViewById(R.id.sort_by_most_recent);

        applyFiltersBtn = rootView.findViewById(R.id.apply_filters_btn);

        sortByLeastExpensive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set the filter; or call the viewmodel to set the filter?
                mViewModel.sortBy.setValue(getResources().getString(R.string.sort_rooms_ascending));
            }
        });
        sortByMostExpensive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.sortBy.setValue(getResources().getString(R.string.sort_rooms_descending));
            }
        });
        sortByMostRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.sortBy.setValue("sort_most_recent");
            }
        });

//        applyFiltersBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //call the repository
//
//            }
//        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FilterRoomsViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.sortBy.observe(this, Observer -> {
            Log.i("Viewmodel", "Viewmodel ,sortby: " + mViewModel.sortBy.getValue());
        });

        applyFiltersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterRoomsRepository roomsRepository = new FilterRoomsRepository();
                roomsRepository.getFilteredRooms(mViewModel.sortBy.getValue());
            }
        });
    }

}
