package asm.wetube.controller;

import asm.wetube.dao.VideoDAO;
import asm.wetube.entity.Video;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        // 1. Lấy từ khóa tìm kiếm
        String keyword = req.getParameter("keyword");
        
        VideoDAO videoDAO = new VideoDAO();
        try {
            List<Video> results;
            
            // 2. Nếu từ khóa trống, trả về tất cả video (hoặc trang chủ)
            if (keyword == null || keyword.trim().isEmpty()) {
                results = videoDAO.findAllActive();
                keyword = ""; // Để hiển thị lại trên giao diện
            } else {
                // 3. Tìm kiếm theo tiêu đề
                results = videoDAO.findByTitle(keyword);
            }

            // 4. Đẩy dữ liệu sang trang kết quả
            req.setAttribute("videos", results);
            req.setAttribute("keyword", keyword); // Gửi lại từ khóa để hiện lên tiêu đề "Kết quả cho..."
            
            req.getRequestDispatcher("/views/search-result.jsp").forward(req, resp);
            
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("index");
        }
        // Không gọi videoDAO.close() vì AbstractDAO của bạn chưa có hàm này
    }
}