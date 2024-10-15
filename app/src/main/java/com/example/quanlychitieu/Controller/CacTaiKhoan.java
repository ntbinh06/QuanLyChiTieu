package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlychitieu.Model.DanhMucTaiKhoan;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.View_ItemCacTK;

import java.util.ArrayList;

public class CacTaiKhoan extends AppCompatActivity {

    String[] tenTK = {"Ví", "Tài khoản ngân hàng", "Tài khoản trả trước"};
    String[] tien = {"400.000", "750.000", "2.000.000"};

    ArrayList<DanhMucTaiKhoan> taikhoan;
    View_ItemCacTK item;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cac_tai_khoan);

        // Khởi tạo ListView và adapter
        lv = findViewById(R.id.listView1);
        taikhoan = new ArrayList<>();
        for (int i = 0; i < tenTK.length; i++) {
            taikhoan.add(new DanhMucTaiKhoan(tenTK[i], tien[i]));
        }
        item = new View_ItemCacTK(this, R.layout.list_item_taikhoan, taikhoan);
        lv.setAdapter(item);

        // Xử lý sự kiện cho các nút
        ImageView ic_back = findViewById(R.id.ic_back);
        ImageView ic_add = findViewById(R.id.ic_add);

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CacTaiKhoan.this, TongQuan.class));
            }
        });

        ic_add.setOnClickListener(v -> {
            // Mở Fragment để thêm tài khoản
            openFragment(new ThemTaiKhoan());
        });

        // Áp dụng insets cho layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Hàm mở Fragment
    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment); // Đảm bảo ID này khớp với ID trong layout
        transaction.addToBackStack(null); // Cho phép quay lại màn hình trước
        transaction.commit();
    }
}