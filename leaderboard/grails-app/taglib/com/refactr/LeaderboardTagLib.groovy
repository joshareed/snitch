package com.refactr

class LeaderboardTagLib {
	def configService

	def avatar = { attrs ->
		def user = attrs?.user
		if (user) {
			def email = (configService.getEmailFor(user.name) ?: user.name)?.trim()?.toLowerCase()
			def size = attrs?.size ?: '30'
			if (email) {
				def hash = email.encodeAsMD5()
				out << """<img src="http://www.gravatar.com/avatar/${hash}?s=${size}&d=mm" class="${attrs.'class' ?: ''}" id="${attrs.id ?: ''}" style="${attrs.style ?: ''}" />"""
			}
		}
	}

	def repoLink = { attrs, body ->
		def project = attrs?.project
		def path = attrs?.path
		if (!project) {
			out << body()
			return
		}

		def repo = configService.getRepoFor(project)
		if (repo) {
			out << """<a href="$repo/blob/master/$path">${body()}</a>"""
		} else {
			out << body()
		}
	}
}
