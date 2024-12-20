package com.example.quanlychitieu.Model;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class M_DanhMucHangMuc  {
    private String idHangmuc;
    private String tenHangmuc;
    private String anhHangmuc;
    private Double nganSachDuTru;
    private String idNhom;
    private String userId;
    private Map<String, Integer> ngayTaoNganSach;

    // Constructor mặc định (Firebase cần)ac
    public M_DanhMucHangMuc() {
    }

    public M_DanhMucHangMuc(String idHangmuc,String tenHangmuc  ) {
        this.idHangmuc= idHangmuc;
        this.tenHangmuc = tenHangmuc;
    }

    public M_DanhMucHangMuc(String idHangmuc, String tenHangmuc, String anhHangmuc) {
        this.idHangmuc = idHangmuc;
        this.tenHangmuc = tenHangmuc;
        this.anhHangmuc = anhHangmuc;
    }

    // Constructor đầy đủ
    public M_DanhMucHangMuc(String idHangmuc, String tenHangmuc, String anhHangmuc, Double nganSachDuTru, String idNhom, String idUser) {
        this.idHangmuc = idHangmuc;
        this.tenHangmuc = tenHangmuc;
        this.anhHangmuc = anhHangmuc;
        this.nganSachDuTru = nganSachDuTru;
        this.idNhom = idNhom;
        this.userId = idUser;
    }

    // Getter và Setter
    public String getIdHangmuc() {
        return idHangmuc;
    }

    public void setIdHangmuc(String idHangmuc) {
        this.idHangmuc = idHangmuc;
    }

    public String getAnhHangmuc() {
        return anhHangmuc;
    }

    public void setAnhHangmuc(String anhHangmuc) {
        this.anhHangmuc = anhHangmuc;
    }

    public String getTenHangmuc() {
        return tenHangmuc;
    }

    public void setTenHangmuc(String tenHangmuc) {
        this.tenHangmuc = tenHangmuc;
    }

    public Double getNganSachDuTru() {
        return nganSachDuTru;
    }

    public void setNganSachDuTru(Double nganSachDuTru) {
        this.nganSachDuTru = nganSachDuTru;
    }

    public String getIdNhom() {
        return idNhom;
    }

    public void setIdNhom(String idNhom) {
        this.idNhom = idNhom;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, Integer> getNgayTaoNganSach() {
        return ngayTaoNganSach;
    }

    public void setNgayTaoNganSach(Map<String, Integer> ngayTaoNganSach) {
        this.ngayTaoNganSach = ngayTaoNganSach;
    }

    // Phương thức này sẽ giúp bạn định dạng lại ngày tháng
    public String getFormattedNgayTao() {
        int year = ngayTaoNganSach.get("nam");
        int month = ngayTaoNganSach.get("thang");
        int day = ngayTaoNganSach.get("ngay");

        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return sdf.format(calendar.getTime());
    }
}
