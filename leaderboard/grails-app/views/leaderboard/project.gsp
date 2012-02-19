<!doctype html>
<html>
	<head>
		<title>Project: ${name}</title>
		<meta name="layout" content="main">
		<style type="text/css" media="screen">
		.violations {
			height: 400px;
			overflow: scroll;
			margin-bottom: 100px;
		}
		.stats {
			margin-top: 10px;
		}
		.stats i {
			margin-top: 5px;
			margin-left: 0;
		}
		.light {
			color: #CCC;
			font-size: 90%;
		}
		</style>
	</head>
	<body>
		<div class="page-header">
			<div class="pull-right stats" style="font-size: 150%">
				${stats.score}<i class="icon-star"></i>
			</div>
			<h1><g:link controller="leaderboard" action="overview">Refactr</g:link> / ${name}</h1>
		</div>
		<div class="row">
			<div class="span5">
				<div class="chart" id="users"></div>
			</div>
			<div class="offset2 span5">
				<table class="table table-bordered table-striped users">
					<thead>
						<tr>
							<th>User</th>
							<th>Score</th>
							<th>Violations</th>
							<th>LOC</th>
						</tr>
					</thead>
					<tbody>
						<g:each in="${users.values()}" var="u">
							<tr>
								<td>
									<g:avatar user="${u}" class="avatar"/>
									${u.name}
								</td>
								<td>${u.score}</td>
								<td>${u.violations}</td>
								<td>${u.lines}</td>
							</tr>
						</g:each>
					</tbody>
					<tfoot>
						<tr>
							<th>Total</th>
							<th>${stats.score}</th>
							<th>${stats.violations}</th>
							<th>${stats.lines}</th>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>

		<h3>Violations <span class="light">&mdash; ${stats.violations}</span></h3>
		<div class="violations">
			<g:render template="violations" model="${violations}"/>
		</div>
		<script type="text/javascript" charset="utf-8">
			var usersChart;
			$(document).ready(function() {
				// enable sorting
				$(".users").tablesorter({ sortList: [[1,0]] });
				$(".violations table").tablesorter();

				usersChart = new Highcharts.Chart({
					chart: {
						renderTo: 'users',
						plotBackgroundColor: null,
						plotBorderWidth: null,
						plotShadow: false
					},
					title: {
						text: 'Violations'
					},
					series: [{
						type: 'pie',
						name: 'Violations',
						data: <%= users.values().collect { [ "'${it.name}'",  it.violations ] } %>
					}],
					credits: false
				});
			});
		</script>
	</body>
</html>
