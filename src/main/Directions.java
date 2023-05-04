import java.util.*;

/*
 * class contains several directs (no multiple occurrences)
 */
public class Directions implements IDirections  {

	//HashMap für Zuordnung von Start-Airport und Set der Directions von diesem
    private final Map<Airport, Set<Direct>> airportDirectMap = new HashMap<>();

    /*
     * adds a direct to directions
     *   returns true if directions changes, false if not
     */
    public boolean add( Direct d ) {
		//Falls für Src-Airport noch kein Set hinterlegt, HashSet anlegen
		airportDirectMap.computeIfAbsent(d.getSrc(), k -> new HashSet<>());

		//Nur hinzufügen, wenn noch nicht vorhanden
		if( !this.contains(d) ){
			airportDirectMap.get(d.getSrc()).add(d);
			return true;
		}
		return false;
    }

    public boolean add( Set<Direct> d ) {
		//Boolean zum Prüfen, ob min. ein Element hinzugefügt wurde
		boolean hasAdded = false;

		//Set durchlaufen und für jedes Element add() aufrufen, wenn Element hinzugefügt: hasAdded -> true
		for(Direct direct : d) {
			if( add(direct) )
				hasAdded = true;
		}
    	return hasAdded;
    }

    /*
     * merges all direct from other into this one
     */
	//Lösung ist O(n^2) → sehr teuer und schlecht.
	//Besser wäre Lösung über getAllDirects() oder map.values(), aber wegen Interface nicht möglich
    public boolean addAll( IDirections other ) {
		//Boolean zum Prüfen, ob min. ein Element hinzugefügt wurde
		boolean hasAdded = false;

		//Alle Srcs und Dsts Airports von other in Set abspeichern
		Set<Airport> airportsSrcsSet = other.getAllSrcs();
		Set<Airport> airportsDstsSet = other.getAllDsts();

		for (Airport airportSrcs : airportsSrcsSet) {
			for (Airport airportDsts : airportsDstsSet) {
				//Jedes mögliche Direct aus Sets erstellen
				Direct tmpDirect = new Direct(airportSrcs, airportDsts);
				//Nur wenn Direct auch in other vorhanden add() aufrufen
				if (other.contains(tmpDirect)) {
					if (add(tmpDirect))
						hasAdded = true;
				}
			}
		}
    	return hasAdded;
    }
    /*
     * removes direct from directions
     *    returns true if directions changes, false if not
     */
    public boolean remove( Direct d ) {
		//Nur wenn Direct vorhanden aus Map entfernen
		if( contains(d) ){
			Set<Direct> directSet = airportDirectMap.get(d.getSrc());
			directSet.remove(d);
			return true;
		}
    	return false;
    }
    
    public boolean contains( Direct d ) {
		Set<Direct> directSet = airportDirectMap.get(d.getSrc());
		//Wenn für den SrcAirport kein Set hinterlegt → Null abfangen und return false
		if(directSet == null)
			return false;
		return directSet.contains(d);
    }
    
    /*
     * returns the set of airports that are src-airport of any direct
     *    (empty set if there are no such)
     */
    public Set<Airport> getAllSrcs(){
		Set<Airport> airportsSrcs = new HashSet<>();
		//Set aller Directs durchlaufen und von jedem Direct den SrcAirport in return Menge einfügen
		for(Direct direct : this.getAllDirects())
				airportsSrcs.add(direct.getSrc());
    	return airportsSrcs;
    }
    
    /*
     * returns the set of airports that are dst-airport of any direct
     *    (empty set if there are no such)
     */
    public Set<Airport> getAllDsts(){
		Set<Airport> airportsDsts = new HashSet<>();
		//Set aller Directs durchlaufen und von jedem Direct den DstAirport in return Menge einfügen
		for(Direct direct : this.getAllDirects())
			airportsDsts.add(direct.getDst());
		return airportsDsts;
    }
    
    /*
     * returns set of all airports that are src- or dst-airports (or both)
     */
    public Set<Airport> getAllAirports(){
		Set<Airport> allAirports = new HashSet<>();
		//Summe aus Mengen von allen SrcAirport und DstAirport
		allAirports.addAll(getAllSrcs());
		allAirports.addAll(getAllDsts());
    	return allAirports;
    }
    /*
     * returns the set of dst-airport reachable direct from src
     *    (empty set of there are no such)
     */
	public Set<Airport> getDsts( Airport src ){
		Set<Airport> dstsAirports = new HashSet<>();

		Set<Direct> directSet = airportDirectMap.get(src);
		//Wenn Set null, leeres Set returnen
		if( directSet == null)
			return dstsAirports;

		//Für jeden Direct ausgehend von SrcAirport den DstAirport ermitteln und Return-Menge hinzufügen
		directSet.forEach(d -> dstsAirports.add(d.getDst()));
		return dstsAirports;

	}
	
