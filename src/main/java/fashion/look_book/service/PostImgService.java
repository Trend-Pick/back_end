package fashion.look_book.service;

import fashion.look_book.domain.*;
import fashion.look_book.repository.MemberRepository;
import fashion.look_book.repository.PictureRepository;
import fashion.look_book.repository.PostImgRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostImgService {
    private final PictureRepository pictureRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;
    private final PostImgRepository postImgRepository;
    private final S3FileService s3FileService;

    @Value("${cloud.aws.s3.bucket}") // .properties 의 itemImgLocation 값을 itemImgLocation 변수에 넣어
    private String imgLocation;

    @Transactional
    public void save(MultipartFile imgInPost, Post post) throws Exception{
        String oriImgName = imgInPost.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";
        Map<String, String> result = s3FileService.upload(imgInPost);
        String s3FileName = result.get("s3FileName");
        String s3Url = result.get("s3Url");
        imgName = s3FileName;
        imgUrl = s3Url;
        PostImg postImg = new PostImg(imgName, oriImgName, imgUrl, "Y", post);

        postImgRepository.save(postImg);
    }


    public List<PostImg> findAllPostImg(){
        return postImgRepository.findAllPostImg();
    }
   public PostImg findByPostId(Long postId){return postImgRepository.findOneByPostId(postId);}

    public PostImg findOne(Long postImgId) {
        return postImgRepository.findOne(postImgId);
    }

    public List<Picture> users_pictures (Long id) {
        Member findMember = memberRepository.findOne(id);
        return pictureRepository.findByMember(findMember);
    }

    @Transactional
    public void updatePostImg(Long postId, MultipartFile imgInPost)
            throws Exception {

        if (!imgInPost.isEmpty()) {
            PostImg postImg = postImgRepository.findOneByPostId(postId);

                if (!StringUtils.isEmpty(postImg.getImgName())) {
                    s3FileService.deleteImage(postImg.getImgName());
                }

                // 수정한 이미지 파일 저장
                String oriImgName = imgInPost.getOriginalFilename();
                Map<String, String> result = s3FileService.upload(imgInPost);
                String s3FileName = result.get("s3FileName");
                String s3Url = result.get("s3Url");
                String imgName = s3FileName;
                String imgUrl = s3Url;

                postImgRepository.postImgUpdate(postId, imgName, oriImgName, imgUrl);

            // 기존 이미지 파일 삭제

        }
        else{
            PostImg postImg = postImgRepository.findOneByPostId(postId);

            // 기존 이미지 파일 삭제 후 데베에서도 삭제
            if (!StringUtils.isEmpty(postImg.getImgName())) {
                s3FileService.deleteImage( postImg.getImgName());
                postImgRepository.postImgDelete(postId);
            }
        }
    }

    @Transactional
    public void deletePostImg(Long postId){
        postImgRepository.postImgDelete(postId);
        PostImg postImg = findOne(postId);
        s3FileService.deleteImage(postImg.getImgName());
    }
}
