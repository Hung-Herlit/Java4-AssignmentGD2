package asm.wetube.controller;

import asm.wetube.dao.FavoriteDAO;
import asm.wetube.entity.Favorite;
import asm.wetube.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/favorite")
public class FavoriteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Kiểm tra đăng nhập (Dù Filter đã chặn, kiểm tra lại cho chắc)
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("currentUser");

        if (user == null) {
            resp.sendRedirect("login");
            return;
        }

        FavoriteDAO favoriteDAO = new FavoriteDAO();
        
        try {
            // 2. Lấy danh sách video đã thích của User
            List<Favorite> favorites = favoriteDAO.findByUser(user.getId());
            
            // 3. Đẩy sang JSP
            req.setAttribute("favorites", favorites);
            req.getRequestDispatcher("/views/favorite.jsp").forward(req, resp);
            
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("index");
        }
    }
}