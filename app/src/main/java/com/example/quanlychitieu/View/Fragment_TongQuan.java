package com.example.quanlychitieu.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.quanlychitieu.Model.DanhMucHangMuc;
import com.example.quanlychitieu.R;

import java.util.ArrayList;


public class Fragment_TongQuan extends Fragment {

    public Fragment_TongQuan() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout cho Fragment
        View view = inflater.inflate(R.layout.activity_tong_quan, container, false);

        return view;
    }
}