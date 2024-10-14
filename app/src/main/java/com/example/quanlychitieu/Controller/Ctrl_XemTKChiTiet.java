package com.example.quanlychitieu.Controller;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieu.Model.DanhMucGiaoDich;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.View_ItemGiaoDich;

import java.util.ArrayList;
public class Ctrl_XemTKChiTiet  extends AppCompatActivity{
    int image[]= {R.drawable.food, R.drawable.family, R.drawable.shopping,R.drawable.wage};
    String tenGD[]={"Đồ ăn","Mua sắm","Gia đình", "Lương"};
    String tenTK[]={"Ví","Ví","Ví"};
    String tien[]={"20.000","400.000","29.000"};
    String ngay[]={"22/02/2024","16/02/2024","01/01/2024"};

    ArrayList<DanhMucGiaoDich> mylist;
    View_ItemGiaoDich myadapter;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xemtaikhoanchitiet); // Your layout file

        lv= findViewById(R.id.listview);
        mylist = new ArrayList<>();
        for(int i=0;i<tenGD.length;i++){
            int index = i % tenTK.length;
            mylist.add(new DanhMucGiaoDich(image[i], tenGD[i], tenTK[index], tien[index], ngay[index]));
        }
        myadapter = new View_ItemGiaoDich(Ctrl_XemTKChiTiet.this,R.layout.list_item_cacdd,mylist);
        lv.setAdapter(myadapter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.danhsachuser), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton ic_back = findViewById(R.id.ic_back);

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ctrl_XemTKChiTiet.this, CacGiaoDich.class);
                startActivity(intent);
            }
        });
    }
}
