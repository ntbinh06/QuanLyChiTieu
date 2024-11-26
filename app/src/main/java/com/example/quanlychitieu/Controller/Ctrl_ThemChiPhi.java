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
import com.example.quanlychitieu.Model.M_HangMucChiPhi;
import com.example.quanlychitieu.Model.M_TaiKhoan;
import com.example.quanlychitieu.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Ctrl_ThemChiPhi extends AppCompatActivity {
    private EditText editTextGiaTri, editTextTu, editTextGhiChu;
    private Spinner spnchiphi;
    private FirebaseFirestore db;
    private DatabaseReference userRef;
    private List<M_TaiKhoan> taiKhoanList; // Danh sách tài khoản
    private List<M_DanhMucHangMuc> HangMucList;
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText editTextDate;
    private ImageView imageViewCalendar;
    private TextView chonHangMucTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themchiphi); // Your layout file

        spnchiphi = findViewById(R.id.spnchiphi);
        db = FirebaseFirestore.getInstance();
        editTextGiaTri = findViewById(R.id.edit_value);
        editTextTu = findViewById(R.id.edit_tu);
        editTextGhiChu = findViewById(R.id.edit_note);
        chonHangMucTextView = findViewById(R.id.chonhangmuc);

        editTextDate = findViewById(R.id.day);
        imageViewCalendar = findViewById(R.id.image_day);

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
                startActivity(new Intent(Ctrl_ThemChiPhi.this, Ctrl_TongQuan.class));
            }
        });

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ctrl_ThemChiPhi.this, Ctrl_TongQuan.class));
            }
        });

        buttonluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double giaTri;
                try {
                    giaTri = Double.parseDouble(editTextGiaTri.getText().toString().trim());
                } catch (NumberFormatException e) {
                    Toast.makeText(Ctrl_ThemChiPhi.this, "Giá trị không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String tu = editTextTu.getText().toString().trim();
                String ghiChu = editTextGhiChu.getText().toString().trim();
                String dateStr = editTextDate.getText().toString().trim();

                // Chuyển đổi chuỗi ngày tháng thành đối tượng Date
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date ngayTao;
                try {
                    ngayTao = sdf.parse(dateStr);
                } catch (ParseException e) {
                    Toast.makeText(Ctrl_ThemChiPhi.this, "Ngày không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo đối tượng M_GiaoDich
                M_GiaoDich giaoDich = new M_GiaoDich();
                giaoDich.setIdGiaoDich(FirebaseDatabase.getInstance().getReference("GiaoDich").push().getKey());
                giaoDich.setGiaTri(giaTri);
                giaoDich.setIdHangMuc(selectedHangMucId);
                giaoDich.setIdTaiKhoan(selectedTaiKhoanId);
                giaoDich.setNgayTao(ngayTao);
                giaoDich.setTu(tu);
                giaoDich.setGhiChu(ghiChu);

                DatabaseReference giaoDichRef = FirebaseDatabase.getInstance().getReference("GiaoDich");

                // Lưu giao dịch trước
                giaoDichRef.child(giaoDich.getIdGiaoDich()).setValue(giaoDich).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sau khi lưu giao dịch, cập nhật ngân sách dự trù
                        DatabaseReference hangMucRef = FirebaseDatabase.getInstance().getReference("HangMuc").child(selectedHangMucId);
                        hangMucRef.child("nganSachDuTru").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Double nganSachDuTru = snapshot.getValue(Double.class);
                                    if (nganSachDuTru != null) {
                                        double updatedNganSachDuTru = nganSachDuTru - giaTri;

                                        // Cập nhật lại giá trị ngân sách dự trù
                                        hangMucRef.child("nganSachDuTru").setValue(updatedNganSachDuTru).addOnCompleteListener(updateTask -> {
                                            if (updateTask.isSuccessful()) {
                                                Toast.makeText(Ctrl_ThemChiPhi.this, "Thông tin đã được lưu và ngân sách được cập nhật!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Ctrl_ThemChiPhi.this, Ctrl_TongQuan.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(Ctrl_ThemChiPhi.this, "Lỗi cập nhật ngân sách. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                } else {
                                    Toast.makeText(Ctrl_ThemChiPhi.this, "Không tìm thấy ngân sách dự trù!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(Ctrl_ThemChiPhi.this, "Lỗi khi đọc ngân sách: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(Ctrl_ThemChiPhi.this, "Lỗi lưu thông tin giao dịch. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
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
                taiKhoanList.clear(); // Xóa danh sách cũ nếu cần

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idTaiKhoan = snapshot.child("idTaiKhoan").getValue(String.class);
                    String tenTaiKhoan = snapshot.child("tenTaiKhoan").getValue(String.class);

                    // Tạo và thêm đối tượng M_TaiKhoan vào danh sách
                    M_TaiKhoan taiKhoan = new M_TaiKhoan(idTaiKhoan, tenTaiKhoan); // Giả sử bạn có constructor như vậy
                    taiKhoanList.add(taiKhoan);
                }
                updateSpinner();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Ctrl_ThemChiPhi.this, "Lỗi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                Log.e("DatabaseError", databaseError.getMessage());
            }
        });
    }
    private String selectedTaiKhoanId = null; // Biến để lưu ID tài khoản đã chọn

    private void updateSpinner() {
        List<String> tentaikhoanList = new ArrayList<>();
        for (M_TaiKhoan taiKhoan : taiKhoanList) {
            if (taiKhoan.getTenTaiKhoan() != null) { // Kiểm tra null trước khi thêm
                tentaikhoanList.add(taiKhoan.getTenTaiKhoan());
            }
        }

        // Kiểm tra nếu danh sách trống
        if (tentaikhoanList.isEmpty()) {
            Toast.makeText(this, "Không có tài khoản nào để hiển thị", Toast.LENGTH_SHORT).show();
            return; // Ngừng thực hiện nếu không có dữ liệu
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tentaikhoanList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnchiphi.setAdapter(adapter);

        spnchiphi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Lưu ID tài khoản dựa trên chỉ số đã chọn
                selectedTaiKhoanId = taiKhoanList.get(i).getIdTaiKhoan(); // Lấy ID tương ứng với tên tài khoản
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(Ctrl_ThemChiPhi.this, selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Ctrl_ThemChiPhi.this, "Chưa chọn mục nào", Toast.LENGTH_SHORT).show();
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

        // Sử dụng idNhom là kiểu string "1"
        String idNhom = "2"; // Giá trị string bạn muốn so sánh

        danhMucRef.orderByChild("idNhom").equalTo(idNhom).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrContact.clear(); // Xóa danh sách cũ nếu cần

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idHangmuc = snapshot.child("idHangmuc").getValue(String.class);
                    String tenHangmuc = snapshot.child("tenHangmuc").getValue(String.class);

                    // Thêm vào danh sách
                    arrContact.add(new M_DanhMucHangMuc(idHangmuc, tenHangmuc));
                }

                // Khởi tạo adapter và gán cho ListView
                Ctrl_HangMucThuNhap customAdapter = new Ctrl_HangMucThuNhap(Ctrl_ThemChiPhi.this, R.layout.list_item_hangmuc, arrContact);
                listView.setAdapter(customAdapter);

                // Thiết lập sự kiện cho ListView
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        M_DanhMucHangMuc selectedItem = arrContact.get(position);
                        selectedHangMucId = selectedItem.getIdHangmuc(); // Lưu ID hạng mục đã chọn
                        String tenHangMuc = selectedItem.getTenHangmuc(); // Lấy tên hạng mục
                        chonHangMucTextView.setText(tenHangMuc); // Cập nhật TextView
                        Toast.makeText(Ctrl_ThemChiPhi.this, "Đã chọn: " + tenHangMuc, Toast.LENGTH_SHORT).show();
                        dialog.dismiss(); // Đóng dialog sau khi chọn
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Ctrl_ThemChiPhi.this, "Lỗi lấy dữ liệu", Toast.LENGTH_SHORT).show();
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

