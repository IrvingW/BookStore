package jaas;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;

public class SimpleCallbackHandler implements CallbackHandler{
	private String user_name;
	private String password;
	
	public SimpleCallbackHandler(String user_name, String password) {
		this.user_name = user_name;
		this.password = password;
	}
	

	@Override
	public void handle(Callback[] callbacks){
		for(Callback callback : callbacks) {
			if(callback instanceof NameCallback) {
				((NameCallback) callback).setName(user_name);
			}else if(callback instanceof PasswordCallback){
				char[] password_charArray = password.toCharArray();
				((PasswordCallback) callback).setPassword(password_charArray);
			}
		}
		
	}
}