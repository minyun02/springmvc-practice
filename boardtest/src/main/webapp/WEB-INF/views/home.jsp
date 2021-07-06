<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>
<script src="https://cdn.ckeditor.com/4.16.0/standard/ckeditor.js"></script>
<script>
	$(function(){
		if(${page.totalRecord == 0}){
			alert("검색 결과가 없습니다.");			
		}
	});
</script>
<style>
	/* ul, li 초기화 */
	ul, li{
		margin: 0;
		padding: 0;
		list-style: none;
	}
	#searchDiv, #boardList, #pagination{
		width: 1000px;
		margin: 0 auto;
	}
	#searchDiv{
		margin-bottom: 20px;
	}
	#searchForm{
		float: right;
	}
	#boardList{
		height: 300px;
		text-align: center;
	}
	/* 게시판 요소 정렬 */
	#boardList li{
		float: left;
		width: 10%;
		height: 40px;
		line-height: 40px;
		border-bottom: 1px solid lightblue;
	}
	#boardList li:nth-child(5n+2){
		width: 50%;
	}
/* 	#boardList li:nth-child(5n+3){ */
/* 		width: 12%; */
/* 	} */
	#boardList li:nth-child(5n+5){
		width: 15%;
	}
	.on{
		font-weight: bold;
	}
	.wordcut{
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		text-align: left;
	}
	#selectBox{
		height: 30px;
	}
</style>
</head>
<body>
	<div class="container">
		<div id="searchDiv">
			<h1>게시판</h1>
			
			<a href="boardWrite">글쓰기</a>
			<a href="/webapp/">전체목록</a>
			<form id="searchForm" action="/webapp/">
				<select id="selectBox" name="searchKey">
					<option value="subject">제목</option>
					<option value="content">글내용</option>
					<option value="userid">작성자</option>
				</select>
				<input type="text" name="searchWord">
				<input type="submit" value="검색">
			</form>
		</div>
		<ul id="boardList">
			<li>번호</li>
			<li>제목</li>
			<li>글쓴이</li>
			<li>조회수</li>
			<li>등록일</li>
			
<%-- 											총 레코드 수 - ((현재 페이지-1)* 한 페이지 레코드 ) --%>
			<c:set var="recordNum" value="${totalRecord - ((page.currentPageNum-1) * page.onePageRecord)}"/>
			<c:forEach var="vo" items="${list}">
				<li>${recordNum}</li>
<%-- 				<li class="wordcut"><pre><a href="boardView?boardNo=${vo.boardNo}">${vo.subject}</a></pre></li> --%>
				<li class="wordcut"><a style="white-space: pre" href="boardView?boardNo=${vo.boardNo}"> <c:out value="${vo.subject}" escapeXml="true"></c:out></a></li>
				<li><c:out value="${vo.userid}"></c:out></li>
				<li>${vo.hit}</li>
				<li>${vo.writedate}</li>
				<c:set var="recordNum" value="${recordNum-1}"/>
			</c:forEach>	
			
		</ul>
		<div id="pagination">
			<c:if test="${page.totalRecord != null && page.totalRecord != ''}">
				<c:if test="${page.currentPageNum!=1}">
					<a href="/webapp/?currnetPageNum=1<c:if test="${page.searchWord != null && page.searchWord != ''}">&searchKey=${page.searchKey}&searchWord=${page.searchWord}</c:if>">첫페이지</a>
				</c:if>
					
				<c:if test="${page.startPageNum!=1}">
					<a href="/webapp/?currentPageNum=${page.startPageNum-1}<c:if test="${page.searchWord != null && page.searchWord != ''}">&searchKey=${page.searchKey}&searchWord=${page.searchWord}</c:if>">&lt;</a>
				</c:if>
			</c:if>
			<c:forEach var="p" begin="${page.startPageNum}" end="${page.startPageNum+page.onePageNum-1}">
				<c:if test="${p<=page.totalPage}">
					<c:if test="${p==page.currentPageNum}">
						<a class="on" href="/webapp/?currentPageNum=${p}<c:if test="${page.searchWord != null && page.searchWord != ''}">&searchKey=${page.searchKey}&searchWord=${page.searchWord}</c:if>">${p}</a>
					</c:if>
					<c:if test="${p!=page.currentPageNum}">
						<a href="/webapp/?currentPageNum=${p}<c:if test="${page.searchWord != null && page.searchWord != ''}">&searchKey=${page.searchKey}&searchWord=${page.searchWord}</c:if>">${p}</a>
					</c:if>
				</c:if>
			</c:forEach>
			
			<c:if test="${page.totalRecord != null && page.totalRecord != ''}">
				<c:if test="${page.totalPage > page.endPage}">
					<a href="/webapp/?currentPageNum=${page.endPage+1}<c:if test="${page.searchWord != null && page.searchWord != ''}">&searchKey=${page.searchKey}&searchWord=${page.searchWord}</c:if>">&gt;</a>
				</c:if>
				
				<c:if test="${page.currentPageNum != page.totalPage}">
					<a href="/webapp/?currentPageNum=${page.totalPage}<c:if test="${page.searchWord != null && page.searchWord != ''}">&searchKey=${page.searchKey}&searchWord=${page.searchWord}</c:if>">마지막페이지</a>
				</c:if>
			</c:if>			
		</div>
	</div>
</body>
</html>
