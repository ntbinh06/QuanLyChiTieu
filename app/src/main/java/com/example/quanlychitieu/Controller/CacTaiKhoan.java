package com.example.quanlychitieu.Controller;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieu.Model.DanhMucTaiKhoan;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.View_ItemCacTK;

import java.util.ArrayList;

public class CacTaiKhoan extends AppCompatActivity {
    String tenTK[]={"Ví", "Tài khoản ngân hàng", "Tài khoản trả trước"};
    String tien[]={"400.000","750.000","2.000.000"};

    ArrayList<DanhMucTaiKhoan> taikhoan;
    View_ItemCacTK Item;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cac_tai_khoan);
        lv= findViewById(R.id.listView1);
        taikhoan= new ArrayList<>();
        for (int i=0;i<tenTK.length;i++){
            int index= i % tenTK.length;
            taikhoan.add(new DanhMucTaiKhoan(tenTK[i], tien[index]));
        }
        Item = new View_ItemCacTK(CacTaiKhoan.this, R.layout.list_item_taikhoan, taikhoan);
        lv.setAdapter(Item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}