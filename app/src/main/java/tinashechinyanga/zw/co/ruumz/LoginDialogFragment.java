package tinashechinyanga.zw.co.ruumz;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginDialogFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginDialogFragment newInstance(String param1, String param2) {
        LoginDialogFragment fragment = new LoginDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Login");
        alertDialogBuilder.setMessage("You must be logged in to continue");
        alertDialogBuilder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //on success
                navToLogin();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //on cancel
                dialog.dismiss();
            }
        });

        return alertDialogBuilder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_dialog, container, false);
    }


    private void navToLogin() {
        Intent intentLogin = new Intent(getContext(), LoginActivity.class);
        //intentLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intentLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentLogin);
    }
}
