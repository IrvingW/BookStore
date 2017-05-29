package action;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;

import model.User;
import service.AppService;

import javax.json.JsonObject;
import javax.json.Json;
import javax.json.JsonWriter;



public class UserAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private AppService appService;
	private int id;
	private String pre_name;
	private String user_name;
	private String password;
	private String role;
	private String phone;
	private String email;
	private String address;
	private String new_pwd;
	
	
	
	public void setAppService(AppService appService) {
		this.appService = appService;
	}
	
	public String getPre_name() {
		return pre_name;
	}

	public void setPre_name(String pre_name) {
		this.pre_name = pre_name;
	}
	
	
	public String getNew_pwd() {
		return new_pwd;
	}

	public void setNew_pwd(String new_pwd) {
		this.new_pwd = new_pwd;
	}

	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String execute() throws Exception {
		List<User> users = appService.getAllUsers();
		request().setAttribute("users", users);
		return SUCCESS;
	}
	
	public String update() throws Exception{
		User user = appService.getUserById(id);
		user.setUser_name(user_name);
		user.setPassword(password);
		user.setPhone(phone);
		user.setEmail(email);
		user.setAddress(address);
		user.setRole(role);
		appService.updateUser(user);
		
		
		List<User> users = appService.getAllUsers();
		request().setAttribute("users", users);
		return SUCCESS;
	}
	
	public String delete() throws Exception{
		User user = appService.getUserById(id);
		appService.deleteUser(user);
		
		List<User> users = appService.getAllUsers();
		request().setAttribute("users", users);
		
		return SUCCESS;
	}
	public String add() throws Exception{
		User user = new User();
		user.setUser_name(user_name);
		user.setPassword(password);
		user.setPhone(phone);
		user.setEmail(email);
		user.setAddress(address);
		user.setRole(role);
		appService.addUser(user);
		
		List<User> users = appService.getAllUsers();
		request().setAttribute("users", users);
		return SUCCESS;
	}
	
	
	public String sign_up() throws Exception{
		User user = new User();
		user.setUser_name(user_name);
		user.setPassword(password);
		user.setPhone(phone);
		user.setEmail(email);
		user.setAddress(address);
		user.setRole("user");
		appService.addUser(user);
		return "sign_up";
	}
	
	public String sign_in() throws Exception{
		User user = appService.getUserByName(user_name);
		if(user == null){
			request().setAttribute("login", "false");
			return ERROR;
		}
			
		if(!user.getPassword().equals(password)){
			request().setAttribute("login", "false");
			return ERROR;
		}
		else{
			if(user.getRole().equals("admin"))
				return execute();
			else{
				session().setAttribute("user_name", user_name);
				// create a cart when user sign_in our system
				HashMap<Integer, Integer> cart = new HashMap<Integer, Integer>();
				session().setAttribute("cart", cart);
				return "user";
			}
		}	
	}
	
	public String sign_out() throws Exception{
		session().setAttribute("user_name", null);
		session().setAttribute("cart", null);
		PrintWriter out = response().getWriter();
		out.print("success");
		return null;
	}
	
	
	public String profile() throws Exception{
		User user = appService.getUserByName(user_name);
		String u_name = user.getUser_name();
		String u_pwd = user.getPassword();
		String u_phone = user.getPhone();
		String u_email = user.getEmail();
		String u_addr = user.getAddress();
		JsonObject model = Json.createObjectBuilder()
				.add("name", u_name)
				.add("pwd", u_pwd)
				.add("phone", u_phone)
				.add("email", u_email)
				.add("addr",u_addr)
				.build();
		
		StringWriter stringWriter = new StringWriter();
		try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
			jsonWriter.writeObject(model);
		}
		String jsonStr = stringWriter.toString();
		response().getWriter().write(jsonStr);
		return null;
	}
	
	public String update_pro() throws Exception{
		User user = appService.getUserByName(pre_name);
		user.setUser_name(user_name);
		user.setPhone(phone);
		user.setEmail(email);
		user.setAddress(address);
		appService.updateUser(user);
		session().setAttribute("user_name", user_name); // rewrite session
		return SUCCESS;
	}
	
	public String check_pwd() throws Exception{
		PrintWriter out = response().getWriter();
		String name = (String) session().getAttribute("user_name");
		if(name==null){
			out.write("login");
			return null;
		}
		User user = appService.getUserByName(name);
		String pre_pwd = user.getPassword();
		
		if(pre_pwd.equals(password)){
			out.write("right");
		}
		else{
			out.write("wrong");
		}
		return null;
	}
	
	public String modify_pwd() throws Exception{
		String name = (String) session().getAttribute("user_name");
		User user = appService.getUserByName(name);
		user.setPassword(new_pwd);
		appService.updateUser(user);
		return SUCCESS;
	}

}
