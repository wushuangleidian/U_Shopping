//判断是否验证通过的变量
var hkrel = false;

$(function() {
	//判断鼠标是否按下
	var isDown = false;
	var kuai = $("#kuai");
	var green = $("#green");

	//滑块按下事件
	kuai.mousedown(function(e) {
		kuai.css({
			"border-color": "#7BBB55",
			"color": "#7BBB55"
		});
		isDown = true;
		//鼠标按下时的x位置
		var x = e.pageX;
		var hkx = kuai.offset().left;
		//鼠标移动事件
		$(document).mousemove(function(e) {
			if(isDown && !hkrel) {
				//鼠标移动的偏移量
				var left = e.pageX - x;
				kuai.css({
					"left": left
				});
				green.css({
					"width": left
				});
				//判断滑块划的位置
				var y = kuai.offset().left - hkx;
				if(y >= 200) {
					isDown = false;
					kuai.css({
						"left": 200
					});
					green.css({
						"width": "100%"
					});
					green.text("验证成功");
					kuai.text("√");
					hkrel = true;
				}
			}
		});
	});
	//鼠标松开事件
	$(document).mouseup(function() {
		$(this).css({
			"border-color": "gray",
			"color": "gray"
		});
		isDown = false;
		if(!hkrel) {
			kuai.css({
				"left": "0px",
				"border-color": "gray",
				"color": "gray"
			});
			green.css({
				"width": "20px"
			});
		}
	});
});
