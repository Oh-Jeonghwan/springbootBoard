package com.nmplus.springbootBoard.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nmplus.springbootBoard.vo.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity //데이터 베이스 연동을 위한 모델 클래스라는 것을 알려주는 어노테이션
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@ToString
public class BoardReply {

	
	@Id //pk라는 것을 알려주d기 위한 어노테이션
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long replyNo;
	
	@Column(nullable = false)
    private String replyContent;

    @Column(nullable = false)
    private String replyWriter;
    
    @ManyToOne
	@JoinColumn(name = "boardNo")
    @JsonIgnoreProperties({"board"})
	private Board board;
    
    @Column(updatable = false, nullable = false)
	@CreatedDate
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime enrollDate;
    
    

	@Column(nullable = false)
	@LastModifiedDate
	@Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime modifiedDate;
	
	@Column
	@ColumnDefault("'Y'")
	private String status;

	@Builder
	public BoardReply(String replyContent
					, Board board
					, String replyWriter
					, String status
					, Long replyNo){
		this.replyContent = replyContent;
		this.board = board;
		this.replyWriter = replyWriter;
		this.status = status;
		this.replyNo = replyNo;
		
	}
	
	

}
