package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieu.Model.DanhMucGiaoDich;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.View_ItemGiaoDich;

import java.util.ArrayList;

public class CacGiaoDich extends AppCompatActivity {
    int image[]= {R.drawable.food, R.drawable.family, R.drawable.shopping,R.drawable.wage};
    String tenGD[]={"Đồ ăn","Mua sắm","Gia đình", "Lương"};
    String tenTK[]={"Ví", "Tài khoản ngân hàng","Thẻ trả trước"};
    String tien[]={"20.000","400.000","29.000"};
    String ngay[]={"22/02/2024","16/02/2024","01/01/2024"};

    ArrayList<DanhMucGiaoDich> mylist;
    View_ItemGiaoDich myadapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cac_giao_dich);
        lv= findViewById(R.id.listview);
        mylist = new ArrayList<>();
        for(int i=0;i<tenGD.length;i++){
            int index = i % tenTK.length;
            mylist.add(new DanhMucGiaoDich(image[i], tenGD[i], tenTK[index], tien[index], ngay[index]));
        }
        myadapter = new View_ItemGiaoDich(CacGiaoDich.this,R.layout.list_item_cacdd,mylist);
        lv.setAdapter(myadapter);
        ImageButton ic_back = findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CacGiaoDich.this, TongQuan.class));
            }
        });
    }
}