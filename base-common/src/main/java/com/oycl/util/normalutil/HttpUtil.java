package com.oycl.util.normalutil;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Map;

/**
 * Http 请求工具类
 */
public class HttpUtil {

    private HttpUtil(){
        throw new AssertionError();
    }

    static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static SSLConnectionSocketFactory sslsf = null;
    private static PoolingHttpClientConnectionManager cm = null;
    private static SSLContextBuilder builder = null;
    private static int DEF_SOCKET_TIMEOUT = 30000;
    private static int DEF_CONNECT_TIMEOUT = 30000;
    private static int DEF_CONNECTION_REQUEST_TIMEOUT = 30000;
    static {
        try {
            builder = new SSLContextBuilder();
            // 全部信任 不做身份鉴定
            builder.loadTrustMaterial(null, (TrustStrategy) (x509Certificates, s) -> true);
            sslsf = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register(HTTP, new PlainConnectionSocketFactory())
                    .register(HTTPS, sslsf)
                    .build();
            cm = new PoolingHttpClientConnectionManager(registry);
            cm.setMaxTotal(200);//max connection
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * httpClient post请求
     * @param url 请求url
     * @param header 头部信息
     * @param param 请求参数 form提交适用
     * @param entity 请求实体 json/xml提交适用
     * @param timeout 请求超时时间
     * @return 可能为空 需要处理
     * @throws Exception
     *
     */
    public static String post(String url, Map<String, String> header, String param, HttpEntity entity, int timeout) throws Exception {
        return post( url,  header,  param, entity, timeout, -1, -1);
    }

    /**
     * httpClient post请求
     * @param url 请求url
     * @param header 头部信息
     * @param param 请求参数 form提交适用
     * @param entity 请求实体 json/xml提交适用
     * @return 可能为空 需要处理
     * @throws Exception
     *
     */
    public static String post(String url, Map<String, String> header, String param, HttpEntity entity) throws Exception {
        return post( url,  header,  param, entity, DEF_SOCKET_TIMEOUT, DEF_CONNECT_TIMEOUT, DEF_CONNECTION_REQUEST_TIMEOUT);
    }

    //20181015 T11519【放款文件检查】个贷车架号查重【车架号个贷信息】设置超时时间 modify by zangqisong start

    /**
     * httpClient post请求
     *
     * @param url                      请求url
     * @param header                   头部信息
     * @param param                    请求参数 form提交适用
     * @param entity                   请求实体 json/xml提交适用
     * @param socketTimeout            响应超时时间
     * @param connectTimeout           链接超时时间
     * @param connectionRequestTimeout 连接池超时时间
     * @return
     * @throws Exception
     */
    public static String post(String url, Map<String, String> header, String param, HttpEntity entity, int socketTimeout, int connectTimeout, int connectionRequestTimeout) throws Exception {
        String result = "";
        CloseableHttpClient httpClient = null;
        try {
            httpClient = getHttpClient();
            HttpPost httpPost = new HttpPost(url);
            // 设置头信息
            if (header != null && !header.isEmpty()) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
            //设置超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout).build();
            httpPost.setConfig(requestConfig);
            StringEntity sentity = new StringEntity(param, "UTF-8");
            httpPost.setEntity(sentity);
            // 设置实体 优先级高
            logger.debug("请求url："+ url);
            logger.debug("请求param："+ param);
            //TODO: 请求头输出
            if (entity != null) {
                httpPost.setEntity(entity);
            }
            HttpResponse httpResponse = httpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = httpResponse.getEntity();
                result = EntityUtils.toString(resEntity);
            } else {
                result = readHttpResponse(httpResponse);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
        return result;
    }

    //20181015 T11519【放款文件检查】个贷车架号查重【车架号个贷信息】设置超时时间 modify by zangqisong end
    public static String get(String url, Map<String, String> header, Map<String, String> params){
        String result = "";
        CloseableHttpClient httpClient = null;
        try {
            httpClient = getHttpClient();
            StringBuilder strParam = new StringBuilder(url);
            if(params.size() > 0){
                strParam.append("?");
                params.entrySet().forEach(entiy->{
                    strParam.append(entiy.getKey());
                    strParam.append("=");
                    strParam.append(entiy.getValue());
                    strParam.append("&");
                });
            }
            if(strParam.lastIndexOf("&") > 0){
                strParam.deleteCharAt(strParam.lastIndexOf("&"));
            }
            HttpGet httpGet = new HttpGet(strParam.toString());
            // 设置头信息
            if (header != null && !header.isEmpty()) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }

            HttpResponse httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = httpResponse.getEntity();
                result = EntityUtils.toString(resEntity);
            } else {
                result = readHttpResponse(httpResponse);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String get(String url, Map<String, String> header) {
        String result = "";
        CloseableHttpClient httpClient = null;
        try {
            httpClient = getHttpClient();
            StringBuilder strParam = new StringBuilder(url);

            HttpGet httpGet = new HttpGet(strParam.toString());
            // 设置头信息
            if (header != null && !header.isEmpty()) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }
            HttpResponse httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = httpResponse.getEntity();
                result = EntityUtils.toString(resEntity);
            } else {
                result = readHttpResponse(httpResponse);
            }
        } catch (Exception e) {
            logger.error("系统异常",e);
        }
        return result;
    }
    public static CloseableHttpClient getHttpClient() throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .setConnectionManager(cm)
                .setConnectionManagerShared(true)
                .build();
        return httpClient;
    }

    public static String readHttpResponse(HttpResponse httpResponse) throws IOException {
        StringBuilder builder = new StringBuilder();
        // 获取响应消息实体
        HttpEntity entity = httpResponse.getEntity();
        // 响应状态
        builder.append("status:" + httpResponse.getStatusLine());
        builder.append("headers:");
        HeaderIterator iterator = httpResponse.headerIterator();
        while (iterator.hasNext()) {
            builder.append("\t" + iterator.next());
        }
        // 判断响应实体是否为空
        if (entity != null) {
            String responseString = EntityUtils.toString(entity);
            builder.append("response length:" + responseString.length());
            builder.append("response content:" + responseString.replace("\r\n", ""));
        }
        return builder.toString();
    }

    public static String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static String getRequestURL(String url, Object... params) {
        return MessageFormat.format(url, params);
    }
}
