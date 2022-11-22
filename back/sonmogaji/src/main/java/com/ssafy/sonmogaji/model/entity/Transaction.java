package com.ssafy.sonmogaji.model.entity;

import com.ssafy.sonmogaji.model.dto.TransactionDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class Transaction {

	// tx 주소
	@Id
	@Column(name = "tx_address")
	private String txAddress;
	
	// 각서 제목
	@Column(name = "tx_title")
	private String txTitle;
	
	// 각서 내용
	@Column(name = "tx_content")
	private String txContent;
	
	// 추억사진 파일이름
	@Column(name = "image_title")
	private String imageTitle;
	
	// 추억사진 주소
	@Column(name = "image_url")
	private String imageUrl;
	
	// 추억사진 비공개 여부
	@Column(name = "image_is_secret")
	private Boolean imageIsSecret;
	
	// 비공개 여부
	@Column(name = "tx_is_secret")
	private Boolean txIsSecret;
	
	// 각서 생성날짜
	@Column(name = "tx_create_date")
	private LocalDate txCreateDate;
	
	// 각서 만료날짜
	@Column(name = "tx_exp_date")
	private LocalDate txExpDate;
	
	// 각서 nft 주소
	@Column(name = "tx_nft_url")
	private String txNftUrl;
	
	public TransactionDto toTransactionDto() {
		return TransactionDto.builder()
				.txAddress(txAddress)
				.txTitle(txTitle)
				.txContent(txContent)
				.imageTitle(imageTitle)
				.imageUrl(imageUrl)
				.imageIsSecret(imageIsSecret)
				.txIsSecret(txIsSecret)
				.txCreateDate(txCreateDate)
				.txExpDate(txExpDate)
				.txNftUrl(txNftUrl)
				.build();
	}
	
}
