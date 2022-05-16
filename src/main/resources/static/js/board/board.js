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
			success: function(f){
				alert(f);
			},
			error: function(){
				console.log("att 다운로드 실패");
			}
		});
	}
	
}

