
public class TestDirections {

	public static void main(String[] args) {
		IDirections d = Data.data();


		Airport airport1 = new Airport("FRA","Frankfurt");
		Airport airport2 = new Airport("ZRH", "Zürich");
		Airport airport3 = new Airport("CR","Crailsheim");
		Airport airport4 = new Airport("SHA", "Schwäbisch Hall");
		Airport airport5 = new Airport("MOS","Mosbach");
		Airport airport6 = new Airport("S", "Stuttgart");

		System.out.println( airport1 );
		System.out.println( new Direct(airport1, airport2) );

		System.out.println(d.getAllDsts());

		Directions directions = new Directions();
		directions.add(new Direct(airport1, airport2));
		directions.add(new Direct(airport3, airport4));
		directions.add(new Direct(airport5, airport6));
		System.out.println(directions.getAllAirports());

		System.out.println(directions.getDsts(airport3));

	}

}
