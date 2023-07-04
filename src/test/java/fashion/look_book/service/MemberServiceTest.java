package fashion.look_book.service;

import fashion.look_book.domain.Member;
import fashion.look_book.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void join() throws Exception {
        Member member = new Member("name", "1234", "hello", 24, true);

        Long savedId = memberService.join(member);

        assertEquals(member, memberRepository.findOne(savedId));
    }
}