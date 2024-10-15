package com.example.quanlychitieu.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.quanlychitieu.Controller.Ctrl_DoiMatKhau;
import com.example.quanlychitieu.Controller.Ctrl_ThongTinCaNhan;
import com.example.quanlychitieu.Controller.TongQuan;
import com.example.quanlychitieu.Controller.TrangChuDNDK;
import com.example.quanlychitieu.R;


public class Fragment_NguoiDung extends Fragment {

    private Button btnDangXuat;

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
                startActivity(new Intent(getActivity(), TongQuan.class));
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

        btnDangXuat = view.findViewById(R.id.btnDangXuat);
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị hộp thoại xác nhận
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có chắc chắn muốn đăng xuất?")
                        .setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Xử lý đăng xuất và điều hướng về trang đăng nhập
                                navigateToLoginScreen();
                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Đóng hộp thoại, không làm gì cả
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return view;
    }

    private void navigateToLoginScreen() {
         startActivity(new Intent(getActivity(), TrangChuDNDK.class));

    }
}