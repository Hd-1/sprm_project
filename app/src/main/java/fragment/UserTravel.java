package fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sprm_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ui.AddTravelActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserTravel#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserTravel extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserTravel() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserTravel.
     */
    // TODO: Rename and change types and number of parameters
    public static UserTravel newInstance(String param1, String param2) {
        UserTravel fragment = new UserTravel();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // initialize floating action button
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View currentFragment = inflater.inflate(R.layout.fragment_user_travel, container, false);

        // Initialize the floating action button
        FloatingActionButton fabAddTravel = currentFragment.findViewById(R.id.fab_add_travel);
        fabAddTravel.setOnClickListener(view ->
        {
            Intent intent = new Intent(currentFragment.getContext(), AddTravelActivity.class);
            // wait a result from the new activity
            startActivity(intent);
            //startActivityForResult(intent, NEW_TRAVEL_ACTIVITY_REQUEST_CODE);
        });

        return currentFragment;
    }
}