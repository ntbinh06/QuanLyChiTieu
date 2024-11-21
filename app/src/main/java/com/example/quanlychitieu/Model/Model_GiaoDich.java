package com.example.quanlychitieu.Model;

public class Model_GiaoDich {
    private int id;                // ID của giao dịch
    private double value;          // Giá trị của giao dịch
    private int categoryId;        // ID của hạng mục
    private int accountId;         // ID của tài khoản
    private String date;           // Ngày giao dịch
    private String from;           // Nguồn của giao dịch
    private String note;           // Ghi chú
    private String image;          // Đường dẫn hình ảnh (nếu có)


    // Constructor
    public Model_GiaoDich(int id, double value, int categoryId, int accountId, String date, String from, String note, String image) {
        this.id = id;
        this.value = value;
        this.categoryId = categoryId;
        this.accountId = accountId;
        this.date = date;
        this.from = from;
        this.note = note;
        this.image = image;
    }

    // Getter và Setter cho id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho value
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    // Getter và Setter cho categoryId
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    // Getter và Setter cho accountId
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    // Getter và Setter cho date
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Getter và Setter cho from
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    // Getter và Setter cho note
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // Getter và Setter cho image
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Phương thức toString để hiển thị thông tin giao dịch
    @Override
    public String toString() {
        return "Model_GiaoDich{" +
                "id=" + id +
                ", value=" + value +
                ", categoryId=" + categoryId +
                ", accountId=" + accountId +
                ", date='" + date + '\'' +
                ", from='" + from + '\'' +
                ", note='" + note + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}