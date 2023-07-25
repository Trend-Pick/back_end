package fashion.look_book.service;

import fashion.look_book.domain.Member;
import fashion.look_book.repository.MemberRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Value("${itemImgLocation}") // .properties 의 itemImgLocation 값을 itemImgLocation 변수에 넣어
    private String imgLocation;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        validateDuplicateMemberByNickname(member);
        memberRepository.save(member);
        return member.getId();
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

    public void validateDuplicateMemberUserId (String userId) {
        List<Member> findMember = memberRepository.findById(userId);
        if(!findMember.isEmpty()) {
            System.out.println("중복");
            // 예외처리하기
        }
        else {
            System.out.println("중복 X");
            // 예외처리하기
        }
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
