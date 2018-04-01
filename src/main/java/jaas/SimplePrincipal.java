package jaas;

import java.security.Principal;
import java.util.Objects;

public class SimplePrincipal implements Principal{
	private String descr;
	private String value;
	
	public SimplePrincipal(String descr, String value) {
		this.descr = descr;
		this.value = value;
	}
	
	@Override
	public String getName() {
		String result = descr + "=" + value;
		return result;
	}
	
	public boolean equals(Object other) {
		if(this == other) return true;
		if(other == null) return false;
		String s1 = this.toString();
		String s2 = other.toString();
		if(s1.equals(s2)) return true;
		else return false;
	}
	
	public int hashCode() {
		return Objects.hashCode(getName());
	}
	
	@Override
	public String toString() {
		return "jaas.SimplePrincipal/" + getName();
	}
	
}
