package soya.lang;

import soya.util.SoyaBaseTestCase;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author: Jun Gong
 */
public class URLTest extends SoyaBaseTestCase {

    private String urlGoogle = "http://www.google.com";
    private String urlHttps = "https://www.google.com";
    private String urlLocal = "http://localhost:8080/xxx?a=1";
    private String urlLocal2 = "http://localhost:8080/xxx/yyy?a=3&b=2";
    private String urlFtp = "ftp://www.xxx.com";

    public void testToString() throws MalformedURLException {
        SoyaURL url = new SoyaURL(urlGoogle);
        assertEquals(urlGoogle, url.toString());

        SoyaURL url2 = new SoyaURL(urlHttps);
        assertEquals(urlHttps, url2.toString());
    }

    public void testURI() throws MalformedURLException, URISyntaxException {
        SoyaURL url = new SoyaURL(urlGoogle);
        assertEquals(urlGoogle, url.toURI().toString());

        SoyaURL url2 = new SoyaURL(urlHttps);
        assertEquals(urlHttps, url2.toURI().toString());
    }

    public void testProtocol() throws MalformedURLException {
        SoyaURL url = new SoyaURL(urlGoogle);
        assertEquals("http", url.getProtocol());

        SoyaURL url2 = new SoyaURL(urlFtp);
        assertEquals("ftp", url2.getProtocol());

        SoyaURL url3 = new SoyaURL(urlHttps);
        assertEquals("https", url3.getProtocol());
    }


    public void testHost() throws MalformedURLException {
        SoyaURL url = new SoyaURL(urlGoogle);
        assertEquals("www.google.com", url.getHost());

        SoyaURL url2 = new SoyaURL(urlLocal);
        assertEquals("localhost", url2.getHost());
    }

    public void testPort() throws MalformedURLException {
        SoyaURL url = new SoyaURL(urlGoogle);
        assertEquals(80, url.getPort());

        SoyaURL url2 = new SoyaURL(urlLocal);
        assertEquals(8080, url2.getPort());
    }

    public void testPath() throws MalformedURLException {
        SoyaURL url = new SoyaURL(urlGoogle);
        assertEquals("", url.getPath());

        SoyaURL url2 = new SoyaURL(urlLocal);
        assertEquals("/xxx", url2.getPath());

        SoyaURL url3 = new SoyaURL(urlLocal2);
        assertEquals("/xxx/yyy", url3.getPath());
    }

    public void testQuery() throws MalformedURLException {
        SoyaURL url = new SoyaURL(urlGoogle);
        assertNull(url.getQuery());

        SoyaURL url2 = new SoyaURL(urlLocal);
        assertEquals("a=1", url2.getQuery());

        SoyaURL url3 = new SoyaURL(urlLocal2);
        assertEquals("a=3&b=2", url3.getQuery());
    }

    public void testParameters() throws MalformedURLException {
        SoyaURL url = new SoyaURL(urlGoogle);
        Map params = url.getParameters();
        assertNotNull(params);
        assertEquals(0, params.size());

        SoyaURL url2 = new SoyaURL(urlLocal);
        Map params2 = url2.getParameters();
        assertEquals(1, params2.size());
        assertEquals("1", params2.get("a"));

        SoyaURL url3 = new SoyaURL(urlLocal2);
        Map param3 = url3.getParameters();
        assertEquals(2, param3.size());
        assertEquals("3", param3.get("a"));
        assertEquals("2", param3.get("b"));
    }

}
