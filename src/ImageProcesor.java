import java.util.*;

/**
 * Image processor slide, given an image finds the black box and segments the pieces of the puzzle.
 */
public class ImageProcesor {
	private final static int[] BLACK = {0, 0, 0};
	
	/**
	 * Constructor for Image Processor class.
	 * 
	 * @param image --> bmp image of a slide puzzle. 
	 */
	public ImageProcesor(Bmp image) {
		// find size of black box
		int[][][] imageData = image.image;
		
		// search for colour
		List<Integer[]> location = image.findList(BLACK);
	}
}
