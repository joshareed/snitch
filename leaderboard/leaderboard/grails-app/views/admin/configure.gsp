<!doctype html>
<html>
	<head>
		<title>Configure</title>
		<meta name="layout" content="main">
	</head>
	<body>
		<div class="page-header">
			<h1><g:link controller="leaderboard" action="overview">Refactr</g:link> / configure</h1>
		</div>
		<g:form action="save" class="form-horizontal">
			<fieldset>
				<legend>Settings</legend>
				<div class="control-group">
					<label for="workspace" class="control-label">Workspace</label>
					<div class="controls">
						<g:textField name="workspace" value="${workspace}" class="span6"/>
					</div>
				</div>
			</fieldset>

			<fieldset>
				<legend>Project URLs</legend>
				<g:if test="${projects}">
					<g:each in="${projects}" var="p">
						<div class="control-group">
							<label for="${p.key}">${p.key - 'project-'}:</label>
							<div class="controls">
								<g:textField name="${p.key}" value="${p.value}" class="span6"/>
							</div>
						</div>
					</g:each>
				</g:if>
				<g:else>
					<div class="controls">
						<em>No projects defined yet</em>
					</div>
				</g:else>
			</fieldset>

			<fieldset>
				<legend>Committer Emails</legend>
				<g:if test="${users}">
					<g:each in="${users}" var="u">
						<div class="control-group">
							<label for="${u.key}">${u.key - 'user-'}:</label>
							<div class="controls">
								<g:textField name="${u.key}" value="${u.value}" class="span6"/>
							</div>
						</div>
					</g:each>
				</g:if>
				<g:else>
					<div class="controls">
						<em>No committers defined yet</em>
					</div>
				</g:else>
			</fieldset>
			<div class="form-actions">
				<input type="submit" class="btn-primary" value="Save"/>
			</div>
		</g:form>
	</body>
</html>
