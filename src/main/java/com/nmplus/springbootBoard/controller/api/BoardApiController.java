package com.nmplus.springbootBoard.controller.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.nmplus.springbootBoard.config.auth.PrincipalDetail;
import com.nmplus.springbootBoard.service.AttachmentService;
import com.nmplus.springbootBoard.service.BoardReplyService;
import com.nmplus.springbootBoard.service.BoardService;
import com.nmplus.springbootBoard.vo.Board;
import com.nmplus.springbootBoard.vo.BoardReplyVo;
import com.nmplus.springbootBoard.vo.UploadVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board/api")
public class BoardApiController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private BoardReplyService boardReplyService;

	@PostMapping("/insert")
	public String insert(Model model
					   , @ModelAttribute Board board
					   , @ModelAttribute UploadVo uploadVo
					   , @AuthenticationPrincipal PrincipalDetail principal) throws MaxUploadSizeExceededException {

		board.setWriter(principal.getUsername());
		Board result = boardService.saveBoard(board);

		if (result == null) {
			model.addAttribute("message", "글 등록에 실패했습니다.");
			return "redirect:/board/insert";
		} else {
			if (uploadVo.isFileExist()) {

				// 보드에 등록 후 첨부파일 등록
				attachmentService.insertAtt(uploadVo, result);
			}
			// 첨부파일 등록 후
			model.addAttribute("message", "글이 등록되었습니다.");
			return "redirect:/board/list";
		}
	}

	@PutMapping("/edit/{boardNo}")
	@ResponseBody
	public int boardEdit(Model model
						  , @ModelAttribute Board board
						  , @ModelAttribute UploadVo uploadVo) {
		
		//변경할 객체 불러오기
		Board boardSelect = boardService.findById(board.getBoardNo());
		
		// 불러온 객체를 바꾸지 않기 위해 새로운 객체를 만들어 복사하여 실행
		Board boardCopy = new Board();
		BeanUtils.copyProperties(boardSelect, boardCopy);
		
		//복사한 객체에 값채우기
		boardCopy.setBoardTitle(board.getBoardTitle());
		boardCopy.setBoardContent(board.getBoardContent());
		
		Board boardUpdate = boardService.saveBoard(boardCopy);
		
		if(boardUpdate==null) {
			model.addAttribute("message", "글 등록에 실패했습니다.");
			return 0;//"redirect:/board/edit/" + board.getBoardNo();
		}else {
			if(uploadVo.isFileExist()) {
				
				attachmentService.attachmentDelete(boardUpdate);
				
				// 보드에 등록 후 첨부파일 등록
				attachmentService.insertAtt(uploadVo, boardUpdate);
			}else {
				attachmentService.attachmentDelete(boardUpdate);
			}
			
			model.addAttribute("message", "글이 등록되었습니다.");
			return 1;//"redirect:/board/content/" + board.getBoardNo();
		}
	
	}

	// ajax통신으로 get에서 put로 바꿔보자
	@GetMapping("/delete/{boardNo}")
	public String boardDelete(HttpSession session
							, @PathVariable Long boardNo) {

		Board boardSelect = boardService.boardSearch(boardNo);

		Board board = new Board();

		BeanUtils.copyProperties(boardSelect, board);

		board.setStatus("N");

		Board boardDelete = boardService.saveBoard(board);
		
		if (boardDelete == null) {
			session.setAttribute("alertMsg", "글 삭제가 안 되었습니다.");
			return "redirect:/board/content/" + boardNo;
		} else {
			boardReplyService.replyDelete(boardDelete);
			attachmentService.attachmentDelete(boardDelete);
			
			session.setAttribute("alertMsg", "글 삭제가 되었습니다.");
			return "redirect:/board/list";
		}

	}

	@ResponseBody
	@PostMapping("/download")
	public void download(HttpServletResponse response
					   , HttpServletRequest request
					   , @RequestParam Long attNo) throws Exception {
		// ResponseEntity<Resource>
		attachmentService.download(response, request, attNo);
	}

	@ResponseBody
	@PostMapping("/replyInsert")
	public BoardReplyVo replyInsert(@RequestParam Long boardNo
								  , @RequestParam String replyContent
								  , @AuthenticationPrincipal PrincipalDetail principal) {

		BoardReplyVo reply = boardReplyService.replyInsert(boardNo, replyContent, principal);

		return reply;
	}

	@ResponseBody
	@PostMapping("/replyList")
	public List<BoardReplyVo> replyList(@RequestParam Long boardNo) {
		List<BoardReplyVo> replyList = boardReplyService.replyList(boardNo);
		return replyList;
	}

	@ResponseBody
	@PutMapping("/replyDelete")
	public int replyDelete(@RequestParam Long replyNo) {
		int result = boardReplyService.replyDelete(replyNo);
		return result;
	}

	/*
	 * FileSizeLimitExceededException - max file size의 설정값보다 큰 파일이 들어갈 때
	 * SizeLimitExceededException - max request size 의 설정값보다 큰 파일이나 총 파일의 크기가 클 때
	 * 
	 * 두 예외 다 스프링 프레임 워크에서 MaxUploadSizeExceededException 로 변환
	 */
}
