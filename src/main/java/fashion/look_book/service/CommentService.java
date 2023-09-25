package fashion.look_book.service;

import fashion.look_book.domain.Comment;
import fashion.look_book.domain.Member;
import fashion.look_book.domain.Post;
import fashion.look_book.repository.CommentRepository;
import fashion.look_book.repository.MemberRepository;
import fashion.look_book.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long save(Comment comment) {
        commentRepository.save(comment);
        return comment.getId();
    }

    public Comment findOne(Long commentId) {
        return commentRepository.findOne(commentId);
    }

    public List<Comment> post_comment(Post post) {
        return commentRepository.findByPost(post);
    }

    @Transactional
    public Long updateComment(Long commentId, String content) {
        Comment findComment = commentRepository.findOne(commentId);

        findComment.update_comment(content);
        return findComment.getId();
    }

    @Transactional
    public void delete_Comment (Long commentId) {
        commentRepository.deleteComment(commentId);
    }
}
