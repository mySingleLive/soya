package soya.lang;

import org.soya.ast.ClassNode;
import org.soya.ast.CompilationUnit;
import org.soya.tools.Configuration;
import org.soya.tools.ErrorListException;
import org.soya.tools.SourceCode;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Jun Gong
 */
public class SoyaClassLoader extends URLClassLoader {

    private Map<String, Class> cachedClasses = new HashMap<String, Class>();
    private final Configuration configuration;

    public SoyaClassLoader(Configuration configuration) {
        this(Thread.currentThread().getContextClassLoader(), configuration);
    }

    public SoyaClassLoader(ClassLoader classLoader, Configuration configuration) {
        super(new URL[0], classLoader);
        this.configuration = configuration;
    }
    
    public Class getCachedClass(String name) {
        name = name.replace('$', '.');
        if (cachedClasses.containsKey(name)) {
            return cachedClasses.get(name);
        }
        ClassLoader parantClassLoader = this.getParent();
        if (parantClassLoader != null && parantClassLoader instanceof SoyaClassLoader) {
            return ((SoyaClassLoader) parantClassLoader).getCachedClass(name);
        }
        return null;
    }

    public void addCachedClass(String name, Class cls) {
        cachedClasses.put(name, cls);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    protected synchronized Class<?> loadClass(String s, boolean b) throws ClassNotFoundException {
        return loadSoyaClass(s, b);
    }

    public boolean classExists(String name) {
        Class cls = null;
        try {
            cls = Class.forName(name);
        }
        catch (ClassNotFoundException e) {
        }
        if (cls != null) {
            return true;
        }
        cls = getCachedClass(name);
        if (cls != null) {
            return true;
        }
        URL url = getSourceURL(name, configuration.getExtension());
        try {
            return new File(url.toURI()).exists();
        } catch (URISyntaxException e) {
        }
        return false;
    }

    public Class loadSoyaClass(String name, boolean resolve) throws ClassNotFoundException, ErrorListException {
        Class cls = null;
        try {
            cls = Class.forName(name);
        }
        catch (ClassNotFoundException e) {
        }
        if (cls != null) {
            return cls;
        }
        cls = getCachedClass(name);
        if (cls != null) {
            return cls;
        }

        URL url = getSourceURL(name, configuration.getExtension());
        try {
            SoyaClassLoader classLoader = new SoyaClassLoader(this, configuration);
            SourceCode sourceCode = new SourceCode(new File(url.toURI()), configuration);
            sourceCode.setClassLoader(classLoader);
            Class retClass = parseClass(sourceCode);
            if (retClass == null) {
                throw new ClassNotFoundException(name);
            }
            addCachedClass(name, retClass);
            return retClass;
        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        } catch (URISyntaxException e) {
            throw new ClassNotFoundException(name);
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new ClassNotFoundException(name);
        }
    }

    public Class parseClass(SourceCode sourceCode) throws ClassNotFoundException, ErrorListException {
        sourceCode.parse();
        CompilationUnit unit = sourceCode.getAST();
        ClassNode classNode = unit.getMainClassNode();
        sourceCode.getClassLoader().defineClass(classNode, unit);
        return unit.getTargetClass();
    }

    private URL getSourceURL(String name, String extension) {
        String filename = name.replace(".", "/") + "." + extension;
        URL ret = getResource(filename);

        if (ret == null) {
            File file = new File(filename);
            URI uri = file.toURI();
            try {
                ret = uri.toURL();
            } catch (MalformedURLException e) {
            }
        }
        else if (isFile(ret) && getFileForUrl(ret, filename) == null)
            return null;
        return ret;
    }


    private File getFileForUrl(URL ret, String filename) {
        String fileWithoutPackage = filename;
        if (fileWithoutPackage.indexOf('/') != -1) {
            int index = fileWithoutPackage.lastIndexOf('/');
            fileWithoutPackage = fileWithoutPackage.substring(index + 1);
        }
        return fileReallyExists(ret, fileWithoutPackage);
    }

    private File fileReallyExists(URL ret, String fileWithoutPackage) {
        File path;
        try {
            path = new File(ret.toURI());
        } catch(URISyntaxException e) {
            path = new File(decodeFileName(ret.getFile()));
        }
        path = path.getParentFile();
        if (path.exists() && path.isDirectory()) {
            File file = new File(path, fileWithoutPackage);
            if (file.exists()) {
                // file.exists() might be case insensitive. Let's do
                // case sensitive match for the filename
                File parent = file.getParentFile();
                for (String child : parent.list()) {
                    if (child.equals(fileWithoutPackage)) return file;
                }
            }
        }
        return null;
    }

    private String decodeFileName(String fileName) {
        String decodedFile = fileName;
        try {
            decodedFile = URLDecoder.decode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodedFile;
    }

    private boolean isFile(URL ret) {
        return ret != null && ret.getProtocol().equals("file");
    }

    private void definePackage(String className) {
        int i = className.lastIndexOf('.');
        if (i != -1) {
            String pkgName = className.substring(0, i);
            java.lang.Package pkg = getPackage(pkgName);
            if (pkg == null) {
                definePackage(pkgName, null, null, null, null, null, null, null);
            }
        }
    }

    public Class defineClass(ClassNode classNode, CompilationUnit unit) throws ClassNotFoundException {
        unit.generateClass(classNode);
        Class clazz = unit.getTargetClass();
        resolveClass(clazz);
        addCachedClass(clazz.getCanonicalName(), clazz);
        
        List<Class> classes = unit.getTargetClasses();
        for (Class cls : classes) {
            addCachedClass(cls.getCanonicalName(), cls);
        }
        
        definePackage(clazz.getName());
        return clazz;
    }

    public Class defineClass(String name, byte[] bytes) {
        return super.defineClass(name, bytes, 0, bytes.length);
    }
}
