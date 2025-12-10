package asm.wetube.dao;

import asm.wetube.entity.User;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class UserDAO extends AbstractDAO<User> {

    public UserDAO() {
        super();
    }

    /**
     * Tìm người dùng theo ID (Username)
     * Dùng cho chức năng Đăng nhập và hiển thị Profile
     */
    public User findById(String id) {
        return super.findById(User.class, id);
    }

    /**
     * Tìm người dùng theo Email
     * Dùng cho chức năng Quên mật khẩu hoặc kiểm tra trùng Email khi đăng ký
     */
    public User findByEmail(String email) {
        String jsql = "SELECT o FROM User o WHERE o.email = :email";
        TypedQuery<User> query = em.createQuery(jsql, User.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Lấy danh sách tất cả người dùng
     * Dùng cho trang quản trị Admin
     */
    public List<User> findAll() {
        return super.findAll(User.class);
    }
    
    /**
     * Kiểm tra đăng nhập
     * @param id Username
     * @param password Password
     * @return User nếu đúng, null nếu sai
     */
    public User checkLogin(String id, String password) {
        User user = this.findById(id);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}