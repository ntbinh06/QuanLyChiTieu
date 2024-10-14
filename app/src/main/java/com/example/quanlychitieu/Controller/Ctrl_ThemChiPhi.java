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

import com.example.quanlychitieu.Model.Model_HangMucChiPhi;
import com.example.quanlychitieu.R;

import java.util.ArrayList;
import java.util.List;

public class Ctrl_ThemChiPhi extends AppCompatActivity {
    private Spinner spnchiphi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themchiphi); // Your layout file

        spnchiphi = findViewById(R.id.spnchiphi);
        List<String> list = new ArrayList<>();
        list.add("Ví");
        list.add("Tài khoản ngân hàng");
        list.add("Trả trước");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnchiphi.setAdapter(adapter);

        spnchiphi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Ctrl_ThemChiPhi.this, spnchiphi.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle case where nothing is selected if needed
            }
        });

        ImageButton hangmuc = findViewById(R.id.hangmuc);
        ImageButton ic_back = findViewById(R.id.ic_back);
        Button buttonhuy = findViewById(R.id.buttonhuy);
        Button buttonluu = findViewById(R.id.buttonluu);

        hangmuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedbackDialog(Gravity.BOTTOM);
            }
        });

        buttonhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ctrl_ThemChiPhi.this, TongQuan.class));
            }
        });

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ctrl_ThemChiPhi.this, TongQuan.class));
            }
        });

        buttonluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ctrl_ThemChiPhi.this, TongQuan.class));
            }
        });
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
        arrContact.add(new Model_HangMucChiPhi(R.drawable.baseline_account_circle_24, "Đồ ăn/ Đồ uống"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.baseline_account_circle_24, "Mua sắm"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.baseline_account_circle_24, "Vận chuyển"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.baseline_account_circle_24, "Giải trí"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.baseline_account_circle_24, "Nhà cửa"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.baseline_account_circle_24, "Gia đình"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.baseline_account_circle_24, "Sức khỏe"));

        Ctrl_HangMucChiPhi customAdapter = new Ctrl_HangMucChiPhi(this, R.layout.list_item, arrContact);
        listView.setAdapter(customAdapter);

        dialog.setCancelable(Gravity.BOTTOM == gravity);
        dialog.show(); // Show the dialog
    }
}