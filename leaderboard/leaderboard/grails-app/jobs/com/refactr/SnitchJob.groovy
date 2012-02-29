package com.refactr

import groovy.json.*

import com.refactr.snitch.*

class SnitchJob {
	static triggers = {
		simple name: 'snitchJobTrigger', startDelay: (10 * 1000), repeatInterval: (30 * 60 * 1000)
	}

	def configService, dataService

	def execute() {
		// get our workspace
		def path = configService?.settings?.workspace
		if (!path) {
			log.error "No workspace configured"
			return
		}

		def workspace = new File(path)
		if (!workspace.exists() || !workspace.isDirectory()) {
			log.error "Workspace ${workspace.absolutePath} does not exist or is not a directory"
			return
		}

		// check each project
		def results = []
		workspace.eachDir { d ->
			log.info "Checking ${d.absolutePath} for snitch violations..."
			results << new SnitchEngine().check(d)
		}
		dataService.addResults(results)
	}
}
