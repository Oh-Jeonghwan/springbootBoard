package com.nmplus.springbootBoard.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nmplus.springbootBoard.entity.BoardReply;
import com.nmplus.springbootBoard.repository.AttachmentRepository;
import com.nmplus.springbootBoard.vo.Attachment;
import com.nmplus.springbootBoard.vo.Board;
import com.nmplus.springbootBoard.vo.BoardReplyVo;
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
					   , HttpServletRequest request
					   , Long attNo) throws Exception{
		
		//httpServletResponse, httpServleRequest(반환값 없음), ResponseEntity<Resource>(반환값 있음)
		
		
		//getFile() 메소드는 war파일에서는 되는데 jar파일에서는 안 됨
		//war는 run시에 실제 리소스 파일인 file:// 프로토콜을 쓰지만 jar에서는 http://사용 
		//Resource resource = resourceLoader.getResource(path);	
		//File file = resource.getFile();
		
		Attachment attachment = attachmentRepository.findByFileNo(attNo);
		String path = System.getProperty("user.dir") + attachment.getFilePath()+attachment.getFilename();
		
		//경로를 통해 파일화 시켜준다.
		File file = new File(path);
		
		//파일을 스트림에 통과될 수 있도록 바이트로 변환해주는 클래스
		FileInputStream fileInputStream = null;
		//서블릿 컨테이너에 를 임플리먼트하는 추상클래스, 고객에게 바이너리 데이터를 보내주기 위한 아웃풋 스트림을 제공해주는 클래스.
	    ServletOutputStream servletOutputStream = null;
	 
	    try{
	        String downName = null;
	        
	        //파일 인코딩(브라우저 마다 다르다.)
	        downName = new String(attachment.getOriginFilename().getBytes("UTF-8"), "ISO-8859-1");
	        /*
	         * String browser = request.getHeader("User-Agent");
		        //파일 인코딩
		        if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){//브라우저 확인 파일명 encode  
		            downName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
		        }else{
		             downName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		        }
	         * */
	        
	        
	      //들어오는 파일이 어떤 형태인지 알여주는웹페이지 자체이거나 웹페이지의 일부인지, 아니면 attachment로써 다운로드 되거나 로컬에 저장될 용도록 쓰이는 것인지를 알려주는 헤더
	        response.setHeader("Content-Disposition","attachment;filename=\"" + downName+"\"");
	        
	        response.setContentType("application/octet-stream");
	        
	        response.setHeader("Content-Transfer-Encoding", "binary;");
	        
	        fileInputStream = new FileInputStream(file);
	        servletOutputStream = response.getOutputStream();
	 
	        byte b [] = new byte[1024];
	        int data = 0;
	        
	      //파일 인풋스트림에서 바이트를 다 읽어올 때까지 반복하여 outputstream에 쓴다.(다 읽어오면 -1반환)
	        while((data=(fileInputStream.read(b, 0, b.length))) != -1){ 
	            servletOutputStream.write(b, 0, data);
	        }
	 
	        servletOutputStream.flush();//출력
	        
	    }catch (Exception e) {
	        e.printStackTrace();
	    }finally{
	        if(servletOutputStream!=null){
	            try{
	                servletOutputStream.close();
	            }catch (IOException e){
	                e.printStackTrace();
	            }
	        }
	        if(fileInputStream!=null){
	            try{
	                fileInputStream.close();
	            }catch (IOException e){
	                e.printStackTrace();
	            }
	        }
	    }
		
		//파일화한 것을 바이트로 변환시켜준다.
//		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//		
//		//파일의 mediaType알아내기
//		
//		//http 형식의 요청 또는 응답을 처리하기 위한 header 설정 (키 밸류 값의 map 형식)
//		HttpHeaders headers = new HttpHeaders();
//		
//		headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName())); 
//		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//		headers.add("Pargma", "no-cache");
//		headers.add("Expires", "0");
//		
//		log.debug("파일길이: "+file.isFile());
//		//httpentity를 상속하고, httpstatus를 포함하고 있는 @Controller와 restTepmplate에 사용된다.
//		ResponseEntity<Resource> responseEntity = 
//			ResponseEntity.ok().headers(headers) //작성한 헤더 담아주기
//			.contentLength(file.length()) //파일 길이
//			.contentType(MediaType.parseMediaType("application/octet-stream")) //파일 형식
//			.body(resource); 
//		
//			return responseEntity;
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

	public void attachmentDelete(Board boardDelete) {
		
		List<Attachment> delete = attachmentRepository.findByBoard(boardDelete);
		
		if(!delete.isEmpty()) {
			for(int i=0; i<delete.size(); i++) {
				Attachment deleteVo = new Attachment();
				BeanUtils.copyProperties(delete.get(i), deleteVo);
				deleteVo.setStatus("N");
				Attachment result = attachmentRepository.save(deleteVo);
			}
		}
		
	}

	
}
