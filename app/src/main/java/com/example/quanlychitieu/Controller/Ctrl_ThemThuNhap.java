package com.example.quanlychitieu.Controller;

import android.app.DatePickerDialog;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.app.Dialog;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychitieu.Model.M_DanhMucHangMuc;
import com.example.quanlychitieu.Model.M_GiaoDich;
import com.example.quanlychitieu.Model.M_HangMucThuNhap;
import com.example.quanlychitieu.Model.M_TaiKhoan;
import com.example.quanlychitieu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Ctrl_ThemThuNhap extends AppCompatActivity {

    private EditText editTextGiaTri, editTextTu, editTextGhiChu; // Khai báo EditText
    private Spinner spnthunhap; // Khai báo Spinner
    private FirebaseFirestore db;
    private DatabaseReference userRef;
    private List<M_TaiKhoan> taiKhoanList; // Danh sách tài khoản
    private List<M_DanhMucHangMuc> HangMucList;
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText editTextDate;
    private ImageView imageViewCalendar;
    private TextView chonHangMucTextView;
    private ImageButton hangmuc; // Khai báo toàn cục
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themthunhap); // Your layout file
        db = FirebaseFirestore.getInstance();
        editTextGiaTri = findViewById(R.id.edit_value);
        editTextTu = findViewById(R.id.edit_tu);
        editTextGhiChu = findViewById(R.id.edit_note);
         chonHangMucTextView = findViewById(R.id.chonhangmuc);
        spnthunhap = findViewById(R.id.spnthunhap);
        spnthunhap.setDropDownVerticalOffset(140);

        editTextDate = findViewById(R.id.day);
        imageViewCalendar = findViewById(R.id.image_day);

        // Thiết lập sự kiện khi nhấn vào ImageView
        imageViewCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        // Lấy danh sách từ mô hình
        taiKhoanList = new ArrayList<>();
        userRef = FirebaseDatabase.getInstance().getReference("TaiKhoan");
        // Lấy danh mục từ Firestore
        gettaiKhoanList();

        hangmuc = findViewById(R.id.hangmuc);
        ImageButton ic_back = findViewById(R.id.ic_back);
        Button buttonhuy = findViewById(R.id.btnHuy);
        Button buttonluu = findViewById(R.id.btnLuu);
        ImageButton ic_camera = findViewById(R.id.ic_camera);


        hangmuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedbackDialog(Gravity.BOTTOM);
            }
        });

        buttonhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ctrl_ThemThuNhap.this, Ctrl_TongQuan.class);
                startActivity(intent);
            }
        });

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ctrl_ThemThuNhap.this, Ctrl_TongQuan.class);
                startActivity(intent);
            }
        });

        buttonluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double giaTri;
                try {
                    giaTri = Double.parseDouble(editTextGiaTri.getText().toString().trim());
                } catch (NumberFormatException e) {
                    Toast.makeText(Ctrl_ThemThuNhap.this, "Giá trị không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String tu = editTextTu.getText().toString().trim();
                String ghiChu = editTextGhiChu.getText().toString().trim();
                String dateStr = editTextDate.getText().toString().trim();

                // Lấy UID của người dùng hiện tại
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                // Tạo đối tượng M_GiaoDich
                M_GiaoDich giaoDich = new M_GiaoDich();
                giaoDich.setIdGiaoDich(FirebaseDatabase.getInstance().getReference("GiaoDich").push().getKey());
                giaoDich.setGiaTri(giaTri);
                giaoDich.setIdHangMuc(selectedHangMucId);
                giaoDich.setIdTaiKhoan(selectedTaiKhoanId);
                giaoDich.setUserId(userId);

                // Chuyển đổi chuỗi ngày tháng thành đối tượng Date
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date ngayTao;
                try {
                    ngayTao = sdf.parse(dateStr);

                    // Lưu ngày tạo dưới dạng Map
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(ngayTao);
                    Map<String, Object> dateMap = new HashMap<>();
                    dateMap.put("ngay", calendar.get(Calendar.DAY_OF_MONTH));
                    dateMap.put("thang", calendar.get(Calendar.MONTH) + 1); // Tháng được tính từ 0
                    dateMap.put("nam", calendar.get(Calendar.YEAR));

                    giaoDich.setNgayTao(dateMap); // Cập nhật ngày tạo để lưu trên Firebase
                } catch (ParseException e) {
                    Toast.makeText(Ctrl_ThemThuNhap.this, "Ngày không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                giaoDich.setTu(tu);
                giaoDich.setGhiChu(ghiChu);

                // Cập nhật dữ liệu vào Firebase
                DatabaseReference giaoDichRef = FirebaseDatabase.getInstance().getReference("GiaoDich").child(giaoDich.getIdGiaoDich());
                giaoDichRef.setValue(giaoDich).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Ctrl_ThemThuNhap.this, "Thông tin đã được cập nhật!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Ctrl_ThemThuNhap.this, Ctrl_CacGiaoDich.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Ctrl_ThemThuNhap.this, "Lỗi cập nhật thông tin. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        ic_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Cập nhật ngày vào EditText theo định dạng dd/MM/yyyy
                    String selectedDate = String.format("%02d/%02d/%d", selectedDay, (selectedMonth + 1), selectedYear);
                    editTextDate.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }
    private void gettaiKhoanList() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                taiKhoanList.clear(); // Clear the old list if needed

                // Get the current user's ID
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idTaiKhoan = snapshot.child("idTaiKhoan").getValue(String.class);
                    String tenTaiKhoan = snapshot.child("tenTaiKhoan").getValue(String.class);
                    String hangMucUserId = snapshot.child("userId").getValue(String.class); // Assuming userId is stored
                    double luongBanDau = snapshot.child("luongBanDau").getValue(Double.class);
                    // Create and add M_TaiKhoan object to the list if userId matches
                    if (userId != null && userId.equals(hangMucUserId)) {
                        M_TaiKhoan taiKhoan = new M_TaiKhoan(idTaiKhoan, tenTaiKhoan, hangMucUserId, luongBanDau); // Assuming constructor includes userId
                        taiKhoanList.add(taiKhoan);
                    }
                }
                updateSpinner();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Ctrl_ThemThuNhap.this, "Lỗi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                Log.e("DatabaseError", databaseError.getMessage());
            }
        });
    }

    private String selectedTaiKhoanId = null; // Variable to store the selected account ID

    private void updateSpinner() {
        List<String> tentaikhoanList = new ArrayList<>();
        for (M_TaiKhoan taiKhoan : taiKhoanList) {
            // Add the account name to the list
            if (taiKhoan.getTenTaiKhoan() != null) {
                tentaikhoanList.add(taiKhoan.getTenTaiKhoan());
            }
        }

        // Check if the list is empty
        if (tentaikhoanList.isEmpty()) {
            Toast.makeText(this, "Không có tài khoản nào để hiển thị", Toast.LENGTH_SHORT).show();
            return; // Stop execution if there is no data
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tentaikhoanList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnthunhap.setAdapter(adapter);

        spnthunhap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Save the account ID based on the selected index
                selectedTaiKhoanId = taiKhoanList.get(i).getIdTaiKhoan(); // Get the ID corresponding to the account name
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(Ctrl_ThemThuNhap.this, selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Ctrl_ThemThuNhap.this, "Chưa chọn mục nào", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String selectedHangMucId = null; // Biến để lưu ID hạng mục đã chọn

    private void openFeedbackDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_hangmucthunhap);
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
        ListView listView = dialog.findViewById(R.id.listView_thunhap);

        // Tạo danh sách hạng mục
        ArrayList<M_DanhMucHangMuc> arrContact = new ArrayList<>();

        // Lấy dữ liệu từ Firebase
        DatabaseReference danhMucRef = FirebaseDatabase.getInstance().getReference("HangMuc");
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // Sử dụng idNhom là kiểu string "1"
        String idNhom = "1"; // Giá trị string bạn muốn so sánh

        danhMucRef.orderByChild("idNhom").equalTo(idNhom).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrContact.clear(); // Xóa danh sách cũ nếu cần

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idHangmuc = snapshot.child("idHangmuc").getValue(String.class);
                    String tenHangmuc = snapshot.child("tenHangmuc").getValue(String.class);
                    String anhHangMuc = snapshot.child("anhHangmuc").getValue(String.class); // Lấy trường anhHangmuc
                    String hangMucUserId = snapshot.child("userId").getValue(String.class); // Assuming userId is stored in each item

                    // Check if the userId matches
                    if (userId != null && userId.equals(hangMucUserId)) {
                        // Add to the list if userId matches
                        arrContact.add(new M_DanhMucHangMuc(idHangmuc, tenHangmuc, anhHangMuc));
                    }

                }

                // Khởi tạo adapter và gán cho ListView
                Ctrl_HangMucThuNhap customAdapter = new Ctrl_HangMucThuNhap(Ctrl_ThemThuNhap.this, R.layout.list_item_hangmuc, arrContact);
                listView.setAdapter(customAdapter);

                // Thiết lập sự kiện cho ListView
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        M_DanhMucHangMuc selectedItem = arrContact.get(position);
                        selectedHangMucId = selectedItem.getIdHangmuc(); // Lưu ID hạng mục đã chọn
                        String tenHangMuc = selectedItem.getTenHangmuc(); // Lấy tên hạng mục
                        chonHangMucTextView.setText(tenHangMuc); // Cập nhật TextView
                        String anhHangMuc = selectedItem.getAnhHangmuc();
                        // Cập nhật ImageButton với ảnh từ anhHangMuc
                        if (anhHangMuc != null && !anhHangMuc.isEmpty()) {
                            int drawableId = getResources().getIdentifier(anhHangMuc, "drawable", getPackageName());
                            if (drawableId != 0) {
                                hangmuc.setImageResource(drawableId); // Gán ảnh từ drawable vào ImageButton
                            } else {
                                hangmuc.setImageResource(R.drawable.analysis); // Ảnh mặc định nếu không tìm thấy
                            }
                        } else {
                            hangmuc.setImageResource(R.drawable.analysis); // Ảnh mặc định nếu anhHangMuc null hoặc rỗng
                        }

                        Toast.makeText(Ctrl_ThemThuNhap.this, "Đã chọn: " + tenHangMuc, Toast.LENGTH_SHORT).show();
                        dialog.dismiss(); // Đóng dialog sau khi chọn
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Ctrl_ThemThuNhap.this, "Lỗi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                Log.e("DatabaseError", databaseError.getMessage());
            }
        });

        dialog.setCancelable(Gravity.BOTTOM == gravity);
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