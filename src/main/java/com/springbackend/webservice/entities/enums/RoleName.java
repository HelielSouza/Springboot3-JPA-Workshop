package com.springbackend.webservice.entities.enums;

public enum RoleName {
    ROLE_CUSTOMER (1),
    ROLE_ADMINISTRATOR (2);
	
	private int code;

	private RoleName(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	public static RoleName valueOf(int code) {
		for (RoleName value : RoleName.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid RoleName code");
	}
}