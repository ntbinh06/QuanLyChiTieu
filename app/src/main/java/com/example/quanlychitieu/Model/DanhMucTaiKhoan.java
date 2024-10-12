package com.example.quanlychitieu.Model;

public class DanhMucTaiKhoan {
    private String tenTaiKhoan;
    private String tien;

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }


    public String getTien() {
        return tien;
    }

    public void setTien(String tien) {
        this.tien = tien;
    }

    public DanhMucTaiKhoan(String tenTaiKhoan, String tien) {
        this.tenTaiKhoan = tenTaiKhoan;
        this.tien = tien;
    }
}
