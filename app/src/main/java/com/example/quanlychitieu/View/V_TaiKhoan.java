package com.example.quanlychitieu.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.quanlychitieu.Controller.C_SideMenu;
import com.example.quanlychitieu.Model.M_SideMenu;
import com.example.quanlychitieu.Model.M_TaiKhoan;
import com.example.quanlychitieu.R;

import java.util.ArrayList;
import java.util.List;

public class V_TaiKhoan extends ArrayAdapter<M_TaiKhoan>{

    private Context context;
    private int resource;
    private List<M_TaiKhoan> arrContact;


    public V_TaiKhoan(Context context, int resource, ArrayList<M_TaiKhoan> arrContact) {
        super(context, resource, arrContact);
        this.context = context;
        this.resource = resource;
        this.arrContact = arrContact;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        V_TaiKhoan.ViewHolder viewHolder;

        if (convertView == null) {
            // Inflate the custom layout for each item
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);

            // Initialize ViewHolder to hold references to views
            viewHolder = new V_TaiKhoan.ViewHolder();
            viewHolder.tvLoaiTaiKhoan = (TextView) convertView.findViewById(R.id.loaiTaiKhoan);
            viewHolder.tvTongSoTien = (TextView) convertView.findViewById(R.id.soTien);
            viewHolder.tvLanSDCuoi = (TextView) convertView.findViewById(R.id.lancuoiSuDung);

            // Set the tag for future reuse
            convertView.setTag(viewHolder);
        } else {
            // Reuse the ViewHolder if already present
            viewHolder = (V_TaiKhoan.ViewHolder) convertView.getTag();
        }

        // Get the current M_SideMenu item
        M_TaiKhoan contact = arrContact.get(position);

        // Set the data to the views
        viewHolder.tvLoaiTaiKhoan.setText(contact.getLoaiTaiKhoan());


        // Return the completed view to render on screen
        return convertView;
    }

    // ViewHolder class to hold references to views for better performance
    public static class ViewHolder {
        TextView tvLoaiTaiKhoan, tvTongSoTien, tvLanSDCuoi;

    }
}
