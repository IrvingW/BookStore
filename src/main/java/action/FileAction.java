package action;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.Part;

import org.hibernate.pretty.Printer;

import com.mongodb.gridfs.GridFSDBFile;

import service.AppService;

public class FileAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private AppService appService;
	
	private String user_name;
	
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public void setAppService(AppService appService) {
		this.appService = appService;
	}
	
	
	public void download(){
		GridFSDBFile dbFile = appService.getFile(user_name+"_portrait");
		try{
			OutputStream outputStream = response().getOutputStream();
			response().setContentType("application/octet-stream");
			 String name = (String) dbFile.get("filename");  
             String fileName = new String(name.getBytes("GBK"), "ISO8859-1");                  
             // 设置下载文件名  
             response().addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");   
             // 向客户端输出文件  
             dbFile.writeTo(outputStream);  
             outputStream.flush();  
             outputStream.close();  
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	
	}







}
