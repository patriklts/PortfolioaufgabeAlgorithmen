import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class TestDirections {


	public static void main(String[] args){
		IDirections d = Data.data();			//Daten aus Data
		IDirections ownDirections = new Directions();			//Eigene Instanz von Directions für eigene Test-Daten

		//Sets mit eigenen erstellen Test-Daten
		Set<Airport> ownDataAirportSet = createTestAirport();
		Set<Direct> ownDataDirectSet =  createTestDirect();
		//Set mit Daten aus Data.java
		Set<Airport> givenDataAirportSet = createGivenDataAirportSet();
		Set<Direct> givenDataDirectSet = createGivenDataDirectSet();

		// Initialisierung des Iterators um diesen wieder zurücksetzen zu können
		Iterator<Airport> iteratorAirport = ownDataAirportSet.iterator();		//Iterator??? TODO

		/*
		 * =========================
		 *  Test Call Section
		 * =========================
		 */

		// Testaufruf um die toString Methoden zu testen
		System.out.println("---------------------------");
		System.out.println("Test von toString-Methoden mit eigenen Datensätzen:");
		System.out.println("---------------------------");
		System.out.println("Ausgabe Airports: ");
		ownDataAirportSet.forEach(TestDirections::testToString);
		System.out.println("Ausgabe Directs: ");
		ownDataDirectSet.forEach(TestDirections::testToString);

		System.out.println("---------------------------");
		System.out.println("Test von toString-Methoden mit gegebenen Datensätzen:");
		System.out.println("---------------------------");
		System.out.println("Ausgabe Airports: ");
		givenDataAirportSet.forEach(TestDirections::testToString);
		System.out.println("Ausgabe Directs: ");
		givenDataDirectSet.forEach(TestDirections::testToString);



		// Testaufruf um die add Methode zu testen
		System.out.println("\n---------------------------");
		System.out.println("Test von addDirect:");
		System.out.println("---------------------------");
		ownDataDirectSet.forEach(direct -> testAddDirectSingle(ownDirections, direct));



		// Testaufruf um die Add(Set) Methode zu testen
		System.out.println("\n---------------------------");
		System.out.println("Test von addDirect(Set):");
		System.out.println("---------------------------");
		testAddDirect(ownDirections, ownDataDirectSet);



		// Testaufruf der getAll-Methoden
		System.out.println("\n---------------------------");
		System.out.println("Test von allen getAll* Methoden mit eigenen Datensätzen:");
		System.out.println("---------------------------");
		testGetAll(ownDirections);

		System.out.println("\n---------------------------");
		System.out.println("Test von allen getAll* Methoden mit gegebenen Datensätzen:");
		System.out.println("---------------------------");
		testGetAll(d);
		//getAllSrcs Soll: [DME, CSX, XYZ, ABC, ZRH, TPE, AGP, FKB]
		//getAllDsts Soll: [DME, CSX, XYZ, ABC, ZRH, TPE, AGP, ORD, FKB]
		//getAllAirport Soll: [DME, CSX, XYZ, ABC, ZRH, TPE, AGP, ORD, FKB]



		// Testaufruf der getSrcs Methode
		System.out.println("\n---------------------------");
		System.out.println("Test von getSrcs Methode mit eigenen Datensätzen:");
		System.out.println("---------------------------");
		System.out.println("Ausgabe:");
		ownDataAirportSet.forEach(airport -> testGetSrcs(ownDirections, airport));

		System.out.println("\n---------------------------");
		System.out.println("Test von getSrcs Methode mit gegebenen Datensätzen:");
		System.out.println("---------------------------");
		System.out.println("Ausgabe:");
		givenDataAirportSet.forEach(airport -> testGetSrcs(d, airport));



		// Testaufruf der GetDst Methode
		System.out.println("\n---------------------------");
		System.out.println("Test von getDst Methode mit eigenen Datensätzen:");
		System.out.println("---------------------------");
		System.out.println("Ausgabe:");
		ownDataAirportSet.forEach(airport -> testGetDst(ownDirections, airport));

		System.out.println("\n---------------------------");
		System.out.println("Test von getDst Methode mit gegeben Datensätzen:");
		System.out.println("---------------------------");
		System.out.println("Ausgabe:");
		givenDataAirportSet.forEach(airport -> testGetDst(d, airport));



		//Test für getDsts(Airport src, int numChanges)
		System.out.println("\n---------------------------");
		System.out.println("Test von getDst-Methode mit angabe Changes mit eigenen Datensätzen:");
		System.out.println("---------------------------");
		System.out.println("Ausgabe:");
		int changes = (int) (Math.random() *10) -1 ;
		System.out.println("Anzahl Changes: " + changes);
		ownDataAirportSet.forEach(airport -> testGetDst( ownDirections, airport, changes ) );

		System.out.println("\n---------------------------");
		System.out.println("Test von getDst-Methode mit angabe Changes mit gegebenen Datensätzen:");
		System.out.println("---------------------------");
		System.out.println("Ausgabe:");
		System.out.println("Anzahl Changes: " + changes);
		givenDataAirportSet.forEach(airport -> testGetDst( d, airport, changes ) );



		//Test für contains(Direct d)
		System.out.println("\n---------------------------");
		System.out.println("Test von contains Methode mit eigenen Datensätzen:");
		System.out.println("---------------------------");
		ownDataDirectSet.forEach(direct -> testContains(ownDirections, direct ));

		System.out.println("\n---------------------------");
		System.out.println("Test von contains Methode mit gegebenen Datensätzen:");
		System.out.println("---------------------------");
		givenDataDirectSet.forEach(direct -> testContains(d, direct ));



		//Test für getBidirectedAirports()
		System.out.println("\n---------------------------");
		System.out.println("Test von BiDirectedAirports Methode mit eigenen Datensätzen:");
		System.out.println("---------------------------");
		testGetBidirectedAirports(ownDirections);

		System.out.println("\n---------------------------");
		System.out.println("Test von BiDirectedAirports Methode mit gegebenen Datensätzen:");
		System.out.println("---------------------------");
		testGetBidirectedAirports(d);



		//Test für minimalRoundtrip(Airport src)
		System.out.println("\n---------------------------");
		System.out.println("Test von der minimalRoundTrip Methode mit eigenen Datensätzen:");
		System.out.println("---------------------------");
		ownDataAirportSet.forEach( airport -> testMinimalRoundTrip(ownDirections, airport) );

		System.out.println("\n---------------------------");
		System.out.println("Test von der minimalRoundTrip Methode mit gegebenen Datensätzen:");
		System.out.println("---------------------------");
		givenDataAirportSet.forEach( airport -> testMinimalRoundTrip(d, airport) );



		//Test für addAll(IDirections other)
		System.out.println("\n---------------------------");
		System.out.println("Test von addAll (Interface IDirections DatenTyp) Methode:");
		System.out.println("---------------------------");
		IDirections other = setUpAnotherIDirections();
		testAddAll(ownDirections, other);



		//Test für getRoute(Airport src, Airport dst)					//TODO ganz komische Testmethode -> Man müsste jegliche Kombinationsmöglichkeit reinwerfen HOW?!!!!
		System.out.println("\n---------------------------");
		System.out.println("Test von der getRoute Methode mit eigenen Datensätzen:");
		System.out.println("---------------------------");
		iteratorAirport = ownDataAirportSet.iterator();
		iteratorAirport = nextIterator(iteratorAirport,2);
		if( iteratorAirport.hasNext() && ownDataAirportSet.iterator().hasNext() )
				testGetRoute( ownDirections, ownDataAirportSet.iterator().next() , iteratorAirport.next() );
//				testGetRoute(d, new Airport("DME"), new Airport("TPE") );



		//Test für getAllDstsInfo
		System.out.println("\n---------------------------");
		System.out.println("Test von der getAllDstsInfo Methode mit eigenen Datensätzen:");
		System.out.println("---------------------------");
		ownDataAirportSet.forEach(airport -> testGetAllDstsInfo(ownDirections, airport));

		System.out.println("\n---------------------------");
		System.out.println("Test von der getAllDstsInfo Methode mit gegebenen Datensätzen:");
		System.out.println("---------------------------");
		givenDataAirportSet.forEach(airport -> testGetAllDstsInfo(d, airport));



		//Test für remove(Direct d)
		System.out.println("\n---------------------------");
		System.out.println("Test von der remove Methode mit eigenen Datensätzen:");
		System.out.println("---------------------------");
		ownDataDirectSet.forEach(direct -> testRemove(ownDirections, direct));
		testRemove(ownDirections, new Direct(new Airport("MOS"), new Airport("S") ) );

		System.out.println("\n---------------------------");
		System.out.println("Test von der remove Methode mit gegebenen Datensätzen:");
		System.out.println("---------------------------");
		givenDataDirectSet.forEach(direct -> testRemove(d, direct));
		testRemove(d, new Direct(new Airport("FKB"), new Airport("CSX") ) );
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
	public static void testToString(Direct direct){

		System.out.println( direct );
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
			System.out.println(other.getAllAirports() + " wurde erfolgreich hinzugefügt. Aktuelle Struktur: " + directions.getAllAirports());
		if(!addAll)
			System.out.println(other.getAllAirports() + " wurde nicht erfolgreich in " +  directions.getAllAirports() +  " hinzugefügt");
	}

	public static void testRemove(IDirections directions, Direct direct){
		System.out.println();
		boolean isSuccessful = directions.remove(direct);
		if(isSuccessful)
			System.out.println(direct + " wurde erfolgreich entfernt. Aktuelle Struktur: " + directions.getAllAirports());
		if(!isSuccessful)
			System.out.println("Bitte prüfen " + direct + " wurde NICHT aus " + directions.getAllAirports() + " entfernt");
	}

	public static void testGetRoute(IDirections directions, Airport src, Airport dst){
		System.out.println();
		System.out.println("Airport Source: " + src);
		System.out.println("Airport Destination: "+ dst);
		System.out.println(directions.getRoute(src, dst));
	}

	public static void testMinimalRoundTrip(IDirections directions, Airport src){
		System.out.println();
		System.out.println("Minimaler Roundtrip für: " + src + ", " + directions.minimalRoundTrip(src));
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

		airportSet.add( new Airport("FRA","Frankfurt") ); // 0
		airportSet.add( new Airport("ZRH", "Zürich") ); // 1
		airportSet.add( new Airport("CR","Crailsheim") ); // 2
		airportSet.add( new Airport("SHA", "Schwäbisch Hall") ); // 3
		airportSet.add( new Airport("MOS","Mosbach") ); // 4
		airportSet.add( new Airport("S", "Stuttgart") ); // 5


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

	public static Set<Airport> createGivenDataAirportSet(){

		Set<Airport> airportSet = new LinkedHashSet<>();

		airportSet.add( new Airport("DME") );
		airportSet.add( new Airport("CSX") );
		airportSet.add( new Airport("FKB") );
		airportSet.add( new Airport("ORD") );
		airportSet.add( new Airport("ZRH") );
		airportSet.add( new Airport("TPE") );
		airportSet.add( new Airport("AGP") );
		airportSet.add( new Airport("XYZ") );
		airportSet.add( new Airport("ABC") );



		return airportSet;

	}

	public static Set<Direct> createGivenDataDirectSet(){

		Set<Direct> directSet = new LinkedHashSet<>();

		directSet.add( new Direct( new Airport("DME"), new Airport("CSX") )  );
		directSet.add( new Direct( new Airport("DME"), new Airport("FKB") )  );
		directSet.add( new Direct( new Airport("DME"), new Airport("ORD") )  );
		directSet.add( new Direct( new Airport("DME"), new Airport("ZRH") )  );
		directSet.add( new Direct( new Airport("ZRH"), new Airport("FKB") )  );
		directSet.add( new Direct( new Airport("ZRH"), new Airport("TPE") )  );
		directSet.add( new Direct( new Airport("TPE"), new Airport("AGP") )  );
		directSet.add( new Direct( new Airport("AGP"), new Airport("ZRH") )  );
		directSet.add( new Direct( new Airport("FKB"), new Airport("CSX") )  );
		directSet.add( new Direct( new Airport("CSX"), new Airport("DME") )  );
		directSet.add( new Direct( new Airport("ABC"), new Airport("XYZ") )  );
		directSet.add( new Direct( new Airport("XYZ"), new Airport("ABC") )  );

		return directSet;

	}

	/*
	 * =========================
	 *  Help Method Section
	 * =========================
	 */

	public static Iterator<Airport> nextIterator(Iterator<Airport> iterator, int hops){
		int counter = 0;

		while( iterator.hasNext() && counter <= hops){
			counter++;
			iterator.next();
		}

		return iterator;
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
