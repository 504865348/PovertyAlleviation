package com.example.jayny.povertyalleviation;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A fragment representing a single group detail screen.
 * This fragment is either contained in a {@link groupListActivity}
 * in two-pane mode (on tablets) or a {@link groupDetailActivity}
 * on handsets.
 */
public class groupDetailFragment extends Fragment {
    public groupDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(getArguments().getString("name"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.group_detail, container, false);
        LinearLayout temp = (LinearLayout) rootView.findViewById(R.id.activess);
        temp.setVisibility(View.INVISIBLE);
        // Show the dummy content as text in a TextView.
        ((TextView) rootView.findViewById(R.id.name)).append(getArguments().getString("name"));
        ((TextView) rootView.findViewById(R.id.master)).append(getArguments().getString("master"));
        ((TextView) rootView.findViewById(R.id.phone)).append(getArguments().getString("phone"));

        return rootView;
    }
}
