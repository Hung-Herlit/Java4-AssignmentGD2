package asm.wetube.dao;

import asm.wetube.entity.Share;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ShareDAO extends AbstractDAO<Share> {

    public ShareDAO() {
        super();
    }

    /**
     * Tìm lịch sử chia sẻ của một người dùng
     * Có thể dùng để hiển thị "Các video bạn đã chia sẻ"
     */
    public List<Share> findByUser(String userId) {
        String jsql = "SELECT o FROM Share o WHERE o.user.id = :uid";
        TypedQuery<Share> query = em.createQuery(jsql, Share.class);
        query.setParameter("uid", userId);
        return query.getResultList();
    }
    
    /**
     * Tìm danh sách chia sẻ của một video cụ thể
     * Dùng cho Admin thống kê video nào được share nhiều
     */
    public List<Share> findByVideo(String videoId) {
        String jsql = "SELECT o FROM Share o WHERE o.video.id = :vid";
        TypedQuery<Share> query = em.createQuery(jsql, Share.class);
        query.setParameter("vid", videoId);
        return query.getResultList();
    }
}