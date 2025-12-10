<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<jsp:include page="/common/head.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/common/header.jsp"></jsp:include>

	<div class="container mt-4">
		<h4 class="mb-3">Video đề xuất</h4>

		<div class="row row-cols-1 row-cols-md-3 row-cols-lg-4 g-4">
			<c:forEach items="${videos}" var="video">
				<div class="col">
					<div class="card video-card h-100">
						<div class="video-thumbnail">
							<a href="detail?id=${video.id}"> <img
								src="https://img.youtube.com/vi/${video.id}/maxresdefault.jpg"
								alt="${video.title}"
								onerror="this.src='https://via.placeholder.com/320x180'">
							</a>
						</div>
						<div class="card-body px-0">
							<h5 class="card-title text-truncate">${video.title}</h5>
							<%--<p class="card-text">
								${video.views} lượt xem
								--Nếu có ngày đăng thì hiển thị thêm ở đây 
							</p>--%>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>

	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>