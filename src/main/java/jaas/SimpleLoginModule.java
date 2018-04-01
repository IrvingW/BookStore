package jaas;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.User;
import service.AppService;

public class SimpleLoginModule implements LoginModule{

	private Subject subject;
	private CallbackHandler callbackHandler;
	private Map<String,	?> options;
	private static UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
			Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.options = options;
		//this.userDao = new UserDaoImpl();
	}

	@Override
	public boolean login() throws LoginException{
		if(callbackHandler == null)
			throw new LoginException("no handler");
		
		NameCallback nameCallback = new NameCallback("username: ");
		PasswordCallback passwordCallback = new PasswordCallback("password: ", false);
		
		try {
			callbackHandler.handle(new Callback[] {nameCallback, passwordCallback});
		}catch (UnsupportedCallbackException e) {
			LoginException e2 = new LoginException("Unsupported callback");
			e2.initCause(e);
			throw e2;
		}catch(IOException e) {
			LoginException e2 = new LoginException("I/O exception in callback");
			e2.initCause(e);
			throw e2;
		}
		
		try {
			boolean result = checkLogin(nameCallback.getName(), String.valueOf(passwordCallback.getPassword()));
			return result;
		}catch(IOException e) {
			LoginException e2 = new LoginException("I/O exception in callback");
			e2.initCause(e);
			throw e2;
		}
	}
	
	private boolean checkLogin(String user_name, String password) throws LoginException, IOException{
		User user = userDao.getUserByName(user_name);
		String pwd = user.getPassword();
		String u_name = user.getUser_name();
		if(u_name.equals(user_name) && pwd.equals(password)) {
			if(user.getRole().equals("VIP")) {	// add principal 
				Set<Principal> principals = subject.getPrincipals();
				principals.add(new SimplePrincipal("role", "VIP"));
			}
			if(user.getRole().equals("admin")) {
				LoginException e3 = new LoginException("admin");
				throw e3;
			}else
				return true;
		}else {
			LoginException e3 = new LoginException("data_error");
			throw e3;
		}
	}

	@Override
	public boolean commit() throws LoginException {
		return true;
	}

	@Override
	public boolean abort() throws LoginException {
		return true;
	}

	@Override
	public boolean logout() throws LoginException {
		return true;
	}



	
}
