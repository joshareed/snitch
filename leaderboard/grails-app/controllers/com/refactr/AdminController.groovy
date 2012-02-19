package com.refactr

class AdminController {
	static defaultAction = "configure"
	def configService

    def configure() {
		def settings = configService.settings
		[
			workspace: settings.workspace,
			users: settings.findAll { it.key.startsWith('user') }
		]
	}

	def save() {
		def save = params ?: [:]
		save.remove('controller')
		save.remove('action')
		configService.saveSettings(save)
		flash.message = "Settings saved"
		redirect action: "configure"
	}
}
