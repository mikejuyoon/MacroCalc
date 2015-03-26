package com.michaelyoon.macrocalcfinal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Michael on 10/12/2014.
 */
public class Fragment2 extends Fragment {

    MyActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_two_view, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState){
    	super.onActivityCreated(savedInstanceState);

        parentActivity = (MyActivity)getActivity();

        // Loads saved data from SharedPreferences
        parentActivity.loadDataViewTwo();
        // Sets up Keyboard onTouch Listener
        parentActivity.setupViewTwo();
        parentActivity.hideKeyboardViewTwo();
    }
}

