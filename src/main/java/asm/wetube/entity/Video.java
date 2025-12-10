package asm.wetube.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date; // Bạn có thể sẽ cần Date cho các entity khác

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="Video")
public class Video {
    @Id
    @Column(name="Id")
    private String id;
    
    @Column(name="Title")
    private String title;
    
    @Column(name="Poster")
    private String poster;
    
    @Column(name="Views")
    private Integer views = 0;
    
    @Column(name="Description")
    private String description;
    
    @Column(name="Active")
    private Boolean active = true;

    @OneToMany(mappedBy="video")
    List<Favorite> favorites;
    
    @OneToMany(mappedBy="video")
    List<Share> shares;
}