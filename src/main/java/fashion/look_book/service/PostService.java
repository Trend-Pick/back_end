package fashion.look_book.service;

import fashion.look_book.domain.*;
import fashion.look_book.repository.CommentRepository;
import fashion.look_book.repository.LikeRepository;
import fashion.look_book.repository.MemberRepository;
import fashion.look_book.repository.PostRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long savePost(Post post) {
        postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public Long updatePost(Long postId, String title, String content) {
        Post findPost = postRepository.findOne(postId);
        // findOne으로 찾아온 findItem은 영속상태이다.

        findPost.update_post(title, content);
        return findPost.getId();
    }

    public Post findOne(Long postId) {
        return postRepository.findOne(postId);
    }

    public List<Post> memberPost(Long id) {
        Member findMember = memberRepository.findOne(id);
        return postRepository.findByMember(findMember);
    } // member 한 사람의 post 목록들

    public List<Post> findAllPost() {
        return postRepository.findAllPost();
    }

    public Post add_comment (Post post, Comment comment) {

        post.getCommentList().add(comment);

        return post;
    }

    public Post delete_comment (Post post, Comment comment) {

        post.getCommentList().remove(comment);

        return post;
    }
}
