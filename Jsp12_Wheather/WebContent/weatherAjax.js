$(function() {
	$("#weaView").click(
			function() {
				var url = "weatherOpen";
				var code = $("#address option:selected").val();
				$.ajax({
					type : "GET",
					url : url + "?code=" + code,
					dataType : "text",
					success : function(data) {
						var temp = $.trim(data);
						var obj = JSON.parse(temp);
						
						$("#pubDate").val(obj.pubDate);
						$("#temp").val(obj.temp);
						$("#x").val(obj.x);
						$("#y").val(obj.y);
						$("#reh").val(obj.reh);
						$("#pop").val(obj.pop);
						$("#wfKor").val(obj.wfKor);

						var weather_condition = obj.wfKor;
						if (weather_condition == "맑음"){
							$("#weather_img").attr("src","/Jsp12_Weather/image/sun.png");
						}else if (weather_condition == "비"){
							$("#weather_img").attr("src","/Jsp12_Weather/image/rain.png");
						}else if (weather_condition == "눈"){
							$("#weather_img").attr("src","/Jsp12_Weather/image/snow.png");
						}else if (weather_condition == "흐림"){
							$("#weather_img").attr("src","/Jsp12_Weather/image/cloud.png");
						}else if (weather_condition == "구름 조금"){
							$("#weather_img").attr("src","/Jsp12_Weather/image/cloud_sun.png");
						}else{
							$("#weather_img").attr("src","/Jsp12_Weather/image/etc.png");
						}
					},
					error : function() {
						alert("정보를 불러오는데 실패하였습니다.");
					}
				});
			});
});
