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

        // Create new instance of BMP object.
        Bmp bmpImage = new Bmp(fileInputStream);
        
        // Pass the bmpImage to the Image processor.
        new ImageProcesor(bmpImage);
        
        // Save and display the solution.
    }
}