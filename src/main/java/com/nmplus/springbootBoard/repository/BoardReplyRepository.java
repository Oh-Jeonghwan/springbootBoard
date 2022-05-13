package com.nmplus.springbootBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nmplus.springbootBoard.entity.BoardReply;
import com.nmplus.springbootBoard.vo.BoardReplyVo;

@Repository
public interface BoardReplyRepository extends JpaRepository<BoardReply, Long>{


}
