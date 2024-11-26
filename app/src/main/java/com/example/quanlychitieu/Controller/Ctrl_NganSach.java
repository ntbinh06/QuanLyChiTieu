package com.example.quanlychitieu.Controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
    private TextView currentMonthText;
    private ImageView prevMonthButton, nextMonthButton;

    private int currentMonthOffset = 0;
    private int pos = 0;

    // Firebase references
    private DatabaseReference databaseDanhMuc;
    private DatabaseReference databaseGiaoDich;

    private ArrayList<M_DanhMucHangMuc> danhMucList = new ArrayList<>();
    private ArrayList<M_GiaoDich> giaoDichList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngan_sach);

        rvNganSach = findViewById(R.id.lvNganSach);

        // Khởi tạo Firebase references
        databaseDanhMuc = FirebaseDatabase.getInstance().getReference("HangMuc");
        databaseGiaoDich = FirebaseDatabase.getInstance().getReference("GiaoDich");

        // Sử dụng LinearLayoutManager để hiển thị danh sách theo chiều dọc
        rvNganSach.setLayoutManager(new LinearLayoutManager(this));

        // Gán Adapter cho RecyclerView
        loadData();

        // Xử lý sự kiện cho các nút khác
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
        // Khi người dùng nhấn vào item để xem chi tiết
        rvNganSach.addOnItemTouchListener(new Ctrl_RecyclerViewItemClickListener(this, rvNganSach, new Ctrl_RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                M_DanhMucHangMuc nganSach = danhMucList.get(position);

                // Lấy adapter để gọi phương thức getSoTienConLai
                V_NganSach adapter = (V_NganSach) rvNganSach.getAdapter();
                double soTienConLai = adapter.getSoTienConLai(position);

                // Tạo Intent để truyền dữ liệu sang Ctrl_ChiTietNganSach
                Intent intent = new Intent(Ctrl_NganSach.this, Ctrl_ChiTietNganSach.class);
                intent.putExtra("transactionId", nganSach.getIdHangmuc()); // Giữ nguyên kiểu dữ liệu
                intent.putExtra("tenHangMuc", nganSach.getTenHangmuc());
                intent.putExtra("soTien", nganSach.getNganSachDuTru()); // Không cần chuyển đổi sang double nếu đã là double
                intent.putExtra("soTienConLai", soTienConLai);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // Xử lý nếu cần thiết
            }
        }));
        // Thiết lập các nút điều hướng tháng
        currentMonthText = findViewById(R.id.currentMonthText);
        prevMonthButton = findViewById(R.id.prevMonthButton);
        nextMonthButton = findViewById(R.id.nextMonthButton);

        updateMonthText();

        // Sự kiện cho DatePickerDialog
        currentMonthText.setOnClickListener(v -> showMonthYearPicker());
        prevMonthButton.setOnClickListener(v -> {
            currentMonthOffset--;
            updateMonthText();
        });

        nextMonthButton.setOnClickListener(v -> {
            currentMonthOffset++;
            updateMonthText();
        });

        // Arc Progress Bar
        CircularSeekBar circularSeekBar = findViewById(R.id.arcProgressBar);
        circularSeekBar.setMax(200);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pos <= 200) {
                    circularSeekBar.setProgress(pos);
                    if (pos == 200) {
                        int redColor = getResources().getColor(R.color.red_2, null);
                        circularSeekBar.setCircleProgressColor(redColor); // Đổi màu thành đỏ
                    }
                    pos++;
                    handler.postDelayed(this, 200);
                }
            }
        }, 200);
    }

    private void loadData() {
        // Lấy danh mục từ Firebase
        databaseDanhMuc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                danhMucList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    M_DanhMucHangMuc danhMuc = snapshot.getValue(M_DanhMucHangMuc.class);
                    // Kiểm tra và chỉ thêm vào danhMucList nếu nganSachDuTru khác null
                    if (danhMuc != null && danhMuc.getNganSachDuTru() != null) {
                        danhMucList.add(danhMuc);
                    }
                }
                loadGiaoDich(); // Chỉ gọi loadGiaoDich nếu đã có danh mục
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu cần
            }
        });

    }

    private void loadGiaoDich() {
        // Lấy giao dịch từ Firebase
        databaseGiaoDich.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                giaoDichList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    M_GiaoDich giaoDich = snapshot.getValue(M_GiaoDich.class);
                    if (giaoDich != null) {
                        giaoDichList.add(giaoDich);
                    }
                }
                // Gán adapter cho RecyclerView sau khi tải xong dữ liệu
                V_NganSach customAdapter = new V_NganSach(Ctrl_NganSach.this, danhMucList, giaoDichList);
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
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", new Locale("Vi", "VN"));
            String monthText = sdf.format(calendar.getTime());
            currentMonthText.setText(monthText);
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