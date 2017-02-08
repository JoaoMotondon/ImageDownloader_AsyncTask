package service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloaderTask extends AsyncTask<String, String, Bitmap> {

    public interface TaskCallback {
        void taskStarted(String fileName);
        void taskFinished(Bitmap bitmap);
    }

    private static final String TAG = ImageDownloaderTask.class.getSimpleName();

    private String mDownloadUrl;


    private TaskCallback mCallback;

    public ImageDownloaderTask(TaskCallback callback, String downloadUrl) {
        Log.v(TAG, "Constructor");

        // Hold a reference to the caller so that it can call its methods prior and after a download.
        this.mCallback = callback;
        this.mDownloadUrl = downloadUrl;
    }

    @Override
    protected void onPreExecute() {
        Log.v(TAG, "onPreExecute");

        super.onPreExecute();

        String file = Uri.parse(mDownloadUrl).getLastPathSegment();
        mCallback.taskStarted(file);
    }

    @Override
    protected Bitmap doInBackground(String...param) {
        Log.v(TAG, "doInBackground");

        try {
            return downloadBitmap(param[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    /**
     * Download image here
     *
     * @param strUrl
     * @return
     * @throws IOException
     */
    private Bitmap downloadBitmap(String strUrl) throws IOException {
        Log.v(TAG, "downloadBitmap");

        Bitmap bitmap=null;
        InputStream iStream = null;
        try{
            URL url = new URL(strUrl);
            /** Creating an http connection to communicate with url */
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            /** Connecting to url */
            urlConnection.connect();

            /** Reading data from url */
            iStream = urlConnection.getInputStream();

            /** Creating a bitmap from the stream returned from the url */
            bitmap = BitmapFactory.decodeStream(iStream);

        }catch(Exception e){
            Log.d(TAG, "Exception while downloading url: " + strUrl + ". Error: " + e.toString());
        }finally{
            iStream.close();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        Log.v(TAG, "onPostExecute");

        // Note we are not checking for any error that may happen!
        mCallback.taskFinished(bitmap);
    }
}
