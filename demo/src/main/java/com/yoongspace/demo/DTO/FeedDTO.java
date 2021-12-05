package com.yoongspace.demo.DTO;

import com.yoongspace.demo.model.FeedEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public FeedDTO(final FeedEntity entity){
        this.id = entity.getId();
        this.cat = entity.getCat();
        this.onlyfriend = entity.isOnlyfriend();
        this.feedtext=entity.getFeedtext();
        this.like=entity.getFeedlike();
        this.studentid=entity.getStudentid();

    }

    public static FeedEntity toEntity(final FeedDTO dto){
        return FeedEntity.builder()
                .id(Long.valueOf(dto.getId()))
                .cat(dto.getCat())
                .onlyfriend(dto.isOnlyfriend())
                .feedtext(dto.getFeedtext())
                .build();
    }
}
