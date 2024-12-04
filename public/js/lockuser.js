document.addEventListener('DOMContentLoaded', function() {
    const lockIcons = document.querySelectorAll('.user-icon-lock');

    lockIcons.forEach(icon => {
        icon.addEventListener('click', function(event) {
            event.preventDefault(); // Ngăn chặn hành vi mặc định của liên kết

            const userId = this.getAttribute('data-user-id');
            const isLocked = this.getAttribute('data-locked') === 'true'; // Chuyển đổi chuỗi thành boolean

            // Chuyển đổi trạng thái lock
            const newLockState = !isLocked;

            fetch(`/toggle-lock/${userId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ lock: newLockState }) // Gửi yêu cầu với trạng thái mới
            })
            .then(response => response.json())
            .then(data => {
                if (data.message === 'Thành công!') {
                    // Cập nhật thuộc tính data-locked và biểu tượng
                    this.setAttribute('data-locked', newLockState);
                    this.querySelector('i').className = newLockState ? 'fa-solid fa-lock' : 'fa-solid fa-unlock';
                } else {
                    alert('Có lỗi xảy ra: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Lỗi:', error);
            });
        });
    });
});