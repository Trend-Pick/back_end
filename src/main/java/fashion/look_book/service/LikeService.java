package fashion.look_book.service;

import fashion.look_book.domain.Like;
import fashion.look_book.domain.LikeStatus;
import fashion.look_book.domain.Member;
import fashion.look_book.domain.Picture;
import fashion.look_book.repository.LikeRepository;
import fashion.look_book.repository.MemberRepository;
import fashion.look_book.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final PictureRepository pictureRepository;

    @Transactional
    public void saveLike(Like like) {
        likeRepository.save(like);
    }

    public Like findOne(Long likeId) {
        return likeRepository.findOne(likeId);
    }

    public List<Like> users_like(Long id) {
        Member findMember = memberRepository.findOne(id);
        return likeRepository.findByMember(findMember);
    }

    public void change_like(Long id, Long pictureId, Like like) {

        Member member = memberRepository.findOne(id);
        Picture picture = pictureRepository.findOne(pictureId);

        if(like.getStatus() == LikeStatus.LIKE) {
            like.update_like(member, picture, LikeStatus.DISLIKE);
        }

        else if(like.getStatus() == LikeStatus.DISLIKE) {
            like.update_like(member, picture, LikeStatus.LIKE);
        }
    }
    // 자동으로 실행될거같은데 한 번 확인해보기
}
