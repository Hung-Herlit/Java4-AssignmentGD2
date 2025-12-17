<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-dark fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/index">
            <i class="fab fa-youtube text-danger fa-lg"></i> Wetube
        </a>

        <form class="d-none d-md-flex mx-auto" action="search" method="get">
            <div class="input-group">
                <input class="form-control search-bar" type="search" placeholder="Tìm kiếm" name="keyword" size="50">
                <button class="btn btn-secondary" type="submit">
                    <i class="fas fa-search"></i>
                </button>
            </div>
        </form>

        <div class="d-flex align-items-center gap-3">
            <c:choose>
                <%-- Nếu chưa đăng nhập --%>
                <c:when test="${empty sessionScope.currentUser}">
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-outline-primary rounded-pill">
                        <i class="fas fa-user-circle"></i> Đăng nhập
                    </a>
                </c:when>

                <%-- Nếu đã đăng nhập --%>
                <c:otherwise>
                    <div class="dropdown">
                        <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" id="userDropdown" data-bs-toggle="dropdown">
                            <img src="${pageContext.request.contextPath}/images/${empty sessionScope.currentUser.picture ? 'user-default.png' : sessionScope.currentUser.picture}"
                            onerror="this.src='https://ui-avatars.com/api/?name=${sessionScope.currentUser.fullname}'" alt="Avatar" width="32" height="32" class="rounded-circle me-2">
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end dropdown-menu-dark">
                            <li><span class="dropdown-item-text">Xin chào, ${sessionScope.currentUser.fullname}</span></li>
                            <li><hr class="dropdown-divider"></li>
                            
                            <c:if test="${sessionScope.currentUser.admin}">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/dashboard">Quản trị Admin</a></li>
                            </c:if>
                            
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/favorite">Video đã thích</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile">Hồ sơ cá nhân</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
                        </ul>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>
<div style="height: 70px;"></div>