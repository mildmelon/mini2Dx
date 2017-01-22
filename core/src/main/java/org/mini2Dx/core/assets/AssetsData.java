/**
 * Copyright 2017 Thomas Cashman
 */
package org.mini2Dx.core.assets;

import java.util.List;

import org.mini2Dx.core.serialization.annotation.Field;

/**
 *
 */
public class AssetsData {
	@Field(optional=true)
	private List<String> texturePacks;
	@Field(optional=true)
	private List<String> soundFiles;
	@Field(optional=true)
	private List<String> musicFiles;
	@Field(optional=true)
	private List<String> mapFiles;
	@Field(optional=true)
	private List<String> dataFiles;

	public List<String> getTexturePacks() {
		return texturePacks;
	}

	public void setTexturePacks(List<String> texturePacks) {
		this.texturePacks = texturePacks;
	}

	public List<String> getSoundFiles() {
		return soundFiles;
	}

	public void setSoundFiles(List<String> soundFiles) {
		this.soundFiles = soundFiles;
	}

	public List<String> getMusicFiles() {
		return musicFiles;
	}

	public void setMusicFiles(List<String> musicFiles) {
		this.musicFiles = musicFiles;
	}

	public List<String> getMapFiles() {
		return mapFiles;
	}

	public void setMapFiles(List<String> mapFiles) {
		this.mapFiles = mapFiles;
	}

	public List<String> getDataFiles() {
		return dataFiles;
	}

	public void setDataFiles(List<String> dataFiles) {
		this.dataFiles = dataFiles;
	}
}
