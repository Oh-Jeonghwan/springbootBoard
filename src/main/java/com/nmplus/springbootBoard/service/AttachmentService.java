package com.nmplus.springbootBoard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nmplus.springbootBoard.repository.AttachmentRepository;
import com.nmplus.springbootBoard.vo.Attachment;

@Service
public class AttachmentService {
	
	@Autowired
	private AttachmentRepository AttachmentRepository;

	public Attachment saveAtt(MultipartFile file) {
		
		Attachment attach = new Attachment();
		
		attach.setOriginFilename(file.getOriginalFilename());
		attach.setFilename(file.getName());
		
		return null;
	}
	

}
