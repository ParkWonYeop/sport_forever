package com.example.sport_forever.community;

import com.example.sport_forever.common.controller.SportException;
import com.example.sport_forever.common.controller.constant.CommunalResponse;
import com.example.sport_forever.common.entity.CommentEntity;
import com.example.sport_forever.common.entity.PostEntity;
import com.example.sport_forever.common.entity.UserEntity;
import com.example.sport_forever.common.repository.CommentRepository;
import com.example.sport_forever.common.repository.PostRepository;
import com.example.sport_forever.common.repository.UserRepository;
import com.example.sport_forever.community.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.sport_forever.common.utils.SecurityUtil.getCurrentMemberId;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommunityService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void addPost(PostDto postDto) {
        Optional<UserEntity> optionalUserEntity = userRepository.findUserEntityByPhoneNumber(getCurrentMemberId());
        if (optionalUserEntity.isEmpty()) {
            throw new SportException(CommunalResponse.USER_NOT_FOUND);
        }

        PostEntity postEntity = new PostEntity(optionalUserEntity.get(), postDto);

        postRepository.save(postEntity);
    }

    @Transactional(readOnly = true)
    public List<PostListResponseDto> getPostList(Integer page) {
        List<PostEntity> postEntities = postRepository.findAll(PageRequest.of(20, page)).getContent();
        List<PostListResponseDto> postListResponseDtoList = new ArrayList<>();

        for (PostEntity postEntity : postEntities) {
            PostListResponseDto postListResponseDto = new PostListResponseDto(
                    postEntity.getPostId(),
                    postEntity.getUser().getName(),
                    postEntity.getTitle(),
                    postEntity.getCreateAt()
            );

            postListResponseDtoList.add(postListResponseDto);
        }

        return postListResponseDtoList;
    }

    @Transactional
    public void deletePost(Long postId) {
        Optional<UserEntity> optionalUserEntity = userRepository.findUserEntityByPhoneNumber(getCurrentMemberId());
        if (optionalUserEntity.isEmpty()) {
            throw new SportException(CommunalResponse.USER_NOT_FOUND);
        }

        Optional<PostEntity> optionalPostEntity = postRepository.findByPostId(postId);

        if (optionalPostEntity.isEmpty()) {
            throw new SportException(CommunalResponse.POST_NOT_FOUND);
        }

        PostEntity postEntity = optionalPostEntity.get();

        if(!postEntity.getUser().equals(optionalUserEntity.get())){
            throw new SportException(CommunalResponse.USER_NOT_CORRECT);
        }

        List<CommentEntity> commentEntityList = commentRepository.findByPost(postEntity);

        commentRepository.deleteAll(commentEntityList);
        postRepository.delete(postEntity);
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long postId) {
        Optional<PostEntity> optionalPostEntity = postRepository.findByPostId(postId);

        if (optionalPostEntity.isEmpty()) {
            throw new SportException(CommunalResponse.POST_NOT_FOUND);
        }

        PostEntity postEntity = optionalPostEntity.get();

        List<CommentEntity> commentEntityList = commentRepository.findByPost(postEntity);

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (CommentEntity commentEntity : commentEntityList) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(
                    commentEntity.getCommentId(),
                    commentEntity.getUser().getName(),
                    commentEntity.getContent(),
                    commentEntity.getCreateAt()
            );

            commentResponseDtoList.add(commentResponseDto);
        }

        return new PostResponseDto(
                postEntity.getPostId(),
                postEntity.getUser().getName(),
                postEntity.getTitle(),
                postEntity.getSubject(),
                commentResponseDtoList,
                postEntity.getCreateAt()
        );
    }

    @Transactional
    public void addComment(CommentDto commentDto) {
        Optional<UserEntity> optionalUserEntity = userRepository.findUserEntityByPhoneNumber(getCurrentMemberId());

        if (optionalUserEntity.isEmpty()) {
            throw new SportException(CommunalResponse.USER_NOT_FOUND);
        }

        UserEntity userEntity = optionalUserEntity.get();

        Optional<PostEntity> optionalPostEntity = postRepository.findByPostId(commentDto.postId());

        if(optionalPostEntity.isEmpty()){
            throw new SportException(CommunalResponse.POST_NOT_FOUND);
        }

        PostEntity postEntity = optionalPostEntity.get();

        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setUser(userEntity);
        commentEntity.setPost(postEntity);
        commentEntity.setContent(commentDto.content());

        commentRepository.save(commentEntity);
    }

    @Transactional
    public void deleteComment(CommentDeleteDto commentDeleteDto) {
        Optional<UserEntity> optionalUserEntity = userRepository.findUserEntityByPhoneNumber(getCurrentMemberId());

        if (optionalUserEntity.isEmpty()) {
            throw new SportException(CommunalResponse.USER_NOT_FOUND);
        }

        UserEntity userEntity = optionalUserEntity.get();

        Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(commentDeleteDto.commentId());

        if(optionalCommentEntity.isEmpty()){
            throw new SportException(CommunalResponse.COMMENT_NOT_FOUND);
        }

        CommentEntity commentEntity = optionalCommentEntity.get();

        if(commentEntity.getUser().equals(userEntity)){
            throw new SportException(CommunalResponse.USER_NOT_CORRECT);
        }

        commentRepository.delete(commentEntity);
    }
}
