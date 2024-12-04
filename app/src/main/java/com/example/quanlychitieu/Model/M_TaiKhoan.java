package com.example.quanlychitieu.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class M_TaiKhoan {

    private String idTaiKhoan;
    private String tenTaiKhoan;
    private Double luongBanDau;
    private Date ngayTao;
    private Date lanSuDungCuoi;
    private String donViTienTe;
    private String ghiChu;
    private String userId;

    // Constructor không tham số

    public M_TaiKhoan() {
    }

    public M_TaiKhoan(String idTaiKhoan, String tenTaiKhoan, String userId) {
        this.idTaiKhoan = idTaiKhoan;
        this.tenTaiKhoan = tenTaiKhoan;
        this.luongBanDau = 0.0; // Default value
        this.ngayTao = new Date(); // Default value
        this.lanSuDungCuoi = null; // Default value
        this.donViTienTe = "VND"; // Default value
        this.ghiChu = ""; // Default value
        this.userId = userId; // Initialize userId
    }

    // Constructor đầy đủ
    public M_TaiKhoan(String idTaiKhoan, String tenTaiKhoan, Double luongBanDau, Date ngayTao, Date lanSuDungCuoi, String donViTienTe, String ghiChu, String userId) {
        this.idTaiKhoan = idTaiKhoan;
        this.tenTaiKhoan = tenTaiKhoan;
        this.luongBanDau = luongBanDau;
        this.ngayTao = ngayTao;
        this.lanSuDungCuoi = lanSuDungCuoi;
        this.donViTienTe = donViTienTe;
        this.ghiChu = ghiChu;
        this.userId = userId;
    }

    // Getters và Setters
    public String getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(String idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public Double getLuongBanDau() {
        return luongBanDau;
    }

    public void setLuongBanDau(Double luongBanDau) {
        this.luongBanDau = luongBanDau;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Date getLanSuDungCuoi() {
        return lanSuDungCuoi;
    }

    public void setLanSuDungCuoi(Date lanSuDungCuoi) {
        this.lanSuDungCuoi = lanSuDungCuoi;
    }

    public String getDonViTienTe() {
        return donViTienTe;
    }

    public void setDonViTienTe(String donViTienTe) {
        this.donViTienTe = donViTienTe;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Hàm định dạng tiền tệ
    public String getFormattedLuongBanDau() {
        return String.format("%,.0f %s", luongBanDau, donViTienTe);
    }

    // Hàm định dạng ngày thành chuỗi
    public String getFormattedNgayTao() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return ngayTao != null ? sdf.format(ngayTao) : "N/A";
    }

    public String getFormattedLanSuDungCuoi() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return lanSuDungCuoi != null ? sdf.format(lanSuDungCuoi) : "N/A";
    }



}
