package asm.wetube.filter;

import asm.wetube.entity.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({ "/*" }) // Áp dụng cho mọi request để set UTF-8
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 1. Thiết lập UTF-8
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // 2. Lấy đường dẫn user đang truy cập
        String uri = req.getRequestURI();
        
        // 3. Lấy thông tin User từ Session (Giả sử key session là "currentUser")
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("currentUser");
        
        // --- LOGIC BẢO MẬT ---
        
        // Case A: Truy cập trang Admin
        if (uri.contains("/admin/")) {
            if (user == null || !user.getAdmin()) {
                // Chưa đăng nhập hoặc không phải admin => Đẩy về trang chủ
                resp.sendRedirect(req.getContextPath() + "/index"); 
                return;
            }
        }
        
        // Case B: Truy cập các chức năng cần đăng nhập (Ví dụ: like, profile)
        // Bạn có thể thêm các từ khóa khác vào điều kiện này
        if (uri.contains("/profile") || uri.contains("/change-password") || uri.contains("/favorite")) {
            if (user == null) {
                // Chưa đăng nhập => Đẩy về trang Login
                // Lưu lại trang hiện tại để login xong redirect lại (optional)
                resp.sendRedirect(req.getContextPath() + "/login"); 
                return;
            }
        }

        // Cho phép đi tiếp
        chain.doFilter(request, response);
    }
}