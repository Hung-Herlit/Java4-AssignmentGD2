package asm.wetube.controller;

import asm.wetube.dao.CommentDAO;
import asm.wetube.entity.Comment;
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

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // 1. Kiểm tra đăng nhập
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        // Nếu chưa đăng nhập thì đẩy về trang login (hoặc báo lỗi tùy bạn)
        if (currentUser == null) {
            resp.sendRedirect("login"); 
            return;
        }

        String videoId = req.getParameter("videoId");
        String content = req.getParameter("content");

        if (content != null && !content.trim().isEmpty()) {
            CommentDAO commentDAO = new CommentDAO();
            try {
                Comment comment = new Comment();
                comment.setContent(content);
                comment.setCommentDate(new Date());
                comment.setUser(currentUser);
                
                Video video = new Video();
                video.setId(videoId);
                comment.setVideo(video);

                commentDAO.create(comment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Quay lại trang detail (neo #comments để trượt xuống phần bình luận)
        resp.sendRedirect("detail?id=" + videoId + "#comments");
    }
}