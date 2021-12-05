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
public class CommentDTO {
    private Long id;
    private FeedEntity feedid;
    private String commenttext;
    private String studentid;
}
