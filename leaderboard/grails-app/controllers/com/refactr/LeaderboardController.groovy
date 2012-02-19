package com.refactr

class LeaderboardController {
	static defaultAction = "overview"
	def dataService

	def overview() {
		def overview = dataService.overview
		if (!overview) {
			flash.message = "Indexing in progress..."
		}
		overview
	}

	def project() {
		if (!params.id) {
			flash.error = "No project name specified"
			redirect action: 'overview'
			return
		}

		def project = dataService.getProject(params.id)
		if (!project) {
			flash.error = "No project with the name '${params.id}'"
			redirect action: 'overview'
			return
		}

		project
	}
}
