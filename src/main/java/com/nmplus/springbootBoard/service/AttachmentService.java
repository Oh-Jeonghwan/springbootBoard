package com.nmplus.springbootBoard.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.util.UriUtils;

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

	public void download(HttpServletResponse response
											, Long attNo) throws IOException{
		
		Attachment attachment = attachmentRepository.findByFileNo(attNo);
		String path = System.getProperty("user.dir") + attachment.getFilePath() + attachment.getFilename();
		
			//getFile() 메소드는 war파일에서는 되는데 jar파일에서는 안 됨
			//war는 run시에 실제 리소스 파일인 file:// 프로토콜을 쓰지만 jar에서는 http://사용 
			//Resource resource = resourceLoader.getResource(path);	
			//File file = resource.getFile();
			
		
			Path path1 = Paths.get(path); //다운로드할 파일의 최종경로
			
			if(!path1.toFile().exists()) {
				throw new RuntimeException("file not found");
			}
			
			response.setHeader("Content-Disposition", "attachment; filename=\"" +  URLEncoder.encode(attachment.getOriginFilename(), "UTF-8") + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Type", "application/download; utf-8");
			response.setHeader("Pragma", "no-cache;");
			response.setHeader("Expires", "-1;");
			
			FileInputStream fis = null;
			
			fis = new FileInputStream(path1.toFile());
			FileCopyUtils.copy(fis, response.getOutputStream());
			response.getOutputStream().flush();
			fis.close();
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
