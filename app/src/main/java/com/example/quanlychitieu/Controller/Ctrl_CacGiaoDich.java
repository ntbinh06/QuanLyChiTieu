package com.example.quanlychitieu.Controller;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlychitieu.Model.M_DanhMucHangMuc;
import com.example.quanlychitieu.Model.M_GiaoDich;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.V_ItemGiaoDich;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Ctrl_CacGiaoDich extends AppCompatActivity {

    private FirebaseFirestore db;
    private List<M_GiaoDich> giaoDichList;
    private V_ItemGiaoDich myAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cac_giao_dich);

        // Kết nối Firestore
        db = FirebaseFirestore.getInstance();

        // Khởi tạo RecyclerView và Adapter
        recyclerView = findViewById(R.id.recyclerviewGD);
        giaoDichList = new ArrayList<>();
        myAdapter = new V_ItemGiaoDich(this, giaoDichList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("GiaoDich");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    M_GiaoDich giaoDich = dataSnapshot.getValue(M_GiaoDich.class);
                    giaoDichList.add(giaoDich);
                }

                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Ctrl_CacGiaoDich.this, "Get list faild", Toast.LENGTH_SHORT).show();
            }
        });

        // Nút quay lại
        ImageButton ic_back = findViewById(R.id.ic_back);
        ic_back.setOnClickListener(view -> finish());


    }


}


