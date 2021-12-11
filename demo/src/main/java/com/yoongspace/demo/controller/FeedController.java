package com.yoongspace.demo.controller;

import com.yoongspace.demo.DTO.FeedDTO;
import com.yoongspace.demo.DTO.ResponseDTO;
import com.yoongspace.demo.DTO.UserInfoDTO;
import com.yoongspace.demo.model.FeedEntity;
import com.yoongspace.demo.service.FeedService;
import com.yoongspace.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/yoongfeed")
public class FeedController {

    @Autowired
    private FeedService feedService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getFeed(@AuthenticationPrincipal String studentid){
        List<FeedEntity> entities = feedService.allfeed(studentid);
        List<FeedDTO> dtos = entities.stream().map(FeedDTO::new).collect(Collectors.toList());
        ResponseDTO<FeedDTO> res= ResponseDTO.<FeedDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(res);
    }


    @PostMapping("/write")
    public ResponseEntity<?> createFeed(@AuthenticationPrincipal String studentid, @RequestBody FeedDTO feedDTO){
        try{
            FeedEntity entity=FeedDTO.toEntity(feedDTO);
            entity.setId(null);
            entity.setStudentid(studentid);
            entity.setWritername(userService.getStudentname(studentid));
            entity.setWriterinform(userService.getStudentinfo(studentid));
            List<FeedEntity> entities = feedService.create(entity);
            List<FeedDTO> dtos = entities.stream().map(FeedDTO::new).collect(Collectors.toList());
            ResponseDTO<FeedDTO> res= ResponseDTO.<FeedDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(res);
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
            List<FeedDTO> dtos = entities.stream().map(FeedDTO::new).collect(Collectors.toList());
            ResponseDTO<FeedDTO> res= ResponseDTO.<FeedDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(res);
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
            List<FeedDTO> dtos = entities.stream().map(FeedDTO::new).collect(Collectors.toList());
            ResponseDTO<FeedDTO> res= ResponseDTO.<FeedDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(res);
        }catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<FeedDTO> res = ResponseDTO.<FeedDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("/currentuser") //새로운 컨트롤러로 분리해야하지만 임시로 피드에서 관리
    public ResponseEntity<?> connectuserinfo(@AuthenticationPrincipal String studentid){
        String username=userService.getStudentname(studentid);
        String userinfo=userService.getStudentinfo(studentid);
        UserInfoDTO userInfoDTO =new UserInfoDTO(username,userinfo);
        List<UserInfoDTO> dtos = new ArrayList<>();
        dtos.add(userInfoDTO);
        ResponseDTO<UserInfoDTO> res= ResponseDTO.<UserInfoDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(res);

    }

}
