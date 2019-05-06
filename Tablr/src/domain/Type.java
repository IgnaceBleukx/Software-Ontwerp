package domain;

import Utils.BooleanCaster;

/**
 * Possible values for a cell.
 * Order: EMAIL, INTEGER, BOOLEAN, STRING
 */
public enum Type {
	
	EMAIL{
		public Object parseValue(String string) {
			if (string.equals("")) return null;
			return string;
		}
		
		public boolean isValidValue(String string){
			if (string == null || string.equals("")) return true;
			return (string.indexOf("@") == string.lastIndexOf("@") && string.indexOf("@") != -1);
		}
	},
	INTEGER{
		public Object parseValue(String string) {
			if (string.isEmpty()) return null;
			return Integer.parseInt(string);
		}
		
		public boolean isValidValue(String string){
			if (string.isEmpty() || string == null) return true;
			if (string.charAt(0) == '0' && string.length() > 1) return false;
			try {
				Integer.parseInt(string);
				return true;
			}catch (NumberFormatException e){
				return false;
			}
		}
		
	},
	BOOLEAN{
		public Object parseValue(String string){
			return BooleanCaster.cast(string);
		}
		
		public boolean isValidValue(String string){
			if (string == null || string.isEmpty()) return true;
			return (string.equals("False") || string.equals("false") || string.equals("True") || string.equals("true"));
		}
		
	},
	STRING{
		public Object parseValue(String string) {
			return string;
		}
		public boolean isValidValue(String string){
			return true;
		}
	};
	
	
	public abstract Object parseValue(String string);
	public abstract boolean isValidValue(String string);
	
}
