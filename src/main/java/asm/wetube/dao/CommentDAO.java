package asm.wetube.dao;

import asm.wetube.entity.Comment;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class CommentDAO extends AbstractDAO<Comment> {
    
    // Lấy danh sách comment của 1 video (Sắp xếp mới nhất lên đầu)
    public List<Comment> findByVideoId(String videoId) {
        String jsql = "SELECT c FROM Comment c WHERE c.video.id = :vid ORDER BY c.commentDate DESC, c.id DESC";
        TypedQuery<Comment> query = em.createQuery(jsql, Comment.class);
        query.setParameter("vid", videoId);
        return query.getResultList();
    }
}