// Include the Dropbox SDK.
import com.dropbox.core.*;

import java.io.*;
import java.util.Locale;

public class DropboxReadWrite {
	long numFiles;
	DbxClient client;
	public DropboxReadWrite() throws IOException, DbxException{
		// Get your app key and secret from the Dropbox developers website.
        final String APP_KEY = "s96ghytmr7e7bfm";
        final String APP_SECRET = "8n7eb6a9fisdwxs";
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
	public void write(String fin, int i, String baseName) throws DbxException, IOException{
		int pos = fin.lastIndexOf(".");
        File inputFile = new File(fin);
        FileInputStream inputStream = new FileInputStream(inputFile);
        System.out.println(baseName);
        try {
            DbxEntry.File uploadedFile = client.uploadFile("/" + baseName + "/File" + i + ".txt",
                DbxWriteMode.add(), inputFile.length(), inputStream);
            System.out.println("Uploaded: " + uploadedFile.toString());
        } finally {
            inputStream.close();
        }
    }
}