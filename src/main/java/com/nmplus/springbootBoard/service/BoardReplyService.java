package com.nmplus.springbootBoard.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import com.nmplus.springbootBoard.config.auth.PrincipalDetail;
import com.nmplus.springbootBoard.controller.api.BoardApiController;
import com.nmplus.springbootBoard.entity.BoardReply;
import com.nmplus.springbootBoard.repository.BoardReplyRepository;
import com.nmplus.springbootBoard.repository.BoardRepository;
import com.nmplus.springbootBoard.vo.Board;
import com.nmplus.springbootBoard.vo.BoardReplyVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardReplyService {
	
	@Autowired
	private BoardReplyRepository boardReplyRepository;
	
	@Autowired
	private BoardRepository boardRepository;
	
	public BoardReplyVo replyInsert(Long boardNo
						  , String replyContent
						  , @AuthenticationPrincipal PrincipalDetail principal) {
		Board board = boardRepository.findByBoardNo(boardNo);
		BoardReplyVo reply = new BoardReplyVo();
		
		
		//replyVo 객체에 값 담아주기
		reply.setBoard(board);
		reply.setReplyContent(replyContent);
		reply.setReplyWriter(principal.getUsername());
		
		BoardReply result = boardReplyRepository.save(reply.toEntity());
		
		//save후 받아온 entity 객체를 기존에 쓰던 vo 객체로 복사
		BoardReplyVo reply1 = new BoardReplyVo();
		BeanUtils.copyProperties(result, reply1);
		
		return reply1;
	}


}
