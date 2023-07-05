package fashion.look_book.controller;

import fashion.look_book.domain.Member;
import fashion.look_book.domain.Post;
import fashion.look_book.service.MemberService;
import fashion.look_book.service.PostService;
import lombok.*;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
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

    @GetMapping("/post_list")
    public List<PostDto> postList() {
        List<Post> findPosts = postService.findAllPost();

        List<PostDto> postLists = findPosts.stream()
                .map(p -> new PostDto(p))
                .collect(Collectors.toList());

        return postLists;
    } // post의 제목들을 넘겨줘야함
    //////////////////////////// result로 감싸기

    /*
    @GetMapping("/create_post") // 글쓰기 페이지로 넘어가는 것
    public Post createPost() {

    }
     */

    @PostMapping("/create_post") // 글쓰기 페이지에서 저장을 누르는거
    public CreateMemberResponse savePost(@RequestBody CreateMemberRequest request) {

        // Member post_member = new Member("hi", "1234", "abc", 24, true);
        // 원래는 세션에서 member의 정보를 가져와야 함
        Member post_member = memberService.findOne(1L);

        Post post = new Post(post_member, request.title, request.content);
        Long id = postService.savePost(post);

        return new CreateMemberResponse(id);
    }


    @GetMapping("/post/{postId}") // 3번의 게시글 하나 클릭해서 들어가는 것
    public PostDto createPost(@PathVariable ("postId") Long postId) {
        // 여기서 모델같은 데에서 데이터를 받아와야 한다.
        Post post = postService.findOne(postId);

        String title = post.getTitle();
        String content = post.getContent();

        return new PostDto(post);
    } // 테스트해보고 수정 필요 (title, content 따로 받아와야 할거같은데)


////////////////////// 여기도 수정 필요
    @PostMapping("/update_post/{postId}")
    public UpdateMemberResponse updatePost(@PathVariable ("postId") Long postId,
                           @RequestBody UpdateMemberRequest request) {

        postService.updatePost(postId, request.title, request.content);

        return new UpdateMemberResponse(postId);
    } // update 넘어가는 메서드


    // 삭제


    @Data
    static class PostDto {
        private String title;
        private String content;

        public PostDto(Post post) {
            this.title = post.getTitle();
            this.content = post.getContent();
        }

    }

    @Data
    static class CreatePost {
        private Long id;

        public CreatePost(Long id) {
            this.id = id;
        }
    }

    @Data
    static class CreateMemberRequest {
        private String title;
        private String content;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }


    @Data
    static class UpdateMemberRequest {
        private String title;
        private String content;
    }

    @Data
    static class UpdateMemberResponse {
        private Long id;

        public UpdateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
