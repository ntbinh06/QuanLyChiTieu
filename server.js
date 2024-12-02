const express = require('express');
const path = require('path');
const { initializeApp } = require('firebase/app');
const { getDatabase, ref, get } = require('firebase/database');

const app = express();
const PORT = process.env.PORT || 3010;

// Cấu hình Firebase
const firebaseConfig = {
  apiKey: "AIzaSyCUIodjJLiYL8bdZajc6OFrWXwyKL9TpS8",
  authDomain: "quanlychitieu-5c040.firebaseapp.com",
  databaseURL: "https://quanlychitieu-5c040-default-rtdb.firebaseio.com",
  projectId: "quanlychitieu-5c040",
  storageBucket: "quanlychitieu-5c040.firebasestorage.app",
  messagingSenderId: "999318802101",
  appId: "1:999318802101:web:8e58f43525cfa277ab38fc",
  measurementId: "G-HZV0RZ95KJ"
};

// Khởi tạo Firebase
const firebaseApp = initializeApp(firebaseConfig);

// Kết nối với Realtime Database
const database = getDatabase(firebaseApp);

// Phục vụ các file tĩnh từ thư mục public
app.use(express.static(path.join(__dirname, 'public')));

// Thiết lập view engine
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'src', 'views'));

// Lấy danh sách người dùng từ Firebase và gửi cho client
app.get('/QuanLyNguoiDung', async (req, res) => {
  try {
    const userRef = ref(database, 'NguoiDung');
    const snapshot = await get(userRef);

    if (snapshot.exists()) {
      const data = snapshot.val();
      const userList = [];

      for (const id in data) {
        userList.push({
          avatar: '../images/user_women.png',
          name: data[id].tenUser,
          email: data[id].email,
        });
      }

      // Kiểm tra nếu userList có dữ liệu trước khi render
      if (userList.length > 0) {
        res.render('QuanLyNguoiDung', { userList });
      } else {
        res.render('QuanLyNguoiDung', { userList: [] });
      }
    } else {
      // Nếu không có dữ liệu từ Firebase, truyền userList rỗng
      res.render('QuanLyNguoiDung', { userList: [] });
    }
  } catch (error) {
    console.error("Lỗi khi đọc dữ liệu Firebase: ", error);
    res.render('QuanLyNguoiDung', { userList: [] });
  }
});

<<<<<<< HEAD
app.get('/QuanLyNguoiDung', (req, res) => {
    res.render('QuanLyNguoiDung.ejs');
});

app.get('/XemChiTietUser', (req, res) => {
    res.render('XemChiTietUser.ejs');
});

app.get('/ThongTinAdmin', (req, res) => {
    res.render('ThongTinAdmin.ejs');
});

app.get('/TrangChu', (req, res) => {
    res.render('TrangChu.ejs');
});

// Bắt đầu server
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
=======

// Các route khác
app.get('/', (req, res) => {
  res.render('DangNhap', { title: 'Đăng nhập' });
});
app.get('/XemChiTietUser', (req, res) => {
  res.render('XemChiTietUser.ejs');
});
app.get('/ThongTinAdmin', (req, res) => {
  res.render('ThongTinAdmin.ejs');
});
app.get('/TrangChu', (req, res) => {
  res.render('TrangChu.ejs');
});
app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);
>>>>>>> b989df8e2070ca5ea50f5e95ef3a7dba4bc0a9d8
});
