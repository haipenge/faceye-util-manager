package com.faceye.feature.util.html;

public class RegexpConstants {
	// 匹配中文字符的正则表达式：
		public static final String CHINESE_CHARACTERS = "[\u4e00-\u9fa5]";
		// 评注：匹配中文还真是个头疼的事，有了这个表达式就好办了
		// 匹配双字节字符(包括汉字在内)：
		public static final String DOUBLE_BYTE_CHAR = "[^\\x00-\\xff]";
		// 评注：可以用来计算字符串的长度（一个双字节字符长度计2，ASCII字符计1）
		// 匹配空白行的正则表达式：
		public static final String BLANK_LINE = "\\n\\s*\\r";
		// public static final String BLANK_LINE="^[\\s&&[^\\n]]*\\n$";
		// 评注：可以用来删除空白行
		// 匹配HTML标记的正则表达式：
		public static final String HTML = "<(\\S*?)[^>]*>.*?</\1>|<.*? />";
		// 评注：网上流传的版本太糟糕，上面这个也仅仅能匹配部分，对于复杂的嵌套标记依旧无能为力
		// 匹配首尾空白字符的正则表达式：^\s*|\s*$
		// 评注：可以用来删除行首行尾的空白字符(包括空格、制表符、换页符等等)，非常有用的表达式
		// 匹配Email地址的正则表达式：
		public static final String EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		
		//(\w)+(\.\w+)*@(\w)+((\.\w+)+)
		//\w+\.\w+*@\w+\.\w+
		public static final String E_MAIL="((\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+))";
		// 评注：表单验证时很实用
		// 匹配网址URL的正则表达式：[a-zA-z]+://[^\s]*
		/**
		 * 提取A标签里面的URL
		 * <a[^>]*?href=[\"\\']?([^\"\\'>]+?)[\"\\']?[^>]*>.+?<[\\s]*?\\/a>
		 * <a[^>]*?href=[\"\\']?([^\"\\'>]+)[\"\\']?[^>]*>.+?<[\\s]*?\\/a>
		 * <a[^>]*?href=[\"\']?([^\"\'>]+?)[\"\']?[^>]*>.+?<[\s]*?\/a>
		 */
		public static final String DISTIL_A_HREF = "<a[^>]*?href=[\"\\']?([^\"\\'>]+)[\"\\']?[^>]*>.+?<[\\s]*?\\/a>";
		/**
		 * 提取网页的标题
		 */
		public static final String DISTIAL_HTML_TITILE = "<title[^\\>]*?>\\s*?(.*?)\\s*?<\\/title>";

		/**
		 * 提取网页的meta数据
		 * <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		 */
		public static final String DISTILL_HTML_META = "<meta\\s+([^>]*?)=[\"\\']?([^>]*?)[\"\\']?\\s+([^>]*?)=[\"\\']?([^>]*?)[\"\\']?[\\s]*?\\/>";
		
		/**
		 * 提取IMG
		 * <div class="image feature-img thumb-180">
		 * <a href="/p/219266.html">
		 * <img alt="Blank" class="image lazyload" data-src="http://a.36krcnd.com/photo/2015/1ff2b4884bd11d44923fde11c8fc89c8.png!slider" src="http://d.36kr.com/assets/images/blank.gif" />
		 * </a>
		 * </div>
		 * ---------------------------------------
		 * <div class="image thumb-60 left"><img src="http://a.36krcnd.com/photo/2015/703fb6438a7ae01c44f99e2ae730b791.png!square" alt="" /></div>
		 * ---------------------------------------
		 * <img src="http://a.36krcnd.com/photo/2015/4eccdb13d5f3f31b5e6a8ddba006c759.png" alt=""/>
		 */
		/**
		 * 提取Img标签里面的data-src
		 * <img[^>]*?data\-src=[\"\']?([^\"\'>]+)[\"\']?[^>]*\/>
		 */
		public final static String DISTILL_IMG_DATA_SRC="<img[^>]*?data\\-src=[\"\\']?([^\"\\'>]+)[\"\\']?[^>]*\\/>";
		
