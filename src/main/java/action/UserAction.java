package action;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;

import model.User;
import service.AppService;
import util.RSAUtil;

import javax.json.JsonObject;
import javax.json.Json;
import javax.json.JsonWriter;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import jaas.SimpleCallbackHandler;



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
	private String introduce;
	private File file;
	
	
	
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public void setFile(File file) {
		this.file = file;
	}
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
		if(System.getSecurityManager() == null) {
			System.setProperty("java.security.policy", "/home/irving/workspace/eclipse/OriginBookStore/src/main/resources/bookView.policy");
			System.setSecurityManager(new SecurityManager());
		}
		if(user_name == null || password == null)
			return ERROR;
		SimpleCallbackHandler handler = new SimpleCallbackHandler(user_name, password);
		System.setProperty("java.security.auth.login.config", "/home/irving/workspace/eclipse/OriginBookStore/src/main/resources/jaas.config");
		LoginContext context = new LoginContext("simple", handler);
		try {
			context.login();
		}catch(LoginException e){
			// log in error or admin
			e.printStackTrace();
			System.setSecurityManager(null);
			if(e.getMessage().equals("admin")) {
				return execute();
			}else {
				request().setAttribute("login", "false");
				return ERROR;
			}
			
		}
		System.setSecurityManager(null);
		
		session().setAttribute("loginContext", context);
		session().setAttribute("user_name", user_name);
		// create a cart when user sign_in our system
		HashMap<Integer, Integer> cart = new HashMap<Integer, Integer>();
		session().setAttribute("cart", cart);
		return "user";
	}
	
	public String sign_out() throws Exception{
		LoginContext context = (LoginContext) session().getAttribute("loginContext");
		context.logout();
		session().setAttribute("user_name", null);
		session().setAttribute("cart", null);
		session().setAttribute("loginContext", null);
		PrintWriter out = response().getWriter();
		out.print("success");
		return null;
	}
	
	
	public String profile() throws Exception{
		JsonObject model = appService.profile(user_name);
		
		StringWriter stringWriter = new StringWriter();
		try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
			jsonWriter.writeObject(model);
		}
		String jsonStr = stringWriter.toString();
		response().setContentType("text/html;charset=utf-8"); // this must before getWriter() 
		response().getWriter().write(jsonStr);
		return null;
	}
	
	public String saveIntro(){
		appService.saveProfile(user_name, introduce);
		return "introduce";
	}
	
	
	public String update_pro() throws Exception{
		User user = appService.getUserByName(pre_name);
		user.setUser_name(user_name);
		user.setPhone(phone);
		user.setEmail(email);
		user.setAddress(address);
		appService.updateUser(user);
		session().setAttribute("user_name", user_name); // rewrite session
		if(file != null){
			String contentType = file.getName().split(".",1).toString();
			appService.saveFile(file, user_name+"_portrait", contentType);
		}
		return SUCCESS;
	}
	
	public String check_pwd() throws Exception{
		PrintWriter out = response().getWriter();
		String name = (String) session().getAttribute("user_name");
		appService.check_pwd(out, name, password);
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
