import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class TestDirections {


	public static void main(String[] args){
		IDirections d = Data.data();         //Daten aus Data
		IDirections directions = new Directions();
		TestDirections test = new TestDirections();

		Set<Airport> airportSet = test.createTestAirport();
		Set<Direct> directSet =  test.createTestDirect();

		// Initialisierung des Iterators um diesen wieder zurücksetzen zu können
		Iterator<Airport> iteratorAirport = airportSet.iterator();
		Iterator<Direct> iteratorDirect = directSet.iterator();

		// ==================================================================================
		// Test call

		// Testaufruf um die toString Methoden zu testen
		test.testToString( iteratorAirport.next(), iteratorAirport.next() );
		iteratorAirport = airportSet.iterator();

		// Testaufruf um die add Methode zu testen
		test.testAddDirectSingle( directions, iteratorDirect.next() );
		iteratorDirect = directSet.iterator();


		// Testaufruf um die Add(Set) Methode zu testen
		test.testAddDirectSet(directions, directSet);

		// Testaufruf der getAll-Methoden mit den Data-Daten
		test.testGetAll(d);

		//getAllSrcs Soll: [DME, CSX, XYZ, ABC, ZRH, TPE, AGP, FKB]
		//getAllDsts Soll: [DME, CSX, XYZ, ABC, ZRH, TPE, AGP, ORD, FKB]
		//getAllAirport Soll: [DME, CSX, XYZ, ABC, ZRH, TPE, AGP, ORD, FKB]


		// Testaufruf der getAll-Methoden mit den eigenen Datensätzen
		test.testGetAll(directions);

		// Testaufruf der getSrcs Methode


		test.testGetSrcs( directions, iteratorAirport.next() );  //Soll: []
		iteratorAirport.next();
		test.testGetSrcs( directions, iteratorAirport.next() ); //Soll: [MOS, FRA]

		test.testGetSrcs( directions,  new Airport("NIE" ) ); //Soll: []

		test.testGetSrcs( d, new Airport("TPE") ); // Soll: [ZRH]









//
//		//Test für getDsts(Airport src)
//		System.out.println("Test für getDsts(Airport src):");
//		System.out.println(d.getDsts(new Airport("ORD")));						//Soll: []
//		System.out.println(d.getDsts(new Airport("DME")));						//Soll: [CSX, FKB, ORD, ZRH]
//		System.out.println();
//
//		//Test für getDsts(Airport src, int numChanges)
//		System.out.println("Test für getDsts(Airport src, int numChanges):");
//		System.out.println(d.getDsts(new Airport("DME"), 1));		//Soll: [CSX, FKB, ORD, ZRH, DME, TPE]
//		System.out.println();
//
//		//Test für contains(Direct d)
//		System.out.println("Test für contains(Direct d):");
//		System.out.println(directions.contains(new Direct(airport1, airport2)));	//Soll: true
//		System.out.println(directions.contains(new Direct(airport6, airport1)));	//Soll: false
//		System.out.println();
//
//		//Test für getBidirectedAirports()
//		System.out.println("Test für getBidirectedAirports():");
//		System.out.println(directions.getBidirectedAirports());		//Soll: []
//		directions.add(new Direct(airport2, airport1));
//		System.out.println(directions.getBidirectedAirports());		//Soll: [[FRA, ZRH]]
//		System.out.println(d.getBidirectedAirports());				//Soll: [[CSX,DME], [ABC, XYZ]]
//		System.out.println();
//
//		//Test für addAll(IDirections other)
//		System.out.println("Test für addAll(IDirections other):");
//		System.out.println(directions.addAll(directions));			//Soll: false
//		IDirections tmpOtherDirect = setUpAnotherIDirections();
//		System.out.println(tmpOtherDirect.getAllAirports());		//Soll: [HN, BGH, MUN, KA]
//		System.out.println(tmpOtherDirect.addAll(directions));		//Soll: true
//		System.out.println(tmpOtherDirect.getAllAirports());		//Soll: [HN, BGH, MUN, KA, FRA, ZRH, CR, SHA, MOS, S]
//		System.out.println();
//
//
//		//Test für remove(Direct d)
//		System.out.println("Test für remove(Direct d):");
//		System.out.println(directions.remove(new Direct(airport6, airport1)));		//Soll: false
//		System.out.println(directions.getSrcs(airport2));							//Soll: [FRA]
//		System.out.println(directions.getAllDsts());								//Soll: [FRA, SHA, S, CR, ZRH]
//		System.out.println(directions.remove(new Direct(airport1, airport2)));		//Soll: true
//		System.out.println(directions.getSrcs(airport2));							//Soll: []
//		System.out.println(directions.getAllDsts());								//Soll: [FRA, SHA, S, CR]
//		System.out.println();
//
//
//		//Test für getRoute(Airport src, Airport dst)
//		System.out.println("Test für getRoute(Airport src, Airport dst):");
//		System.out.println(directions.getRoute(airport1, airport6));						//Soll: null
//		directions.add(new Direct(airport4, airport6));
//		System.out.println(directions.getRoute(airport1, airport6));						//Soll: [FRA->CR, CR->SHA, SHA->S]
//		System.out.println(d.getRoute(new Airport("DME"), new Airport("TPE")));		//Soll: [DME-ZRH, ZRH->TPE]
//		System.out.println();
//
//		//Test für minimalRoundtrip(Airport src)
//		System.out.println("Test für minimalRoundtrip(Airport src)");
//		System.out.println(directions.minimalRoundTrip(airport1));					//Soll: null
//		directions.add(new Direct(airport4, airport1));
//		System.out.println(directions.minimalRoundTrip(airport1));					//Soll: FRA, CR, SHA, FRA
//		System.out.println();
//
//		//Minitest von getAllDstsInfo
//		System.out.println("Test für getAllDstsInfo(Airport src)");
//		Map<Integer, Set<Airport>> dummyMap = d.getAllDstsInfo(new Airport("DME"));
//		for(int i : dummyMap.keySet())
//			//Gibt das Set der Airports, die mit i Umstiegen erreichbar sind aus
//			System.out.println("Anzahl der Umstiege: " + i + "Neue erreichbare Airports: " + dummyMap.get(i));
//		System.out.println();
	}


	// Test Method Section
	// =====================================


	public void testToString(Airport a1, Airport a2){

		System.out.println("\n");
		System.out.println("Test von toString-Methoden:");
		System.out.println("---------------------------\n");

		System.out.println("Ausgabe: ");
		System.out.println( a1 );
		System.out.println( a2) ;
		System.out.println( new Direct(a1, a2) );

	}

	public void testAddDirectSingle(IDirections directions, Direct d1){
		System.out.println("\n");
		System.out.println("Test von addDirect:");
		System.out.println("---------------------------\n");


		boolean isSuccessful = directions.add(d1);
		if( isSuccessful ) {
			System.out.println("Success!\n");
			System.out.println("Ausgabe:");
			System.out.println(directions.getAllAirports());
		}
		if( !isSuccessful )
			System.out.println("Bitte Prüfen, der Direct wurde nicht hinzugefügt");

		System.out.println("\n");
	}

	public void testAddDirectSet(IDirections directions, Set<Direct> directSet){

		System.out.println("\nTest von addDirect(Set):");
		System.out.println("---------------------------\n");


		boolean isSuccessful = directions.add( directSet );

		if( isSuccessful) {
			System.out.println("Success!\n");
			System.out.println("Ausgabe:");
			System.out.println( directions.getAllAirports() );

		}
		if( !isSuccessful ){
			System.out.println("Bitte Prüfen, das Set an Directs wurde nicht hinzugefügt");
		}
		System.out.println("\n");
	}

	public void testGetAll(IDirections data){

		System.out.println("\nTest von allen getAll* Methoden:");
		System.out.println("---------------------------");

		System.out.println("\n getAllSrcs: ");
		System.out.println( data.getAllSrcs() );

		System.out.println("\n getAllDsts: ");
		System.out.println(data.getAllDsts() );

		System.out.println("\n getAllAirports: ");
		System.out.println( data.getAllAirports() );


	}

	public void testGetSrcs(IDirections directions, Airport dst){

		System.out.println("\nTest von getSrcs Methode:");
		System.out.println("---------------------------\n");

		System.out.println("Ausgabe:");
		System.out.println( "Destination Airport: " + dst.toString() );
		System.out.println( "Sources: " + directions.getSrcs(dst) );


	}


	// Creation Method Section
	// ===================================


	public Set<Airport> createTestAirport(){

		Set<Airport> airportSet = new LinkedHashSet<>();

		airportSet.add( new Airport("FRA","Frankfurt") );
		airportSet.add( new Airport("ZRH", "Zürich") );
		airportSet.add( new Airport("CR","Crailsheim") );
		airportSet.add( new Airport("SHA", "Schwäbisch Hall") );
		airportSet.add( new Airport("MOS","Mosbach") );
		airportSet.add( new Airport("S", "Stuttgart") );


		return airportSet;

	}

	public Set<Direct> createTestDirect(){

		Set<Direct> directSet = new LinkedHashSet<>();

		Airport[] airportArr = new Airport[ this.createTestAirport().size() - 1 ];

		airportArr = this.createTestAirport().toArray(airportArr);

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
