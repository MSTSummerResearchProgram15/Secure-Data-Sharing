
import java.io.File;

/**
 *
 * Deletes file set with specified path, base name, and extension. Number of files with this format specified with numberOfFiles
 */
public class FileDeleter {
    String filePath;
    String baseFileName;
    String extension;
    long numberOfFiles;
    File toDelete;
    
    public FileDeleter(String filePath, String baseFileName, String extension, long numberOfFiles){
        this.filePath = filePath;
        this.baseFileName = baseFileName;
        this.extension = extension;
        this.numberOfFiles = numberOfFiles;
    }
    
    // deletes files specified in constructor
    public void delete(){
        String path;
        for (long i = 0; i < numberOfFiles; i++) {
            path = filePath + baseFileName + i + extension;
            toDelete = new File(path);
            toDelete.delete();
        }
    }
    
}
