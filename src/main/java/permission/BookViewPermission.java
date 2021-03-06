package permission;

import java.security.Permission;
import java.util.Objects;

import javaslang.test.Gen;


public class BookViewPermission extends Permission {
	private String action;
	
	public BookViewPermission(String target, String anAction) {
		super(target);
		action = anAction;
	}
	public String getActions() {
		return action;
	}
	public boolean equals(Object other) {
		if(other == null) return false;
		if(!getClass().equals(other.getClass())) return false;
		BookViewPermission b = (BookViewPermission) other;
		if(!Objects.equals(action, b.action)) return false;
		if("view".equals(action)) return Objects.equals(getName(), b.getName());
		else
			return false;
		
	}
	public int hashCode() {
		return Objects.hash(getName(), action);
	}
	public boolean implies(Permission other) {
		if(!(other instanceof BookViewPermission)) return false;
		BookViewPermission b = (BookViewPermission) other;
		if(action.equals("view")) {
			if(b.action.equals("view")) {
				String book_categorys = b.getName();
				if(getName().indexOf(book_categorys) >= 0) {
					return true;
				}else {
					return false;
				}
			}else return false;
		}
		
		else return false;
	}
}
