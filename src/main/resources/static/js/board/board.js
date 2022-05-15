/**
 * 
 */
//파일 다운로드를 위한 ajax
let board = {
	download: function(e){
		$.ajax({
			type:"post",
			url: "/board/api/download",
			data: {attNo: e},
			success: function(){
				
			},
			error: function(){
				console.log("att 다운로드 실패");
			}
		});
	},
	
	replyInsert: function(e){
		//함수의 매개변수로 문자나 숫자는 받아주지만 객체 등 다른 형태의 자료는 못 받아준다.
		let replyContent = $("#replyContent").val()
		
		//텍스트area 내의 공백(\s) 또는(|) 문자열의 모든 문자(g)
		//대소문자를 무시(i)하고 공백("")을 제거하여 그 길이가 0이면 
		if(replyContent.replace(/\s| /gi,"").length == 0){
			alert("댓글을 입력해주세요.");
			$("#replyContent").focus();
		}
		else{
			$.ajax({
				type: "post",
				url: "/board/api/replyInsert",
				data: {
					replyContent: replyContent,
					boardNo: e
				},
				success:function(result){
					$("#replyContent").val("");
					alert("댓글이 등록되었습니다.");
					
					if(result!=null){
        				
        			}
				},
				error: function(){
					console.log("댓글 등록 오류");
				}
				
			});
		}
		
	}
	
}

let index={
	init: function(){	
	}
	
}

index.init();