

function getParameterValues(){
	var name = "name=" + encodeURIComponent(document.getElementById("name").value);
	var kor = "kor=" + document.getElementById("kor").value;
	var eng = "eng=" +document.getElementById("eng").value;
	var math = "math=" +document.getElementById("math").value;
	
	return "?" + name + "&" + kor + "&" + eng + "&" + math;
}

function load(){
	var url = "score.do" + getParameterValues();
	
	httpRequest = new XMLHttpRequest();						// server와 통신하는 것을 도와주는 객체. 통칭: XHR
	httpRequest.onreadystatechange=callback;				// 처리할 함수 readystatechange가 on될때마다 callback을 실행할 거다
	//onreadychange = XHM의 진행 상황
	httpRequest.open("GET", url, true);						// true : 비동기 / false : 동기
	httpRequest.send();										// get : send() / post : send("data")
}

function callback(){
	alert("readystate : "+httpRequest.readyState);
	// readystate == 4 : 통신 완료
	if (httpRequest.readyState == 4){
		alert("status : " + httpRequest.status);
		//200 : 통신이 성공적으로 완료
		if (httpRequest.status == 200){
			
			// responseText : 요청 후 응답받은 문자열
			var obj = JSON.parse(httpRequest.responseText);
			document.getElementById("result").innerHTML = decodeURIComponent(obj.name) + "의 총점 : " + obj.sum +
			"\n평균 : " + obj.avg;
			
		}else{
			alert("통신 실패");
		}
	}
}



/*
	XMLHttpRequest : javascript object. http를 통한 데이터 송수신 지원
	
	<onreadystatechange>
	-readystate
	 0: uninitialized - 실행(load)되지 않음
	 1: loading - 로드 중
	 2:	loaded - 로드 됨
	 3: interactive - 통신 됨
	 4: complete - 통신 완료

	-status
	 200: 성공
	 400: bad request
	 401: unauthorized
	 403: forbidden
	 404: not found
	 415: unsupported media type
	 500: internal server error

	**
	encodeURIComponent : 모든 문자를 인코딩 (UTF-8)
	URI로 데이터를 전달하기 위해서 문자열을 인코딩.
	HTTP로 URL 값을 전달할때 오로지 영문자와 숫자만으로 전달한다면 인코딩 디코딩이 필요 없을 것이나 실제에 있어서는 다양한 특수문자와 한글 등이 
	URL 값에 포함되어 전달되는데 이때 제대로 인식을 못해서 404 Not found 에러가 발생하거나 잘못된 값이 전달되는 경우가 발생할수 있다.

	decodeURIComponent: UTF-8에서 다시 원래 글자로.
	encodeURIComponent로 이스케이핑 된 문자열을 정상적인 문자열로 되돌려주는 역할을 한다.
	
	encodeURI : 주소줄에서 사용되는 특수문자는 제외하고 인코딩
	
	JSON.parse : json 형태의 문자열을 json 객체로 변환 (string -> json object)
	JSON 문자열의 구문을 분석하고, 그 결과에서 JavaScript 값이나 객체를 
	JSON.stringify : javascript 객체 (json 형태로 변환할 수 있는)를 json 형태의 문자열로 변환(object -> json string)
	json 객체를 String 객체로 변환

*/