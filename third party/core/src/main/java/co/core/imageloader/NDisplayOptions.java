package co.core.imageloader;

/**
 * @author TUNGDX
 */

import android.support.annotation.DrawableRes;

/**
 * Custom display image to view (ex: default image for loading or animation...)
 */
public class NDisplayOptions {
    private boolean isNoImageWhileLoading;

    private boolean checkActualViewSize;
    private int imageOnLoading;
    private int imageOnFail;
    private boolean isCacheOnDisk;

    public boolean isNoImageWhileLoading() {
        return isNoImageWhileLoading;
    }

    public boolean isCheckActualViewSize() {
        return checkActualViewSize;
    }

    @DrawableRes
    public int getImageOnLoading() {
        return imageOnLoading;
    }

    @DrawableRes
    public int getImageOnFail() {
        return imageOnFail;
    }

    public boolean isCacheOnDisk() {
        return isCacheOnDisk;
    }

    private NDisplayOptions(Builder builder) {
        isNoImageWhileLoading = builder.isNoImageWhileLoading;
        checkActualViewSize = builder.checkActualViewSize;
        imageOnLoading = builder.imageOnLoading;
        imageOnFail = builder.imageOnFail;
        isCacheOnDisk = builder.isCacheOnDisk;
    }

    public static class Builder {
        private boolean isNoImageWhileLoading;
        private boolean checkActualViewSize;
        private int imageOnLoading = -1;
        private int imageOnFail = -1;
        private boolean isCacheOnDisk = true;

        public Builder setImageOnFail(@DrawableRes int resourceId) {
            this.imageOnFail = resourceId;
            return this;
        }

        public Builder setImageOnLoading(@DrawableRes int resourceId) {
            this.imageOnLoading = resourceId;
            return this;
        }

        public Builder setNoImageWhileLoading(boolean isNoImage) {
            isNoImageWhileLoading = isNoImage;
            return this;
        }

        public Builder setCheckActualViewSize(boolean isCheck) {
            checkActualViewSize = isCheck;
            return this;
        }

        /**
         * @param isCache Default is true.
         * @return
         */
        public Builder cacheOnDisk(boolean isCache) {
            isCacheOnDisk = isCache;
            return this;
        }

        public NDisplayOptions build() {
            return new NDisplayOptions(this);
        }
    }
}
