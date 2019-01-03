package com.example.mkk.pmb;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class InfoKampusFragment extends Fragment {
    RelativeLayout view;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view = (RelativeLayout) inflater.inflate(R.layout.activity_info_kampus, container, false);
        getActivity().setTitle("Info Kampus");



        return view;
    }
    @Override
    public void onResume() {
        super.onResume();

    }
}
