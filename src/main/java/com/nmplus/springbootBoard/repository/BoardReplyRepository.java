package com.nmplus.springbootBoard.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nmplus.springbootBoard.entity.BoardReply;
import com.nmplus.springbootBoard.vo.Board;
import com.nmplus.springbootBoard.vo.BoardReplyVo;

@Repository
public interface BoardReplyRepository extends JpaRepository<BoardReply, Long>{

	BoardReply findByReplyNo(Long replyNo);

	List<BoardReply> findByBoardAndStatus(Board board, String status, Sort sort);

	List<BoardReply> findByBoard(Board boardDelete);

}
