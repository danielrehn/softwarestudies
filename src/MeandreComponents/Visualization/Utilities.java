import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Utilities {
	static byte[] buffer = new byte[1024];
	static ZipEntry ze = null;
	public static String zipfile(String[] file, String Dir){
		try{
			 
    		FileOutputStream fos = new FileOutputStream(Dir+file+".zip");
    		ZipOutputStream zos = new ZipOutputStream(fos);
    		for(int i=0;i<file.length;i++){
	    		ze= new ZipEntry(file[i]);
	    		zos.putNextEntry(ze);
    		}
    		FileInputStream in = new FileInputStream(Dir+file);
 
    		int len;
    		while ((len = in.read(buffer)) > 0) {
    			zos.write(buffer, 0, len);
    		}
 
    		in.close();
    		zos.closeEntry();
 
    		zos.close();
    		
    		return Dir+file+".zip";
 
    	}catch(IOException ex){
    	   ex.printStackTrace();
    	   return null;
    	}
    }
}
