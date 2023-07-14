package fashion.look_book.service;

import fashion.look_book.domain.Member;
import fashion.look_book.repository.MemberRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

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

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }


    public List<Member> MemberByRandom() {
        List<Member> allMember = memberRepository.findAll();

        Collections.shuffle(allMember);

        Member member1 = allMember.get(0);
        Member member2 = allMember.get(1);

        List<Member> twoMember = new ArrayList<>();
        twoMember.add(member1);
        twoMember.add(member2);

        return twoMember;
    }

}
