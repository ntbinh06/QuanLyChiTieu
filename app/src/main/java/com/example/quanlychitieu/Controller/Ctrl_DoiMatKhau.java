package com.example.quanlychitieu.Controller;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.Fragment_NguoiDung;

public class Ctrl_DoiMatKhau extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doi_mat_khau); // Your layout file


        ImageButton ic_back = findViewById(R.id.ic_back);
        Button btn_save = findViewById(R.id.btn_save);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ctrl_DoiMatKhau.this, Fragment_NguoiDung.class));
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ctrl_DoiMatKhau.this, Fragment_NguoiDung.class));
            }
        });

    }
}
