package com.ssafy.sonmogaji.model.service;

import com.ssafy.sonmogaji.model.dto.LoginResponseDto;
import com.ssafy.sonmogaji.model.entity.Member;
import com.ssafy.sonmogaji.model.entity.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.sonmogaji.jwt.JwtTokenProvider;
import com.ssafy.sonmogaji.model.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
	
	private final MemberRepository memberRepository;
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public LoginResponseDto login(String memberAddress) {
	
		if(!memberRepository.findByMemberAddress(memberAddress).isPresent()) {
			// 회원가입 로직

			Member member = Member.builder()
					.memberAddress(memberAddress)
					.nonce(new Random().nextInt())
					.role(Role.USER)
					.build();

			memberRepository.save(member);
		}

		// 로그인 로직
			Member member = memberRepository.findByMemberAddress(memberAddress).orElseThrow(() -> new IllegalArgumentException("회원정보가 존재하지 않습니다!"));
			String accessToken = jwtTokenProvider.createAccessToken(memberAddress);

		return new LoginResponseDto(accessToken);
	}

//	@Override
//	public LoginResponseDto reIssueAccessToken(String memberAddress, String refreshToken) {
//		Member member = memberRepository.findByMemberAddress(memberAddress).orElseThrow(() -> new IllegalArgumentException("회원정보가 존재하지 않습니다!"));
//		jwtTokenProvider.checkRefreshToken(memberAddress, refreshToken);
//		String accessToken = jwtTokenProvider.createAccessToken(member.getMemberAddress());
//
//		return new LoginResponseDto(accessToken, refreshToken);
//	}

	@Override
	public void logout(String memberAddress, String accessToken) {
		jwtTokenProvider.logout(memberAddress, accessToken);
	}


}
