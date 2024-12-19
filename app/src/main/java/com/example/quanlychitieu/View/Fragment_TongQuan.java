package com.example.quanlychitieu.View;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Locale;

import com.example.quanlychitieu.Controller.Ctrl_GioiThieu;
import com.example.quanlychitieu.Controller.Ctrl_NganSach;
import com.example.quanlychitieu.Controller.Ctrl_CacGiaoDich;
import com.example.quanlychitieu.Controller.Ctrl_CacTaiKhoan;
import com.example.quanlychitieu.Controller.Ctrl_NguoiDung;
import com.example.quanlychitieu.Controller.Ctrl_RecyclerViewItemClickListener;
import com.example.quanlychitieu.Controller.Ctrl_ThongKe;
import com.example.quanlychitieu.Controller.Ctrl_TongQuan;
import com.example.quanlychitieu.Controller.Ctrl_QuanLyHangMuc;
import com.example.quanlychitieu.Controller.Ctrl_XemChiPhi;
import com.example.quanlychitieu.Controller.Ctrl_XemTKChiTiet;
import com.example.quanlychitieu.Controller.Ctrl_XemThuNhap;
import com.example.quanlychitieu.Model.M_DanhMucHangMuc;
import com.example.quanlychitieu.Model.M_GiaoDich;
import com.example.quanlychitieu.Model.M_TaiKhoan;
import com.example.quanlychitieu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Fragment_TongQuan extends Fragment {

    private ImageView btnMenu, btnXemCacTaiKhoan, btnXemCacGiaoDich, imgEmptyState,btnsodo;
    private TextView amountTextView, currentMonthofYear, txtTongThuNhap, txtTongChiPhi, txtEmptyState;
    private ImageView eyeIcon;
    private boolean isAmountVisible = true;
    private RecyclerView rvCacTaiKhoan, rvCacGiaoDich;
    private List<M_TaiKhoan> taiKhoanList = new ArrayList<>();
    private List<M_GiaoDich> giaoDichList = new ArrayList<>();
    private V_TongQuan_CacGiaoDich giaoDichAdapter;
    private Map<String, String> taiKhoanMap = new HashMap<>();
    private V_TongQuan_CacTaiKhoan myAdapter;
    private ProgressBar progressBarThuNhap, progressBarChiPhi;
    private DatabaseReference taiKhoanRef, giaoDichRef;
    private Map<String, M_DanhMucHangMuc> hangMucMap = new HashMap<>();
    private Map<String, String> nhomHangMucMap = new HashMap<>();

    public Fragment_TongQuan() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout cho Fragment
        View view = inflater.inflate(R.layout.activity_tong_quan, container, false);

        btnMenu = view.findViewById(R.id.btnMenu);
        btnsodo = view.findViewById(R.id.all_sodo);
        btnXemCacTaiKhoan = view.findViewById(R.id.all_cactaikhoan);
        btnXemCacGiaoDich = view.findViewById(R.id.all_cacgiaodich);
        progressBarThuNhap = view.findViewById(R.id.progressBarThuNhap);
        progressBarChiPhi = view.findViewById(R.id.progressBarChiPhi);
        txtEmptyState = view.findViewById(R.id.txtEmptyState);
        imgEmptyState = view.findViewById(R.id.imgEmptyState);
        rvCacGiaoDich = view.findViewById(R.id.recyclerViewTransactions);

        // Khởi tạo RecyclerView và Adapter cac tai khoan
        rvCacTaiKhoan = view.findViewById(R.id.recyclerviewGD);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);

        rvCacTaiKhoan.addItemDecoration(dividerItemDecoration);
        taiKhoanList = new ArrayList<>();
        myAdapter = new V_TongQuan_CacTaiKhoan(getContext(), taiKhoanList);
        rvCacTaiKhoan.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCacTaiKhoan.setAdapter(myAdapter);

