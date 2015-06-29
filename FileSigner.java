import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Dylan on 6/24/2015.
 *
 * Possible improvement: don't create new output stream every time
 */
public class FileSigner {
    Pairing pairing;
    Element privateKey;
    Element publicKey;
    Element sysParams;
    Signer signer;

    // full constructor
    public FileSigner(Pairing pairing, Element privateKey, Element publicKey, Element sysParams){
        this.pairing = pairing;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.sysParams = sysParams;
        signer = new Signer(pairing, sysParams, publicKey, privateKey); // uses signer to do actual signing
    }

    // partial constructor, use for signing
    public FileSigner(Pairing pairing, Element privateKey){
        this(pairing, privateKey, null, null); // neither public key nor system Params are needed for signing
    }

    // partial constructor, use for verifying
    public FileSigner(Pairing pairing, Element publicKey, Element sysParams){
        this(pairing, null, publicKey, sysParams);  // private key not required for verification
    }

    // takes file name and number of files with that name(iterator concatenated) and verifies using .signature files in same directory
    public boolean verifyFiles(String filePath, String fileName, int numberOfFiles) throws IOException
    {
        filePath = filePath.concat("\\").concat(fileName); // get base file path and name for files
        String temp; // holds filenames of the current file
        int successes = 0; // number of successfully verified files
        for (int i = 1; i <= numberOfFiles; i++) { // go through all files
            temp = filePath.concat(Integer.toString(i)).concat(".txt"); // create file name of the file we want to access
            byte[] message = readFile(temp); // read message into byte array
            temp = temp.concat(".signature");
            byte[] sig = readFile(temp);     // read signature into byte array
            Element signature = pairing.getG1().newElementFromBytes(sig); // transfer signature into Element data type
            boolean v = signer.verifySignature(signature, message); // signer verifies sig/message
            if(v){successes++;}
        }
        boolean allFilesGood = false; // defaults to false
        if(successes == numberOfFiles){
            allFilesGood = true;
        }

        return allFilesGood;
    }

    // takes a file name and number of files of that name(with iterator concatenated) and generates signatures for all of them
    // original file name is name of file without extensions
    public void signFiles(String filePath, String FileName, int numberOfFiles) throws IOException{
        filePath = filePath.concat("\\").concat(FileName); // constructs base path and name
        String temp;    // stores currently worked on filename
        for (int i = 1; i <= numberOfFiles; i++) {   // goes through all files
            temp = filePath.concat(Integer.toString(i)).concat(".txt"); // makes name of file currently being accessed
            byte[] messageBlock = readFile(temp);   // reads in file
            Element signature = signer.generateSignature(messageBlock); // passes data to signer to generate signature
            writeSignature(temp, signature);    // writes signature
            //writeSignature(filePath, signature);
        }
    }

    // takes a file path and generates signature with same file name but with .signature
    public void writeSignature(String filePath, Element signature) throws IOException {
        OutputStream os = new FileOutputStream(filePath.concat(".signature")); // creates output stream and writes file
        os.write(signature.toBytes());
        os.close();
    }

    // reads file into byte array, don't use for 'large' files
    public byte[] readFile(String filePath) throws IOException{
        byte[] file;
        Path path = Paths.get(filePath); // gets path object from file path string
        file = Files.readAllBytes(path); // reads file into array
        return file;    // return array
    }
}
