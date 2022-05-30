'use strict';

let boardReply = {
	replyPost: function (e) {
		//함수의 매개변수로 문자나 숫자는 받아주지만 객체 등 다른 형태의 자료는 못 받아준다.
		let replyContent = $("#replyContent").val();
		//텍스트area 내의 공백(\s) 또는(|) 문자열의 모든 문자(g)
		//대소문자를 무시(i)하고 공백("")을 제거하여 그 길이가 0이면 
		if (replyContent.replace(/\s| /gi, "").length == 0) {
			alert("댓글을 입력해주세요.");
			$("#replyContent").focus();
		}
		else {
			$.ajax({
				type: "post",
				url: "/board/api/replyPost",
				data:JSON.stringify({
					replyContent:replyContent,
					boardNo: e
				}),
				contentType: 'application/json; charset=UTF-8',
				success: function (result) {
					$("#replyContent").val("");
					if (result != null) {
						$.ajax({
							type: "get",
							url: "/board/api/replyList",
							data: {
								boardNo: e
							},
							contentType: 'application/json',
							success: function (list) {
								if (list != null) {
									let result = "";
									for (let i in list) {//for in 문
										let replyNo = list[i].replyNo;
										result += "<tr>"
											+ "<td>" + list[i].replyWriter + "</td>"
											+ "<td>" + list[i].replyContent + "</td>"
											+ "<td style='width:100px;'>"
												+ "<button type='button' onclick='boardReply.replyDelete("+replyNo+", "+ e+")'>"
												+ "삭제</button>"
											+ "</td>"
											+ "<td>" + list[i].enrollDate + "</td>"
											+ "</tr>";
									}
									//tbody를 골라 innerHtml로 넣어주기
									$("#replyTbody").html(result);
								}
							},
							error: function () {
								console.log("댓글 등록 오류");
							}

						});

					}
				},
				error: function () {
					console.log("댓글 등록 오류");
				}

			});
		}

	},
	
	replyList: function (e) {
		$.ajax({
			type: "get",
			url: "/board/api/replyList",
			data: {
				boardNo: e
			},
			contentType: 'application/json',
			success: function (list) {
				if (list != null) {
					let result = "";
					for (let i in list) {//for in 문
						let replyNo = list[i].replyNo;
						result += "<tr>"
							+ "<td>" + list[i].replyWriter + "</td>"
							+ "<td>" + list[i].replyContent + "</td>"
							+ "<td style='width:100px;'>"
								+ "<button type='button' onclick='boardReply.replyDelete("+replyNo+", "+ e+")'>"
								+ "삭제</button>"
							+ "</td>"
							+ "<td>" + list[i].enrollDate + "</td>"
							+ "</tr>";
					}
					//tbody를 골라 innerHtml로 넣어주기
					$("#replyTbody").html(result);
				}
			},
			error: function () {
				console.log("댓글 등록 오류");
			}

		});

	},
	
	replyDelete: function(e, f){
		$.ajax({
			type:"get",
			url: "/board/api/replyDelete",
			data:{replyNo: e},
			success: function(data){
				if(data>0){
					alert("댓글이 삭제되었습니다.");
					
					$.ajax({
						type: "get",
						url: "/board/api/replyList",
						data: {
							boardNo: f
						},
						contentType: 'application/json',
						success: function (list) {
							if (list != null) {
								let result = "";
								for (let i in list) {//for in 문
									let replyNo = list[i].replyNo;
									result += "<tr>"
										+ "<td>" + list[i].replyWriter + "</td>"
										+ "<td>" + list[i].replyContent + "</td>"
										+ "<td style='width:100px;'>"
											+ "<button type='button' onclick='boardReply.replyDelete("+replyNo+", "+ f+")'>"
											+ "삭제</button>"
										+ "</td>"
										+ "<td>" + list[i].enrollDate + "</td>"
										+ "</tr>";
								}
								//tbody를 골라 innerHtml로 넣어주기
								$("#replyTbody").html(result);
							}
						},
						error: function () {
							console.log("댓글 삭제 후 리스트 불러오기 오류");
						}

					});
				}else if(data==0){
					alert("삭제가 안 되었습니다.");
				}else{
					alert("댓글의 작성자가 아닙니다.");
				}
			},
			error:function(){
				console.log("댓글 삭제 에러");
			}
		});
	}
}

$(function () {
    var token = $("meta[name='_csrf']").attr('content');
    var header = $("meta[name='_csrf_header']").attr('content');
    if(token && header) {
        $(document).ajaxSend(function(event, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    }
});
