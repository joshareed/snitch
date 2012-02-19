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
				<td>${u.stats.s}</td>
				<td>${u.stats.v}</td>
				<td>${u.stats.l}</td>
			</tr>
		</g:each>
	</tbody>
</table>
<script type="text/javascript" charset="utf-8">
var usersChart;
$(document).ready(function() {
	var categories = <%= users.collect { "'${it.name}'" } %>;
	var data = <%= users.collect { it?.stats?.s ?: 0 } %>;

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
});
</script>
