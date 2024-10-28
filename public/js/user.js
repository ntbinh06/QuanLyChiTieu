// Array of users to display
const users = [
    {
        avatar: '../images/user_women.png',
        name: 'Lê Thị Kim Ngân',
        email: 'ltkngan@gmail.com',
    },
    {
        avatar: '../images/user_women.png',
        name: 'Nguyễn Thanh Bình',
        email: 'ntbinh@gmail.com',
    },
    {
        avatar: '../images/user_women.png',
        name: 'Cái Thị Nhân Đức',
        email: 'nguyenvana@gmail.com',
    },
    {
        avatar: '../images/user_women.png',
        name: 'Nguyễn Như Ngọc',
        email: 'nnngoc@gmail.com',
    },
    {
        avatar: '../images/user_women.png',
        name: 'Nguyễn Văn A',
        email: 'nguyenvana@gmail.com',
    },
    {
        avatar: '../images/user_women.png',
        name: 'Nguyễn Văn A',
        email: 'nguyenvana@gmail.com',
    },
    {
        avatar: '../images/user_women.png',
        name: 'Nguyễn Văn A',
        email: 'nguyenvana@gmail.com',
    },
    {
        avatar: '../images/user_women.png',
        name: 'Nguyễn Văn A',
        email: 'nguyenvana@gmail.com',
    },
    {
        avatar: '../images/user_women.png',
        name: 'Nguyễn Văn A',
        email: 'nguyenvana@gmail.com',
    },
    {
        avatar: '../images/user_women.png',
        name: 'Nguyễn Văn A',
        email: 'nguyenvana@gmail.com',
    },
];

// Function to render user list
function renderUserList(userList) {
    const userListContainer = document.getElementById('userList');
    userListContainer.innerHTML = ''; // Clear existing elements

    userList.forEach(user => {
        // Create a user list item
        const userItem = document.createElement('li');
        userItem.innerHTML = `
            <div class="user-avatar">
                <img src="${user.avatar}" alt="User Avatar">
            </div>
            <div class="user-info">
                <div class="user-name">${user.name}</div>
                <div class="user-email">${user.email}</div>
            </div>
            <div class="user-icon">
                <a href="/XemChiTietUser" class="user-icon-eye"><i class="fa-solid fa-eye"></i></a>
                <a href="#" class="user-icon-lock"><i class="fa-solid fa-lock"></i></a>
            </div>
        `;
        userListContainer.appendChild(userItem);
    });
}

// Ensure the function is called once the DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    renderUserList(users);
});
