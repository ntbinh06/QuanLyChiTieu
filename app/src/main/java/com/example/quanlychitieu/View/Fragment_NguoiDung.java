package com.example.quanlychitieu.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.quanlychitieu.Controller.Ctrl_DoiMatKhau;
import com.example.quanlychitieu.Controller.Ctrl_ThongTinCaNhan;
import com.example.quanlychitieu.Controller.Ctrl_TongQuan;
import com.example.quanlychitieu.Controller.Ctrl_TrangChuDNDK;
import com.example.quanlychitieu.R;


public class Fragment_NguoiDung extends Fragment {

    private Button btnDangXuat, btnXoaTaiKhoan;

    public Fragment_NguoiDung() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout cho Fragment
        View view = inflater.inflate(R.layout.activity_nguoi_dung, container, false);

        ImageView ic_back = view.findViewById(R.id.ic_back);
        ImageView thongtin= view.findViewById(R.id.thongtin);
        ImageView ic_doimk= view.findViewById(R.id.ic_doimk);
        LinearLayout layoutThongTinCaNhan = view.findViewById(R.id.thongtinCN);

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ctrl_TongQuan.class));
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

        layoutThongTinCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Ctrl_ThongTinCaNhan.class));
            }
        });

        btnDangXuat = view.findViewById(R.id.btnDangXuat);
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo LayoutInflater để tạo view từ layout tùy chỉnh
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View dialogView = inflater.inflate(R.layout.custom_dialog_dangxuat, null);

                // Khởi tạo AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(dialogView); // Set layout tùy chỉnh

                // Khởi tạo các thành phần trong layout tùy chỉnh
                TextView tvMessage = dialogView.findViewById(R.id.tvMessage);
                Button btnLogout = dialogView.findViewById(R.id.btnLogout);
                Button btnCancel = dialogView.findViewById(R.id.btnCancel);

                // Tạo AlertDialog
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_dialog); // Đặt nền cho dialog

                // Xử lý sự kiện cho nút Đăng xuất
                btnLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Xử lý đăng xuất và điều hướng về trang đăng nhập
                        navigateToLoginScreen();
                        dialog.dismiss(); // Đóng dialog sau khi thực hiện đăng xuất
                    }
                });

                // Xử lý sự kiện cho nút Hủy
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Đóng hộp thoại, không làm gì cả
                        dialog.dismiss();
                    }
                });

                // Hiển thị AlertDialog
                dialog.show();
            }
        });

        btnXoaTaiKhoan = view.findViewById(R.id.btn_xoataikhoan);
        btnXoaTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo LayoutInflater để tạo view từ layout tùy chỉnh
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View dialogView = inflater.inflate(R.layout.custom_dialog_xoataikhoan, null);

                // Khởi tạo AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(dialogView); // Set layout tùy chỉnh

                // Khởi tạo các thành phần trong layout tùy chỉnh
                TextView tvMessage = dialogView.findViewById(R.id.tvMessage);
                Button btnDelete = dialogView.findViewById(R.id.btnDelete);
                Button btnCancel = dialogView.findViewById(R.id.btnCancel);

                // Tạo AlertDialog
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_dialog); // Đặt nền cho dialog

                // Xử lý sự kiện cho nút Đăng xuất
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Xử lý đăng xuất và điều hướng về trang đăng nhập
                        navigateToLoginScreen();
                        dialog.dismiss(); // Đóng dialog sau khi thực hiện đăng xuất
                    }
                });

                // Xử lý sự kiện cho nút Hủy
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Đóng hộp thoại, không làm gì cả
                        dialog.dismiss();
                    }
                });

                // Hiển thị AlertDialog
                dialog.show();
            }
        });



        return view;
    }

    private void navigateToLoginScreen() {
        startActivity(new Intent(getActivity(), Ctrl_TrangChuDNDK.class));

    }
}