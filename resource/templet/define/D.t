package #packageName#;

import com.hz.dafeiji.cfg.manual.define.DefineTempletCfg;

/**
 * 常数定义，自动生成，勿改
 * @author liukun
 * #date
 */
 @SuppressWarnings("UnusedDeclaration")
public class #CLASS_NAME# {

#FILEDS#
		public static void reload() {
#RELOAD_ALL#
		}
		
		private static int getInt(String s) {
			return new Integer(get(s));
		}

		private static float getFloat(String s) {
			return new Float(get(s));
		}

		private static Boolean getBoolean(String s) {
			return Boolean.valueOf( get( s ) );
		}

		private static String getString(String s) {
			String string = get(s);
			if(string.endsWith("\"") && string.startsWith("\"")) {
				string = string.substring(1, string.length() - 1);
			}
			return string;
		}

		private static String get(String s) {
			return DefineTempletCfg.get(s).getValue().trim();
		}

		private static double getDouble(String s) {
			return new Double(get(s));
		}
}