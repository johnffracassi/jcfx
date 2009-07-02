package com.siebentag.fx.loader;

import java.io.File;
import java.io.IOException;

public interface FXFileReader
{
	void readFile(File path) throws IOException;
}
