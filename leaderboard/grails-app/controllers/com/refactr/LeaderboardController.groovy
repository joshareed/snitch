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
}
