package com.wesely.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wesely.service.MemberService;
import com.wesely.vo.CommVO;
import com.wesely.vo.MemberVO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memberService;

	// 회원 가입 폼 처리
	@RequestMapping(value = "/join")
	public String join() {
		return "/member/join";
	}

	// 아이디 중복확인
	@RequestMapping(value = "/idCheck", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String idCheck(@RequestParam String userid) {
		log.info("{}의 idCheck 호출 : {}", this.getClass().getName(), userid);
		int count = memberService.idCheck(userid);
		log.info("{}의 idCheck 리턴 : {}", this.getClass().getName(), count);
		return count + "";
	}


	
	
	// 로그인 폼 처리하기
	@GetMapping(value = "/login")
	public String login(HttpServletRequest request, Model model) {
		// 쿠키에 저장된 사용자아이디가 있으면 읽어서 간다.
		Cookie[] cookies = request.getCookies();
		String userid = null;
		// userid변수에 쿠키에 userid가 있다면 읽어서 대입하자
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userid")) {
					userid = cookie.getValue();
					break;
				}
			}
		}
		// 모델에 쿠키값을 저장한다.
		model.addAttribute("userid", userid);
		// 로그인 폼으로 포워딩 한다.
		return "/member/login";
	}

	// 로그인 처리하기
	@GetMapping(value = "/loginOk")
	public String loginOk(Model model) {
		return "redirect:/index";
	}



	// 로그 아웃 처리
	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {
		// 세션에 저장된 회원 정보를 지워버린다.
		session.removeAttribute("mvo");
		return "redirect:/";
	}

	// 회원 가입 처리
	@GetMapping("/joinOk")
	public String joinOkGet() {
		return "redirect:/";
	}

	@PostMapping("/joinOk")
	public String joinOkPost(@ModelAttribute MemberVO memberVO) {
		if (memberVO != null) {
			// 서비스를 호출하여 저장을 수행한다.
			memberService.insert(memberVO);
		}
		return "redirect:/member/login";
	}

	// 아이디 찾기 폼
	@GetMapping(value = "/findUserId")
	public String findUserId() {
		return "/member/findUserId";
	}

	// 아이디 찾기 실행
	@GetMapping(value = "/findUserIdOk")
	public String findUserIdOkGet() {
		return "redirect:/";
	}

	@PostMapping(value = "/findUserIdOk")
	public String findUserIdOkPost(@ModelAttribute MemberVO vo, Model model) {
		// 사용자 이름과 전화번호 받음
		MemberVO dbVO = memberService.findUserId(vo);
		if (dbVO == null) {
			return "redirect:/member/findUserId";
		}
		model.addAttribute("vo", dbVO);
		return "/member/viewUserId";
	}

	// 비밀번호 찾기 폼
	@GetMapping(value = "/findPassword")
	public String findPassword() {
		return "/member/findPassword";
	}

	// 비밀번호 찾기 실행
	@GetMapping(value = "/findPasswordOk")
	public String findPasswordGet() {
		return "redirect:/";
	}

	// 회원정보수정 폼
	@GetMapping(value = "/updateProfile")
	public String updateProfile() {
		return "/member/updateProfile";
	}

	// 회원정보수정 실행
	@GetMapping(value = "/updateProfileOk")
	public String updateProfileOk() {
		return "/member/updateProfileOk";
	}

	// 비밀번호변경 폼
	@GetMapping(value = "/updatePassword")
	public String updatePassword() {
		return "/member/updatePassword";
	}

	// 비밀번호변경 실행
	@GetMapping(value = "/updatePasswordOk")
	public String updatePasswordOk() {
		return "/member/updatePasswordOk";
	}

	// 회원약관 동의 폼
	@GetMapping(value = "/agreement")
	public String agreement() {
		return "/member/agreement";
	}

	@Autowired
	private JavaMailSender javaMailSender;

	@PostMapping(value = "/findPasswordOk")
	public String findPasswordPost(@ModelAttribute MemberVO vo, Model model) throws MessagingException {
		MemberVO dbVO = memberService.findPassword(vo);
		if (dbVO == null) {
			// 일치하지 않는다면
			return "redirect:/member/findPassword";
		}
		// 일치하면
		// 메일을 발송하고
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setFrom("ithuman202303@gmail.com");
		helper.setTo(dbVO.getEmail());
		helper.setSubject(dbVO.getUsername() + "님 비밀번호 안내입니다.");
		// 메일 내용 만들기
		StringBuffer sb = new StringBuffer();
		sb.append(dbVO.getUsername() + "님 비밀번호 안내입니다.<br>");
		sb.append(dbVO.getUsername() + "님의 임시 비밀번호는 " + dbVO.getPassword() + "입니다.<br>");
		helper.setText(sb.toString(), true);

		// 메일 발송
		javaMailSender.send(message);

		model.addAttribute("vo", dbVO);
		return "/member/viewPassword";
	}

	// 커뮤니티 목록보기
	@RequestMapping(value = { "/", "/list" })
	public String getList(@ModelAttribute CommVO cv, Model model) {

		return "comm/community";
	}

}
