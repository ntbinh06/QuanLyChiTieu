// Hàm tìm kiếm hạng mục
async function searchCategory() {
    const searchInput = document.getElementById("search-input").value.trim();

        try {
            const response = await fetch(`/tim-kiem-chi-phi?name=${encodeURIComponent(searchInput)}`);
            if (response.ok) {
                const categoryList = await response.json();
                updateCategoryList(categoryList); // Cập nhật danh sách hạng mục hiển thị
            } else {
                alert("Không tìm thấy hạng mục phù hợp!");
            }
        } catch (error) {
            console.error("Lỗi tìm kiếm:", error);
            alert("Có lỗi xảy ra khi tìm kiếm!");
        }
  }
  
  // Hàm cập nhật danh sách hạng mục
  function updateCategoryList(categoryList) {
    const categoryListElement = document.querySelector(".category-list");
  
    // Nếu danh sách hạng mục trống
    if (!categoryList || categoryList.length === 0) {
        categoryListElement.innerHTML = `<li>Không có hạng mục nào phù hợp.</li>`;
        return;
    }
  
    // Tạo HTML mới cho danh sách hạng mục
    const newListHTML = categoryList
        .map(
            (category) => `
            <li class="category-item">
                <div class="category-info">
                     <img src="../images/${category.anhHangMuc}" alt="${category.tenHangMuc}" class="category-image">
                    <p>${category.tenHangMuc}</p>
                </div>
                <div class="category-icon">
                    <a href="#" class="category-icon-edit"><i class="fa-solid fa-pen-to-square" style="color: #000000;"></i></a>
                    <a href="#" class="category-icon-delete"><i class="fa-solid fa-trash-can" style="color: #000000;"></i></a>
                </div>
            </li>
        `
        )
        .join("");
  
    // Cập nhật lại nội dung danh sách
    categoryListElement.innerHTML = newListHTML;
  }
  
  // Gắn sự kiện tìm kiếm vào nút bấm
  document.getElementById("search-button").addEventListener("click", searchCategory);


// Mở modal
function openModal() {
    document.getElementById("inputModal").style.display = "block";
}

// Đóng modal
function closeModal() {
    document.getElementById("inputModal").style.display = "none";
}

let selectedIcon = ""; // Khai báo biến toàn cục

function selectIcon(iconSrc) {
  selectedIcon = iconSrc; // Gán giá trị tên tệp icon được chọn
  console.log("Icon được chọn: " + selectedIcon);

  // Hiển thị ảnh đã chọn trong modal
  const previewImage = document.getElementById('selectedImagePreview');
  previewImage.src = `../images/${selectedIcon}.png`; // Thêm thư mục chứa ảnh vào trước tên tệp
  previewImage.style.display = "inline-flex"; // Hiển thị thẻ <img>
}

function submitCategory() {
  const categoryInput = document.getElementById('categoryInput').value;

  if (!categoryInput.trim()) {
    alert('Vui lòng nhập tên hạng mục!');
    return;
  }

  if (!selectedIcon) {
    alert('Vui lòng chọn một icon!');
    return;
  }

  const idNhom = "2"; // Mặc định nhóm Thu Nhập
  fetch('/addChiPhi', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      tenHangmuc: categoryInput,
      idNhom: idNhom,
      anhHangmuc: selectedIcon, // Lưu icon được chọn vào trường dữ liệu
    }),
  })
    .then((response) => {
      if (!response.ok) {
        return response.json().then((errorData) => {
          throw new Error(errorData.error || 'Lỗi khi thêm hạng mục');
        });
      }
      return response.json();
    })
    .then((newCategory) => {
      alert('Thêm hạng mục thành công!');
      console.log('Hạng mục mới:', newCategory);
    
      // Thêm hạng mục mới vào danh sách UI
      addCategoryToList({
        tenHangmuc: newCategory.tenHangmuc,
        anhHangmuc: newCategory.anhHangmuc || '../images/money.png', // Đường dẫn ảnh
        idHangmuc: newCategory.idHangmuc || Date.now(), // Tạo ID giả nếu cần
      });
    
      closeModal();
    })
    
    .catch((error) => {
      console.error('Lỗi:', error.message);
      alert('Đã xảy ra lỗi: ' + error.message);
    });
}





function addCategoryToList(category) {
  const categoryListElement = document.querySelector('.category-list');

  // Thêm hạng mục mới vào danh sách
  const newCategoryHTML = `
    <li class="category-item">
      <div class="category-info">
        <img src="../images/${category.anhHangmuc}.png" alt="${category.tenHangmuc}" class="category-image">
        <p>${category.tenHangmuc}</p>
      </div>
      <div class="category-icon">
        <a href="#" class="category-icon-edit"><i class="fa-solid fa-pen-to-square" style="color: #000000;"></i></a>
        <a href="#" class="category-icon-delete" data-id="${category.idHangmuc}">
            <i class="fa-solid fa-trash-can" style="color: #000000;"></i>
        </a>
      </div>
    </li>
  `;

  categoryListElement.insertAdjacentHTML('beforeend', newCategoryHTML);
}