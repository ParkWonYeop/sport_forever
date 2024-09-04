package com.example.sport_forever.community;

import com.example.sport_forever.community.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Community", description = "커뮤니티 관련 기능")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;

    @Operation(
            operationId = "커뮤니티 글 쓰기",
            summary = "게시글을 씁니다.",
            description = "커뮤니티 게시글을 씁니다."
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/post")
    public void postCommunity(@RequestBody @Valid PostDto postDto) {
        communityService.addPost(postDto);
    }

    @Operation(
            operationId = "게시글 리스트",
            summary = "게시글 리스트를 가져옵니다.",
            description = "전체 게시글 리스트를 가져옵니다."
    )
    @GetMapping("/post/list")
    public List<PostListResponseDto> getCommunityList(@RequestParam Integer page) {
        return communityService.getPostList(page);
    }

    @Operation(
            operationId = "게시글 조회",
            summary = "게시글 정보를 가져옵니다.",
            description = "게시글을 조회합니다."
    )
    @GetMapping("/post")
    public PostResponseDto getCommunityList(@RequestParam Long postId) {
        return communityService.getPost(postId);
    }

    @Operation(
            operationId = "게시글 삭제",
            summary = "게시글을 삭제합니다.",
            description = "게시글 id를 받아 삭제합니다."
    )
    @DeleteMapping("/post")
    public void deleteCommunity(@RequestParam Long id) {
        communityService.deletePost(id);
    }

    @Operation(
            operationId = "댓글 추가",
            summary = "댓글을 추가합니다.",
            description = "게시글에 댓글을 추가합니다."
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/comment")
    public void postComment(@RequestBody @Valid CommentDto commentDto) {
        communityService.addComment(commentDto);
    }

    @Operation(
            operationId = "댓글 삭제",
            summary = "댓글을 삭제합니다.",
            description = "댓글을 삭제합니다."
    )
    @DeleteMapping("/comment")
    public void deleteComment(@RequestBody @Valid CommentDeleteDto commentDeleteDto) {
        communityService.deleteComment(commentDeleteDto);
    }
}
