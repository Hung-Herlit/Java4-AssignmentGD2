package asm.wetube.entity;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="Favorite")
public class Favorite {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Long id;
    
    // Liên kết N-1 tới User
    @ManyToOne 
    @JoinColumn(name="UserId")
    private User user;
    
    // Liên kết N-1 tới Video
    @ManyToOne 
    @JoinColumn(name="VideoId")
    private Video video;
    
    @Temporal(TemporalType.DATE)
    @Column(name="LikeDate")
    private Date likeDate = new Date(); // Tự động lấy ngày hiện tại
}