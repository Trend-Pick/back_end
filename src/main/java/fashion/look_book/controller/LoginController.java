package fashion.look_book.controller;

import fashion.look_book.Dto.LoginDtos.*;
import fashion.look_book.domain.Member;
import fashion.look_book.login.Login;
import fashion.look_book.login.LoginService;
import fashion.look_book.login.SessionConst;
import fashion.look_book.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final LoginService loginService;


    /////////////////// 여기 잘못됨
    @GetMapping("/")
    public String homeLogin (@Login LoginDtoRequest loginMember) {

        // 세션에 회원 데이터가 없으면 home (로그인 또는 회원가입 하는 페이지로 이동)
        if (loginMember == null) {
            return "ok";
        }

        // 세션이 유지되면 로그인으로 이동(사진 고르는 기능 페이지로 이동)
        return "ok";
    }



    /**
        회원가입
     **/

    @GetMapping("/member/add")
    public String addMember() {
        return null;
    }

    
    @PostMapping("/member/add")
    public ResponseEntity<?> saveMember(@RequestBody @Valid addMemberDtoRequest request, Errors errors) {
        if(errors.hasErrors()) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        Member member = new Member(request.getUser_user_id(), request.getEmail(), request.getPassword(), request.getNickname());

        Long id = memberService.join(member);

        return new ResponseEntity(HttpStatus.OK);
    }

    // 중복확인 버튼
    // 새로운 페이지가 아니라 아이디만 Post매핑으로 보내서 검증하는 식으로
    @PostMapping("/validation/id")
    public void validationId(@RequestParam String userId) {
        memberService.validateDuplicateMemberUserId(userId);
    }

    /**
     비밀번호 찾기
 **/

    @PostMapping("/mailConfirm")
    public boolean confirmMail(@RequestBody InputCodeRequest request ) {
        return memberService.verifyJoinEmail(request.getInputCode());
        //false면 프론트에서 다시 인증번호 받기 누르도록.
    }

    @PostMapping("/member/findPassword")
    public void findPassword(@RequestBody InputEmailRequest request) throws Exception {
        memberService.findPassword(request.getEmail());
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
        return "ok"; // 홈 페이지로 리다이렉트
    }
}