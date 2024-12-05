const express = require('express');
const path = require('path');
const { initializeApp } = require('firebase/app');
const { getDatabase, ref, get } = require('firebase/database');
const { update } = require('firebase/database'); // Import hàm update

const app = express();
const PORT = process.env.PORT || 3007;

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
          id: id, // Thêm userId
          avatar: '../images/user_women.png',
          name: data[id].tenUser,
          email: data[id].email,
          lock: data[id].lock || false, // Thêm trạng thái lock
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

// Route xử lý toggle trạng thái khóa
app.post('/toggleLock', express.json(), async (req, res) => {
  const { userId, lock } = req.body; // Lấy userId và trạng thái mới từ request body

  if (!userId) {
    return res.status(400).send('Thiếu userId!');
  }

  try {
    const userRef = ref(database, `NguoiDung/${userId}`); // Tham chiếu đến người dùng
    await update(userRef, { lock }); // Cập nhật trạng thái khóa trong Firebase

    res.status(200).send('Cập nhật trạng thái thành công!');
  } catch (error) {
    console.error('Lỗi khi cập nhật trạng thái khóa:', error);
    res.status(500).send('Lỗi máy chủ!');
  }
});

// Các route khác
app.get('/', (req, res) => {
  res.render('DangNhap', { title: 'Đăng nhập' });
});

//Xemchitiet
app.get('/XemChiTietUser', async (req, res) => {
  const userId = req.query.userId; // Lấy userId từ query string

  if (!userId) {
    return res.send('Không tìm thấy userId!');
  }

  try {
    const userRef = ref(database, `NguoiDung/${userId}`); // Truy cập vào userId trong Firebase
    const snapshot = await get(userRef);

    if (snapshot.exists()) {
      const userDetails = snapshot.val();
      res.render('XemChiTietUser', {
        name: userDetails.tenUser || "Không có tên",
        birthDate: userDetails.ngaySinh || "Không có ngày sinh",
        phone: userDetails.SDT || "Không có số điện thoại",
        email: userDetails.email || "Không có email",
        avatar: userDetails.avatar || "../images/user_women.png", // Avatar mặc định
      });
    } else {
      res.status(404).send('Người dùng không tồn tại!');
    }
  } catch (error) {
    console.error('Lỗi khi lấy dữ liệu người dùng: ', error);
    res.status(500).send('Lỗi máy chủ!');
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