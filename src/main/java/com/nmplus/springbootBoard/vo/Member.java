package com.nmplus.springbootBoard.vo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity //데이터 베이스 연동을 위한 모델 클래스라는 것을 알려주는 어노테이션
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert //인서트 시에 null인 필드 제외하고 인서트 , not null x nullable
@ToString
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberNo;
	
	@Column(nullable=false, length = 20)
	private String memberName;
	
	@Column(nullable=false, length = 20)
	private String memberId;
	
	@Column(nullable=false, length = 100)//=> 해시(비밀번호 암호화)
	private String memberPwd;
	
	@Column(nullable=false, length = 30)
	private String email;
	
	@Column(nullable=false, length = 11)
	private String phone;
	
	@Enumerated(EnumType.STRING)
	private RoleType role; //admin, user //Enum을 쓰는 게 좋다.(오타를 낼 수 있으니까) 
	
	@Column(updatable = false, nullable = false)
	@CreatedDate
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime enrollDate;
	
	@Column(nullable = false)
	@LastModifiedDate
	@Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime modifiedDate;
	
	@Column(length = 2)
	@ColumnDefault("'Y'")
	private String status;
	
	
	

}
