/**
 * Created by Dylan on 6/16/2015.
 *
 *
 * still need a way to generate curve properties file
 */

import java.io.*;

public class BLS {

    public static final int BLOCK_SIZE = 128; // block size file splitter in bytes

    public static void main(String args[]) {

        long startTime = System.currentTimeMillis(); // get start time (used to determine execution time)

        BLSSignature bls = new BLSSignature("a.properties"); // generates curveParams, System params, and key pair using properties file
        File dir = new File("output");
        dir.mkdir();                        // creates folder 'output', so all of the new files are all in one spot

        FileSplitter fs = new FileSplitter("output");   // sets output folder
        int numberOfFiles = 0;                          // initialize number of files counter
        try {
            numberOfFiles = fs.splitFile("message.txt", "splitFile", BLOCK_SIZE);   // splits original file into new files of size blocksize
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println(numberOfFiles);  // check how many files were created

        FileSigner signer = new FileSigner(bls.getPairing(), bls.getPrivateKey());    // initialize signer
        try {
            signer.signFiles("output", "splitFile", numberOfFiles);     // sign files
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }

        FileSigner verifier = new FileSigner(bls.getPairing(), bls.getPublicKey(), bls.getSysParams());       // initialize signer in 'verify mode'
        boolean success = false;
        try {
            success = verifier.verifyFiles("output", "splitFile", numberOfFiles); // verify files
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
        if (success) {
            System.out.println("verified");     // find out if it worked
        } else {
            System.out.println("fail");
        }

        System.out.println("\nms to run:");
        System.out.println(System.currentTimeMillis() - startTime);   // output execution time

        System.exit(0); // end program
    }
}