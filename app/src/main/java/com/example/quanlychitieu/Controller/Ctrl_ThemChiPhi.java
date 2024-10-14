package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.app.Dialog;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychitieu.Model.Model_HangMucChiPhi;
import com.example.quanlychitieu.R;

import java.util.ArrayList;

public class Ctrl_ThemChiPhi extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themchiphi); // Your layout file

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
                Intent intent = new Intent(Ctrl_ThemChiPhi.this, TongQuan.class);
                startActivity(intent);
            }
        });

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ctrl_ThemChiPhi.this, TongQuan.class);
                startActivity(intent);
            }
        });

        buttonluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ctrl_ThemChiPhi.this, TongQuan.class);
                startActivity(intent);
            }
        });
    }

    private void openFeedbackDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.hangmucchiphi); // Sử dụng layout mới cho dialog
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
        dialog.show(); // Hiển thị dialog
    }
}