		/**
		 *  提取Img标签里面的src
		 *  <img[^>]*?src=[\"\\']?([^\"\\'>]+)[\"\\']?[^>]*\\/>
		 *  <img[^>]*?src=[\"\']?([^\"\'>]+)[\"\']?[^>]*\/>
		 */
		public final static String DISTILL_IMG_SRC="<img[^>]*?src=[\"\\']?([^\"\\'>]+)[\"\\']?[^>]*\\/>";
		
		/**
		 * 提取事个IMG标签，将整个img标签作为一个分组进行提取
		 */
		public final static String DISTILL_IMG_FULL="(<img[^>]*?\\/>)";

		/**
		 * JS
		 */
		// public static final String HTML_SCRIPT = "<script[\\s].*?>\\$\\{\\}\\s\\S.*?<\\/script>";
		// <script[\s].*?>[\W\w]*?<\/script>
		// <script[\\s].*?>[\\W\\w]*?<\\/script>
		// <script[^>]*?>[^>]*?<\\/script>
		public static final String HTML_SCRIPT = "<script[^>]*?>[\\W\\w]*?<\\/script>";

		public static final String REPLACE_DIV="<[\\s\\/]*?div[^>]*?>";
		public static final String HTML_DIV_START = "<div[^>]*?>";
		public static final String HTML_DIV_END = "<[\\s]*?\\/div>";
		public static final String HTML_NOTE = "<!--[\\W\\w]*?-->";

		/**
		 * 匹配INS
		 * exam:
		 * <ins class="adsbygoogle"
										style="display:inline-block;width:120px;height:600px"
										data-ad-client="ca-pub-1090193214637198"
										data-ad-slot="7813820853"></ins>
		 */
		public static final String HTML_INS = "<ins[^>]*?><\\/ins>";

		public static final String HTML_BUTTON = "<button[^>]*?>[\\W\\w]*?<\\/button>";

		/**
		 * 匹配input
		 */
		public static final String HTML_INPUT = "<input[^>]*?>";

		/**
		 * 匹配全部html元素
		 */
		public static final String HTML_ALL = "<[^>].*?>";
	    /**
	     * 匹配URL的正则表达式,如页面中的:http://www.sohu.com
	     * @author @haipenge 
	     * haipenge@gmail.com
	    *  Create Date:2015年1月19日
	     */
		static class URL {
			// 子域名
	       private static final String SUB_DOMAIN  = "(?i:[a-z0-9]|[a-z0-9][-a-z0-9]*[a-z0-9])";
	        
	        // 顶级域名
	       private static final String TOP_DOMAINS = 
	                            "(?x-i:com\\b        \n" +
	                            "     |edu\\b        \n" +
	                            "     |biz\\b        \n" +
	                            "     |in(?:t|fo)\\b \n" +
	                            "     |mil\\b        \n" +
	                            "     |net\\b        \n" +
	                            "     |org\\b        \n" +
	                            "     |[a-z][a-z]\\b \n" + // 国家代码
	                            ")                   \n";
	        // 主机名
	       private static  final String HOSTNAME = "(?:" + SUB_DOMAIN + "\\.)+" + TOP_DOMAINS;
	        
	        // URL 地址中不允许包括的字符，以及其他的条件
	       private static final String NOT_IN   = ";:\"'<>()\\[\\]{}\\s\\x7F-\\xFF";
	       private static final String NOT_END  = "!.,?";
	       private static  final String ANYWHERE = "[^" + NOT_IN + NOT_END + "]";
	       private static final String EMBEDDED = "[" + NOT_END + "]";
	       private static  final String URL_PATH = "/" + ANYWHERE + "*(" + EMBEDDED + "+" + ANYWHERE + "+)*";
	        
