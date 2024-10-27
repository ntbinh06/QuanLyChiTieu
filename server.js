const express = require('express');
const path = require('path');

const app = express();
const PORT = process.env.PORT || 3004;

// Phục vụ các file tĩnh từ thư mục public
app.use(express.static(path.join(__dirname, 'public')));


// Thiết lập view engine
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'src', 'views'));

// Route chính
app.get('/', (req, res) => {
    res.render('DangNhap', { title: 'Đăng nhập' });
});

app.get('/QuanLyNguoiDung', (req, res) => {
    res.render('QuanLyNguoiDung.ejs');
  });




// Bắt đầu server
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});