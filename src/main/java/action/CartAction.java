package action;



import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.Session;

import org.springframework.context.ApplicationEventPublisher;

import model.Book;
import model.Cart_item;
import model.Order;
import model.Orderitem;
import model.User;
import service.AppService;



public class CartAction extends BaseAction{
	private static final long serialVersionUID = 1L;

	
	private int book_id;
	private String method;
	private String selected_id;
	private AppService appService;
	
	
	
	public String getSelected_id() {
		return selected_id;
	}
	public void setSelected_id(String selected_id) {
		this.selected_id = selected_id;
	}
	public void setAppService(AppService appService) {
		this.appService = appService;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public int getBook_id() {
		return book_id;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
	public void setBook_id(String id_str){
		try {
			this.book_id = Integer.parseInt(id_str);
		} catch (NumberFormatException e) {
		    e.printStackTrace();
		}
		 
	}
	
	public String add_product() throws Exception{
		PrintWriter out = response().getWriter();
		appService.add_product(out, session(), book_id);
		return null;
	}
	
	public String rmv_product() throws Exception{
		appService.rmv_product(session(), book_id);
		return SUCCESS;
	}
	public String update_cnt() throws Exception{
		appService.update_cnt(session(), book_id, method);
		return pay();
	}
	
	public String pay() throws Exception{
		appService.pay(session(), request());
		return SUCCESS;
	}
	
	public String make_order() throws Exception{
		appService.make_order(session(), selected_id);
		return "payed";
	}

	
}
