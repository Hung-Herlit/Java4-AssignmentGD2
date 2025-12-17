<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
</head>
<body>
    <jsp:include page="/common/header.jsp"></jsp:include>

    <div class="container mt-5" style="min-height: 80vh;">
        <div class="row justify-content-center">
            
            <div class="col-md-5 mb-4">
                <div class="card bg-dark text-white border-secondary h-100">
                    <div class="card-header border-secondary">
                        <h5 class="mb-0">Hồ sơ cá nhân</h5>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty message}">
                            <div class="alert alert-success">${message}</div>
                        </c:if>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>

                        <form action="profile" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="action" value="update_info">
                            
                            <div class="text-center mb-4">
                                <img src="${pageContext.request.contextPath}/images/${empty sessionScope.currentUser.picture ? 'user-default.png' : sessionScope.currentUser.picture}" 
                                     class="rounded-circle border border-secondary" 
                                     style="width: 120px; height: 120px; object-fit: cover;"
                                     onerror="this.src='https://ui-avatars.com/api/?name=${sessionScope.currentUser.fullname}'">
                                <div class="mt-3">
                                    <label for="fileUpload" class="btn btn-sm btn-outline-light">
                                        <i class="fas fa-camera"></i> Đổi ảnh
                                    </label>
                                    <input type="file" id="fileUpload" name="photo" class="d-none" accept="image/*" onchange="previewImage(event)">
                                </div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label text-secondary">Họ và tên</label>
                                <input type="text" class="form-control form-control-dark text-white bg-black" 
                                       name="fullname" value="${sessionScope.currentUser.fullname}" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label text-secondary">Email</label>
                                <input type="email" class="form-control form-control-dark text-white bg-black" 
                                       name="email" value="${sessionScope.currentUser.email}" required>
                            </div>
                            
                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary">Cập nhật hồ sơ</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div class="col-md-5 mb-4">
                <div class="card bg-dark text-white border-secondary h-100">
                    <div class="card-header border-secondary">
                        <h5 class="mb-0">Đổi mật khẩu</h5>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty passMessage}">
                            <div class="alert alert-success">${passMessage}</div>
                        </c:if>
                        <c:if test="${not empty passError}">
                            <div class="alert alert-danger">${passError}</div>
                        </c:if>

                        <form action="profile" method="post">
                            <input type="hidden" name="action" value="change_password">

                            <div class="mb-3">
                                <label class="form-label text-secondary">Mật khẩu hiện tại</label>
                                <input type="password" class="form-control form-control-dark text-white bg-black" 
                                       name="oldPass" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label text-secondary">Mật khẩu mới</label>
                                <input type="password" class="form-control form-control-dark text-white bg-black" 
                                       name="newPass" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label text-secondary">Xác nhận mật khẩu mới</label>
                                <input type="password" class="form-control form-control-dark text-white bg-black" 
                                       name="confirmPass" required>
                            </div>

                            <div class="d-grid mt-4">
                                <button type="submit" class="btn btn-danger">Đổi mật khẩu</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <jsp:include page="/common/footer.jsp"></jsp:include>
    
    <script>
        // Hàm xem trước ảnh khi chọn file
        function previewImage(event) {
            var reader = new FileReader();
            reader.onload = function(){
                var output = document.querySelector('img.rounded-circle');
                output.src = reader.result;
            };
            reader.readAsDataURL(event.target.files[0]);
        }
    </script>
</body>
</html>