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
		
		// get the dimensions of each segment in the puzzle, based on the dimensions of the black box.
		List<int[]> black_indexs = image.findList(BLACK);
		int[] SegmentDimension = findSegmentSize(black_indexs);
	}

	/**
	 * todo: Sama's notes my main worry about this is if there is a black pixel thats an outlier. So maybe we need to
	 * scan and count. Originally i wanted to just get the min and max :(
	 * @param black_indexs
	 */
	private int[] findSegmentSize(List<int[]> black_indexs){
		int count_i = 0;
		int count_j = 0;

		int[] current;
		int[] future;
		int[] previous;
		for (int i=1; i<black_indexs.size() - 1; i++){
			current = black_indexs.get(i);
			future = black_indexs.get(i+1);
			previous = black_indexs.get(i-1);

			/**
			 * todo: [50, 0] [100,0][100,1] [101,0][101,1]
			 * how will the logic flow?
			 * if ((curr[0] == future[0]) && (future[1] - curr[1] == 1))
			 * 	count_i++;
			 * if ((curr[1] == future[1]) && (future[0] - curr[0] == 1))
			 * 	count_j++;
			 */
			
			System.out.println(black_indexs.get(i)[0] + ", " + black_indexs.get(i)[1]);
		}
		return (new int[]{count_i, count_j});
	}
}
