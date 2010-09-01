package com.jeff.fx.backtest.strategy.coder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

@SuppressWarnings("rawtypes")
public class StrategyCompiler implements DiagnosticListener {

	private StringBuffer errBuf;
	
	public Object compile(String className, String content) throws Exception {
		errBuf = new StringBuffer();
	    MemoryClassLoader mcl = new MemoryClassLoader(className, content, this);
	    return mcl.loadClass(className).newInstance();
	}

	public String getOutput() {
		return errBuf.toString();
	}

	public void report(Diagnostic diagnostic) {
		StringBuffer buf = new StringBuffer();
		
		buf.append("===\n");
		buf.append(diagnostic.getMessage(Locale.getDefault()).substring(8) + "\n");
		buf.append("line:" + diagnostic.getLineNumber() + " char:" + diagnostic.getColumnNumber() + "\n");
		
		errBuf.append(buf);
	}
}

class Source extends SimpleJavaFileObject {
	
    private final String content;

    Source(String name, Kind kind, String content) {
        super(URI.create("memo:///" + name.replace('.', '/') + kind.extension), kind);
        this.content = content;
    }

    @Override
    public CharSequence getCharContent(boolean ignore) {
        return this.content;
    }
}

class Output extends SimpleJavaFileObject {
	
    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

    Output(String name, Kind kind) {
        super(URI.create("memo:///" + name.replace('.', '/') + kind.extension), kind);
    }

    byte[] toByteArray() {
        return this.baos.toByteArray();
    }

    public ByteArrayOutputStream openOutputStream() {
        return this.baos;
    }
}

class MemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {
    
	protected final Map<String, Output> map = new HashMap<String, Output>();

    MemoryFileManager(JavaCompiler compiler) {
        super(compiler.getStandardFileManager(null, null, null));
    }

    public Output getJavaFileForOutput(Location location, String name, Kind kind, FileObject source) {
        Output mc = new Output(name, kind);
        this.map.put(name, mc);
        return mc;
    }
}

@SuppressWarnings("rawtypes")
class MemoryClassLoader extends ClassLoader {
	
    private final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    private final MemoryFileManager manager = new MemoryFileManager(this.compiler);

    public MemoryClassLoader(String classname, String filecontent, DiagnosticListener diagnosticListener) {
        this(Collections.singletonMap(classname, filecontent), diagnosticListener);
    }

    @SuppressWarnings("unchecked")
	public MemoryClassLoader(Map<String, String> map, DiagnosticListener diagnosticListener) {
    	
    	// create a list of source files
        List<Source> list = new ArrayList<Source>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            list.add(new Source(entry.getKey(), Kind.SOURCE, entry.getValue()));
        }

        // use the same classpath as the execution environment
		List<String> options = new ArrayList<String>();
		options.add("-classpath");
		StringBuilder sb = new StringBuilder();
		URLClassLoader urlClassLoader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
		for (URL url : urlClassLoader.getURLs()) {
			sb.append(url.getFile()).append(File.pathSeparator);
		}
		options.add(sb.toString());
		
		// run the compiler task
		compiler.getTask(new PrintWriter(System.out), this.manager, diagnosticListener, options, null, list).call();
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        synchronized (manager) {
            Output mc = this.manager.map.remove(name);
            if (mc != null) {
                byte[] array = mc.toByteArray();
                return defineClass(name, array, 0, array.length);
            }
        }
        return super.findClass(name);
    }
}
