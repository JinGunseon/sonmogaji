package com.ssafy.sonmogaji.model.service;

import com.ssafy.sonmogaji.model.dto.LoginResponseDto;

public interface MemberService {
	
	// 로그인
	public LoginResponseDto login(String memberAddress);


//    public LoginResponseDto reIssueAccessToken(String memberAddress, String refreshToken);

	void logout(String memberAddress, String accessToken);
}
