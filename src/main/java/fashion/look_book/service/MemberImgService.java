package fashion.look_book.service;

import fashion.look_book.domain.Member;
import fashion.look_book.domain.MemberImg;
import fashion.look_book.repository.MemberImgRepository;
import fashion.look_book.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberImgService {

    private final MemberImgRepository memberImgRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;
    private final S3FileService s3FileService;

    @Value("${cloud.aws.s3.bucket}") // .properties 의 itemImgLocation 값을 itemImgLocation 변수에 넣어
    private String imgLocation;

    @Transactional
    public void saveImg(Member member, MultipartFile imgInMember) throws Exception{

        String memberImgName = imgInMember.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";
        Map<String, String> result = s3FileService.upload(imgInMember);
        String s3FileName = result.get("s3FileName");
        String s3Url = result.get("s3Url");
        imgName = s3FileName;
        imgUrl = s3Url;
        MemberImg memberImg = new MemberImg(member, imgName, memberImgName, imgUrl);

        memberImgRepository.save(memberImg);
    }

    public MemberImg findOne(Long memberImgId) {
        return memberImgRepository.findOne(memberImgId);
    }

    public MemberImg findByMemberId(Long memberId) {
        return memberImgRepository.findByMemberId(memberId);
    }

    @Transactional
    public void updateImg(Long memberImgId, MultipartFile imgInMember) throws Exception {
        MemberImg memberImg = findOne(memberImgId);

        s3FileService.deleteImage(memberImg.getImgName());

        String memberImgName = imgInMember.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";
        Map<String, String> result = s3FileService.upload(imgInMember);
        String s3FileName = result.get("s3FileName");
        String s3Url = result.get("s3Url");
        imgName = s3FileName;
        imgUrl = s3Url;

        memberImg.update_memberImg(imgName, memberImgName, imgUrl);
    }
}
