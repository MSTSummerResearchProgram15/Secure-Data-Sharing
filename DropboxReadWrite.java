// Include the Dropbox SDK.
import com.dropbox.core.*;

import java.io.*;
import java.util.Locale;

public class DropboxReadWrite {
	long numFiles;
	DbxClient client;
	public DropboxReadWrite() throws IOException, DbxException{
		// Get your app key and secret from the Dropbox developers website.
        final String APP_KEY = "ADD KEY";
        final String APP_SECRET = "ADD KEY";
        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
        DbxRequestConfig config = new DbxRequestConfig("Secure-Data-Sharing",
            Locale.getDefault().toString());
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);

        // Have the user sign in and authorize your app.
        String authorizeUrl = webAuth.start();
        System.out.println("1. Go to: " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first)");
        System.out.println("3. Copy the authorization code.");
        String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();

        // This will fail if the user enters an invalid authorization code.
        DbxAuthFinish authFinish = webAuth.finish(code);
        String accessToken = authFinish.accessToken;

        client = new DbxClient(config, accessToken);

        System.out.println("Linked account: " + client.getAccountInfo().displayName);
	}
	public void write(String fin, int i) throws DbxException, IOException{
		int pos = fin.lastIndexOf(".");
        String baseName = "";
        if (pos > 0) {
        baseName = fin.substring(0, pos);
        }
        
        File inputFile = new File(fin);
        FileInputStream inputStream = new FileInputStream(inputFile);
        System.out.println(baseName);
        try {
            DbxEntry.File uploadedFile = client.uploadFile("/Messages/File" + i + ".txt",
                DbxWriteMode.add(), inputFile.length(), inputStream);
            System.out.println("Uploaded: " + uploadedFile.toString());
        } finally {
            inputStream.close();
        }


        DbxEntry.WithChildren listing = client.getMetadataWithChildren("/");
        System.out.println("Files in the root path:");
        for (DbxEntry child : listing.children) {
            System.out.println("	" + child.name + ": " + child.toString());
        }

        FileOutputStream outputStream = new FileOutputStream("magnum-opus.txt");
        try {
            DbxEntry.File downloadedFile = client.getFile("/magnum-opus.txt", null,
                outputStream);
            System.out.println("Metadata: " + downloadedFile.toString());
        } finally {
            outputStream.close();
        }

    }
}