package com.nmplus.springbootBoard.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//jsp에서 한번에 파일 여러 개가 넘어올 때 받기 위한 객체
//업파일을 리스트로 받는다.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadVo {
	
	private String uploader;
	private List<MultipartFile> upfile;

	//파일 유무를 반환하는 메소드
	public boolean isFileExist() {
		//파일이 없는 ㄴ경우의 수를 나열=> 각각 return false;
		if(upfile==null) {
			return false;
		}
		if(upfile.size()<1) { //upfile 객체가 있지만 size가 영일 경우
			return false;
		}
		return true;
	}
}
