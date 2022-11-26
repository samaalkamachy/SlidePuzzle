import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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

        // Todo: Load the .bmp slide puzzle
        Bmp bmp = new Bmp(fileInputStream);
        // Todo: Extract buffer image from BmpLoader

    }
}