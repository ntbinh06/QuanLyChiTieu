package com.example.quanlychitieu.Controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlychitieu.Model.M_DanhMucHangMuc;
import com.example.quanlychitieu.Model.M_GiaoDich;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.V_NganSach;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import me.tankery.lib.circularseekbar.CircularSeekBar;

public class Ctrl_NganSach extends AppCompatActivity {
    private RecyclerView rvNganSach;
    private Button btnTaoNganSach;
    private TextView currentMonthText, tong, totalrest, totaldachi, dayrest;
    private ImageView prevMonthButton, nextMonthButton;
    private CircularSeekBar arcProgressBar;

    private int currentMonthOffset = 0;

    // Firebase references
    private DatabaseReference databaseDanhMuc;
    private DatabaseReference databaseGiaoDich;

    private ArrayList<M_DanhMucHangMuc> danhMucList = new ArrayList<>();
    private ArrayList<M_GiaoDich> giaoDichList = new ArrayList<>();

    private double soTienConLai = 0.0; // Biến để lưu số tiền còn lại
    private double soTien = 0.0; // Biến để lưu số tiền dự trù

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngan_sach);

        // Khởi tạo TextView
        tong = findViewById(R.id.tong);
        totalrest = findViewById(R.id.totalrest);
        totaldachi = findViewById(R.id.totaldachi);
        arcProgressBar = findViewById(R.id.arcProgressBar);
        dayrest = findViewById(R.id.dayrest);
        rvNganSach = findViewById(R.id.lvNganSach);
        rvNganSach.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo Firebase references
        databaseDanhMuc = FirebaseDatabase.getInstance().getReference("HangMuc");
        databaseGiaoDich = FirebaseDatabase.getInstance().getReference("GiaoDich");

        loadData(); // Gọi loadData để tải danh mục ngay khi bắt đầu

        btnTaoNganSach = findViewById(R.id.btnTaoNgansach);
        btnTaoNganSach.setOnClickListener(view -> {
            Intent intent = new Intent(Ctrl_NganSach.this, Ctrl_NganSachMoi.class);
            startActivity(intent);
        });

        ImageButton iconBack = findViewById(R.id.back_ngansach);
        iconBack.setOnClickListener(view -> {
            Intent intent = new Intent(Ctrl_NganSach.this, Ctrl_TongQuan.class);
            startActivity(intent);
        });

        // Xử lý sự kiện click cho RecyclerView
        rvNganSach.addOnItemTouchListener(new Ctrl_RecyclerViewItemClickListener(this, rvNganSach, new Ctrl_RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                M_DanhMucHangMuc nganSach = danhMucList.get(position);
                V_NganSach adapter = (V_NganSach) rvNganSach.getAdapter();
                double soTienConLai = adapter.getSoTienConLai(position);

                Intent intent = new Intent(Ctrl_NganSach.this, Ctrl_ChiTietNganSach.class);
                intent.putExtra("transactionId", nganSach.getIdHangmuc());
                intent.putExtra("tenHangMuc", nganSach.getTenHangmuc());
                intent.putExtra("soTien", nganSach.getNganSachDuTru());
                intent.putExtra("soTienConLai", soTienConLai);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // Xử lý nếu cần thiết
            }
        }));

        currentMonthText = findViewById(R.id.currentMonthText);
        prevMonthButton = findViewById(R.id.prevMonthButton);
        nextMonthButton = findViewById(R.id.nextMonthButton);

        updateMonthText();

        currentMonthText.setOnClickListener(v -> showMonthYearPicker());
        prevMonthButton.setOnClickListener(v -> {
            currentMonthOffset--;
            updateMonthText();
        });

        nextMonthButton.setOnClickListener(v -> {
            currentMonthOffset++;
            updateMonthText();
        });
    }

    private void updateTong() {
        double totalNganSachDuTru = 0.0;
        for (M_DanhMucHangMuc danhMuc : danhMucList) {
            if (danhMuc.getNganSachDuTru() != null) {
                totalNganSachDuTru += danhMuc.getNganSachDuTru();
            }
        }

        // Hiển thị tổng vào TextView
        tong.setText(String.format("%.2f M", totalNganSachDuTru));
    }

    private void loadData() {
        databaseDanhMuc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                danhMucList.clear();
                soTien = 0.0; // Đặt lại số tiền mỗi lần tải dữ liệu mới

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    M_DanhMucHangMuc danhMuc = snapshot.getValue(M_DanhMucHangMuc.class);
                    if (danhMuc != null && danhMuc.getNganSachDuTru() != null) {
                        danhMucList.add(danhMuc);
                        soTien += danhMuc.getNganSachDuTru();
                    }
                }

                loadGiaoDich(); // Gọi loadGiaoDich sau khi đã tải danh mục
                updateTong(); // Cập nhật tổng ngay sau khi tải danh mục
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu cần
            }
        });
    }

    private void loadGiaoDich() {
        databaseGiaoDich.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                giaoDichList.clear();
                double totalSpent = 0.0;

                // Lấy tháng và năm hiện tại từ currentMonthText
                Calendar currentCalendar = Calendar.getInstance();
                currentCalendar.add(Calendar.MONTH, currentMonthOffset);
                SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy", new Locale("vi", "VN"));
                String monthYear = sdf.format(currentCalendar.getTime());

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    M_GiaoDich giaoDich = snapshot.getValue(M_GiaoDich.class);
                    if (giaoDich != null) {
                        String ngayTao = giaoDich.getFormattedNgayTao();
                        if (ngayTao != null && ngayTao.endsWith(monthYear)) {
                            totalSpent += giaoDich.getGiaTri();
                        }
                        giaoDichList.add(giaoDich);
                    }
                }

                soTienConLai = soTien - totalSpent;

                double totalDachi = 0.0;
                for (M_DanhMucHangMuc danhMuc : danhMucList) {
                    if (danhMuc.getNganSachDuTru() != null) {
                        String idHangMuc = danhMuc.getIdHangmuc();
                        for (M_GiaoDich giaoDich : giaoDichList) {
                            if (giaoDich.getIdHangMuc() != null && giaoDich.getIdHangMuc().equals(idHangMuc)) {
                                totalDachi += giaoDich.getGiaTri();
                            }
                        }
                    }
                }

                totaldachi.setText(String.format("%.2f M", totalSpent));

                double remainingAmount = soTien - totalDachi;
                totalrest.setText(String.format("%.2f M", remainingAmount));

                // Cập nhật CircularSeekBar
                arcProgressBar.setMax((int) soTien); // Giá trị tối đa là số tiền dự trù
                arcProgressBar.setProgress((int) totalDachi); // Thiết lập giá trị hiện tại

                int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
                int totalDaysInMonth = currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                int daysLeft = totalDaysInMonth - currentDay;
                dayrest.setText(String.format("%d ngày", daysLeft));

                V_NganSach customAdapter = new V_NganSach(Ctrl_NganSach.this, danhMucList, giaoDichList);
                // Cập nhật adapter cho RecyclerView
                rvNganSach.setAdapter(customAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu cần
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
            String monthText = sdf.format(calendar.getTime());
            currentMonthText.setText(monthText);
        }

        // Gọi loadData để tải lại danh mục và kiểm tra giao dịch
        loadData();
    }

    private void showMonthYearPicker() {
        Locale.setDefault(new Locale("vi", "VN"));
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                R.style.CustomDatePickerDialog,
                (view, year, month, dayOfMonth) -> {
                    currentMonthOffset = (year - Calendar.getInstance().get(Calendar.YEAR)) * 12 + (month - Calendar.getInstance().get(Calendar.MONTH));
                    updateMonthText();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Ẩn spinner ngày
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