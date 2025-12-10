<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
</head>
<body>
    <jsp:include page="/common/header.jsp"></jsp:include>

    <div class="container mt-4" style="min-height: 60vh;">
        <div class="d-flex align-items-center mb-4 text-white">
            <i class="fas fa-heart text-danger fa-2x me-3"></i>
            <h3 class="mb-0 fw-bold">Video đã thích</h3>
        </div>

        <c:choose>
            <%-- Nếu danh sách trống --%>
            <c:when test="${empty favorites}">
                <div class="text-center py-5">
                    <i class="far fa-sad-tear fa-4x text-secondary mb-3"></i>
                    <h5 class="text-secondary">Bạn chưa thích video nào.</h5>
                    <a href="index" class="btn btn-outline-danger mt-3">Khám phá ngay</a>
                </div>
            </c:when>

            <%-- Nếu có dữ liệu --%>
            <c:otherwise>
                <div class="row row-cols-1 row-cols-md-3 row-cols-lg-4 g-4">
                    <c:forEach items="${favorites}" var="item">
                        <div class="col">
                            <div class="card video-card h-100">
                                <div class="video-thumbnail position-relative">
                                    <a href="detail?id=${item.video.id}">
                                        <img src="https://img.youtube.com/vi/${item.video.id}/mqdefault.jpg" 
                                             alt="${item.video.title}" 
                                             onerror="this.src='https://via.placeholder.com/320x180'">
                                    </a>
                                </div>
                                <div class="card-body px-0">
                                    <h5 class="card-title text-truncate mb-1" title="${item.video.title}">
                                        ${item.video.title}
                                    </h5>
                                    
                                    <div class="d-flex justify-content-between align-items-center mt-2">
                                        <small class="text-secondary">
                                            Đã thích: <fmt:formatDate value="${item.likeDate}" pattern="dd/MM/yyyy"/>
                                        </small>
                                        
                                        <button class="btn btn-sm btn-link text-danger p-0" 
                                                onclick="unlikeVideo('${item.video.id}', this)" title="Bỏ thích">
                                            <i class="fas fa-trash-alt"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <jsp:include page="/common/footer.jsp"></jsp:include>
    
    <script>
        function unlikeVideo(videoId, btnElement) {
            if(!confirm("Bạn có chắc muốn bỏ video này khỏi danh sách yêu thích?")) return;

            fetch('like-video?videoId=' + videoId, { method: 'POST' })
            .then(response => {
                if (response.ok) {
                    // Xóa card khỏi giao diện ngay lập tức
                    const cardCol = btnElement.closest('.col');
                    cardCol.remove();
                } else {
                    alert("Có lỗi xảy ra!");
                }
            });
        }
    </script>
</body>
</html>