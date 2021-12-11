package com.yoongspace.demo.DTO;

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
public class FeedDTO {
    private Long id;
    private String cat;
    private boolean onlyfriend;
    private String feedtext;
    private Long like;
    private String studentid;
    private String feedwrite;
    private String writername;
    private String writerinform;

    public FeedDTO(final FeedEntity entity){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss");
        this.id = entity.getId();
        this.cat = entity.getCat();
        this.onlyfriend = entity.isOnlyfriend();
        this.feedtext=entity.getFeedtext();
        this.like=entity.getFeedlike();
        this.studentid=entity.getStudentid();
        this.feedwrite=df.format(entity.getFeedwrite());
        this.writername=entity.getWritername();
        this.writerinform=entity.getWriterinform();

    }

    public static FeedEntity toEntity(final FeedDTO dto){
        return FeedEntity.builder()
                .cat(dto.getCat())
                .onlyfriend(dto.isOnlyfriend())
                .feedtext(dto.getFeedtext())
                .build();
    }
}
