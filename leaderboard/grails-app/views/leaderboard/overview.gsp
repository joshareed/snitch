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
				<g:render template="users" model="${users}"/>
			</div>
			<div class="offset2 span5">
				<div class="page-header">
					<h2>Projects</h2>
				</div>
				<div class="chart" id="projects"></div>
				<g:render template="projects" model="${projects}"/>
			</div>
		</div>
		<script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				$(".table").tablesorter({
					sortList: [[1,0]]
				});
			});
		</script>
	</body>
</html>