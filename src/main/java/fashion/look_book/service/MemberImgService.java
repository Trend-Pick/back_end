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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberImgService {

    private final MemberImgRepository memberImgRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;

    @Value("${itemImgLocation}") // .properties 의 itemImgLocation 값을 itemImgLocation 변수에 넣어
    private String imgLocation;

    @Transactional
    public void saveImg(MultipartFile imgInMember, Member member) throws Exception{

        String memberImgName = imgInMember.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";
        imgName = fileService.uploadFiles(imgLocation, memberImgName, imgInMember.getBytes());
        imgUrl = imgLocation + "/" + imgName;
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

        String memberImgName = imgInMember.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";
        imgName = fileService.uploadFiles(imgLocation, memberImgName, imgInMember.getBytes());
        imgUrl = imgLocation + "/" + imgName;

        memberImg.update_memberImg(imgName, memberImgName, imgUrl);
    }

    @Transactional
    public void deleteImg(Long memberId) {
        memberImgRepository.postImgDelete(memberId);
    }
}