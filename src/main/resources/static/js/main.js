function editButton(button){
	const buttonId = $(button).attr("id");
	
	var urlStr
	var idKey;
	var nameKey;
	if( buttonId.match(/medicine/i)){
		urlStr = "/medicine/edit"
		idKey = "medicineId";
		nameKey = "medicineName";
	} else {
		urlStr = "/bodyparts/edit"
		idKey = "bodyPartsId";
		nameKey = "bodyPartsName";
	}
	
	var idValue = $(button).parent().siblings().get(1).value;
	var nameValue = $(button).parent().siblings().get(0).value;
	
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
      xhr.setRequestHeader(header, token);
    });
    
    var data = {}
    data[idKey] = idValue;
    data[nameKey] = nameValue;
    
    try {
    	$.ajax({
    		url : urlStr,
    		type : "POST",
    		contentType: "application/json; charset=utf-8",
    		dataType : "json",
    		data : JSON.stringify(data)
    	})
    	.done(function(data){
    		$("#editResult").text(data.resultMessage);
    		$("#editResultModal").modal("show");
    	})	
    	.fail(function(data){
    		console.log(data);
    	});
	} catch (e) {
		console.log(e);
	}
}

function addInput(type) {
	const maxCount = 10;
	const inputCount = $('#' + type + ' .unit').length;
	if (inputCount < maxCount) {
		var element = $('#' + type + ' .unit:last-child').clone();
		// ナンバリングをインクリメントする
		var numberList = element[0].querySelectorAll('#basic-addon');
		var i = inputCount;
		numberList.forEach(function(elem){
			elem.textContent = ++i;
		});
		// input要素をインクリメントする
		var inputList = element[0].querySelectorAll('input');
		inputList.forEach(function(elem){
			elem.value = "";
			var nextName = $(elem).attr("name").replace(/\d/, inputCount);
			$(elem).attr("name", nextName);
			if (!$(elem).attr("name").includes("Id")){
				$(elem).attr("value", i);
			}
		});
		
		$('#' + type + ' .unit').parent().append(element);
	}
}

function deleteInput(type) {
	const minCount = 1;
	const inputCount = $('#' + type + ' .unit').length;
	
	if (inputCount > minCount){
		$('#' + type + ' .unit:last-child').remove();
	}
}

function loginWithTrialUser(form) {
    form["email"].value = "test@gmail.com";
    form["password"].value = "Test1234";
    form.submit();
}

$(function(){
	$(document).ready(function(){
	    $("#upload-images").on('change', function(e){
	            var tgt = e.target || window.event.srcElement,
	                files = tgt.files;
	            
	            if (FileReader && files && files.length) {
	                for (var i = 0; i < files.length; i++)
	                    {
	                        (function(){
	                            var fr = new FileReader();
	                            fr.onload = function () {
	                                $("#preview-img").append("<img class='img-fluid img-thumbnail' src='"+fr.result+"'></img>");
	                            }
	                            fr.readAsDataURL(files[i]);
	                        })();
	                    }
	            }
	    });
	});
});