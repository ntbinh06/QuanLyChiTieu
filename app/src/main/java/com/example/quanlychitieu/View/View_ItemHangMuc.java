package com.example.quanlychitieu.View;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.quanlychitieu.Model.DanhMucHangMuc;
import com.example.quanlychitieu.R;

import java.util.ArrayList;

public class View_ItemHangMuc extends ArrayAdapter<DanhMucHangMuc> {
    private final Activity context;
    private final int idLayout;
    private final ArrayList<DanhMucHangMuc> myList;
    private final FragmentManager fragmentManager;

    public View_ItemHangMuc(Activity context, int idLayout, ArrayList<DanhMucHangMuc> myList, FragmentManager fragmentManager) {
        super(context, idLayout, myList);
        this.context = context;
        this.idLayout = idLayout;
        this.myList = myList;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflater = context.getLayoutInflater();
        convertView = myInflater.inflate(idLayout, null);

        DanhMucHangMuc hangMuc = myList.get(position);
        ImageView img_HM = convertView.findViewById(R.id.image_hangMuc);
        img_HM.setImageResource(hangMuc.getImage());

        TextView tenHM = convertView.findViewById(R.id.name);
        tenHM.setText(hangMuc.getTenHangMuc());

        ImageButton icEye = convertView.findViewById(R.id.ic_eye);
        icEye.setOnClickListener(v -> openDialogFragment());

        return convertView;
    }

    private void openDialogFragment() {
        Fragment_Them_Hang_Muc dialogFragment = new Fragment_Them_Hang_Muc();
        dialogFragment.show(fragmentManager, "ThemHangMuc");
    }
}