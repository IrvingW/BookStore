package express;

import java.rmi.*;

public interface Warehouse extends Remote{
	String getExpressStatus(String id) throws RemoteException;
	
	boolean putNewExpress(String sender, String receiver, String address, int order_id) throws RemoteException;
}
