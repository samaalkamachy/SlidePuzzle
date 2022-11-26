import java.io.FileInputStream;
import java.io.IOException;

/**
 * BMP helper class, allows for the decoding of raw .bmp file data.
 * 
 * Please note, currently only works for Windows and not OS.
 */
public class Bmp {
    private FileInputStream fileInputStream;
    private final int BM = 0x4d42;				// Windows 3.1x, 95, NT, .. etc. 
    private int position = 0;					// Cursor position in the file input stream.

    // BMP Info header global variables
    short signature;	 						// Used to identify the BMP file.
    int fileSize;								// Size of BMP file in bytes. 
    short reserved_1;
    short reserved_2;
    int pixelArrayOffset;						// Starting address of the pixel data. 
    
    // BMP header global variables 
    int headersize;								// Size of the header, indicates header type.
    int width;									// The bitmap width in pixels
    int height;									// The bitmap height in pixels
    short planes;								// The number of colour planes (must be 1)
    short bitsPerPixels;						// Bits per pixels, that are the colour depth of the image. 
    int compression;							// Compression method being used. 
    int imageSize;								// The size of the raw bitmap data. 
    int xPixelsPerMeter;						// The horizontal resolution of the image.
    int yPixelPerMeter;							// The vertical resolution of the image.
    int coloursInColourTable;					// The numbers of colours in the colour pallet; 0 to default to 2n. 
    int importantColours;						// The number of important colours used; 0 when every colour is important. 
    
    String scan_type;
 
    /**
     * Constructor for Bmp class
     *
     * @param fileInputStream --> .bmp slide puzzle
     * @throws Exception
     */
    public Bmp(FileInputStream fileInputStream) throws Exception {
        this.fileInputStream = fileInputStream;
        readFileHeader();
        readHeader();
        readColourTable();
        readPixelArray();
    }
    
    /**
     * Reading and assigning BMP file header reserved data to global variables.
     * 
     * @throws Exception --> If the file signature is not BM 
     */
    private void readFileHeader() throws Exception {
        signature = readShort();
        if (signature != BM) {
            throw new Exception("File signature is not 'BM'"); 
        }
        
        fileSize = readInt();
        reserved_1 = readShort();
        reserved_2 = readShort();
        pixelArrayOffset = readInt();
    }

    /**
     * Reading and assigning BMP header reserved data to global variables.
     * 
     * @throws Exception --> If header is not off type BITMAPINFOHEADER. 
     * 					 --> If compresion is not off type BI_RGB. 
     */
    private void readHeader() throws Exception {
        headersize = readInt();
        if (headersize != 40) {
        	throw new Exception("BMP Header is an invalid type, application only accepts BITMAPINFOHEADER.");
        }
        
        width = readInt();
        height = readInt();
        planes = readShort();
        bitsPerPixels = readShort();	// If bpp is 16 or higher, there will be no colour table. 
        
        compression =  readInt();
        if (compression != 0) {
        	throw new Exception("Compression type invalid, application only accepts BI_RGB (no compression).");
        }
        
        // Because we are assuming BI_RGB compression type, a dummy value of 0 can be given to the image size. 
        imageSize = readInt(); 
        imageSize = 0; 
        
        xPixelsPerMeter = readInt();
        yPixelPerMeter = readInt();
        coloursInColourTable = readInt();
        importantColours = readInt();
        
        // Windows bitmaps can also specify an upper-left origin by using a negative value for the image height.
        if (height < 0) {
        	scan_type = "TOP_TO_BOTTOM";
        }
    }
    
    /**
     * 
     */
    private void readColourTable() {
    	if (coloursInColourTable == 0) {
    		//number of entries in the palette is 2n (where n is the number of bits per pixel)
    	}
    }

    /**
     * 
     */
    private void readPixelArray() {
    	
    }
    
    /**
     * Read and return the 4 bytes in the 32 bit array from the input stream.
     */
    private int readInt() throws IOException {
        int byte_1 = fileInputStream.read();
        int byte_2 = fileInputStream.read();
        int byte_3 = fileInputStream.read();
        int byte_4 = fileInputStream.read();
        position =+ 4;
        return ((byte_4 << 24) + (byte_3 << 16) + (byte_2 << 8) + (byte_1 << 0));
    }

    /**
     * Read and return the 2 bytes from the 16 bits input stream.
     */
    private short readShort() throws IOException {
        int byte_1 = fileInputStream.read();
        int byte_2 = fileInputStream.read();
        position =+2;
        return (short)((byte_2 << 8) + byte_1);
    }
}
