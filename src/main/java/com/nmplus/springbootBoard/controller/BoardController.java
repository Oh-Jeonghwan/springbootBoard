package com.nmplus.springbootBoard.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nmplus.springbootBoard.config.auth.PrincipalDetail;
import com.nmplus.springbootBoard.service.AttachmentService;
import com.nmplus.springbootBoard.service.BoardService;
import com.nmplus.springbootBoard.vo.Attachment;
import com.nmplus.springbootBoard.vo.Board;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private AttachmentService attachmentService;

	// 리스트까지는 로그인 인증 안 해도 볼 수 있도록 허용
	@GetMapping("/list")
	public String list(Model model
					 , @PageableDefault(size = 10, sort = "boardNo", direction = Sort.Direction.DESC) Pageable pageable
					 , @RequestParam(required = false, defaultValue = "") String condition
					 , @RequestParam(required = false, defaultValue = "") String keyword) {

		Page<Board> boards = boardService.selectList(pageable, condition, keyword); // PageRequest.of(시작 인덱스 0, 보여줄 사이즈
																					// 20)

		int startPage = Math.max(1, boards.getPageable().getPageNumber() - 9);
		// 1과 (현재 페이지 - 9) 중 큰 것 반환(현재 페이지가 10을 넘기 전까지 1반환)

		int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 10);
		// 전체 페이지 수와 현재 페이지 + 10 중 작은 것 반환(전체 페이지가 11일 경우 현재 페이지가 2를 넘어가면 전체 페이지 수(11)
		// 반환 )

		int page = boards.getPageable().getPageNumber();

		int totalPages = boards.getTotalPages();

		// endPage와 totalPage가 값이 0이 넘어가는 것을 방지하기 위해
		endPage = boardService.zeroToOne(endPage);
		totalPages = boardService.zeroToOne(totalPages);

		model.addAttribute("page", page);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("boards", boards);
		model.addAttribute("totalPages", totalPages);
		return "board/list";
	}

	@GetMapping("/insert")
	public String insert(Model model) {
		// 등록을 위해 채울 새 보드 객체를 보내준다.
		model.addAttribute("board", new Board());
		return "board/insertForm";
	}

	@GetMapping("/content/{boardNo}")
	public String contentSearch(Model model
							  , @PathVariable Long boardNo) {

		Board boardContent = boardService.boardSearch(boardNo);
		List<Attachment> attachment = attachmentService.attachSearch(boardContent);

		if (boardContent == null || boardContent.getStatus().equals("N")) {
			model.addAttribute("alertMsg", "게시물이 존재하지 않습니다.");
			return "redirect:/board/list";
		} else {
			model.addAttribute("attachment",attachment);
			model.addAttribute("boardContent", boardContent);
			return "board/content";
		}
	}

	// 게시글 수정 폼 띄워주는 메소드
	@GetMapping("/edit/{boardNo}")
	public String boardEdit(Model model
						  , HttpSession session
						  , @PathVariable Long boardNo
						  , @AuthenticationPrincipal PrincipalDetail principal) {

		Board boardEdit = boardService.boardSearch(boardNo);

		if (boardEdit.getWriter().equals(principal.getUsername())) {
			model.addAttribute("board", boardEdit);
			return "board/edit";
		} else {
			session.setAttribute("alertMsg", "글쓴이가 아닙니다.");
			return "redirect:/board/content/"+boardNo;
		}

	}

}
