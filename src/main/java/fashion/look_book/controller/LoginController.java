package fashion.look_book.controller;

import fashion.look_book.Dto.LoginDtos.*;
import fashion.look_book.domain.Member;
import fashion.look_book.login.Login;
import fashion.look_book.login.LoginService;
import fashion.look_book.login.SessionConst;
import fashion.look_book.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "http://3.35.99.247:3000", maxAge = 3600)
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final LoginService loginService;

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

    
    @PostMapping("/member/add")
    public ResponseEntity<?> saveMember(@Validated @RequestBody addMemberDtoRequest request, BindingResult bindingResult) {
        /*if (bindingResult.hasErrors()) {
            log.info("회원가입 검증 오류 발생");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }*/
        // 웹 구현되면 검증 해보기

        Member member = new Member(request.getUser_user_id(), request.getEmail(), request.getPassword(), request.getNickname());

        Long id = memberService.join(member);

        return new ResponseEntity(HttpStatus.OK);
    }

    // 중복확인 버튼
    // 새로운 페이지가 아니라 아이디만 Post매핑으로 보내서 검증하는 식으로
    @PostMapping("/validation/id")
    public boolean validationId(@RequestBody validationRequest request) {
        return memberService.validateDuplicateMemberUserId(request.getUser_user_id());
    }

    @PostMapping("/validation/nickname")
    public boolean validationNickname(@RequestBody validationNickname request) {
        return memberService.validateByNickname(request.getNickname());
    }

    @PostMapping("/validation/email")
    public boolean validationEmail(@RequestBody validationEmail request) {
        return memberService.validateByEmail(request.getEmail());
    }

    /**
     비밀번호 찾기
    **/

    @PostMapping("/mailConfirm")
    public boolean confirmMail(@RequestBody InputCodeRequest request) {
        return memberService.verifyJoinEmail(request.getInputCode());
        //false면 프론트에서 다시 인증번호 받기 누르도록.
        // 나중에 회원가입 때 이메일 인증 받을 때를 대비해서 만들어 놓은 것
    }

    @PostMapping("/member/findPassword")
    public void findPassword(@RequestBody InputEmailRequest request) throws Exception {
        memberService.findPassword(request.getEmail());
    }


    /**
         로그인, 로그아웃
    **/

    @PostMapping("/login")
    public ResponseEntity<?> login (@Validated @RequestBody LoginDtoRequest request, BindingResult bindingResult,
                                    HttpServletRequest httpServletRequest) {
        if(bindingResult.hasErrors()) {
            log.info("로그인 오류");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
            // 로그인 페이지로 리다이렉트
        }

        Member loginMember = loginService.login(request.getUser_user_id(), request.getPassword());

        if(loginMember == null) {
            bindingResult.reject("loginFail");
            return new ResponseEntity(HttpStatus.BAD_REQUEST); // 여기도 로그인 실패로 리다이렉트
        }

        HttpSession session = httpServletRequest.getSession();
        // request.getSession() -> 세션이 있으면 세션 반환, 없으면 신규 세션을 생성
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        // 세션에 로그인 회원 정보를 보관

        return new ResponseEntity(HttpStatus.OK);
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout (HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ResponseEntity(HttpStatus.OK); // 홈 페이지로 리다이렉트
    }
}