package com.nmplus.springbootBoard.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

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
					 , HttpServletRequest request
					 , @PageableDefault(size = 10, sort = "boardNo", direction = Sort.Direction.DESC) Pageable pageable
					 , @RequestParam(required = false, defaultValue = "") String condition
					 , @RequestParam(required = false, defaultValue = "") String keyword) {

		Page<Board> boards = boardService.selectList(pageable, condition, keyword); // PageRequest.of(시작 인덱스 0, 보여줄 사이즈
																					// 20)
		int page = boards.getPageable().getPageNumber();

		int totalPages = boards.getTotalPages();
		
		int startPage = Math.max(1, page - 9);
		// 1과 (현재 페이지 - 9) 중 큰 것 반환(현재 페이지가 10을 넘기 전까지 1반환)

		int endPage = Math.min(totalPages, page + 10);
		// 전체 페이지 수와 현재 페이지 + 10 중 작은 것 반환(전체 페이지가 11일 경우 현재 페이지가 2를 넘어가면 전체 페이지 수(11)
		// 반환 )

		// 조회된 게시물이 없을 떄 endPage와 totalPage가 값이 0이 넘어가는 것을 방지하기 위해
		endPage = boardService.zeroToOne(endPage);
		totalPages = boardService.zeroToOne(totalPages);
		
		//한페이지에 나타낼 페이징 시작번호와 끝번호 조정 코드
		if(totalPages<=10) { //총 페이지가 10이하일 때  
			startPage=1;
			endPage=totalPages;
		}else if(page<10) { //총 페이지가 10초과이면서, 현재 페이지가 10미만일 때
			startPage = 1;
			endPage = 10;
		}else if(10<=page && page<totalPages-9) {//총 페이지가 10초과이면서, 현재페이지가 10이상이고, 총 페이지와 현재페이지의 차가 9초과일 때
			startPage = page+1;
			endPage = startPage+9;
		}else if(page>=totalPages-9 || page>totalPages){//총 페이지와 현재 페이지가 9이상 차이나지 않을 때, 오류로 현재페이지가 총 페이지를 초과할 때
			startPage=totalPages-9;
			endPage = totalPages;
		}
		
		//게시물 존재하지 않을 시 메시지 띄워즐 세션을 받아오는 코드
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		
		String alertMsg = null;
		if(inputFlashMap != null) {
			alertMsg = (String)inputFlashMap.get("alertMsg");
		}
		
		model.addAttribute("alertMsg", alertMsg);
		model.addAttribute("page", page);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("boards", boards);
		model.addAttribute("totalPages", totalPages);
		return "board/list";
	}

	@GetMapping("/insert")
	public String insert(Model model, HttpSession session) {
		// 등록을 위해 채울 새 보드 객체를 보내준다.
		model.addAttribute("board", new Board());
		return "board/insertForm";
	}

	@GetMapping("/content/{boardNo}")
	public String contentSearch(Model model
							  , HttpServletRequest request
							  , RedirectAttributes redirect
							  , @PathVariable Long boardNo){
		
		//리다이렉트 되어 보내진 메시지가 있을 때 담아주는 코드
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		
		String alertMsg = null;
		if(inputFlashMap != null) {
			alertMsg = (String)inputFlashMap.get("alertMsg");
		}
		
		//게시글 정보 불러오는 코드
		Board boardContent = boardService.boardSearch(boardNo);
		List<Attachment> attachment = attachmentService.attachSearch(boardContent);
		
		if (boardContent == null || boardContent.getStatus().equals("N")) {
			redirect.addFlashAttribute("alertMsg", "게시물이 존재하지 않습니다.");
			return "redirect:/board/list";
		} else {
			model.addAttribute("alertMsg",alertMsg);
			model.addAttribute("attachment",attachment);
			model.addAttribute("boardContent", boardContent);
			return "board/content";
		}
	}

	// 게시글 수정 폼 띄워주는 메소드
	@GetMapping("/edit/{boardNo}")
	public String boardEdit(Model model
						  , RedirectAttributes redirect
						  , @PathVariable Long boardNo
						  , @AuthenticationPrincipal PrincipalDetail principal) {

		Board boardEdit = boardService.boardSearch(boardNo);

		if (boardEdit.getWriter().equals(principal.getUsername())) {
			model.addAttribute("board", boardEdit);
			return "board/edit";
		} else {
			String msg = "작성자가 아닙니다.";
			redirect.addFlashAttribute("alertMsg", msg);
			return "redirect:/board/content/"+boardNo;
		}

	}

}
