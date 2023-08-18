package fashion.look_book.controller;

import fashion.look_book.Dto.Board.*;
import fashion.look_book.domain.*;
import fashion.look_book.login.SessionConst;
import fashion.look_book.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://3.35.99.247:3000", maxAge = 3600)
@RestController
@RequiredArgsConstructor
public class BoardController {

    // 게시글이라는 베너를 클릭했을 때
    // 1. 최근 게시글들이 시간 순으로 나온다. (제목)
    // 오른쪽 위에 글쓰기 버튼이 있다.
    // 2. 글쓰기를 클릭하면 글쓰는 페이지로 넘어간다.
    // 저장을 하면 다시 (1번)으로 돌아간다.
    // 3. 게시판 목록에서 게시글을 클릭하면 내용을 보여준다. 만약 자기가 쓴 글이라면 수정, 삭제 버튼도 보여준다.

    @Value("${cloud.aws.s3.bucket}") // .properties 의 itemImgLocation 값을 itemImgLocation 변수에 넣어
    private String imgLocation;

    private final PostService postService;
    private final CommentService commentService;
    private final FileService fileService;
    private final PictureService pictureService;
    private final PostImgService postImgService;
    private final HttpSession session;
    private final S3FileService s3FileService;

    @GetMapping("/post_list")
    public List<PostDtoTitle> postList() {
        List<Post> findPosts = postService.findAllPost();

        List<PostDtoTitle> postLists = findPosts.stream()
                .map(c -> {
                    String imgUrl = Optional.ofNullable(c.getPostImg())
                            .map(PostImg::getImgUrl)
                            .orElse(null);
                    try {
                        return new PostDtoTitle(c.getTitle(), c.getContent(), c.getPostTime(), imgUrl, c.getId(), c.getPost_member().getNickname());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect((Collectors.toList()));

        return postLists;
    }


    @GetMapping("/create_post") // 글쓰기 페이지로 넘어가는 것
    public void createPost() {
        // 이거도 세션 만들어야지 넘어가는 걸로
    }

    @PostMapping("/create_post") // 글쓰기 페이지에서 저장을 누르는거
    public CreatePostResponse savePost(@RequestPart (value="createPostRequest") CreatePostRequest createPostRequest,
                                       @RequestPart (value="imgInPost", required = false) MultipartFile imgInPost) throws Exception{

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Post post = new Post(member, createPostRequest.getTitle(), createPostRequest.getContent(), LocalDateTime.now());
        Long postId = postService.savePost(post);

        if (imgInPost != null) {
            postImgService.save(imgInPost, post);
        }

        return new CreatePostResponse(postId);
    }

    @GetMapping("/post/{postId}") // 3번의 게시글 하나 클릭해서 들어가는 것
    public PostWithCommentDto post (@PathVariable ("postId") Long postId) {

        Post post = postService.findOne(postId);
        PostImg postImg = postImgService    .findByPostId(postId);
        List<Comment> commentList = commentService.post_comment(postId);

        List<CommentDtoContent> commentDtoContents = commentList.stream()
                .map(c -> {
                    String imgUrl = Optional.ofNullable(c.getComment_member().getMemberImg())
                            .map(MemberImg::getImgUrl)
                            .orElse(null);
                    try {
                        return new CommentDtoContent(c.getContent(), imgUrl, c.getId(), c.getComment_member().getNickname(), c.getCommentTime());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());

        if (postImg == null) {
            return new PostWithCommentDto(post, post.getPost_member().getNickname(), post.getPost_member().getId(), null, commentDtoContents);
        }

        return new PostWithCommentDto(post, post.getPost_member().getNickname(), post.getPost_member().getId(), postImg.getImgUrl(), commentDtoContents);
        // 자기 게시글이면 수정, 삭제 버튼 보이게
        // 프론트분들이랑 상의하기
    }

    @PutMapping("/update_post/{postId}")
    public UpdatePostResponse updatePost(@PathVariable ("postId") Long postId,
                                         @RequestPart (value="createPostRequest") CreatePostRequest createPostRequest,
                                         @RequestPart (value="imgInPost", required = false) MultipartFile imgInPost)
            throws Exception{

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Post post = postService.findOne(postId);


        if(post.getPost_member().getId() == member.getId()) {
            postService.updatePost(postId, createPostRequest.getTitle(), createPostRequest.getContent());

            if(imgInPost!=null) { //원본에는 이미지가 없었는데 수정했을 때는 이미지가 추가된 경우
                if(postImgService.findByPostId(postId)==null){
                    postImgService.save(imgInPost, post);
                    post.update_postTime(LocalDateTime.now());
                }
                else{ //원본에도 이미지가 있었는데 수정 후 추가된 경우
                    postImgService.updatePostImg(postId, imgInPost);
                    post.update_postTime(LocalDateTime.now());
                }
            } else{ //원본에는 이미지가 있었는데 수정했을 때는 이미지가 없다면 원래 있던 postImg 삭제 필요
                if(postImgService.findByPostId(postId)!=null)
                    postImgService.deletePostImg(postId);
                post.update_postTime(LocalDateTime.now());
            }
        }
        else {
            return null;
        }

        return new UpdatePostResponse(postId);
    }

    @DeleteMapping("/delete_post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable ("postId") Long postId) {

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Post post = postService.findOne(postId);

        if(post.getPost_member().getId() == member.getId()) {

            postService.delete_Post(postId);
        }
        else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }



    // 여기서부터 댓글


    // 댓글 만들기
    @PostMapping("/create/{postId}/comment")
    public ResponseEntity<?> saveComment(@PathVariable ("postId") Long postId,
                                         @RequestBody CreateCommentRequest request) {

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Post post = postService.findOne(postId);

        if(post != null) {
            Comment comment = new Comment(member, request.getContent(), post, LocalDateTime.now());
            commentService.save(comment);
        }
        else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }


    // 댓글 수정하기
    @PutMapping("/update_comment/{postId}/{CommentId}")
    public ResponseEntity<?> updatePost(@PathVariable ("postId") Long postId,
                                            @PathVariable ("CommentId") Long commentId,
                                            @RequestBody UpdateCommentRequest request) {

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Comment comment = commentService.findOne(commentId);

        if(comment.getComment_member().getId() == member.getId()) {
            commentService.updateComment(commentId, request.getContent());
            comment.update_commentTime(LocalDateTime.now());
        }
        else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        comment.update_commentTime(LocalDateTime.now());

        return new ResponseEntity(HttpStatus.OK);
    }


    // 댓글 삭제하기
    @DeleteMapping("/delete_comment/{commentId}")
    public String deleteComment(@PathVariable ("commentId") Long commentId) {

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Comment comment = commentService.findOne(commentId);

        if(comment.getComment_member().getId() == member.getId()) {
            commentService.delete_Comment(commentId);
        }
        else {
            return null;
        }

        return "ok";
    }
}
