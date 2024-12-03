const ctx = document.getElementById('userChart').getContext('2d');
// Dữ liệu cho biểu đồ
const data = {
    labels: ['Thứ 2', 'Thứ 3', 'Thứ 4', 'Thứ 5', 'Thứ 6', 'Thứ 7', 'Chủ Nhật'],
    datasets: [{
        label: 'Số người dùng tích cực',
        data: [50, 40, 60, 70, 90, 100, 80], // Dữ liệu mẫu
        backgroundColor: [
            'rgba(0, 128, 0, 1)', // Xanh lá đậm
            'rgba(139, 0, 0, 1)', // Đỏ thẫm
            'rgba(0, 128, 0, 1)', 
            'rgba(139, 0, 0, 1)', 
            'rgba(0, 128, 0, 1)', 
            'rgba(139, 0, 0, 1)', 
            'rgba(0, 128, 0, 1)',
        ],
        borderWidth: 0 // Loại bỏ viền
    }]
};

// Cấu hình biểu đồ
const config = {
    type: 'bar',
    data: data,
    options: {
        responsive: true,
        plugins: {
            legend: {
                position: 'top',
            },
            title: {
                display: true,
                text: 'Số người dùng tích cực theo ngày'
            }
        },
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
};

// Tạo biểu đồ
const userChart = new Chart(ctx, config);
