package com.nmplus.springbootBoard.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
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

	public ResponseEntity<Resource> download(Long attNo) throws Exception{
		
		//getFile() 메소드는 war파일에서는 되는데 jar파일에서는 안 됨
		//war는 run시에 실제 리소스 파일인 file:// 프로토콜을 쓰지만 jar에서는 http://사용 
		//Resource resource = resourceLoader.getResource(path);	
		//File file = resource.getFile();
		
		Attachment attachment = attachmentRepository.findByFileNo(attNo);
		String path = System.getProperty("user.dir") + attachment.getFilePath()+attachment.getFilename();
		
		//경로를 통해 파일화 시켜준다.
		File file = new File(path);
		
		//파일화한 것을 바이트로 변환시켜준다.
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		
		//http 형식의 요청 또는 응답을 처리하기 위한 header 설정 (키 밸류 값의 map 형식)
		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName())); 
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pargma", "no-cache");
		headers.add("Expires", "0");
		
		//httpentity를 상속하고, httpstatus를 포함하고 있는 @Controller와 restTepmplate에 사용된다.
		ResponseEntity<Resource> responseEntity = 
			ResponseEntity.ok().headers(headers)
			.contentLength(file.length())
			.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
		
			return responseEntity;
			//URLEncoder.encode(attachment.getOriginFilename()
			
//			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(file.getName(), "UTF-8")+"\";");
//            response.setHeader("Content-Transfer-Encoding", "binary");
//            response.setHeader("Content-Type", "application/octet-stream");
//            response.setHeader("Content-Length", "" + file.length());
//            response.setHeader("Pragma", "no-cache;");
//            response.setHeader("Expires", "-1;");
			
//			 ResponseEntity.ok()
//					.header(HttpHeaders.CONTENT_DISPOSITION,file.getName())	//다운 받아지는 파일 명 설정
//					.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))	//파일 사이즈 설정
//					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString())	//바이너리 데이터로 받아오기 설정
//					.body(resource);	//파일 넘기기
			
//		try (FileInputStream fis = new FileInputStream(path);
//			 OutputStream out = response.getOutputStream();){ 
//			 
//			int readCount = 0;
//			byte[] buffer = new byte[1024];
//			while((readCount = fis.read(buffer))!= -1) {
//				out.write(buffer,0,readCount);
//			}
//		} catch (Exception e ) {
//			e.printStackTrace();
//			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		} 
	}

	
}
