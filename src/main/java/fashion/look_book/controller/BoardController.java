package fashion.look_book.controller;

import fashion.look_book.Dto.Board.*;
import fashion.look_book.domain.Comment;
import fashion.look_book.domain.Member;
import fashion.look_book.domain.Post;
import fashion.look_book.service.CommentService;
import fashion.look_book.service.MemberService;
import fashion.look_book.service.PostService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BoardController {

    // 게시글이라는 베너를 클릭했을 때
    // 1. 최근 게시글들이 시간 순으로 나온다. (제목)
    // 오른쪽 위에 글쓰기 버튼이 있다.
    // 2. 글쓰기를 클릭하면 글쓰는 페이지로 넘어간다.
    // 저장을 하면 다시 (1번)으로 돌아간다.
    // 3. 게시판 목록에서 게시글을 클릭하면 내용을 보여준다. 만약 자기가 쓴 글이라면 수정, 삭제 버튼도 보여준다.

    private final PostService postService;
    private final MemberService memberService;
    private final CommentService commentService;

    @GetMapping("/post_list")
    public List<PostDtoTitle> postList() {
        List<Post> findPosts = postService.findAllPost();

        List<PostDtoTitle> postLists = findPosts.stream()
                .map(p -> new PostDtoTitle(p.getTitle()))
                .collect(Collectors.toList());

        return postLists;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class PostDtoTitle {
        private String title;
    }

    /*
    @GetMapping("/create_post") // 글쓰기 페이지로 넘어가는 것
    public Post createPost() {
        // 이거도 세션 만들어야지 넘어가는 걸로
    }
     */

    @PostMapping("/create_post") // 글쓰기 페이지에서 저장을 누르는거
    public CreatePostResponse savePost(@RequestBody CreatePostRequest request) {

        // Member post_member = new Member("hi", "1234", "abc", 24, true);
        // 원래는 세션에서 member의 정보를 가져와야 함
        Member post_member = memberService.findOne(1L);

        Post post = new Post(post_member, request.getTitle(), request.getContent());
        Long id = postService.savePost(post);

        return new CreatePostResponse(id);
    }


    @GetMapping("/post/{postId}") // 3번의 게시글 하나 클릭해서 들어가는 것
    public PostWithCommentDto createPost(@PathVariable ("postId") Long postId) {

        Post post = postService.findOne(postId);

        List<Comment> commentList = commentService.post_comment(postId);

        List<CommentDtoContent> commentDtoContents = commentList.stream()
                .map(c -> new CommentDtoContent(c.getContent()))
                .collect((Collectors.toList()));

        return new PostWithCommentDto(post, commentDtoContents);
    }

    @PutMapping("/update_post/{postId}")
    public UpdatePostResponse updatePost(@PathVariable ("postId") Long postId,
                                           @RequestBody UpdatePostRequest request) {

        postService.updatePost(postId, request.getTitle(), request.getContent());

        return new UpdatePostResponse(postId);
    }

    @DeleteMapping("/delete_post/{postId}")
    public String deletePost(@PathVariable ("postId") Long postId) {
        postService.delete_Post(postId);
        return "ok";
    }



    // 여기서부터 댓글

    // 세션 만들어지면 GetMapping


    // 댓글 만들기
    @PostMapping("/create/{postId}/comment")
    public CreateCommentResponse saveComment(@PathVariable ("postId") Long postId,
                                            @RequestBody CreateCommentRequest request) {

        // Member post_member = new Member("hi", "1234", "abc", 24, true);
        // 원래는 세션에서 member의 정보를 가져와야 함
        Member post_member = memberService.findOne(1L);
        Post post = postService.findOne(postId);

        Comment comment = new Comment(post_member, request.getContent(), post);
        Long id = commentService.save(comment);

        return new CreateCommentResponse(id);
    }


    // 댓글 수정하기
    @PutMapping("/update_post/{postId}/{CommentId}")
    public UpdateCommentResponse updatePost(@PathVariable ("postId") Long postId,
                                            @PathVariable ("CommentId") Long commentId,
                                            @RequestBody UpdateCommentRequest request) {

        commentService.updateComment(commentId, request.getContent());

        return new UpdateCommentResponse(commentId);
    }


    // 댓글 삭제하기
    @DeleteMapping("/delete_comment/{commentId}")
    public String deleteComment(@PathVariable ("commentId") Long commentId) {
        commentService.delete_Comment(commentId);
        return "ok";
    }
}
