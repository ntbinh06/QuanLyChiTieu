package com.example.quanlychitieu.Controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychitieu.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Ctrl_ChuyenTien extends AppCompatActivity {
    private Spinner spnfrom;
    private Spinner spnto;
    private ImageView btnCalender;
    private TextView txtDateMonthYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chuyen_tien);
        spnfrom = findViewById(R.id.spin_bank_from);
        List<String> list = new ArrayList<>();
        list.add("Ví");
        list.add("Tài khoản ngân hàng");
        list.add("Trả trước");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnfrom.setAdapter(adapter);

        spnfrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Ctrl_ChuyenTien.this, spnfrom.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle case where nothing is selected if needed
            }
        });

        spnto = findViewById(R.id.spin_bank_to);
        List<String> list1 = new ArrayList<>();
        list1.add("Ví");
        list1.add("Tài khoản ngân hàng");
        list1.add("Trả trước");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnto.setAdapter(adapter);

        spnto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Ctrl_ChuyenTien.this, spnto.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle case where nothing is selected if needed
            }
        });
        ImageButton ic_back = findViewById(R.id.ic_back);
        Button btn_save = findViewById(R.id.btn_save);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ctrl_ChuyenTien.this, Ctrl_TongQuan.class));
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ctrl_ChuyenTien.this, Ctrl_CacTaiKhoan.class));
            }
        });

        //Hop thoai chon ngay thang nam
        txtDateMonthYear = findViewById(R.id.txtDateMonthYear); // Khởi tạo TextView để hiển thị ngày tháng

        // Thiết lập sự kiện nhấn vào icon lịch
        btnCalender = findViewById(R.id.button_calendar);
        btnCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy ngày hiện tại
                Locale.setDefault(new Locale("vi", "VN"));
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Tạo hộp thoại chọn ngày
                DatePickerDialog datePickerDialog = new DatePickerDialog(Ctrl_ChuyenTien.this,
                        R.style.CustomDatePickerDialog_ChuyenTien,
                        (view1, selectedYear, selectedMonth, selectedDay) -> {
                            // Cập nhật TextView với ngày đã chọn
                            String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                            txtDateMonthYear.setText(selectedDate);
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
    }
}