$(function(){
    getJsonData();
});

function getJsonData(){
    $.getJSON("resources/json/bike.json", function(mydata){
	//resources/json/bike.json에서 요청받아온 값 = mydata라는 이름으로 가지고 옴
        $.ajax({
            url: "bike.do",
            method: "post",
            data: {"command": "getdata", "mydata": JSON.stringify(mydata)},
            dataType: "json",
			//data: 보내는 값. datatype: 값을 받는 형식(제이쿼리가 자동으로 제이슨 형태 객체로 바꿔 줌)
            success: function(msg){
			//msg는 json 타입으로 된 객체로 전달됨
                var $tbody = $("tbody");

/*
{"command" : "getdata" , "mydata" : json파일 } 
이렇게 보냈기 때문에 

서버에서

String command = request.getParameter("command");

String data = request.getParameter("mydata");

받을 수 있는 것

*/
                var list = msg.result;
				//console.log(list)
                for (var i = 0; i < list.length; i++){
                    var $tr = $("<tr>");

                    $tr.append($("<td>").append(list[i].name));
                    $tr.append($("<td>").append(list[i].addr));
                    $tr.append($("<td>").append(list[i].latitude));
                    $tr.append($("<td>").append(list[i].longitude));
                    $tr.append($("<td>").append(list[i].bike_count));

                    $tbody.append($tr);
                }
            },
            error: function(){
                alert("통신 실패");
            }
        });
    });
}