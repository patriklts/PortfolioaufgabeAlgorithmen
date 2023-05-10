import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class TestDirections {
	public static void main(String[] args){

		IDirections d = Data.data();			//Daten aus Data

		//Set mit Daten aus Data.java
		Set<Airport> givenDataAirportSet = createGivenDataAirportSet();
		Set<Direct> givenDataDirectSet = createGivenDataDirectSet();


		/*
		 * =========================
		 *  Test Call Section
		 * =========================
		 */


		// Testaufruf um die toString Methoden zu testen
		/* Kontrollwerte:
		 * Airports:
		 * DME, CSX, FKB, ORD, ZRH, TPE, AGP, XYZ, ABC
		 * Directs:
		 * DME->CSX, DME->FKB, DME->ORD
		 *
		 */
		System.out.println("---------------------------");
		System.out.println("Test von toString-Methoden mit gegebenen Datensätzen:");
		System.out.println("---------------------------");
		System.out.println("Ausgabe Airports: ");
		givenDataAirportSet.forEach(TestDirections::testToString);
		System.out.println("Ausgabe Directs: ");
		givenDataDirectSet.forEach(TestDirections::testToString);

		// Testaufruf um die add Methode zu testen
		/* Kontrollwerte:
		 * Für d alles false, da bereits enthalten
		 * <FRA->ZRH, true>, <Test1->Test2, true>, <null->null, false>
		 */
		System.out.println("\n---------------------------");
		System.out.println("Test von addDirect:");
		System.out.println("---------------------------");
		givenDataDirectSet.forEach(direct -> testAddDirect(d,  direct));
		testAddDirect(new Directions(), new Direct(new Airport("FRA"), new Airport("ZRH")));
		testAddDirect(new Directions(), new Direct(new Airport("Test1"), new Airport("Test2")));
		testAddDirect(new Directions(), new Direct(null, null));
		testAddDirect(null, new Direct(null, null));

		// Testaufruf um die Add(Set) Methode zu testen
		/* Kontrollwerte:
		 * Für d alles false, da bereits enthalten.
		 * Für neues Directions sollten alle Werte hinzugefügt werden.
		 */
		System.out.println("\n---------------------------");
		System.out.println("Test von addDirect(Set):");
		System.out.println("---------------------------");
		testAddDirect(d, givenDataDirectSet);
		testAddDirect(new Directions(), givenDataDirectSet);
		testAddDirect(d, new HashSet<>());

		// Testaufruf der getAll-Methoden
		/* Kontrollwerte:
		 * getAllSrcs Soll: [DME, CSX, XYZ, ABC, ZRH, TPE, AGP, FKB]
		 * getAllDsts Soll: [DME, CSX, XYZ, ABC, ZRH, TPE, AGP, ORD, FKB]
		 * getAllAirport Soll: [DME, CSX, XYZ, ABC, ZRH, TPE, AGP, ORD, FKB]
		 */
		System.out.println("\n---------------------------");
		System.out.println("Test von allen getAll* Methoden mit gegebenen Datensätzen:");
		System.out.println("---------------------------");
		testGetAll(d);
		testGetAll(null);

		// Testaufruf der getSrcs Methode
		/* Kontrollwerte:
		 * <null, []>, <ABC, XYZ>, <FKB, [DME, CSX, ZRH]>
		 */
		System.out.println("\n---------------------------");
		System.out.println("Test von getSrcs Methode mit gegebenen Datensätzen:");
		System.out.println("---------------------------");
		System.out.println("Ausgabe:");
		givenDataAirportSet.forEach(airport -> testGetSrcs(d, airport));
		testGetSrcs(null, null);
		testGetSrcs(d, null);
		testGetSrcs(null, new Airport("JFK"));

		// Testaufruf der GetDst Methode
		/* Kontrollwerte:
		 * <null, []>, <ABC, [XYZ]>, <FKB, [CSX]>, <DME, [CSX, FKB, ORD, ZRH]>
		 */
		System.out.println("\n---------------------------");
		System.out.println("Test von getDst Methode mit gegebenen Datensätzen:");
		System.out.println("---------------------------");
		System.out.println("Ausgabe:");
		givenDataAirportSet.forEach(airport -> testGetDst(d, airport));
		testGetDst(null, null);
		testGetDst(null, new Airport("ZRH"));
		testGetDst(d, null);

		// Test für getDsts(Airport src, int numChanges)
		/* Kontrollwerte:
		 * <null, []>, <ABC, [XYZ, ABC]>, <FKB, [CSX, DME, FKB, ORD, ZRH]>
		 */
		int changes = 2;
		System.out.println("\n---------------------------");
		System.out.println("Test von getDst-Methode mit angabe Changes mit gegebenen Datensätzen:");
		System.out.println("---------------------------");
		System.out.println("Ausgabe:");
		System.out.println("Anzahl Changes: " + changes);
		givenDataAirportSet.forEach(airport -> testGetDst( d, airport, changes ) );
		testGetDst(null, null, changes);
		testGetDst(d, null, changes);
		testGetDst(null, new Airport("JFK"), changes);

		// Test für contains(Direct d)
		/* Kontrollwerte:
		 * <null, false>, <ABC->XYZ, true>, <FKB->DME, false>
		 */
		System.out.println("\n---------------------------");
		System.out.println("Test von contains Methode mit gegebenen Datensätzen:");
		System.out.println("---------------------------");
		givenDataDirectSet.forEach(direct -> testContains(d, direct ));
		testContains(null, new Direct(new Airport("JFK"), new Airport("ZRH")));
		testContains(d, null);

		// Test für getBidirectedAirports()
		/* Kontrollwerte:
		 * <[[DME, CSX], [FKB, CSX], [XYZ, ABC]]>
		 */
		System.out.println("\n---------------------------");
		System.out.println("Test von BiDirectedAirports Methode mit gegebenen Datensätzen:");
		System.out.println("---------------------------");
		testGetBidirectedAirports(d);
		testGetBidirectedAirports(null);

		// Test für minimalRoundtrip(Airport src)
		/* Kontrollwerte:
		 * <ORD, null>, <DME, [DME, CSX, DME]>, <ZRH, [ZRH, TPE, AGP, ZRH]>, <null, null>
		 */
		System.out.println("\n---------------------------");
		System.out.println("Test von der minimalRoundTrip Methode mit gegebenen Datensätzen:");
		System.out.println("---------------------------");
		givenDataAirportSet.forEach( airport -> testMinimalRoundTrip(d, airport) );
		testMinimalRoundTrip(d, null);
		testMinimalRoundTrip(null, new Airport("JFK"));

		// Test für getRoute(Airport src, Airport dst)
		/* Kontrollwerte:
		 * <ORD, DME, []>, <DME, ORD, [DME->ORD]>,
		 * <DME, AGP, [DME->ZRH, ZRH->TPE, TPE->AGP]>, <null, null, []>
		 */
		System.out.println("\n---------------------------");
		System.out.println("Test von der getRoute Methode mit gegebenen Datensätzen:");
		System.out.println("---------------------------");
		testGetRoute(d, givenDataAirportSet);
		testGetRoute(null, null);
		testGetRoute(null, givenDataAirportSet);
		testGetRoute(d, null);

		// Test für getAllDstsInfo
		/* Kontrollwerte:
		 * <ORD, 0: []>, <CSX, 0: [DME] 1: [ORD, CSX, FKB, ZRH] 2: [TPE] 3: [AGP]>,
		 * <AGP, 0: [ZRH] 1: [TPE, FKB] 2: [AGP, CSX] 3: [DME] 4: [ORD]>, <null, 0: []>
		 */
		System.out.println("\n---------------------------");
		System.out.println("Test von der getAllDstsInfo Methode mit gegebenen Datensätzen:");
		System.out.println("---------------------------");
		givenDataAirportSet.forEach(airport -> testGetAllDstsInfo(d, airport));
		testGetAllDstsInfo(d, null);
		testGetAllDstsInfo(null, new Airport("JFK"));

		// Test für remove(Direct d)
		/* Kontrollwerte:
		 * <DME-CSX, true>, <ZRH->TPE, true>, 1.<FKB->CSX, true>, 2.<FKB->CSX, false>, <null, false>
		 */
		System.out.println("\n---------------------------");
		System.out.println("Test von der remove Methode mit gegebenen Datensätzen:");
		System.out.println("---------------------------");
		givenDataDirectSet.forEach(direct -> testRemove(d, direct));
		testRemove(d, new Direct(new Airport("FKB"), new Airport("CSX") ) );
		testRemove(d, null);
		testRemove(null, new Direct(new Airport("ZRH"), new Airport("JFK")));

		// Test für addAll(IDirections other)
		/* Kontrollwerte:
		 * 1.<[BGH, MUN, HN, KA], true>, 2.<[BGH, MUN, HN, KA]>, <null, false>
		 */
		System.out.println("\n---------------------------");
		System.out.println("Test von addAll (Interface IDirections DatenTyp) Methode:");
		System.out.println("---------------------------");
		IDirections other = setUpAnotherIDirections();
		testAddAll(d, other);
		testAddAll(d, other);
		testAddAll(d, null);
		testAddAll(null, other);
	}


	/*
	 * =========================
	 *  Test Method Section
	 * =========================
	 */


	public static void testToString(Airport a1){
		System.out.println( a1 );
	}

	public static void testToString(Direct direct){
		System.out.println( direct );
	}

	public static void testAddDirect(IDirections directions, Direct d){

		if(nullPointerCheck(directions)) {
			System.out.println("Objekt null!");
			return;
		}

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
		if( nullPointerCheck(directions) ) {
			System.out.println("Objekt null!");
			return;
		}
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

	public static void testGetAll(IDirections directions){
		if( nullPointerCheck(directions) ) {
			System.out.println("Objekt null!");
			return;
		}
		System.out.println("\n getAllSrcs: ");
		System.out.println( directions.getAllSrcs() );
		System.out.println("\n getAllDsts: ");
		System.out.println( directions.getAllDsts() );
		System.out.println("\n getAllAirports: ");
		System.out.println( directions.getAllAirports() );

	}

	public static void testGetSrcs(IDirections directions, Airport dst){
		if(nullPointerCheck(directions, dst)){
			System.out.println("Objekt null!");
			return;
		}
		System.out.println();
		System.out.println( "Destination Airport: " + dst.toString() );
		System.out.println( "Sources: " + directions.getSrcs(dst) );
	}

	public static void testGetDst(IDirections directions, Airport src){
		if(nullPointerCheck(directions, src)){
			System.out.println("Objekt null!");
			return;
		}
		System.out.println();
		System.out.println( "Source Airport: " + src.toString() );
		System.out.println( "Destinations: " + directions.getDsts(src) );
	}

	public static void testGetDst(IDirections directions, Airport src, int changes){
		//Test, ob eine der Objektparameter auf null verweist
		if( nullPointerCheck(directions, src)  ) {
			System.out.println("Objekt null!");
			return;
		}

		System.out.println();
		System.out.println( "Source Airport: " + src.toString() );
		System.out.println( "Destinations: " + directions.getDsts(src, changes) );
	}

	public static void testContains(IDirections directions, Direct direct){
		//Test, ob eine der Objektparameter auf null verweist
		if( nullPointerCheck(directions)){
			System.out.println("Objekt null!");
			return;
		}

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
		//Test, ob eine der Objektparameter auf null verweist
		if(nullPointerCheck(directions)){
			System.out.println("Objekt null!");
			return;
		}

		System.out.println();
		System.out.println( directions.getBidirectedAirports() );
	}

	public static void testAddAll(IDirections directions, IDirections other){
		//Test, ob eine der Objektparameter auf null verweist
		if( nullPointerCheck(directions) ) {
			System.out.println("Objekt null!");
			return;
		}

		System.out.println();
		boolean addAll = directions.addAll(other);
		if(addAll)
			System.out.println(other.getAllAirports() + " wurde erfolgreich hinzugefügt.\nAktuelle Struktur: " + directions.getAllAirports());
		if(!addAll)
			System.out.println(other.getAllAirports() + " wurde nicht erfolgreich in " +  directions.getAllAirports() +  " hinzugefügt");
	}

	public static void testRemove(IDirections directions, Direct direct){
		//Test, ob eine der Objektparameter auf null verweist
		if(nullPointerCheck(directions)){
			System.out.println("Objekt null!");
			return;
		}

		System.out.println();
		boolean isSuccessful = directions.remove(direct);
		if(isSuccessful)
			System.out.println(direct + " wurde erfolgreich entfernt. \nAktuelle Struktur: " + directions.getAllAirports());
		if(!isSuccessful)
			System.out.println("Bitte prüfen " + direct + " wurde NICHT aus " + directions.getAllAirports() + " entfernt");
	}

	public static void testGetRoute(IDirections directions, Set<Airport> setOfAirport){
		//Test, ob eine der Objektparameter auf null verweist
		if( nullPointerCheck(directions, setOfAirport) || setOfAirport.isEmpty() ){
			System.out.println("Objekt null!");
			return;
		}

		System.out.println();
		Airport[] ownAirportArray= new Airport[ setOfAirport.size() -1 ];
		ownAirportArray = setOfAirport.toArray(ownAirportArray);
		for(int i = 0; i <= (ownAirportArray.length -1); i++ ) {
			for (int j = i + 1; j <= (ownAirportArray.length - 1); j++){
				System.out.println("Airport Source: " + ownAirportArray[i]);
				System.out.println("Airport Destination: " +ownAirportArray[j]);
				System.out.println(directions.getRoute(ownAirportArray[i], ownAirportArray[j]));
				System.out.println();
			}
		}
	}

	public static void testMinimalRoundTrip(IDirections directions, Airport src){
		//Test, ob eine der Objektparameter auf null verweist
		if(nullPointerCheck(directions)){
			System.out.println("Objekt null!");
			return;
		}

		System.out.println();
		System.out.println("Minimaler Roundtrip für: " + src + ", " + directions.minimalRoundTrip(src));
	}

	public static void testGetAllDstsInfo(IDirections directions, Airport airport){
		//Test, ob eine der Objektparameter auf null verweist
		if(nullPointerCheck(directions)){
			System.out.println("Objekt null!");
			return;
		}

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

	public static Set<Airport> createOwnTestAirport(){

		Set<Airport> airportSet = new LinkedHashSet<>();

		airportSet.add( new Airport("FRA","Frankfurt") ); // 0
		airportSet.add( new Airport("ZRH", "Zürich") ); // 1
		airportSet.add( new Airport("CR","Crailsheim") ); // 2
		airportSet.add( new Airport("SHA", "Schwäbisch Hall") ); // 3
		airportSet.add( new Airport("MOS","Mosbach") ); // 4
		airportSet.add( new Airport("S", "Stuttgart") ); // 5

		return airportSet;

	}

	public static Set<Direct> createOwnTestDirect(){

		Set<Direct> directSet = new LinkedHashSet<>();

		Airport[] airportArr = new Airport[ createOwnTestAirport().size() - 1 ];

		airportArr = createOwnTestAirport().toArray(airportArr);

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


	/*
	 * Help Method Section
	 */


	private static boolean nullPointerCheck(Object ... testObject){
		for(int i = 0; i <= testObject.length -1; i++) {
			if (testObject[i] == null)
				return true;
		}
		return false;
	}


}
