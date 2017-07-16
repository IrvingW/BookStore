package action;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import model.Book;
import model.Statistic;
import model.User;
import service.AppService;

public class StatisticAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private AppService appService;
	private Date start_time;
	private Date end_time;
	private String user_name;
	private String book_name;
	private String category;
	public AppService getAppService() {
		return appService;
	}
	public void setAppService(AppService appService) {
		this.appService = appService;
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getBook_name() {
		return book_name;
	}
	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String user() throws Exception{
		User user = appService.getUserByName(user_name);
		if(user == null){
			request().setAttribute("name", "不存在的用户");
			return SUCCESS;
		}
		// check time span is valid
		if(start_time == null || end_time == null || start_time.after(end_time)){
			request().setAttribute("name", "不存在的时间段");
			return SUCCESS;
		}
		List<Statistic> statistics = appService.getUserStatistics(user_name, start_time, end_time);
		request().setAttribute("statistics", statistics);
		request().setAttribute("name", user_name);
		return SUCCESS;
	}
	
	public String book() throws Exception{
		Book book = appService.getBookByName(book_name);
		if(book == null){
			request().setAttribute("name", "不存在的图书");
			return SUCCESS;
		}
		if(start_time == null || end_time == null || start_time.after(end_time)){
			request().setAttribute("name", "不存在的时间段");
			return SUCCESS;
		}
		List<Statistic> statistics = appService.getBookStatistics(book_name, start_time, end_time);
		request().setAttribute("name", "《"+book_name+"》");
		request().setAttribute("statistics", statistics);
		return SUCCESS;
	}
	
	public String category() throws Exception{
		if(start_time == null || end_time == null || start_time.after(end_time)){
			request().setAttribute("name", "不存在的时间段");
			return SUCCESS;
		}
		List<Statistic> statistics = appService.getCategoryStatistics(category, start_time, end_time);
		request().setAttribute("name", category);
		request().setAttribute("statistics", statistics);
		return SUCCESS;
	}

	
}
