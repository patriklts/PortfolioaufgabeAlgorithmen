
public class TestDirections {

	public static void main(String[] args) {
		IDirections d = Data.data();


		Airport airport1 = new Airport("FRA","Frankfurt");
		Airport airport2 = new Airport("ZRH", "Zürich");

		System.out.println( airport1 );
		System.out.println( new Direct(airport1, airport2) );
		
	}

}
