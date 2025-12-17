package asm.wetube.dao;

import asm.wetube.entity.Video;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class VideoDAO extends AbstractDAO<Video> {

    public VideoDAO() {
        super();
    }

    public void deleteSoft(String id) {
        Video video = findById(id);
        if (video != null) {
            video.setActive(false); // Đổi trạng thái thành ngưng hoạt động
            update(video); // Lưu lại
        }
    }
    public Video findById(String id) {
        return super.findById(Video.class, id);
    }

    public List<Video> findAll() {
        return super.findAll(Video.class);
    }

    public List<Video> findAll(int page, int pageSize) {
        String jsql = "SELECT o FROM Video o WHERE o.active = true"; // Chỉ lấy video đang hoạt động
        TypedQuery<Video> query = em.createQuery(jsql, Video.class);
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public List<Video> findByTitle(String keyword) {
        String jsql = "SELECT o FROM Video o WHERE o.title LIKE :keyword AND o.active = true";
        TypedQuery<Video> query = em.createQuery(jsql, Video.class);
        query.setParameter("keyword", "%" + keyword + "%");
        return query.getResultList();
    }
    
    public List<Video> findTopViews(int top) {
        String jsql = "SELECT o FROM Video o WHERE o.active = true ORDER BY o.views DESC";
        TypedQuery<Video> query = em.createQuery(jsql, Video.class);
        query.setMaxResults(top);
        return query.getResultList();
    }
    
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