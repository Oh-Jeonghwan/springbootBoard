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
	
	contentEdit:function(e){
		let url = $("#form").attr("action"); 
		let form = $('#form')[0]; 
		let formData = new FormData(form);
		$.ajax({ 
			url: url, 
			type: 'put', 
			data: formData,
			//contentType: "application/json; charset=utf-8", // body 데이터가 어떤
															// 타입인지(MIME)
			//dataType: "json",
			success: function (data) { 
				if(data>0){
					alert("글이 수정 되었습니다.");
					location.href="/board/content/"+e;
				} 
				else{
					alert("글을 수정하지 못 했습니다.");
					location.href="/board/edit/"+e;
				}
			}, 
			error: function (data) { 
				console.log(data); 
			}, 
			cache: false, 
			contentType: false, 
			processData: false 
		});
	}
	
}

