package com.example.quanlychitieu.Controller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieu.Model.Model_HangMucChiPhi;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.View_CustomSpinner;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NganSachMoi extends AppCompatActivity implements View_CustomSpinner.OnSpinnerEventsListener{

    private View_CustomSpinner spnTaiKhoan, spnDateStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ngan_sach_moi);

        // Initialize Spinner for Tai Khoan
        spnTaiKhoan = findViewById(R.id.spnTaiKhoan);
        spnTaiKhoan.setSpinnerEventsListener(this);

        // List for Tai Khoan Spinner
        ArrayList<String> listTaiKhoan = new ArrayList<>();
        listTaiKhoan.add("Ví");
        listTaiKhoan.add("MB Bank");
        listTaiKhoan.add("Tiết kiệm");

        // Adapter for Tai Khoan Spinner
        ArrayAdapter<String> adapterTaiKhoan = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listTaiKhoan);
        adapterTaiKhoan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTaiKhoan.setAdapter(adapterTaiKhoan);

        spnTaiKhoan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(NganSachMoi.this, spnTaiKhoan.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        spnTaiKhoan.setDropDownVerticalOffset(150);

        // Initialize Spinner for Date Start


        // Window insets handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton ic_back = findViewById(R.id.ic_back);
        Button btn_save = findViewById(R.id.btn_save);
        ImageButton hangmuc = findViewById(R.id.hangmuc);
        hangmuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedbackDialog(Gravity.BOTTOM);
            }
        });
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NganSachMoi.this, C_NganSach.class));
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NganSachMoi.this, C_NganSach.class));
            }
        });

        //Ngay bat dau
        TextView ngaybatdau = findViewById(R.id.ngaybatdau);
        TextView ngayketthuc = findViewById(R.id.ngayketthuc);

        ngaybatdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy ngày hiện tại
                Locale.setDefault(new Locale("vi", "VN"));
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Tạo hộp thoại chọn ngày
                DatePickerDialog datePickerDialog = new DatePickerDialog(NganSachMoi.this,
                        R.style.CustomDatePickerDialog,
                        (view1, selectedYear, selectedMonth, selectedDay) -> {
                            // Cập nhật TextView với ngày đã chọn
                            String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                            ngaybatdau.setText(selectedDate);
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        ngayketthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy ngày hiện tại
                Locale.setDefault(new Locale("vi", "VN"));
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Tạo hộp thoại chọn ngày
                DatePickerDialog datePickerDialog = new DatePickerDialog(NganSachMoi.this,
                        R.style.CustomDatePickerDialog_ChuyenTien,
                        (view1, selectedYear, selectedMonth, selectedDay) -> {
                            // Cập nhật TextView với ngày đã chọn
                            String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                            ngayketthuc.setText(selectedDate);
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

    }

    @Override
    public void onPopupWindowOpened(Spinner spinner) {
        spnTaiKhoan.setBackground(getResources().getDrawable(R.drawable.bg_spinner_up, null));
    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {
        spnTaiKhoan.setBackground(getResources().getDrawable(R.drawable.bg_spinner, null));
    }
    private void openFeedbackDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.hangmucchiphi); // Use a new layout for the dialog
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        // Set up ListView in the dialog
        ListView listView = dialog.findViewById(R.id.listView_chiphi);

        ArrayList<Model_HangMucChiPhi> arrContact = new ArrayList<>();
        arrContact.add(new Model_HangMucChiPhi(R.drawable.food, "Đồ ăn/ Đồ uống"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.shopping, "Mua sắm"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.family, "Vận chuyển"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.delivery_van, "Giải trí"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.drum_set, "Nhà cửa"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.house, "Gia đình"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.healthcare, "Sức khỏe"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.pets, "Thú cưng"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.travel_luggage, "Du lịch"));

        Ctrl_HangMucChiPhi customAdapter = new Ctrl_HangMucChiPhi(this, R.layout.list_item, arrContact);
        listView.setAdapter(customAdapter);

        dialog.setCancelable(Gravity.BOTTOM == gravity);
        dialog.show(); // Show the dialog
    }

}
