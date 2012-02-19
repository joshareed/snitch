package com.refactr

class ConfigService {
	static transactional = false
	private _settings

	public getDataDir() {
		File dir = new File(".snitch")
		if (!dir.exists()) {
			dir.mkdirs()
		}
		return dir
	}

	private getSettingsFile() {
		return new File(dataDir, "config")
	}

	public getSettings() {
		if (!_settings) {
			_settings = new Properties()
			def file = settingsFile
			if (file.exists()) {
				_settings.load(file.newReader())
			}
		}
		_settings
	}

	public saveSettings(Map map) {
		def save = settings
		save.putAll(map)
		save.store(settingsFile.newWriter(), null)
	}

	public getEmail(name) {
		return settings["user-$name".toString()]
	}
}
