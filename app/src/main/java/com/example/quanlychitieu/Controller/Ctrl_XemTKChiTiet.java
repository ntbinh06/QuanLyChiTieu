package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
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
import java.util.Map;

public class Ctrl_XemTKChiTiet extends AppCompatActivity {

    private ArrayList<M_GiaoDich> mylist;
    private V_ItemGiaoDich myadapter;
    private RecyclerView lv;
    private TextView txtTenTaiKhoan, txtLuongBD, txtLSDC, txtNgayTao, txtSoDu;
    private DatabaseReference giaoDichRef;
    private Map<String, String> hangMucMap = new HashMap<>();
    private Map<String, String> taiKhoanMap = new HashMap<>();// Map lưu idHangMuc -> tenHangMuc

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemtaikhoanchitiet);

        // Ánh xạ các view
        lv = findViewById(R.id.recyclerView);
        lv.setLayoutManager(new LinearLayoutManager(this));
        txtTenTaiKhoan = findViewById(R.id.tenTK);
        txtLuongBD = findViewById(R.id.LBD);
        txtLSDC = findViewById(R.id.LSDC);
        txtNgayTao = findViewById(R.id.NgayTao);
        txtSoDu = findViewById(R.id.sodu);
        ImageButton ic_back = findViewById(R.id.ic_back);

        // Lấy tham chiếu đến Firebase
        giaoDichRef = FirebaseDatabase.getInstance().getReference("GiaoDich");

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        String idTaiKhoan = intent.getStringExtra("idTK");
        String tenTaiKhoan = intent.getStringExtra("tenTK");
        String luongbandau = intent.getStringExtra("luongbandau");
        String ngayTao = intent.getStringExtra("ngayTao");
        String LSDC = intent.getStringExtra("lanSuDungCuoi");

        // Kiểm tra nếu thiếu dữ liệu từ Intent
        if (idTaiKhoan == null || luongbandau == null) {
            Toast.makeText(this, "Thiếu thông tin tài khoản hoặc lượng ban đầu", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Gán dữ liệu cho TextView
        txtTenTaiKhoan.setText(" " + (tenTaiKhoan != null ? tenTaiKhoan : "Không xác định"));
        txtLuongBD.setText("Lượng ban đầu: " + luongbandau);
        txtLSDC.setText("Lần sử dụng cuối: " + (LSDC != null ? LSDC : "Không có dữ liệu"));
        txtNgayTao.setText("Ngày tạo: " + (ngayTao != null ? ngayTao : "Không xác định"));

        // Gọi phương thức tải hạng mục và giao dịch
        loadHangMuc(idTaiKhoan, luongbandau);
        loadTaiKhoan(idTaiKhoan, luongbandau);

        // Xử lý sự kiện nút quay lại
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ctrl_XemTKChiTiet.this, Ctrl_CacTaiKhoan.class);
                startActivity(intent);
            }
        });

        lv.addOnItemTouchListener(new Ctrl_RecyclerViewItemClickListener(this, lv, new Ctrl_RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Giả sử bạn có một danh sách chứa giao dịch
                M_GiaoDich selectedGiaoDich = mylist.get(position); // Lấy giao dịch từ vị trí
                String idGiaoDich = selectedGiaoDich.getIdGiaoDich();

                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

                // Truy vấn bảng GiaoDich
                dbRef.child("GiaoDich").child(idGiaoDich).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot giaoDichSnap) {
                        if (giaoDichSnap.exists()) {
                            String idHangMuc = giaoDichSnap.child("idHangMuc").getValue(String.class);
                            long giaTri = giaoDichSnap.child("giaTri").getValue(Long.class);

                            // Truy vấn bảng HangMuc dựa trên idHangMuc
                            dbRef.child("HangMuc").child(idHangMuc).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot hangMucSnap) {
                                    if (hangMucSnap.exists()) {
                                        String idNhom = hangMucSnap.child("idNhom").getValue(String.class);
                                        String tenHangMuc = hangMucSnap.child("tenHangMuc").getValue(String.class);

                                        // Truy vấn bảng NhomHangMuc dựa trên idNhom
                                        dbRef.child("NhomHangMuc").child(idNhom).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot nhomSnap) {
                                                if (nhomSnap.exists()) {
                                                    String tenNhom = nhomSnap.child("tenNhom").getValue(String.class);

                                                    // Chuyển hướng theo idNhom
                                                    Intent intent = new Intent();
                                                    if ("1".equals(idNhom)) {
                                                        intent = new Intent(view.getContext(), Ctrl_XemThuNhap.class);
                                                    } else if ("2".equals(idNhom)) {
                                                        intent = new Intent(view.getContext(), Ctrl_XemChiPhi.class);
                                                    }

                                                    // Truyền dữ liệu giao dịch
                                                    intent.putExtra("idGiaoDich", idGiaoDich);
                                                    intent.putExtra("idHangMuc", idHangMuc);
                                                    intent.putExtra("giaTri", giaTri);
                                                    intent.putExtra("tenHangMuc", tenHangMuc);
                                                    intent.putExtra("tenNhom", tenNhom);

                                                    view.getContext().startActivity(intent);

                                                    // Ghi log thông tin
                                                    Log.d("Firebase", "Chi tiết giao dịch:");
                                                    Log.d("Firebase", "Tên nhóm: " + tenNhom);
                                                    Log.d("Firebase", "Tên hạng mục: " + tenHangMuc);
                                                    Log.d("Firebase", "Giá trị giao dịch: " + giaTri);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Log.e("Firebase", "Lỗi truy vấn NhomHangMuc: " + error.getMessage());
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.e("Firebase", "Lỗi truy vấn HangMuc: " + error.getMessage());
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Firebase", "Lỗi truy vấn GiaoDich: " + error.getMessage());
                    }
                });
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // Xử lý khi click dài (nếu cần thiết)
            }
        }));
    }

    // Phương thức tải dữ liệu hạng mục
    private void loadHangMuc(String idTaiKhoan, String luongbandau) {
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
                // Gọi loadGiaoDich sau khi tải xong hạng mục
                loadGiaoDich(idTaiKhoan, luongbandau);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Ctrl_XemTKChiTiet.this, "Không thể tải dữ liệu hạng mục", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTaiKhoan(String idTaiKhoan, String luongbandau) {
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
                loadGiaoDich(idTaiKhoan, luongbandau); // Bước 2: Sau khi có dữ liệu tài khoản, tải giao dịch
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Ctrl_XemTKChiTiet.this, "Không thể tải dữ liệu tài khoản", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Phương thức tải dữ liệu giao dịch
    private void loadGiaoDich(String idTaiKhoan, String luongbandau) {
        giaoDichRef.orderByChild("idTaiKhoan").equalTo(idTaiKhoan).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mylist = new ArrayList<>();
                double tong = 0.0;

                for (DataSnapshot data : snapshot.getChildren()) {
                    M_GiaoDich giaoDich = data.getValue(M_GiaoDich.class);
                    if (giaoDich != null) {
                        // Lấy tên hạng mục từ Map
                        String tenHangMuc = hangMucMap.get(giaoDich.getIdHangMuc());
                        String tenTaiKhoan = taiKhoanMap.get(giaoDich.getIdTaiKhoan());
                        if (tenHangMuc != null && tenTaiKhoan != null) {
                            giaoDich.setIdHangMuc(tenHangMuc); // Thay idHangMuc bằng tên hạng mục
                            giaoDich.setIdTaiKhoan(tenTaiKhoan);
                        }
                        mylist.add(giaoDich);
                        tong += giaoDich.getGiaTri();
                    }
                }

                // Tính toán số dư
                double luongBanDau = Double.parseDouble(luongbandau);
                double soDu = luongBanDau - tong;
                txtSoDu.setText(" " + soDu);

                // Cập nhật giao diện RecyclerView
                myadapter = new V_ItemGiaoDich(Ctrl_XemTKChiTiet.this, mylist);

                lv.setAdapter(myadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Ctrl_XemTKChiTiet.this, "Không thể tải giao dịch", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
