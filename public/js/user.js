app.get('/QuanLyNguoiDung', async (req, res) => {
    try {
      const userRef = ref(database, 'NguoiDung'); // Tham chiếu tới nhánh 'NguoiDung'
      const snapshot = await get(userRef); // Lấy dữ liệu từ Firebase
  
      if (snapshot.exists()) {
        const data = snapshot.val(); // Dữ liệu người dùng
        const userList = [];
  
        // Chuyển đổi dữ liệu Firebase thành mảng
        for (const id in data) {
          userList.push({
            avatar: '../images/user_women.png', // Đường dẫn ảnh cố định
            name: data[id].tenUser,  // Tên người dùng
            email: data[id].email,   // Email người dùng
          });
        }
  
        // Truyền dữ liệu vào view EJS
        res.render('QuanLyNguoiDung', { userList });
      } else {
        // Trường hợp không có dữ liệu
        res.render('QuanLyNguoiDung', { userList: [] });
      }
    } catch (error) {
      console.error("Lỗi khi đọc dữ liệu Firebase: ", error);
      res.render('QuanLyNguoiDung', { userList: [] });
    }
  });
  