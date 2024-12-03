const express = require('express');
const path = require('path');
const { initializeApp } = require('firebase/app');
const { getDatabase, ref, get } = require('firebase/database');

const app = express();
const PORT = process.env.PORT || 3034;

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
        // Truy xuất userCount để truyền vào TrangChu
        const userCount = Object.keys(users).length;

        // Truyền thêm userCount vào template TrangChu.ejs
        res.render('TrangChu', { user: userInfo, userCount });
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
app.get('/QuanLyHangMuc', async (req, res) => {
  try {
    const categoryRef = ref(database, 'HangMuc'); // Tham chiếu tới bảng HangMuc
    const snapshot = await get(categoryRef);

    let categoryList = []; // Khởi tạo danh sách hạng mục
    if (snapshot.exists()) {
      const data = snapshot.val();
      for (const id in data) {
        categoryList.push({
          tenHangMuc: data[id].tenHangmuc || "Không có tên", // Tên hạng mục
          anhHangMuc: '../images/money.png',
        });
      }
    }

    // Truyền danh sách hạng mục vào file QuanLyHangMuc.ejs
    res.render('QuanLyHangMuc', { categoryList });
  } catch (error) {
    console.error("Lỗi khi đọc dữ liệu Firebase: ", error);
    res.render('QuanLyHangMuc', { categoryList: [] }); // Truyền danh sách rỗng khi lỗi
  }
});

app.get('/TrangChu', async (req, res) => {
  try {
    const userRef = ref(database, 'NguoiDung'); // Tham chiếu đến bảng NguoiDung
    const snapshot = await get(userRef);

    let userCount = 0;
    let allUsers = [];

    if (snapshot.exists()) {
      const data = snapshot.val();
      userCount = Object.keys(data).length; // Đếm tổng số người dùng

      // Duyệt qua tất cả người dùng và thêm vào danh sách
      for (const id in data) {
        const user = data[id];
        allUsers.push({
          avatar: user.avatar || '../images/binh.png', // Avatar mặc định
          name: user.tenUser || "Người dùng không tên",
          email: user.email || "Không có email",
        });
      }
    }

    // Render giao diện TrangChu với danh sách tất cả người dùng
    res.render('TrangChu', { userCount, activeUsers: allUsers });
  } catch (error) {
    console.error("Lỗi khi đọc dữ liệu Firebase: ", error);
    res.render('TrangChu', { userCount: 0, activeUsers: [] });
  }
});



app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);
});
