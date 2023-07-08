package fashion.look_book.controller;

import fashion.look_book.Dto.LoginDtos.*;
import fashion.look_book.domain.Member;
import fashion.look_book.login.LoginService;
import fashion.look_book.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final LoginService loginService;

    @GetMapping("/")
    public String homeLogin (@SessionAttribute(name = SessionConst.LOGIN_MEMBER,
            required = false) Member loginMember, Model model) {
        // 세션을 생성하지 않기 때문에 가져올때만 @SessionAttribute 사용

        if (loginMember == null) {
            return null; // 처음 페이지로 리다이렉트
        }

        // 세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "aaa"; // 로그인 해서 다음 페이지로 이동
    }



    /**
        회원가입
     **/


    @GetMapping("/member/add")
    public String addMemberDto() {
        return null;
    }





    @PostMapping("/member/add")
    public addMemberDtoResponse saveMember(@RequestBody addMemberDtoRequest request) {
        Member member = new Member(request.getUser_user_id(), request.getPassword(), request.getNickname(),
                                    request.getAge(), request.isSex());

        Long id = memberService.join(member);

        return new addMemberDtoResponse(id);
    }



    /**
         로그인, 로그아웃
     **/

    @PostMapping("/login")
    public LoginDtoResponse login (@Valid LoginDtoRequest request, BindingResult bindingResult,
                                   HttpServletRequest httpServletRequest) {
        if(bindingResult.hasErrors()) {
            return null; // 로그인 페이지로 리다이렉트
        }
        Member loginMember = loginService.login(request.getUser_user_id(), request.getPassword());

        if(loginMember == null) {
            bindingResult.reject("loginFail");
            return null; // 여기도 로그인 실패로 리다이렉트
        }

        HttpSession session = httpServletRequest.getSession();
        // request.getSession() -> 세션이 있으면 세션 반환, 없으면 신규 세션을 생성
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        // 세션에 로그인 회원 정보를 보관

        return new LoginDtoResponse(loginMember.getId());
    }

    @PostMapping("/logout")
    public String logout (HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/"; // 홈 페이지로 리다이렉트
    }
}