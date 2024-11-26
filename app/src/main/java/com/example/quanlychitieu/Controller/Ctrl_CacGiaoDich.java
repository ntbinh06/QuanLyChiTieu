package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlychitieu.Model.M_GiaoDich;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.V_ItemGiaoDich;
import android.util.Log;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Ctrl_CacGiaoDich extends AppCompatActivity {
    private FirebaseFirestore db;
    private List<M_GiaoDich> giaoDichList;
    private V_ItemGiaoDich myAdapter;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cac_giao_dich);

        // Khởi tạo Firestore
        db = FirebaseFirestore.getInstance();

        // Khởi tạo RecyclerView và Adapter
        rv = findViewById(R.id.recyclerviewGD); // Đảm bảo ID khớp với layout XML
        giaoDichList = new ArrayList<>();

        myAdapter = new V_ItemGiaoDich(Ctrl_CacGiaoDich.this, giaoDichList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(myAdapter);

        // Lấy dữ liệu từ Firestore
        loadGiaoDichFromFirestore();

        // Nút quay về
        ImageButton ic_back = findViewById(R.id.ic_back);
        ic_back.setOnClickListener(v -> startActivity(new Intent(Ctrl_CacGiaoDich.this, Ctrl_TongQuan.class)));
    }

    private void loadGiaoDichFromFirestore() {
        db.collection("GiaoDich")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Log.e("Ctrl_CacGiaoDich", "Lỗi khi lấy dữ liệu từ Firestore: " + e.getMessage());
                        return;
                    }

                    if (queryDocumentSnapshots != null) {
                        giaoDichList.clear(); // Xóa danh sách cũ
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            M_GiaoDich giaoDich = document.toObject(M_GiaoDich.class);
                            giaoDichList.add(giaoDich);
                            Log.d("GiaoDich", "Đã thêm giao dịch: " + giaoDich.getIdHangMuc());
                        }
                        myAdapter.notifyDataSetChanged(); // Cập nhật adapter để hiển thị dữ liệu mới
                    }
                });
    }
}