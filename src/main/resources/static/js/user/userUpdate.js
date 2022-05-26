/**
 * 
 */
let index = {
	init: function(){
		$("#btn-memberUpdate").on("click", ()=>{
			let form = document.querySelector("#updateForm");
            if (form.checkValidity() == false) {
                console.log("회원수정 안됨")
            } else {						
				this.memberEdit();
			}
		});
	},
	
	memberEdit:function(){
		let data = {
			email: $("#email").val(),// 이메일
			memberPwd: $("#memberPwd").val(),
			phone: $("#phone").val()// 핸드폰
		};
		// ajax 통신을 이용해서 3개의 데이터를 jason으로 변경하여 insert 요청
		// ajax호출시 default가 비동기 호출
		$.ajax({// 회원가입 수행요청
			type: "put",
			url: "/user/memberEdit",
			data: JSON.stringify(data), // http body 데이터
			contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MIME)
			dataType: "json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열
							//(생긴 것이 json이라면 )=>javascript object로 변경
		}).done(function (resp) {// 성공했을 때
			console.log(resp);
			if (resp == 1) {
				alert("회원수정 완료.");
				location.href = "/";
			}
			else {
				alert("회원수정 실패.");
				// 회원가입 안 됐을 경우
				location.href = "/user/info";
			}

		}).fail(function (error) {// 실패했을 떄
			alert(JSON.stringify(error));
		});
		
	}
		
}

index.init();

$(function () {
    var token = $("meta[name='_csrf']").attr('content');
    var header = $("meta[name='_csrf_header']").attr('content');
    if(token && header) {
        $(document).ajaxSend(function(event, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    }
});