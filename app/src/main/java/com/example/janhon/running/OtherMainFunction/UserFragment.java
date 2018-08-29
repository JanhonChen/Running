package com.example.janhon.running.OtherMainFunction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.janhon.running.R;

public class UserFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.btSubmit);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, //container傳入
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false); //inflater 載入器,載入layout.fragment_score
        //handleViews(view);
        return view;
    }
}
