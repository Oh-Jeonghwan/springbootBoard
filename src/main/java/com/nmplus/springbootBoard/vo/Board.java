package com.nmplus.springbootBoard.vo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nmplus.springbootBoard.entity.BoardReply;

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
public class Board {
	@Id //pk라는 것을 알려주d기 위한 어노테이션
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardNo;
	
	@Column
	private String boardTitle;
	
	@Column(columnDefinition = "TEXT")
	private String boardContent;
	
	@Column
	private String writer;
	
	@Column
	@ColumnDefault("0")
	private int count;
	
	@Column
	@OneToMany(
			mappedBy = "board"
		  , cascade = {CascadeType.PERSIST}
			)
	private List<Attachment> attachment;
	
	@Column
	@OneToMany(mappedBy = "board"
			  , cascade = {CascadeType.ALL})
	@JsonIgnoreProperties({"board"})
	private List<BoardReply> boardReply;
    
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
	
}
