const express = require('express');
const path = require('path');
const { initializeApp } = require('firebase/app');
const { getDatabase, ref, get } = require('firebase/database');

const app = express();
const PORT = process.env.PORT || 3029;

// Cấu hình Firebase
const firebaseConfig = {
  apiKey: "AIzaSyCUIodjJLiYL8bdZajc6OFrWXwyKL9TpS8",
  authDomain: "quanlychitieu-5c040.firebaseapp.com",
  databaseURL: "https://quanlychitieu-5c040-default-rtdb.firebaseio.com",
  projectId: "quanlychitieu-5c040",
  storageBucket: "quanlychitieu-5c040.appspot.com",
  messagingSenderId: "999318802101",
  appId: "1:999318802101:web:8e58f43525cfa277ab38fc",
  measurementId: "G-HZV0RZ95KJ"
};

// Khởi tạo Firebase
const firebaseApp = initializeApp(firebaseConfig);
const database = getDatabase(firebaseApp);

// Middleware xử lý dữ liệu form
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

// Phục vụ các file tĩnh từ thư mục public
app.use(express.static(path.join(__dirname, 'public')));

// Thiết lập view engine
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'src', 'views'));

// Route cho trang chủ (trang đăng nhập)
app.get('/', (req, res) => {
  res.render('DangNhap', { error: null });
});

// Route xử lý đăng nhập
app.post('/DangNhap', async (req, res) => {
  const { email, password } = req.body;
  try {
    const userRef = ref(database, 'NguoiDung');
    const snapshot = await get(userRef);

    if (snapshot.exists()) {
      const users = snapshot.val();
      let isAuthenticated = false;
      let userInfo = null;

      // Kiểm tra thông tin người dùng
      for (const id in users) {
        if (users[id].email === email && users[id].matKhau === password) {
          isAuthenticated = true;
          userInfo = users[id];
          break;
        }
      }

      if (isAuthenticated) {
        res.render('TrangChu', { user: userInfo }); // Chuyển đến Trang Chủ với thông tin người dùng
      } else {
        res.render('DangNhap', { error: 'Email hoặc mật khẩu không chính xác!' });
      }
    } else {
      res.render('DangNhap', { error: 'Không tìm thấy dữ liệu người dùng!' });
    }
  } catch (error) {
    console.error("Lỗi khi đăng nhập: ", error);
    res.status(500).send('Lỗi hệ thống');
  }
});

// Route hiển thị danh sách người dùng
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

      res.render('QuanLyNguoiDung', { userList });
    } else {
      res.render('QuanLyNguoiDung', { userList: [] });
    }
  } catch (error) {
    console.error("Lỗi khi đọc dữ liệu Firebase: ", error);
    res.render('QuanLyNguoiDung', { userList: [] });
  }
});

// Route để xem chi tiết người dùng
app.get('/XemChiTietUser/:userId', async (req, res) => {
  try {
    const userId = req.params.userId;
    const userRef = ref(database, 'NguoiDung/' + userId);
    const snapshot = await get(userRef);

    if (snapshot.exists()) {
      const userData = snapshot.val();
      const userDetails = {
        avatar: userData.avatar || '../images/user_women.png',
        name: userData.tenUser,
        birthday: userData.ngaySinh,
        phone: userData.sdt,
        email: userData.email,
      };

      res.render('XemChiTietUser', { userDetails });
    } else {
      res.status(404).send('User not found');
    }
  } catch (error) {
    console.error("Error retrieving user details:", error);
    res.status(500).send('Internal Server Error');
  }
});

// Route hiển thị thông tin admin
app.get('/ThongTinAdmin', (req, res) => {
  res.render('ThongTinAdmin');
});

// Route trang chủ sau khi đăng nhập thành công
app.get('/TrangChu', (req, res) => {
  res.render('TrangChu', { user: null }); // Bạn có thể truyền thông tin người dùng từ phiên
});

// Bắt đầu server
app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);
});
