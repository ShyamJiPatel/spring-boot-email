package com.dbi.shyam.utils;

public enum Constants {

	BLANK(""), COLON(" : "), DASH(" - "), ENTRY("Entry"), EXIT("Exit");

	private String value;

	private Constants(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
