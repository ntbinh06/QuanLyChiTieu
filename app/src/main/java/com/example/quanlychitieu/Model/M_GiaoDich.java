package com.example.quanlychitieu.Model;


import java.text.SimpleDateFormat;
import java.util.Date;

public class M_GiaoDich {
    private String idGiaoDich;
    private double giaTri;
    private String idHangMuc;
    private String idTaiKhoan;
    private Date ngayTao; // Sử dụng Date
    private String tu;
    private String ghiChu;

    // Constructor mặc định
    public M_GiaoDich() {}

    // Constructor có tham số
    public M_GiaoDich(String idGiaoDich, double giaTri, String idHangMuc,String idTaiKhoan, Date ngayTao, String tu, String ghiChu) {
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

    public String getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(String idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
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


    // Phương thức chuyển đổi Date thành String
    public String getFormattedNgayTao() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng ngày
        return ngayTao != null ? dateFormat.format(ngayTao) : ""; // Trả về chuỗi định dạng hoặc chuỗi rỗng nếu ngày là null
    }
}