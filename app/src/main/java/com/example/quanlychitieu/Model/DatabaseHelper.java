package com.example.quanlychitieu.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbQUANLYCHITIEU.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và cột
    private static final String TABLE_HANGMUC = "Hangmuc";
    private static final String TABLE_GIAODICH = "CacGiaoDich";

    // Cột của bảng Hangmuc
    private static final String COLUMN_ID_HANGMUC = "Idhangmuc";
    private static final String COLUMN_TEN_HANGMUC = "Tenhangmuc";
    private static final String COLUMN_ANH_HANGMUC = "Anhhangmuc";
    private static final String COLUMN_NGANSACH_DUTRU = "ngansachdutru";
    private static final String COLUMN_ID_NHOM = "idnhom";
    private static final String COLUMN_ID_USER = "Iduser";

    // Cột của bảng CacGiaoDich
    private static final String COLUMN_ID_GIAODICH = "idGiaoDich";
    private static final String COLUMN_GIA_TRI = "giaTri";
    private static final String COLUMN_ID_HANGMUC_GIAODICH = "Idhangmuc";
    private static final String COLUMN_ID_TAI_KHOAN = "Idtaikhoan";
    private static final String COLUMN_NGAY_TAO = "ngayTao";
    private static final String COLUMN_TU = "Tu";
    private static final String COLUMN_GHI_CHU = "ghiChu";
    private static final String COLUMN_HINH_ANH = "hinhAnh";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Hangmuc
        String createHangmucTable = "CREATE TABLE " + TABLE_HANGMUC + " (" +
                COLUMN_ID_HANGMUC + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TEN_HANGMUC + " NVARCHAR(50), " +
                COLUMN_ANH_HANGMUC + " VARCHAR(250), " +
                COLUMN_NGANSACH_DUTRU + " DECIMAL(18, 0), " +
                COLUMN_ID_NHOM + " INTEGER, " +
                COLUMN_ID_USER + " INTEGER)";
        db.execSQL(createHangmucTable);

        // Tạo bảng CacGiaoDich
        String createGiaoDichTable = "CREATE TABLE " + TABLE_GIAODICH + " (" +
                COLUMN_ID_GIAODICH + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_GIA_TRI + " DECIMAL(18,0), " +
                COLUMN_ID_HANGMUC_GIAODICH + " INTEGER, " +
                COLUMN_ID_TAI_KHOAN + " INTEGER, " +
                COLUMN_NGAY_TAO + " DATE, " +
                COLUMN_TU + " NVARCHAR(50), " +
                COLUMN_GHI_CHU + " NVARCHAR(1000), " +
                COLUMN_HINH_ANH + " VARCHAR(200), " +
                "FOREIGN KEY (" + COLUMN_ID_HANGMUC_GIAODICH + ") REFERENCES " + TABLE_HANGMUC + "(" + COLUMN_ID_HANGMUC + "))";
        db.execSQL(createGiaoDichTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GIAODICH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HANGMUC);
        onCreate(db);
    }



    // Phương thức chèn giao dịch
    public boolean insertTransaction(double value, int categoryId, int accountId, String date, String from, String note, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_GIA_TRI, value);
        contentValues.put(COLUMN_ID_HANGMUC_GIAODICH, categoryId);
        contentValues.put(COLUMN_ID_TAI_KHOAN, accountId);
        contentValues.put(COLUMN_NGAY_TAO, date);
        contentValues.put(COLUMN_TU, from);
        contentValues.put(COLUMN_GHI_CHU, note);
        contentValues.put(COLUMN_HINH_ANH, image);
        long result = db.insert(TABLE_GIAODICH, null, contentValues);
        return result != -1; // trả về true nếu chèn thành công
    }

    // Phương thức lấy danh sách hạng mục
    public List<String> getAllHangMuc() {
        List<String> hangMucList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_TEN_HANGMUC + " FROM " + TABLE_HANGMUC, null);
        if (cursor.moveToFirst()) {
            do {
                hangMucList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return hangMucList;
    }


    public boolean insertTransaction(Model_GiaoDich transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_GIA_TRI, transaction.getValue());
        contentValues.put(COLUMN_ID_HANGMUC_GIAODICH, transaction.getCategoryId());
        contentValues.put(COLUMN_ID_TAI_KHOAN, transaction.getAccountId());
        contentValues.put(COLUMN_NGAY_TAO, transaction.getDate());
        contentValues.put(COLUMN_TU, transaction.getFrom());
        contentValues.put(COLUMN_GHI_CHU, transaction.getNote());
        contentValues.put(COLUMN_HINH_ANH, transaction.getImage());

        long result = db.insert(TABLE_GIAODICH, null, contentValues);
        return result != -1; // trả về true nếu chèn thành công
    }
}