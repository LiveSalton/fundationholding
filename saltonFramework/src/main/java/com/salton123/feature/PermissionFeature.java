package com.salton123.feature;

import android.Manifest;

import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;

/**
 * User: newSalton@outlook.com
 * Date: 2018/12/25 4:59 PM
 * ModifyTime: 4:59 PM
 * Description:
 */
public class PermissionFeature implements IFeature {
    private String[] permissions = getPermissionArr();

    public String[] getPermissionArr() {
        return new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
        };
    }

    @Override
    public void onBindLogic() {
        SoulPermission.getInstance().checkAndRequestPermissions(Permissions.build(permissions),
                new CheckRequestPermissionsListener() {
                    @Override
                    public void onAllPermissionOk(Permission[] allPermissions) {
                        onRequestFinish(true);
                    }

                    @Override
                    public void onPermissionDenied(Permission[] refusedPermissions) {
                        onRequestFinish(false);
                    }
                });
    }

    @Override
    public void onBindUI() {

    }

    public void onRequestFinish(boolean isGranted) {

    }

    @Override
    public void onUnBind() {

    }
}
