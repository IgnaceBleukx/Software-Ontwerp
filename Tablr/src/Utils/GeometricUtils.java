package Utils;

public class GeometricUtils {

	public static int[] intersection (int x1,int y1,int w1,int h1, int x2, int y2, int w2, int h2){
		int endX1 = x1 + w1;
		int endY1 = y1 + h1;
		
		int endX2 = x2 + w2;
		int endY2 = y2 + h2;
		
		int iL = Math.max(x1, x2);
		int iR = Math.min(endX1, endX2);
		int iT = Math.max(y1, y2);
		int iB = Math.min(endY1, endY2);
		
		return new int[]{iL,iT,iR-iL,iB-iT};
	}
	
}
