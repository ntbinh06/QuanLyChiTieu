package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlychitieu.Model.M_NganSach;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.Fragment_TongQuan;
import com.example.quanlychitieu.View.View_NganSach;

import java.util.ArrayList;

public class C_NganSach extends AppCompatActivity {

    private RecyclerView rvNganSach;
    private Button btnTaoNganSach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ngan_sach);

        rvNganSach = findViewById(R.id.lvNganSach);
        ArrayList<M_NganSach> arrContact = new ArrayList<>();
        M_NganSach contact1 = new M_NganSach("Mua sắm", "Còn lại", 2000000, 1700000, R.drawable.shopping_cart, 70);
        M_NganSach contact2 = new M_NganSach("Ăn uống", "Còn lại", 1000000, 500000, R.drawable.shopping_cart, 50);
        M_NganSach contact3 = new M_NganSach("Mua sắm", "Còn lại", 2000000, 1700000, R.drawable.shopping_cart, 70);
        M_NganSach contact4 = new M_NganSach("Ăn uống", "Còn lại", 1000000, 500000, R.drawable.shopping_cart, 50);

        arrContact.add(contact1);
        arrContact.add(contact2);
        arrContact.add(contact3);
        arrContact.add(contact4);

        // Sử dụng LinearLayoutManager để hiển thị danh sách theo chiều dọc
        rvNganSach.setLayoutManager(new LinearLayoutManager(this));

        // Gán Adapter cho RecyclerView
        View_NganSach customAdapter = new View_NganSach(this, arrContact);
        rvNganSach.setAdapter(customAdapter);


        ImageButton back =  findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(C_NganSach.this, Fragment_TongQuan.class);
                startActivity(intent);
            }
        });

        btnTaoNganSach = (Button) findViewById(R.id.btnTaoNgansach);
        btnTaoNganSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(C_NganSach.this, NganSachMoi.class);
                startActivity(intent);
            }
        });

        // Xử lý insets nếu cần
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
