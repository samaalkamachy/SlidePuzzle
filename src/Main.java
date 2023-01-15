import java.io.FileInputStream;

public class Main {
	
    /**
     * Provides step-by-step solution of a slide puzzle, given that the puzzle is a .bmp file
     * and the file path is inputted into the program.
     *
     * @param args --> STD Input, file path of .bmp slide puzzle
     */
    public static void main(String[] args) throws Exception {
        String filePath = args[0];
        FileInputStream fileInputStream = new FileInputStream(filePath);

        // Extract image as pixel array from the file input stream.
        BitmapFileReader bitmapFileReader = new BitmapFileReader(fileInputStream);
        int[][][] image = bitmapFileReader.image;

        // todo process slide puzzle.
        new ImageToSlidePuzzleProccessor(image);
        new DividAndConquer();
    }
}