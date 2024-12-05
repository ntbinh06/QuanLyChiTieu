// Mở modal
function openModal() {
    document.getElementById('inputModal').style.display = 'block';
  }

  // Đóng modal
  function closeModal() {
    document.getElementById('inputModal').style.display = 'none';
  }

  // Gửi dữ liệu đến server
  function submitCategory() {
    const tenHangmuc = document.getElementById('categoryInput').value.trim();

    if (!tenHangmuc) {
      alert("Vui lòng nhập tên hạng mục!");
      return;
    }

    // Gửi yêu cầu POST đến server
    fetch('/addHangMucThuNhap', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        tenHangmuc: 'Hạng mục thu nhập mới',
        idNhom: '1',
      }),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.success) {
          console.log('Thêm dữ liệu thành công:', data.data);
        } else {
          console.error('Lỗi:', data.error);
        }
      })
      .catch((error) => console.error('Lỗi kết nối:', error));
  }