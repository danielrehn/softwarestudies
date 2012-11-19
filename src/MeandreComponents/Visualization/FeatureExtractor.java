import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * 
 * Class contains methods for preparing arguments to be passed to 
 * FeatureExtractor. This means generating txt file of image paths
 * provided by the client, creating directories which FeatureExtractor
 * will place resulting files in, and possibly logging unique IDs for
 * each execution of FeatureExtractor.
 *
 * TODO where to place resulting files from FeatureExtractor? a new folder for each new job? 
 */
public class FeatureExtractor {
	
	private String txtImagePaths; 		//file path to be passed to FeatureExtractor
	private String clientFilePath;		//file path uploaded by user
	private String clientDirectoryPath; //Directory of file uploaded by user
	private String feedbackMessage;		//feedback message to pass back to user
	
	public FeatureExtractor(String filepath){
		clientFilePath = filepath;
		clientDirectoryPath = new String();
		feedbackMessage = new String();
		txtImagePaths = new String();
	}
	
	public void GenerateImgPathsFile(){
		Writer output = null; //writer for FeatureExtractor TXT file
		this.clientDirectoryPath = clientFilePath.substring(0,clientFilePath.lastIndexOf("/"));
		File configFile = new File(clientDirectoryPath+"/paths.txt");
		
		txtImagePaths = configFile.getAbsolutePath();
		BufferedReader readbuffer = null; //Reader for file uploaded by client
		String strRead; //reads in each line in Reader
		
		try {
			output = new BufferedWriter(new FileWriter(configFile)); //Open output file for writing
			readbuffer = new BufferedReader(new FileReader(clientFilePath));
			String headers[] = readbuffer.readLine().split("\t"); //first line is header, don't write it to file
			int imagefileindex = 0;
			int imagedirindex = 0;
			for(int i=0;i<headers.length;i++){
				if(headers[i].toLowerCase().equals("filename")) //filename is the header of all image filenames
					imagefileindex = i;
				if(headers[i].toLowerCase().equals("path")) //path is the header of all image file paths
					imagedirindex = i;
			}
			while ((strRead=readbuffer.readLine())!=null){
				String splitarray[] = strRead.split("\t");
				String filename = splitarray[imagefileindex];
				String filepath = splitarray[imagedirindex];
				output.write(filepath + filename+"\n"); //write one line to file
			}
			output.close();
			this.feedbackMessage = "FeatureExtractor Image Path File Created"; 
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	/**
	 * 
	 * @return feedback message to server/client on the status 
	 */
	public String getMessage(){
		return this.feedbackMessage;
	}
	
	/**
	 * Once a txt file is generated which contains a list of image directory paths
	 * (used for option 2 in FeatureExtractor documentation), this method will return
	 * the path to that generated txt file.
	 * 
	 * @return absolute path of generated txt file of containin image paths
	 */
	public String getFEImageFilePath(){
		return this.txtImagePaths;
	}
	
	/**
	 * 
	 * @return directory which generated txt file resides in
	 */
	public String getFEImageDirPath(){
		return this.clientDirectoryPath;
	}

}