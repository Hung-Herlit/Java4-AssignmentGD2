package asm.wetube.controller;

import asm.wetube.dao.UserDAO;
import asm.wetube.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Hiển thị trang form đăng nhập
        // Bạn cần tạo file login.jsp trong thư mục webapp/views/
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Lấy tham số từ form
        String id = req.getParameter("id");
        String password = req.getParameter("password");
        
        // 2. Kiểm tra đăng nhập
        User user = userDAO.checkLogin(id, password);
        
        if (user != null) {
            // Đăng nhập thành công
            HttpSession session = req.getSession();
            session.setAttribute("currentUser", user); // Lưu user vào session
            
            // Nếu user định vào trang nào đó mà bị chặn, có thể redirect lại trang đó
            // Ở đây tạm thời redirect về trang chủ
            resp.sendRedirect("index"); 
        } else {
            // Đăng nhập thất bại
            req.setAttribute("message", "Tên đăng nhập hoặc mật khẩu không đúng!");
            req.setAttribute("id", id); // Giữ lại username để user đỡ phải nhập lại
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }
}