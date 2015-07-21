package com.ifenduo.coach.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifenduo.coach.App;
import com.ifenduo.coach.activity.BaseActivity;

public abstract class BaseFragment extends Fragment {
	private View view;
	protected App app;
	protected ProgressDialog dialog;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(setLayout(), container, false);
		app = (App) getActivity().getApplication();
		this.view = view;
		initViews();
		dialog = ((BaseActivity) getActivity()).getDialog();
		return view;
	}

	public abstract int setLayout();
	public abstract void initViews();
	public View findViewById(int id) {
		return view.findViewById(id);
	}
}
