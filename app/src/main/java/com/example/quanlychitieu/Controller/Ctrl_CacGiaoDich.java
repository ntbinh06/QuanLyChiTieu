package com.example.quanlychitieu.Controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlychitieu.Model.M_DanhMucHangMuc;
import com.example.quanlychitieu.Model.M_GiaoDich;
import com.example.quanlychitieu.Model.M_NhomHangMuc;
import com.example.quanlychitieu.Model.M_TaiKhoan;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.V_ItemGiaoDich;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Ctrl_CacGiaoDich extends AppCompatActivity {

    private RecyclerView recyclerView;
    private V_ItemGiaoDich myAdapter;
    private List<M_GiaoDich> giaoDichList = new ArrayList<>();
    private Map<String, M_DanhMucHangMuc> hangMucMap = new HashMap<>();
    private Map<String, String> taiKhoanMap = new HashMap<>();
    private Map<String, String> nhomHangMucMap = new HashMap<>();
    private ImageButton btnBack;
    private TextView txtTong;
    private TextView currentMonthText;
    private ImageView prevMonthButton, nextMonthButton;

    private int currentMonthOffset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cac_giao_dich);

        btnBack = findViewById(R.id.ic_back);
        recyclerView = findViewById(R.id.recyclerviewGD);
        currentMonthText = findViewById(R.id.TextTime);
        prevMonthButton = findViewById(R.id.backDate);
        nextMonthButton = findViewById(R.id.nextDate);

        // Cài đặt RecyclerView
        // Cập nhật khi khởi tạo adapter
        myAdapter = new V_ItemGiaoDich(this, giaoDichList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

        recyclerView.addOnItemTouchListener(new Ctrl_RecyclerViewItemClickListener(this, recyclerView, new Ctrl_RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

                // Lấy thông tin giao dịch tại vị trí được nhấp
                dbRef.child("GiaoDich").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int currentIndex = 0;
                        for (DataSnapshot giaoDichSnap : dataSnapshot.getChildren()) {
                            if (currentIndex == position) {
                                String idHangMuc = giaoDichSnap.child("idHangMuc").getValue(String.class);

                                // Truy vấn `HangMuc` để lấy `idNhom`
                                dbRef.child("HangMuc").child(idHangMuc).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot hangMucSnap) {
                                        String idNhom = hangMucSnap.child("idNhom").getValue(String.class);

                                        // Điều hướng dựa trên idNhom
                                        Intent intent;
                                        if ("1".equals(idNhom)) {
                                            intent = new Intent(view.getContext(), Ctrl_XemThuNhap.class);
                                        } else {
                                            intent = new Intent(view.getContext(), Ctrl_XemChiPhi.class);
                                        }

                                        // Truyền ID giao dịch và ID nhóm qua Intent
                                        intent.putExtra("idGiaoDich", giaoDichSnap.getKey());
                                        intent.putExtra("idNhom", idNhom);
                                        view.getContext().startActivity(intent);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.w("Firebase", "loadHangMuc:onCancelled", databaseError.toException());
                                    }
                                });
                                break;
                            }
                            currentIndex++;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("Firebase", "loadGiaoDich:onCancelled", databaseError.toException());
                    }
                });
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // Xử lý khi click dài (nếu cần thiết)
            }
        }));



        // Tải dữ liệu
        loadHangMuc();
        loadTaiKhoan();

        // Điều hướng tháng
        updateMonthText();

        prevMonthButton.setOnClickListener(v -> {
            currentMonthOffset--;
            updateMonthText();
            loadGiaoDich();
        });

        nextMonthButton.setOnClickListener(v -> {
            currentMonthOffset++;
            updateMonthText();
            loadGiaoDich();
        });

        currentMonthText.setOnClickListener(v -> showMonthYearPicker());

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(Ctrl_CacGiaoDich.this, Ctrl_TongQuan.class);
            startActivity(intent);
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
                        hangMucMap.put(hangMuc.getIdHangmuc(), hangMuc);  // Lưu đối tượng M_DanhMucHangMuc vào map
                    }
                }
                loadGiaoDich();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Ctrl_CacGiaoDich.this, "Không thể tải dữ liệu hạng mục", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadTaiKhoan() {
        DatabaseReference taiKhoanRef = FirebaseDatabase.getInstance().getReference("TaiKhoan");
        taiKhoanRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taiKhoanMap.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    M_TaiKhoan taiKhoan = dataSnapshot.getValue(M_TaiKhoan.class);
                    if (taiKhoan != null) {
                        taiKhoanMap.put(taiKhoan.getIdTaiKhoan(), taiKhoan.getTenTaiKhoan());
                    }
                }
                loadGiaoDich();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Ctrl_CacGiaoDich.this, "Không thể tải dữ liệu tài khoản", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadGiaoDich() {
        DatabaseReference giaoDichRef = FirebaseDatabase.getInstance().getReference("GiaoDich");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, currentMonthOffset);
        int selectedYear = calendar.get(Calendar.YEAR);
        int selectedMonth = calendar.get(Calendar.MONTH);

        giaoDichRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                giaoDichList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    M_GiaoDich giaoDich = dataSnapshot.getValue(M_GiaoDich.class);
                    if (giaoDich != null) {
                        try {
                            Date giaoDichDate = giaoDich.getNgayTao();
                            Calendar giaoDichCal = Calendar.getInstance();
                            giaoDichCal.setTime(giaoDichDate);

                            if (giaoDichCal.get(Calendar.YEAR) == selectedYear &&
                                    giaoDichCal.get(Calendar.MONTH) == selectedMonth) {
                                // Lấy tên hạng mục
                                M_DanhMucHangMuc hangMuc = hangMucMap.get(giaoDich.getIdHangMuc());
                                String tenHangMuc = (hangMuc != null) ? hangMuc.getTenHangmuc() : "Không xác định";

                                // Lấy tên tài khoản
                                String tenTaiKhoan = taiKhoanMap.get(giaoDich.getIdTaiKhoan());
                                if (tenTaiKhoan == null) {
                                    tenTaiKhoan = "Không xác định";
                                }

                                // Gán tên vào giao dịch
                                giaoDich.setIdHangMuc(tenHangMuc);
                                giaoDich.setIdTaiKhoan(tenTaiKhoan);

                                giaoDichList.add(giaoDich);
                            }
                        } catch (NumberFormatException e) {
                            Toast.makeText(Ctrl_CacGiaoDich.this, "Định dạng ngày không hợp lệ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Ctrl_CacGiaoDich.this, "Không thể tải dữ liệu giao dịch", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void updateMonthText() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, currentMonthOffset);
        if (currentMonthOffset == 0) {
            currentMonthText.setText("Tháng này");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", new Locale("vi", "VN"));
            currentMonthText.setText(sdf.format(calendar.getTime()));
        }
    }

    private void showMonthYearPicker() {
        Locale.setDefault(new Locale("vi", "VN"));
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                R.style.CustomDatePickerDialog,
                (view, year, month, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, 1);
                    Calendar currentDate = Calendar.getInstance();
                    currentMonthOffset = (year - currentDate.get(Calendar.YEAR)) * 12 + (month - currentDate.get(Calendar.MONTH));
                    updateMonthText();
                    loadGiaoDich();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        try {
            Field dayPicker = datePickerDialog.getDatePicker().getClass().getDeclaredField("mDaySpinner");
            dayPicker.setAccessible(true);
            View dayView = (View) dayPicker.get(datePickerDialog.getDatePicker());
            dayView.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        datePickerDialog.show();
    }

}
