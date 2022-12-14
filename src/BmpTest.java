import org.junit.Before;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BmpTest {
    // The unit under test.
    private Bmp uut;

    // Test images
    private String valid3x3ImageFilePath;

    @Before
    public void setup(){
        valid3x3ImageFilePath = "test.bmp";
    }

    @Test
    public void validBmSignature() throws FileNotFoundException {
        FileInputStream valid3x3ImageIS = new FileInputStream(valid3x3ImageFilePath);
    }

}
