package asm.wetube.controller;

import asm.wetube.dao.UserDAO;
import asm.wetube.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// 1. Thêm mapping "/admin/user/create"
@WebServlet({"/admin/users", "/admin/user/edit", "/admin/user/create", "/admin/user/update", "/admin/user/delete"})
public class AdminUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        UserDAO userDAO = new UserDAO();

        if (uri.contains("edit")) {
            // --- EDIT MODE ---
            String id = req.getParameter("id");
            User user = userDAO.findById(id);
            req.setAttribute("user", user); // Gửi user sang để điền vào form
            req.getRequestDispatcher("/views/admin/user-edit.jsp").forward(req, resp);
            
        } else if (uri.contains("create")) {
            // --- CREATE MODE ---
            // Không gửi attribute "user" sang, để form trống
            req.getRequestDispatcher("/views/admin/user-edit.jsp").forward(req, resp);
            
        } else if (uri.contains("delete")) {
            // --- DELETE ---
            String id = req.getParameter("id");
            User user = userDAO.findById(id);
            if(user != null) {
                userDAO.delete(user);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        } else {
            // --- LIST MODE ---
            List<User> list = userDAO.findAll();
            req.setAttribute("users", list);
            req.getRequestDispatcher("/views/admin/user-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String uri = req.getRequestURI();
        UserDAO userDAO = new UserDAO();
        
        try {
            User user = new User();
            String id = req.getParameter("id");
            
            // Lấy thông tin từ form
            user.setId(id);
            user.setFullname(req.getParameter("fullname"));
            user.setEmail(req.getParameter("email"));
            user.setAdmin(req.getParameter("admin") != null);
            user.setPassword(req.getParameter("password")); // Lấy pass (quan trọng khi tạo mới)

            if (uri.contains("create")) {
                // --- XỬ LÝ TẠO MỚI ---
                // 1. Kiểm tra trùng ID
                if (userDAO.findById(id) != null) {
                    req.setAttribute("error", "Username " + id + " đã tồn tại!");
                    req.getRequestDispatcher("/views/admin/user-edit.jsp").forward(req, resp);
                    return;
                }
                // 2. Tạo mới
                userDAO.create(user);
                
            } else if (uri.contains("update")) {
                // --- XỬ LÝ CẬP NHẬT ---
                User oldUser = userDAO.findById(id);
                if (oldUser != null) {
                    // Nếu admin để trống pass thì giữ pass cũ
                    if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                        user.setPassword(oldUser.getPassword());
                    }
                    userDAO.update(user);
                }
            }
            
            resp.sendRedirect(req.getContextPath() + "/admin/users");
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi: " + e.getMessage());
            req.getRequestDispatcher("/views/admin/user-edit.jsp").forward(req, resp);
        }
    }
}