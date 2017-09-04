package co.core.imageloader;

import android.net.Uri;
import android.widget.ImageView;

import java.io.File;

/**
 * @author TUNGDX
 */

/**
 * Cac function load va hien thi anh<br/>
 * <br/>
 * - Day la mot interface chung cho nhieu ung dung, nen chi dinh nghia nhung
 * function chung nhat, neu ung dung nao muon them cac function ve hien thi thi
 * them truc tiep vao interface nay hoac muon custom viec hien thi hon nua thi
 * su dung class {@link NDisplayOptions} de custom.<br/>
 * <br/>
 * Vi du: Mac dinh cac man hinh deu co 1 anh default trong luc loading nhung lai
 * co man hinh nao do khong co anh default nay ma hien thi trang. => Khi nay cho
 * gia tri do vao class {@link NDisplayOptions} de dieu khien.
 */
public interface NDisplayImageLoader {
    /**
     * Load image from internet and display to view
     *
     * @param url       address of image
     * @param imageView
     */
    void display(String url, ImageView imageView);

    /**
     * Load image from internet and display to view
     *
     * @param url       address of image
     * @param imageView
     * @param options   Custom display image to view
     */
    void display(String url, ImageView imageView, NDisplayOptions options);

    /**
     * Load image from internet and display to view
     *
     * @param url       address of image
     * @param imageView
     * @param listener  callback after image loaded (error or success)
     */
    void display(String url, ImageView imageView,
                 NImageLoadingListener listener);

    /**
     * Load image from internet and display to view
     *
     * @param url       address of image
     * @param imageView
     * @param options   Custom display image to view
     * @param listener  callback after image loaded (error or success)
     */
    void display(String url, ImageView imageView,
                 NDisplayOptions options, NImageLoadingListener listener);

    /**
     * Load image from internet and display to view
     *
     * @param url              address of image
     * @param imageView
     * @param progressListener progress while loading image
     */
    void display(String url, ImageView imageView,
                 NImageLoadingProgressListener progressListener);

    /**
     * Load image from internet and display to view
     *
     * @param url              address of image
     * @param imageView
     * @param options          Custom display image to view
     * @param progressListener progress while loading image
     */
    void display(String url, ImageView imageView,
                 NDisplayOptions options,
                 NImageLoadingProgressListener progressListener);

    /**
     * Load image from internet and display to view
     *
     * @param url              address of image
     * @param imageView
     * @param listener         callback after image loaded (error or success)
     * @param progressListener progress while loading image
     */
    void display(String url, ImageView imageView,
                 NImageLoadingListener listener,
                 NImageLoadingProgressListener progressListener);

    /**
     * Load image from internet and display to view
     *
     * @param url              address of image
     * @param imageView
     * @param options          Custom display image to view
     * @param listener         callback after image loaded (error or success)
     * @param progressListener progress while loading image
     */
    void display(String url, ImageView imageView,
                 NDisplayOptions options, NImageLoadingListener listener,
                 NImageLoadingProgressListener progressListener);

    /**
     * Load image from uri and display to view
     *
     * @param uri       uri of image. uri of photo, video, asset, contact (Ex:
     *                  content://, assets://, file:///)
     * @param imageView
     */
    void display(Uri uri, ImageView imageView);

    /**
     * Load image from uri and display to view
     *
     * @param uri       uri of image. uri of photo, video, asset, contact (Ex:
     *                  content://, assets://, file:///)
     * @param imageView
     * @param options   Custom display image to view
     */
    void display(Uri uri, ImageView imageView, NDisplayOptions options);

    /**
     * Load image from uri and display to view
     *
     * @param uri       uri of image. uri of photo, video, asset, contact (Ex:
     *                  content://, assets://, file:///)
     * @param imageView
     * @param listener  callback after image loaded (error or success)
     */
    void display(Uri uri, ImageView imageView,
                 NImageLoadingListener listener);

