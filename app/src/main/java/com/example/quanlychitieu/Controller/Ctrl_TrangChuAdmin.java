package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.V_DanhSachUser;


public class Ctrl_TrangChuAdmin extends AppCompatActivity {
    private LinearLayout ln;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_chu_admin);

        ImageView ic_list = findViewById(R.id.ic_list);
        ImageView ic_tongquan = findViewById(R.id.ic_tongquan);
        ic_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ctrl_TrangChuAdmin.this, V_DanhSachUser.class));
            }
        });

        ic_tongquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ctrl_TrangChuAdmin.this, Ctrl_TongQuan.class);
                startActivity(intent);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}