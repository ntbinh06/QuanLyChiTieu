package com.example.quanlychitieu.Controller;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
