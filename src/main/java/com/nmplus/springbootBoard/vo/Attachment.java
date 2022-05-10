package com.nmplus.springbootBoard.vo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@DynamicInsert
@ToString
public class Attachment {
	
	@Id //pk라는 것을 알려주d기 위한 어노테이션
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long fileNo;
	
	@Column(nullable = false) //기존의 파일이름
    private String originFilename;

    @Column(nullable = false) //글 등록 후 파일이름
    private String filename;

    @Column(nullable = false) //파일 경로
    private String filePath;
    
    @Column(updatable = false, nullable = false)
	@CreatedDate
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime enrollDate;
	
	@Column(nullable = false)
	@LastModifiedDate
	@Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime modifiedDate;
	
	@Column(nullable = true)
	private Long refBoardNo;
	
	@Column
	@ColumnDefault("'Y'")
	private String status;
	
	
    
    
}
