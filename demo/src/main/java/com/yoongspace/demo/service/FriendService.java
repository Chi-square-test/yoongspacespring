package com.yoongspace.demo.service;

import com.yoongspace.demo.DTO.FeedDTO;
import com.yoongspace.demo.DTO.UserlistDTO;
import com.yoongspace.demo.model.FriendEntity;
import com.yoongspace.demo.model.UserEntity;
import com.yoongspace.demo.persistence.FriendRepo;
import com.yoongspace.demo.persistence.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FriendService {

    @Autowired
    private FriendRepo friendRepo;

    @Autowired
    private UserRepo userRepo;

    public void addfriend(final String studentid, final String otherid){
        if (friendRepo.existsByFriendAAndFriendB(studentid, otherid)) {
            log.warn("이미 친구 관계입니다.");
            throw new RuntimeException("이미 친구 관계입니다.");
        }else{
            FriendEntity friendEntity1 = new FriendEntity(null,studentid,otherid);
            FriendEntity friendEntity2 = new FriendEntity(null,otherid,studentid);
            friendRepo.save(friendEntity1);
            friendRepo.save(friendEntity2);
        }
    }

    public void deletefriend(final String studentid, final String otherid){
        if (!friendRepo.existsByFriendAAndFriendB(studentid, otherid)) {
            log.warn("이미 친구관계가 아닙니다.");
            throw new RuntimeException("이미 친구 관계가 아닙니다.");
        }else{
            FriendEntity friendEntity1 = friendRepo.findByFriendAAndFriendB(studentid,otherid);
            FriendEntity friendEntity2 = friendRepo.findByFriendAAndFriendB(otherid,studentid);
            friendRepo.delete(friendEntity1);
            friendRepo.delete(friendEntity2);
        }
    }

    public List<UserlistDTO> getuserlist(final String studentid){
        List<FriendEntity> friendEntities = friendRepo.findByFriendA(studentid);
        List<UserEntity> entities =userRepo.findByStudentidNot(studentid);
        List<UserlistDTO> userlistDTOS =entities.stream().map(UserlistDTO::new).collect(Collectors.toList());

        if (!friendEntities.isEmpty()){
            List<String> friendlist = new ArrayList<>();
            for (FriendEntity fe:friendEntities){
                friendlist.add(fe.getFriendB());
            }
            for (UserlistDTO uld : userlistDTOS){
                if (friendlist.contains(uld.getStudentid())){
                    uld.setMyfriend(true);
                }
            }
            return userlistDTOS;
        }else{
            log.info("친구가 없");
            return userlistDTOS;
        }

    }


}
