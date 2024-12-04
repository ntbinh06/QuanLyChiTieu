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
        this.luongBanDau = 0.0; // Giá trị mặc định
        this.ngayTao = new Date(); // Ngày tạo mặc định
        this.donViTienTe = "VND"; // Đơn vị tiền tệ mặc định
        this.ghiChu = ""; // Ghi chú mặc định
    }

    public M_TaiKhoan(String idTaiKhoan, String tenTaiKhoan, String userId, Double luongBanDau) {
        this.idTaiKhoan = idTaiKhoan;
        this.tenTaiKhoan = tenTaiKhoan;
        this.luongBanDau = luongBanDau != null ? luongBanDau : 0.0; // Giá trị mặc định nếu null
        this.ngayTao = new Date(); // Ngày tạo mặc định
        this.lanSuDungCuoi = null; // Giá trị mặc định
        this.donViTienTe = "VND"; // Đơn vị tiền tệ mặc định
        this.ghiChu = ""; // Ghi chú mặc định
        this.userId = userId; // Khởi tạo userId
    }

    // Constructor đầy đủ
    public M_TaiKhoan(String idTaiKhoan, String tenTaiKhoan, Double luongBanDau, Date ngayTao, Date lanSuDungCuoi, String donViTienTe, String ghiChu, String userId) {
        this.idTaiKhoan = idTaiKhoan;
        this.tenTaiKhoan = tenTaiKhoan;
        this.luongBanDau = luongBanDau != null ? luongBanDau : 0.0; // Giá trị mặc định nếu null
        this.ngayTao = ngayTao != null ? ngayTao : new Date(); // Ngày tạo mặc định
        this.lanSuDungCuoi = lanSuDungCuoi; // Giá trị có thể null
        this.donViTienTe = donViTienTe != null ? donViTienTe : "VND"; // Đơn vị tiền tệ mặc định
        this.ghiChu = ghiChu != null ? ghiChu : ""; // Ghi chú mặc định
        this.userId = userId; // Khởi tạo userId
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