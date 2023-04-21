import java.util.Objects;

public class Direct {
	private Airport src;
	private Airport dst;
	
	public Direct( Airport src, Airport dst ) {
		this.src = src;
		this.dst = dst;
	}

	public String toString(){
		return this.src + "->" + this.dst;
	}
	public Airport getSrc(){
		return this.src;
	}
	public Airport getDst(){
		return this.dst;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Direct direct = (Direct) o;
		return Objects.equals(src, direct.src) && Objects.equals(dst, direct.dst);
	}

	@Override
	public int hashCode() {
		return Objects.hash(src, dst);
	}
}
