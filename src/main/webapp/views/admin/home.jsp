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
        <h2 class="text-white mb-4">Dashboard Quản Trị</h2>
        
        <div class="row g-4">
            <div class="col-md-4">
                <div class="card bg-dark text-white border-secondary h-100">
                    <div class="card-body text-center">
                        <h1 class="display-4 fw-bold text-danger">${videoCount}</h1>
                        <h5 class="text-secondary">Tổng Video</h5>
                        <a href="${pageContext.request.contextPath}/admin/videos" class="btn btn-outline-danger mt-3">Quản lý Video</a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4">
                <div class="card bg-dark text-white border-secondary h-100">
                    <div class="card-body text-center">
                        <h1 class="display-4 fw-bold text-primary">${userCount}</h1>
                        <h5 class="text-secondary">Người dùng</h5>
                        <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-outline-primary mt-3">Quản lý User</a>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card bg-dark text-white border-secondary h-100">
                    <div class="card-body text-center">
                        <h1 class="display-4 fw-bold text-success">${totalViews}</h1>
                        <h5 class="text-secondary">Tổng Lượt Xem</h5>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>