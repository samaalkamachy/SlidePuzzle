import java.io.FileInputStream;
import java.io.IOException;

/**
 * BMP helper class, allows for the decoding of raw .bmp file data.
 */
public class Bmp {
    private FileInputStream fileInputStream;
    private final int BM = 0x4d42;

    short signature;
    int fileSize;
    short reserved_1;
    short reserved_2;
    int pixelArrayOffset;
    int headersize;
    int width;
    int height;
    short planes;
    short bitsPerPixels;
    int compression;
    int imageSize;
    int xPixelsPerMeter;
    int yPixelPerMeter;
    int coloursInColourTable;
    int importantColours;
    int redChanelBitMask;
    int greenChanelBitMask;
    int blueChanelBitMask;
    int alpaChanelBitMask;
    int colourSpaceType;
    int colourSpaceEndpoints;
    int redChanelGamma;
    int greenChanelGamma;
    int blueChanelGamma;
    int intent;
    int iccProfileData;
    int iccProfileSize;
    int reserved;

    /**
     * Constructor for Bmp class
     *
     * @param fileInputStream --> --> .bmp slide puzzle
     */
    public Bmp(FileInputStream fileInputStream) throws Exception {
        this.fileInputStream = fileInputStream;
        readFileHeader();
        readHeader();
        // todo: read pallet

        // todo: pixel data
    }

    /**
     * Assigning BMP file header reserved data to global variables.
     */
    private void readFileHeader() throws Exception {
        signature = readShort();
        if (signature != BM) {
            throw new Exception("Signature does not match that of a BMP file.");  // wrong file type
        }
        fileSize = readInt();
        reserved_1 = readShort();
        reserved_2 = readShort();
        pixelArrayOffset = readInt();
    }

    /**
     * Assigning BMP header reserved data to global variables.
     */
    private void readHeader() throws IOException {
        headersize = readInt();
        width = readInt();
        height = readInt();
        planes = readShort();
        bitsPerPixels = readShort();
        compression =  readInt();
        imageSize = readInt();
        xPixelsPerMeter = readInt();
        yPixelPerMeter = readInt();
        coloursInColourTable = readInt();
        importantColours = readInt();

        // todo: find out why?
        if (bitsPerPixels == 24){
            coloursInColourTable = importantColours = 0;
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
