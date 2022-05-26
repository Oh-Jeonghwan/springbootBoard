'use strict';

let submitFlag = false;
//파일 다운로드를 위한 ajax
let board = {
	download: function(e){
		$.ajax({
			type:"post",
			url: "/board/api/download",
			data: {attNo: e},
			success: function(){
			},
			error: function(e){
				console.log("att 다운로드 실패");
			}
		});
	},
	
	contentEdit:function(e){
		let url = $("#form").attr("action"); 
		let form = $('#form')[0];
		let formData = new FormData(form); //FormData: 폼에 있는 데이터를 들고 오는 듯
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
	},
	
	clickSubmit : function(){
		if(submitCheck()){return;}
		document.getElementById('form').submit();
	},
	
	submitCheck : function(){
		if(submitFlag){
			return submitFlag;
		}else{
			submitFlag = true;
			return false;
		}
	}
}

let index = {
		init:function(){
			$("input[name=upfile]").on("change", () => {
				this.fileSizeCheck(event);
			});
		},
	
		fileSizeCheck:function(event){
			var files = event.target.files;
			
			var maxSize = 20 * 1024 * 1024;
			var perSize = 10 * 1024 * 1024;
			
			var filesSize = 0;
			for(let i=0; i<files.length; i++){
				filesSize += files[i].size
			}
			
			if(filesSize>maxSize){
				alert("파일 총 용량이 20MB를 넘을 수 없습니다.");
				$("input[name=upfile]").val("");
				return false;
			}
			
			for(let i=0; i<files.length; i++){
				var fileSize = files[i].size;
				if(fileSize>perSize){
					alert("파일 하나의 용량이 10MB를 넘을 수 없습니다.");
					$("input[name=upfile]").val("");
					return false;
				}
			}
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

