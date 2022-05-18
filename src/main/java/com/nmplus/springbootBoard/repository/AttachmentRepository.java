package com.nmplus.springbootBoard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nmplus.springbootBoard.vo.Attachment;
import com.nmplus.springbootBoard.vo.Board;

public interface AttachmentRepository extends JpaRepository<Attachment,Long> {

	Attachment findByFileNo(Long attNo);

	List<Attachment> findByBoard(Board boardDelete);

}
