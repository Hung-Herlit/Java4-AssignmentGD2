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
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h3 class="text-white">Danh sách Video</h3>
            <a href="${pageContext.request.contextPath}/views/admin/video-edit.jsp" class="btn btn-success">
                <i class="fas fa-plus"></i> Thêm Video Mới
            </a>
        </div>

        <div class="table-responsive">
            <table class="table table-dark table-hover border-secondary">
                <thead>
                    <tr>
                        <th>Poster</th>
                        <th>Tiêu đề</th>
                        <th>Lượt xem</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${videos}" var="item">
                        <tr>
                            <td style="width: 120px;">
                                <img src="https://img.youtube.com/vi/${item.id}/default.jpg" 
                                     alt="thumb" style="width: 100px; border-radius: 4px;">
                            </td>
                            <td class="align-middle">${item.title}</td>
                            <td class="align-middle">${item.views}</td>
                            <td class="align-middle">
                                <c:choose>
                                    <c:when test="${item.active}">
                                        <span class="badge bg-success">Active</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-secondary">Inactive</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="align-middle">
                                <a href="video/edit?id=${item.id}" class="btn btn-sm btn-primary me-2">
                                    <i class="fas fa-edit"></i> Sửa
                                </a>
                                <a href="video/delete?id=${item.id}" class="btn btn-sm btn-danger" 
                                   onclick="return confirm('Bạn có chắc muốn xóa video này?');">
                                    <i class="fas fa-trash"></i> Xóa
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    
    <jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>