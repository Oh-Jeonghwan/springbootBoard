/**
 * 
 */
let index={
	init: function(){
		$("#download").on("clcik", () => {
			this.download();
		});
			
	},
	
	download: function(){
		
		let boardNo = {${boardContent.boardNo}};
		
		$.ajax({
			url: "/board/api/download",
			data: {boardNo: boardNo},
			type: "GET"
			success:function(){
				console.log("다운됨");
			},
			error: function(){
				console.log("ajax통신 실패");
			}
		});
		
	}
	
}


index.init();