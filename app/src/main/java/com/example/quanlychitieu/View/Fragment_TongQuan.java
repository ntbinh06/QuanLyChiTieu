package com.example.quanlychitieu.View;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.example.quanlychitieu.Controller.Ctrl_GioiThieu;
import com.example.quanlychitieu.Controller.Ctrl_NganSach;
import com.example.quanlychitieu.Controller.Ctrl_CacGiaoDich;
import com.example.quanlychitieu.Controller.Ctrl_CacTaiKhoan;
import com.example.quanlychitieu.Controller.Ctrl_NguoiDung;
import com.example.quanlychitieu.Controller.Ctrl_TongQuan;
import com.example.quanlychitieu.Controller.Ctrl_QuanLyHangMuc;
import com.example.quanlychitieu.R;

public class Fragment_TongQuan extends Fragment {

    private ImageView btnMenu, btnXemCacTaiKhoan, btnXemCacGiaoDich;
    private TextView amountTextView;
    private ImageView eyeIcon;
    private boolean isAmountVisible = true;


    public Fragment_TongQuan() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout cho Fragment
        View view = inflater.inflate(R.layout.activity_tong_quan, container, false);

        btnMenu = view.findViewById(R.id.btnMenu);
        btnXemCacTaiKhoan = view.findViewById(R.id.all_cactaikhoan);
        btnXemCacGiaoDich = view.findViewById(R.id.all_cacgiaodich);

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

        amountTextView = view.findViewById(R.id.txtTongTienSH);
        eyeIcon = view.findViewById(R.id.ic_eye);


        return view;
    }

    private void deleteTransaction(int transactionId) {
        // Thêm logic xóa giao dịch từ cơ sở dữ liệu tại đây
        // Ví dụ:
        // SQLiteDatabase db = getWritableDatabase();
        // db.delete("transactions", "id=?", new String[]{String.valueOf(transactionId)});
    }

    private void showCustomPopupWindow(View anchorView, boolean isTaiKhoanMenu) {
        // Inflate layout tùy chỉnh cho popup menu
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_menu_custom, null);

        // Khởi tạo PopupWindow với kích thước WRAP_CONTENT
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Đặt focusable để có thể bấm ra ngoài và tắt popup
        popupWindow.setFocusable(true);

        popupWindow.showAsDropDown(anchorView, anchorView.getWidth(), -20);

        // Set sự kiện cho các item trong popup
        TextView menuItem1 = popupView.findViewById(R.id.menu_item_1);


        if (isTaiKhoanMenu) {
            // Menu cho tài khoản
            menuItem1.setText("Xem tất cả");

            menuItem1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển sang màn hình xem tất cả tài khoản
                    Intent intent = new Intent(getActivity(), Ctrl_CacTaiKhoan.class);
                    startActivity(intent);
                    popupWindow.dismiss();
                }
            });
        } else {
            // Menu cho giao dịch
            menuItem1.setText("Xem tất cả");

            menuItem1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển sang màn hình xem tất cả giao dịch
                    Intent intent = new Intent(getActivity(), Ctrl_CacGiaoDich.class);
                    startActivity(intent);
                    popupWindow.dismiss();
                }
            });

        }



    }

    private void showDialog() {

        Dialog dialog = new Dialog(getContext()); // Sử dụng getContext() để lấy context
        dialog.setContentView(R.layout.tongquan_menu);


        // Thiết lập kích thước của Dialog
        Window window = dialog.getWindow();
        if (window != null) {
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_navigationview);// Tùy chỉnh layout của bạn
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());

            layoutParams.width = (int) getResources().getDimension(R.dimen.dialog_width); // Chiều rộng cố định (300dp)
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT; // Chiều cao bằng màn hình
            layoutParams.gravity = Gravity.START; // Nằm sát mép trái

            LinearLayout tongquan = dialog.findViewById(R.id.layoutTongQuan);
            LinearLayout cacgiaodich = dialog.findViewById(R.id.layoutCacGiaoDich);
            LinearLayout cactaikhoan = dialog.findViewById(R.id.layoutCacTaiKhoan);
            LinearLayout ngansach = dialog.findViewById(R.id.layoutNganSach);
            LinearLayout nguoidung = dialog.findViewById(R.id.layoutNguoiDung);
            LinearLayout qlhangmuc = dialog.findViewById(R.id.layoutQuanlyHangMuc);
            LinearLayout gioithieu = dialog.findViewById(R.id.layoutGioiThieu);

            tongquan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Ctrl_TongQuan.class);
                    startActivity(intent);
                }
            });

            cactaikhoan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Ctrl_CacTaiKhoan.class);
                    startActivity(intent);
                }
            });

            cacgiaodich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Ctrl_CacGiaoDich.class);
                    startActivity(intent);
                }
            });

            ngansach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Ctrl_NganSach.class);
                    startActivity(intent);
                }
            });

            nguoidung.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Ctrl_NguoiDung.class);
                    startActivity(intent);
                }
            });

            qlhangmuc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Ctrl_QuanLyHangMuc.class);
                    startActivity(intent);
                }
            });

            gioithieu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Ctrl_GioiThieu.class);
                    startActivity(intent);
                }
            });
            window.setAttributes(layoutParams);
            window.setGravity(Gravity.START); // Hiển thị từ bên trái
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            window.setWindowAnimations(R.style.DialogAnimation_Left); // Áp dụng animation nếu cần
        }


        dialog.show();
    }

}
