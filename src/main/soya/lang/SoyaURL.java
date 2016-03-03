package soya.lang;

import java.awt.*;
import java.io.*;
import java.lang.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import org.soya.runtime.MetaClassUtil;

/**
 * @author: Jun Gong
 */
public class SoyaURL extends EvalObject implements Serializable, soya.lang.Readable {

    private final URL url;

    public SoyaURL(String url) throws MalformedURLException {
        this(new URL(url));
    }

    public SoyaURL(URL url) {
        super(MetaClassUtil.URL);
        this.url = url;
    }
    
    public URL getURL() {
		return url;
	}

	public String getQuery() {
        return url.getQuery();
    }

    public String getPath() {
        return url.getPath();
    }

    public Map getParameters() {
        Map<String, String> parameters = new HashMap<String, String>();
        String query = getQuery();
        if (query == null) {
            return parameters;
        }
        String[] params = query.split("&");
        for (int i = 0; i < params.length; i++) {
            String param = params[i];
            String[] parts = param.split("=");
            if (parts.length == 2) {
                parameters.put(parts[0], parts[1]);
            }
        }
        return parameters;
    }

    public String getUserInfo() {
        return url.getUserInfo();
    }

    public String getAuthority() {
        return url.getAuthority();
    }

    public int getPort() {
        int port = url.getPort();
        if (port == -1) {
            return getDefaultPort();
        }
        return port;
    }

    public int getDefaultPort() {
        return url.getDefaultPort();
    }

    public String getProtocol() {
        return url.getProtocol();
    }

    public String getHost() {
        return url.getHost();
    }

    public String getFile() {
        return url.getFile();
    }

    public String getRef() {
        return url.getRef();
    }

    public boolean equals(Object o) {
        if (o instanceof SoyaURL || o instanceof URL) {
            return url.toString().equals(o.toString());
        }
        return url.equals(o);
    }

    public synchronized int hashCode() {
        return url.hashCode();
    }

    public boolean sameFile(URL url) {
        return url.sameFile(url);
    }

    public String toString() {
        return url.toString();
    }

    public String toExternalForm() {
        return url.toExternalForm();
    }

    public URI toURI() throws URISyntaxException {
        return url.toURI();
    }

    public URLConnection openConnection() throws IOException {
        return url.openConnection();
    }

    public URLConnection openConnection(Proxy proxy) throws IOException {
        return url.openConnection(proxy);
    }

    public final InputStream openStream() throws IOException {
        return url.openStream();
    }

    public final Object getContent() throws IOException {
        return url.getContent();
    }

    public final Object getContent(Class[] classes) throws IOException {
        return url.getContent(classes);
    }

    public static void setURLStreamHandlerFactory(URLStreamHandlerFactory urlStreamHandlerFactory) {
        URL.setURLStreamHandlerFactory(urlStreamHandlerFactory);
    }

    public void browse() throws URISyntaxException, IOException {
        Desktop desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.BROWSE)) {
            desktop.browse(toURI());
        }
    }



    public Object read() throws IOException {
/*
        HttpClient httpClient = new HttpClient();
        GetMethod method = new GetMethod(url.toString());
        httpClient.executeMethod(method);
        String response = method.getResponseBodyAsString();
        method.releaseConnection();
        return response;
*/
        URLConnection connection = openConnection();
        String type = connection.getContentType();
        InputStream inputStream = connection.getInputStream();
        int c;
        StringBuffer buffer = new StringBuffer();
        while ((c = inputStream.read()) != -1) {
            buffer.append((char) c);
        }
        return buffer.toString();
    }

/*
    public void read(Closure closure) throws Throwable {
        URLConnection connection = openConnection();
        InputStream inputStream = connection.getInputStream();
        connection.connect();
        try {
            int c;
            while ((c = inputStream.read()) != -1) {
                closure.call(connection, c);
            }
        } catch (Throwable t) {
            throw t;
        } finally {
            inputStream.close();
        }
    }
*/

    public URL plus(String s) throws MalformedURLException {
        return new URL(url.toString() + s);
    }

    public URL plus(File f) throws IOException {
        String query = url.getQuery();
        String filePath = f.getPath();
        int port = url.getPort();
        StringBuffer buffer = new StringBuffer();
        buffer.append(url.getProtocol());
        buffer.append("://");
        buffer.append(url.getHost());
        if (port > 0) {
            buffer.append(':');
            buffer.append(port);
        }
        buffer.append(url.getPath());
        if (filePath.charAt(0) == '/' || filePath.charAt(0) == '\\') {
            filePath = filePath.substring(1);
        }
        buffer.append('/');
        buffer.append(filePath);
        if (query != null && !query.isEmpty()) {
            buffer.append('?');
            buffer.append(query);
        }
        return new URL(buffer.toString());
    }


}
