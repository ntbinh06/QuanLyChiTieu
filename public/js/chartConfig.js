const ctx = document.getElementById('userChart').getContext('2d');
const userChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ['2/9', '3/9', '4/9', '5/9', '6/9', '7/9'],
        datasets: [
            {
                label: 'Các tài khoản đang hoạt động',
                data: [220, 300, 150, 100, 250, 300],
                backgroundColor: 'green'
            },
            {
                label: 'Các tài khoản không hoạt động',
                data: [100, 80, 70, 50, 120, 150],
                backgroundColor: 'red'
            }
        ]
    },
    options: {
        responsive: true,
        plugins: {
            legend: {
                position: 'top',
            }
        }
    }
});
