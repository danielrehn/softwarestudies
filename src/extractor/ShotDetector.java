package extractor;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class ShotDetector {
	
	public String inputPath;
	public int PIXEL_THRESHOLD = 30;
	public double REGION_THRESHOLD = 0.2;
	public double FRAME_THRESHOLD = 0.5;
	public int GRID_SIZE = 4;
	public final int REGION_NO = GRID_SIZE * GRID_SIZE;
	public int AVERAGE_OVER_FRAMES = 1;
	public int MIN_SHOT_LENGTH = 5;
	
  private static final String SEPARATOR = ",";
    
	public ShotDetector(String path) {
		inputPath = path;
	}

	private void divideFrame(int[] target, int d) {
		for (int i=0;i<target.length;i++) {
			target[i] /= d;
		}
	}
	
	private void addFrame(int[] target, int[] source) {
		for (int i=0;i<target.length;i++) {
			target[i] += source[i];
		}
	}
	
	private int[] getRGBFromPackedRgb(int[] packedRgb) {
		int[] ret = new int[3 * packedRgb.length];
		for (int i=0;i<packedRgb.length;i++) {
			int b = (packedRgb[i])       & 0xff;
			int g = (packedRgb[i] >> 8)  & 0xff;
			int r = (packedRgb[i] >> 16) & 0xff;
			ret[i*3] = r;
			ret[i*3+1] = g;
			ret[i*3+2] = b;
		}
		return ret;
	}
	
	private void detectPixelChange(int[] last, int[] current, boolean[] output) {
		
		for (int i=0;i<output.length;i++) {
			int currentR = current[i*3]; 
			int currentG = current[i*3+1];
			int currentB = current[i*3+2];

			int lastR = last[i*3]; 
			int lastG = last[i*3+1];
			int lastB = last[i*3+2];
			
			if (Math.abs(lastB - currentB) > PIXEL_THRESHOLD ||
					Math.abs(lastG - currentG) > PIXEL_THRESHOLD ||
					Math.abs(lastR - currentR) > PIXEL_THRESHOLD) {
				output[i] = true;
			}
			else {
				output[i] = false;
			}
		}
		
	}
	
	private void detectRegionChange(int w, int h, boolean[] pixelChange, boolean[] regionChange) {
		
		int regionW = w / GRID_SIZE + 1;
		int regionH = h / GRID_SIZE + 1;
		
		int[] regionCount = new int[regionChange.length];
		for (int i=0;i<pixelChange.length;i++) {
			if (pixelChange[i]) {
				int r = i / w;
				int c = i % w;
				r /= regionH;
				c /= regionW;

				regionCount[r * GRID_SIZE + c]++;
			}
		}
		
		for (int i=0;i<regionCount.length;i++) {
			//System.out.println("region_" +i+ "_change_count=" + regionCount[i]);
			if (regionCount[i] > REGION_THRESHOLD * regionW*regionH) regionChange[i] = true;
			else regionChange[i] = false;
		}
		
	}
	
	private boolean detectFrameChange(boolean[] regionChange) {
		int count = 0;
		
		for (int i=0;i<regionChange.length;i++) {
			if (regionChange[i]) count++;
		}
		
		//System.out.println("frame_change_count=" + count);
		
		return (count > FRAME_THRESHOLD * REGION_NO);
	}
	
	
	private void outputImage(BufferedImage b1, BufferedImage b2, String filename) {
		BufferedImage outImage = new BufferedImage(b1.getWidth()*2,b1.getHeight(),BufferedImage.TYPE_INT_RGB);
		Graphics g = outImage.getGraphics();
		g.drawImage(b1, 0, 0, b1.getWidth(), b1.getHeight(), null);
		g.drawImage(b2, b1.getWidth(), 0, b2.getWidth(), b2.getHeight(), null);
		g.dispose();
		try {
			ImageIO.write(outImage, "png", new File(filename));
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void process(File output) {

	    BufferedWriter bw;
		BufferedImage bimg = null;
		BufferedImage lastKeyFrame = null;
		int[] packedRgb = null;
		int[] rgbLastFrame = null;
		int[] rgbThisFrame = null;
		boolean[] pixelChange = null;
		boolean[] regionChange = new boolean[REGION_NO];
		int shotNo = 1;
		int firstFrameIdx;
		boolean toBuffer;
		ArrayList<String> buffer = new ArrayList<String>();
		    
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return (name.toLowerCase().endsWith(".png") || 
					name.toLowerCase().endsWith(".jpg")); 
			}
		};

		File dir = new File(inputPath);
		if (!dir.exists()) {
			System.out.println("Error: cannot find directory " + inputPath);
			return;
		}

		// create a file list
		String[] children = dir.list(filter);
		
		if (children == null || children.length == 0) {
			System.out.println("Error: make sure " + inputPath + " contains png or jpg files");
			return;
		}
		
		// sort by file name
		Arrays.sort(children);
		
		try {
			
		  // initialization
			
			bw = new BufferedWriter(new FileWriter(output));
			bw.write("filename" + SEPARATOR +"Cut" + SEPARATOR + "Shot_number\n");
			bw.write("string" + SEPARATOR + "int" + SEPARATOR + "int\n");


			bimg = ImageIO.read(new File(inputPath + File.separator + children[0]));
			lastKeyFrame = bimg;
			
			//outputImage(bimg,lastKeyFrame,inputPath + File.separator + children[0] + ".out.png");
			
			pixelChange = new boolean[bimg.getWidth() * bimg.getHeight()];
			packedRgb = new int[bimg.getWidth() * bimg.getHeight()];
			
			bimg.getRGB(0, 0, bimg.getWidth(), bimg.getHeight(), packedRgb, 0, bimg.getWidth()); 
			rgbLastFrame = getRGBFromPackedRgb(packedRgb);
			rgbThisFrame = new int[bimg.getWidth() * bimg.getHeight() * 3]; 
				
			toBuffer = false;

			bw.write(children[0] + SEPARATOR + "0" + SEPARATOR + shotNo + "\n");
			
			
		  // for each image 
			for (int i=1; i<children.length; i++) {
				
				bimg = ImageIO.read(new File(inputPath + File.separator + children[i]));
				bimg.getRGB(0, 0, bimg.getWidth(), bimg.getHeight(), packedRgb, 0, bimg.getWidth()); 

				//outputImage(bimg,lastKeyFrame,inputPath + File.separator + children[i] + ".out.png");
				
				addFrame(rgbThisFrame, getRGBFromPackedRgb(packedRgb));
				
				if (i % AVERAGE_OVER_FRAMES == 0) {
					
				        divideFrame(rgbThisFrame, AVERAGE_OVER_FRAMES);
					detectPixelChange(rgbLastFrame, rgbThisFrame, pixelChange);
					detectRegionChange(bimg.getWidth(), bimg.getHeight(), pixelChange, regionChange);
					
					
					boolean change = detectFrameChange(regionChange);
					int cut = 0;
					if (change) {
					    if (buffer.size() == 0) {
						toBuffer = true;
						buffer.add(children[i]);
					    }
					    else {
						if (buffer.size() < MIN_SHOT_LENGTH) {
						    bw.write(buffer.get(0) + SEPARATOR +"0"+ SEPARATOR + shotNo +"\n");
						    for (int j=1;j<buffer.size();j++) {
							bw.write(buffer.get(j) + SEPARATOR + "0" + SEPARATOR + shotNo +"\n");
						    }
						    bw.flush();
						    buffer.clear();
						    toBuffer = false;
						}
						else {
						    shotNo++;
						    bw.write(buffer.get(0) + SEPARATOR + "1" + SEPARATOR + shotNo + "\n");
						    for (int j=1;j<buffer.size();j++) {
							bw.write(buffer.get(j) + SEPARATOR + "0" + SEPARATOR + shotNo + "\n");
						    }
						    bw.flush();
						    buffer.clear();
						    toBuffer = false;
						}
					    }
					}
					else {
					    if (!toBuffer) {
						bw.write(children[i] + SEPARATOR + "0"+ SEPARATOR + shotNo + "\n");
						bw.flush();
					    }
					    else {
						buffer.add(children[i]);
					    }
					}
					
					System.out.println("[shot]Processing frame " + (i+1));
					
					rgbLastFrame = rgbThisFrame;
					rgbThisFrame = new int[bimg.getWidth() * bimg.getHeight() * 3];
					
				}
				else {
				    if (!toBuffer) {
					bw.write(children[i] + SEPARATOR + "0" + SEPARATOR + shotNo + "\n");
					bw.flush();
				    }
				    else {
					buffer.add(children[i]);
				    }
				}
			}			
			
			bw.write("\n");
			bw.close();

		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

		
	public static void main(String[] args) {
		
		if (args.length < 8) {
			System.out.println("Please use python wrapper");
			return;
		}
		
		String inputPath = args[0];
		String outputFile = args[7];
		
		ShotDetector sd = new ShotDetector(inputPath);
		sd.PIXEL_THRESHOLD = Integer.parseInt(args[1]);
		sd.REGION_THRESHOLD = Double.parseDouble(args[2]);
		sd.FRAME_THRESHOLD = Double.parseDouble(args[3]);
		sd.GRID_SIZE = Integer.parseInt(args[4]);
		sd.AVERAGE_OVER_FRAMES = Integer.parseInt(args[5]);
		sd.MIN_SHOT_LENGTH = Integer.parseInt(args[6]);
		
		sd.process(new File(outputFile));
	}
	
}
