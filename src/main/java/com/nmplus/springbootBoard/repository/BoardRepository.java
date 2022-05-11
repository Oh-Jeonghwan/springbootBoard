package com.nmplus.springbootBoard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nmplus.springbootBoard.vo.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long>{

	Page<Board> findByStatus(String status, Pageable pageable);//<vo객체, 주키의 자료형>

	Page<Board> findByBoardTitleContainingAndStatus(String keyword, String status, Pageable pageable);

	Page<Board> findByBoardContentContainingAndStatus(String keyword, String status, Pageable pageable);

	Page<Board> findByWriterContainingAndStatus(String keyword, String status, Pageable pageable);

	Board findByBoardNoUsingJoin(Long boardNo);

	
	
}
