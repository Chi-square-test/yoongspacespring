package com.yoongspace.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="Feed")
public class FeedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cat;

    @Column(nullable = false)
    private boolean onlyfriend;

    @Column(nullable = false)
    private String feedtext;

    @Builder.Default
    @Column
    private Long feedlike= Long.valueOf(0);

    @Column(nullable = false)
    private String studentid;

    @CreationTimestamp
    Date feedwrite;

    @Column(nullable = false)
    private String writername;

    @Column(nullable = false)
    private String writerinform;



}
