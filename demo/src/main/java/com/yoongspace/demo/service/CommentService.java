package com.yoongspace.demo.service;


import com.yoongspace.demo.model.CommentEntity;

import com.yoongspace.demo.model.FeedEntity;
import com.yoongspace.demo.persistence.CommentRepo;
import com.yoongspace.demo.persistence.FeedRepo;
import com.yoongspace.demo.persistence.FriendRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class CommentService {//id값 받아서 allfeed처럼)

    @Autowired
    private CommentRepo crepo;

    @Autowired
    private FriendRepo frepo;

    @Autowired
    private FeedRepo feedrepo;


    public List<CommentEntity> commentcreate(final CommentEntity entity){
        validate(entity,false);
        crepo.save(entity);
        log.info("댓글 작성.",entity.getStudentid());
        return feedscomment(entity.getStudentid(),entity.getFeedid());

    }

    public List<CommentEntity> feedscomment(final String studentid, final String feedid){
        final String[] feedowner = {""};
        final Boolean[] feedset = {false};
        final Optional<FeedEntity> original = feedrepo.findById(Long.parseLong(feedid));
        original.ifPresent(feed->{
            feedowner[0] =feed.getStudentid();
            feedset[0]=feed.isOnlyfriend();
        });

        if (feedset[0]&&!studentid.equals(feedowner[0])){ //친구공개이면서 자기글이 아닌경우
            if(frepo.existsByFriendAAndFriendB(studentid, feedowner[0])){
                return crepo.findByFeedid(feedid);
            }
            else {
                throw new RuntimeException("잘못된 접근입니다.");
            }
        }
        else{
            return crepo.findByFeedid(feedid);
        }
    }

    public List<CommentEntity> commentdelete(final CommentEntity entity){
        validate(entity,true);
        try{
            log.info("댓글 삭제 ",entity.getId());
            crepo.delete(entity);
        } catch (Exception e){
            log.error("error deleting entity"+entity.getId()+e);
            throw new RuntimeException("error deleting entity"+entity.getId());
        }
        log.info(entity.getStudentid()+" "+entity.getFeedid());
        return feedscomment(entity.getStudentid(),entity.getFeedid());
    }

    private void validate(final CommentEntity entity, final boolean del){
        if(entity==null){
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if(entity.getCommenttext()==null&&!del){
            log.warn("Null Text");
            throw new RuntimeException("댓글을 입력해 주세요");
        }
    }
    public void checkowner(final Long feedid,final String studentid){
        if(crepo.countByIdAndStudentid(feedid,studentid)==0){
            log.warn("NOT OWNER");
            throw new RuntimeException("작성자만 수정/삭제할 수 있습니다.");
        }
    }
}
