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

@WebServlet({"/index", "/home"}) // URL mapping
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VideoDAO videoDAO = new VideoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Lấy danh sách video Active từ CSDL
        // (Tạm thời lấy hết, sau này có thể thêm phân trang)
        List<Video> videos = videoDAO.findAllActive(); 

        // 2. Đẩy dữ liệu sang JSP
        req.setAttribute("videos", videos);

        // 3. Chuyển hướng về trang giao diện index.jsp
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
    
}