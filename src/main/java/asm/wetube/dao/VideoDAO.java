package asm.wetube.dao;

import asm.wetube.entity.Video;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class VideoDAO extends AbstractDAO<Video> {

    public VideoDAO() {
        super();
    }

    /**
     * Tìm video theo ID
     * Dùng cho trang xem chi tiết Video (Detail)
     */
    public Video findById(String id) {
        return super.findById(Video.class, id);
    }

    /**
     * Lấy tất cả video
     * Dùng cho trang Admin quản lý
     */
    public List<Video> findAll() {
        return super.findAll(Video.class);
    }

    /**
     * Lấy danh sách video có phân trang
     * Dùng cho trang chủ để không load quá nhiều video một lúc
     * @param page Trang hiện tại (bắt đầu từ 0)
     * @param pageSize Số lượng video trên một trang
     */
    public List<Video> findAll(int page, int pageSize) {
        String jsql = "SELECT o FROM Video o WHERE o.active = true"; // Chỉ lấy video đang hoạt động
        TypedQuery<Video> query = em.createQuery(jsql, Video.class);
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    /**
     * Tìm kiếm video theo tiêu đề (gần đúng)
     * Dùng cho thanh tìm kiếm header
     * @param keyword Từ khóa tìm kiếm
     */
    public List<Video> findByTitle(String keyword) {
        String jsql = "SELECT o FROM Video o WHERE o.title LIKE :keyword AND o.active = true";
        TypedQuery<Video> query = em.createQuery(jsql, Video.class);
        query.setParameter("keyword", "%" + keyword + "%");
        return query.getResultList();
    }
    
    /**
     * Lấy Top video được xem nhiều nhất
     * Dùng để gợi ý video nổi bật
     * @param top Số lượng video muốn lấy (ví dụ: Top 10)
     */
    public List<Video> findTopViews(int top) {
        String jsql = "SELECT o FROM Video o WHERE o.active = true ORDER BY o.views DESC";
        TypedQuery<Video> query = em.createQuery(jsql, Video.class);
        query.setMaxResults(top);
        return query.getResultList();
    }
    
    /**
     * Lấy tổng số lượng video
     * Dùng để tính tổng số trang cho phân trang
     */
    public long count() {
        String jsql = "SELECT count(o) FROM Video o WHERE o.active = true";
        TypedQuery<Long> query = em.createQuery(jsql, Long.class);
        return query.getSingleResult();
    }
    
    public List<Video> findAllActive() {
        String jsql = "SELECT o FROM Video o WHERE o.active = true";
        TypedQuery<Video> query = em.createQuery(jsql, Video.class);
        return query.getResultList();
    }
}