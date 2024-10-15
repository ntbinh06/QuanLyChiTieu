package com.example.quanlychitieu.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.quanlychitieu.Controller.Ctrl_DoiMatKhau;
import com.example.quanlychitieu.Controller.Ctrl_NguoiDung;
import com.example.quanlychitieu.Controller.Ctrl_ThongTinCaNhan;
import com.example.quanlychitieu.Controller.TongQuan;
import com.example.quanlychitieu.R;


public class Fragment_NguoiDung extends Fragment {

    public Fragment_NguoiDung() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout cho Fragment
        View view = inflater.inflate(R.layout.nguoi_dung, container, false);

        ImageView ic_back = view.findViewById(R.id.ic_back);
        ImageView thongtin= view.findViewById(R.id.thongtin);
        ImageView ic_doimk= view.findViewById(R.id.ic_doimk);

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Fragment_TongQuan.class));
            }
        });
        thongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ctrl_ThongTinCaNhan.class));
            }
        });
        ic_doimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ctrl_DoiMatKhau.class));
            }
        });

        return view;
    }
}