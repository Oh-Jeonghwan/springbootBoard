package com.nmplus.springbootBoard.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nmplus.springbootBoard.repository.AttachmentRepository;
import com.nmplus.springbootBoard.vo.Attachment;
import com.nmplus.springbootBoard.vo.Board;
import com.nmplus.springbootBoard.vo.UploadVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AttachmentService {
	
	@Autowired
	private ResourceLoader resourceLoader;

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
					//파일을 서버에 넣어주는 코드
					uploadVo.getUpfile().get(i).transferTo(target1);
					
					//파일에 대한 정보를 DB에 넣어주는 코드
					attachment.setOriginFilename(uploadVo.getUpfile().get(i).getOriginalFilename());
					attachment.setFilename(changeName);
					attachment.setBoard(result);
					attachmentRepository.save(attachment);

				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public ResponseEntity<Resource> download(Long attNo) throws IOException{
		
		Attachment attachment = attachmentRepository.findByFileNo(attNo);
		//String path = System.getProperty("user.dir") + attachment.getFilePath() + attachment.getFilename();
		Path path = Paths.get(File.separatorChar + "file", File.separatorChar + attachment.getFilename());
		
		try{
				//파일 경로를 인풋스트림 리소스의 형태에 맞게 변환 시켜 Resorce 인터페이스에 채워준다.
				Resource resource = new InputStreamResource(getClass().getResourceAsStream(path.toString()));
				
				//war에서는 되는데 jar에서는 안 된다.(파일을 못 찾는다. 왜?)
				//File file = resource.getFile();
				
				//리스폰스 엔티티의 헤더 영역을 채워서 파일 경로로 가는 것이 아닌 다운이 되도록 해서 반환.
				return ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_OCTET_STREAM)
						.cacheControl(CacheControl.noCache())
						.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+attachment.getFilename()+"")
						.body(resource);

		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	
}