    /**
     * Load image from uri and display to view
     *
     * @param uri       uri of image. uri of photo, video, asset, contact (Ex:
     *                  content://, assets://, file:///)
     * @param imageView
     * @param options   Custom display image to view
     * @param listener  callback after image loaded (error or success)
     */
    void display(Uri uri, ImageView imageView, NDisplayOptions options,
                 NImageLoadingListener listener);

    /**
     * Load image from uri and display to view
     *
     * @param uri              uri of image. uri of photo, video, asset, contact (Ex:
     *                         content://, assets://, file:///)
     * @param imageView
     * @param progressListener progress while loading image
     */
    void display(Uri uri, ImageView imageView,
                 NImageLoadingProgressListener progressListener);

    /**
     * Load image from uri and display to view
     *
     * @param uri              uri of image. uri of photo, video, asset, contact (Ex:
     *                         content://, assets://, file:///)
     * @param imageView
     * @param options          Custom display image to view
     * @param progressListener progress while loading image
     */
    void display(Uri uri, ImageView imageView, NDisplayOptions options,
                 NImageLoadingProgressListener progressListener);

    /**
     * Load image from uri and display to view
     *
     * @param uri              uri of image. uri of photo, video, asset, contact (Ex:
     *                         content://, assets://, file:///)
     * @param imageView
     * @param listener         callback after image loaded (error or success)
     * @param progressListener progress while loading image
     */
    void display(Uri uri, ImageView imageView,
                 NImageLoadingListener listener,
                 NImageLoadingProgressListener progressListener);

    /**
     * Load image from uri and display to view
     *
     * @param uri              uri of image. uri of photo, video, asset, contact (Ex:
     *                         content://, assets://, file:///)
     * @param imageView
     * @param options          Custom display image to view
     * @param listener         callback after image loaded (error or success)
     * @param progressListener progress while loading image
     */
    void display(Uri uri, ImageView imageView, NDisplayOptions options,
                 NImageLoadingListener listener,
                 NImageLoadingProgressListener progressListener);

    /**
     * Load image from resourceId and display to view
     *
     * @param resourceId resource in drawable folder
     * @param imageView
     */
    void display(int resourceId, ImageView imageView);

    /**
     * Load image from resourceId and display to view
     *
     * @param resourceId resource in drawable folder
     * @param imageView
     * @param options    Custom display image to view
     */
    void display(int resourceId, ImageView imageView,
                 NDisplayOptions options);

    /**
     * Load image from resourceId and display to view
     *
     * @param resourceId resource in drawable folder
     * @param imageView
     * @param listener   callback after image loaded (error or success)
     */
    void display(int resourceId, ImageView imageView,
                 NImageLoadingListener listener);

    /**
     * Load image from resourceId and display to view
     *
     * @param resourceId resource in drawable folder
     * @param imageView
     * @param options    Custom display image to view
     * @param listener   callback after image loaded (error or success)
     */
    void display(int resourceId, ImageView imageView,
                 NDisplayOptions options, NImageLoadingListener listener);

    /**
     * Load image from resourceId and display to view
     *
     * @param resourceId       resource in drawable folder
     * @param imageView
     * @param progressListener progress while loading image
     */
    void display(int resourceId, ImageView imageView,
                 NImageLoadingProgressListener progressListener);

    /**
     * Load image from resourceId and display to view
     *
     * @param resourceId       resource in drawable folder
     * @param imageView
     * @param options          Custom display image to view
     * @param progressListener progress while loading image
     */
    void display(int resourceId, ImageView imageView,
                 NDisplayOptions options,
                 NImageLoadingProgressListener progressListener);

    /**
     * Load image from resourceId and display to view
     *
     * @param resourceId       resource in drawable folder
     * @param imageView
     * @param listener         callback after image loaded (error or success)
     * @param progressListener progress while loading image
     */
    void display(int resourceId, ImageView imageView,
                 NImageLoadingListener listener,
                 NImageLoadingProgressListener progressListener);

