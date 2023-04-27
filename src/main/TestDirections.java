import javax.sound.midi.Soundbank;
import java.util.HashSet;
import java.util.Set;

public class TestDirections {

	public static void main(String[] args) {
		IDirections d = Data.data();				//Daten aus Data
		IDirections directions = new Directions();	//Eigene Daten

		//Eigene TestAirports
		Airport airport1 = new Airport("FRA","Frankfurt");
		Airport airport2 = new Airport("ZRH", "Zürich");
		Airport airport3 = new Airport("CR","Crailsheim");
		Airport airport4 = new Airport("SHA", "Schwäbisch Hall");
		Airport airport5 = new Airport("MOS","Mosbach");
		Airport airport6 = new Airport("S", "Stuttgart");

		//Test von toString()
		System.out.println( airport1 );
		System.out.println( new Direct(airport1, airport2) );

		/*
		//Test von add(Direct d)
		directions.add(new Direct(airport1, airport2));
		directions.add(new Direct(airport3, airport4));
		directions.add(new Direct(airport5, airport6));
		directions.add(new Direct(airport1, airport3));
		directions.add(new Direct(airport5, airport3));
		*/

		//Test von add(Set<Direct> d)
		Set<Direct> directSet = new HashSet<>();
		directSet.add(new Direct(airport1, airport2));
		directSet.add(new Direct(airport3, airport4));
		directSet.add(new Direct(airport5, airport6));
		directSet.add(new Direct(airport1, airport3));
		directSet.add(new Direct(airport5, airport3));
		directions.add(directSet);


		//Test von getAllSrcs(), getAllDsts(), getAllAirports()
		System.out.println(d.getAllSrcs());							//Soll: [DME, CSX, XYZ, ABC, ZRH, TPE, AGP, FKB]
		System.out.println(d.getAllDsts());							//Soll: [DME, CSX, XYZ, ABC, ZRH, TPE, AGP, ORD, FKB]
		System.out.println(d.getAllAirports());						//Soll: [DME, CSX, XYZ, ABC, ZRH, TPE, AGP, ORD, FKB]

		//Test von getAllSrcs(), getAllDsts(), getAllAirports()
		System.out.println(directions.getAllSrcs()); 				//Soll: [FRA, CR, MOS]
		System.out.println(directions.getAllDsts()); 				//Soll: [ZRH, SHA, S, CR]
		System.out.println(directions.getAllAirports()); 			//Soll: [FRA, ZRH, CR, SHA, MOS, S]

		//Test für getSrcs(Airport dst)
		System.out.println(directions.getSrcs(airport3));							//Soll: [MOS, FRA]
		System.out.println(directions.getSrcs(new Airport("NIE")));				//Soll: []

		//Test für getDsts(Airport src)
		System.out.println(d.getDsts(new Airport("ORD")));						//Soll: []
		System.out.println(d.getDsts(new Airport("DME")));						//Soll: [CSX, FKB, ORD, ZRH]

		//Test für getDsts(Airport src, int numChanges)
		System.out.println(d.getDsts(new Airport("DME"), 1));		//Soll: [CSX, FKB, ORD, ZRH, DME, TPE]

		//Test für contains(Direct d)
		System.out.println(directions.contains(new Direct(airport1, airport2)));	//Soll: true
		//Test für contains(Direct d)
		System.out.println(directions.contains(new Direct(airport6, airport1)));	//Soll: false

		//Test für getBidirectedAirports()
		System.out.println(directions.getBidirectedAirports());		//Soll: []
		directions.add(new Direct(airport2, airport1));
		System.out.println(directions.getBidirectedAirports());		//Soll: [[FRA, ZRH]]
		System.out.println(d.getBidirectedAirports());				//Soll: [[CSX,DME], [ABC, XYZ]]

		//Test für addAll(IDirections other)
		System.out.println(directions.addAll(directions));			//Soll: false
		IDirections tmpOtherDirect = setUpSecondIDirections();
		System.out.println(tmpOtherDirect.getAllAirports());		//Soll: [HN, BGH, MUN, KA]
		System.out.println(tmpOtherDirect.addAll(directions));		//Soll: true
		System.out.println(tmpOtherDirect.getAllAirports());		//Soll: [HN, BGH, MUN, KA, FRA, ZRH, CR, SHA, MOS, S]

		//Test für remove(Direct d)
		System.out.println(directions.remove(new Direct(airport6, airport1)));		//Soll: false
		System.out.println(directions.remove(new Direct(airport1, airport2)));		//Soll: true
		System.out.println(directions.getSrcs(airport2));							//Soll: []
		System.out.println(directions.getAllDsts());								//Soll: [FRA, SHA, S, CR]
	}

	public static IDirections setUpSecondIDirections(){
		//Setup von neuem IDirections für addAll(IDirections ohter)
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
