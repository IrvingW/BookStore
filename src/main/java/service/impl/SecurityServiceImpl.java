package service.impl;

import java.rmi.RemoteException;
import java.security.AccessControlException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.naming.NamingContext;
import org.springframework.beans.factory.support.ManagedArray;

import model.ViewChecker;
import permission.BookViewPermission;
import service.SecurityService;

public class SecurityServiceImpl implements SecurityService{
	@Override
	public void checkPermission(String view) throws AccessControlException{
		BookViewPermission b = new BookViewPermission(view, "view");
		System.setProperty("java.security.policy", "/home/irving/workspace/eclipse/OriginBookStore/src/main/resources/bookView.policy");
		System.setSecurityManager(new SecurityManager());
		SecurityManager manager = System.getSecurityManager();
		try {
			manager.checkPermission(b);
			// close security manager
		}finally {
		    System.setSecurityManager(null);
		}
	}
}
