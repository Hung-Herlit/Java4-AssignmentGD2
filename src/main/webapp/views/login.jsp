<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
    <style>
        /* CSS riêng cho trang Login để căn giữa màn hình */
        .login-container {
            min-height: 80vh; /* Chiều cao tối thiểu để căn giữa */
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .login-card {
            background-color: #1e1e1e;
            border: 1px solid #333;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.5);
            max-width: 450px;
            width: 100%;
        }
        .form-control-dark {
            background-color: #121212;
            border: 1px solid #333;
            color: #fff;
        }
        .form-control-dark:focus {
            background-color: #121212;
            border-color: var(--primary-color);
            color: #fff;
            box-shadow: 0 0 0 0.25rem rgba(255, 0, 0, 0.25);
        }
    </style>
</head>
<body>
    <jsp:include page="/common/header.jsp"></jsp:include>

    <div class="container login-container">
        <div class="card login-card p-4">
            <div class="card-body">
				<div class="text-center mb-4">
					<i class="fab fa-youtube text-danger fa-3x mb-3"></i>
					<h3 class="fw-bold text-white">Đăng nhập Wetube</h3>
					<p style="color: #cccccc;">Sử dụng tài khoản PolyOE của bạn</p>
				</div>

				<c:if test="${not empty message}">
                    <div class="alert alert-danger d-flex align-items-center" role="alert">
                        <i class="fas fa-exclamation-triangle me-2"></i>
                        <div>${message}</div>
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/login" method="post">
                    <div class="mb-3">
                        <label for="id" class="form-label text-secondary">Tên đăng nhập / ID</label>
                        <input type="text" class="form-control form-control-dark py-2" id="id" name="id" 
                               value="${id}" required placeholder="Nhập ID của bạn">
                    </div>

                    <div class="mb-3">
                        <label for="password" class="form-label text-secondary">Mật khẩu</label>
                        <input type="password" class="form-control form-control-dark py-2" id="password" name="password" 
                               required placeholder="Nhập mật khẩu">
                    </div>

                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <div class="form-check">
                            <input class="form-check-input bg-dark border-secondary" type="checkbox" id="remember" name="remember">
                            <label class="form-check-label text-secondary" for="remember">
                                Ghi nhớ
                            </label>
                        </div>
                    </div>

                    <div class="d-grid">
                        <button type="submit" class="btn btn-danger btn-lg fw-bold">Đăng nhập</button>
                    </div>
                </form>
                
                <hr class="border-secondary my-4">
                
                <div class="text-center text-secondary">
                    Chưa có tài khoản? 
                    <a href="${pageContext.request.contextPath}/register" class="text-white text-decoration-none fw-bold">Đăng ký ngay</a>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>