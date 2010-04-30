package com.jeff.fx.datasource;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.jeff.fx.common.FXDataPoint;
import com.jeff.fx.util.ZipUtil;

public class ZippedAbstractDataSource<T extends FXDataPoint> extends AbstractDataSource<T> {
	
	private static Logger log = Logger.getLogger(ZippedAbstractDataSource.class);

	public byte[] process(byte[] data) throws IOException {
		return ZipUtil.unzipBytes(data);
	}
}
