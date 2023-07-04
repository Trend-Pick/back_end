package fashion.look_book.service;

import fashion.look_book.domain.*;
import fashion.look_book.repository.CommentRepository;
import fashion.look_book.repository.LikeRepository;
import fashion.look_book.repository.MemberRepository;
import fashion.look_book.repository.PostRepository;
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
    public void savePost(Post post) {
        postRepository.save(post);
    }

    public Post findOne(Long postId) {
        return postRepository.findOne(postId);
    }

    public List<Post> users_like(Long id) {
        Member findMember = memberRepository.findOne(id);
        return postRepository.findByMember(findMember);
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
