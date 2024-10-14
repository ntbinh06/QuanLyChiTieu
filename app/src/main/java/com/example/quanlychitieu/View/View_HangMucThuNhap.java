package com.example.quanlychitieu.View;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ListView;

import com.example.quanlychitieu.Controller.Ctrl_HangMucChiPhi;
import com.example.quanlychitieu.Model.Model_HangMucChiPhi;
import com.example.quanlychitieu.R;

import java.util.ArrayList;

public class View_HangMucThuNhap extends AppCompatActivity {
    private ListView lvContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.hangmucthunhap);

        // Khởi tạo ListView
        lvContact = findViewById(R.id.listView_thunhap);

        // Tạo danh sách chi phí
        ArrayList<Model_HangMucChiPhi> arrContact = new ArrayList<>();
        arrContact.add(new Model_HangMucChiPhi(R.drawable.baseline_account_circle_24, "Thu nhập tài chính"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.baseline_account_circle_24, "Lương"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.baseline_account_circle_24, "Tiền từ việc vặt"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.baseline_account_circle_24, "Tiền trợ cấp"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.baseline_account_circle_24, "Tiền tiết kiệm"));

        // Khởi tạo adapter và gán cho ListView
        Ctrl_HangMucChiPhi customAdapter = new Ctrl_HangMucChiPhi(this, R.layout.list_item, arrContact);
        lvContact.setAdapter(customAdapter);

        // Thiết lập padding cho view chính
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.hangmucthunhap), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}