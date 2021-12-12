package com.yoongspace.demo.controller;

import com.yoongspace.demo.DTO.*;
import com.yoongspace.demo.model.CommentEntity;
import com.yoongspace.demo.model.FeedEntity;
import com.yoongspace.demo.model.UserEntity;
import com.yoongspace.demo.service.CommentService;
import com.yoongspace.demo.service.FeedService;
import com.yoongspace.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/yoongfeed")
public class FeedController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private FeedService feedService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getFeed(@AuthenticationPrincipal String studentid){
        List<FeedEntity> entities = feedService.allfeed(studentid);
        return getResponseEntity(entities);
    }


    @GetMapping("/onlyf")
    public ResponseEntity<?> getonlyFeed(@AuthenticationPrincipal String studentid){
        List<FeedEntity> entities = feedService.onlyfeed(studentid);
        return getResponseEntity(entities);
    }


    @PostMapping("/write")
    public ResponseEntity<?> createFeed(@AuthenticationPrincipal String studentid, @RequestBody FeedDTO feedDTO){
        try{
            UserEntity user =userService.getStudentinfo(studentid);
            FeedEntity entity=FeedDTO.toEntity(feedDTO);
            String inform=user.getGrade()+"학년/"+studentid.substring(2,4)+"학번";
            entity.setId(null);
            entity.setStudentid(studentid);
            entity.setWritername(user.getUsername());
            entity.setWriterinform(inform);
            List<FeedEntity> entities = feedService.create(entity);
            return getResponseEntity(entities);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<FeedDTO> res = ResponseDTO.<FeedDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(res);
        }
    }
    @GetMapping("/check")
    public  ResponseEntity<?> check(@AuthenticationPrincipal String studentid,@RequestParam Long feedid){
        try{
            feedService.checkowner(feedid,studentid);
            return ResponseEntity.ok().body(studentid);
        }catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<FeedDTO> res = ResponseDTO.<FeedDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(res);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatefeed(@AuthenticationPrincipal String studentid,@RequestBody FeedDTO dto){
        try{
            feedService.checkowner(Long.valueOf(dto.getId()),studentid);
            FeedEntity entity = FeedDTO.toEntity(dto);
            entity.setId(dto.getId());
            entity.setStudentid(studentid);
            List<FeedEntity> entities = feedService.update(entity);
            return getResponseEntity(entities);
        }catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<FeedDTO> res = ResponseDTO.<FeedDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(res);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@AuthenticationPrincipal String studentid,@RequestBody FeedDTO dto){
        try{
            feedService.checkowner(dto.getId(),studentid);
            FeedEntity entity = FeedDTO.toEntity(dto);
            entity.setId(dto.getId());
            entity.setStudentid(studentid);
            List<FeedEntity> entities = feedService.delete(entity);
            return getResponseEntity(entities);
        }catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<FeedDTO> res = ResponseDTO.<FeedDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("/currentuser") //새로운 컨트롤러로 분리해야하지만 임시로 피드에서 관리
    public ResponseEntity<?> connectuserinfo(@AuthenticationPrincipal String studentid){
        UserEntity userinfo=userService.getStudentinfo(studentid);
        final UserDTO resuserDTO=UserDTO.builder()
                .studentid(userinfo.getStudentid())
                .username(userinfo.getUsername())
                .grade(userinfo.getGrade())
                .userinfo(userinfo.getUserinfo())
                .build();
        return ResponseEntity.ok().body(resuserDTO);

    }

    @GetMapping("/like") //새로운 컨트롤러로 분리해야하지만 임시로 피드에서 관리
    public ResponseEntity<?> pushlike(@AuthenticationPrincipal String studentid,@RequestParam String feedid){
        try{
            Long likecount=feedService.liketoggle(feedid,studentid);
            return ResponseEntity.ok().body(likecount);
        }catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<FeedDTO> res = ResponseDTO.<FeedDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(res);
        }

    }

    @PostMapping("/comment") //댓글도 분리를 해야하나 임시로 피드에서 관리
    public ResponseEntity commentwrite(@AuthenticationPrincipal String studentid, @RequestBody CommentDTO dto){
        try{
            UserEntity user =userService.getStudentinfo(studentid);
            String inform=user.getGrade()+"학년/"+studentid.substring(2,4)+"학번";
            CommentEntity entity=CommentDTO.toEntity(dto);
            entity.setId(null);
            entity.setStudentid(studentid);
            entity.setWritername(user.getUsername());
            entity.setWriterinform(inform);
            List<CommentEntity> entities = commentService.commentcreate(entity);
            List<CommentDTO> dtos = entities.stream().map(CommentDTO::new).collect(Collectors.toList());
            ResponseDTO<CommentDTO> res= ResponseDTO.<CommentDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<CommentDTO> res = ResponseDTO.<CommentDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("/checkcomment")
    public  ResponseEntity<?> checkcomment(@AuthenticationPrincipal String studentid,@RequestParam Long commentid){
        try{
            feedService.checkowner(commentid,studentid);
            return ResponseEntity.ok().body(studentid);
        }catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<CommentDTO> res = ResponseDTO.<CommentDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("/comment")
    public ResponseEntity<?> getFeedCommmet(@AuthenticationPrincipal String studentid,@RequestParam String feedid){
        try {
            List<CommentEntity> entities = commentService.feedscomment(studentid,feedid);
            List<CommentDTO> dtos = entities.stream().map(CommentDTO::new).collect(Collectors.toList());
            ResponseDTO<CommentDTO> res= ResponseDTO.<CommentDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(res);
        }catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<CommentDTO> res = ResponseDTO.<CommentDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(res);
        }
    }

    @DeleteMapping("/comment")
    public ResponseEntity<?> deletecomment(@AuthenticationPrincipal String studentid,@RequestBody CommentDTO dto){
        try{
            commentService.checkowner(dto.getId(),studentid);
            CommentEntity entity = CommentDTO.toEntity(dto);
            entity.setId(dto.getId());
            entity.setStudentid(studentid);
            List<CommentEntity> entities = commentService.commentdelete(entity);
            List<CommentDTO> dtos = entities.stream().map(CommentDTO::new).collect(Collectors.toList());
            ResponseDTO<CommentDTO> res= ResponseDTO.<CommentDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(res);
        }catch (Exception e) {
            String error = e.getMessage();
            log.error(error);
            ResponseDTO<CommentDTO> res = ResponseDTO.<CommentDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(res);
        }
    }

    private ResponseEntity<?> getResponseEntity(List<FeedEntity> entities) {
        List<FeedDTO> dtos = entities.stream().map(FeedDTO::new).collect(Collectors.toList());
        Collections.sort(dtos, Collections.reverseOrder());
        ResponseDTO<FeedDTO> res= ResponseDTO.<FeedDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(res);
    }

}
