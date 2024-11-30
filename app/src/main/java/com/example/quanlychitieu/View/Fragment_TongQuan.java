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
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlychitieu.Controller.Ctrl_GioiThieu;
import com.example.quanlychitieu.Controller.Ctrl_NganSach;
import com.example.quanlychitieu.Controller.Ctrl_CacGiaoDich;
import com.example.quanlychitieu.Controller.Ctrl_CacTaiKhoan;
import com.example.quanlychitieu.Controller.Ctrl_NguoiDung;
import com.example.quanlychitieu.Controller.Ctrl_RecyclerViewItemClickListener;
import com.example.quanlychitieu.Controller.Ctrl_TongQuan;
import com.example.quanlychitieu.Controller.Ctrl_QuanLyHangMuc;
import com.example.quanlychitieu.Controller.Ctrl_XemTKChiTiet;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_TongQuan extends Fragment {

    private ImageView btnMenu, btnXemCacTaiKhoan, btnXemCacGiaoDich;
    private TextView amountTextView;
    private ImageView eyeIcon;
    private boolean isAmountVisible = true;
    private RecyclerView rvCacTaiKhoan;
    private List<M_TaiKhoan> taiKhoanList = new ArrayList<>();
    private Map<String, String> taiKhoanMap = new HashMap<>();
    private V_TongQuan_CacTaiKhoan myAdapter;

    public Fragment_TongQuan() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout cho Fragment
        View view = inflater.inflate(R.layout.activity_tong_quan, container, false);

        btnMenu = view.findViewById(R.id.btnMenu);
        btnXemCacTaiKhoan = view.findViewById(R.id.all_cactaikhoan);
        btnXemCacGiaoDich = view.findViewById(R.id.all_cacgiaodich);

        // Khởi tạo RecyclerView và Adapter
        rvCacTaiKhoan = view.findViewById(R.id.recyclerviewGD);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvCacTaiKhoan.addItemDecoration(dividerItemDecoration);
        taiKhoanList = new ArrayList<>();
        myAdapter = new V_TongQuan_CacTaiKhoan(getContext(), taiKhoanList);
        rvCacTaiKhoan.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCacTaiKhoan.setAdapter(myAdapter);


        //Hien thi Tong So Du
        eyeIcon = view.findViewById(R.id.ic_eye);
        amountTextView = view.findViewById(R.id.txtTongTienSH);

        final Drawable eyeClosed = ContextCompat.getDrawable(getContext(), R.drawable.eye_of); // Đảm bảo icon đóng mắt là đúng
        final Drawable eyeOpened = ContextCompat.getDrawable(getContext(), R.drawable.eye_of); // Đảm bảo icon mở mắt là đúng

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





        //HIEN THI DANH SACH CAC KHOAN
        DatabaseReference taiKhoanRef = FirebaseDatabase.getInstance().getReference("TaiKhoan");
        taiKhoanRef.orderByChild("ngayTao")
                .limitToFirst(3)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        taiKhoanList.clear(); // Xóa danh sách cũ
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            M_TaiKhoan taiKhoan = dataSnapshot.getValue(M_TaiKhoan.class);
                            taiKhoanList.add(taiKhoan); // Thêm tài khoản vào danh sách
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

        // Thiết lập sự kiện click cho btnMenu
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
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





}