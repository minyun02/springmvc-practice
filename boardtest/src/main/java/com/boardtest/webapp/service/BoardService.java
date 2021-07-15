package com.boardtest.webapp.service;

import java.util.List;

import com.boardtest.webapp.vo.BoardVO;
import com.boardtest.webapp.vo.PageVO;

public interface BoardService {
	public int boardInsert(BoardVO vo);
	public List<BoardVO> getList(PageVO pVo);
	public int getTotalRecord(PageVO pVo);
	public BoardVO getSelectedRecord(int boardNo);
	public String getPassword(int boardNo);
	public int boardEdit(BoardVO vo);
	public int boardDelete(int boardNo);
	public int updateHit(int boardNo);
	
	public BoardVO getOriInfo(int boardNo);
	public int replyInsert(BoardVO vo);
	public int indentCount(BoardVO vo);
}
