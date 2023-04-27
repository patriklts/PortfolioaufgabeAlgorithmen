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
    public boolean addAll( IDirections other ) {
		//Boolean zum Prüfen, ob min. ein Element hinzugefügt wurde
		boolean hasAdded = false;


		//FUCK
		//for(Direct direct : other)

		//Code ist falsch, da neue Directions erfindet, möglicherweise vorher abgleich über contains
		/*
		Set<Airport> airportsSrcsSet = other.getAllSrcs();
		Set<Airport> airportsDstsSet = other.getAllDsts();

		List<Airport> airportsSrcsList = new ArrayList<>(airportsSrcsSet);
		List<Airport> airportsDstsList = new ArrayList<>(airportsDstsSet);

		for(int i = 0; i < airportsSrcsList.size(); i++){
			if( add(new Direct(airportsSrcsList.get(i), airportsDstsList.get(i))) )
				hasAdded = true;
		}
		*/
    	return hasAdded;
    }
    /*
     * removes direct from directions
     *    returns true if directions changes, false if not
     */
    public boolean remove( Direct d ) {
		if( contains(d) ){
			Set<Direct> directSet = airportDirectMap.get(d.getSrc());
			directSet.remove(d);
			return true;
		}
    	return false;
    }
    
    public boolean contains( Direct d ) {
		Set<Direct> directSet = airportDirectMap.get(d.getSrc());
		if(directSet == null)
			return false;
		return directSet.contains(d) ;
    }
    
    /*
     * returns the set of airports that are src-airport of any direct
     *    (empty set if there are no such)
     */
    public Set<Airport> getAllSrcs(){
		Set<Airport> airportsSrcs = new HashSet<>();

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

		for(Direct direct : this.getAllDirects())
			airportsDsts.add(direct.getDst());

		return airportsDsts;
    }
    
    /*
     * returns set of all airports that are src- or dst-airports (or both)
     */
    public Set<Airport> getAllAirports(){
		Set<Airport> allAirports = new HashSet<>();

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
		if( directSet == null)
			return dstsAirports;

		directSet.forEach(d -> dstsAirports.add(d.getDst()));
		return dstsAirports;

	}
	
	/*
	 * returns the set of src-airport from where dst-airport is reachable directly
	 *    (empty set if there are no such)
	 */
	public Set<Airport> getSrcs( Airport dst ){
		Set<Airport> srcsAirports = new HashSet<>();

		for(Set<Direct> directSet : airportDirectMap.values() ){
			for(Direct direct : directSet)
				if( direct.getDst() == dst )
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
		return null;
	}
	/*
	 * returns a set of (2-element) sets of airports that are directly connected to each other
	 */
	public Set<Set<Airport>> getBidirectedAirports(){
		Set<Set<Airport>> bidirectedAirports = new HashSet<>();

		for(Airport airport : airportDirectMap.keySet()){
			for(Direct direct : airportDirectMap.get(airport)){
				if( contains(new Direct(direct.getDst(), airport))) {
					Set<Airport> airports = new HashSet<>();

					airports.add(airport);
					airports.add(direct.getDst());

					bidirectedAirports.add(airports);
				}
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

		// vllt mit getRoute(). Also check, ob von dsts getRoute auf ursprünglichen srcs möglich
		return null;
	}

	//eigene Methode um alle Directs in einem Set zu erhalten
	private Set<Direct> getAllDirects(){
		Set<Direct> directs = new HashSet<>();

		for(Set<Direct> directSet : airportDirectMap.values())
			directs.addAll(directSet);
		return directs;
	}
}