import java.util.Objects;

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

	public String toString(){
		return this.iata;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Airport airport = (Airport) o;
		return Objects.equals(iata, airport.iata);
	}

	@Override
	public int hashCode() {
		return Objects.hash(iata);
	}
}
