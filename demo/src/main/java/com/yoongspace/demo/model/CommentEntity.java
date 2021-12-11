package com.yoongspace.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.*;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="Comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String feedid;

    @Column(nullable = false)
    private String commenttext;

    @Column(nullable = false)
    private String studentid;

    @CreationTimestamp
    Date commentwrite;

    @Column(nullable = false)
    private String writername;

    @Column(nullable = false)
    private String writerinform;
}
