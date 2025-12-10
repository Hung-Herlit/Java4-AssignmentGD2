package asm.wetube.controller;

import asm.wetube.dao.FavoriteDAO;
import asm.wetube.dao.VideoDAO;
import asm.wetube.entity.User;
import asm.wetube.entity.Video;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/detail")
public class DetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        // 1. Validate ID
        if (id == null || id.trim().isEmpty()) {
            resp.sendRedirect("index");
            return;
        }

        VideoDAO videoDAO = new VideoDAO();
        Video video = videoDAO.findById(id);

        if (video == null) {
            resp.sendRedirect("index");
            return;
        }

        // 2. Tăng view
        video.setViews(video.getViews() + 1);
        videoDAO.update(video);

        // 3. Lấy video gợi ý
        List<Video> relatedVideos = videoDAO.findAllActive();
        relatedVideos.removeIf(v -> v.getId().equals(video.getId()));

        // --- PHẦN BỔ SUNG: Kiểm tra trạng thái Like ---
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        boolean isLiked = false;

        if (currentUser != null) {
            FavoriteDAO favoriteDAO = new FavoriteDAO();
            // Hàm isFavorited đã có trong FavoriteDAO bạn cung cấp từ đầu
            isLiked = favoriteDAO.isFavorited(currentUser.getId(), video.getId());
        }
        
        req.setAttribute("flagLiked", isLiked); // Gửi cờ 'flagLiked' sang JSP
        // ----------------------------------------------

        req.setAttribute("video", video);
        req.setAttribute("relatedVideos", relatedVideos);

        req.getRequestDispatcher("/views/detail.jsp").forward(req, resp);
    }
}