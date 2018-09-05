package com.example.mohamedali.testfirebaseui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mohamedali.testfirebaseui.R;

/**
 * Created by Mohamed Ali on 03/09/2018.
 */


/**
 * A placeholder fragment containing a simple view.
 */
public class UsersFragment extends Fragment {

    private ListView mUsersListView;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public UsersFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static UsersFragment newInstance(int sectionNumber) {
        UsersFragment fragment = new UsersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_users, container, false);
//        TextView textView = rootView.findViewById(R.id.section_label);
//        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        mUsersListView = rootView.findViewById(R.id.lv_users);
        if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {

            // set adapter for listView and set data
        }


        return rootView;
    }
}
