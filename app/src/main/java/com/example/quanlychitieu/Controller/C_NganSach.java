package com.example.quanlychitieu.Controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlychitieu.Model.M_NganSach;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.View_NganSach;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import me.tankery.lib.circularseekbar.CircularSeekBar;

public class C_NganSach extends AppCompatActivity {

    private RecyclerView rvNganSach;
    private Button btnTaoNganSach;
    private TextView currentMonthText;
    private ImageView prevMonthButton, nextMonthButton;

    private int currentMonthOffset = 0;
    private int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ngan_sach);

        rvNganSach = findViewById(R.id.lvNganSach);
        ArrayList<M_NganSach> arrContact = new ArrayList<>();
        M_NganSach contact1 = new M_NganSach("Mua sắm", "Còn lại", 2000000, 1700000, R.drawable.shopping_cart, 70);
        M_NganSach contact2 = new M_NganSach("Ăn uống", "Còn lại", 1000000, 500000, R.drawable.shopping_cart, 50);

        arrContact.add(contact1);
        arrContact.add(contact2);

        // Sử dụng LinearLayoutManager để hiển thị danh sách theo chiều dọc
        rvNganSach.setLayoutManager(new LinearLayoutManager(this));

        // Gán Adapter cho RecyclerView
        View_NganSach customAdapter = new View_NganSach(this, arrContact);
        rvNganSach.setAdapter(customAdapter);

        // Khi người dùng nhấn vào item để xem chi tiết
        rvNganSach.addOnItemTouchListener(new RecyclerViewItemClickListener(this, rvNganSach, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                M_NganSach nganSach = arrContact.get(position);

                // Tạo Intent để truyền dữ liệu sang ChiTietNganSach
                Intent intent = new Intent(C_NganSach.this, ChiTietNganSach.class);
                intent.putExtra("transactionId", position);  // Ví dụ truyền vị trí làm transactionId
                intent.putExtra("tenHangMuc", nganSach.getTenHangMuc());
                intent.putExtra("soTien", nganSach.getTongSoTien());
                intent.putExtra("soTienConLai", nganSach.getSoTienConLai());
                intent.putExtra("hinhAnh", nganSach.getHinhAnh());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // Xử lý nếu cần thiết
            }
        }));

        // Xử lý insets nếu cần
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Nút tạo ngân sách mới
        btnTaoNganSach = findViewById(R.id.btnTaoNgansach);
        btnTaoNganSach.setOnClickListener(view -> {
            Intent intent = new Intent(C_NganSach.this, NganSachMoi.class);
            startActivity(intent);
        });

        ImageButton iconBack = (ImageButton) findViewById(R.id.back_ngansach);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(C_NganSach.this, TongQuan.class);
                startActivity(intent);
            }
        });

        //Nut dieu huong den thang truoc va thang toi
        currentMonthText = findViewById(R.id.currentMonthText);
        prevMonthButton = findViewById(R.id.prevMonthButton);
        nextMonthButton = findViewById(R.id.nextMonthButton);

        updateMonthText();


        // Thiết lập sự kiện nhấn vào TextView để mở DatePickerDialog
        currentMonthText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMonthYearPicker();
            }
        });

        prevMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMonthOffset--;
                updateMonthText();
            }
        });

        nextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMonthOffset++;
                updateMonthText();
            }
        });

        //Arc Progress Bar


        CircularSeekBar circularSeekBar = findViewById(R.id.arcProgressBar);
        circularSeekBar.setMax(200);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pos <= 200){

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
                    // Cập nhật currentMonthOffset dựa vào tháng được chọn
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, 1);

                    // Tính currentMonthOffset dựa trên sự khác biệt giữa tháng hiện tại và tháng được chọn
                    Calendar currentDate = Calendar.getInstance();
                    currentMonthOffset = (year - currentDate.get(Calendar.YEAR)) * 12 + (month - currentDate.get(Calendar.MONTH));

                    // Cập nhật TextView với tháng và năm được chọn
                    updateMonthText();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Ẩn ngày để chỉ chọn tháng và năm
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
