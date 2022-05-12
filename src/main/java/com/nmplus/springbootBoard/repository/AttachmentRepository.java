package com.nmplus.springbootBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nmplus.springbootBoard.vo.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment,Long> {

	Attachment findByFileNo(Long attNo);

}
