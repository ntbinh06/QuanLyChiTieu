package com.example.quanlychitieu.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.example.quanlychitieu.R;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.IconViewHolder> {

    private List<Integer> icons;
    private OnIconClickListener listener;

    // Constructor nhận danh sách icon và callback khi người dùng chọn icon
    public IconAdapter(List<Integer> icons, OnIconClickListener listener) {
        this.icons = icons;
        this.listener = listener;
    }

    // Giao diện callback để xử lý sự kiện khi chọn icon
    public interface OnIconClickListener {
        void onIconClick(int iconResId);
    }

    // ViewHolder quản lý từng item của RecyclerView
    public static class IconViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;

        public IconViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.icon_image);
        }
    }

    @NonNull
    @Override
    public IconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_icon, parent, false);
        return new IconViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IconViewHolder holder, int position) {
        int iconResId = icons.get(position);
        holder.iconImage.setImageResource(iconResId);

        // Xử lý sự kiện click vào icon
        holder.itemView.setOnClickListener(v -> listener.onIconClick(iconResId));
    }

    @Override
    public int getItemCount() {
        return icons.size();
    }
}

