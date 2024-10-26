package com.example.quanlychitieu.View;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlychitieu.Controller.CacGiaoDich;
import com.example.quanlychitieu.Controller.CacTaiKhoan;
import com.example.quanlychitieu.Controller.Ctrl_XemChiPhi;
import com.example.quanlychitieu.Controller.Ctrl_XemTKChiTiet;
import com.example.quanlychitieu.Model.DanhMucGiaoDich;
import com.example.quanlychitieu.Model.DanhMucTaiKhoan;
import com.example.quanlychitieu.R;

import java.util.ArrayList;

public class View_ItemCacTK extends ArrayAdapter<DanhMucTaiKhoan> {
    Activity context;
    int idLayout;
    ArrayList<DanhMucTaiKhoan> myList;
    public View_ItemCacTK(Activity context, int idLayout, ArrayList<DanhMucTaiKhoan> myList) {
        super(context, idLayout,myList);
        this.context = context;
        this.idLayout = idLayout;
        this.myList = myList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myFlater= context.getLayoutInflater();
        convertView= myFlater.inflate(idLayout,null);

        DanhMucTaiKhoan taikhoan= myList.get(position);
        TextView tentk= convertView.findViewById(R.id.tenTK);
        TextView tien = convertView.findViewById(R.id.tien);
        ImageView iconXemChiTiet = convertView.findViewById(R.id.IconxemChiTiet);
        ImageView iconXoaTaiKhoan =  convertView.findViewById(R.id.ic_deleteTaiKhoan);


        tentk.setText(taikhoan.getTenTaiKhoan());
        tien.setText(taikhoan.getTien()+" đ");

        // Xử lý sự kiện khi nhấn vào icon con mắt
        iconXemChiTiet.setOnClickListener(v -> {
            // Tạo Intent để chuyển sang Activity XemTaiKhoanChiTiet
            Intent intent = new Intent(context, Ctrl_XemTKChiTiet.class);

            // Truyền dữ liệu tài khoản qua Intent
            intent.putExtra("tenTK", taikhoan.getTenTaiKhoan());
            intent.putExtra("sodu", taikhoan.getTien());

            // Khởi động Activity
            context.startActivity(intent);
        });

        iconXoaTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.custom_dialog_xoa, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(dialogView);

                TextView tvMessage = dialogView.findViewById(R.id.tvMessage);
                Button btnDelete = dialogView.findViewById(R.id.btnDelete);
                Button btnCancel = dialogView.findViewById(R.id.btnCancel);

                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_dialog);

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteTransaction(taikhoan.getTenTaiKhoan()); // Truyền tên tài khoản để xóa
                        dialog.dismiss();

                        Intent intent = new Intent(context, CacTaiKhoan.class);
                        context.startActivity(intent);
                    }
                });


                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        return convertView;
    }

    private void deleteTransaction(String tenTaiKhoan) {
        // Logic xóa tài khoản từ cơ sở dữ liệu dựa trên tên tài khoản
        // Ví dụ:
        // SQLiteDatabase db = getWritableDatabase();
        // db.delete("taiKhoan", "tenTaiKhoan=?", new String[]{tenTaiKhoan});
    }

}
