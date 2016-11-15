package com.faceye.feature.util.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Http {
	private Logger logger = LoggerFactory.getLogger(getClass());
	public static CloseableHttpClient httpClient = null;
	public static CloseableHttpClient defaultHttpClient = null;
	public static CloseableHttpClient sslHttpClient = null;
	private static int BUFFER_SIZE = 64 * 1024;
	private Map<String, String> headers = new HashMap<String, String>(0);
	private String useragent = "";
	private static Http INSTANCE = null;
	private PoolingHttpClientConnectionManager cm = null;

	private final static String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.3) Gecko/20100101 Firefox/8.0";

	private Http() {
		cm = new PoolingHttpClientConnectionManager();
		// 将最大连接数增加到200
		cm.setMaxTotal(200);
		// 将每个路由基础的连接增加到20
		cm.setDefaultMaxPerRoute(20);
		// 将目标主机的最大连接数增加到50
		// HttpHost localhost = new HttpHost("www.baidu.com", 80);
		// cm.setMaxPerRoute(new HttpRoute(localhost), 50);

	}

	public synchronized static Http getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Http();
		}
		return INSTANCE;
	}

	/**
	 * 构建http client
	 */
	private void buildHttpClient(String url) {
		if (StringUtils.isNotBlank(url)) {
			if (StringUtils.lowerCase(url).startsWith("https")) {
				if (sslHttpClient == null) {
					HttpClientBuilder builder = HttpClientBuilder.create();
					X509TrustManager x509mgr = new X509TrustManager() {
						public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
						}

						public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
						}

						public X509Certificate[] getAcceptedIssuers() {
							return null;
						}
					};
					SSLContext sslContext = null;
					try {
						sslContext = SSLContext.getInstance("TLS");
						sslContext.init(null, new TrustManager[] { x509mgr }, null);
					} catch (NoSuchAlgorithmException e1) {
						logger.debug(">>FaceYe ->", e1);
					} catch (KeyManagementException e1) {
						logger.debug(">>FaceYe -> ", e1);
					}
					// SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
					SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
					sslHttpClient = builder.setSSLSocketFactory(sslsf).build();
				}
				httpClient = sslHttpClient;
			} else {
				if (defaultHttpClient == null) {
					defaultHttpClient = HttpClients.custom().setConnectionManager(cm).build();
				}
				httpClient = defaultHttpClient;
			}
		} else {
			if (defaultHttpClient == null) {
				defaultHttpClient = HttpClients.custom().setConnectionManager(cm).build();
			}
			httpClient = defaultHttpClient;
		}
	}
	
	public Map<String, String> getResponseHeaders(){
		return headers;
	}
	// public synchronized CloseableHttpClient getClient() {
	// return httpClient;
	// }

	public byte[] getContent(String url, String charset, Map<String, String> params) {
		HttpGet httpget = null;
		CloseableHttpResponse response = null;
		InputStream in = null;
		byte[] content = null;
		try {
			this.buildHttpClient(url);
			httpget = this.initHttpGet(url);
			if (MapUtils.isNotEmpty(params)) {
				org.apache.http.client.config.RequestConfig rc = null;
				// HttpParams httpParams=new HttpParams();
				// httpget.getConfig().custom().
				// httpget.set
				Iterator<String> it = params.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();
					String value = MapUtils.getString(params, key);
					httpget.getParams().setParameter(key, value);
					// httpget.setParams(params);
					// httpget.set
				}
			}
			HttpContext context = HttpClientContext.create();
			response = httpClient.execute(httpget, context);
			HttpEntity entity = response.getEntity();
			if (StringUtils.isEmpty(charset)) {
				charset = ContentType.getOrDefault(entity).getCharset().displayName();
			}
			in = entity.getContent();
			Header[] heads = response.getAllHeaders();
			initHeaders(heads);
			long contentLength = Long.MAX_VALUE;
			if (entity.getContentLength() != -1) {
				contentLength = entity.getContentLength();
			}
			byte[] buffer = new byte[BUFFER_SIZE];
			int bufferFilled = 0;
			int totalRead = 0;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			while ((bufferFilled = in.read(buffer, 0, buffer.length)) != -1 && totalRead + bufferFilled <= contentLength) {
				totalRead += bufferFilled;
				out.write(buffer, 0, bufferFilled);
			}
			content = out.toByteArray();
			if (content != null) {
				String contentEncoding = MapUtils.getString(headers, "Content-Encoding");
				if (StringUtils.isNotEmpty(contentEncoding)) {
					if (StringUtils.equals("gzip", contentEncoding.toLowerCase()) || StringUtils.equals("x-gzip", contentEncoding.toLowerCase())) {
						content = GZIPUtils.unzipBestEffort(content);
					} else if (StringUtils.equals("deflate", contentEncoding.toLowerCase())) {
						content = DeflateUtils.deflate(content);
					}
				}
			}
		} catch (ClientProtocolException ex) {
			logger.error(">>FaceYe error,url is :" + httpget.getURI().toASCIIString());
		} catch (IOException ex) {
			logger.error(">>FaceYe error,url is :" + httpget.getURI().toASCIIString());
		} catch (HttpException e) {
			logger.error(">>Get Url Content Throws Exception :" + e.toString() + ",url is:" + httpget.getURI().toString(), e);
		} catch (Exception e) {
			logger.error(">>Get Url Content Throws Exception :" + e.toString() + ",url is:" + httpget.getURI().toString(), e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error(">>--->" + e.getMessage());
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error(">>--->" + e.getMessage());
				}
			}
			this.destroy();
			// if (null != content) {
			// try {
			// res = new String(content, charset);
			// } catch (UnsupportedEncodingException e) {
			// logger.error(">>--->" + e.toString());
			// }
			// }
		}
		return content;
	}

	public String get(String url, String charset, Map<String, String> params) {
		String res = "";
		byte[] content = this.getContent(url, charset, params);
		if (null != content) {
			try {
				res = new String(content, charset);
			} catch (UnsupportedEncodingException e) {
				logger.error(">>--->" + e.toString());
			}
		}
		return res;
	}

	public String get(String url, String charset) {
		return this.get(url, charset, null);
	}

	/**
	 * 关闭使用后的链接
	 * 
	 * @todo
	 * @author:@haipenge haipenge@gmail.com 2014年7月6日
	 * @throws IOException
	 */
	public void destroy() {
		if (cm != null) {
			// cm.close();
		}
		// cm = null;
		// try {
		// httpClient.close();
		// } catch (IOException e) {
		// logger.error(">>FaceYe throws Exception: --->", e);
		// }
		// httpClient = null;
		// INSTANCE = null;
	}

	/**
	 * POST数据提交，参数对应
	 * 
	 * @todo
	 * @param url
	 * @param charset
	 * @param params
	 * @return
	 * @author:@haipenge 联系:haipenge@gmail.com 创建时间:2015年7月7日
	 */

	public String post(String url, String charset, Map<String, String> params) {
		return this.post(url, charset, params, null, null);
	}

	/**
	 * post json格式的数据
	 * 
	 * @todo
	 * @param url
	 * @param charset
	 * @param params
	 * @param data
	 * @return
	 * @author:@haipenge 联系:haipenge@gmail.com 创建时间:2015年7月7日
	 */
	public String postJson(String url, String charset, String data) {
		return this.post(url, charset, null, data, ContentType.APPLICATION_JSON);
	}

	/**
	 * post xml格式数据
	 * 
	 * @todo
	 * @param url
	 * @param charset
	 * @param data
	 * @return
	 * @author:@haipenge 联系:haipenge@gmail.com 创建时间:2015年7月7日
	 */
	public String postXml(String url, String charset, String data) {
		return this.post(url, charset, null, data, ContentType.APPLICATION_XML);
	}

	/**
	 * POST提交
	 * 
	 * @todo
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2014年12月29日
	 */
	public String post(String url, String charset, Map<String, String> params, String data, ContentType contentType) {
		String res = "";
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		InputStream in = null;
		byte[] content = null;
		try {
			this.buildHttpClient(url);
			httpPost = this.initHttpPost(url);
			if (StringUtils.isEmpty(data)) {
				List<NameValuePair> nvps = map2NameValuePair(params);
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
			} else {
				HttpEntity entity = new StringEntity(data, contentType);
				httpPost.setEntity(entity);
			}
			HttpContext context = HttpClientContext.create();
			response = httpClient.execute(httpPost, context);
			HttpEntity entity = response.getEntity();
			if (StringUtils.isEmpty(charset)) {
				charset = ContentType.getOrDefault(entity).getCharset().displayName();
			}
			in = entity.getContent();
			Header[] heads = response.getAllHeaders();
			initHeaders(heads);
			long contentLength = Long.MAX_VALUE;
			if (entity.getContentLength() != -1) {
				contentLength = entity.getContentLength();
			}
			byte[] buffer = new byte[BUFFER_SIZE];
			int bufferFilled = 0;
			int totalRead = 0;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			while ((bufferFilled = in.read(buffer, 0, buffer.length)) != -1 && totalRead + bufferFilled <= contentLength) {
				totalRead += bufferFilled;
				out.write(buffer, 0, bufferFilled);
			}
			content = out.toByteArray();
			if (content != null) {
				String contentEncoding = MapUtils.getString(headers, "Content-Encoding");
				if (StringUtils.isNotEmpty(contentEncoding)) {
					if (StringUtils.equals("gzip", contentEncoding.toLowerCase()) || StringUtils.equals("x-gzip", contentEncoding.toLowerCase())) {
						content = GZIPUtils.unzipBestEffort(content);
					} else if (StringUtils.equals("deflate", contentEncoding.toLowerCase())) {
						content = DeflateUtils.deflate(content);
					}
				}
			}
		} catch (ClientProtocolException ex) {
			logger.error(">>FaceYe error,url is :" + httpPost.getURI().toASCIIString(), ex);
		} catch (IOException ex) {
			logger.error(">>FaceYe error,url is :" + httpPost.getURI().toASCIIString(), ex);
		} catch (HttpException e) {
			logger.error(">>Get Url Content Throws Exception :" + e.toString() + ",url is:" + httpPost.getURI().toString(), e);
		} catch (Exception e) {
			logger.error(">>Get Url Content Throws Exception :" + e.toString() + ",url is:" + httpPost.getURI().toString(), e);
		} finally {
			if (null != content) {
				try {
					res = new String(content, charset);
				} catch (UnsupportedEncodingException e) {
					logger.error(">>FaceYe throws Exception: --->" + e.toString(), e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error(">>--->" + e.getMessage(), e);
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error(">>--->" + e.getMessage(), e);
				}
			}
			if (httpPost != null) {
				httpPost.abort();
			}
			this.destroy();
		}
		return res;
	}

	/**
	 * 组装参数
	 * 
	 * @param params
	 * @return
	 */
	private List<NameValuePair> map2NameValuePair(Map<String, String> params) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (MapUtils.isNotEmpty(params)) {
			Iterator<String> it = params.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value = MapUtils.getString(params, key);
				// if (null!=value &&StringUtils.isNotEmpty(value)&&StringUtils.isNotBlank(value)) {
				if (null != value) {
					NameValuePair nvp = new BasicNameValuePair(key, value);
					logger.debug(">>HttpCall params:->" + key + "    =   " + value);
					nvps.add(nvp);
				}
			}
		}
		return nvps;
	}

	private void initHeaders(Header[] heads) throws Exception {
		if (MapUtils.isNotEmpty(headers)) {
			headers.clear();
		}
		if (heads != null && heads.length > 0) {
			for (int i = 0; i < heads.length; i++) {
				Header header = heads[i];
				String name = header.getName();
				String value = header.getValue();
				headers.put(name, value);
			}
		}
	}

	private void initRequestConfig(HttpGet httpget) {
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectionRequestTimeout(30000).setConnectTimeout(30000).build();
		httpget.setConfig(requestConfig);
	}

	private HttpGet initHttpGet(String url) {
		HttpGet httpGet = new HttpGet(url);
		this.initRequestConfig(httpGet);
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.setHeader("User-Agent", USER_AGENT);
		// 用逗号分隔显示可以同时接受多种编码
		httpGet.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpGet.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		return httpGet;
	}

	/**
	 * 初始化post请求
	 * 
	 * @todo
	 * @param url
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2014年12月29日
	 */
	private HttpPost initHttpPost(String url) {
		HttpPost post = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectionRequestTimeout(30000).setConnectTimeout(30000).build();
		post.setConfig(requestConfig);
		// 用逗号分隔显示可以同时接受多种编码
		post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		post.setHeader("User-Agent", USER_AGENT);
		post.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
		post.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		post.setHeader("Keep-Alive", "300");
		return post;
	}
}
