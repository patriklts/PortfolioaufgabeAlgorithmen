import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class TestDirections {


	public static void main(String[] args){
		IDirections d = Data.data();         //Daten aus Data
		IDirections directions = new Directions();


		Set<Airport> airportSet = createTestAirport();
		Set<Direct> directSet =  createTestDirect();

		// Initialisierung des Iterators um diesen wieder zurücksetzen zu können
		Iterator<Airport> iteratorAirport = airportSet.iterator();

		// ==================================================================================
		// Test call

		// Testaufruf um die toString Methoden zu testen
		System.out.println("---------------------------");
		System.out.println("Test von toString-Methoden:");
		System.out.println("---------------------------");

		System.out.println("Ausgabe Airports: ");
		airportSet.forEach(TestDirections::testToString);
		System.out.println("Ausgabe Directs: ");
		for(Airport airport : airportSet)
			if(iteratorAirport.hasNext())
				testToString( iteratorAirport.next(), iteratorAirport.next() );
		iteratorAirport = airportSet.iterator();



		// Testaufruf um die add Methode zu testen
		System.out.println("\n---------------------------");
		System.out.println("Test von addDirect:");
		System.out.println("---------------------------");
		directSet.forEach(direct -> testAddDirectSingle(directions, direct));



		// Testaufruf um die Add(Set) Methode zu testen
		System.out.println("\n---------------------------");
		System.out.println("Test von addDirect(Set):");
		System.out.println("---------------------------");

		testAddDirect(directions, directSet);


		// Testaufruf der getAll-Methoden mit den Data-Daten
		System.out.println("\n---------------------------");
		System.out.println("Test von allen getAll* Methoden mit gegebenen Datensätzen:");
		System.out.println("---------------------------");
		testGetAll(d);

		//getAllSrcs Soll: [DME, CSX, XYZ, ABC, ZRH, TPE, AGP, FKB]
		//getAllDsts Soll: [DME, CSX, XYZ, ABC, ZRH, TPE, AGP, ORD, FKB]
		//getAllAirport Soll: [DME, CSX, XYZ, ABC, ZRH, TPE, AGP, ORD, FKB]


		// Testaufruf der getAll-Methoden mit den eigenen Datensätzen
		System.out.println("\n---------------------------");
		System.out.println("Test von allen getAll* Methoden mit eigenen Datensätzen:");
		System.out.println("---------------------------");
		testGetAll(directions);

		// Testaufruf der getSrcs Methode
//		test.testGetSrcs( directions, iteratorAirport.next() );  //Soll: []
//		test.testGetSrcs( directions, iteratorAirport.next() ); //Soll: [MOS, FRA]
		System.out.println("\n---------------------------");
		System.out.println("Test von getSrcs Methode:");
		System.out.println("---------------------------");
		System.out.println("Ausgabe:");
		airportSet.forEach(airport -> testGetSrcs(directions, airport));


		// Testaufruf der GetDst Methode
		System.out.println("\n---------------------------");
		System.out.println("Test von getDst Methode:");
		System.out.println("---------------------------");
		System.out.println("Ausgabe:");
		airportSet.forEach(airport -> testGetDst(directions, airport));


//		//Test für getDsts(Airport src, int numChanges)

		System.out.println("\n---------------------------");
		System.out.println("Test von getDst-Methode mit angabe Changes:");
		System.out.println("---------------------------");
		System.out.println("Ausgabe:");
		int changes = (int) (Math.random() *10) -1 ;
		System.out.println("Anzahl Changes: " + changes);
		airportSet.forEach(airport -> testGetDst( directions, airport, changes ) );



//
//		//Test für contains(Direct d)
		System.out.println("\n---------------------------");
		System.out.println("Test von contains Methode:");
		System.out.println("---------------------------");
		directSet.forEach(direct -> testContains(directions, direct ));


//		//Test für getBidirectedAirports()
		System.out.println("\n---------------------------");
		System.out.println("Test von BiDirectedAirports Methode:");
		System.out.println("---------------------------");
		testGetBidirectedAirports(d);


//		//Test für addAll(IDirections other)
		System.out.println("\n---------------------------");
		System.out.println("Test von addAll (Interface IDirections DatenTyp) Methode:");
		System.out.println("---------------------------");
		IDirections other = setUpAnotherIDirections();
		testAddAll(directions, other);


//		//Test für getRoute(Airport src, Airport dst)
		System.out.println("\n---------------------------");
		System.out.println("Test von der getRoute Methode:");
		System.out.println("---------------------------");
		for(Airport airport : airportSet)
			if(iteratorAirport.hasNext())
				testGetRoute( directions, airportSet.iterator().next() , airportSet.iterator().next() );
//				testGetRoute(d, new Airport("DME"), new Airport("TPE") );


//		Test für minimalRoundtrip(Airport src)
		System.out.println("\n---------------------------");
		System.out.println("Test von der minimalRoundTrip Methode:");
		System.out.println("---------------------------");
		airportSet.forEach( airport -> testMinimalRoundTrip(d, airport) );

//
//		Minitest von getAllDstsInfo
		System.out.println("\n---------------------------");
		System.out.println("Test von der getAllDstsInfo Methode:");
		System.out.println("---------------------------");
		airportSet.forEach(airport -> testGetAllDstsInfo(d, airport));

		//Test für remove(Direct d)
		System.out.println("\n---------------------------");
		System.out.println("Test von der remove Methode:");
		System.out.println("---------------------------");
		directSet.forEach(direct -> testRemove(directions, direct));

	}


	/*
	 * =========================
	 *  Test Method Section
	 * =========================
	 */

	public static void testToString(Airport a1){

		System.out.println( a1 );
		//System.out.println( new Direct(a1, a2 ) );

	}
	public static void testToString(Airport a1, Airport a2){

		System.out.println( new Direct(a1, a2 ) );
	}

	public static void testAddDirectSingle(IDirections directions, Direct d){

		boolean isSuccessful = directions.add(d);
		if( isSuccessful ) {
			System.out.println("Success!");
			System.out.println("Ausgabe:");
			System.out.println("Hinzugefügtes Element: " + d);
			System.out.println(directions.getAllAirports());
		}
		if( !isSuccessful )
			System.out.println("Bitte Prüfen, der Direct " + d + " wurde nicht hinzugefügt");

		System.out.println("\n");
	}

	public static void testAddDirect(IDirections directions, Set<Direct> directSet){
		System.out.println();
		boolean isSuccessful = directions.add( directSet );

		if( isSuccessful) {
			System.out.println("Success!");
			System.out.println("Ausgabe:");
			System.out.println( directions.getAllAirports() );

		}
		if( !isSuccessful ){
			System.out.println("Bitte Prüfen, das Set " + directSet + " wurde nicht hinzugefügt");
		}
	}

	public static void testGetAll(IDirections data){

		System.out.println("\n getAllSrcs: ");
		System.out.println( data.getAllSrcs() );

		System.out.println("\n getAllDsts: ");
		System.out.println(data.getAllDsts() );

		System.out.println("\n getAllAirports: ");
		System.out.println( data.getAllAirports() );


	}

	public static void testGetSrcs(IDirections directions, Airport dst){

		System.out.println();
		System.out.println( "Destination Airport: " + dst.toString() );
		System.out.println( "Sources: " + directions.getSrcs(dst) );


	}

	public static void testGetDst(IDirections directions, Airport src){
		System.out.println();
		System.out.println( "Source Airport: " + src.toString() );
		System.out.println( "Destinations: " + directions.getDsts(src) );
	}

	public static void testGetDst(IDirections directions, Airport src, int changes){
		System.out.println();
		System.out.println( "Source Airport: " + src.toString() );
		System.out.println( "Destinations: " + directions.getDsts(src, changes) );
	}

	public static void testContains(IDirections directions, Direct direct){
		System.out.println();
		boolean isSuccessful = directions.contains(direct);
		if(isSuccessful) {
			System.out.println("Success!");
			System.out.println(direct + " wurde in " + directions.getAllAirports() + " gefunden");
		}
		if(!isSuccessful)
			System.out.println(direct + " wurde nicht in " + directions.getAllAirports() + " gefunden");
	}
	public static void testGetBidirectedAirports(IDirections directions){
		System.out.println();
		System.out.println( directions.getBidirectedAirports() );
	}

	public static void testAddAll(IDirections directions, IDirections other){
		System.out.println();
		boolean addAll = directions.addAll(other);
		if(addAll)
			System.out.println(other.getAllAirports() + " wurde erfolgreich in " + directions.getAllAirports() + " hinzugefügt");
		if(!addAll)
			System.out.println(other.getAllAirports() + " wurde nicht erfolgreich in " +  directions.getAllAirports() +  " hinzugefügt");
	}

	public static void testRemove(IDirections directions, Direct direct){
		System.out.println();
		boolean isSucessful = directions.remove(direct);
		if(isSucessful)
			System.out.println(direct + " wurde erfolgreich aus " + directions.getAllAirports() + " entfernt");
		if(!isSucessful)
			System.out.println("Bitte prüfen " + direct + " wurde NICHT aus " + directions.getAllAirports() + " entfernt");
	}

	public static void testGetRoute(IDirections directions, Airport src, Airport dst){
		System.out.println();
		System.out.println(directions.getRoute(src, dst));
	}

	public static void testMinimalRoundTrip(IDirections directions, Airport src){
		System.out.println();
		System.out.println(directions.minimalRoundTrip(src));
	}

	public static void testGetAllDstsInfo(IDirections directions, Airport airport){
		System.out.println();
		Map<Integer, Set<Airport>> dummyMap = directions.getAllDstsInfo(airport);
		System.out.println("Airport: " + airport);
		for(int i : dummyMap.keySet()){
			System.out.println("Anzahl der Umstiege: " + i + " erreichbare Airports: " + dummyMap.get(i));
		}
	}

	/*
	 * =========================
	 *  Create Test Data Section
	 * =========================
	 */

	public static Set<Airport> createTestAirport(){

		Set<Airport> airportSet = new LinkedHashSet<>();

		airportSet.add( new Airport("FRA","Frankfurt") );
		airportSet.add( new Airport("ZRH", "Zürich") );
		airportSet.add( new Airport("CR","Crailsheim") );
		airportSet.add( new Airport("SHA", "Schwäbisch Hall") );
		airportSet.add( new Airport("MOS","Mosbach") );
		airportSet.add( new Airport("S", "Stuttgart") );


		return airportSet;

	}

	public static Set<Direct> createTestDirect(){

		Set<Direct> directSet = new LinkedHashSet<>();

		Airport[] airportArr = new Airport[ createTestAirport().size() - 1 ];

		airportArr = createTestAirport().toArray(airportArr);

		directSet.add( new Direct( airportArr[0], airportArr[1] )  );
		directSet.add( new Direct( airportArr[2], airportArr[3] )  );
		directSet.add( new Direct( airportArr[4], airportArr[5] )  );
		directSet.add( new Direct( airportArr[0], airportArr[2] )  );
		directSet.add( new Direct( airportArr[4], airportArr[2] )  );

		return directSet;

	}


	public static IDirections setUpAnotherIDirections(){
		//Setup von neuem IDirections für addAll(IDirections other)
		IDirections otherDirections = new Directions();

		//Erstellen von DummyAirports
		Airport airport1 = new Airport("HN","Heilbronn");
		Airport airport2 = new Airport("BGH", "Bad Mergentheim");
		Airport airport3 = new Airport("MUN","Muenchen");
		Airport airport4 = new Airport("KA", "Karlsruhe");

		//Directs erstellen mit DummyAirports
		Direct tmpDirect1 = new Direct(airport1, airport2);
		Direct tmpDirect2 = new Direct(airport3, airport4);

		//Airports zu otherDirections hinzufügen
		otherDirections.add(tmpDirect1);
		otherDirections.add(tmpDirect2);

		return otherDirections;
	}



}
