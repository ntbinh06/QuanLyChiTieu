package com.example.quanlychitieu.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public class M_GiaoDich {
    private String idGiaoDich;
    private double giaTri;
    private String idHangMuc;
    private String tenHangMuc;
    private String idTaiKhoan;
    private String tenTaiKhoan;
    private Map<String, Object> ngayTao;  // Sử dụng Map cho ngày
    private String tu;
    private String ghiChu;
    private String idNhom;
    private String userId;

    // Constructor mặc định
    public M_GiaoDich() {}

    // Constructor có tham số
    public M_GiaoDich(String idGiaoDich, double giaTri, String idHangMuc, String idTaiKhoan, Map<String, Object> ngayTao, String tu, String ghiChu) {
        this.idGiaoDich = idGiaoDich;
        this.giaTri = giaTri;
        this.idHangMuc = idHangMuc;
        this.idTaiKhoan = idTaiKhoan;
        this.ngayTao = ngayTao;
        this.tu = tu;
        this.ghiChu = ghiChu;
    }

    // Getters và Setters
    public String getIdGiaoDich() {
        return idGiaoDich;
    }

    public void setIdGiaoDich(String idGiaoDich) {
        this.idGiaoDich = idGiaoDich;
    }

    public double getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(double giaTri) {
        this.giaTri = giaTri;
    }

    public String getIdHangMuc() {
        return idHangMuc;
    }

    public void setIdHangMuc(String idHangMuc) {
        this.idHangMuc = idHangMuc;
    }

    public String getTenHangMuc() {
        return tenHangMuc;
    }

    public void setTenHangMuc(String tenHangMuc) {
        this.tenHangMuc = tenHangMuc;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(String idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }

    public Map<String, Object> getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Map<String, Object> ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTu() {
        return tu;
    }

    public void setTu(String tu) {
        this.tu = tu;
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
    // Phương thức chuyển đổi Map thành String
    public String getFormattedNgayTao() {
        if (ngayTao != null) {
            int ngay = Integer.parseInt(ngayTao.get("ngay").toString());
            int thang = Integer.parseInt(ngayTao.get("thang").toString());
            int nam = Integer.parseInt(ngayTao.get("nam").toString());

            Calendar calendar = Calendar.getInstance();
            calendar.set(nam, thang - 1, ngay); // Tháng trong Calendar bắt đầu từ 0

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format(calendar.getTime()); // Trả về định dạng ngày
        }
        return ""; // Trả về chuỗi rỗng nếu ngày là null
    }
}