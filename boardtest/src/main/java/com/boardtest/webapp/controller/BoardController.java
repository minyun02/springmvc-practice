package com.boardtest.webapp.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.boardtest.webapp.service.BoardService;
import com.boardtest.webapp.vo.BoardVO;

@Controller
public class BoardController {
	
	@Inject
	BoardService boardService;
	
	@RequestMapping("/boardView")
	public ModelAndView boardView(int boardNo) {
		ModelAndView mav = new ModelAndView();
		boardService.updateHit(boardNo);
		
		BoardVO vo = boardService.getSelectedRecord(boardNo);
		System.out.println(vo.getSubject()+"???");
		
		
		
		mav.addObject("vo", boardService.getSelectedRecord(boardNo));
		mav.setViewName("/board/boardView");
		return mav;
	}
	
	@RequestMapping("/boardWrite")
	public String boardWrite() {
		return "/board/boardWrite";
	}
	
	@RequestMapping(value="/boardWriteOk", method = RequestMethod.POST)
	public ModelAndView boardWriteOk(BoardVO vo) {
		ModelAndView mav = new ModelAndView();
		
		int result = boardService.boardInsert(vo);
		if(result > 0) { //글 등록 성공
			mav.setViewName("redirect:/");
		}else {
			mav.setViewName("redirect:boardWrite");
		}
		return mav;
	}
	
	@RequestMapping("/editCheck")
	@ResponseBody
	public int editCheck(int boardNo, String password) {
		int result = 0;
		String originalPwd = boardService.getPassword(boardNo);
		if(originalPwd.equals(password)) {
			result = 1;
		}
		return result;
	}
	
	@RequestMapping("/boardEdit")
	public ModelAndView boardEdit(int boardNo) {
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("vo", boardService.getSelectedRecord(boardNo));
		mav.setViewName("/board/boardEdit");
		return mav;
	}
	
	@RequestMapping(value="/boardEditOk", method = RequestMethod.POST)
	public ModelAndView boardEditOk(BoardVO vo) {
		ModelAndView mav = new ModelAndView();
		int updateResult = boardService.boardEdit(vo);
		if(updateResult > 0) {
			mav.setViewName("redirect:/");
		}else {
			mav.addObject("boardNo", vo.getBoardNo());
			mav.setViewName("redirect:boardEdit");
		}
		return mav;
	}
	
	@RequestMapping("/boardDelete")
	public ModelAndView boardDelete(int boardNo) {
		ModelAndView mav = new ModelAndView();
		int deleteResult = boardService.boardDelete(boardNo);
		if(deleteResult>0) {
			mav.setViewName("redirect:/");
		}else {
			mav.addObject("boardNo", boardNo);
			mav.setViewName("redirect:boardView");
		}
		return mav;
	}
}
