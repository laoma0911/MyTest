package com.medlinker.dentist.provider.exception;

public class ZookeeperException extends RuntimeException {

	private static final long serialVersionUID = 1393957353478034407L;

	public ZookeeperException(final Exception cause) {
		super(cause);
	}
}
