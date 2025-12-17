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

@WebServlet({"/admin/videos", "/admin/video/edit", "/admin/video/create", "/admin/video/update", "/admin/video/delete"})
public class AdminVideoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        VideoDAO videoDAO = new VideoDAO();

        if (uri.contains("edit")) {
            // --- CHẾ ĐỘ SỬA ---
            String id = req.getParameter("id");
            Video video = videoDAO.findById(id);
            req.setAttribute("video", video); // Gửi video sang để điền dữ liệu cũ
            req.getRequestDispatcher("/views/admin/video-edit.jsp").forward(req, resp);

        } else if (uri.contains("create")) {
            // --- CHẾ ĐỘ THÊM MỚI ---
            // Forward sang trang edit nhưng không có dữ liệu "video"
            req.getRequestDispatcher("/views/admin/video-edit.jsp").forward(req, resp);

        } else if (uri.contains("delete")) {
            // --- XÓA ---
            String id = req.getParameter("id");
            Video video = videoDAO.findById(id);
            if (video != null) {
            	video.setActive(false);
                videoDAO.update(video);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/videos");

        } else {
            // --- DANH SÁCH ---
            List<Video> list = videoDAO.findAll();
            req.setAttribute("videos", list);
            req.getRequestDispatcher("/views/admin/video-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String uri = req.getRequestURI();
        VideoDAO videoDAO = new VideoDAO();

        try {
            String id = req.getParameter("id");
            Video video = new Video();
            
            // Lấy dữ liệu từ form
            video.setId(id);
            video.setTitle(req.getParameter("title"));
            video.setPoster(req.getParameter("poster"));
            video.setDescription(req.getParameter("description"));
            video.setActive(req.getParameter("active") != null);

            if (uri.contains("create")) {
                // --- XỬ LÝ TẠO MỚI ---
                if (videoDAO.findById(id) != null) {
                    req.setAttribute("error", "Video ID " + id + " đã tồn tại!");
                    req.getRequestDispatcher("/views/admin/video-edit.jsp").forward(req, resp);
                    return;
                }
                video.setViews(0); // Video mới view bằng 0
                videoDAO.create(video);

            } else if (uri.contains("update")) {
                // --- XỬ LÝ CẬP NHẬT ---
                Video oldVideo = videoDAO.findById(id);
                if (oldVideo != null) {
                    video.setViews(oldVideo.getViews()); // Giữ nguyên lượt view cũ
                    videoDAO.update(video);
                }
            }

            resp.sendRedirect(req.getContextPath() + "/admin/videos");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi: " + e.getMessage());
            req.getRequestDispatcher("/views/admin/video-edit.jsp").forward(req, resp);
        }
    }
}