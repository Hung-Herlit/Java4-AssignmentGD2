<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
    <style>
        .register-container {
            min-height: 85vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px 0;
        }
        .register-card {
            background-color: #1e1e1e;
            border: 1px solid #333;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.5);
            max-width: 500px;
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

    <div class="container register-container">
        <div class="card register-card p-4">
            <div class="card-body">
                <div class="text-center mb-4">
                    <i class="fab fa-youtube text-danger fa-3x mb-3"></i>
                    <h3 class="fw-bold text-white">Đăng ký Wetube</h3>
                    <p style="color: #cccccc;">Tạo tài khoản để thích và chia sẻ video</p>
                </div>

                <c:if test="${not empty message}">
                    <div class="alert alert-danger" role="alert">
                        ${message}
                    </div>
                </c:if>

                <form action="register" method="post">
                    <div class="mb-3">
                        <label class="form-label text-secondary">Tên đăng nhập</label>
                        <input type="text" class="form-control form-control-dark" name="id" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label text-secondary">Mật khẩu</label>
                        <input type="password" class="form-control form-control-dark" name="password" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label text-secondary">Họ và tên</label>
                        <input type="text" class="form-control form-control-dark" name="fullname" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label text-secondary">Email</label>
                        <input type="email" class="form-control form-control-dark" name="email" required>
                    </div>

                    <div class="d-grid mt-4">
                        <button type="submit" class="btn btn-danger btn-lg fw-bold">Đăng ký</button>
                    </div>
                </form>

                <hr class="border-secondary my-4">
                
                <div class="text-center text-secondary">
                    Đã có tài khoản? 
                    <a href="login" class="text-white text-decoration-none fw-bold">Đăng nhập</a>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>