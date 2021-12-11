package com.yoongspace.demo.DTO;

import com.yoongspace.demo.model.CommentEntity;
import com.yoongspace.demo.model.FeedEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private String feedid;
    private String commenttext;
    private String studentid;
    private String commentwrite;
    private String writername;
    private String writerinform;

    public CommentDTO(final CommentEntity entity){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss");
        this.id = entity.getId();
        this.feedid = entity.getFeedid();
        this.commenttext=entity.getCommenttext();
        this.studentid=entity.getStudentid();
        this.commentwrite=df.format(entity.getCommentwrite());
        this.writername=entity.getWritername();
        this.writerinform=entity.getWriterinform();

    }

    public static CommentEntity toEntity(final CommentDTO dto){
        return CommentEntity.builder()
                .feedid(dto.getFeedid())
                .commenttext(dto.getCommenttext())
                .build();
    }
}

