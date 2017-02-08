package com.motondon.imagedownloader_asynctask;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import service.ImageDownloaderTask;

/**
 * This is basically a "no GUI fragment" with uses setRetained(true).
 *
 * It will start the ImageDownloaderTask runnable task, and since it will be retained in case of an orientation change,
 * it will not destroy the ImageDownloaderTask task while a task is in progress.
 *
 * After an orientation change, the MainFragment instance will retrieve the ImageDownloaderFragment instance from the
 * FragmentManager and update its instance on it. This will make this fragment to always contains the right reference to the
 * MainFragment.
 *
 */
public class ImageDownloaderFragment extends Fragment implements ImageDownloaderTask.TaskCallback {

    public static final String TAG = ImageDownloaderFragment.class.getSimpleName();

    private ImageDownloaderTask mImageDownloaderTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    public void startDownload(String downloadUrl) {
        mImageDownloaderTask = new ImageDownloaderTask(this, downloadUrl);
        mImageDownloaderTask.execute(downloadUrl);
    }

    /**
     * Use Fragment::getTargetFragment() method in order to ensure we will always get the right reference.
     *
     * @param file
     */
    public void taskStarted(String file) {
        ((MainFragment)getTargetFragment()).taskStarted(file);
    }

    /**
     * * Use Fragment::getTargetFragment() method in order to ensure we will always get the right reference.
     *
     * @param bitmap
     */
    public void taskFinished(Bitmap bitmap) {
        ((MainFragment)getTargetFragment()).taskFinished(bitmap);
    }
}
