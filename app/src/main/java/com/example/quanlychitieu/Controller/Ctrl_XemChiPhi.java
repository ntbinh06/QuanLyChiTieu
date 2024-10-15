package com.example.quanlychitieu.Controller;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.quanlychitieu.R;

import java.util.ArrayList;
public class Ctrl_XemChiPhi  extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xemchiphi); // Your layout file


        ImageButton ic_back = findViewById(R.id.ic_back);
        Button buttonsua = findViewById(R.id.btnSua);

        ImageView imgHinhGD = findViewById(R.id.xemCTHinhGD);
        TextView txtTenGD = findViewById(R.id.xemCTTenGD);
        TextView txtGiaGD = findViewById(R.id.xemCTGiaGD);
        TextView txtTKGD = findViewById(R.id.xemCTTKGD);
        TextView txtNgayGD = findViewById(R.id.xemCTNgayGD);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int hinhGD = extras.getInt("hinhGD");
            String tenGD = extras.getString("tenGD");
            String giaGD = extras.getString("tien");
            String tenTK = extras.getString("tenTK");
            String ngayGD = extras.getString("ngay");

            // Gán dữ liệu cho các View
            imgHinhGD.setImageResource(hinhGD);
            txtTenGD.setText(tenGD);
            txtGiaGD.setText(giaGD);
            txtTKGD.setText(tenTK);
            txtNgayGD.setText(ngayGD);
        }


        buttonsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ctrl_XemChiPhi.this, Ctrl_ThemThuNhap.class);
                startActivity(intent);
            }
        });

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ctrl_XemChiPhi.this, CacGiaoDich.class);
                startActivity(intent);
            }
        });
    }
}
