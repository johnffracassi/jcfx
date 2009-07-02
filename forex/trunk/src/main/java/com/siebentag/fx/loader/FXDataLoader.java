package com.siebentag.fx.loader;

import java.util.List;

public interface FXDataLoader<T>
{
	void prepare() throws Exception;
	void complete() throws Exception;
	void save(List<T> data, int jobId);
}
