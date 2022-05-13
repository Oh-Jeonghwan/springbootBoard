package com.nmplus.springbootBoard.vo;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.nmplus.springbootBoard.entity.BoardReply;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@NoArgsConstructor
public class BoardReplyVo {

	private Long replyNo;
	
    private String replyContent;

    private String replyWriter;
    
	private Board board;
	
	private LocalDateTime enrollDate;
	
    private LocalDateTime modifiedDate;
	
	private String status;
	
	public BoardReply toEntity() {
		return BoardReply.builder()
				.board(board)
				.replyContent(replyContent)
				.replyWriter(replyWriter)
				.build();
		
	}

}