package com.boardtest.webapp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.boardtest.webapp.dao.BoardDAO;
import com.boardtest.webapp.vo.BoardVO;
import com.boardtest.webapp.vo.PageVO;

@Service
public class BoardServiceImp implements BoardService {
	@Inject
	BoardDAO dao;
	
	@Override
	public int boardInsert(BoardVO vo) {
		// 글 등록
		return dao.boardInsert(vo);
	}

	@Override
	public List<BoardVO> getList(PageVO pVo) {
		// 글 목록가져오기
		return dao.getList(pVo);
	}

	@Override
	public int getTotalRecord(PageVO pVo) {
		// page용 총 레코드 수
		return dao.getTotalRecord(pVo);
	}

	@Override
	public BoardVO getSelectedRecord(int boardNo) {
		// 글 읽기
		return dao.getSelectedRecord(boardNo);
	}

	@Override
	public String getPassword(int boardNo) {
		// 글 수정 비번 확인
		return dao.getPassword(boardNo);
	}

	@Override
	public int boardEdit(BoardVO vo) {
		// 글 수정하기
		return dao.boardEdit(vo);
	}

	@Override
	public int boardDelete(int boardNo) {
		// 글 삭제
		return dao.boardDelete(boardNo);
	}

	@Override
	public int updateHit(int boardNo) {
		// 조회수 올리기
		return dao.updateHit(boardNo);
	}

	@Override
	public BoardVO getOriInfo(int boardNo) {
		// 원글 정보 가져오기
		return dao.getOriInfo(boardNo);
	}

	@Override
	public int replyInsert(BoardVO vo) {
		// 답글 등록하기
		return dao.replyInsert(vo);
	}

	@Override
	public int indentCount(BoardVO vo) {
		// indent update
		return dao.indentCount(vo);
	}

}
