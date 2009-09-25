package com.jeff.fx.datasource.gain;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.jeff.fx.common.TickDataPoint;
import com.jeff.fx.datasource.AbstractDataSource;
import com.jeff.fx.util.ZipUtil;

@Component
public class GAINTickDataSource extends AbstractDataSource<TickDataPoint> {

	public byte[] process(byte[] data) throws IOException {
		return ZipUtil.unzipBytes(data);
	}
}
