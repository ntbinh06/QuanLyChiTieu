package com.example.quanlychitieu.Controller;
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
import android.widget.ImageButton;
import android.app.Dialog;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychitieu.Model.Model_HangMucThuNhap;
import com.example.quanlychitieu.R;

import java.util.ArrayList;
import java.util.List;

public class Ctrl_ThemThuNhap extends AppCompatActivity {
    private Spinner spnthunhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themthunhap); // Your layout file

        spnthunhap = findViewById(R.id.spnthunhap);
        List<String> list = new ArrayList<>();
        list.add("Ví");
        list.add("Tài khoản ngân hàng");
        list.add("Trả trước");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnthunhap.setAdapter(adapter);

        spnthunhap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Ctrl_ThemThuNhap.this, spnthunhap.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle case where nothing is selected if needed
            }
        });

        ImageButton hangmuc = findViewById(R.id.hangmuc);
        ImageButton ic_back = findViewById(R.id.ic_back);
        Button buttonhuy = findViewById(R.id.btnHuy);
        Button buttonluu = findViewById(R.id.btnLuu);

        hangmuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedbackDialog(Gravity.BOTTOM);
            }
        });

        buttonhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ctrl_ThemThuNhap.this, TongQuan.class);
                startActivity(intent);
            }
        });

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ctrl_ThemThuNhap.this, TongQuan.class);
                startActivity(intent);
            }
        });

        buttonluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ctrl_ThemThuNhap.this, TongQuan.class);
                startActivity(intent);
            }
        });
    }

    private void openFeedbackDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.hangmucthunhap); // Sử dụng layout mới cho dialog
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        // Thiết lập ListView trong dialog
        ListView listView = findViewById(R.id.listView_thunhap);

        // Tạo danh sách chi phí
        ArrayList<Model_HangMucThuNhap> arrContact = new ArrayList<>();
        arrContact.add(new Model_HangMucThuNhap(R.drawable.baseline_account_circle_24, "Thu nhập tài chính"));
        arrContact.add(new Model_HangMucThuNhap(R.drawable.baseline_account_circle_24, "Lương"));
        arrContact.add(new Model_HangMucThuNhap(R.drawable.baseline_account_circle_24, "Tiền từ việc vặt"));
        arrContact.add(new Model_HangMucThuNhap(R.drawable.baseline_account_circle_24, "Tiền trợ cấp"));
        arrContact.add(new Model_HangMucThuNhap(R.drawable.baseline_account_circle_24, "Tiền tiết kiệm"));

        // Khởi tạo adapter và gán cho ListView
        Ctrl_HangMucThuNhap customAdapter = new Ctrl_HangMucThuNhap(this, R.layout.list_item, arrContact);
        listView.setAdapter(customAdapter);

        dialog.setCancelable(Gravity.BOTTOM == gravity);
        dialog.show(); // Hiển thị dialog
    }
}
