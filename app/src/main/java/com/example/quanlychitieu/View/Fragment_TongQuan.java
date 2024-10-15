package com.example.quanlychitieu.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlychitieu.Controller.Ctrl_NguoiDung;
import com.example.quanlychitieu.Controller.Ctrl_ThongTinCaNhan;
import com.example.quanlychitieu.R;

public class Fragment_TongQuan extends Fragment {

    private ImageView btnMenu;

    public Fragment_TongQuan() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout cho Fragment
        View view = inflater.inflate(R.layout.activity_tong_quan, container, false);

        btnMenu = view.findViewById(R.id.btnMenu);

        // Thiết lập sự kiện click cho btnMenu
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khởi chạy Activity V_SideMenu
                startActivity(new Intent(getActivity(), V_SideMenu.class));
            }
        });


        return view;
    }
}
