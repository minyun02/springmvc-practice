package com.boardtest.webapp;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.boardtest.webapp.service.BoardService;
import com.boardtest.webapp.vo.BoardVO;
import com.boardtest.webapp.vo.PageVO;

@Controller
public class HomeController {
	@Inject
	BoardService boardService;
	
//	@RequestMapping(value = {"/", "list"}, method = RequestMethod.GET)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(PageVO pVo) {
		ModelAndView mav = new ModelAndView();
		
		System.out.println(pVo.getEndPage());
		pVo.setTotalRecord(boardService.getTotalRecord(pVo));
		mav.addObject("totalRecord", pVo.getTotalRecord());
		mav.addObject("list", boardService.getList(pVo));
		mav.addObject("page", pVo);
		mav.setViewName("home");
		
		return mav;
	}
	
}
