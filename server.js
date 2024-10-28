const express = require('express');
const path = require('path');

const app = express();
const PORT = process.env.PORT || 3006;

// Phục vụ các file tĩnh từ thư mục public
app.use(express.static(path.join(__dirname, 'public')));


// Thiết lập view engine
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'src', 'views'));

app.get('/', (req, res) => {
    console.log("Rendering DangNhap.ejs");
    res.render('DangNhap', { title: 'Đăng nhập' });
});

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
});