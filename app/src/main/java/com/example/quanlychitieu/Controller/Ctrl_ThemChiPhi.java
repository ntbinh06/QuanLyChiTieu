package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.app.Dialog;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychitieu.Model.Model_HangMucChiPhi;
import com.example.quanlychitieu.R;

import java.util.ArrayList;
import java.util.List;

public class Ctrl_ThemChiPhi extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1; // Thêm dòng này
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
        ImageView btnCamera = findViewById(R.id.btnCamera);

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

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    private void openFeedbackDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.hangmucchiphi); // Đảm bảo layout này tồn tại

        Window window = dialog.getWindow();
        if (window == null) {
            return; // Nếu không lấy được window, trả về
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        // Tìm ListView trong dialog
        ListView listView = dialog.findViewById(R.id.listView_chiphi); // Kiểm tra ID có đúng không

        if (listView == null) {
            // Log lỗi nếu ListView không tồn tại
            Log.e("Ctrl_ThemChiPhi", "ListView is null. Check the layout for ID listView_chiphi.");
            return; // Trả về nếu không tìm thấy ListView
        }

        ArrayList<Model_HangMucChiPhi> arrContact = new ArrayList<>();
        arrContact.add(new Model_HangMucChiPhi(R.drawable.food, "Đồ ăn/ Đồ uống"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.shopping, "Mua sắm"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.family, "Gia đình"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.delivery_van, "Vận chuyển"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.drum_set, "Giải trí"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.house, "Nhà cửa"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.healthcare, "Sức khỏe"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.pets, "Thú cưng"));
        arrContact.add(new Model_HangMucChiPhi(R.drawable.travel_luggage, "Du lịch"));

        Ctrl_HangMucChiPhi customAdapter = new Ctrl_HangMucChiPhi(this, R.layout.list_item_hangmuc, arrContact);
        listView.setAdapter(customAdapter); // Đảm bảo listView không null

        dialog.setCancelable(true); // Cho phép hủy dialog
        dialog.show(); // Hiển thị dialog
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            // Thực hiện các hành động cần thiết với hình ảnh, ví dụ: hiển thị trong ImageView
        }
    }
}

