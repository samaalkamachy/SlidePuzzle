import java.io.FileInputStream;
import java.io.IOException;

public class BitmapFileReader {
    private final FileInputStream fileInputStream;
    private final static int BM = 0x4d42;		        // Windows 3.1x, 95, NT, .. etc.
    private int position = 0;					        // Cursor position in the file input stream.

    // BMP Info header global variables
    private short signature;	 						// Used to identify the BMP file.
    private int fileSize;								// Size of BMP file in bytes.
    private short reserved_1;
    private short reserved_2;
    private int pixelArrayOffset;						// Starting address of the pixel data.

    // BMP header global variables
    private int headersize;								// Size of the header, indicates header type.
    private int width;									// The bitmap width in pixels
    private int height;									// The bitmap height in pixels
    private short planes;								// The number of colour planes (must be 1)
    private short bitsPerPixels;						// Bits per pixels, that are the colour depth of the image.
    private int compression;							// Compression method being used.
    private int imageSize;								// The size of the raw bitmap data.
    private int xPixelsPerMeter;						// The horizontal resolution of the image.
    private int yPixelPerMeter;							// The vertical resolution of the image.
    private int coloursInColourTable;					// The numbers of colours in the colour pallet; 0 to default to 2n.
    private int importantColours;						// The number of important colours used; 0 when every colour is important.

    // BMP extracted information
    private String scan_type;                           // The direction in which pixels are stored.
    int[][][] image;                                    // Image in the form of a RGB pixel array.
    /**
     * Constructor for BitmapFileReader class
     *
     * @param fileInputStream --> .bmp slide puzzle
     * @throws Exception -->
     */
    public BitmapFileReader(FileInputStream fileInputStream) throws Exception {
        this.fileInputStream = fileInputStream;
        readFileHeader();
        readHeader();
        readColourTable();
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
     * Reads the header size then calls the function responsible for reading the remaining header; depending on size.
     *
     * @throws Exception --> If header type is not supported by windows.
     */
    private void readHeader() throws Exception {
        headersize = readInt();
        if (headersize == 12) {
            readBitmapCoreHeader();
        } else if (headersize == 40) {
            readBitmapInfoHeader();
        } else if (headersize == 108) {
            readBitmapV4Header();
        } else if (headersize == 124) {
            readBitmapV5Header();
        } else {
            throw new Exception("Application currently only supports windows header types.");
        }
    }

    /**
     * Reading and assigning BMP header (of type "BITMAPCOREHEADER") reserved data to global variables.
     *
     * @throws Exception --> todo
     */
    private void readBitmapCoreHeader() throws Exception {
        // todo: Complete bitmap core header
        width = readShort();
        height = readShort();
        planes = readShort(); // todo: wikipedia says this must be 1 you should probably make sure its actually 1.
        bitsPerPixels = readShort();
    }

    /**
     * Reading and assigning BMP header (of type "BITMAPINFOHEADER") reserved data to global variables.
     *
     * @throws Exception --> If header is not off type BITMAPINFOHEADER.
     * 					 --> If compresion is not off type BI_RGB.
     */
    private void readBitmapInfoHeader() throws Exception {
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

        coloursInColourTable = readInt(); // If the number of colours is 0 then default to 2^n where n is bits per pixel.
        if (coloursInColourTable == 0) {
            coloursInColourTable = (int) Math.pow(1, bitsPerPixels);
        }

        importantColours = readInt();

        // Windows bitmaps can also specify an upper-left origin by using a negative value for the image height.
        if (height < 0) {
            scan_type = "TOP_TO_BOTTOM";
        }
    }

    /**
     * !!!!NOT YET IMPLEMENTED!!!! skeleton for V4 Header types
     *
     * @throws Exception
     */
    private void readBitmapV4Header() throws Exception {
        // todo: Complete v4 header
        throw new Exception("Bitmap with header type V4 is not yet implemented.");
    }

    /**
     * !!!!NOT YET IMPLEMENTED!!!! skeleton for V5 Header types
     *
     * @throws Exception --> A meme
     */
    private void readBitmapV5Header() throws Exception {
        // todo: Complete v5 header
        throw new Exception("Dude.. give it a second I haven't implemented V5 header type yet.");
    }

    /**
     * !! NOT TRUSTED !! todo: idk this feels very wrong, going to start testing it.
     * Its seems like weve extracted all of this data to just use the width and height?
     * Also what about the padding, re-watch bmp youtube video and rethink the logic.
     *
     * @throws Exception
     */
    private void readColourTable() throws Exception {
        // each entry (colours in colour table) in the color table occupies 4 bytes, in the order blue, green, red, 0x00
        int rowSize = ((bitsPerPixels * width) / 32) * 4;
        int pixelArraySize = rowSize * Math.abs(height);

        if (scan_type.equals("TOP_TO_BOTTOM")){
            image = scanTopToBottom();
        } else {
            image = scanBottomUp();
        }
    }

    /**
     * Read the colour table starting from the top left pixel, traversing left to right then top to bottom.
     *
     * @return Image, in the format of top to bottom
     */
    private int[][][] scanTopToBottom() throws Exception {
        image = new int[width][height][3];
        int padding = width%4;

        for (int i=0; i<width - 1; i++) {
            for (int j=0; j< height; j++) {
                image[i][j][2] = fileInputStream.read();	// Red.
                image[i][j][1] = fileInputStream.read();	// Green.
                image[i][j][0] = fileInputStream.read();	// Blue.
                validatePixel(image[i][j]);
            }

            // Padding
            if (padding != 0){
                for (int x=0; x< padding; x++){
                    fileInputStream.read();
                }
            }
        }
        return image;
    }

    /**
     * Read the colour table starting from the bottom left pixel, traversing left to right then upwards.
     *
     * @return Image, in the format of top to bottom
     */
    private int[][][] scanBottomUp() throws Exception {
        image = new int[width][height][3];
        int padding = width%4;

        for (int i= width - 1; i >= 0; i--){
            for (int j = 0; j< height; j++){
                image[i][j][2] = fileInputStream.read();	// Red.
                image[i][j][1] = fileInputStream.read();	// Green.
                image[i][j][0] = fileInputStream.read();	// Blue.
                validatePixel(image[i][j]);
            }

            // Padding
            if (padding != 0){
                for (int x=0; x< padding; x++){
                    fileInputStream.read();
                }
            }
        }
        return image;
    }

    /**
     * Validates pixel data.
     *
     * @param pixel --> in the format RGB.
     * @throws Exception --> if pixel colour is out of bounds (0-255)
     */
    private void validatePixel(int[] pixel) throws Exception {
        if (pixel[0] < 0 || pixel[0] > 255 ||
                pixel[1] < 0 || pixel[1] > 255 ||
                pixel[2] < 0 || pixel[2] > 255) {
            throw new Exception("theres something wrong with the image data");
        }
    }

    /**
     * Read and return the 4 bytes in the 32 bit array from the input stream.
     */
    private int readInt() throws IOException {
        int byte_1 = fileInputStream.read();
        int byte_2 = fileInputStream.read();
        int byte_3 = fileInputStream.read();
        int byte_4 = fileInputStream.read();
        position =  position + 4;
        return ((byte_4 << 24) + (byte_3 << 16) + (byte_2 << 8) + byte_1);
    }

    /**
     * Read and return the 2 bytes from the 16 bits input stream.
     */
    private short readShort() throws IOException {
        int byte_1 = fileInputStream.read();
        int byte_2 = fileInputStream.read();
        position = position + 2;
        return (short)((byte_2 << 8) + byte_1);
    }
}
