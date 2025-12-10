package asm.wetube.controller;

import asm.wetube.dao.UserDAO;
import asm.wetube.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Hiển thị form đăng ký
        req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        String id = req.getParameter("id");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");

        UserDAO userDAO = new UserDAO();
        
        try {
            // 1. Kiểm tra ID đã tồn tại chưa
            if (userDAO.findById(id) != null) {
                req.setAttribute("message", "Tên đăng nhập đã tồn tại!");
                req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
                return;
            }

            // 2. Kiểm tra Email đã tồn tại chưa
            if (userDAO.findByEmail(email) != null) {
                req.setAttribute("message", "Email này đã được sử dụng!");
                req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
                return;
            }

            // 3. Tạo User mới
            User user = new User();
            user.setId(id);
            user.setPassword(password);
            user.setFullname(fullname);
            user.setEmail(email);
            user.setAdmin(false); // Mặc định là user thường

            userDAO.create(user);

            // 4. Chuyển hướng về trang Login kèm thông báo
            req.setAttribute("message", "Đăng ký thành công! Hãy đăng nhập ngay.");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "Lỗi hệ thống: " + e.getMessage());
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
        }
    }
}