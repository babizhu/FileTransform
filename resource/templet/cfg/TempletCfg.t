package #packageName#;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模版配置
 * @author liukun
 * #date
 */
public class #className# {
	private static final Map<Integer,#templetClassName#> #mapName#s = new HashMap<>();


	static{
		//init();

	}
	private static final String FILE = "#xmlPath#";



	public static void init(){

		SAXBuilder builder = new SAXBuilder();
		Document document;
		try {
			document = builder.build( FILE );
			Element root = document.getRootElement();
			List<?> list = root.getChildren( "#xmlNode#" );

			for (Object o : list) {
				#templetClassName# templet = new #templetClassName#( (Element) o );
				#templetClassName# temp = #mapName#s.put( templet.getId(), templet );
				if( temp != null ){
					throw new RuntimeException( "#templetClassName# id [" + temp.getId() + "] 重复了" );
				}

			}
		} catch (JDOMException | IOException e) {
		    e.printStackTrace();
        }

		System.out.println( "#templetClassName# xml配置文件解析完毕" );
	}


	/**
	 * 通过id获取#templetClassName#的引用
	 * @param   templetId   id
	 * @return  返回一个引用
	 */
	public static #templetClassName# get#templetClassName#ById( int templetId ){
		return #mapName#s.get( templetId );
	}

	/*自定义代码开始*/
	$manualWork$
	/*自定义代码结束*/

	public static void main(String[] args) {

		int id = 100001;
		System.out.println( get#templetClassName#ById( id ) );
	}
}
