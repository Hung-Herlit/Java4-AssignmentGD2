<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
    <style>
        /* CSS riêng cho trang Detail */
        .video-container {
            position: relative;
            padding-bottom: 56.25%; /* Tỷ lệ 16:9 */
            height: 0;
            overflow: hidden;
            border-radius: 12px;
            background-color: #000;
        }
        .video-container iframe {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            border: 0;
        }
        /* Style cho danh sách video gợi ý bên phải */
        .related-video-item {
            display: flex;
            gap: 10px;
            margin-bottom: 15px;
            text-decoration: none;
            cursor: pointer;
        }
        .related-video-thumb {
            width: 168px;
            height: 94px;
            border-radius: 8px;
            object-fit: cover;
            flex-shrink: 0; /* Không bị co lại */
        }
        .related-video-info h6 {
            color: #fff;
            font-size: 0.95rem;
            margin-bottom: 4px;
            display: -webkit-box;
            -webkit-line-clamp: 2; /* Cắt dòng nếu tên quá dài */
            -webkit-box-orient: vertical;
            overflow: hidden;
        }
        .action-btn {
            background-color: #303030;
            color: #fff;
            border-radius: 20px;
            padding: 8px 16px;
            border: none;
            font-weight: 500;
            transition: 0.2s;
        }
        .action-btn:hover {
            background-color: #444;
            color: #fff;
        }
        /* Style cho nút Like khi đã active (sẽ xử lý logic sau) */
        .action-btn.active {
            color: #3ea6ff; /* Màu xanh youtube */
        }
    </style>