	        // 端口号 0～65535
	       private static  final String PORT = 
	            "(?:                          \n" +
	            "  [0-5]?[0-9]{1,4}           \n" +
	            "  |                          \n" +
	            "  6(?:                       \n" +
	            "     [0-4][0-9]{3}           \n" +
	            "     |                       \n" +
	            "     5(?:                    \n" +
	            "        [0-4][0-9]{2}        \n" +
	            "        |                    \n" +
	            "        5(?:                 \n" +
	            "           [0-2][0-9]        \n" +
	            "           |                 \n" +
	            "           3[0-5]            \n" +
	            "         )                   \n" +
	            "      )                      \n" +
	            "   )                         \n" +
	            ")";
	        
	        // URL 表达式
	        public static final String URL =
	            "(?x:                                                \n"+
	            "  \\b                                               \n"+
	            "  (?:                                               \n"+
	            "    (?: ftp | http s? ): // [-\\w]+(\\.\\w[-\\w]*)+ \n"+
	            "   |                                                \n"+
	            "    " + HOSTNAME + "                                \n"+
	            "  )                                                 \n"+
	            "  (?: : " + PORT + " )?                             \n"+
	            "  (?: " + URL_PATH + ")?                            \n"+
	            ")";
		}
		
		/**
		 * 匹配页面中URL的正则表达式
		 */
		public static final String PATTERN_URL=URL.URL;

		/**
		 * 替换页面中的所有A标签
		 */
		// public static final String REPLACE_ALL_A_HREF = "<a[^>]*?href=[\"\\']?[^\"\\'>]+[\"\\']?[^>]*>.+?<[\\s]*?\\/a>";
		// <a[^>]*?href=[\"\']?[^\"\'>]+[\"\']?[^>]*>[\W\w]*?<[\s]*?\/a>
		public static final String REPLACE_ALL_A_HREF_LEFT="<a[^>]*?href=[\"\\']?[^\"\\'>]+[\"\\']?[^>]*>";
		public static final String REPLACE_ALL_A_HREF_RIGHT="<[\\s]*?\\/a>";
		public static final String REPLACE_ALL_A_HREF = "<a[^>]*?href=[\"\\']?[^\"\\'>]+[\"\\']?[^>]*>[\\W\\w]*?<[\\s]*?\\/a>";

		/**
		 * 替换Iframe
		 */
		public static final String REPLACE_ALL_IFRAME = "<IFRAME[^>]*?><\\/IFRAME>";

		/**
		 * 替换页面中的所有IMG标签
		 */
		public static final String REPLACE_ALL_IMG = "<img[^>]*?>";

		/**
		 * 替换页面中的所有div标签
		 */
		public static final String REPLACE_ALL_DIV = "<[\\s]*?\\/?div[^>]*?>";

		/**
		 * Empty <ul></ul>
		 */
		public static final String REPLACE_EMPTY_UL = "<ul[^>]*?>[\\s\\n&nbsp;]*?<\\/ul>";

		/**
		 * Empty <li></li>
		 */
		public static final String REPLACE_EMPTY_LI = "<li[^>]*?>[\\s\\n&nbsp;]*?<\\/li>";

		/**
		 * Empty <h2></h2>
		 */
		public static final String REPLACE_EMPTY_H = "<h\\d{1}[^>]*?>[\\s\\n&nbsp;]*?<\\/h\\d{1}>";

		/**
		 * Empty <span></span>
		 */
		public static final String REPLACE_EMPTY_SPAN = "<span[^>]*?>[\\s\\n&nbsp;]*?<\\/span>";
		

		/**
		 * Empty <p></p>
		 * <p[^>]*?>[\ss\n&nbsp;]*?<\/p>
		 */
		public static final String REPLACE_EMPTY_P = "<p[^>]*?>[\\s\\n&nbsp;]*?<\\/p>";
		
		
		/**
		 * 替换空的<b></b>标签
		 */
		public static final String REPLACE_EMPTY_B="<b>[\\s\\n&nbsp;]*?<\\/b>";
		
