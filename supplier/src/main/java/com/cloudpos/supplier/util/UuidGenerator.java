package com.cloudpos.supplier.util;

import java.util.UUID;

public final class UuidGenerator {

	private UuidGenerator() {
	}

	public static String generate() {
		return UUID.randomUUID().toString();
	}
}
