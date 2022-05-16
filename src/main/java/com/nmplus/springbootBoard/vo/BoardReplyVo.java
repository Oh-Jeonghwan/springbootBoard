package com.nmplus.springbootBoard.vo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityListeners;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.nmplus.springbootBoard.entity.BoardReply;
import com.nmplus.springbootBoard.service.BoardReplyService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	
	@Builder
	public BoardReplyVo(String replyContent
					, Board board
					, String replyWriter
					, Long replyNo
					, LocalDateTime enrollDate
					, String status){
		this.replyContent = replyContent;
		this.board = board;
		this.replyWriter = replyWriter;
		this.replyNo = replyNo;
		this.enrollDate = enrollDate;
		this.status= status;
		
	}
	
	public BoardReply toEntity() {
		return BoardReply.builder()
				.board(board)
				.replyContent(replyContent)
				.replyWriter(replyWriter)
				.status(status)
				.replyNo(replyNo)
				.build();
		
	}
	

}