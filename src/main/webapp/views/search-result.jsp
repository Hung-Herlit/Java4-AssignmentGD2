<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
</head>
<body>
    <jsp:include page="/common/header.jsp"></jsp:include>

    <div class="container mt-4" style="min-height: 80vh;">
        <div class="mb-4">
            <h4 class="text-white">
                Kết quả tìm kiếm cho: <span class="text-danger">"${keyword}"</span>
            </h4>
            <p class="text-secondary small">Tìm thấy ${videos.size()} video phù hợp</p>
        </div>
        
        <div class="row row-cols-1 row-cols-md-3 row-cols-lg-4 g-4">
            
            <%-- Nếu không tìm thấy video nào --%>
            <c:if test="${empty videos}">
                <div class="col-12 text-center py-5">
                    <i class="fas fa-search fa-3x text-secondary mb-3"></i>
                    <h5 class="text-secondary">Không tìm thấy video nào phù hợp với từ khóa này.</h5>
                    <a href="${pageContext.request.contextPath}/index" class="btn btn-outline-light mt-3">Quay lại trang chủ</a>
                </div>
            </c:if>

            <%-- Hiển thị danh sách kết quả --%>
            <c:forEach items="${videos}" var="video">
                <div class="col">
                    <div class="card video-card h-100">
                        <div class="video-thumbnail">
                            <a href="detail?id=${video.id}">
                                <img src="https://img.youtube.com/vi/${video.id}/mqdefault.jpg" 
                                     alt="${video.title}" 
                                     onerror="this.src='https://via.placeholder.com/320x180'">
                            </a>
                        </div>
                        <div class="card-body px-0">
                            <h5 class="card-title text-truncate mb-1" title="${video.title}">${video.title}</h5>
                            <p class="card-text text-secondary small">
                                ${video.views} lượt xem
                            </p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>