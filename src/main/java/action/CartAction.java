package action;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Book;
import model.Cart_item;
import service.AppService;



public class CartAction extends BaseAction{
	private static final long serialVersionUID = 1L;

	
	private int book_id;
	private String method;
	private AppService appService;
	
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
		@SuppressWarnings("unchecked")
		HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>)session().getAttribute("cart");
		if(cart==null){
			out.write("login");
			return null;
		}
		if(cart.containsKey(book_id)){
			cart.put(book_id, (cart.get(book_id)+1));
		}else{
			cart.put(book_id, 1);
		}
		session().setAttribute("cart", cart);
		out.write("success");
		return null;
	}
	public String rmv_product() throws Exception{
		@SuppressWarnings("unchecked")
		HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>)session().getAttribute("cart");
		cart.remove(book_id);
		session().setAttribute("cart", cart);
		
		return SUCCESS;
	}
	public String update_cnt() throws Exception{
		@SuppressWarnings("unchecked")
		HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>)session().getAttribute("cart");
		if(method.equals("add"))
			cart.put(book_id, (cart.get(book_id)+1));
		if(method.equals("minus"))
			cart.put(book_id, (cart.get(book_id)-1));
		
		session().setAttribute("cart", cart);
		return null;
	}
	
	public String pay() throws Exception{
		@SuppressWarnings("unchecked")
		HashMap<Integer, Integer> map = (HashMap<Integer, Integer>)session().getAttribute("cart");
		List<Cart_item> cart = new ArrayList<Cart_item>();
		for(Map.Entry<Integer,Integer> entry: map.entrySet()){
			int e_id = entry.getKey();
			int e_count = entry.getValue();
			Book book = appService.getBookById(e_id);
			//cart.add(new Cart_item(book.getBook_name(), e_count, book.getPrice()));
			Cart_item item = new Cart_item();
			item.setBook_id(e_id);
			item.setBook_name(book.getBook_name());
			item.setCount(e_count);
			item.setPrice(book.getPrice());
			cart.add(item);
		}
		request().setAttribute("cart", cart);
		
		return SUCCESS;
	}
	
}