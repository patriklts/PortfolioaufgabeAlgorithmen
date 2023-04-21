import java.util.*;

/*
 * class contains several directs (no multiple occurrences)
 */
public class Directions implements IDirections  {
    
    private Map<Airport, Direct> airportDirectMap = new HashMap<>();
    /*
     * adds a direct to directions
     *   returns true if directions changes, false if not
     */
    public boolean add( Direct d ) {
    	if( !this.contains(d) ){
			airportDirectMap.put(d.getSrc(), d);
			return true;
		}
		return false;
    }

    public boolean add( Set<Direct> d ) {
		boolean wasAdded = false;
		for(Direct e : d) {    //weiß ich nicht, vllt geht das, vllt auch nicht
			if( add(e) )
				wasAdded = true;
		}
    	return wasAdded;
    }

    /*
     * merges all direct from other into this one
     */
    public boolean addAll( IDirections other ) {
		// mit other.getAllSrcs(); other.getAllDsts(); ein neues Set<Direct> erstellen und dann add Methode aufrufen?
    	return false;
    }
    /*
     * removes direct from directions
     *    returns true if directions changes, false if not
     */
    public boolean remove( Direct d ) {
    	return false;
    }
    
    public boolean contains( Direct d ) {
		return false;
    }
    
    /*
     * returns the set of airports that are src-airport of any direct
     *    (empty set if there are no such)
     */
    public Set<Airport> getAllSrcs(){
		Set<Airport> airportsSrcs = new HashSet<>();

		for(Airport key : airportDirectMap.keySet())
			airportsSrcs.add(airportDirectMap.get(key).getSrc());

    	return airportsSrcs;
    }
    
    /*
     * returns the set of airports that are dst-airport of any direct
     *    (empty set if there are no such)
     */
    public Set<Airport> getAllDsts(){
		Set<Airport> airportsDsts = new HashSet<>();

		for(Airport key : airportDirectMap.keySet())
			airportsDsts.add(airportDirectMap.get(key).getDst());

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
		return null;
	}
	
	/*
	 * returns the set of src-airport from where dst-airport is reachable directly
	 *    (empty set if there are no such)
	 */
	public Set<Airport> getSrcs( Airport dst ){
		return null;
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
		return null;
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