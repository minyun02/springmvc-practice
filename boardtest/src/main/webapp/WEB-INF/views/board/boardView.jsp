<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 내용보기</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
<script>
	function boardEdit(){
		var locationUrl = "/webapp/boardEdit?boardNo=${vo.boardNo}";
		getPassword(locationUrl)
	}
	
	function boardDelete(){
		var locationUrl = "/webapp/boardDelete?boardNo=${vo.boardNo}";
		getPassword(locationUrl)
	}
	
	function getPassword(locationUrl){
		var pwd = prompt("비밀번호를 입력하세요");
		
		if(pwd != null){
			checkPassword(pwd, locationUrl);
		}
	}
	
	function checkPassword(pwd, locationUrl){
		$.ajax({
			url : "/webapp/editCheck",
			data : "boardNo=${vo.boardNo}&password="+pwd,
			success : function(result){
				if(result==0){
					alert("비밀번호를 다시 확인해주세요.");		
					getPassword(locationUrl);
				}else if(result==1){
					location.href = locationUrl;
				}
			}, error : function(){
				
			}
		});
	}
	
	
	$(function(){
		//에디터 불러오기
		$("#content").summernote({
  			width : 1000,
  			height : 300,
  			toolbar : []
  		});
		$("#content").summernote('disable')
	})
</script>
<style type="text/css">
	ul, li{
		margin: 0;
		padding: 0;
		list-style: none;
	}
	#subjectView:focus {
	    outline: none !important;
	    border: none;
/* 	    box-shadow: 0 0 10px #719ECE; */
	}
</style>
</head>
<body>
	<div class="container">
		<h3><a href="/webapp/">목록</a></h3>
		<ul id="viewDetail">
			<li style="float:left; margin-right: 50px;">글번호 : <span>${vo.boardNo}</span></li>
			<li style="float:left; margin-right: 50px;">조회수 : <span>${vo.hit}</span></li>
			<li>작성자 : <span><c:out value="${vo.userid}"></c:out></span><li>
<%-- 			<li>파일 : ${vo.filename}</li> --%>
			<li>제목</li>	
			<li> <input id="subjectView" type="text" value="<c:out value="${vo.subject}"></c:out>" style="width: 1000px; height: 30px; line-height: 30px; border:1px solid; word-break:break-all;" readonly></li>
			<li>내용</li>
			<li>
			<textarea id="content" style="width: 1000px; height: 300px; border:1px solid; word-break:break-all; overflow: auto;"><c:out value="${vo.content}"></c:out></textarea>
<%-- 			<c:out value="${vo.content}"></c:out> --%>
			</li>
		</ul>
		<a href="javascript:boardEdit()">수정</a>
		<a href="javascript:boardDelete()">삭제</a>
		<a href="replyWrite?boardNo=${vo.boardNo}">답글</a><br>
		
<!-- 		<form action=""> -->
<!-- 			<textarea rows="4" cols="70"></textarea> -->
<!-- 			<input type="submit" value="댓글작성"> -->
<!-- 		</form> -->
	</div>
</body>
</html>