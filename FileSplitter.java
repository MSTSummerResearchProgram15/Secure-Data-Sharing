import java.io.*;

/**
 * Created by Dylan on 6/24/2015.
 *
 * possible improvement: don't create a new output stream every loop
 */
public class FileSplitter {
    int blockSize;
    String filePath;
    String splitFileName;

    public FileSplitter(String filePath){
        this.filePath = filePath;
    }

    // splits one file into multiple files of size blockSize(bytes) into \output folder, returns number of files, -1 if fail
    public int splitFile(String fileName, String splitFileName, int blockSize) throws FileNotFoundException {
        int numberOfFiles = 1; // start counting at one so that first file is '1'
        String PathName = "output\\".concat(splitFileName); // where the split files will go
        File file = new File(fileName); // file we are reading from
        InputStream is = new FileInputStream(file); // initialize input stream
        byte[] temp = new byte[blockSize];  // byte array to hold values read from input
        try{
            while (is.available() >= blockSize) { // if what's available can fill the byte array, keep going
                is.read(temp); // read file
                OutputStream os = new FileOutputStream(PathName.concat(Integer.toString(numberOfFiles)).concat(".txt")); // put together split file name
                numberOfFiles++;    // increment number of files to keep track and so next file will have next number
                os.write(temp);     // write file
                os.close();
            }
            byte[] temp2 = new byte[is.available()]; // last file may be of different size
            is.read(temp2);     // read whatever is left
            OutputStream os = new FileOutputStream(PathName.concat(Integer.toString(numberOfFiles)).concat(".txt")); // construct last file name
            os.write(temp);     // write
            os.close();
            is.close();
        } catch(Exception IOException){
            IOException.printStackTrace();
            return -1; // ended unexpectedly
        }
        return numberOfFiles;
    }
}