// Kết nối đến Firebase Database


        ///Khoi tao RecycleView vaf Adapter cac giao dich
        giaoDichList = new ArrayList<>();
        giaoDichAdapter = new V_TongQuan_CacGiaoDich(getContext(), giaoDichList);
        rvCacGiaoDich.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCacGiaoDich.setAdapter(giaoDichAdapter);

        // Hiển thị tháng năm hiện tại
        currentMonthofYear = view.findViewById(R.id.txt_ThangHienTai);

        // Lấy tháng và năm hiện tại
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM / yyyy", new Locale("Vi", "VN")); // "vi" để hiển thị tiếng Việt
        String currentMonthYear = sdf.format(calendar.getTime());

        // Gán chuỗi tháng năm vào TextView
        currentMonthofYear.setText(currentMonthYear);


        // Tinh Tong thu nhap va Tong Chi phi của tháng hien tai
        txtTongChiPhi = view.findViewById(R.id.txtTongChiPhi);
        txtTongThuNhap = view.findViewById(R.id.txtTongThuNhap);
        loadThuChiThangHienTai();


        eyeIcon = view.findViewById(R.id.ic_eye);
        amountTextView = view.findViewById(R.id.txtTongTienSH);

// Giả sử bạn có các icon đóng/mở mắt
        final Drawable eyeClosed = ContextCompat.getDrawable(getContext(), R.drawable.eye_of);
        final Drawable eyeOpened = ContextCompat.getDrawable(getContext(), R.drawable.eye_of);

