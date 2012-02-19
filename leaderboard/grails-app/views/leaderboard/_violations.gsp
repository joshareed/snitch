<table class="table table-bordered table-striped table-condensed">
	<thead>
		<tr>
			<th>File</th>
			<th>Line</th>
			<th>User</th>
			<th class="span8">Message</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${violations}" var="v">
			<tr>
				<td>
					<g:repoLink project="${name}" path="${v.path}">${v.file}</g:repoLink>
				</td>
				<td>${v.line}</td>
				<td>${v.blame}</td>
				<td>${v.message}</td>
			</tr>
		</g:each>
	</tbody>
</table>
