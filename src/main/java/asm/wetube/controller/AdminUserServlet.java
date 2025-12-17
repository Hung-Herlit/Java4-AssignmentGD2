package asm.wetube.controller;

import asm.wetube.dao.UserDAO;
import asm.wetube.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig; // Thêm dòng này
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part; // Thêm dòng này
import java.io.File; // Thêm dòng này
import java.io.IOException;
import java.nio.file.Files; // Thêm dòng này
import java.nio.file.Path; // Thêm dòng này
import java.util.List;

@WebServlet({"/admin/users", "/admin/user/edit", "/admin/user/create", "/admin/user/update", "/admin/user/delete"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class AdminUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // ... (Giữ nguyên logic doGet cũ)
        String uri = req.getRequestURI();
        UserDAO userDAO = new UserDAO();

        if (uri.contains("edit")) {
            String id = req.getParameter("id");
            User user = userDAO.findById(id);
            req.setAttribute("user", user);
            req.getRequestDispatcher("/views/admin/user-edit.jsp").forward(req, resp);
        } else if (uri.contains("create")) {
            req.getRequestDispatcher("/views/admin/user-edit.jsp").forward(req, resp);
        } else if (uri.contains("delete")) {
            String id = req.getParameter("id");
            User user = userDAO.findById(id);
            if(user != null) userDAO.delete(user);
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        } else {
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
            
            user.setId(id);
            user.setFullname(req.getParameter("fullname"));
            user.setEmail(req.getParameter("email"));
            user.setAdmin(req.getParameter("admin") != null);
            user.setPassword(req.getParameter("password"));

            // --- XỬ LÝ UPLOAD ẢNH (MỚI) ---
            Part part = req.getPart("photo"); // name="photo" bên file JSP
            String uploadedFileName = null;

            if (part != null && part.getSize() > 0) {
                String realPath = req.getServletContext().getRealPath("/images");
                if (!Files.exists(Path.of(realPath))) {
                    Files.createDirectories(Path.of(realPath));
                }
                String filename = Path.of(part.getSubmittedFileName()).getFileName().toString();
                uploadedFileName = id + "_" + System.currentTimeMillis() + "_" + filename;
                part.write(realPath + File.separator + uploadedFileName);
                
                user.setPicture(uploadedFileName);
            }
            // --------------------------------

            if (uri.contains("create")) {
                if (userDAO.findById(id) != null) {
                    req.setAttribute("error", "Username " + id + " đã tồn tại!");
                    req.getRequestDispatcher("/views/admin/user-edit.jsp").forward(req, resp);
                    return;
                }
                userDAO.create(user);
                
            } else if (uri.contains("update")) {
                User oldUser = userDAO.findById(id);
                if (oldUser != null) {
                    // Logic mật khẩu
                    if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                        user.setPassword(oldUser.getPassword());
                    }
                    // Logic Avatar: Nếu không upload ảnh mới thì giữ ảnh cũ
                    if (uploadedFileName == null) {
                        user.setPicture(oldUser.getPicture());
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