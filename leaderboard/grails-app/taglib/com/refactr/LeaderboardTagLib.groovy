package com.refactr

class LeaderboardTagLib {

	def avatar = { attrs ->
		def user = attrs?.user
		if (user) {
			def email = (user.email ?: user.name)?.trim()?.toLowerCase()
			def size = attrs?.size ?: '30'
			if (email) {
				def hash = email.encodeAsMD5()
				out << """<img src="http://www.gravatar.com/avatar/${hash}?s=${size}&d=mm" class="${attrs.'class' ?: ''}" id="${attrs.id ?: ''}" style="${attrs.style ?: ''}" />"""
			}
		}
	}
}
