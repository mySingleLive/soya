package soya.lang;


import org.soya.runtime.MetaClassUtil;

import java.io.*;
import java.lang.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * @author: Jun Gong
 */
public class SoyaFile extends EvalObject implements Serializable, Comparable, Readable, Writable {
	
	private final File file;

	public SoyaFile(String parent, String child) {
		this(new File(parent, child));
	}
	
	public SoyaFile(File parent, String child) {
		this(new File(parent, child));
	}
	
	public SoyaFile(URI uri) {
		this(new File(uri));
	}
	
	public SoyaFile(String filepath) {
		this(new File(filepath));
	}
	
	public SoyaFile(File file) {
		super(MetaClassUtil.FILE);
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public int compareTo(Object pathname) {
		if (pathname instanceof File) {
			return file.compareTo((File) pathname);
		}
		else if (pathname instanceof SoyaFile) {
			return file.compareTo(((SoyaFile) pathname).getFile());
		}
		return -1;
	}
	
	public String getName() {
		return file.getName();
	}

    public String getExtensionName() {
        String ext = "";
        String name = getName();
        if (name.indexOf('.') > 0) {
            int pos = name.lastIndexOf('.');
            ext = name.substring(pos + 1);
        }
        return ext;
    }
	
	public String getParent() {
		return file.getParent();
	}
	
	public SoyaFile getParentFile() {
		return new SoyaFile(file.getParentFile());
	}
	
	public String getPath() {
		return file.getPath();
	}
	
	public boolean isAbsolute() {
		return file.isAbsolute();
	}
	
	public String getAbsolutePath() {
		return file.getAbsolutePath();
	}
	
	public SoyaFile getAbsoluteFile() {
		return new SoyaFile(file.getAbsoluteFile());
	}

	public String getCanonicalPath() throws IOException {
		return file.getCanonicalPath();
	}
	
	public SoyaFile getCanonicalFile() throws IOException {
		return new SoyaFile(file.getCanonicalFile());
	}

	public URI toURI() {
		return file.toURI();
	}
	
	public URL toURL() throws MalformedURLException {
		return toURI().toURL();
	}
	
	public boolean canRead() {
		return file.canRead();
	}
	
	public boolean canWrite() {
		return file.canWrite();
	}
	
	public boolean exists() {
		return file.exists();
	}
	
	public boolean isDirectory() {
		return file.isDirectory();
	}
	
	public boolean isFile() {
		return file.isFile();
	}
	
	public boolean isHidden() {
		return file.isHidden();
	}
	
	public long lastModified() {
		return file.lastModified();
	}
	
	public long length() {
		return file.length();
	}
	
	public boolean createNewFile() throws IOException {
		return file.createNewFile();
	}
	
	public boolean delete() {
		return file.delete();
	}
	
	public void deleteOnExit() {
		file.deleteOnExit();
	}
	
	public String[] list() {
		return file.list();
	}
	
	public String[] list(FilenameFilter filter) {
		return file.list(filter);
	}
	
	public File[] listFiles() {
		return file.listFiles();
	}
	
	public File[] listFiles(FilenameFilter filter) {
		return file.listFiles(filter);
	}
	
	public File[] listFiles(FileFilter filter) {
		return file.listFiles(filter);
	}
	
	public boolean mkdir() {
		return file.mkdir();
	}
	
	public boolean mkdirs() {
		return file.mkdirs();
	}
	
	public boolean renameTo(File dest) {
		return file.renameTo(dest);
	}
	
	public boolean renameTo(SoyaFile dest) {
		return file.renameTo(dest.getFile());
	}
	
	public boolean setLastModified(long time) {
		return file.setLastModified(time);
	}
	
	public boolean setReadOnly() {
		return file.setReadOnly();
	}
	
	public boolean setWritable(boolean writable, boolean ownerOnly) {
		return file.setWritable(writable, ownerOnly);
	}
	
	public boolean setWritable(boolean writable) {
		return file.setWritable(writable);
	}
	
	public boolean setReadable(boolean readable, boolean ownerOnly) {
		return file.setReadable(readable, ownerOnly);
	}
	
	public boolean setReadable(boolean readable) {
		return file.setReadable(readable);
	}
	
	public boolean setExecutable(boolean executable, boolean ownerOnly) {
		return file.setExecutable(executable, ownerOnly);
	}
	
	public boolean setExecutable(boolean executable) {
		return file.setExecutable(executable);
	}
	
	public boolean canExecute() {
		return file.canExecute();
	}
	
	public static File[] listRoots() {
		return File.listRoots();
	}
	
	public long getTotalSpace() {
		return file.getTotalSpace();
	}
	
	public long getFreeSpace() {
		return file.getFreeSpace();
	}
	
	public long getUsableSpace() {
		return file.getUsableSpace();
	}
	
	public static File createTempFile(String prefix, String suffix, File directory) throws IOException {
		return File.createTempFile(prefix, suffix, directory);
	}
	
	public static File createTempFile(String prefix, String suffix) throws IOException {
		return File.createTempFile(prefix, suffix);
	}

	public boolean equals(Object obj) {
		if (obj instanceof File) {
			return file.equals(obj);
		}
		else if (obj instanceof SoyaFile) {
			return file.equals(((SoyaFile) obj).getFile());
		}
		return false;
	}
	
	public int hashCode() {
		return file.hashCode();
	}
	
	public String toString() {
		return file.toString();
	}

	public Object read() throws IOException {
		if (canRead()) {
			FileReader reader = new FileReader(file);
			StringBuffer buffer = new StringBuffer();
			int c;
			while ((c = reader.read()) != -1) {
				buffer.append((char) c);
			}
			reader.close();
			return buffer.toString();
		}
		return null;
	}
	
	public void read(Closure closure) throws Throwable {
		Object obj = read();
		closure.call(obj);
	}
	
	public void readChar(Closure closure) throws Throwable {
		if (canRead()) {
            FileReader reader = null;
            try {
                reader = new FileReader(file);
                int c;
                while ((c = reader.read()) != -1) {
                    closure.call(new SoyaString(new String(new char[] {(char) c})));
                }
            } catch (FileNotFoundException e) {
                throw e;
            } finally {
                reader.close();
            }
        }
	}
	
	public void readLine(Closure closure) throws Throwable {
		if (canRead()) {
            FileReader reader = null;
            BufferedReader br = null;
            try {
                reader = new FileReader(file);
                br = new BufferedReader(reader);
                String line = null;
                while ((line = br.readLine()) != null) {
                    closure.call(line);
                }
            } catch (FileNotFoundException e) {
                throw e;
            } finally {
                br.close();
                reader.close();
            }
        }
	}
	
	public void read(Object pattern, Closure closure) throws Throwable {
		if (pattern == null) {
			read(closure);
		}
		else if (pattern instanceof ClassPattern) {
			if (((ClassPattern) pattern).getMetaClass().isKindOf(StringLine.class)) {
				readLine(closure);
			}
		}
	}

    public void write(Object content, boolean b) throws IOException {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, b);
            if (content instanceof Int) {
                writer.write(((Int) content).getValue());
            }
            else if (content instanceof Integer) {
                writer.write(((Integer) content).intValue());
            }
            else {
                writer.write(content.toString());
            }
        } catch (IOException e) {
            throw e;
        } finally {
            writer.close();
        }
    }

    public void write(Object content) throws Exception {
        if (!exists()) {
            createNewFile();
        }
        if (canWrite()) {
            write(content, true);
        }
    }

    public boolean lessThan(Object o) throws IOException {
        write(o, false);
        return true;
    }

    public File plus(String s) {
        return new File(file.getPath() + s);
    }

    public File plus(File f) {
        return new File(file.getPath() + f.getPath());
    }

    public File div(String s) {
        String abpath = file.getAbsolutePath();
        char splitor = '/';
        if (abpath.indexOf('\\') > -1) {
            splitor = '\\';
        }
        return new File(file.getPath() + splitor + s);
    }

    public File div(File f) {
        return div(f.getPath());
    }
}
