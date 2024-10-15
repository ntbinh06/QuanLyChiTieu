package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychitieu.R;

public class Ctrl_NguoiDung extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nguoi_dung); // Your layout file


        ImageView ic_back = findViewById(R.id.ic_back);
        ImageView thongtin= findViewById(R.id.thongtin);
        ImageView ic_doimk= findViewById(R.id.ic_doimk);
        Button btnDangXuat= findViewById(R.id.btnDangXuat);
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
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ctrl_NguoiDung.this, TrangChuDNDK.class));
            }
        });
    }
}
