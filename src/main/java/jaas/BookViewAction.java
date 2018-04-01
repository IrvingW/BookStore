package jaas;

import java.security.AccessControlException;
import java.security.PrivilegedAction;

import permission.BookViewPermission;
import service.SecurityService;

public class BookViewAction implements PrivilegedAction<String>{
	private SecurityService securityService;
	
	public BookViewAction(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	@Override
	public String run() {
		try {
			BookViewPermission b = new BookViewPermission("vip-book", "view");
			if(System.getSecurityManager() == null) {
				System.setProperty("java.security.policy", "/home/irving/workspace/eclipse/OriginBookStore/src/main/resources/bookView.policy");
				System.setSecurityManager(new SecurityManager());
			}
			SecurityManager manager = System.getSecurityManager();
			manager.checkPermission(b);
		}catch (AccessControlException e) {
			return "denied";
		}finally {
			System.setSecurityManager(null);
		}
		System.setSecurityManager(null);
		return "success";
	}
	
}
