package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychitieu.R;

public class Ctrl_ThemHangMuc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.themhangmuc);
        ImageButton ic_back = findViewById(R.id.ic_back);

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ctrl_ThemHangMuc.this, View_QuanLyHangMuc.class);
                startActivity(intent);
            }
        });
    }
}