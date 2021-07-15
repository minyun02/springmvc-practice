package com.boardtest.webapp.controller;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
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
	
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
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
	
	//답글 쓰기 폼 
	@RequestMapping("/replyWrite")
	public ModelAndView replyWrite(Integer boardNo) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("boardNo", boardNo);
		mav.setViewName("/board/replyWrite");
		return mav;
	}
	
	//답글 쓰기
	@RequestMapping(value =  "/replyWriteOk", method = RequestMethod.POST)
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
	public ModelAndView replyWriteOk(BoardVO vo) {
		//트랜잭션 객체 생성
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
		
		TransactionStatus status = transactionManager.getTransaction(def);
		
		ModelAndView mav = new ModelAndView();
		System.out.println(vo.getContent());
		System.out.println(vo.getBoardNo()+"AASDFSAF");
		try {
			//1. 원글의 정보 가져오기
			BoardVO oriVo = boardService.getOriInfo(vo.getBoardNo());
			//2. 해당 답글에 정보 추가
			int indentCnt = boardService.indentCount(oriVo);
			//2-1 원글번호를 그룹번호 넣어줌
			vo.setGroupNo(oriVo.getGroupNo());
			//2-2 그룹순서 정해주기
			vo.setGroupOrder(oriVo.getGroupOrder()+1);
			//2-3 들여쓰기 정해주기
			vo.setIndent(oriVo.getIndent()+1);
			
			//답글 등록 메서드
			int replyInsert = boardService.replyInsert(vo);
			if(replyInsert>0) {//등록 성공
				mav.setViewName("redirect:/");
				transactionManager.commit(status);
			}else {//실패
				mav.setViewName("redirect:replyWrite");
				transactionManager.rollback(status);
			}
		}catch(Exception e) {
			mav.addObject("boardNo", vo.getBoardNo());
			mav.setViewName("redirect:replyWrite");
			System.out.println("답글 쓰기 에러 --- 롤백");
			e.printStackTrace();
		}
		return mav;
	}
}
