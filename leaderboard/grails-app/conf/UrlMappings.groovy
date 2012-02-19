class UrlMappings {

	static mappings = {
		"/overall"(controller: 'leaderboard', action: 'overall')

		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(controller: 'leaderboard', action: 'overall')
		"500"(view:'/error')
	}
}
