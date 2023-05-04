import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IDirections {

	/*
	 * adds a direct to directions
	 *   returns true if directions changes, false if not
	 */
	boolean add(Direct d);

	boolean add(Set<Direct> d);

	/*
	 * merges all direct from other into this one
	 */
	boolean addAll(IDirections other);

	/*
	 * removes direct from directions
	 *    returns true if directions changes, false if not
	 */
	boolean remove(Direct d);

	boolean contains(Direct d);

	/*
	 * returns the set of airports that are src-airport of any direct
	 *    (empty set if there are no such)
	 */
	Set<Airport> getAllSrcs();

	/*
	 * returns the set of airports that are dst-airport of any direct
	 *    (empty set if there are no such)
	 */
	Set<Airport> getAllDsts();

	/*
	 * returns set of all airports that are src- or dst-airports (or both)
	 */
	Set<Airport> getAllAirports();

	/*
	 * returns the set of dst-airport reachable direct from src
	 *    (empty set of there are no such)
	 */
	Set<Airport> getDsts(Airport src);

	/*
	 * returns the set of src-airport from where dst-airport is reachable directly
	 *    (empty set if there are no such)
	 */
	Set<Airport> getSrcs(Airport dst);

	/*
	 * all airports reachable from src with numChanges changes
	 *    and not reachable with fewer changes (0 = direct)
	 */
	Set<Airport> getDsts(Airport src, int numChanges);

	/*
	 * returns a list of directs that build a route from src-airport to dst-airport
	 * <src_0, dst_0>, <src_1, dst_1>, ... , <src_n, dst_n>
	 * s.t. src_0 = src, dst_n = dst and dst_(i+1) = src_(i)
	 * 
	 *    if such a route exists then it is guaranteed to be minimal (there is no shorter)
	 *    remark: there might no such route, exactly one such route or more such routes
	 *    (if no route exists, return null; of more than one (minimal) exist, return any of those
	 */
	List<Direct> getRoute(Airport src, Airport dst);

	/*
	 * returns a set of (2-element) sets of airports that are directly connected to each other
	 */
	Set<Set<Airport>> getBidirectedAirports();

	/*
	 * returns a (length-) minimal round-trip from airport src to at least 
	 * on other direction, that leads sometimes back to this airport
	 *    (if no such exists, return null)
	 */
	List<Airport> minimalRoundTrip(Airport src);


	Map<Integer, Set<Airport>> getAllDstsInfo(Airport src);
}