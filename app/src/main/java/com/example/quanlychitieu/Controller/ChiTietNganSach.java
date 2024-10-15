package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieu.R;


public class ChiTietNganSach extends AppCompatActivity {

    private TextView tvTenHangMuc, tvSoTien, tvSoTienConLai;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chi_tiet_ngan_sach);


        ImageButton ic_back = findViewById(R.id.ic_back);
        Button btnEdit = findViewById(R.id.btnEdit);

        image= findViewById(R.id.imgCategory);
        tvTenHangMuc = findViewById(R.id.tvCategoryName);
        tvSoTien = findViewById(R.id.tvAmount);
        tvSoTienConLai = findViewById(R.id.tvRemaining);

        // Nhận dữ liệu từ Intent
        String tenHangMuc = getIntent().getStringExtra("tenHangMuc");
        int soTien = getIntent().getIntExtra("soTien", 0);
        int soTienConLai = getIntent().getIntExtra("soTienConLai", 0);
        int imgHangMuc = getIntent().getIntExtra("imgHangMuc", R.drawable.family);

        // Hiển thị dữ liệu
        tvTenHangMuc.setText(tenHangMuc);
        tvSoTien.setText(" " + soTien + " VND");
        tvSoTienConLai.setText(" " + soTienConLai + " VND");
        image.setImageResource(imgHangMuc);  // Hiển thị ảnh


        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChiTietNganSach.this, C_NganSach.class));
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChiTietNganSach.this, NganSachMoi.class));
            }
        });
    }
}