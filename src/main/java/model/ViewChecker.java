package model;

import java.rmi.*;
public interface ViewChecker extends Remote{
	public void checkPermission(String view) throws RemoteException;
}
