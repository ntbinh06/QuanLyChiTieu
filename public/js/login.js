document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();
  
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
  
    try {
        const response = await fetch("/login", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({ email, password }), // Kiểm tra email và password đã lấy đúng chưa
          });
          
  
      const result = await response.json();
  
      if (response.ok) {
        alert(`Đăng nhập thành công. Chào mừng ${result.user.name}!`);
        window.location.href = "/TrangChu"; // Điều hướng đến trang chủ
      } else {
        alert(result.message);
      }
    } catch (error) {
      console.error("Lỗi:", error);
      alert("Đã xảy ra lỗi, vui lòng thử lại.");
    }
  });
  