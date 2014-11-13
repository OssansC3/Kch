var endpoint = 'http://' + location.host + ':8080/axis2/services/GetKchController';
var check = 'http://' + location.host + ':8080/axis2/services/GetKchService';
var srcH = '<img src="images/';
var srcT = '" width="500" height="707"/>';
var userName = '';

$(function() {
	var userId = getUrlVars()["userId"];
	if (userId == null) {
		$('#message').text("ユーザーIDが指定されていません");
		$('#kchImage').append(srcH + 'error.jpg' + srcT);
		$('#line_chart').remove();
		$('#gauge_chart').remove();
	} else {
		isRegistered(userId);
	}

});

function isRegistered(userId) {
	$.ajax({
		type : 'GET',
		async : false,
		url : check + '/isRegistered',
		data : {
			userId : userId,
		},
		success : function(xml) {
			if ($('return', xml).text() == 'true') {
				setName(userId);
				isAnalysed(userId);
			} else {
				$('#message').text('ユーザー"' + userId + '"は登録されていません');
				$('#kchImage').append(srcH + 'error.jpg' + srcT);
				$('#line_chart').remove();
				$('#gauge_chart').remove();
			}
		},
	});
}

function isAnalysed(userId) {
	$.ajax({
		type : 'GET',
		async : true,
		url : check + '/isAnalysed',
		data : {
			userId : userId,
		},
		success : function(xml) {
			if ($('return', xml).text() == 'true') {
				execute(userId);
			} else {
				$('#message').text('ユーザー"' + userName + '"はまだ解析されていません');
				$('#kchImage').append(srcH + 'error.jpg' + srcT);
				$('#line_chart').remove();
				$('#gauge_chart').remove();
			}
		},
	});
}

function execute(userId) {
	$.ajax({
		type : 'GET',
		async : true,
		url : endpoint + '/executeImage',
		data : {
			userId : userId,
		},
		success : function(xml) {
			$('#message').text('ユーザー"' + userName + '"の感情データ');
			$('#kchImage').append(srcH + $('return', xml).text() + srcT);
		},
	});
}

function setName(userId) {
	$.ajax({
		type : 'GET',
		async : true,
		url : check + '/getUserName',
		data : {
			userId : userId,
		},
		success : function(xml) {
			userName = $('return', xml).text();
		},
	});
}

function drawChart() {
	var dataArray = [['Number', 'Like', 'Joy', 'Anger']];
	var scoreArray = getEmotionArray();
	$.merge(dataArray, scoreArray);
	var line_data = google.visualization.arrayToDataTable(dataArray);

	var line_options = {
		legend : {
			position : 'top'
		},
		colors : ['blue', 'green', 'red'],
		title : '感情の変化',
		tooltip : {
			trigger : 'none'
		},
		vAxis : {
			textPosition : 'none',
			gridlines : {
				count : 9
			},
			viewWindow : {
				min : -4,
				max : 4
			}
		},
		hAxis : {
			textPosition : 'none'
		}
	};

	var totalScore = getTotalScore();
	var gauge_data = google.visualization.arrayToDataTable([['Label', 'Value'], ['感情値', totalScore]]);

	var gauge_options = {
		max : 100,
		min : 0,
		width : 200,
		height : 200,
		redFrom : 0,
		redTo : 30,
		yellowFrom : 30,
		yellowTo : 70,
		greenFrom : 70,
		greenTo : 100,
		majorTicks : [0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100],
		minorTicks : 2,
		textPosition : 'none'
	};

	var line_chart = new google.visualization.LineChart(document.getElementById('line_chart'));
	line_chart.draw(line_data, line_options);

	var gauge_chart = new google.visualization.Gauge(document.getElementById('gauge_chart'));
	gauge_chart.draw(gauge_data, gauge_options);

}

function getEmotionArray() {
	var userId = getUrlVars()["userId"];
	var scoreArray = [];

	$.ajax({
		type : 'GET',
		async : false,
		url : endpoint + '/executeChart',
		data : {
			userId : userId,
		},
		success : function(xml) {
			var result = $('return', xml);
			for (var i = 0; i < result.length; i++) {
				var array = $('array', result.eq(i));
				scoreArray[i] = ["EM" + (i + 1), eval(array.eq(0).text()) - 0.05, eval(array.eq(1).text()), eval(array.eq(2).text()) + 0.05];
			}
		},
	});

	return scoreArray;
}

function getTotalScore() {
	var userId = getUrlVars()["userId"];
	var totalScore;
	$.ajax({
		type : 'GET',
		async : false,
		url : endpoint + '/executeGauge',
		data : {
			userId : userId,
		},
		success : function(xml) {
			var result = $('return', xml);
			totalScore = eval(result.text());
		},
	});

	return totalScore;
}

function getUrlVars() {
	var vars = [], hash;
	var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	for (var i = 0; i < hashes.length; i++) {
		hash = hashes[i].split('=');
		vars.push(hash[0]);
		vars[hash[0]] = hash[1];
	}
	return vars;
}

google.load("visualization", "1", {
	packages : ["corechart", "gauge"],
});

google.setOnLoadCallback(drawChart);

