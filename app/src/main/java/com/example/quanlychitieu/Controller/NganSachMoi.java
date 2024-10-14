package com.example.quanlychitieu.Controller;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.View_CustomSpinner;

import java.util.ArrayList;
import java.util.List;

public class NganSachMoi extends AppCompatActivity implements View_CustomSpinner.OnSpinnerEventsListener{

    private View_CustomSpinner spnTaiKhoan, spnDateStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ngan_sach_moi);

        // Initialize Spinner for Tai Khoan
        spnTaiKhoan = findViewById(R.id.spnTaiKhoan);
        spnTaiKhoan.setSpinnerEventsListener(this);

        // List for Tai Khoan Spinner
        ArrayList<String> listTaiKhoan = new ArrayList<>();
        listTaiKhoan.add("Ví");
        listTaiKhoan.add("MB Bank");
        listTaiKhoan.add("Tiết kiệm");

        // Adapter for Tai Khoan Spinner
        ArrayAdapter<String> adapterTaiKhoan = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listTaiKhoan);
        adapterTaiKhoan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTaiKhoan.setAdapter(adapterTaiKhoan);

        spnTaiKhoan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(NganSachMoi.this, spnTaiKhoan.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        spnTaiKhoan.setDropDownVerticalOffset(150);

        // Initialize Spinner for Date Start
        spnDateStart = findViewById(R.id.spnDateStart);
        spnDateStart.setSpinnerEventsListener(this);

        // List for Date Start Spinner
        ArrayList<String> listDateStart = new ArrayList<>();
        listDateStart.add("1 Tuần");
        listDateStart.add("1 Tháng");
        listDateStart.add("1 Năm");

        // Adapter for Date Start Spinner
        ArrayAdapter<String> adapterDateStart = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listDateStart);
        adapterDateStart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDateStart.setAdapter(adapterDateStart);

        spnDateStart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(NganSachMoi.this, spnDateStart.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        spnDateStart.setDropDownVerticalOffset(150);

        // Window insets handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onPopupWindowOpened(Spinner spinner) {
        if (spinner.getId() == R.id.spnTaiKhoan) {
            spnTaiKhoan.setBackground(getResources().getDrawable(R.drawable.bg_spinner_up, null));
        } else if (spinner.getId() == R.id.spnDateStart) {
            spnDateStart.setBackground(getResources().getDrawable(R.drawable.bg_spinner_up, null));
        }
    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {
        if (spinner.getId() == R.id.spnTaiKhoan) {
            spnTaiKhoan.setBackground(getResources().getDrawable(R.drawable.bg_spinner, null));
        } else if (spinner.getId() == R.id.spnDateStart) {
            spnDateStart.setBackground(getResources().getDrawable(R.drawable.bg_spinner, null));
        }
    }
}
