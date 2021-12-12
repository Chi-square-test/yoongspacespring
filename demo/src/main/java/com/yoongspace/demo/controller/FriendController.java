package com.yoongspace.demo.controller;

import com.yoongspace.demo.DTO.FeedDTO;
import com.yoongspace.demo.DTO.ReqFriendDTO;
import com.yoongspace.demo.DTO.ResponseDTO;
import com.yoongspace.demo.DTO.UserlistDTO;
import com.yoongspace.demo.service.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/yoongfriend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping
    public ResponseEntity<?> getuserlist(@AuthenticationPrincipal String studentid){
        List<UserlistDTO> userlistDTOList=friendService.getuserlist(studentid);
        ResponseDTO<UserlistDTO> res =ResponseDTO.<UserlistDTO>builder().data(userlistDTOList).build();

        return ResponseEntity.ok().body(res);
    }

    @PostMapping
    public ResponseEntity<?> addfriend(@AuthenticationPrincipal String studentid, @RequestBody ReqFriendDTO reqFriendDTO){
        try {
            friendService.addfriend(studentid, reqFriendDTO.getOtherid());
            List<ReqFriendDTO> resdata= new ArrayList<>(Arrays.asList(reqFriendDTO));
            ResponseDTO<ReqFriendDTO> res= ResponseDTO.<ReqFriendDTO>builder().data(resdata).build();
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<ReqFriendDTO> res = ResponseDTO.<ReqFriendDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(res);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deletefriend(@AuthenticationPrincipal String studentid, @RequestBody ReqFriendDTO reqFriendDTO){
        try {
            friendService.deletefriend(studentid, reqFriendDTO.getOtherid());
            List<ReqFriendDTO> resdata= new ArrayList<>(Arrays.asList(reqFriendDTO));
            ResponseDTO<ReqFriendDTO> res= ResponseDTO.<ReqFriendDTO>builder().data(resdata).build();
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<ReqFriendDTO> res = ResponseDTO.<ReqFriendDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(res);
        }
    }



}
