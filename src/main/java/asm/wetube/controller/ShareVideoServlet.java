package asm.wetube.controller;

import asm.wetube.dao.ShareDAO;
import asm.wetube.entity.Share;
import asm.wetube.entity.User;
import asm.wetube.entity.Video;
import asm.wetube.util.EmailUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@WebServlet("/share")
public class ShareVideoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            resp.sendRedirect("login");
            return;
        }

        String videoId = req.getParameter("videoId");
        String emailTo = req.getParameter("email");
        String subject = req.getParameter("subject");
        String content = req.getParameter("content");

        try {
            // 1. Gửi Email
            EmailUtil.sendEmail(emailTo, subject, content);
            
            // 2. Lưu lịch sử Share vào Database
            ShareDAO shareDAO = new ShareDAO();
            try {
                Share share = new Share();
                share.setUser(currentUser);
                
                Video video = new Video();
                video.setId(videoId);
                share.setVideo(video);
                
                share.setEmails(emailTo);
                share.setShareDate(new Date());
                
                shareDAO.create(share);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            // Không gọi shareDAO.close() nữa
            
            // 3. Quay lại trang detail kèm thông báo
            resp.sendRedirect("detail?id=" + videoId + "&shared=true");
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi gửi email: " + e.getMessage());
            req.getRequestDispatcher("/views/detail.jsp").forward(req, resp);
        }
    }
}