package co.core.androidpermission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by NTH1991 on 3/1/2016.
 * <p/>
 * class to check permission added in {@link AppPermissionChecker#addPermission(String, AddPermissionListener)}
 * and call {@link ActivityCompat#requestPermissions(Activity, String[], int)}
 * to get Permission
 */
public final class AppPermissionChecker {
    public static final int REQUEST_CODE_GET_PERMISSION = 112;
    private Context mContext;
    private Map<String, AddPermissionListener> permissions = new HashMap<>();
    private AddPermissionListener mListener;

    /**
     * @param mContext must be {@link Activity }
     */
    public AppPermissionChecker(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * add permission to check and run callback
     *
     * @param permission to check
     * @param listener   call back after check and request permission
     */
    public void addPermission(String permission, AddPermissionListener listener) {
        if (!permissions.containsKey(permission)) {
            permissions.put(permission, listener);
        }
    }


    public void setListenerToAllPermission(AddPermissionListener listener) {
        mListener = listener;
    }

    public void clearPermission() {
        if (permissions != null)
            permissions.clear();
    }


    public boolean shouldShowDialog(Activity activity, String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    /**
     * start call check Permission
     */
    public void checkPermission(Fragment fragment) {
        if (Build.VERSION_CODES.M <= Build.VERSION.SDK_INT) {
            check(fragment);
        } else {
            for (String permission : permissions.keySet()) {
                AddPermissionListener listener = permissions.get(permission);
                if (listener != null) {
                    listener.onPermissionGranted(permission);
                }
            }
        }
    }

    public void openPermissionSetting(Activity activity, String packageName) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + packageName));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        activity.startActivity(intent);
    }

    public static boolean isGranted(Context context, String permission) {
        if (Build.VERSION_CODES.M > Build.VERSION.SDK_INT) {
            return true;
        }
        return ContextCompat.checkSelfPermission(context,
                permission)
                == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * check whether if Permission is granted or denied
     * and call {@link ActivityCompat#requestPermissions(Activity, String[], int)} in activity
     * or call {@link Fragment#requestPermissions(String[], int)} in fragment
     */
    private void check(Fragment fragment) {
        List<String> per = new ArrayList<>();

        for (String permission : permissions.keySet()) {
            AddPermissionListener listener = permissions.get(permission);

            if (!isGranted(mContext, permission)) {

                per.add(permission);

            } else {
                if (listener != null) {
                    listener.onPermissionGranted(permission);
                }
            }
        }

        if (per.size() > 0) {
            String[] arr = new String[per.size()];
            if (fragment != null) {
                fragment.requestPermissions(
                        per.toArray(arr),
                        REQUEST_CODE_GET_PERMISSION);
            } else {
                ActivityCompat.requestPermissions((Activity) mContext,
                        per.toArray(arr),
                        REQUEST_CODE_GET_PERMISSION);
            }
        }
    }


    /**
     * call in {@link Activity#onRequestPermissionsResult(int, String[], int[])} in activity
     * and {@link Fragment#onRequestPermissionsResult(int, String[], int[])} in fragment
     * to recieve results and call listener {@link AddPermissionListener}
     *
     * @param requestCode
     * @param pers
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode,
                                           String pers[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_GET_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                boolean granted = true;
                if (grantResults.length > 0) {
                    for (int i = 0; i < pers.length; i++) {
                        String permission = pers[i];
                        AddPermissionListener listener = permissions.get(permission);
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            // permission was granted, yay! Do the
                            // contacts-related task you need to do.
                            if (listener != null)
                                listener.onPermissionGranted(permission);
                        } else {
                            granted = false;
                            // permission denied, boo! Disable the
                            // functionality that depends on this permission.
                            if (listener != null)
                                listener.onPermissionDenied(permission);
                        }
                    }
                } else {
                    granted = false;
                }
                if (mListener != null) {
                    if (granted) mListener.onPermissionGranted(Arrays.toString(pers));
                    else mListener.onPermissionDenied(Arrays.toString(pers));
                }
                break;
            }
        }
    }

    public void destroy() {
        mContext = null;
        permissions.clear();
        permissions = null;
        mListener = null;
    }


    public interface AddPermissionListener {
        void onPermissionGranted(String per);

        void onPermissionDenied(String per);
    }
}
