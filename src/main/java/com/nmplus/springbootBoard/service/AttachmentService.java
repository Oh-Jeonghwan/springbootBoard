package com.nmplus.springbootBoard.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nmplus.springbootBoard.controller.api.BoardApiController;
import com.nmplus.springbootBoard.repository.AttachmentRepository;
import com.nmplus.springbootBoard.vo.Attachment;
import com.nmplus.springbootBoard.vo.Board;
import com.nmplus.springbootBoard.vo.UploadVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AttachmentService {

	@Autowired
	private AttachmentRepository attachmentRepository;

	public void insertAtt(UploadVo uploadVo, Board result) {
		
		String savePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\upfile";

		if (uploadVo.isFileExist()) {

			for (int i = 0; i < uploadVo.getUpfile().size(); i++) {
				Attachment attachment = new Attachment();
				
				UUID uuid = UUID.randomUUID();
				String changeName = uuid + "_" + uploadVo.getUpfile().get(i).getOriginalFilename();
				File target1 = new File(savePath, changeName);

				try {
					uploadVo.getUpfile().get(i).transferTo(target1);

					attachment.setOriginFilename(uploadVo.getUpfile().get(i).getOriginalFilename());
					attachment.setFilename(changeName);
					attachment.setBoard(result);
					//attachment.setFilePath("C:\\\\Users\\\\nmplus\\\\eclipse-workspace\\\\springbootBoard\\\\src\\\\main\\\\resources\\\\static\\\\upfile");
					attachmentRepository.save(attachment);

				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
