package fashion.look_book.service;

import fashion.look_book.domain.Comment;
import fashion.look_book.domain.Member;
import fashion.look_book.domain.Post;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired EntityManager em;
    @Autowired PostService postService;

    @Test
    public void create_post() throws Exception {
        Member member = createMember();

        Post post = createPost(member);

        Comment comment1 = new Comment(member, "comment1", post);
        Comment comment2 = new Comment(member, "comment1", post);
        em.persist(comment1);
        em.persist(comment2);

        postService.add_comment(post, comment1);
        postService.add_comment(post, comment2);
        //댓글 생성 및 게시글에 추가

        System.out.println("========================================");
        System.out.println(post.getCommentList());

        postService.delete_comment(post, comment2);
        System.out.println("========================================");
        System.out.println(post.getCommentList());
    }



    private Post createPost(Member member) {
        Post post = new Post(member, "main", "content");
        em.persist(post);
        // 게시글 생성
        return post;
    }

    private Member createMember() {
        Member member = new Member("name", "1234", "hello", 24, true);
        em.persist(member);
        return member;
    }
}