		/**
		 * 连续的<br>
		 */
		public static final String REPLACE_BR="<br>[\\n&nbsp;]*?<br>[\\n&nbsp;]*?<br>";
		//<br[\s\/]*?>[\n&nbsp;]*?<br[\s\/]*?>
		public static final String REPLACE_BR_="<br[\\s\\/]*?>[\\n&nbsp;]*?<br[\\s\\/]*?>[\\n&nbsp;]*?<br[\\s\\/]*?>";
		//清除连续的两个br
		public static final String REPLACE_BR_2="<br[\\s\\/]*?>[\\n&nbsp;]*?<br[\\s\\/]*?>";
		
		
		/**
		 * 替换H标签
		 * <\/?\s?h\d[^>]*?>
		 */
		public static final String REPLACE_H_="<\\/?\\s?h\\d[^>]*?>";
		
		/**
		 * <font size="4">
		 * </font>
		 * <[\/\s]*?font[^>]*?>
		 */
		public static final String REPLACE_FONT_SIZE="<[\\/\\s]*?font[^>]*?>";
		
		/**
		 * 替换 strong
		 * <[\/\s]*?strong>
		 */
		public static final String REPLACE_STRONG="<[\\/\\s]*?strong>";
		
		/**
		 * 替换掉span
		 * <span style="font-family:SimHei; font-size:14px"></span>
		 * <[\/\s]*?span[^>]*?>
		 */
		public static final String REPLACE_SPAN="<[\\/\\s]*?span[^>]*?>";
		
		/**
		 * 替换flash标签
		 */
		public static final String REPLACE_FLASE="<embed[^>]*?>";

		/**
		 * 提取oschina博客列表页所有博客文章明细URL
		 */
		// <a href="http://my.oschina.net/Jacedy/blog/300872" target='_blank' title='Lambda表达式解析'>Lambda表达式解析</a>
		public static final String DISTILL_OSCHINA_BLOGS_LIST_LINKS = "<a[\\s]href=\"([^\"\\'>]+)\"[\\s]target=\\'_blank\\'[\\s]title=\\'[^>]*?\\'>[^>]*?</a>";

		/**
		 * 提取oschina 博客中中分类页
		 */
		// <a href="/blog?type=428602#catalogs" class='tag'>移动开发</a>
		public static final String DISTILL_OSCHINA_BLOG_CATEGORY_LINKS = "<a[\\s]href=\"([^\"\\'>]+)\"[\\s]class=\\'tag\\'>[^>]*?</a>";
		/**
		 * 提取OSchina博客内容详情
		 */
		public static final String DISTILL_OSCHINA_BLOG_DTAIL = "<div class=\\'BlogContent\\'>(.+?)</div>[\\s]*?<div class=\\'BlogShare\\'>";

		/**
		 * 提取csdn博客明细
		 */
		public static final String DISTILL_CSDN_BLOG_DETAIL = "<div id=\"article_content\" class=\"article_content\">(.+?)<div class=\"bdsharebuttonbox\" style=\"float: right;\">";

		/**
		 * 提取iteye博客明细
		 */
		public static final String DISTILL_ITEYE_BLOG_DETAIL = "<div class=\"blog_main\">(.+?)<div id=\"bottoms\" class=\"clearfix\">";

		/**
		 *  提取Cnblogs文章明细
		 */
		public static final String DISTIL_CNBLOGS_BODY = "<div id=\"cnblogs_post_body\">(.+?)<div id=\"blog_post_info_block\">";
		/**
		 * 提取Cnblog博客园子首页 文章明细链接
		 * http://home.cnblogs.com/blog/page/2/
		 */
		public static final String DISTIL_CNBLOGS_LIST_LINKS = "<a[\\s]href=\"([^\"\\'>]+)\"[\\s]target=\"_blank\">[^>]*?</a>";

}
