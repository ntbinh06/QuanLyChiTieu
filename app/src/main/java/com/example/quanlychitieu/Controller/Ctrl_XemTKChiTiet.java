package com.example.quanlychitieu.Controller;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlychitieu.Model.M_DanhMucGiaoDich;
import com.example.quanlychitieu.Model.M_GiaoDich;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.V_ItemGiaoDich;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class Ctrl_XemTKChiTiet  extends AppCompatActivity{

    ArrayList<M_GiaoDich> mylist;
    V_ItemGiaoDich myadapter;
    RecyclerView lv;
    TextView txtTenTaiKhoan,txtLuongBD,txtLSDC,txtNgayTao,txtSoDu;
    DatabaseReference giaoDichRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemtaikhoanchitiet); // Your layout file

        lv= findViewById(R.id.recyclerView);
        lv.setLayoutManager(new LinearLayoutManager(this));
        mylist = new ArrayList<>();
        txtTenTaiKhoan = findViewById(R.id.tenTK);
        txtLSDC= findViewById(R.id.LSDC);
        txtLuongBD= findViewById(R.id.LBD);
        txtNgayTao= findViewById(R.id.NgayTao);
        txtSoDu=findViewById(R.id.sodu);

        // Lấy tham chiếu đến Firebase
        giaoDichRef = FirebaseDatabase.getInstance().getReference("GiaoDich");


        // Nhận dữ liệu từ Intent
        String idTaiKhoan = getIntent().getStringExtra("idTK");
        String tenTaiKhoan = getIntent().getStringExtra("tenTK");
        String luongbandau = getIntent().getStringExtra("luongbandau");
        String ngayTao= getIntent().getStringExtra("ngayTao");
        String LSDC= getIntent().getStringExtra("lanSuDungCuoi");
        // Gán dữ liệu cho các TextView
        txtTenTaiKhoan.setText(" " + tenTaiKhoan);
        txtLuongBD.setText("Lượng ban đầu: " + luongbandau);
        txtLSDC.setText("Lần sử dụng cuối: "+LSDC);
        txtNgayTao.setText("Ngày tạo: " + ngayTao);
        // Tải dữ liệu giao dịch
        loadGiaoDich(idTaiKhoan, luongbandau);

        ImageButton ic_back = findViewById(R.id.ic_back);

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ctrl_XemTKChiTiet.this, Ctrl_CacTaiKhoan.class);
                startActivity(intent);
            }
        });
    }
    private void loadGiaoDich(String idTaiKhoan, String LBD) {
        giaoDichRef.orderByChild("idTaiKhoan").equalTo(idTaiKhoan).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mylist.clear();
                Double tong = 0.0;
                for (DataSnapshot data : snapshot.getChildren()) {
                    M_GiaoDich giaoDich = data.getValue(M_GiaoDich.class);
                    mylist.add(giaoDich);
                    tong=tong+giaoDich.getGiaTri();
                }
                Double sodu = Double.parseDouble(LBD) - tong;
                txtSoDu.setText(" "+sodu);
                V_ItemGiaoDich adapter = new V_ItemGiaoDich(Ctrl_XemTKChiTiet.this,mylist);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi
            }
        });
    }
}
