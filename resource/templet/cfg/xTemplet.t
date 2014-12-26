package #packageName#;

import org.jdom2.Element;

/**
 * 模版
 * @author liukun
 * #date
 */
public class #className# {

    #fieldArea#

	public #className#( Element element ) {
		#construct#
	}

	@Override
	public String toString() {
		return "#className# [#toString# + "]";
	}

	/*自定义代码开始*/$manualWork$/*自定义代码结束*/
}
