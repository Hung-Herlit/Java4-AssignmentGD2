<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
</head>
<body>
    <jsp:include page="/common/header.jsp"></jsp:include>

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card bg-dark text-white border-secondary">
                    <div class="card-header border-secondary">
                        <h4 class="text-white">${not empty video ? 'Cập nhật Video' : 'Thêm Video Mới'}</h4>
                    </div>
                    <div class="card-body">
                        
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/admin/video/${not empty video ? 'update' : 'create'}" method="post">
                            
                            <div class="mb-3">
                                <label class="form-label text-secondary">YouTube ID</label>
                                <div class="input-group">
                                    <input type="text" class="form-control form-control-dark text-white bg-black" 
                                           id="youtubeId" name="id" placeholder="Ví dụ: 1eAsdWOVRxA" required
                                           value="${video.id}"
                                           ${not empty video ? 'readonly style="background-color: #333 !important;"' : 'onchange="loadVideoInfo()"'} >
                                    
                                    <c:if test="${empty video}">
                                        <button class="btn btn-danger" type="button" onclick="loadVideoInfo()">
                                            <i class="fas fa-magic"></i> Lấy thông tin
                                        </button>
                                    </c:if>
                                </div>
                                <c:if test="${not empty video}">
                                    <div class="form-text text-secondary">Không thể sửa ID Video.</div>
                                </c:if>
                            </div>

                            <div class="mb-3">
                                <label class="form-label text-secondary">Tiêu đề Video</label>
                                <input type="text" class="form-control form-control-dark text-white bg-black" 
                                       id="videoTitle" name="title" required value="${video.title}">
                            </div>

                            <div class="mb-3">
                                <label class="form-label text-secondary">Hình ảnh (Poster ID)</label>
                                <div class="row">
                                    <div class="col-4">
                                        <img id="posterPreview" 
                                             src="https://img.youtube.com/vi/${not empty video.id ? video.id : 'default'}/mqdefault.jpg" 
                                             class="img-fluid rounded border border-secondary"
                                             onerror="this.src='https://via.placeholder.com/320x180'">
                                    </div>
                                    <div class="col-8">
                                        <input type="text" class="form-control form-control-dark text-white bg-black mb-2" 
                                               id="posterInput" name="poster" 
                                               value="${video.poster}" required
                                               oninput="updatePreview(this.value)">
                                        <span class="text-secondary small fst-italic">
                                            Nhập ID video (VD: 1eAsdWOVRxA) để lấy ảnh.
                                        </span>
                                    </div>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label text-secondary">Mô tả</label>
                                <textarea class="form-control form-control-dark text-white bg-black" 
                                          name="description" rows="4">${video.description}</textarea>
                            </div>

                            <div class="form-check mb-4">
                                <input class="form-check-input bg-dark" type="checkbox" name="active" value="true" 
                                       ${empty video || video.active ? 'checked' : ''}>
                                <label class="form-check-label text-white">
                                    Hoạt động (Active)
                                </label>
                            </div>

                            <button type="submit" class="btn btn-primary px-4">
                                ${not empty video ? 'Lưu thay đổi' : 'Thêm mới'}
                            </button>
                            <a href="${pageContext.request.contextPath}/admin/videos" class="btn btn-secondary">Hủy</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/common/footer.jsp"></jsp:include>

    <script>
        // Cập nhật ảnh xem trước khi người dùng gõ tay vào ô Poster
        function updatePreview(id) {
            document.getElementById("posterPreview").src = "https://img.youtube.com/vi/" + id + "/mqdefault.jpg";
        }

        // Tự động lấy thông tin (Chỉ dùng khi thêm mới)
        function loadVideoInfo() {
            var videoId = document.getElementById("youtubeId").value.trim();
            if (videoId.length === 0) {
                alert("Vui lòng nhập YouTube ID!");
                return;
            }
            // Điền ID vào ô poster luôn
            document.getElementById("posterInput").value = videoId;
            updatePreview(videoId);

            // Gọi API lấy title
            var apiUrl = "https://noembed.com/embed?url=https://www.youtube.com/watch?v=" + videoId;
            fetch(apiUrl)
                .then(response => response.json())
                .then(data => {
                    if (data.title) {
                        document.getElementById("videoTitle").value = data.title;
                    }
                });
        }
    </script>
</body>
</html>