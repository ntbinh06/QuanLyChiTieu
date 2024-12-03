package com.example.quanlychitieu.View;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.PrimitiveIterator;

import com.example.quanlychitieu.Controller.Ctrl_IconAdapter;
import com.example.quanlychitieu.Model.M_DanhMucHangMuc;
import com.example.quanlychitieu.Model.M_IconManager;
import com.example.quanlychitieu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fragment_Them_Hang_Muc extends DialogFragment {
    private static final String ARG_ID_HANG_MUC = "id_hang_muc";
    private static final String ARG_ID_NHOM = "id_nhom"; // Key để truyền idNhom
    private static final String ARG_TEN_HANG_MUC = "ten_hang_muc";
    private static final String ARG_ANH_HANG_MUC = "anh_hang_muc";

    private String idHangMuc; // Lưu trữ ID của hạng mục (nếu có)
    private int idNhom; // Lưu trữ idNhom
    private String tenHangMuc;
    private String anhHangMuc;

    private ImageView selectedImageView;
    private RecyclerView recyclerView;
    private EditText edtTenHangMuc;
    private Button btnCancel, btnSave;

    private int selectedIcon = -1; // Lưu ID ảnh được chọn
    private DatabaseReference hangMucRef;

    public static Fragment_Them_Hang_Muc newInstance(String idHangMuc,int idNhom, String tenHangMuc, String anhHangMuc) {
        Fragment_Them_Hang_Muc fragment = new Fragment_Them_Hang_Muc();
        Bundle args = new Bundle();
        args.putString(ARG_ID_HANG_MUC, idHangMuc);
        args.putInt(ARG_ID_NHOM, idNhom);
        args.putString(ARG_TEN_HANG_MUC, tenHangMuc);
        args.putString(ARG_ANH_HANG_MUC, anhHangMuc);
        fragment.setArguments(args);
        return fragment;
    }

    // Phương thức newInstance để truyền idNhom
    public static Fragment_Them_Hang_Muc newInstance(int idNhom) {
        Fragment_Them_Hang_Muc fragment = new Fragment_Them_Hang_Muc();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_NHOM, idNhom);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idHangMuc = getArguments().getString(ARG_ID_HANG_MUC);
            idNhom = getArguments().getInt(ARG_ID_NHOM); // Lấy giá trị idNhom từ arguments
            tenHangMuc = getArguments().getString(ARG_TEN_HANG_MUC); // Có thể null
            anhHangMuc = getArguments().getString(ARG_ANH_HANG_MUC); // Có thể null
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__them__hang__muc, container, false);

        // Ánh xạ các thành phần giao diện
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnSave = view.findViewById(R.id.btn_save);
        selectedImageView = view.findViewById(R.id.selected_image);
        recyclerView = view.findViewById(R.id.recyclerViewIcons);
        edtTenHangMuc = view.findViewById(R.id.edt_name);

        // Khởi tạo Firebase reference
        hangMucRef = FirebaseDatabase.getInstance().getReference("HangMuc");

        // Lấy danh sách icon từ IconManager
        List<Integer> iconList = M_IconManager.getIconList();

        // Tạo adapter và truyền danh sách icon vào
        Ctrl_IconAdapter adapter = new Ctrl_IconAdapter(iconList, selectedIconResId -> {
            selectedIcon = selectedIconResId;
            selectedImageView.setImageResource(selectedIconResId);
        });

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 4));
        recyclerView.setAdapter(adapter);

        // Hiển thị dữ liệu truyền vào nếu đang chỉnh sửa (Edit)
        if (!TextUtils.isEmpty(tenHangMuc)) {
            edtTenHangMuc.setText(tenHangMuc);
        }

        if (!TextUtils.isEmpty(anhHangMuc)) {
            int imgResId = getResources().getIdentifier(anhHangMuc, "drawable", requireContext().getPackageName());
            if (imgResId != 0) {
                selectedImageView.setImageResource(imgResId);
                selectedIcon = imgResId; // Cập nhật icon đã chọn
            }
        }
        // Xử lý nút Hủy
        btnCancel.setOnClickListener(v -> dismiss());

        // Xử lý nút Lưu
        btnSave.setOnClickListener(v -> saveHangMuc());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            Window window = getDialog().getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    private void saveHangMuc() {
        String tenHangMucMoi = edtTenHangMuc.getText().toString().trim();
        if (TextUtils.isEmpty(tenHangMucMoi)) {
            Toast.makeText(getContext(), "Vui lòng nhập tên hạng mục!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedIcon == -1) {
            Toast.makeText(getContext(), "Vui lòng chọn một ảnh!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy UID của người dùng hiện tại
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Nếu ID hạng mục không rỗng thì cập nhật
        if (!TextUtils.isEmpty(idHangMuc)) {
            hangMucRef.child(idHangMuc).setValue(new M_DanhMucHangMuc(
                    idHangMuc,
                    tenHangMucMoi,
                    getResources().getResourceEntryName(selectedIcon),
                    null,
                    String.valueOf(idNhom),
                    userId // Thêm userId vào đây
            )).addOnSuccessListener(aVoid -> {
                Toast.makeText(getContext(), "Cập nhật hạng mục thành công!", Toast.LENGTH_SHORT).show();
                dismiss();
            }).addOnFailureListener(e -> {
                Toast.makeText(getContext(), "Lỗi khi cập nhật: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        } else {
            // Thêm mới hạng mục
            String idHangMucMoi = hangMucRef.push().getKey();
            hangMucRef.child(idHangMucMoi).setValue(new M_DanhMucHangMuc(
                    idHangMucMoi,
                    tenHangMucMoi,
                    getResources().getResourceEntryName(selectedIcon),
                    null,
                    String.valueOf(idNhom),
                    userId // Thêm userId vào đây
            )).addOnSuccessListener(aVoid -> {
                Toast.makeText(getContext(), "Thêm hạng mục thành công!", Toast.LENGTH_SHORT).show();
                dismiss();
            }).addOnFailureListener(e -> {
                Toast.makeText(getContext(), "Lỗi khi thêm: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }
}