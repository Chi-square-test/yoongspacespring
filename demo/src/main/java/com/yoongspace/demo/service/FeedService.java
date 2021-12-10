package com.yoongspace.demo.service;

import com.yoongspace.demo.model.FeedEntity;
import com.yoongspace.demo.model.FriendEntity;
import com.yoongspace.demo.persistence.FeedRepo;
import com.yoongspace.demo.persistence.FriendRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FeedService {


    @Autowired
    private FeedRepo repo;

    @Autowired
    private FriendRepo frepo;

    public List<FeedEntity> create(final FeedEntity entity){
        validate(entity,false);
        repo.save(entity);
        log.info("게시글 작성.",entity.getStudentid());
        return allfeed(entity.getStudentid());
    }

    public List<FeedEntity> allfeed(final String studentId){
        List<FeedEntity> all;
        List<FeedEntity> onlyf = new ArrayList<>();
        List<FriendEntity> friendEntities;
        all=repo.findByOnlyfriend(false);
        log.info(studentId);
        all.addAll(repo.findByOnlyfriendAndStudentid(true,studentId));
        if (!frepo.findByFriendA(studentId).isEmpty()){
            friendEntities = frepo.findByFriendA(studentId);
            for(FriendEntity fe:friendEntities){
                onlyf.addAll(repo.findByOnlyfriendAndStudentid(true,fe.getFriendB()));
            }
            all.addAll(onlyf);
        }
        return all;
    }

    public List<FeedEntity> update(final FeedEntity entity){
        validate(entity,false);
        final Optional<FeedEntity> original = repo.findById(entity.getId());
        original.ifPresent(feed->{
            feed.setFeedtext(entity.getFeedtext());
            feed.setOnlyfriend(entity.isOnlyfriend());
            repo.save(feed);
        });
        return allfeed(entity.getStudentid());
    }

    public List<FeedEntity> delete(final FeedEntity entity){
        validate(entity,true);
        try{
            repo.delete(entity);
        } catch (Exception e){
            log.error("error deleting entity",entity.getId(),e);
            throw new RuntimeException("error deleting entity"+entity.getId());
        }
        return allfeed(entity.getStudentid());
    }



    private void validate(final FeedEntity entity,final boolean del){
        if(entity==null){
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if(entity.getFeedtext()==null&&!del){
            log.warn("Null Text");
            throw new RuntimeException("본문을 입력해 주세요");
        }
    }
    public void checkowner(final Long feedid,final String studentid){
        if(repo.countByIdAndStudentid(feedid,studentid)==0){
            log.warn("NOT OWNER");
            throw new RuntimeException("작성자만 수정/삭제할 수 있습니다.");
        }
    }


}
