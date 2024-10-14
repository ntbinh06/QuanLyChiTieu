package com.example.quanlychitieu.View;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlychitieu.Model.DanhMucGiaoDich;
import com.example.quanlychitieu.Model.DanhMucHangMuc;
import com.example.quanlychitieu.R;

import java.util.ArrayList;

public class View_ItemHangMuc extends ArrayAdapter <DanhMucHangMuc> {
    Activity context;
    int idLayout;
    ArrayList<DanhMucHangMuc> myList;

    public View_ItemHangMuc(Activity context, int idLayout, ArrayList<DanhMucHangMuc> myList) {
        super(context, idLayout, myList);
        this.context = context;
        this.idLayout = idLayout;
        this.myList = myList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //tạo để chứa layout
        LayoutInflater myFlater= context.getLayoutInflater();
        //đặt id layout lên đé để tạo thành view
        convertView= myFlater.inflate(idLayout,null);
        //lấy 1 phần tử trong mảng
        DanhMucHangMuc hangMuc = myList.get(position);
        //khai báo,tham chiếu id và hiển thị hình aảnh lên ImageView
        ImageView img_HM= convertView.findViewById(R.id.image_hangMuc);
        img_HM.setImageResource(hangMuc.getImage());

        //hiển thị tên DD
        TextView tenHM = convertView.findViewById(R.id.name);
        tenHM.setText(hangMuc.getTenHangMuc());

        return  convertView;
    }
}
