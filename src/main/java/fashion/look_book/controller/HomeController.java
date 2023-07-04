package fashion.look_book.controller;

import fashion.look_book.domain.Member;
import fashion.look_book.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @GetMapping("/home")
    public CreateMemberDto test() {

        Member member = new Member("hi", "1234", "abc", 24, true);

        Long id = memberService.join(member);

        Member findMember = memberService.findOne(id);

        return new CreateMemberDto(findMember);
    }


    @Data
    static class CreateMemberDto {

        private String id;
        private String password;
        private String nickname;
        private int age;
        private boolean sex;

        public CreateMemberDto(Member member) {
            this.id = member.getUser_user_id();
            this.password = member.getPassword();
            this.nickname = member.getNickname();
            this.age = member.getAge();
            this.sex = member.isSex(); // boolean 타입은 is로
        }
    }
}
