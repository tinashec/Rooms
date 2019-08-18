package tinashechinyanga.zw.co.ruumz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

/**
 * Created by Tinashe on 7/26/2016.
 */

public class EditRoomDialogFragment extends DialogFragment {
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
        String roomId = bundle.getString("roomId");
        Toast.makeText(getActivity(), "RoomID, " + roomId, Toast.LENGTH_LONG).show();

        String items[] = {"Remove room", "Mark room as reserved"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update Room Status")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                //remove the room
                                removeUsersRoom(roomId);
                                Toast.makeText(getActivity(), "Room removed", Toast.LENGTH_LONG).show();
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

    private void removeUsersRoom(String objectId) {
        //remove room using supplied ID
//        ParseQuery<ParseObject> roomQuery = ParseQuery.getQuery("Room");
//        roomQuery.whereEqualTo("objectId", objectId);
//        roomQuery.getFirstInBackground(new GetCallback<ParseObject>() {
//            @Override
//            public void done(ParseObject object, ParseException e) {
//                if(object == null){
//                    //nothing returned
//                }else{
//                    //remove object
//                    try {
//                        object.delete();
//                    } catch (ParseException e1) {
//                        e1.printStackTrace();
//                    }
//                }
//                if(e == null){
//                    //an error occurred
//                }else {
//                    //no error
//                }
//            }
//        });
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstance){
        return layoutInflater.inflate(R.layout.fragment_edit_room_dialog, container);
    }


}
