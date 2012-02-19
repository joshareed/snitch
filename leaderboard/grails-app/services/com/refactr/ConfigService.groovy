package com.refactr

class ConfigService {
	static transactional = false

	private _settings

	private getSettingsFile() {
		return new File("snitch.properties")
	}

	def getSettings() {
		if (!_settings) {
			_settings = new Properties()
			def file = settingsFile
			if (file.exists()) {
				_settings.load(file.newReader())
			}
		}
		_settings
	}

	def saveSettings(Map map) {
		def save = settings
		save.putAll(map)
		save.store(settingsFile.newWriter(), null)
	}
}
