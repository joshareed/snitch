package com.refactr

class DataService {
	static transactional = false
	def configService

	private _projects = [:]
	private _users = [:]
	private _overview = [:]

	void addResults(list) {
		def projects = [:]
		def users = [:]

		// to determine if config settings exist for users and projects
		def settings = configService.settings
		def add = [:]

		list.each { results ->
			// build our project structure
			def project = buildProjectStructure(results)
			projects[project.name] = project

			// build our user structure
			project.users.each { name, stats ->
				if (!users.containsKey(name)) {
					users[name] = [
						name: name,
						stats: [lines: 0, violations: 0],
						projects: [:]
					]
				}
				def user = users[name]
				user.projects[project.name] = [:] + stats + [name: project.name]
				user.stats.lines = user.stats.lines + stats.lines
				user.stats.violations = user.stats.violations + stats.violations
				user.stats.projects = user.projects.size()
			}

			// add config settings for projects
			String key = "project-${project.name}".toString()
			if (!settings.containsKey(key)) {
				add[key] = ''
			}
		}

		// summarize users
		users.each { name, user ->
			user.stats.score = score(user.stats)
			String key = "user-${name}".toString()
			if (!settings.containsKey(key)) {
				add[key] = ''
			}
		}

		// add any config settings
		if (add) {
			configService.saveSettings(add)
		}

		// build overview
		def overview = [:]
		overview.users = users.collect { name, user -> [name: name] + user.stats }.sort { it.score }
		overview.projects = projects.collect { name, project -> [name: name] + project.stats }.sort { it.score }

		// store
		_overview = overview
		_users = users
		_projects = projects
	}

	private score(stats) {
		int l = stats.lines
		int v = stats.violations
		(int) Math.round(v / l * 100)
	}

	private buildProjectStructure(r) {
		def project = [
			name: r.project.name,
			stats: [
				files: r.files,
				lines: r.lines,
				violations: r.violations.size()
			],
			users: [:],
			violations: []
		]
		project.stats.score = score(project.stats)

		// collect our users
		r.users.each { name, stats ->
			project.users[name] = [
				name: name,
				lines: stats.lines,
				violations: stats.violations
			]
		}
		project.users.each { k, v -> v.score = score(v) }

		// collect our violations
		int strip = r.project.canonicalPath.length() + 1
		r.violations.each { v ->
			project.violations << [
				file: v.file.name,
				path: v.file.canonicalPath.substring(strip),
				line: v.line,
				rule: v.rule,
				message: v.message,
				blame: v.blame
			]
		}
		return project
	}

	Map getOverview() {
		_overview
	}

	Map getProject(name) {
		_projects[name]
	}

	Map getUser(name) {
		_users[name]
	}
}
