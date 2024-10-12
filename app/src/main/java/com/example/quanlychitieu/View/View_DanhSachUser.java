package com.example.quanlychitieu.View;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ListView;

import com.example.quanlychitieu.Controller.Ctrl_DanhSachUser;
import com.example.quanlychitieu.Model.Model_DanhSachUser;
import com.example.quanlychitieu.R;

import java.util.ArrayList;

public class View_DanhSachUser extends AppCompatActivity {
    private ListView lvContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.danhsachuser_admin);

        lvContact = findViewById(R.id.lvuser);
        ArrayList<Model_DanhSachUser> arrContact = new ArrayList<>();

        // Tạo các đối tượng Model_DanhSachUser
        Model_DanhSachUser contact1 = new Model_DanhSachUser( R.drawable.user_woman_female_person_icon,"Nguyễn Thanh Bình", "binhqb@gmail", R.drawable.ic_eye,R.drawable.ic_lock_user);
        Model_DanhSachUser contact2 = new Model_DanhSachUser(R.drawable.user_woman_female_person_icon,"Lê Thị Kim Ngân", "ngan@gmail", R.drawable.ic_eye,R.drawable.ic_lock_user);
        Model_DanhSachUser contact3 = new Model_DanhSachUser(R.drawable.user_woman_female_person_icon,"Cái Thị Nhân Đức", "duc@gmail", R.drawable.ic_eye,R.drawable.ic_lock_user);
        Model_DanhSachUser contact4 = new Model_DanhSachUser(R.drawable.user_woman_female_person_icon,"Lê Thị A", "thia@gmail", R.drawable.ic_eye,R.drawable.ic_lock_user);
        Model_DanhSachUser contact5 = new Model_DanhSachUser(R.drawable.user_woman_female_person_icon,"Cái Thị B", "thib@gmail", R.drawable.ic_eye,R.drawable.ic_lock_user);
        Model_DanhSachUser contact6 = new Model_DanhSachUser(R.drawable.user_woman_female_person_icon,"Nguyễn Thị C", "thic@gmail", R.drawable.ic_eye,R.drawable.ic_lock_user);


        // Thêm các đối tượng vào danh sách
        arrContact.add(contact1);
        arrContact.add(contact2);
        arrContact.add(contact3);
        arrContact.add(contact4);

        // Tạo adapter và thiết lập cho ListView
        Ctrl_DanhSachUser customAdapter = new Ctrl_DanhSachUser(this, R.layout.list_item_user, arrContact);
        lvContact.setAdapter(customAdapter);

        // Thiết lập padding cho View chính
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.lvuser), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}