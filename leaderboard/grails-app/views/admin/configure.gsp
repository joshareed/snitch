<!doctype html>
<html>
	<head>
		<title>Configure</title>
		<meta name="layout" content="main">
	</head>
	<body>
		<div class="page-header">
			<h1>Configure</h1>
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
				<legend>Committers</legend>
				<g:if test="${users}">
					<g:each in="${users}" var="u">
						<div class="control-group">
							<label for="user-${u.key}">${u.key}</label>
							<div class="controls">
								<g:textField name="user-${u.key}" value="${u.value}" class="span6"/>
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
