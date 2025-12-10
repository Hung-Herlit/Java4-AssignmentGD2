package asm.wetube.controller;

import asm.wetube.dao.UserDAO;
import asm.wetube.dao.VideoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/admin/dashboard", "/admin/index"})
public class AdminHomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        VideoDAO videoDAO = new VideoDAO();
        UserDAO userDAO = new UserDAO();

        try {
            // Thống kê đơn giản
            req.setAttribute("videoCount", videoDAO.findAll().size());
            req.setAttribute("userCount", userDAO.findAll().size());
            
            // Tính tổng lượt xem (Java 8 Stream)
            long totalViews = videoDAO.findAll().stream().mapToLong(v -> v.getViews()).sum();
            req.setAttribute("totalViews", totalViews);

            req.getRequestDispatcher("/views/admin/home.jsp").forward(req, resp);
        } finally {
            // videoDAO.close(); // Nếu bạn không dùng hàm close thì bỏ qua dòng này
        }
    }
}