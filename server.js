const express = require('express');
const path = require('path');
const { initializeApp } = require('firebase/app');
const { getDatabase, ref, get, set, push } = require('firebase/database');
const { update } = require('firebase/database'); // Import hàm update

const app = express();
const PORT = process.env.PORT || 3051;

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

// Lấy danh sách người dùng từ Firebase 
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

    // Gửi danh sách kết quả về client
    res.status(200).json(userList);
  } catch (error) {
    console.error("Lỗi khi tìm kiếm:", error);
    res.status(500).send('Lỗi máy chủ khi tìm kiếm người dùng!');
  }
});

// Các route khác
app.get('/', (req, res) => {
  res.render('DangNhap', { title: 'Đăng nhập' });
});

// API Đăng nhập
app.post("/login", async (req, res) => {
  const { email, password } = req.body;

  // Kiểm tra dữ liệu đầu vào
  if (!email || !password) {
    console.log("Thiếu email hoặc password");
    return res.status(400).json({ message: "Vui lòng nhập email và mật khẩu." });
  }

  try {
    // Lấy dữ liệu từ Firebase
    const adminRef = ref(database, "Admin");
    const snapshot = await get(adminRef);

    if (snapshot.exists()) {
      const admin = snapshot.val();
      console.log("Dữ liệu từ Firebase:", admin);

      // Kiểm tra thông tin đăng nhập
      if (admin.email.toLowerCase().trim() === email.toLowerCase().trim() && admin.matkhau.trim() === password.trim()) {
        console.log("Đăng nhập thành công:", admin);

        return res.status(200).json({
          message: "Đăng nhập thành công",
          user: {
            name: admin.name,
            email: admin.email,
          },
        });
      }
    }

    // Nếu không tìm thấy tài khoản
    console.log("Email hoặc mật khẩu không đúng.");
    return res.status(401).json({ message: "Email hoặc mật khẩu không đúng." });
  } catch (error) {
    console.error("Lỗi server:", error);
    return res.status(500).json({ message: "Lỗi server." });
  }
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

///THÊM THU NHẬP
// API thêm hạng mục
app.post('/addThuNhap', express.json(), async (req, res) => {
  const hangMucRef = ref(database, 'HangMuc');
  let { tenHangmuc, idNhom, anhHangmuc } = req.body;

  if (!idNhom) {
    idNhom = "1"; // Giá trị mặc định là nhóm "Thu Nhập"
  }

  try {
    const newRef = push(hangMucRef); // Tạo key mới
    const newCategory = {
      idHangmuc: newRef.key,
      idNhom,
      tenHangmuc,
      anhHangmuc, // Lưu icon vào trường anhHangmuc
    };

    await set(newRef, newCategory); // Ghi dữ liệu vào Firebase

    res.status(200).json(newCategory); // Trả về hạng mục vừa thêm
  } catch (error) {
    console.error('Lỗi khi thêm hạng mục:', error);
    res.status(500).json({ error: 'Lỗi khi thêm hạng mục' });
  }
});



app.get('/HangMucThuNhap', async (req, res) => {
  try {
    const categoryRef = ref(database, 'HangMuc'); // Tham chiếu tới bảng HangMuc
    const snapshot = await get(categoryRef);

    let categoryList = []; // Khởi tạo danh sách hạng mục
    if (snapshot.exists()) {
      const data = snapshot.val();
      for (const id in data) {
        if (data[id].idNhom === "1" && !('userId' in data[id])) { // So sánh trực tiếp với chuỗi "1"
          categoryList.push({
            tenHangMuc: data[id].tenHangmuc || "Không có tên",
            anhHangMuc: data[id].anhHangmuc || "Không có tên",
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

// Route để tìm kiếm hạng mục thu nhập
app.get('/tim-kiem-thu-nhap', async (req, res) => {
  const searchName = req.query.name?.toLowerCase(); // Lấy từ khóa tìm kiếm
  const thuNhapRef = ref(database, 'HangMuc');

  try {
      const snapshot = await get(thuNhapRef);
      let categoryList = [];

      if (snapshot.exists()) {
          const data = snapshot.val();
          for (const id in data) {
              const hangMuc = data[id];

              // Lọc các hạng mục thuộc nhóm "1" (thu nhập) và phù hợp với từ khóa tìm kiếm
              if (
                  hangMuc.idNhom === "1" &&
                  (!searchName || hangMuc.tenHangmuc.toLowerCase().includes(searchName))
              ) {
                  categoryList.push({
                      tenHangMuc: hangMuc.tenHangmuc || "Không có tên",
                      anhHangMuc: '../images/money.png', // Icon mặc định
                  });
              }
          }
      }

      // Trả về danh sách hạng mục dưới dạng JSON
      res.status(200).json(categoryList);
  } catch (error) {
      console.error("Lỗi khi tìm kiếm:", error);
      res.status(500).json({ error: "Lỗi máy chủ khi tìm kiếm hạng mục thu nhập!" });
  }
});
///Xoá hạng mục thu nhập
app.post('/delete-category', async (req, res) => {
  const { idHangmuc } = req.body;

  try {
      await database.ref(`HangMuc/${idHangmuc}`).remove();
      res.json({ success: true });
  } catch (error) {
      console.error('Error deleting category:', error);
      res.json({ success: false, message: 'Xóa hạng mục thất bại.' });
  }
});



///CHI PHÍ
app.post('/addChiPhi', express.json(), async (req, res) => {
  const hangMucRef = ref(database, 'HangMuc');
  let { tenHangmuc, idNhom, anhHangmuc } = req.body;


  if (!idNhom) {
    idNhom = "2"; // Giá trị mặc định là nhóm "Thu Nhập"
  }

  try {
    const newRef = push(hangMucRef); // Tạo key mới
    const newCategory = {
      idHangmuc: newRef.key,
      idNhom,
      tenHangmuc,
      anhHangmuc,
    };

   
    await set(newRef, newCategory); // Ghi dữ liệu vào Firebase

    // Trả về hạng mục vừa thêm
    res.status(200).json(newCategory);
  } catch (error) {
    console.error('Lỗi khi thêm hạng mục:', error);
    res.status(500).json({ error: 'Lỗi khi thêm hạng mục' });
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
        // Lọc các hạng mục có idNhom là "2" và không có trường idUser
        if (data[id].idNhom === "2" && !('userId' in data[id])) {
          categoryList.push({
            tenHangMuc: data[id].tenHangmuc || "Không có tên", // Lấy tên hạng mục
            anhHangMuc: data[id].anhHangmuc || "Không có tên", // Đường dẫn hình ảnh
          });
        }
      }
    }

    // Truyền danh sách hạng mục vào file HangMucChiPhi.ejs
    res.render('HangMucChiPhi', { categoryList });
  } catch (error) {
    console.error("Lỗi khi đọc dữ liệu Firebase: ", error);
    res.render('HangMucChiPhi', { categoryList: [] }); // Truyền danh sách rỗng khi lỗi
  }
});





// Route để tìm kiếm hạng mục chi phí
app.get('/tim-kiem-chi-phi', async (req, res) => {
  const searchName = req.query.name?.toLowerCase(); // Lấy từ khóa tìm kiếm
  const thuNhapRef = ref(database, 'HangMuc');

  try {
      const snapshot = await get(thuNhapRef);
      let categoryList = [];

      if (snapshot.exists()) {
          const data = snapshot.val();
          for (const id in data) {
              const hangMuc = data[id];

              // Lọc các hạng mục thuộc nhóm "1" (thu nhập) và phù hợp với từ khóa tìm kiếm
              if (
                  hangMuc.idNhom === "2" &&
                  (!searchName || hangMuc.tenHangmuc.toLowerCase().includes(searchName))
              ) {
                  categoryList.push({
                      tenHangMuc: hangMuc.tenHangmuc || "Không có tên",
                      anhHangMuc: '../images/money.png', // Icon mặc định
                  });
              }
          }
      }

      // Trả về danh sách hạng mục dưới dạng JSON
      res.status(200).json(categoryList);
  } catch (error) {
      console.error("Lỗi khi tìm kiếm:", error);
      res.status(500).json({ error: "Lỗi máy chủ khi tìm kiếm hạng mục thu nhập!" });
  }
});


app.get('/TrangChu', async (req, res) => {
  try {
    
    // Tham chiếu đến bảng NguoiDung
    const userRef = ref(database, 'NguoiDung');
    const userSnapshot = await get(userRef);

    let userCount = 0;
    let allUsers = [];
    if (userSnapshot.exists()) {
      const userData = userSnapshot.val();
      userCount = Object.keys(userData).length; // Đếm tổng số người dùng

      // Duyệt qua danh sách người dùng
      for (const id in userData) {
        const user = userData[id];
        allUsers.push({
          avatar: user.avatar || '../images/binh.png', // Avatar mặc định
          name: user.tenUser || "Người dùng không tên",
          email: user.email || "Không có email",
        });
      }
    }

    // Tham chiếu đến bảng HangMuc
    const categoryRef = ref(database, 'HangMuc');
    const categorySnapshot = await get(categoryRef);

    let categoryList = [];
    let categoryWithoutUserIdCount = 0; // Biến để đếm số hạng mục không có userId

    if (categorySnapshot.exists()) {
      const categoryData = categorySnapshot.val();

      // Duyệt qua danh sách hạng mục
      for (const id in categoryData) {
        const category = categoryData[id];

        // Kiểm tra nếu không có trường userId hoặc trường này rỗng
        if (!category.hasOwnProperty('userId') || !category.userId) {
          categoryList.push({
            tenHangMuc: category.tenHangmuc || "Không có tên", // Lấy tên hạng mục
            anhHangMuc: category.anhHangmuc || "Không có tên", // Đường dẫn hình ảnh
          });
          categoryWithoutUserIdCount++; // Tăng số lượng hạng mục không có userId
        }
      }
    }

    // Render giao diện TrangChu với danh sách người dùng và hạng mục
    res.render('TrangChu', {
      userCount,
      activeUsers: allUsers,
      categoryList,
      categoryWithoutUserIdCount, // Thêm số lượng hạng mục không có userId
    });
  } catch (error) {
    console.error("Lỗi khi đọc dữ liệu Firebase: ", error);
    res.render('TrangChu', {
      userCount: 0,
      activeUsers: [],
      categoryList: [],
      totalCategories: 0,
      categoryWithoutUserIdCount: 0, // Đảm bảo số lượng là 0 nếu có lỗi
    });
  }
});



app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);
});