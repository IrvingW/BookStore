package service;

import java.rmi.RemoteException;
import java.security.AccessControlException;

import javax.naming.NamingException;

public interface SecurityService {
	public void checkPermission(String view) throws AccessControlException;
}
