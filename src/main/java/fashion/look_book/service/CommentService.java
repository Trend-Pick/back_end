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

    @Transactional
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    public Comment findOne(Long commentId) {
        return commentRepository.findOne(commentId);
    }

    public List<Comment> users_comment(Long id) {
        Member findMember = memberRepository.findOne(id);
        return commentRepository.findByMember(findMember);
    }
}
