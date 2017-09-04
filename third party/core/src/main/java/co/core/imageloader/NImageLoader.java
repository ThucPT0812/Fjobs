package co.core.imageloader;

/**
 * @author TUNGDX
 */

/**
 * Tong hop nhung function can co cua mot thu vien load anh duoc su dung trong
 * ung dung.<br/>
 * Nhung function can co nhu sau <li>Load va hien thi anh tu internet (http)</li>
 * <li>Load va hien thi anh tu mot Uri (Uri co the la: photo, video, asset,
 * contact)</li> <li>Load va hien thi anh tu file</li> <li>
 * Load va hien thi anh tu resource (Anh o cac thu muc drawable)</li> <br/>
 * <br/>
 * - Trong cac ung dung se dung truc tiep interface nay ma khong su dung cu the
 * mot thu vien load anh nao (lam viec nay de viec thay doi thu vien load anh de
 * dang, khong gap nhieu kho khan). <br/>
 * - Khi muon dung thu vien load anh nao vao ung dung thi thu vien do phai
 * implements interface nay. Da su dung thu vien <a
 * href="https://github.com/nostra13/Android-Universal-Image-Loader">UIL</a> de
 * lam thu vien implements mac dinh cho interface nay, ung dung nao muon su dung
 * thu vien khac thi dev nghien cuu thu vien do va implements.<br/>
 * - Interface nay rat chung cho nhieu ung dung nen trong ung nao nao can them
 * function moi thi nghien cuu va them vao interface nay.
 */
public interface NImageLoader extends NDisplayImageLoader {
    /**
     * Load image from internet (only load)
     *
     * @param url      address of image
     * @param listener callback after image loaded (error or success)
     */
    void loadImage(String url, NImageLoadingListener listener);

    /**
     * Load image from internet (only load)
     *
     * @param url        address of image
     * @param listener   callback after image loaded (error or success)
     * @param widthSize  widthSize desire of image after loaded
     * @param heightSize heightSize desire of image after loaded
     */
    void loadImage(String url, NImageLoadingListener listener,
                   int widthSize, int heightSize);

    /**
     * Load image from internet (only load)
     *
     * @param url              address of image
     * @param progressListener progress while loading image
     */
    void loadImage(String url,
                   NImageLoadingProgressListener progressListener);

    /**
     * Load image from internet (only load)
     *
     * @param url              address of image
     * @param progressListener progress while loading image
     * @param widthSize        widthSize desire of image after loaded
     * @param heightSize       heightSize desire of image after loaded
     */
    void loadImage(String url,
                   NImageLoadingProgressListener progressListener, int widthSize,
                   int heightSize);

    /**
     * Load image from internet (only load)
     *
     * @param url              address of image
     * @param listener         callback after image loaded (error or success)
     * @param progressListener progress while loading image
     */
    void loadImage(String url, NImageLoadingListener listener,
                   NImageLoadingProgressListener progressListener);

    /**
     * Load image from internet (only load)
     *
     * @param url              address of image
     * @param listener         callback after image loaded (error or success)
     * @param progressListener progress while loading image
     * @param widthSize        widthSize desire of image after loaded
     * @param heightSize       heightSize desire of image after loaded
     */
    void loadImage(String url, NImageLoadingListener listener,
                   NImageLoadingProgressListener progressListener, int widthSize,
                   int heightSize);
}
