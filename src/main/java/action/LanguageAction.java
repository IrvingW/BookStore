package action;


public class LanguageAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private String lang;

	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public String switch_lang() throws Exception{
		session().setAttribute("lang", lang);
		return lang;
	}
}
