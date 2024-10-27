package com.example.quanlychitieu.Controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieu.R;

public class Ctrl_NguoiDung extends AppCompatActivity {

    private Button btnDangXuat, btnXoaTaiKhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.nguoi_dung);

        ImageView ic_back = findViewById(R.id.ic_back);
        ImageView thongtin= findViewById(R.id.thongtin);
        ImageView ic_doimk= findViewById(R.id.ic_doimk);
        LinearLayout layoutThongTinCaNhan = findViewById(R.id.thongtinCN);

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Ctrl_NguoiDung.this, TongQuan.class));
            }
        });

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ctrl_NguoiDung.this, TongQuan.class));
            }
        });
        thongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ctrl_NguoiDung.this, Ctrl_ThongTinCaNhan.class));
            }
        });
        ic_doimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ctrl_NguoiDung.this, Ctrl_DoiMatKhau.class));
            }
        });

        layoutThongTinCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Ctrl_NguoiDung.this, Ctrl_ThongTinCaNhan.class));
            }
        });

        btnDangXuat = findViewById(R.id.btnDangXuat);
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo LayoutInflater để tạo view từ layout tùy chỉnh
                LayoutInflater inflater = LayoutInflater.from(Ctrl_NguoiDung.this);
                View dialogView = inflater.inflate(R.layout.custom_dialog_dangxuat, null);

                // Khởi tạo AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(Ctrl_NguoiDung.this);
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

        btnXoaTaiKhoan = findViewById(R.id.btn_xoataikhoan);
        btnXoaTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo LayoutInflater để tạo view từ layout tùy chỉnh
                LayoutInflater inflater = LayoutInflater.from(Ctrl_NguoiDung.this);
                View dialogView = inflater.inflate(R.layout.custom_dialog_xoataikhoan, null);

                // Khởi tạo AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(Ctrl_NguoiDung.this);
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void navigateToLoginScreen() {
        startActivity(new Intent(Ctrl_NguoiDung.this, TrangChuDNDK.class));

    }
}