</head>
<body>
    <jsp:include page="/common/header.jsp"></jsp:include>

    <div class="container-fluid mt-4">
        <div class="row">
            <div class="col-lg-8 mb-4">
                <div class="video-container mb-3 shadow-sm">
                    <iframe src="https://www.youtube.com/embed/${video.id}?autoplay=1&rel=0" 
                            allowfullscreen allow="autoplay; encrypted-media"></iframe>
                </div>

                <h4 class="fw-bold text-white mb-2">${video.title}</h4>

                <div class="d-flex flex-wrap justify-content-between align-items-center border-bottom border-secondary pb-3 mb-3">
                    <div class="text-secondary small">
                        ${video.views} lượt xem 
                    </div>

					<div class="d-flex gap-2">
						<button id="likeBtn"
							class="action-btn ${flagLiked ? 'active' : ''}"
							style="${flagLiked ? 'color: #3ea6ff;' : ''}"
							onclick="toggleLike('${video.id}')">

							<c:choose>
								<c:when test="${flagLiked}">
									<i class="fas fa-thumbs-up me-1"></i> Đã thích
        						</c:when>
								<c:otherwise>
									<i class="far fa-thumbs-up me-1"></i> Thích
        						</c:otherwise>
							</c:choose>
						</button>

						<button class="action-btn" data-bs-toggle="modal"
							data-bs-target="#shareModal">
							<i class="fas fa-share me-1"></i> Chia sẻ
						</button>
					</div>
				</div>

				<div class="modal fade" id="shareModal" tabindex="-1"
					aria-hidden="true">
					<div class="modal-dialog modal-dialog-centered">
						<div class="modal-content"
							style="background-color: #1e1e1e; border: 1px solid #333;">
							<div class="modal-header border-secondary">
								<h5 class="modal-title text-white">Chia sẻ video này</h5>
								<button type="button" class="btn-close btn-close-white"
									data-bs-dismiss="modal" aria-label="Close"></button>
							</div>
							<div class="modal-body">
								<form action="share" method="post">
									<input type="hidden" name="videoId" value="${video.id}">

									<div class="mb-3">
										<label class="form-label text-secondary">Người gửi</label> <input
											type="text" class="form-control form-control-dark"
											value="${not empty sessionScope.currentUser ? sessionScope.currentUser.email : 'Bạn chưa đăng nhập'}"
											disabled
											style="background: #2a2a2a; border: 1px solid #333; color: #aaa;">
									</div>

									<div class="mb-3">
										<label class="form-label text-secondary">Người nhận
											(Email)</label> <input type="email" name="email"
											class="form-control form-control-dark"
											placeholder="friend@example.com" required
											style="background: #121212; border: 1px solid #333; color: white;">
									</div>

									<div class="mb-3">
										<label class="form-label text-secondary">Tiêu đề</label> <input
											type="text" name="subject"
											class="form-control form-control-dark"
											value="Tôi muốn chia sẻ video này với bạn: ${video.title}"
											style="background: #121212; border: 1px solid #333; color: white;">
									</div>

									<div class="mb-3">
										<label class="form-label text-secondary">Nội dung</label>
										<c:set var="fullLink"
											value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/detail?id=${video.id}" />

										<textarea name="content"
											class="form-control form-control-dark" rows="3"
											style="background: #121212; border: 1px solid #333; color: white;">Hãy xem video "${video.title}" tại đây nhé: ${fullLink}</textarea>
									</div>

									<div class="d-flex justify-content-end">
										<c:if test="${not empty sessionScope.currentUser}">
											<button type="submit"
												class="btn btn-primary btn-sm rounded-pill px-4">Gửi
												Email</button>
										</c:if>
										<c:if test="${empty sessionScope.currentUser}">
											<a href="login"
												class="btn btn-danger btn-sm rounded-pill px-4">Đăng
												nhập để gửi</a>
										</c:if>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>

				<div class="p-3 rounded" style="background-color: #1e1e1e;">
                    <h6 class="text-white fw-bold mb-2">Mô tả video</h6>
                    <p class="mb-0 text-light-emphasis small" style="white-space: pre-line;">
                        ${video.description}
                    </p>
                </div>
                <hr class="border-secondary my-4">

				<div id="comments">
					<h5 class="text-white mb-3">
						Bình luận <span class="text-secondary">(${comments.size()})</span>
					</h5>

					<c:choose>
						<c:when test="${not empty sessionScope.currentUser}">
							<div class="d-flex gap-3 mb-4">
								<img
									src="https://ui-avatars.com/api/?name=${sessionScope.currentUser.fullname}&background=random"
									class="rounded-circle" width="40" height="40">
								<form action="comment" method="post" class="w-100">
									<input type="hidden" name="videoId" value="${video.id}">
									<div class="form-floating mb-2">
										<textarea
											class="form-control bg-dark text-white border-secondary"
											placeholder="Viết bình luận..." name="content"
											style="height: 60px" required></textarea>
										<label class="text-secondary">Viết bình luận...</label>
									</div>
									<div class="d-flex justify-content-end">
										<button type="submit"
											class="btn btn-primary btn-sm rounded-pill px-3">Bình
											luận</button>
									</div>
								</form>
							</div>
						</c:when>
						<c:otherwise>
							<div
								class="alert alert-dark border-secondary text-secondary mb-4">
								Vui lòng <a href="login"
									class="text-danger fw-bold text-decoration-none">đăng nhập</a>
								để bình luận.
							</div>
						</c:otherwise>
					</c:choose>

					<div class="comment-list">
						<c:forEach items="${comments}" var="cmt">
							<div class="d-flex gap-3 mb-3">
								<img
									src="https://ui-avatars.com/api/?name=${cmt.user.fullname}&background=random"
									class="rounded-circle" width="40" height="40">
								<div>
									<div class="text-white fw-bold" style="font-size: 0.9rem;">
										${cmt.user.fullname} <span
											class="text-secondary fw-normal ms-2"
											style="font-size: 0.8rem;"> <fmt:formatDate
												value="${cmt.commentDate}" pattern="dd/MM/yyyy" />
										</span>
									</div>
									<p class="text-light-emphasis mb-0 mt-1"
										style="font-size: 0.95rem;">${cmt.content}</p>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>

            <div class="col-lg-4">
                <h5 class="text-white mb-3">Video khác</h5>
                
                <c:forEach items="${relatedVideos}" var="item">
                    <a href="detail?id=${item.id}" class="related-video-item">
                        <img src="https://img.youtube.com/vi/${item.id}/mqdefault.jpg" 
                             alt="${item.title}" class="related-video-thumb">
                        <div class="related-video-info pt-1">
                            <h6>${item.title}</h6>
                            <p class="text-secondary small mb-0">KudoHa Channel</p>
                            <p class="text-secondary small">${item.views} views</p>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </div>
    </div>
    
    <div class="modal fade" id="shareModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content" style="background-color: #1e1e1e; border: 1px solid #333;">
                <div class="modal-header border-secondary">
                    <h5 class="modal-title text-white">Chia sẻ video này</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="share" method="post">
                        <input type="hidden" name="videoId" value="${video.id}">
                        <div class="mb-3">
                            <label class="form-label text-secondary">Nhập email người nhận</label>
                            <input type="email" name="email" class="form-control form-control-dark" 
                                   placeholder="friend@example.com" required 
                                   style="background: #121212; border: 1px solid #333; color: white;">
                        </div>
                        <div class="d-flex justify-content-end">
                            <button type="submit" class="btn btn-primary btn-sm rounded-pill px-4">Gửi</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
	<script>
    // Kiểm tra thông báo từ Share Servlet (nếu có)
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get('shared') === 'true') {
        alert("Đã gửi email chia sẻ thành công!");
    }

    // Hàm xử lý Like Ajax
    function toggleLike(videoId) {
        fetch('like-video?videoId=' + videoId, {
            method: 'POST'
        })
        .then(response => {
            if (response.status === 401) {
                // Chưa đăng nhập
                alert("Vui lòng đăng nhập để thích video này!");
                // Có thể redirect: window.location.href = 'login';
            } else if (response.ok) {
                return response.text();
            } else {
                alert("Có lỗi xảy ra!");
            }
        })
        .then(data => {
            const btn = document.getElementById('likeBtn');
            if (data === 'liked') {
                btn.innerHTML = '<i class="fas fa-thumbs-up me-1"></i> Đã thích';
                btn.classList.add('active'); // Bạn cần CSS class .active đổi màu chữ thành xanh
                btn.style.color = '#3ea6ff';
            } else if (data === 'unliked') {
                btn.innerHTML = '<i class="far fa-thumbs-up me-1"></i> Thích';
                btn.classList.remove('active');
                btn.style.color = '#fff';
            }
        })
        .catch(error => console.error('Error:', error));
    }
</script>
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>