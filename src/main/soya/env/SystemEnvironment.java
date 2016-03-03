package soya.env;

import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import soya.lang.Closure;
import soya.lang.Null;
import soya.lang.SoyaObject;
import soya.lang.SoyaURL;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * SystemEnvironment is a singleton class, its methods will be called as global methods
 * @author: Jun Gong
 */
public class SystemEnvironment implements Environment {

    private static SystemEnvironment systemEnvironmentInstance = null;

    public static SystemEnvironment getSystemEnvironmentInstance() {
        if (systemEnvironmentInstance == null) {
            systemEnvironmentInstance = new SystemEnvironment();
        }
        return systemEnvironmentInstance;
    }

    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public Environment getParent() {
        return null;
    }

    @Override
    public void start() {
    }

    @Override
    public void end() {
    }


    public void send(Map<String, Object> options) throws ExecutionException, InterruptedException, IOException {
        Object url = options.get("url");
        String urlStr = null;
        if (url != null) {
            urlStr = url.toString();
        }

        Object type =  options.get("type");
        String typeStr = "GET";
        if (type != null) {
            typeStr = String.valueOf(type);
        }

        Object header = options.get("header");
        Object params = options.get("params");
        Object requestBody = options.get("body");
        final Object success = options.get("success");
        Object error = options.get("error");

        Map<String, Object> headerMap = null;
        Closure successClosure = null;
        Closure errorClosure = null;

        String requestBodyStr = null;
        if (requestBody != null) {
            requestBodyStr = String.valueOf(requestBody);
        }

        if (header != null) {
            if (header instanceof Map) {
                headerMap = (Map<String, Object>) header;
            }
        }

        if (success != null && success instanceof Closure) {
            successClosure = (Closure) success;
        }

        if (error != null && error instanceof Closure) {
            errorClosure = (Closure) error;
        }

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(3000)
                .setConnectTimeout(3000).build();
        final CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom()
                .setDefaultRequestConfig(requestConfig).build();


        try {
            httpclient.start();
            HttpRequestBase request = null;

            if (typeStr.equals("GET")) {
                request = new HttpGet(urlStr);
            }
            else if (typeStr.equals("POST")) {
                request = new HttpPost(urlStr);
            }

            if (headerMap != null) {
                for (Iterator<String> iterator = headerMap.keySet().iterator(); iterator.hasNext(); ) {
                    String key = iterator.next();
                    Object value = headerMap.get(key);
                    request.setHeader(key, String.valueOf(value));
                }
            }

            if (request instanceof HttpPost) {
                HttpPost postRequest = (HttpPost) request;
                if (requestBodyStr != null) {
                    HttpEntity entity = new StringEntity(requestBodyStr);
                    postRequest.setEntity(entity);
                }
            }

            final Closure finalSuccessClosure = successClosure;
            final Closure finalErrorClosure = errorClosure;
            httpclient.execute(request, new FutureCallback<HttpResponse>() {
                @Override
                public void completed(HttpResponse result) {
                    if (finalSuccessClosure != null) {
                        try {
                            HttpEntity entity = result.getEntity();
                            String body = null;
                            try {
                                body = EntityUtils.toString(entity);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            finalSuccessClosure.call(body);
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                    try {
                        httpclient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failed(Exception ex) {
                    if (finalErrorClosure != null) {
                        try {
                            finalErrorClosure.call(ex);
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                    try {
                        httpclient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void cancelled() {
//                    latch.countDown();
                    try {
                        httpclient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
//            System.out.println("Response: " + response.getStatusLine());
//            System.out.println("Shutting down");
        } finally {
//            latch.countDown();
//            httpclient.close();
        }
    }



    /**
     * Terminates the currently running Java Virtual Machine. The
     * argument serves as a status code; by convention, a nonzero status
     * code indicates abnormal termination.
     * <p>
     * This method calls the <code>exit</code> method in class
     * <code>Runtime</code>. This method never returns normally.
     * <p>
     * The call <code>System.exit(n)</code> is effectively equivalent to
     * the call:
     * <blockquote><pre>
     * Runtime.getRuntime().exit(n)
     * </pre></blockquote>
     *
     * @param      status   exit status.
     * @throws  SecurityException
     *        if a security manager exists and its <code>checkExit</code>
     *        method doesn't allow exit with the specified status.
     * @see        java.lang.Runtime#exit(int)
     */
    public void exit(int status) {
        System.exit(status);
    }

    /**
     * Terminates the current line by writing the line separator string.  The
     * line separator string is defined by the system property
     * <code>line.separator</code>, and is not necessarily a single newline
     * character (<code>'\n'</code>).
     * @see        System#out#println()
     */
    public Object println() {
        System.out.println();
        return null;
    }

    /**
     * Prints an Object and then terminate the line.  This method calls
     * at first String.valueOf(x) to get the printed object's string value,
     * then behaves as
     * though it invokes <code>{@link System#out#print(String)}</code> and then
     * <code>{@link #println()}</code>.
     * @see        System#out#println(Object)
     *
     * @param obj  The <code>Object</code> to be printed.
     */
    public Object println(Object obj) {
        System.out.println(obj);
        return null;
    }

    /**
     * Prints an object.  The string produced by the <code>{@link
     * java.lang.String#valueOf(Object)}</code> method is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the
     * <code>{@link #write(int)}</code> method.
     *
     * @param      obj   The <code>Object</code> to be printed
     * @see        java.lang.Object#toString()
     */
    public Object print(Object obj) {
        System.out.print(obj);
        return null;
    }

    public void write(int b) {
        System.out.write(b);
    }

    public void write(byte[] b) throws IOException {
        System.out.write(b);
    }
}
