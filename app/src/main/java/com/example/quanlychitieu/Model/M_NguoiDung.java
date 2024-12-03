package com.example.quanlychitieu.Model;

public class M_NguoiDung {
    private String idUser;          // Iduser
    private String tenUser;      // Tenuser
    private String email;        // Email
    private String ngaySinh;     // Ngaysinh (dùng String để dễ dàng định dạng)
    private String sdt;          // Sdt
    private String matKhau;
    private boolean lock;
    private String anhDaiDien;

    public M_NguoiDung() {

    }

    // Constructor dùng để đăng ký, chỉ với tên, email và mật khẩu
    public M_NguoiDung(String idUser, String tenUser, String email, String matKhau) {
        this.idUser = idUser;
        this.tenUser = tenUser;
        this.email = email;
        this.matKhau = matKhau;
        this.ngaySinh = null; // Chưa có
        this.sdt = null; // Chưa có
        this.anhDaiDien = null;
        this.lock = false;
    }

    // Constructor đầy đủ, dùng khi cập nhật thông tin
    public M_NguoiDung(String idUser, String tenUser, String email, String ngaySinh, String sdt, String matKhau, String anhDaiDien) {
        this.idUser = idUser;
        this.tenUser = tenUser;
        this.email = email;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.matKhau = matKhau;
        this.anhDaiDien = anhDaiDien;
    }



    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getTenUser() {
        return tenUser;
    }

    public void setTenUser(String tenUser) {
        this.tenUser = tenUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }
    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }
}
