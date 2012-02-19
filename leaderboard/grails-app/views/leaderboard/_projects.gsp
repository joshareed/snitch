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
				<td>${p.name}</td>
				<td>${p.score}</td>
				<td>${p.files}</td>
				<td>${p.violations}</td>
				<td>${p.lines}</td>
			</tr>
		</g:each>
	</tbody>
</table>
<script type="text/javascript" charset="utf-8">
var projectsChart;
$(document).ready(function() {
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
});
</script>
