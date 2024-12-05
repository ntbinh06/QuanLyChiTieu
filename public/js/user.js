// Thêm sự kiện cho nút "Tìm kiếm"
document.getElementById("search-button").addEventListener("click", async () => {
    const searchInput = document.getElementById("search-input").value.trim();

    if (searchInput) {
        try {
            // Gửi yêu cầu GET đến server để tìm kiếm người dùng
            const response = await fetch(`/tim-kiem?name=${encodeURIComponent(searchInput)}`);
            if (response.ok) {
                const userList = await response.json();
                updateUserList(userList); // Cập nhật danh sách người dùng hiển thị
            } else {
                alert("Không tìm thấy người dùng!");
            }
        } catch (error) {
            console.error("Lỗi tìm kiếm:", error);
            alert("Có lỗi xảy ra khi tìm kiếm!");
        }
    } else {
        alert("Vui lòng nhập tên cần tìm kiếm!");
    }
});

// Hàm cập nhật danh sách người dùng hiển thị
function updateUserList(users) {
    const userListElement = document.getElementById("userList");
    userListElement.innerHTML = ""; // Xóa danh sách cũ

    if (users.length > 0) {
        users.forEach((user) => {
            const userItem = document.createElement("li");
            userItem.innerHTML = `
                <div class="user-avatar">
                    <img src="${user.avatar}" alt="User Avatar">
                </div>
                <div class="user-info">
                    <div class="user-name">${user.name}</div>
                    <div class="user-email">${user.email}</div>
                </div>
                <div class="user-icon">
                    <a href="/XemChiTietUser?userId=${user.id}" class="user-icon-eye">
                        <i class="fa-solid fa-eye"></i>
                    </a>
                </div>
            `;
            userListElement.appendChild(userItem);
        });
    } else {
        userListElement.innerHTML = "<li>Không có người dùng nào.</li>";
    }
}
async function toggleLock(userId, currentLock) {
    const confirmation = confirm(
      currentLock
        ? "Bạn có chắc muốn mở khóa tài khoản này?"
        : "Bạn có chắc muốn khóa tài khoản này?"
    );

    if (confirmation) {
      try {
        const response = await fetch('/toggleLock', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ userId, lock: !currentLock }),
        });

        if (response.ok) {
          alert('Cập nhật trạng thái thành công!');
          window.location.reload(); // Tải lại trang để cập nhật giao diện
        } else {
          alert('Cập nhật trạng thái thất bại!');
        }
      } catch (error) {
        console.error('Lỗi khi cập nhật trạng thái:', error);
        alert('Có lỗi xảy ra, vui lòng thử lại sau.');
      }
    }
  }