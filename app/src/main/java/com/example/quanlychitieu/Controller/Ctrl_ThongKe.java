package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.V_ThongKeItem;

public class Ctrl_ThongKe extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke); // Kiểm tra layout

        ImageView ic_back = findViewById(R.id.ic_back);
        RecyclerView recyclerView = findViewById(R.id.listviewsodo);
        V_ThongKeItem adapter = new V_ThongKeItem(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

// Tải dữ liệu từ Firebase
        adapter.loadData();

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ctrl_ThongKe.this, Ctrl_TongQuan.class));
            }
        });
    }
}