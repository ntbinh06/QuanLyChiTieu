const express = require('express');
const path = require('path');
const { initializeApp } = require('firebase/app');
const { getDatabase, ref, get, set } = require('firebase/database');

const app = express();
const PORT = process.env.PORT || 3007;

app.use(express.json());
app.use(express.static(path.join(__dirname, 'public'))); // Phục vụ các file tĩnh từ thư mục public

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
const database = getDatabase(firebaseApp);

// Thiết lập view engine
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'src', 'views'));

app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);
});

// Lấy danh sách người dùng từ Firebase và gửi cho client
app.get('/QuanLyNguoiDung', async (req, res) => {
  try {
    const userRef = ref(database, 'NguoiDung');
    const snapshot = await get(userRef);

    if (snapshot.exists()) {
      const data = snapshot.val();
      const userList = [];

      // Thêm ID người dùng vào danh sách
      for (const id in data) {
        userList.push({
          id: id,  // Thêm ID người dùng
          avatar: '../images/user_women.png',
          name: data[id].tenUser,
          email: data[id].email,
        });
      }

      res.render('QuanLyNguoiDung', { userList: userList.length > 0 ? userList : [] });
    } else {
      res.render('QuanLyNguoiDung', { userList: [] });
    }
  } catch (error) {
    console.error("Lỗi khi đọc dữ liệu Firebase: ", error);
    res.render('QuanLyNguoiDung', { userList: [] });
  }
});

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

// Route để xem chi tiết người dùng
app.get('/XemChiTietUser/:userId', async (req, res) => {
  try {
      const userId = req.params.userId;
      console.log("Đang lấy thông tin cho userId:", userId);  // Log userId
      const userRef = ref(database, 'NguoiDung/' + userId);
      const snapshot = await get(userRef);

      if (snapshot.exists()) {
          const userData = snapshot.val();
          console.log("Dữ liệu người dùng tìm thấy:", userData);  // Log userData
          const userDetails = {
              avatar: userData.avatar || '../images/user_women.png',
              name: userData.tenUser,
              birthday: userData.ngaySinh,
              phone: userData.SDT,
              email: userData.email,
          };

          res.render('XemChiTietUser', { userDetails });
      } else {
          console.log("Không tìm thấy người dùng.");  // Log nếu không tìm thấy người dùng
          res.status(404).send('Không tìm thấy người dùng');
      }
  } catch (error) {
      console.error("Lỗi khi truy xuất thông tin người dùng:", error);
      res.status(500).send('Lỗi máy chủ nội bộ');
  }
});

// Route để tìm kiếm người dùng
app.get('/tim-kiem', async (req, res) => {
  const searchName = req.query.name.toLowerCase(); // Lấy tên từ query string
  const userRef = ref(database, 'NguoiDung');
  
  try {
      const snapshot = await get(userRef);
      let userList = [];

      if (snapshot.exists()) {
          const data = snapshot.val();
          for (const id in data) {
              const user = {
                  id: id,
                  avatar: '../images/user_women.png',
                  name: data[id].tenUser,
                  email: data[id].email,
              };
              // Kiểm tra xem tên người dùng có chứa chuỗi tìm kiếm không
              if (user.name.toLowerCase().includes(searchName)) {
                  userList.push(user);
              }
          }
      }
      
      // Render danh sách người dùng khớp với tìm kiếm
      res.render('QuanLyNguoiDung', { userList });
  } catch (error) {
      console.error("Lỗi khi tìm kiếm người dùng: ", error);
      res.status(500).send('Lỗi máy chủ nội bộ');
  }
});

// Route để chuyển đổi trạng thái khóa tài khoản
app.post('/toggle-lock/:userId', async (req, res) => {
  const userId = req.params.userId;
  const { lock } = req.body; // Trạng thái mới từ client

  // Kiểm tra xem có gửi dữ liệu lock hay không
  if (lock === undefined) {
      return res.status(400).json({ message: 'Trạng thái lock không được cung cấp.' });
  }

  // Tham chiếu đến người dùng trong Firebase
  const userRef = ref(database, 'NguoiDung/' + userId);
  
  try {
      const snapshot = await get(userRef);
      if (snapshot.exists()) {
          const userData = snapshot.val();

          // Cập nhật trạng thái lock
          userData.lock = lock; // Đặt lock thành giá trị mới từ client

          // Lưu thông tin cập nhật vào Firebase
          await set(userRef, userData);
          res.json({ message: 'Thành công!' });
      } else {
          res.status(404).json({ message: 'Không tìm thấy người dùng.' });
      }
  } catch (error) {
      console.error("Lỗi khi thay đổi trạng thái khóa:", error);
      res.status(500).json({ message: 'Lỗi máy chủ nội bộ' });
  }
});