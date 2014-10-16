package com.michaelyoon.macrocalcfinal;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Michael on 10/12/2014.
 */
public class Fragment1 extends Fragment{

	MyActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    	ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_one_view, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState){
    	super.onActivityCreated(savedInstanceState);

        parentActivity = (MyActivity)getActivity();

		parentActivity.setupViewOne();
        parentActivity.hideKeyboardViewOne();
    }
}
