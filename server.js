const express = require('express');
const path = require('path');
const { initializeApp } = require('firebase/app');
const { getDatabase, ref, get } = require('firebase/database');

const app = express();
const PORT = process.env.PORT || 3049;

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

app.use(express.json());      // Xử lý JSON từ client 

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




// Các route khác
app.get('/', (req, res) => {
  res.render('DangNhap', { title: 'Đăng nhập' });
});
app.get('/XemChiTietUser', (req, res) => {
  res.render('XemChiTietUser.ejs');
});

///THÊM THU NHẬP
app.post('/HangMucThuNhap', async (req, res) => {
  console.log("Yêu cầu POST nhận được:", req.body);  // Kiểm tra dữ liệu từ client
  const { tenHangmuc } = req.body;  // Chỉ cần lấy tên hạng mục từ client

  // Tạo ID ngẫu nhiên hoặc lấy key mới từ Firebase
  const newCategoryRef = ref(database, 'HangMuc').push();
  const newCategoryData = {
    idHangmuc: newCategoryRef.key, // Lấy key ngẫu nhiên làm ID
    idNhom: "1",                   // Gán cố định là chuỗi "1"
    tenHangmuc
  };

  // Thêm vào Firebase
  newCategoryRef.set(newCategoryData)
    .then(() => res.json({ success: true }))
    .catch(err => res.status(500).json({ success: false, error: err.message }));
});


app.get('/HangMucThuNhap', async (req, res) => {
  try {
    const categoryRef = ref(database, 'HangMuc'); // Tham chiếu tới bảng HangMuc
    const snapshot = await get(categoryRef);

    let categoryList = []; // Khởi tạo danh sách hạng mục
    if (snapshot.exists()) {
      const data = snapshot.val();
      for (const id in data) {
        if (data[id].idNhom === "1") { // So sánh trực tiếp với chuỗi "1"
          categoryList.push({
            tenHangMuc: data[id].tenHangmuc || "Không có tên",
            anhHangMuc: '../images/money.png',
          });        
        }
      }
    }

    // Truyền danh sách hạng mục vào file QuanLyHangMuc.ejs
    res.render('HangMucThuNhap', { categoryList });
  } catch (error) {
    console.error("Lỗi khi đọc dữ liệu Firebase: ", error);
    res.render('HangMucThuNhap', { categoryList: [] }); // Truyền danh sách rỗng khi lỗi
  }
});


app.post('/HangMucChiPhi', async (req, res) => {
  const { tenHangmuc } = req.body;

  if (!tenHangmuc) {
    return res.status(400).json({ success: false, error: "Tên hạng mục không được để trống." });
  }

  const idHangmuc = `HM-${Date.now()}`; // Tạo ID tùy chỉnh, ví dụ: HM-<timestamp>
  const newCategoryData = {
    idHangmuc,
    idNhom: "2",
    tenHangmuc,
  };

  try {
    await set(ref(database, `HangMuc/${idHangmuc}`), newCategoryData);
    res.json({ success: true, data: newCategoryData });
  } catch (err) {
    res.status(500).json({ success: false, error: err.message });
  }
});

app.get('/HangMucChiPhi', async (req, res) => {
  try {
    const categoryRef = ref(database, 'HangMuc'); // Tham chiếu tới bảng HangMuc
    const snapshot = await get(categoryRef);

    let categoryList = []; // Khởi tạo danh sách hạng mục
    if (snapshot.exists()) {
      const data = snapshot.val();
      for (const id in data) {
        if (data[id].idNhom === "2") { // So sánh trực tiếp với chuỗi "1"
          categoryList.push({
            tenHangMuc: data[id].tenHangmuc || "Không có tên",
            anhHangMuc: '../images/money.png',
          });        
        }
      }
    }

    // Truyền danh sách hạng mục vào file QuanLyHangMuc.ejs
    res.render('HangMucChiPhi', { categoryList });
  } catch (error) {
    console.error("Lỗi khi đọc dữ liệu Firebase: ", error);
    res.render('HangMucChiPhi', { categoryList: [] }); // Truyền danh sách rỗng khi lỗi
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