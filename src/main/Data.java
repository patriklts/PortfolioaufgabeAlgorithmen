public class Data {
	public static String[][] getData() {
		String[][] dirs = new String[12][2];
		//first: src, second: dst
		dirs[0][0] = "DME";
		dirs[0][1] = "CSX";
		dirs[1][0] = "DME";
		dirs[1][1] = "FKB";
		dirs[2][0] = "DME";
		dirs[2][1] = "ORD";
		dirs[3][0] = "DME";
		dirs[3][1] = "ZRH";
		dirs[4][0] = "ZRH";
		dirs[4][1] = "FKB";
		dirs[5][0] = "ZRH";
		dirs[5][1] = "TPE";
		dirs[6][0] = "TPE";
		dirs[6][1] = "AGP";
		dirs[7][0] = "AGP";
		dirs[7][1] = "ZRH";
		dirs[8][0] = "FKB";
		dirs[8][1] = "CSX";
		dirs[9][0] = "CSX";
		dirs[9][1] = "DME";
		dirs[10][0] = "ABC";
		dirs[10][1] = "XYZ";
		dirs[11][0] = "XYZ";
		dirs[11][1] = "ABC";
		return dirs;
	}
	private static IDirections data2dirs( String[][] data ) {
		IDirections d = new Directions();
		for( String[] s : data ) {
			Airport a1 = new Airport(s[0]);
			Airport a2 = new Airport(s[1]);
			d.add( new Direct( a1, a2 ) );
		}
		return d;
	}
	public static IDirections data () {
		return data2dirs( getData() );
	}
}
