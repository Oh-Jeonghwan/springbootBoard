package com.nmplus.springbootBoard.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.nmplus.springbootBoard.controller.BoardController;
import com.nmplus.springbootBoard.repository.AttachmentRepository;
import com.nmplus.springbootBoard.repository.BoardRepository;
import com.nmplus.springbootBoard.vo.Board;
import com.nmplus.springbootBoard.vo.UploadVo;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private AttachmentService attachmentService;
	
	private int isOK = 1;
	
	private int isNO = 0;
	
	public Page<Board> selectList(Pageable pageable
								, String condition
								, String keyword) {
		//Sort.by(Sort.Direction.DESC, "기준컬럼명")
		
		String status = "Y";
		
		if(condition.equals("")) {
			return boardRepository.findByStatus(status, pageable);
		}
		else if(condition.equals("boardTitle")) {
			return boardRepository.findByBoardTitleContainingAndStatus(keyword, status, pageable);
		}
		else if(condition.equals("boardContent")){
			return boardRepository.findByBoardContentContainingAndStatus(keyword, status, pageable);
		}
		else {
			return boardRepository.findByWriterContainingAndStatus(keyword, status, pageable);
		}
		
		
	}
	
	public Board saveBoard(Board board) {
		return boardRepository.save(board);
	}
	
	public Board boardSearch(Long boardNo) {
		Board board = (Board) boardRepository.findByBoardNo(boardNo);
		return board;
	}

	public int zeroToOne(int number) {
		if(number<=0) {number=1;}
		return number;
	}

	public Board findById(Long boardNo) {
		return boardRepository.findByBoardNo(boardNo);
	}
	
}
