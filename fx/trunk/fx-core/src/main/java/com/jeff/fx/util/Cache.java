package com.jeff.fx.util;

import java.io.IOException;

public interface Cache<T> {
	public void store(String key, T data) throws IOException;

	public T retrieve(String key) throws IOException;

	public boolean exists(String key);
}
