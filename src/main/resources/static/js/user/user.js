/**
 * 
 */
let index = {
	init: function () {
		$("#btn-save").on("click", () => { // function()가 아닌 ()=>{}를 쓰는 이유는
											// this를 바인딩하기 위해
			this.save();
		});
		
		$("#memberId").on("change", () => {
			this.idCheck2();
		});
		
		$("#memberPwd").on("change", ()=>{
			this.pwdValidate();
		});
		
		$("#pwdCheck").on("change", ()=>{
			this.pwdCheck1();
		});
		
		$("#phone").on("change", ()=>{
			this.phCheck();
		});
		
		$("#email").on("change", ()=>{
			this.emailCheck();
		});
		
		$("#btn-memberEdit").on("click", ()=>{
			this.memberEdit();
		});
	},

	save: function () {

		let data = {
			memberName: $("#memberName").val(),// 이름
			memberId: $("#memberId").val(),// 아이디
			memberPwd: $("#memberPwd").val(),// 비밀번호
			email: $("#email").val(),// 이메일
			phone: $("#phone").val()// 핸드폰
		};

		// ajax 통신을 이용해서 3개의 데이터를 jason으로 변경하여 insert 요청
		// ajax호출시 default가 비동기 호출
		$.ajax({// 회원가입 수행요청
			type: "post",
			url: "/auth/joinProc",
			data: JSON.stringify(data), // http body 데이터
			contentType: "application/json; charset=utf-8", // body 데이터가 어떤
															// 타입인지(MIME)
			dataType: "json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴 것이
								// json이라면 )=>javascript object로 변경

		}).done(function (resp) {// 성공했을 때
			if (resp == null) {
				alert("회원가입이 안 되었습니다.");
				// 회원가입 안 됐을 경우
				location.href="/auth/joinForm";
			}
			else {
				alert("회원가입이 완료되었습니다.");
				location.href = "/";
			}

		}).fail(function (error) {// 실패했을 떄
			alert(JSON.stringify(error));
		});
	},
	
	idCheck2: function(){
		let regExp = /\w{8,15}/;
        let idCheck = $("#memberId").val();

        if (!regExp.test(idCheck)) {
            $("#idResult").text("유효하지 않은 아이디 입니다.");
            $("#idResult").css('color', 'red');
            $("#idResult").css('font-size', '5px');
            $("#memberId").val("");

        } else {
            $.ajax({
                url: "/auth/idCheck",
                data: {
                    id: $("#memberId").val()
                },
                type: "post",
                success: function(result) {
                    if (result == 1) {
                        $("#idResult").text("중복된 아이디 입니다.");
                        $("#idResult").css('color', 'red');
                        $("#idResult").css('font-size', '5px');
                        $("#memberId").val("");
                    } else {
                        $("#idResult").text("사용 가능한 아이디입니다.");
                        $("#idResult").css('color', 'green');
                        $("#idResult").css('font-size', '5px');
                    }

                },
                error: function() {
                    console.log("아이디체크 에러");
                }
            });
        }
	},
	
	pwdValidate: function(){
		 let regExp = /^[a-zA-Z0-9]{8,15}$/;
         let pwdVali = $("#memberPwd").val();

         if (regExp.test(pwdVali)) {
             $("#pwdValid").text("유효한 비밀번호입니다.");
             $("#pwdValid").css('color', 'green');
             $("#pwdValid").css('font-size', '5px');
         } else {
             $("#pwdValid").text("영문, 숫자 포함 8~15자를 입력해주세요.");
             $("#pwdValid").css('color', 'red');
             $("#pwdValid").css('font-size', '5px');
             $("#memberPwd").val("");
         }
	},
	
	pwdCheck1: function(){
		let pwdVali = $("#memberPwd").val();
        let pwdCheck = $("#pwdCheck").val();

        if (pwdVali == pwdCheck) {
            $("#pwdCheck1").text("비밀번호가 일치합니다.");
            $("#pwdCheck1").css('color', 'green');
            $("#pwdCheck1").css('font-size', '5px');
        } else {
            $("#pwdCheck1").text("비밀번호가 다릅니다.");
            $("#pwdCheck1").css('color', 'red');
            $("#pwdCheck1").css('font-size', '5px');
            $("#pwdCheck").val("");
        }
		
	},
	
	phCheck: function(){
        let regExp = /^010[0-9]{3,4}[0-9]{4}$/;
        let phone = $("#phone").val();

        if (regExp.test(phone)) {
            $("#phCheck").text("유효한 전화번호입니다.");
            $("#phCheck").css('color', 'green');
            $("#phCheck").css('font-size', '5px');
        } else {
            $("#phCheck").text("전화번호 형식을 확인해주세요.");
            $("#phCheck").css('color', 'red');
            $("#phCheck").css('font-size', '5px');
            $("#phone").val("");
        }
	},
	
	emailCheck:function(){
        let regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
        let email = $("#email").val();
        	
        if (!regExp.test(email)) {
        	$("#emailCheck").text("email 형식에 맞춰 작성해주세요.");
            $("#emailCheck").css('color', 'red');
            $("#emailCheck").css('font-size', '5px');
            $("#email").val("");
        } else {
            $.ajax({
                url: "/auth/emailCheck",
                data: {
                    email: $("#email").val()
                },
                type: "post",
                success: function(result) {
                    if (result == 1) {
                        $("#emailCheck").text("중복된 email 입니다.");
                        $("#emailCheck").css('color', 'red');
                        $("#emailCheck").css('font-size', '5px');
                        $("#email").val("");
                    } else {
                        $("#emailCheck").text("사용 가능한 email입니다.");
                        $("#emailCheck").css('color', 'green');
                        $("#emailCheck").css('font-size', '5px');
                    }

                },
                error: function() {
                    console.log("email체크 에러");
                }
            });
        }
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
			}
			else {
				alert("회원수정 실패.");
				// 회원가입 안 됐을 경우
			}

		}).fail(function (error) {// 실패했을 떄
			alert(JSON.stringify(error));
		});
		
	}
}
index.init();