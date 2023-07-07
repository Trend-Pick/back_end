package fashion.look_book.service;

import fashion.look_book.domain.Like;
import fashion.look_book.domain.LikeStatus;
import fashion.look_book.domain.Member;
import fashion.look_book.domain.Picture;
import fashion.look_book.repository.MemberRepository;
import fashion.look_book.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PictureService {

    private final PictureRepository pictureRepository;
    private final MemberRepository memberRepository;


    @Value("${itemImgLocation}") // .properties 의 itemImgLocation 값을 itemImgLocation 변수에 넣어
    private String imgLocation;

    @Transactional
    public void save(Picture picture) throws Exception{


        pictureRepository.save(picture);

    }

    public Picture findOne(Long pictureId) {
        return pictureRepository.findOne(pictureId);
    }

    public List<Picture> users_post(Long id) {
        Member findMember = memberRepository.findOne(id);
        return pictureRepository.findByMember(findMember);
    }

    // 이 사진의 like 갯수
    
}
