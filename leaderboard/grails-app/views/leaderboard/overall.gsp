<!doctype html>
<html>
	<head>
		<title>Overall</title>
		<meta name="layout" content="main">
		<style type="text/css" media="screen">
			.avatar {
				float: left;
				padding-right: 10px;
				margin-top: -7px;
			}
			.table tbody td {
				padding-top: 17px;
			}
			.table th:hover {
				cursor: pointer;
				background-color: rgba(0, 0, 0, 0.2);
			}
			.table th.headerSortUp, .table th.headerSortDown {
				background-color: rgba(0, 0, 0, 0.2);
			}
			.table th.headerSortUp:after {
				content: '▲';
				float: right;
			}
			.table th.headerSortDown:after {
				content: '▼';
				float: right;
			}
			.chart {
				margin-bottom: 30px;
			}
		</style>
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