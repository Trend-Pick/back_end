package fashion.look_book.service;

import fashion.look_book.domain.Member;
import fashion.look_book.domain.Picture;
import fashion.look_book.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PictureService {

    private final PictureRepository pictureRepository;
    private final LikeService likeService;
    private final FileService fileService;
    private final S3FileService s3FileService;

    @Value("${cloud.aws.s3.bucket}") // .properties 의 itemImgLocation 값을 itemImgLocation 변수에 넣어
    private String imgLocation;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public void save(Picture picture) throws Exception{

        pictureRepository.save(picture);
    }

    //삭제하려고 할 때 pictureId를 받아서 그 pictureId에 연결된 img를 삭제하는 식으로.
    @Transactional
    public void delete(Picture picture) throws Exception {
        pictureRepository.delete(picture.getId());
        s3FileService.deleteImage(picture.getImgName());
    }

    public Picture findOne(Long pictureId) {
        return pictureRepository.findOne(pictureId);
    }


    public Picture PictureByRandom(Member member) {
        List<Picture> pictures = pictureRepository.CanLikePicture(member);

        Collections.shuffle(pictures);

        Picture picture = pictures.get(0);

        return picture;
    }

    public List<Long> RankingOfPicture() {
        List<Picture> pictures = pictureRepository.findAll();

        int size = pictures.size();

        Map<Long, Long> likeMap = new HashMap<>();

        for (int i = 0; i < size; i++) {
            Long like = likeService.LikeNumber(pictures.get(i).getId());
            likeMap.put(pictures.get(i).getId(), like);
        }

        List<Long> keySet = new ArrayList<>(likeMap.keySet());

        keySet.sort((o1, o2) -> likeMap.get(o2).compareTo(likeMap.get(o1)));

        return keySet;
    }


    public List<Picture> MyPagePicture(Long memberId) {
        List<Picture> pictures = pictureRepository.MyPagePicture(memberId);
        return pictures;
    }
}
