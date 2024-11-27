package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlychitieu.Model.M_DanhMucHangMuc;
import com.example.quanlychitieu.Model.M_GiaoDich;
import com.example.quanlychitieu.Model.M_TaiKhoan;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.V_ItemGiaoDich;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ctrl_CacGiaoDich extends AppCompatActivity {

    private RecyclerView recyclerView;
    private V_ItemGiaoDich myAdapter;
    private List<M_GiaoDich> giaoDichList = new ArrayList<>();
    private Map<String, String> hangMucMap = new HashMap<>(); // Map để lưu idHangMuc -> tenHangMuc
    private Map<String, String> taiKhoanMap = new HashMap<>();
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cac_giao_dich);

        btnBack = findViewById(R.id.ic_back);

        // Khởi tạo RecyclerView và Adapter
        recyclerView = findViewById(R.id.recyclerviewGD);
        giaoDichList = new ArrayList<>();
        myAdapter = new V_ItemGiaoDich(this, giaoDichList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

        loadHangMuc();
        loadTaiKhoan();// Bước 1: Lấy dữ liệu từ bảng HangMuc

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ctrl_CacGiaoDich.this, Ctrl_TongQuan.class);
                startActivity(intent);
            }
        });
    }

    private void loadHangMuc() {
        DatabaseReference hangMucRef = FirebaseDatabase.getInstance().getReference("HangMuc");

        hangMucRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hangMucMap.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    M_DanhMucHangMuc hangMuc = dataSnapshot.getValue(M_DanhMucHangMuc.class);
                    if (hangMuc != null) {
                        hangMucMap.put(hangMuc.getIdHangmuc(), hangMuc.getTenHangmuc());
                    }
                }
                loadGiaoDich(); // Bước 2: Sau khi có dữ liệu hạng mục, tải giao dịch
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Ctrl_CacGiaoDich.this, "Không thể tải dữ liệu hạng mục", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTaiKhoan() {
        DatabaseReference TaiKhoanRef = FirebaseDatabase.getInstance().getReference("TaiKhoan");

        TaiKhoanRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taiKhoanMap.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    M_TaiKhoan taiKhoan = dataSnapshot.getValue(M_TaiKhoan.class);
                    if (taiKhoan != null) {
                        taiKhoanMap.put(taiKhoan.getIdTaiKhoan(), taiKhoan.getTenTaiKhoan());
                    }
                }
                loadTaiKhoan(); // Bước 2: Sau khi có dữ liệu taif khoanr, tải giao dịch
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Ctrl_CacGiaoDich.this, "Không thể tải dữ liệu hạng mục", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadGiaoDich() {
        DatabaseReference giaoDichRef = FirebaseDatabase.getInstance().getReference("GiaoDich");

        giaoDichRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                giaoDichList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    M_GiaoDich giaoDich = dataSnapshot.getValue(M_GiaoDich.class);
                    if (giaoDich != null) {
                        // Lấy tên hạng mục từ Map
                        String tenHangMuc = hangMucMap.get(giaoDich.getIdHangMuc());
                        String tenTaiKhoan = taiKhoanMap.get(giaoDich.getIdTaiKhoan());
                        if (tenHangMuc != null && tenTaiKhoan != null) {
                            giaoDich.setIdHangMuc(tenHangMuc); // Thay idHangMuc bằng tên hạng mục
                            giaoDich.setIdTaiKhoan(tenTaiKhoan);
                        }
                        giaoDichList.add(giaoDich);
                    }
                }
                myAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Ctrl_CacGiaoDich.this, "Không thể tải dữ liệu giao dịch", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
