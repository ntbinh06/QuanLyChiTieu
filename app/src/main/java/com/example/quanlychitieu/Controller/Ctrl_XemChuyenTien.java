package com.example.quanlychitieu.Controller;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.quanlychitieu.R;

import java.util.ArrayList;
public class Ctrl_XemChuyenTien extends AppCompatActivity {
    private int transactionId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xemchuyentien); // Your layout file

        ImageButton ic_back = findViewById(R.id.ic_back);
        Button buttonsua = findViewById(R.id.btnSua);
        Button buttonquay = findViewById(R.id.btnquaylai);

        // Liên kết các View với mã Java

        TextView txtgiatri = findViewById(R.id.giatri);
        TextView txttu = findViewById(R.id.tu);
        TextView txtden = findViewById(R.id.den);
        TextView txtngay = findViewById(R.id.ngay);
        TextView txtghichu = findViewById(R.id.ghichu);

        // Nhận dữ liệu từ Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String giatri = extras.getString("giatri");
            String tu = extras.getString("tu");
            String den = extras.getString("den");
            String ngay = extras.getString("ngay");
            String ghichu = extras.getString("ghichu");

            // Gán dữ liệu cho các View

            txtgiatri.setText(giatri);
            txttu.setText(tu);
            txtden.setText(den);
            txtngay.setText(ngay);
            txtghichu.setText(ghichu);
        }

        buttonsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ctrl_XemChuyenTien.this, ChuyenTien.class);
                startActivity(intent);
            }
        });

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ctrl_XemChuyenTien.this, TongQuan.class);
                startActivity(intent);
            }
        });
        buttonquay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ctrl_XemChuyenTien.this, TongQuan.class);
                startActivity(intent);
            }
        });


    }
}
