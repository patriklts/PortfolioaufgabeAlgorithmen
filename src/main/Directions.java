import java.util.*;

/*
 * class contains several directs (no multiple occurrences)
 */
public class Directions implements IDirections  {
    
    private Map<Airport, Set<Direct>> airportDirectMap = new HashMap<>();
    /*
     * adds a direct to directions
     *   returns true if directions changes, false if not
     */
    public boolean add( Direct d ) {
		airportDirectMap.computeIfAbsent(d.getSrc(), k -> new HashSet<>());
		
		if( !this.contains(d) ){
			airportDirectMap.get(d.getSrc()).add(d);
			return true;
		}
		return false;
    }

    public boolean add( Set<Direct> d ) {
		boolean hasAdded = false;

		//d.forEach(e -> add(e));
		for(Direct direct : d) {    //weiß ich nicht, vllt geht das, vllt auch nicht
			if( add(direct) )
				hasAdded = true;
		}
    	return hasAdded;
    }

    /*
     * merges all direct from other into this one
     */
    public boolean addAll( IDirections other ) {
		boolean hasAdded = false;

		Set<Airport> airportsSrcsSet = other.getAllSrcs();
		Set<Airport> airportsDstsSet = other.getAllDsts();

		List<Airport> airportsSrcsList = new ArrayList<>(airportsSrcsSet);
		List<Airport> airportsDstsList = new ArrayList<>(airportsDstsSet);

		for(int i = 0; i < airportsSrcsList.size(); i++){
			if( add(new Direct(airportsSrcsList.get(i), airportsDstsList.get(i))) )
				hasAdded = true;
		}

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

		for(Set<Direct> directSet : airportDirectMap.values())
			for(Direct direct : directSet)
				airportsSrcs.add(direct.getSrc());

    	return airportsSrcs;
    }
    
    /*
     * returns the set of airports that are dst-airport of any direct
     *    (empty set if there are no such)
     */
    public Set<Airport> getAllDsts(){
		Set<Airport> airportsDsts = new HashSet<>();

		for(Set<Direct> directSet : airportDirectMap.values())
			for(Direct direct : directSet)
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

		airportDirectMap.get(src).forEach(d -> dstsAirports.add(d.getDst()));
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
		return null;
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
		return null;
	}
}