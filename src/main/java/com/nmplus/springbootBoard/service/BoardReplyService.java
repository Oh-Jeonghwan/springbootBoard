package com.nmplus.springbootBoard.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import com.nmplus.springbootBoard.config.auth.PrincipalDetail;
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
	
	private Sort sortByReplyNo() {
		return Sort.by(Sort.Direction.DESC,"replyNo");
	}
	
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

	public List<BoardReplyVo> replyList(Long boardNo) {
		
		Board board = boardRepository.findByBoardNo(boardNo);
		
		String status = "Y";
		
		Sort sort = sortByReplyNo();
		
		List<BoardReply> list = boardReplyRepository.findByBoardAndStatus(board, status, sort);
		
		List<BoardReplyVo> voList = new ArrayList<>();
		
		for(int i=0; i<list.size(); i++) {
			BoardReplyVo replyVo = new BoardReplyVo();
			
			voList.add(replyVo);
			
			voList.get(i).setReplyNo(list.get(i).getReplyNo());
			voList.get(i).setReplyWriter(list.get(i).getReplyWriter());
			voList.get(i).setReplyContent(list.get(i).getReplyContent());
			voList.get(i).setEnrollDate(list.get(i).getEnrollDate());
		}
		
		return voList;
	}

	public int replyDelete(Long replyNo) {
		BoardReply delete = boardReplyRepository.findByReplyNo(replyNo);
		
		BoardReplyVo deleteVo = new BoardReplyVo();
		
		BeanUtils.copyProperties(delete, deleteVo);
		
		deleteVo.setStatus("N");
		
		BoardReply result = boardReplyRepository.save(deleteVo.toEntity());
		
		if(result!=null) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public void replyDelete(Board boardDelete) {
		List<BoardReply> delete = boardReplyRepository.findByBoard(boardDelete);
		
		if(!delete.isEmpty()) {
			for(int i=0; i<delete.size(); i++) {
				BoardReplyVo deleteVo = new BoardReplyVo();
				BeanUtils.copyProperties(delete.get(i), deleteVo);
				deleteVo.setStatus("N");
				BoardReply result = boardReplyRepository.save(deleteVo.toEntity());
			}
		}
	}


}
