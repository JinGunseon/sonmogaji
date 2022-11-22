package com.ssafy.sonmogaji.controller;

import com.ssafy.sonmogaji.model.dto.LoginRequestDto;
import com.ssafy.sonmogaji.model.dto.LoginResponseDto;
import com.ssafy.sonmogaji.model.entity.Member;
import com.ssafy.sonmogaji.model.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<?> login (@RequestBody LoginRequestDto loginDto, HttpServletResponse response) throws Exception {
		
		try {

			LoginResponseDto responseDto = memberService.login(loginDto.getMemberAddress());
//			String token = memberService.login(memberAddress);
//			response.setHeader("authorization", "bearer " + token);

			return new ResponseEntity<LoginResponseDto>(responseDto, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
		
	}

	// AccessToken 재발급
//	@GetMapping("/re-issue")
//	public ResponseEntity<?> reIssue(@RequestParam("memberAddress") String memberAddress, @RequestParam("refreshToken") String refreshToken) {
//		LoginResponseDto responseDto = memberService.reIssueAccessToken(memberAddress, refreshToken);
//		return new ResponseEntity<LoginResponseDto>(responseDto, HttpStatus.OK);
//	}


	// 로그아웃
	@GetMapping("/logout")
	public ResponseEntity<?> logout( Member member, HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization").substring(7);
		memberService.logout(member.getMemberAddress(), accessToken);
		return new ResponseEntity<String>("로그아웃 완료", HttpStatus.OK);
	}
	
	// 예외처리
			private ResponseEntity<String> exceptionHandling(Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
	
	
}
