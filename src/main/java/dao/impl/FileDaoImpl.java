package dao.impl;

import java.io.File;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import dao.FileDao;

public class FileDaoImpl implements FileDao{
	private MongoTemplate mongoTemplate;
	private String collectionName = "portrait";
	

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}



	public void saveFile(File file, String fileName, String contentType){
		DB db = mongoTemplate.getDb();
		GridFS gfs = new GridFS(db,collectionName);
        GridFSDBFile dbfile = gfs.findOne(fileName);
        // insert a new portrait
        
        if(dbfile != null){  // if file already exist, remove it first
        	gfs.remove(fileName);
			
        }
        try{
			GridFSInputFile inputFile = gfs.createFile(file);  
			inputFile.setFilename(fileName);  
	        inputFile.setContentType(contentType);  
			inputFile.save(); 
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
  	 
	}
	
	public GridFSDBFile getFile(String fileName){
		try {
            DB db = mongoTemplate.getDb();
            // 获取fs的根节点
            GridFS gridFS = new GridFS(db, collectionName);
            GridFSDBFile dbfile = gridFS.findOne(fileName);
            if (dbfile != null) {
                return dbfile;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
	}
}
