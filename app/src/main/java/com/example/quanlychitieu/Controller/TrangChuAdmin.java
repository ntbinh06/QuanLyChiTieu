package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieu.R;


public class TrangChuAdmin extends AppCompatActivity {
    private LinearLayout ln;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_chu_admin);

        ln=findViewById(R.id.cardOverview);
        //set sự kiện click vào tong quan
        ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mở TongQuanActivity khi nhấn vào LinearLayout cardOverview
                Intent intent = new Intent(TrangChuAdmin.this, TongQuan.class);
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