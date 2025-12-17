package asm.wetube.controller;

import asm.wetube.dao.UserDAO;
import asm.wetube.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/profile")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("currentUser");

        if (user == null) {
            resp.sendRedirect("login");
            return;
        }

        req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        
        if ("update_info".equals(action)) {
            doUpdateInfo(req, resp);
        } else if ("change_password".equals(action)) {
            doChangePassword(req, resp);
        }
    }

    private void doUpdateInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        UserDAO userDAO = new UserDAO();

        try {
            // 1. Lấy thông tin text
            String fullname = req.getParameter("fullname");
            String email = req.getParameter("email");

            // 2. Xử lý Upload Ảnh
            Part part = req.getPart("photo"); // name="photo" trong form
            
            if (part != null && part.getSize() > 0) {
                // Đường dẫn thư mục lưu ảnh: /webapp/images
                String realPath = req.getServletContext().getRealPath("/images");
                if (!Files.exists(Path.of(realPath))) {
                    Files.createDirectories(Path.of(realPath));
                }

                // Lấy tên file gốc
                String filename = Path.of(part.getSubmittedFileName()).getFileName().toString();
                // Đổi tên file để tránh trùng (ví dụ: user01_avatar.jpg)
                String newFilename = currentUser.getId() + "_" + System.currentTimeMillis() + "_" + filename;
                
                // Lưu file
                part.write(realPath + File.separator + newFilename);
                
                // Cập nhật tên ảnh vào object
                currentUser.setPicture(newFilename);
            }

            // 3. Cập nhật thông tin khác
            currentUser.setFullname(fullname);
            currentUser.setEmail(email);

            // 4. Lưu xuống DB
            userDAO.update(currentUser);
            
            // Cập nhật lại session
            session.setAttribute("currentUser", currentUser);
            req.setAttribute("message", "Cập nhật hồ sơ thành công!");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi cập nhật: " + e.getMessage());
        }
        req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
    }

    private void doChangePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        UserDAO userDAO = new UserDAO();

        String oldPass = req.getParameter("oldPass");
        String newPass = req.getParameter("newPass");
        String confirmPass = req.getParameter("confirmPass");

        try {
            // 1. Kiểm tra mật khẩu cũ
            if (!currentUser.getPassword().equals(oldPass)) {
                req.setAttribute("passError", "Mật khẩu cũ không chính xác!");
            } 
            // 2. Kiểm tra xác nhận mật khẩu mới
            else if (!newPass.equals(confirmPass)) {
                req.setAttribute("passError", "Mật khẩu xác nhận không khớp!");
            } 
            else {
                // 3. Đổi mật khẩu
                currentUser.setPassword(newPass);
                userDAO.update(currentUser);
                session.setAttribute("currentUser", currentUser);
                req.setAttribute("passMessage", "Đổi mật khẩu thành công!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("passError", "Lỗi hệ thống!");
        }
        req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
    }
}