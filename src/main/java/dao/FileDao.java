package dao;

import java.io.File;

import com.mongodb.gridfs.GridFSDBFile;

public interface FileDao {
	public void saveFile(File file, String fileName, String contentType);
	
	public GridFSDBFile getFile(String fileName);
}
