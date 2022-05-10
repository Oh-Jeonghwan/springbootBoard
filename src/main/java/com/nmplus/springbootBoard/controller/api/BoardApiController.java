package com.nmplus.springbootBoard.controller.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nmplus.springbootBoard.config.auth.PrincipalDetail;
import com.nmplus.springbootBoard.service.AttachmentService;
import com.nmplus.springbootBoard.service.BoardService;
import com.nmplus.springbootBoard.vo.Attachment;
import com.nmplus.springbootBoard.vo.Board;
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

	@PostMapping("/insert")
	public String insert(HttpSession session, @ModelAttribute Board board,
			@AuthenticationPrincipal PrincipalDetail principal, @ModelAttribute UploadVo uploadVo) {
		log.debug("몇개?"+uploadVo.getUpfile().size());
		board.setWriter(principal.getUsername());
		Board result = boardService.saveBoard(board);

		if (result == null) {
			session.setAttribute("alertMsg", "글 등록에 실패했습니다.");
			return "redirect:/board/insert";
		} else {
			// 보드에 등록 후 첨부파일 등록
			attachmentService.insertAtt(uploadVo, result);

			// 첨부파일 등록 후
			session.setAttribute("alertMsg", "글이 등록되었습니다.");
			return "redirect:/board/list";
		}
	}

	// 나중에 ajax로 post가 아닌 put메소드 사용으로 바꾼다.
	@PostMapping("/edit/put/{boardNo}")
	public String boardEdit(HttpSession session, @PathVariable Long boardNo, @RequestParam String boardTitle,
			@RequestParam String boardContent) {

		Board boardSelect = boardService.boardSearch(boardNo);

		// 불러온 객체를 바꾸지 않기 위해 새로운 객체를 만들어 복사하여 실행
		Board board = new Board();

		BeanUtils.copyProperties(boardSelect, board);

		board.setBoardTitle(boardTitle);
		board.setBoardContent(boardContent);

		Board boardEdit = boardService.saveBoard(board);

		if (boardEdit == null) {
			session.setAttribute("alertMsg", "글 수정이 안 되었습니다.");
			return "redirect:/board/edit/" + boardNo;
		} else {
			session.setAttribute("alertMsg", "글 수정이 되었습니다.");
			return "redirect:/board/content/" + boardNo;
		}

	}

	// ajax통신으로 get에서 put로 바꿔보자
	@GetMapping("/delete/{boardNo}")
	public String boardDelete(HttpSession session, @PathVariable Long boardNo) {

		Board boardSelect = boardService.boardSearch(boardNo);

		Board board = new Board();

		BeanUtils.copyProperties(boardSelect, board);

		board.setStatus("N");

		Board boardDelete = boardService.saveBoard(board);

		if (boardDelete == null) {
			session.setAttribute("alertMsg", "글 삭제가 안 되었습니다.");
			return "redirect:/board/content/" + boardNo;
		} else {
			log.debug("변경될까? 안 될까?: " + boardDelete.toString());
			session.setAttribute("alertMsg", "글 삭제가 되었습니다.");
			return "redirect:/board/list";
		}

	}

}