	/*
	 * returns the set of src-airport from where dst-airport is reachable directly
	 *    (empty set if there are no such)
	 */
	public Set<Airport> getSrcs( Airport dst ){
		Set<Airport> srcsAirports = new HashSet<>();

		for(Direct direct : this.getAllDirects()){
			if( direct.getDst().equals(dst) )
				srcsAirports.add(direct.getSrc());
		}
		return srcsAirports;
	}
	
	/*
	 * all airports reachable from src with numChanges changes
	 *    and not reachable with fewer changes (0 = direct)
	 */
	public Set<Airport> getDsts( Airport src, int numChanges ){
		Set<Airport> airportSet = new HashSet<>(getDsts(src));

		for(int i = 0; i<numChanges; i++){
			Set<Airport> tmpSet = new HashSet<>();
			for(Airport airport : airportSet) {
				tmpSet.addAll(getDsts(airport));
			}
			airportSet.addAll(tmpSet);
		}
		return airportSet;
	}

	/*
	 * returns a list of directs that build a route from src-airport to dst-airport
	 * <src_0, dst_0>, <src_1, dst_1>, ... , <src_n, dst_n>
	 * s.t. src_0 = src, dst_n = dst and dst_(i+1) = src_(i)
	 * 
	 *    if such a route exists then it is guaranteed to be minimal (there is no shorter)
	 *    remark: there might no such route, exactly one such route or more such routes
	 *    (if no route exists, return null; of more than one (minimal) exist, return any of those
	 */
	public List<Direct> getRoute( Airport src, Airport dst) {

		List<Direct> routeList = new ArrayList<>();
		Airport commonAirport = null;

		//Wenn dst nie erreichbar, return null
		if( getSrcs(dst).isEmpty() ){
			return null;
		}

		//Falls Direktverbindung vorhanden
		if( airportDirectMap.get(src).contains(new Direct(src, dst)) ){
			routeList.add(new Direct(src, dst));
			return routeList;
		}

		//Wenn in Menge der DstAirports von src dst nicht dabei, numChanges erhöhen
		//Worst Case: Airports sind alle verkettet und src erstes und dst letztes Element → numChanges muss size - 1 groß werden
		for(int numChanges = 0; numChanges <= airportDirectMap.keySet().size() - 1; numChanges++){

			Set<Airport> airportsFromSrc = getDsts(src, numChanges);
			Set<Airport> airportsToDst = getSrcs(dst);

			//Abgleich ob in beiden Mengen gleicher Airport
			for(Airport airport : airportsFromSrc) {
				if( airportsToDst.contains(airport) ){
					commonAirport = airport;
					routeList.add(new Direct(commonAirport, dst));
					routeList.addAll(0, getRoute(src, commonAirport));

					return routeList;
				}
			}
		}
		//Wenn alle Fälle durchlaufen und kein commonAirport, return null
		return null;
	}
	/*
	 * returns a set of (2-element) sets of airports that are directly connected to each other
	 */
	public Set<Set<Airport>> getBidirectedAirports(){
		Set<Set<Airport>> bidirectedAirports = new HashSet<>();


		for(Direct direct : this.getAllDirects()){
			if( contains(new Direct(direct.getDst(), direct.getSrc()))) {
				Set<Airport> airports = new HashSet<>();

				airports.add(direct.getSrc());
				airports.add(direct.getDst());

				bidirectedAirports.add(airports);
			}
		}

		return bidirectedAirports;
	}
	/*
	 * returns a (length-) minimal round-trip from airport src to at least 
	 * on other direction, that leads sometimes back to this airport
	 *    (if no such exists, return null)
	 */
	public List<Airport> minimalRoundTrip( Airport src ){

		List<Airport> airportList = new ArrayList<>();

		List<Direct> directList = getRoute(src, src);
		if(directList == null)
			return null;

		for(Direct direct : directList){
			airportList.add(direct.getSrc());
		}

		if(airportList.size() != 0) {
			airportList.add(src);
			return airportList;
		}
		return null;
	}

	public Map<Integer, Set<Airport>> getAllDstsInfo(Airport src){
		Map<Integer, Set<Airport>> myMap = new HashMap<>();
		Set<Airport> airportsAlreadyInMap = new HashSet<>();
		int numChangesKey = 1;
		boolean hasNextAirport = true;

		myMap.put(0, getDsts(src));

		while(hasNextAirport){
			Set<Airport> tmpSet = getDsts(src, numChangesKey);
			if(tmpSet.isEmpty())
				hasNextAirport = false;
			for(Airport airport : tmpSet){
				if(!airportsAlreadyInMap.contains(airport)) {
					myMap.computeIfAbsent(numChangesKey, k -> new HashSet<>());
					myMap.get(numChangesKey).add(airport);
				}
			}
			airportsAlreadyInMap.addAll(tmpSet);
			numChangesKey++;
		}

		return myMap;
	}


	//eigene Methode um alle Directs in einem Set zu erhalten
	private Set<Direct> getAllDirects(){
		Set<Direct> directs = new HashSet<>();

		for(Airport keyAirport : airportDirectMap.keySet()) {
				directs.addAll(airportDirectMap.get(keyAirport));
		}
		return directs;
	}
}
