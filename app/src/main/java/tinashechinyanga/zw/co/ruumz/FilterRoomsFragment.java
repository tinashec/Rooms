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

public class FilterRoomsFragment extends Fragment {

    private FilterRoomsViewModel mViewModel;

    private RadioButton sortByLeastExpensive;
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

        sortByLeastExpensive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set the filter; or call the viewmodel to set the filter?
                mViewModel.sortBy.setValue("ascending");
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
    }

}
