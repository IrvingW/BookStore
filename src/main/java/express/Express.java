package express;

import java.io.Serializable;

public class Express implements Serializable{
	String id;
	private String sender;
	private String receiver;
	private String address;
	private Warehouse location;
	private String status;
	
	public Express(String id, String sender, String receiver, String address) {
		this.id = id;
		this.address = address;
		this.sender = sender;
		this.receiver = receiver;
	}
	
	public String getId() {return id;}
	public String getStatus() {return status;}
	public Warehouse getLocation()	{return location; }
	public String getReceiver() {return receiver;}
	public String getAddress()	{return address;}
	public String getSender() {return sender;}
	
	public void setLocation(Warehouse location) {
		this.location = location;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
}
