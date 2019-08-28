package tinashechinyanga.zw.co.ruumz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

/**
 * Created by Tinashe on 7/26/2016.
 */

public class EditRoomDialogFragment extends DialogFragment {
    private Object ParseObject;
    private ParseObject parseObject;

    public EditRoomDialogFragment(){

    }

    public static EditRoomDialogFragment newInstance(){
        EditRoomDialogFragment fragment = new EditRoomDialogFragment();
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //get the roomId
        Bundle bundle = getArguments();
        ParseObject selectedRoom = bundle.getParcelable("selectedRoom");
        Log.i("Bundle Parcel", "Bundle parcel arguments, " + bundle.getParcelable("selectedRoom"));

        String[] items = {"Remove room", "Mark room as reserved"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update Room Status")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                //remove the room
                                removeUsersRoom(selectedRoom);
                                Toast.makeText(getActivity(), "Room removed, " + (selectedRoom != null ? selectedRoom.getParseObject("Room").getString("roomSurburb") : null), Toast.LENGTH_LONG).show();
                                break;
                            case 1:
                                //mark room as reserved
                                Toast.makeText(getActivity(), "Room reserved", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                //leave room as is
                                break;
                        }
                    }
                });
        return builder.create();
    }

    private void removeUsersRoom(ParseObject objectId) {
        //remove room using supplied ID
        Log.i("ParseObject ID: ", "Parse Object ID, " + objectId.getObjectId());

        objectId.deleteInBackground(new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    //success, dismiss loading icon, remove from view
                    //getActivity().finish();
                }else {
                    //error
                    Toast.makeText(getActivity(), "Error, " + e.getMessage(), Toast.LENGTH_LONG).show();
                    //try again

                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstance){
        return layoutInflater.inflate(R.layout.fragment_edit_room_dialog, container);
    }


}
