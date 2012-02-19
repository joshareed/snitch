package com.refactr

class LeaderboardController {
	static defaultAction = "overall"

	def overall() {
		[
			users: [
				[name: 'Justin Coyne', email: 'justin@refactr.com', stats: [v: 925, l:7432, s:12]],
				[name: 'Spencer Hartberg', email: 'spencer.hartberg@gmail.com', stats: [v: 188, l:406, s:46]],
				[name: 'Test 2', email: '', stats: [v:93, l:407, s:22]],
				[name: 'Josh Reed', email: 'josh@refactr.com', stats: [v:725, l:30298, s:2]],
				[name: 'hiromi', email: 'hiromi@refactr.com', stats: [v:141, l:1652, s:8]],
				[name: 'Kyle Skogen', email: 'kyle@refactr.com', stats: [v:641, l:3358, s:19]]
			].sort { it.stats.s },
			projects: [
				[name: 'Realief', stats: [v: 2713, l: 43552, f: 486, s:6]]
			].sort { it.stats.s }
		]
	}
}
