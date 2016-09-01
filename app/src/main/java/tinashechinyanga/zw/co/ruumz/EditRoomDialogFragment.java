package tinashechinyanga.zw.co.ruumz;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MUZ0007 on 7/26/2016.
 */

public class EditRoomDialogFragment extends DialogFragment {
    public EditRoomDialogFragment(){

    }

    public static EditRoomDialogFragment newInstance(){
        EditRoomDialogFragment fragment = new EditRoomDialogFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstance){
        return layoutInflater.inflate(R.layout.fragment_edit_room_dialog, container);
    }


}
