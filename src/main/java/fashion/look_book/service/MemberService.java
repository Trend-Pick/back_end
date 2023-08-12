package fashion.look_book.service;

import fashion.look_book.domain.Member;
import fashion.look_book.domain.Post;
import fashion.look_book.password.TempKey;
import fashion.look_book.repository.MemberRepository;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;
    private final MemberImgService memberImgService;
    private String ePw;
    private String findPwCode;
    private Member member;

    @Value("${cloud.aws.s3.bucket}") // .properties 의 itemImgLocation 값을 itemImgLocation 변수에 넣어
    private String imgLocation;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        validateDuplicateMemberByNickname(member);
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public boolean verifyJoinEmail(String inputKey){
        if(inputKey.equals(this.ePw)){
            Member member1 = new Member(this.member.getUser_user_id(), this.member.getEmail(),
                                        this.member.getPassword(), this.member.getNickname());
            memberRepository.save(member1);
            return true;
        }
        else{
            return false;
        }
    } // 나중에 회원가입할 때 이메일 인증 필요할 때 사용할 메서드

    @Transactional
    public void findPassword(String email) throws Exception {
        List<Member> findMember = memberRepository.findByEmail(email);
        if(findMember.isEmpty()) {
            throw new IllegalStateException();
        } //저장된 email이 없으면 예외발생
        Member member = memberRepository.findOneByEmail(email);
        MimeMessage message = javaMailSender.createMimeMessage();
        String memberKey = new TempKey().getKey(6,false);
        String memberPw = BCrypt.hashpw(memberKey,BCrypt.gensalt()).substring(0,12);

        memberRepository.updatePassword(email, memberPw);

        message.addRecipients(Message.RecipientType.TO,email);
        message.setSubject("Trend Pick 임시 비밀번호 발급.");
        // this.findPwCode = createKey();
        String body = "<div>"
                + "<h3> 임시비밀번호 발급</h3>"
                + "<br>"+member.getUser_user_id()+"님"
                + "<p>비밀번호 찾기를 통한 임시 비밀번호입니다.<p>"
                + "<br>임시비밀번호 : <h3>"+memberPw+"</h2>"
                +"<br>로그인 후 비밀번호 변경을 해주세요.";

        message.setText(body,"utf-8","html");

        message.setFrom(new InternetAddress("trend132@naver.com","Trend-Pick admin"));
        javaMailSender.send(message);
    }

    private void validateDuplicateMember (Member member) {
        List<Member> findMember = memberRepository.findById(member.getUser_user_id());
        if(!findMember.isEmpty()) {
            throw new IllegalStateException();
        }
    }

    private void validateDuplicateMemberByNickname (Member member) {
        List<Member> findMember = memberRepository.findById(member.getNickname());
        if(!findMember.isEmpty()) {
            throw new IllegalStateException();
        }
    }

    public boolean validateDuplicateMemberUserId (String userId) {
        List<Member> findMember = memberRepository.findById(userId);
        if (!findMember.isEmpty()) {
            System.out.println("중복");
            return true;
            // 예외처리하기
        } else {
            System.out.println(userId);
            System.out.println("중복 X");
            // 예외처리하기
            return false;
        }
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public Long updateMember(Long memberId, String password) {
        Member findMember = memberRepository.findOne(memberId);
        findMember.update_password(password);

        return findMember.getId();
    }
}
