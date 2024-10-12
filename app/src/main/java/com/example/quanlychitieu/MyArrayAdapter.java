package com.example.quanlychitieu;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<DanhMucGiaoDich> {
    Activity context;
    int idLayout;
    ArrayList<DanhMucGiaoDich> myList;

    //tạo constructor để layout gọi đến
    public MyArrayAdapter(Activity context, int idLayout, ArrayList<DanhMucGiaoDich> myList) {
        super(context, idLayout,myList);
        this.context = context;
        this.idLayout = idLayout;
        this.myList = myList;
    }

    //gọi hàm getView-> tiến hành sắp xếp dữ liệu
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //tạo để chứa layout
        LayoutInflater myFlater= context.getLayoutInflater();
        //đặt id layout lên đé để tạo tahfnh view
        convertView= myFlater.inflate(idLayout,null);
        //lấy 1 phần tử trong mảng
        DanhMucGiaoDich giaoDich = myList.get(position);
        //khai báo,tham chiếu id và hiển thị hình aảnh lên ImageView
        ImageView img_GD= convertView.findViewById(R.id.img_DM);
        img_GD.setImageResource(giaoDich.getImage());

        //hiển thị tên DD
        TextView tenGD = convertView.findViewById(R.id.textDescription);
        tenGD.setText(giaoDich.getTenGD());

        //hiển thị tài khoản
        TextView taiKhoan = convertView.findViewById(R.id.textAccount);
        taiKhoan.setText(giaoDich.getTaikhoan());

        //hiển thị tiền
        TextView tien = convertView.findViewById(R.id.textAmount);
        tien.setText(giaoDich.getTien());

        //hiển thị Ngày
        TextView ngay = convertView.findViewById(R.id.textDate);
        ngay.setText(giaoDich.getNgay());
        return  convertView;
    }
}
