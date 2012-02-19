<!doctype html>
<html>
	<head>
		<title>Overall</title>
		<meta name="layout" content="main">
	</head>
	<body>
		<div class="row">
			<div class="span5">
				<div class="page-header">
					<h2>Committers</h2>
				</div>
				<div class="chart" id="users"></div>
				<table class="table table-bordered table-striped">
					<thead>
						<tr>
							<th>User</th>
							<th>Score</th>
							<th>Violations</th>
							<th>LOC</th>
						</tr>
					</thead>
					<tbody>
						<g:each in="${users}" var="u">
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
				</table>
			</div>
			<div class="offset2 span5">
				<div class="page-header">
					<h2>Projects</h2>
				</div>
				<div class="chart" id="projects"></div>
				<table class="table table-bordered table-striped">
					<thead>
						<tr>
							<th>Name</th>
							<th>Score</th>
							<th>Files</th>
							<th>Violations</th>
							<th>LOC</th>
						</tr>
					</thead>
					<tbody>
						<g:each in="${projects}" var="p">
							<tr>
								<td>
									<g:link controller="leaderboard" action="project" id="${p.name}">${p.name}</g:link>
								</td>
								<td>${p.score}</td>
								<td>${p.files}</td>
								<td>${p.violations}</td>
								<td>${p.lines}</td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
		</div>
		<script type="text/javascript" charset="utf-8">
			var userChart, projectChart;
			$(document).ready(function() {
				$(".table").tablesorter({
					sortList: [[1,0]]
				});

				// user chart
				(function() {
					var categories = <%= users.collect { "'${it.name}'" } %>;
					var data = <%= users.collect { it?.score ?: 0 } %>;

					usersChart = new Highcharts.Chart({
						chart: {
							renderTo: 'users',
							type: 'bar'
						},
						title: { text: '' },
						xAxis: { categories: categories },
						yAxis: {
							title: { text: 'Score' }
						},
						series: [{
							name: 'Score',
							data: data
						}],
						legend: { enabled: false },
						credits: false
					});
				})();

				// project chart
				(function() {
					var categories = <%= projects.collect { "'${it.name}'" } %>;
					var data = <%= projects.collect { it?.score ?: 0 } %>;

					projectsChart = new Highcharts.Chart({
						chart: {
							renderTo: 'projects',
							type: 'bar'
						},
						title: { text: '' },
						xAxis: { categories: categories },
						yAxis: {
							title: { text: 'Score' }
						},
						series: [{
							name: 'Score',
							color: '#AA4643',
							data: data
						}],
						legend: { enabled: false },
						credits: false
					});
				})();
			});
		</script>
	</body>
</html>