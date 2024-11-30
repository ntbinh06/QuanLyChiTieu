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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Ctrl_SuaThuNhap extends AppCompatActivity {
    private EditText editTextGiaTri, editTextTu, editTextGhiChu, editTextDate;
    private Spinner spnthunhap;
    private ImageView imageViewCalendar;
    private DatabaseReference userRef, giaoDichRef;
    private List<M_TaiKhoan> taiKhoanList = new ArrayList<>();
    private static final int PICK_IMAGE_REQUEST = 1;
    private String transactionId, selectedTaiKhoanId, selectedHangMucId;
    private TextView chonHangMucTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suathunhap); // Your layout file

        // Khởi tạo các view
        editTextGiaTri = findViewById(R.id.edit_value);
        editTextTu = findViewById(R.id.edit_tu);
        editTextGhiChu = findViewById(R.id.edit_note);
        editTextDate = findViewById(R.id.day);
        imageViewCalendar = findViewById(R.id.image_day);
        chonHangMucTextView = findViewById(R.id.chonhangmuc);
        spnthunhap = findViewById(R.id.spnthunhap);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        transactionId = intent.getStringExtra("idGiaoDich");
        double giaTri = intent.getDoubleExtra("giaTri", 0);
        String ngayTao = intent.getStringExtra("ngayTao");
        String ghiChu = intent.getStringExtra("ghiChu");
        String tu = intent.getStringExtra("tu");
        String idHangMuc = intent.getStringExtra("idHangMuc");
        String idTaiKhoan = intent.getStringExtra("idTaiKhoan");
        ImageButton ic_back = findViewById(R.id.ic_back);
        Button buttonhuy = findViewById(R.id.buttonhuy);
        Button buttonluu = findViewById(R.id.buttonluu);
        ImageView btnCamera = findViewById(R.id.btnCamera);
        ImageButton hangmuc = findViewById(R.id.hangmuc);
        // Gán dữ liệu cho các view
        editTextGiaTri.setText(String.valueOf(giaTri));
        editTextDate.setText(ngayTao);
        editTextGhiChu.setText(ghiChu != null ? ghiChu : "Không có ghi chú");
        editTextTu.setText(tu != null ? tu : "Không có thông tin");

        // Khởi tạo Firebase
        userRef = FirebaseDatabase.getInstance().getReference("TaiKhoan");
        giaoDichRef = FirebaseDatabase.getInstance().getReference("GiaoDich").child(transactionId);

        // Lấy thông tin danh mục hàng hóa
        loadHangMuc(idHangMuc);
        // Lấy thông tin tài khoản
        loadTaiKhoan(idTaiKhoan);
        imageViewCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        hangmuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedbackDialog(Gravity.BOTTOM);
            }
        });

        buttonhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ctrl_SuaThuNhap.this, Ctrl_TongQuan.class));
            }
        });

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ctrl_SuaThuNhap.this, Ctrl_TongQuan.class));
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        buttonluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double giaTri;
                // Kiểm tra giá trị nhập vào cho giaTri
                try {
                    giaTri = Double.parseDouble(editTextGiaTri.getText().toString().trim());
                } catch (NumberFormatException e) {
                    Toast.makeText(Ctrl_SuaThuNhap.this, "Giá trị không hợp lệ!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Ctrl_SuaThuNhap.this, "Ngày không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Nhận ID giao dịch từ Intent
                String idGiaoDich = getIntent().getStringExtra("idGiaoDich");
                if (idGiaoDich == null || idGiaoDich.isEmpty()) {
                    Toast.makeText(Ctrl_SuaThuNhap.this, "ID giao dịch không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Sử dụng giá trị cũ nếu ID hạng mục hoặc tài khoản là null
                String hangMucIdToUpdate = selectedHangMucId != null ? selectedHangMucId : getCurrentHangMucId();
                String taiKhoanIdToUpdate = selectedTaiKhoanId != null ? selectedTaiKhoanId : getCurrentTaiKhoanId();

                // Tạo đối tượng M_GiaoDich và cập nhật vào Firebase
                DatabaseReference giaoDichRef = FirebaseDatabase.getInstance().getReference("GiaoDich").child(idGiaoDich);

                // Tạo một HashMap để cập nhật các trường mong muốn
                HashMap<String, Object> updates = new HashMap<>();
                updates.put("giaTri", giaTri);
                updates.put("idHangMuc", hangMucIdToUpdate); // Cập nhật ID hạng mục
                updates.put("idTaiKhoan", taiKhoanIdToUpdate); // Cập nhật ID tài khoản
                updates.put("ngayTao", ngayTao); // Lưu ngày dưới dạng Date
                updates.put("tu", tu);
                updates.put("ghiChu", ghiChu);

                // Cập nhật dữ liệu vào Firebase
                giaoDichRef.updateChildren(updates).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Ctrl_SuaThuNhap.this, "Thông tin đã được cập nhật!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Ctrl_SuaThuNhap.this, Ctrl_CacGiaoDich.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Ctrl_SuaThuNhap.this, "Lỗi cập nhật thông tin. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            // Phương thức để lấy ID hạng mục hiện tại
            private String getCurrentHangMucId() {
                // Nhận ID hạng mục từ Intent
                String idHangMuc = getIntent().getStringExtra("idHangMuc");
                if (idHangMuc != null && !idHangMuc.isEmpty()) {
                    return idHangMuc; // Trả về ID từ Intent
                }

                // Nếu không có ID từ Intent, kiểm tra biến toàn cục
                if (selectedHangMucId != null) {
                    return selectedHangMucId; // Trả về giá trị đã chọn
                } else {
                    // Nếu chưa chọn, có thể trả về một giá trị mặc định hoặc null
                    return null; // Hoặc "defaultHangMucId" nếu cần
                }
            }
            // Phương thức để lấy ID tài khoản hiện tại
            private String getCurrentTaiKhoanId() {
                // Trả về ID tài khoản hiện tại, có thể lấy từ một biến toàn cục hoặc một phương thức nào đó
                return selectedTaiKhoanId != null ? selectedTaiKhoanId : "defaultTaiKhoanId"; // Thay "defaultTaiKhoanId" bằng giá trị mặc định nếu cần
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
                    String selectedDate = String.format("%02d/%02d/%d", selectedDay, (selectedMonth + 1), selectedYear);
                    editTextDate.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }


    private void loadHangMuc(String idHangMuc) {
        DatabaseReference hangMucRef = FirebaseDatabase.getInstance().getReference("HangMuc").child(idHangMuc);
        hangMucRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot hangMucSnap) {
                M_DanhMucHangMuc danhMucHangMuc = hangMucSnap.getValue(M_DanhMucHangMuc.class);
                chonHangMucTextView.setText(danhMucHangMuc != null ? danhMucHangMuc.getTenHangmuc() : "Không có tên");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Ctrl_SuaThuNhap.this, "Lỗi khi tải danh mục", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTaiKhoan(String idTaiKhoan) {
        userRef.child(idTaiKhoan).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot taiKhoanSnap) {
                M_TaiKhoan taiKhoan = taiKhoanSnap.getValue(M_TaiKhoan.class);
                // Cập nhật tên tài khoản nếu có
                // Cần thêm một TextView để hiển thị tên tài khoản
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Ctrl_SuaThuNhap.this, "Lỗi khi tải tài khoản", Toast.LENGTH_SHORT).show();
            }
        });

        getTaiKhoanList(idTaiKhoan); // Lấy danh sách tài khoản
    }

    private void getTaiKhoanList(String selectedId) {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                taiKhoanList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idTaiKhoan = snapshot.child("idTaiKhoan").getValue(String.class);
                    String tenTaiKhoan = snapshot.child("tenTaiKhoan").getValue(String.class);
                    taiKhoanList.add(new M_TaiKhoan(idTaiKhoan, tenTaiKhoan));
                }
                updateSpinner(selectedId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Ctrl_SuaThuNhap.this, "Lỗi lấy dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateSpinner(String selectedId) {
        List<String> tentaikhoanList = new ArrayList<>();
        for (M_TaiKhoan taiKhoan : taiKhoanList) {
            tentaikhoanList.add(taiKhoan.getTenTaiKhoan());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tentaikhoanList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnthunhap.setAdapter(adapter);

        spnthunhap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTaiKhoanId = taiKhoanList.get(i).getIdTaiKhoan(); // Lưu ID tài khoản đã chọn
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedTaiKhoanId = null; // Không có gì được chọn
            }
        });

        // Chọn tài khoản mặc định nếu ID đã cho
        if (selectedId != null) {
            for (int i = 0; i < taiKhoanList.size(); i++) {
                if (taiKhoanList.get(i).getIdTaiKhoan().equals(selectedId)) {
                    spnthunhap.setSelection(i);
                    break;
                }
            }
        }
    }



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
                arrContact.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idHangmuc = snapshot.child("idHangmuc").getValue(String.class);
                    String tenHangmuc = snapshot.child("tenHangmuc").getValue(String.class);
                    arrContact.add(new M_DanhMucHangMuc(idHangmuc, tenHangmuc));
                }

                // Khởi tạo adapter và gán cho ListView
                Ctrl_HangMucThuNhap customAdapter = new Ctrl_HangMucThuNhap(Ctrl_SuaThuNhap.this, R.layout.list_item_hangmuc, arrContact);
                listView.setAdapter(customAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        M_DanhMucHangMuc selectedItem = arrContact.get(position);
                        selectedHangMucId = selectedItem.getIdHangmuc(); // Lưu ID hạng mục đã chọn
                        String tenHangMuc = selectedItem.getTenHangmuc(); // Lấy tên hạng mục
                        chonHangMucTextView.setText(tenHangMuc); // Cập nhật TextView
                        Toast.makeText(Ctrl_SuaThuNhap.this, "Đã chọn: " + tenHangMuc, Toast.LENGTH_SHORT).show();
                        dialog.dismiss(); // Đóng dialog sau khi chọn
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Ctrl_SuaThuNhap.this, "Lỗi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                Log.e("DatabaseError", databaseError.getMessage());
            }
        });

        dialog.setCancelable(Gravity.BOTTOM == gravity);
        dialog.show(); // Hiển thị dialog// Hiển thị dialog
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
            // Thực hiện các hành động cần thiết với hình ảnh
        }
    }


}