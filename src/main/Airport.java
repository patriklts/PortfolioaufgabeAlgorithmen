
public class Airport {
	private String iata;
	private String city;
	//private ...
	public Airport( String iata ) {
		this(iata, null);
	}
	public Airport( String iata, String city ) {
		this.iata=iata; this.city=city;
	}

}