// Lưu giá trị ban đầu vào tag của amountTextView để sử dụng khi cần hiển thị lại
        amountTextView.setTag(amountTextView.getText().toString());

        eyeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAmountVisible) {
                    // Ẩn số bằng dấu "*"
                    String amountText = amountTextView.getText().toString();
                    StringBuilder hiddenText = new StringBuilder();
                    for (int i = 0; i < amountText.length(); i++) {
                        hiddenText.append('*');
                    }
                    amountTextView.setText(hiddenText.toString());
                    eyeIcon.setImageDrawable(eyeClosed); // Thay đổi icon mắt thành đóng
                } else {
                    // Hiển thị lại số tiền
                    amountTextView.setText(amountTextView.getTag().toString()); // Lấy lại giá trị ban đầu từ tag
                    eyeIcon.setImageDrawable(eyeOpened); // Thay đổi icon mắt thành mở
                }
                isAmountVisible = !isAmountVisible; // Đổi trạng thái của isAmountVisible
            }
        });






        //HIEN THI DANH SACH CAC TAI KHOAN
        taiKhoanRef = FirebaseDatabase.getInstance().getReference("TaiKhoan");
        loadCacTaiKhoan();
        // Khi người dùng nhấn vào item để xem chi tiết
        rvCacTaiKhoan.addOnItemTouchListener(new Ctrl_RecyclerViewItemClickListener(getContext(), rvCacTaiKhoan, new Ctrl_RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                M_TaiKhoan taikhoan= taiKhoanList.get(position);
                //Định dạng ngày
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                // Tạo Intent để chuyển sang Activity XemTaiKhoanChiTiet
                Intent intent = new Intent(getContext(), Ctrl_XemTKChiTiet.class);

                // Truyền dữ liệu tài khoản qua Intent
                intent.putExtra("idTK", taikhoan.getIdTaiKhoan());
                intent.putExtra("tenTK", taikhoan.getTenTaiKhoan());
                intent.putExtra("luongbandau", String.valueOf(taikhoan.getLuongBanDau()));
                intent.putExtra("ngayTao", sdf.format(taikhoan.getNgayTao()));
                intent.putExtra("lanSuDungCuoi", sdf.format(taikhoan.getLanSuDungCuoi()));



                // Khởi động Activity
                getContext().startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));



        // HIEN THI CAC GIAO DICH
        giaoDichRef = FirebaseDatabase.getInstance().getReference("GiaoDich");
        loadGiaoDich();
        loadHangMuc();
        loadTaiKhoan();
        rvCacGiaoDich.addOnItemTouchListener(new Ctrl_RecyclerViewItemClickListener(getContext(), rvCacGiaoDich, new Ctrl_RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Giả sử bạn có một danh sách chứa giao dịch
                M_GiaoDich selectedGiaoDich = giaoDichList.get(position); // Lấy giao dịch từ vị trí
                String idGiaoDich = selectedGiaoDich.getIdGiaoDich();

                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

                // Truy vấn bảng GiaoDich
                dbRef.child("GiaoDich").child(idGiaoDich).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot giaoDichSnap) {
                        if (giaoDichSnap.exists()) {
                            String idHangMuc = giaoDichSnap.child("idHangMuc").getValue(String.class);
                            long giaTri = giaoDichSnap.child("giaTri").getValue(Long.class);

                            // Truy vấn bảng HangMuc dựa trên idHangMuc
                            dbRef.child("HangMuc").child(idHangMuc).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot hangMucSnap) {
                                    if (hangMucSnap.exists()) {
                                        String idNhom = hangMucSnap.child("idNhom").getValue(String.class);
                                        String tenHangMuc = hangMucSnap.child("tenHangMuc").getValue(String.class);

                                        // Truy vấn bảng NhomHangMuc dựa trên idNhom
                                        dbRef.child("NhomHangMuc").child(idNhom).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot nhomSnap) {
                                                if (nhomSnap.exists()) {
                                                    String tenNhom = nhomSnap.child("tenNhom").getValue(String.class);

                                                    // Chuyển hướng theo idNhom
                                                    Intent intent = new Intent();
                                                    if ("1".equals(idNhom)) {
                                                        intent = new Intent(view.getContext(), Ctrl_XemThuNhap.class);
                                                    } else if ("2".equals(idNhom)) {
                                                        intent = new Intent(view.getContext(), Ctrl_XemChiPhi.class);
                                                    }

                                                    // Truyền dữ liệu giao dịch
                                                    intent.putExtra("idGiaoDich", idGiaoDich);
                                                    intent.putExtra("idHangMuc", idHangMuc);
                                                    intent.putExtra("giaTri", giaTri);
                                                    intent.putExtra("tenHangMuc", tenHangMuc);
                                                    intent.putExtra("tenNhom", tenNhom);

                                                    view.getContext().startActivity(intent);

                                                    // Ghi log thông tin
                                                    Log.d("Firebase", "Chi tiết giao dịch:");
                                                    Log.d("Firebase", "Tên nhóm: " + tenNhom);
                                                    Log.d("Firebase", "Tên hạng mục: " + tenHangMuc);
                                                    Log.d("Firebase", "Giá trị giao dịch: " + giaTri);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Log.e("Firebase", "Lỗi truy vấn NhomHangMuc: " + error.getMessage());
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.e("Firebase", "Lỗi truy vấn HangMuc: " + error.getMessage());
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Firebase", "Lỗi truy vấn GiaoDich: " + error.getMessage());
                    }
                });
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // Xử lý khi click dài (nếu cần thiết)
            }
        }));





        // Thiết lập sự kiện click cho btnMenu
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        btnsodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomPopupWindowsodo(v, true);
            }
        });
        btnXemCacTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomPopupWindow(v, true);
            }
        });

        btnXemCacGiaoDich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomPopupWindow(v, false);
            }
        });

        return view;
    }

    private void deleteTransaction(int transactionId) {
        // Logic xóa giao dịch từ cơ sở dữ liệu
        // SQLiteDatabase db = getWritableDatabase();
        // db.delete("transactions", "id=?", new String[]{String.valueOf(transactionId)});
    }

    private void showCustomPopupWindow(View anchorView, boolean isTaiKhoanMenu) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_menu_custom, null);

        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(anchorView, anchorView.getWidth(), -20);

        TextView menuItem1 = popupView.findViewById(R.id.menu_item_1);

        if (isTaiKhoanMenu) {
            menuItem1.setText("Xem tất cả");
            menuItem1.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), Ctrl_CacTaiKhoan.class);
                startActivity(intent);
                popupWindow.dismiss();
            });
        } else {
            menuItem1.setText("Xem tất cả");
            menuItem1.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), Ctrl_CacGiaoDich.class);
                startActivity(intent);
                popupWindow.dismiss();
            });
        }
    }
    private void showCustomPopupWindowsodo(View anchorView, boolean isTaiKhoanMenu) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_menu_custom, null);

        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(anchorView, anchorView.getWidth(), -20);

        TextView menuItem1 = popupView.findViewById(R.id.menu_item_1);

        if (isTaiKhoanMenu) {
            menuItem1.setText("Xem tất cả");
            menuItem1.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), Ctrl_ThongKe.class);
                startActivity(intent);
                popupWindow.dismiss();
            });
        } else {
            menuItem1.setText("Xem tất cả");
            menuItem1.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), Ctrl_CacGiaoDich.class);
                startActivity(intent);
                popupWindow.dismiss();
            });
        }
    }

    private void showDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.tongquan_menu);

        TextView txtTenUser_SM= dialog.findViewById(R.id.txtTenUser_SideMenu);
        TextView  txtEmailUser_SM= dialog.findViewById(R.id.txtEmail_SideMenu);

        // Firebase User
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("NguoiDung").child(userId);

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String tenUser = dataSnapshot.child("tenUser").getValue(String.class);
                        String email = dataSnapshot.child("email").getValue(String.class);

                        txtTenUser_SM.setText(tenUser);
                        txtEmailUser_SM.setText(email);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getContext(), "Không thể tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
            });
        }

        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.bg_navigationview);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = (int) getResources().getDimension(R.dimen.dialog_width);
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.gravity = Gravity.START;

            LinearLayout tongquan = dialog.findViewById(R.id.layoutTongQuan);
            LinearLayout cacgiaodich = dialog.findViewById(R.id.layoutCacGiaoDich);
            LinearLayout cactaikhoan = dialog.findViewById(R.id.layoutCacTaiKhoan);
            LinearLayout ngansach = dialog.findViewById(R.id.layoutNganSach);
            LinearLayout nguoidung = dialog.findViewById(R.id.layoutNguoiDung);
            LinearLayout qlhangmuc = dialog.findViewById(R.id.layoutQuanlyHangMuc);
            LinearLayout gioithieu = dialog.findViewById(R.id.layoutGioiThieu);

            tongquan.setOnClickListener(view -> startActivity(new Intent(getActivity(), Ctrl_TongQuan.class)));
            cactaikhoan.setOnClickListener(view -> startActivity(new Intent(getActivity(), Ctrl_CacTaiKhoan.class)));
            cacgiaodich.setOnClickListener(view -> startActivity(new Intent(getActivity(), Ctrl_CacGiaoDich.class)));
            ngansach.setOnClickListener(view -> startActivity(new Intent(getActivity(), Ctrl_NganSach.class)));
            nguoidung.setOnClickListener(view -> startActivity(new Intent(getActivity(), Ctrl_NguoiDung.class)));
            qlhangmuc.setOnClickListener(view -> startActivity(new Intent(getActivity(), Ctrl_QuanLyHangMuc.class)));
            gioithieu.setOnClickListener(view -> startActivity(new Intent(getActivity(), Ctrl_GioiThieu.class)));

            window.setAttributes(layoutParams);
            window.setGravity(Gravity.START);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            window.setWindowAnimations(R.style.DialogAnimation_Left);
        }

        dialog.show();
    }


    public void loadThuChiThangHienTai() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        // Get current month and year
        Calendar calendar = Calendar.getInstance();
        int thangHienTai = calendar.get(Calendar.MONTH) + 1; // Month (1-12)
        int namHienTai = calendar.get(Calendar.YEAR); // Year

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        dbRef.child("HangMuc").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot hangMucSnap) {
                HashMap<String, String> hangMucMap = new HashMap<>();
                for (DataSnapshot hangMuc : hangMucSnap.getChildren()) {
                    String idHangMuc = hangMuc.getKey();
                    String idNhom = hangMuc.child("idNhom").getValue(String.class);
                    if (idHangMuc != null && idNhom != null) {
                        hangMucMap.put(idHangMuc, idNhom);
                    }
                }

                // Load data from GiaoDich table
                dbRef.child("GiaoDich").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot giaoDichSnap) {
                        long tongThuNhap = 0;
                        long tongChiPhi = 0;

                        for (DataSnapshot giaoDich : giaoDichSnap.getChildren()) {
                            String giaoDichUserId = giaoDich.child("userId").getValue(String.class);
                            if (userId.equals(giaoDichUserId)) { // Check userId
                                String idHangMuc = giaoDich.child("idHangMuc").getValue(String.class);
                                Double giaTri = giaoDich.child("giaTri").getValue(Double.class);

                                // Get date info
                                Integer ngay = giaoDich.child("ngayTao/ngay").getValue(Integer.class);
                                Integer thang = giaoDich.child("ngayTao/thang").getValue(Integer.class);
                                Integer nam = giaoDich.child("ngayTao/nam").getValue(Integer.class);

                                if (idHangMuc != null && giaTri != null && ngay != null && thang != null && nam != null) {
                                    // Check if the transaction belongs to the current month and year
                                    if (thang == thangHienTai && nam == namHienTai) {
                                        String idNhom = hangMucMap.get(idHangMuc);

                                        if ("1".equals(idNhom)) { // Group 1 is income
                                            tongThuNhap += giaTri;
                                        } else if ("2".equals(idNhom)) { // Group 2 is expense
                                            tongChiPhi += giaTri;
                                        }
                                    }
                                }
                            }
                        }

                        // Calculate percentages for income and expenses
                        long tongCong = tongThuNhap + tongChiPhi;
                        int percentThuNhap = 0;
                        int percentChiPhi = 0;

                        if (tongCong > 0) { // Ensure total is not zero
                            percentThuNhap = (int) ((tongThuNhap * 100) / tongCong);
                            percentChiPhi = (int) ((tongChiPhi * 100) / tongCong);
                        }

                        // Format numbers
                        NumberFormat format = NumberFormat.getInstance(Locale.forLanguageTag("vi-VN"));
                        String formattedThuNhap = format.format(tongThuNhap) + "đ";
                        String formattedChiPhi = format.format(tongChiPhi) + "đ";

                        // Update ProgressBar
                        progressBarThuNhap.setMax(100); // Ensure max is 100
                        progressBarChiPhi.setMax(100); // Ensure max is 100
                        progressBarThuNhap.setProgress(percentThuNhap);
                        progressBarChiPhi.setProgress(percentChiPhi);

                        // Update display values
                        txtTongThuNhap.setText(formattedThuNhap);
                        txtTongChiPhi.setText(formattedChiPhi);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Firebase", "Error querying GiaoDich: " + error.getMessage());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error querying HangMuc: " + error.getMessage());
            }
        });
    }

    //Hien thi ten hang muc
    private void loadHangMuc() {
        DatabaseReference hangMucRef = FirebaseDatabase.getInstance().getReference("HangMuc");
        hangMucRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hangMucMap.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    M_DanhMucHangMuc hangMuc = dataSnapshot.getValue(M_DanhMucHangMuc.class);
                    if (hangMuc != null) {
                        hangMucMap.put(hangMuc.getIdHangmuc(), hangMuc);  // Lưu đối tượng M_DanhMucHangMuc vào map
                    }
                }
                loadGiaoDich();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Không thể tải dữ liệu hạng mục", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Hien thi ten tai khoan
    private void loadTaiKhoan() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Lấy UID của người dùng hiện tại
        DatabaseReference taiKhoanRef = FirebaseDatabase.getInstance().getReference("TaiKhoan");

        // Thêm điều kiện lọc theo userId
        taiKhoanRef.orderByChild("userId").equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taiKhoanMap.clear(); // Xóa dữ liệu cũ
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    M_TaiKhoan taiKhoan = dataSnapshot.getValue(M_TaiKhoan.class);
                    if (taiKhoan != null) {
                        taiKhoanMap.put(taiKhoan.getIdTaiKhoan(), taiKhoan.getTenTaiKhoan());
                    }
                }
                loadGiaoDich(); // Gọi phương thức để tải giao dịch sau khi đã có dữ liệu tài khoản
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Không thể tải dữ liệu tài khoản", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadGiaoDich() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Lấy UID của người dùng hiện tại
        giaoDichRef.orderByChild("userId").equalTo(userId).limitToLast(4).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                giaoDichList.clear(); // Xóa dữ liệu cũ

                // Duyệt qua từng giao dịch
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    M_GiaoDich giaoDich = dataSnapshot.getValue(M_GiaoDich.class);

                    // Lấy tên hạng mục
                    M_DanhMucHangMuc hangMuc = hangMucMap.get(giaoDich.getIdHangMuc());
                    String tenHangMuc = (hangMuc != null) ? hangMuc.getTenHangmuc() : "Không xác định";

                    // Lấy tên tài khoản
                    String tenTaiKhoan = taiKhoanMap.get(giaoDich.getIdTaiKhoan());
                    if (tenTaiKhoan == null) {
                        tenTaiKhoan = "Không xác định";
                    }

                    // Gán tên vào giao dịch
                    giaoDich.setIdHangMuc(tenHangMuc);
                    giaoDich.setIdTaiKhoan(tenTaiKhoan);

                    // Thêm giao dịch vào danh sách
                    giaoDichList.add(giaoDich);
                }

                // Cập nhật adapter sau khi thêm giao dịch vào danh sách
                giaoDichAdapter.notifyDataSetChanged();

                // Cập nhật giao diện
                updateUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI() {
        if (giaoDichList.isEmpty()) {
            rvCacGiaoDich.setVisibility(View.GONE);
            imgEmptyState.setVisibility(View.VISIBLE);
            txtEmptyState.setVisibility(View.VISIBLE);
        } else {
            rvCacGiaoDich.setVisibility(View.VISIBLE);
            imgEmptyState.setVisibility(View.GONE);
            giaoDichAdapter.notifyDataSetChanged();
        }
    }

    private void loadCacTaiKhoan() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Lấy UID của người dùng hiện tại
        taiKhoanRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taiKhoanList.clear(); // Xóa danh sách cũ
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    M_TaiKhoan taiKhoan = dataSnapshot.getValue(M_TaiKhoan.class);
                    if (taiKhoan != null &&userId.equals(taiKhoan.getUserId())) {
                        taiKhoanList.add(taiKhoan); // Thêm tài khoản vào danh sách
                    }
                }

                // Đảo ngược danh sách để sắp xếp giảm dần (ngày tạo gần nhất trước)
                Collections.reverse(taiKhoanList);

                // Cập nhật giao diện RecyclerView
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Không thể tải dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void tinhTongTienCuaTatCaTaiKhoan() {
        // Biến để lưu tổng số tiền của tất cả các tài khoản
        double tongTienTatCa = 0;

        // Duyệt qua tất cả các tài khoản trong danh sách taiKhoanList
        for (M_TaiKhoan taiKhoan : taiKhoanList) {
            // Lấy số tiền của mỗi tài khoản và cộng vào tổng
            double soTien = taiKhoan.getLuongBanDau(); // Giả sử phương thức getLuongBanDau() trả về số tiền của tài khoản
            tongTienTatCa += soTien; // Cộng dồn vào tổng số tiền
        }

        // Chuyển đổi tổng số tiền thành chuỗi và hiển thị lên TextView
        String tongTienStr = String.format("%.2f", tongTienTatCa); // Định dạng số tiền với 2 chữ số thập phân
        amountTextView.setText(tongTienStr); // Hiển thị tổng số tiền lên TextView
    }




}