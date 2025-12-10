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
        <h3 class="text-white mb-4">Quản lý Người dùng</h3>
        <a href="${pageContext.request.contextPath}/admin/user/create" class="btn btn-success">
        	<i class="fas fa-user-plus"></i> Thêm User Mới
    	</a>
        
        <div class="table-responsive">
            <table class="table table-dark table-hover border-secondary">
                <thead>
                    <tr>
                        <th>ID (Username)</th>
                        <th>Họ và tên</th>
                        <th>Email</th>
                        <th>Vai trò</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="u">
                        <tr>
                            <td>${u.id}</td>
                            <td>${u.fullname}</td>
                            <td>${u.email}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${u.admin}">
                                        <span class="badge bg-danger">Admin</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-info text-dark">User</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="user/edit?id=${u.id}" class="btn btn-sm btn-primary me-2">
                                    <i class="fas fa-user-edit"></i> Sửa
                                </a>
                                <a href="user/delete?id=${u.id}" class="btn btn-sm btn-danger"
                                   onclick="return confirm('Xóa người dùng này? Hành động không thể hoàn tác!');">
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