package com.example.quanlychitieu.Model;

public class M_TaiKhoan {

    private String loaiTaiKhoan, lansdCuoi;
    private long tongSoTien;

    public M_TaiKhoan(String loaiTaiKhoan, String lansdCuoi, long tongSoTien) {
        this.loaiTaiKhoan = loaiTaiKhoan;
        this.lansdCuoi = lansdCuoi;
        this.tongSoTien = tongSoTien;
    }

    public String getLoaiTaiKhoan() {
        return loaiTaiKhoan;
    }

    public void setLoaiTaiKhoan(String loaiTaiKhoan) {
        this.loaiTaiKhoan = loaiTaiKhoan;
    }

    public String getLansdCuoi() {
        return lansdCuoi;
    }

    public void setLansdCuoi(String lansdCuoi) {
        this.lansdCuoi = lansdCuoi;
    }

    public long getTongSoTien() {
        return tongSoTien;
    }

    public void setTongSoTien(long tongSoTien) {
        this.tongSoTien = tongSoTien;
    }

    public String getFormattedSoTien() {
        return String.format("%,d đ", tongSoTien);
    }
}
