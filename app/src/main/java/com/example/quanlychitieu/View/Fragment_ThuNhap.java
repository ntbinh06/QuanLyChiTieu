package com.example.quanlychitieu.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.quanlychitieu.Model.M_DanhMucHangMuc;
import com.example.quanlychitieu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_ThuNhap extends Fragment {

    private ListView lv;
    private ArrayList<M_DanhMucHangMuc> danhMuc;
    private V_ItemHangMuc myAdapter;

    private DatabaseReference hangMucRef; // Tham chiếu Firebase

    public Fragment_ThuNhap() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout cho Fragment
        View view = inflater.inflate(R.layout.fragment__thu_nhap, container, false);

        // Khởi tạo ListView
        lv = view.findViewById(R.id.listView_thunhap);
        danhMuc = new ArrayList<>();

        // Khởi tạo Firebase reference
        hangMucRef = FirebaseDatabase.getInstance().getReference("HangMuc");

        // Lấy dữ liệu từ Firebase
        loadHangMucFromFirebase();

        // Khởi tạo Adapter
        myAdapter = new V_ItemHangMuc(getActivity(), R.layout.list_item, danhMuc, getChildFragmentManager());
        lv.setAdapter(myAdapter);

        // Xử lý Insets cho toàn màn hình
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        return view;
    }

    private void loadHangMucFromFirebase() {
        // Lấy UID của người dùng hiện tại
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Lấy dữ liệu từ Firebase, lọc theo userId và idNhom
        hangMucRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                danhMuc.clear(); // Xóa dữ liệu cũ
                for (DataSnapshot data : snapshot.getChildren()) {
                    M_DanhMucHangMuc hangMuc = data.getValue(M_DanhMucHangMuc.class);
                    if (hangMuc != null && "1".equals(hangMuc.getIdNhom())&& userId.equals(hangMuc.getUserId())) { // Kiểm tra idNhom
                        danhMuc.add(hangMuc);
                    }
                }
                myAdapter.notifyDataSetChanged(); // Cập nhật ListView
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Firebase", "Lỗi khi lấy dữ liệu: " + error.getMessage());
            }
        });
    }
}
