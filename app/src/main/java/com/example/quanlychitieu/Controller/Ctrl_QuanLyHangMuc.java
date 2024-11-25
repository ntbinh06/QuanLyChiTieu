package com.example.quanlychitieu.Controller;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.Fragment_ChiPhi;
import com.example.quanlychitieu.View.Fragment_Them_Hang_Muc;
import com.example.quanlychitieu.View.Fragment_ThuNhap;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;

public class Ctrl_QuanLyHangMuc extends AppCompatActivity {
    FrameLayout framelayout;
    TabLayout tablayout;
    ImageView addHangMuc, iconback;
    private DatabaseReference nhomHangMucRef, hangMucRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlyhangmuc);

        // Khởi tạo tham chiếu Firebase
        nhomHangMucRef = FirebaseDatabase.getInstance().getReference("NhomHangMuc");
        hangMucRef = FirebaseDatabase.getInstance().getReference("HangMuc");

//        addNhomHangMucToFirebase("1", "Nhóm Thu Nhập");
//        addNhomHangMucToFirebase("2", "Nhóm Chi Phí");
//
//        addHangMucToFirebase("1", "Tien Dien","hthp/...jpg",0.0,"2","DSEZCk7wpuPyegmLbv66Tn6HjCz1");
//        addHangMucToFirebase("2","Giai tri","/.jpg2",1.0,"2","DSEZCk7wpuPyegmLbv66Tn6HjCz1");
//        addHangMucToFirebase("3","Luong","/.jpg3",4.0,"1","DSEZCk7wpuPyegmLbv66Tn6HjCz1");
//        addHangMucToFirebase("4", "An Uong", "/an_uong.jpg", 2.0, "1", "DSEZCk7wpuPyegmLbv66Tn6HjCz1");
//        addHangMucToFirebase("5", "Du Lich", "/du_lich.jpg", 3.5, "2", "DSEZCk7wpuPyegmLbv66Tn6HjCz1");
//        addHangMucToFirebase("6", "Y Te", "/y_te.jpg", 1.2, "2", "DSEZCk7wpuPyegmLbv66Tn6HjCz1");
//        addHangMucToFirebase("7", "Giao Duc", "/giao_duc.jpg", 0.8, "1", "DSEZCk7wpuPyegmLbv66Tn6HjCz1");
//        addHangMucToFirebase("8", "Di Lai", "/di_lai.jpg", 1.0, "2", "DSEZCk7wpuPyegmLbv66Tn6HjCz1");
//

        // Ánh xạ các view
        framelayout = findViewById(R.id.framelayout);
        tablayout = findViewById(R.id.tablayout);
        addHangMuc = findViewById(R.id.add_HangMuc);
        iconback = findViewById(R.id.ic_back);// Ánh xạ ImageView
//        addInitialHangMuc();

        // Thêm sự kiện click vào ImageView để mở DialogFragment
        addHangMuc.setOnClickListener(v -> openDialogFragment());
        ImageView ic_back = findViewById(R.id.ic_back);

        ic_back.setOnClickListener(v -> {
            Intent intent = new Intent(Ctrl_QuanLyHangMuc.this, Ctrl_TongQuan.class);
            startActivity(intent);
        });

        if (savedInstanceState == null) {
            displayFragment(new Fragment_ThuNhap());
        }

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new Fragment_ThuNhap(); // Fragment cho tab Thu Nhập
                        break;
                    case 1:
                        fragment = new Fragment_ChiPhi(); // Fragment cho tab Chi Phí
                        break;
                }
                if (fragment != null) {
                    displayFragment(fragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        iconback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ctrl_QuanLyHangMuc.this, Ctrl_TongQuan.class));
            }
        });


    }

    // Phương thức để thay thế và hiển thị Fragment vào FrameLayout
    private void displayFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }
    // Phương thức để mở DialogFragment
    private void openDialogFragment() {
        Fragment_Them_Hang_Muc dialogFragment = new Fragment_Them_Hang_Muc();
        dialogFragment.show(getSupportFragmentManager(), "ThemHangMuc");
    }
    // thêm dữ liệu mới
    private void addHangMucToFirebase(String idHangMuc, String tenHangMuc, String anhHangMuc,Double nganSachDuTru, String idNhom, String idUser ) {
        // Tạo một đối tượng HashMap để lưu dữ liệu
        HashMap<String, Object> data = new HashMap<>();
        data.put("idHangmuc", idHangMuc);
        data.put("tenHangmuc", tenHangMuc);
        data.put("anhHangmuc", anhHangMuc);
        data.put("nganSachDuTru", nganSachDuTru != null ? nganSachDuTru : 0.0); // double được hỗ trợ
        data.put("idNhom", idNhom);
        data.put("idUser", idUser);

        // Thêm dữ liệu vào Firebase
        hangMucRef.child(idHangMuc).setValue(data).addOnSuccessListener(aVoid -> {
            // Hiển thị thông báo thành công
            Toast.makeText(this, "Them hang muc thanh cong!", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            // Hiển thị thông báo lỗi
            Toast.makeText(this, "Them hang muc that bai: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
    private void addNhomHangMucToFirebase(String idNhom, String tenNhom) {
        // Kiểm tra dữ liệu đầu vào (đảm bảo không null hoặc rỗng)
        if (idNhom == null || tenNhom == null || idNhom.isEmpty() || tenNhom.isEmpty()) {
            Toast.makeText(this, "ID nhóm và tên nhóm không được để trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo một đối tượng HashMap để lưu dữ liệu
        HashMap<String, String> data = new HashMap<>();
        data.put("idNhom", idNhom);
        data.put("tenNhom", tenNhom);

        // Thêm dữ liệu vào Firebase
        nhomHangMucRef.child(idNhom).setValue(data)
                .addOnSuccessListener(aVoid -> {
                    // Hiển thị thông báo thành công
                    Log.d("Firebase", "Them nhom hang muc thanh cong voi ID: " + idNhom);
                    Toast.makeText(this, "Them nhom hang muc thanh cong!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Hiển thị thông báo lỗi
                    Log.e("Firebase", "Loi khi them nhom hang muc: " + e.getMessage(), e);
                    Toast.makeText(this, "Them nhom hang muc that bai: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
//    //tạo dữ liệu ban đầu
//    private void addInitialHangMuc() {
//        // Tạo dữ liệu mẫu
//        String idHangMuc = "hm001";
//        String tenHangMuc = "Điện nước";
//        String anhHangMuc = "https://example.com/image.jpg";
//        String idNhom = "2";
//
//        // Tạo đối tượng dữ liệu
//        HashMap<String, String> hangMuc = new HashMap<>();
//        hangMuc.put("idHangmuc", idHangMuc);
//        hangMuc.put("tenHangmuc", tenHangMuc);
//        hangMuc.put("anhHangmuc", anhHangMuc);
//        hangMuc.put("idNhom", idNhom);
//
//        Log.d("Firebase", "Dữ liệu: " + hangMuc.toString());
//        // Thêm vào Firebase
//        DatabaseReference hangMucRef = FirebaseDatabase.getInstance().getReference("HangMuc");
//        hangMucRef.child(idHangMuc).setValue(hangMuc)
//                .addOnSuccessListener(aVoid -> {
//                    Log.d("Firebase", "Them du lieu thanh cong!");
//                    Toast.makeText(this, "Tao hang muc thanh cong!", Toast.LENGTH_SHORT).show();
//                })
//                .addOnFailureListener(e -> {
//                    Log.e("Firebase", "Loi them du lieu: ", e);
//                    Toast.makeText(this, "Loi khi tao hang muc: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                });
//    }
//

}
