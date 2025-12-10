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
            <div class="col-md-6">
                <div class="card bg-dark text-white border-secondary">
                    <div class="card-header border-secondary">
                        <h4 class="text-white">${not empty user ? 'Cập nhật User' : 'Thêm Người dùng Mới'}</h4>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/admin/user/${not empty user ? 'update' : 'create'}" method="post">
                            
                            <div class="mb-3">
                                <label class="form-label text-secondary">Tên đăng nhập (ID)</label>
                                <input type="text" class="form-control form-control-dark text-white bg-black" 
                                       name="id" value="${user.id}" required
                                       ${not empty user ? 'readonly style="background-color: #333 !important;"' : ''}>
                                <c:if test="${not empty user}">
                                    <div class="form-text text-secondary">Không thể thay đổi ID khi chỉnh sửa.</div>
                                </c:if>
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label text-secondary">Mật khẩu</label>
                                <input type="password" class="form-control form-control-dark text-white bg-black" 
                                       name="password" value="${user.password}"
                                       ${empty user ? 'required' : ''} placeholder="${not empty user ? 'Bỏ trống nếu không muốn đổi mật khẩu' : ''}">
                            </div>

                            <div class="mb-3">
                                <label class="form-label text-secondary">Họ và tên</label>
                                <input type="text" class="form-control form-control-dark text-white bg-black" 
                                       name="fullname" value="${user.fullname}" required>
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label text-secondary">Email</label>
                                <input type="email" class="form-control form-control-dark text-white bg-black" 
                                       name="email" value="${user.email}" required>
                            </div>
                            
                            <div class="form-check mb-4">
                                <input class="form-check-input bg-dark" type="checkbox" name="admin" 
                                       id="chkAdmin" ${user.admin ? 'checked' : ''}>
                                <label class="form-check-label text-white" for="chkAdmin">
                                    Vai trò Quản trị viên (Admin)
                                </label>
                            </div>
                            
                            <button type="submit" class="btn btn-primary">
                                ${not empty user ? 'Lưu thay đổi' : 'Tạo mới'}
                            </button>
                            <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-secondary">Hủy</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>