    /**
     * Load image from resourceId and display to view
     *
     * @param resourceId       resource in drawable folder
     * @param imageView
     * @param options          Custom display image to view
     * @param listener         callback after image loaded (error or success)
     * @param progressListener progress while loading image
     */
    void display(int resourceId, ImageView imageView,
                 NDisplayOptions options, NImageLoadingListener listener,
                 NImageLoadingProgressListener progressListener);

    /**
     * Load image from file and display to view
     *
     * @param file      file is photo, not support if other (ex: video's not
     *                  supported, use
     *                  {@link NDisplayImageLoader#display(Uri, ImageView, NDisplayOptions)}
     *                  instead)
     * @param imageView
     */
    void display(File file, ImageView imageView);

    /**
     * Load image from file and display to view
     *
     * @param file      file is photo, not support if other (ex: video's not
     *                  supported, use
     *                  {@link NDisplayImageLoader#display(Uri, ImageView, NDisplayOptions)}
     *                  instead)
     * @param imageView
     * @param options   Custom display image to view
     */
    void display(File file, ImageView imageView, NDisplayOptions options);

    /**
     * Load image from file and display to view
     *
     * @param file      file is photo, not support if other (ex: video's not
     *                  supported, use
     *                  {@link NDisplayImageLoader#display(Uri, ImageView, NDisplayOptions)}
     *                  instead)
     * @param imageView
     * @param listener  callback after image loaded (error or success)
     */
    void display(File file, ImageView imageView,
                 NImageLoadingListener listener);

    /**
     * Load image from file and display to view
     *
     * @param file      file is photo, not support if other (ex: video's not
     *                  supported, use
     *                  {@link NDisplayImageLoader#display(Uri, ImageView, NDisplayOptions)}
     *                  instead)
     * @param imageView
     * @param options   Custom display image to view
     * @param listener  callback after image loaded (error or success)
     */
    void display(File file, ImageView imageView, NDisplayOptions options,
                 NImageLoadingListener listener);

    /**
     * Load image from file and display to view
     *
     * @param file             file is photo, not support if other (ex: video's not
     *                         supported, use
     *                         {@link NDisplayImageLoader#display(Uri, ImageView, NDisplayOptions)}
     *                         instead)
     * @param imageView
     * @param progressListener progress while loading image
     */
    void display(File file, ImageView imageView,
                 NImageLoadingProgressListener progressListener);

    /**
     * Load image from file and display to view
     *
     * @param file             file is photo, not support if other (ex: video's not
     *                         supported, use
     *                         {@link NDisplayImageLoader#display(Uri, ImageView, NDisplayOptions)}
     *                         instead)
     * @param imageView
     * @param options          Custom display image to view
     * @param progressListener progress while loading image
     */
    void display(File file, ImageView imageView, NDisplayOptions options,
                 NImageLoadingProgressListener progressListener);

    /**
     * Load image from file and display to view
     *
     * @param file             file is photo, not support if other (ex: video's not
     *                         supported, use
     *                         {@link NDisplayImageLoader#display(Uri, ImageView, NDisplayOptions)}
     *                         instead)
     * @param imageView
     * @param listener         callback after image loaded (error or success)
     * @param progressListener progress while loading image
     */
    void display(File file, ImageView imageView,
                 NImageLoadingListener listener,
                 NImageLoadingProgressListener progressListener);

    /**
     * Load image from file and display to view
     *
     * @param file             file is photo, not support if other (ex: video's not
     *                         supported, use
     *                         {@link NDisplayImageLoader#display(Uri, ImageView, NDisplayOptions)}
     *                         instead)
     * @param imageView
     * @param options          Custom display image to view
     * @param listener         callback after image loaded (error or success)
     * @param progressListener progress while loading image
     */
    void display(File file, ImageView imageView, NDisplayOptions options,
                 NImageLoadingListener listener,
                 NImageLoadingProgressListener progressListener);

}
