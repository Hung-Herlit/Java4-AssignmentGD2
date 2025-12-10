package asm.wetube.controller;

import asm.wetube.dao.FavoriteDAO;
import asm.wetube.entity.Favorite;
import asm.wetube.entity.User;
import asm.wetube.entity.Video;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@WebServlet("/like-video")
public class LikeVideoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Kiểm tra đăng nhập
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            // Trả về lỗi 401 để Client biết chưa đăng nhập
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
            return;
        }

        // 2. Lấy VideoId từ request
        String videoId = req.getParameter("videoId");
        if (videoId == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        FavoriteDAO favoriteDAO = new FavoriteDAO();
        try {
            // 3. Kiểm tra xem user đã like video này chưa
            Favorite favorite = favoriteDAO.findFavorite(currentUser.getId(), videoId);

            if (favorite == null) {
                // Chưa like -> Thêm mới (Like)
                Video video = new Video();
                video.setId(videoId); // Chỉ cần set ID để Hibernate map khóa ngoại
                
                Favorite newFavorite = new Favorite();
                newFavorite.setUser(currentUser);
                newFavorite.setVideo(video);
                newFavorite.setLikeDate(new Date());
                
                favoriteDAO.create(newFavorite);
                
                // Phản hồi text để JS đổi nút thành "Đã thích"
                resp.getWriter().write("liked"); 
            } else {
                // Đã like -> Xóa (Unlike)
                favoriteDAO.delete(favorite);
                
                // Phản hồi text để JS đổi nút về "Thích"
                resp.getWriter().write("unliked");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        // Không gọi favoriteDAO.close() nữa
    }
}