package com.salton123.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Special;
import com.qw.soul.permission.callbcak.SpecialPermissionListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.core.app.ActivityCompat;

/**
 * @desc: 权限检测工具类
 */
public class PermissionUtil {
    private static final String TAG = "PermissionUtil";
    //音频输入-麦克风
    private final static int AUDIO_INPUT = MediaRecorder.AudioSource.MIC;
    //采用频率
    //44100是目前的标准，但是某些设备仍然支持22050，16000，11025
    //采样频率一般共分为22.05KHz、44.1KHz、48KHz三个等级
    private final static int AUDIO_SAMPLE_RATE = 16000;
    //声道 单声道
    private final static int AUDIO_CHANNEL = android.media.AudioFormat.CHANNEL_IN_MONO;
    //编码
    private final static int AUDIO_ENCODING = android.media.AudioFormat.ENCODING_PCM_16BIT;

    private PermissionUtil() {
    }

    public static boolean canDrawOverlays() {
        boolean checkResult = SoulPermission.getInstance().checkSpecialPermission(Special.SYSTEM_ALERT);
        return checkResult;
    }

    public static void requestDrawOverlays(Context context) {
        SoulPermission.getInstance().checkAndRequestPermission(Special.SYSTEM_ALERT, new SpecialPermissionListener() {
            @Override
            public void onGranted(Special permission) {
                Toast.makeText(context, "system alert is enable now", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(Special permission) {
                Toast.makeText(context, "system alert is disable yet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 检测是否有sd卡读写权限，不可靠。无sd卡或者磁盘满了的情况下，即使赋予了权限也可能报无权限
     *
     * @return
     */
    public static boolean checkStorageUnreliable() {
        FileOutputStream outputStream = null;
        FileInputStream inputStream = null;
        try {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + ".DoraemonkitTest.kit");
            outputStream = new FileOutputStream(file);
            outputStream.write(1);
            outputStream.flush();
            outputStream.close();
            outputStream = null;

            inputStream = new FileInputStream(file);
            inputStream.read();
            inputStream.close();
            inputStream = null;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 检测是否有定位权限，不可靠，比如在室内打开app，这种情况下定位模块没有值，会报无权限
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static boolean checkLocationUnreliable(Context context) {
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location location2 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            return location != null || location2 != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 检测是否有相机权限
     *
     * @return
     */
    public static boolean checkCameraUnreliable() {
        Camera camera = null;
        try {
            camera = android.hardware.Camera.open();
            return camera != null;
        } catch (Exception e) {
            return false;
        } finally {
            if (camera != null) {
                try {
                    camera.release();
                } catch (Exception e) {

                }
            }
        }
    }

    /**
     * 检测是否有录音权限
     *
     * @return
     */
    public static boolean checkRecordUnreliable() {
        AudioRecord audioRecord = null;
        int bufferSizeInBytes = AudioRecord.getMinBufferSize(AUDIO_SAMPLE_RATE, AUDIO_CHANNEL, AUDIO_ENCODING);
        try {
            audioRecord =
                    new AudioRecord(AUDIO_INPUT, AUDIO_SAMPLE_RATE, AUDIO_CHANNEL, AUDIO_ENCODING, bufferSizeInBytes);
            return audioRecord != null && audioRecord.getState() == AudioRecord.STATE_INITIALIZED;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (audioRecord != null) {
                try {
                    audioRecord.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 检测是否有读取设备信息权限
     *
     * @return
     */
    public static boolean checkReadPhoneUnreliable(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission")
            String imei = tm.getDeviceId();
            return !TextUtils.isEmpty(imei);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 检测是否有读取联系人权限，不可靠。在联系人列表为空时，即使赋予了读取权限也会报没权限
     *
     * @return
     */
    public static boolean checkReadContactUnreliable(Context context) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver()
                    .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
