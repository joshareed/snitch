class UrlMappings {

	static mappings = {
		"/overview"(controller: 'leaderboard', action: 'overview')

		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(controller: 'leaderboard', action: 'overview')
		"500"(view:'/error')
	}
}
