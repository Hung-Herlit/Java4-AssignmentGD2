package asm.wetube.entity;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="\"User\"") // Dùng dấu ngoặc kép vì "User" là từ khóa trong SQL
public class User {
    
    @Id
    @Column(name="Id")
    private String id;
    
    @Column(name="Password")
    private String password;
    
    @Column(name="Email")
    private String email;
    
    @Column(name="Fullname")
    private String fullname;
    
    @Column(name="Admin")
    private Boolean admin = false; // Mặc định là false (người dùng thường)

    // Liên kết 1-N tới bảng Favorite
    @OneToMany(mappedBy="user")
    List<Favorite> favorites;
    
    // Liên kết 1-N tới bảng Share
    @OneToMany(mappedBy="user")
    List<Share> shares;
    
}