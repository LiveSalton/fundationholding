package com.salton123.util;

import android.os.Bundle;
import android.util.Log;

import com.salton123.C;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Fragment工具类, 包括fragment的Add, Remove, Hide, Show, Replace, Find操作<br/>
 * 默认使用fragment.getClass().getName来作为Tag
 *
 * @author zhongyongsheng
 */

public class FragmentUtils {
    private static final String TAG = "FragmentUtils";

    /**
     * 添加fragment,里面的view会被inflate
     *
     * @param fm          support包的FragmentManager
     * @param f           support包的Fragment
     * @param containerId 要加载到的layout id
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment f,
                           @IdRes final int containerId) {
        add(fm, f, containerId, false, false);
    }

    /**
     * 添加fragment,里面的view会被inflate
     *
     * @param fm          support包的FragmentManager
     * @param f           support包的Fragment
     * @param containerId 要加载到的layout id
     * @param isHidden    true时不显示fragment,但view还是会被inflate
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment f,
                           @IdRes final int containerId,
                           final boolean isHidden,
                           final boolean override) {

        FragmentTransaction ft = fm.beginTransaction();
        action(ActionType.Add, fm, ft, f, containerId, isHidden, override);
    }

    /**
     * 移除frament,里面的view会被销毁
     *
     * @param fm support包的FragmentManager
     * @param f  support包的Fragment
     */
    public static void remove(@NonNull final FragmentManager fm,
                              @NonNull final Fragment f) {

        FragmentTransaction ft = fm.beginTransaction();
        action(ActionType.Remove, fm, ft, f, 0, false, false);
    }

    /**
     * 移除frament,里面的view会被销毁
     *
     * @param fm          support包的FragmentManager
     * @param containerId 要删除的layout id
     */
    public static void remove(@NonNull final FragmentManager fm,
                              @IdRes final int containerId) {

        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = findFragment(fm, containerId);
        if (f != null) {
            action(ActionType.Remove, fm, ft, f, 0, false, false);
        }
    }

    /**
     * 显示fragment
     *
     * @param fm support包的FragmentManager
     * @param f  support包的Fragment
     */
    public static void show(@NonNull final FragmentManager fm,
                            @NonNull final Fragment f) {

        FragmentTransaction ft = fm.beginTransaction();
        action(ActionType.Show, fm, ft, f, 0, false, false);
    }

    /**
     * 隐藏fragment
     *
     * @param fm support包的FragmentManager
     * @param f  support包的Fragment
     */
    public static void hide(@NonNull final FragmentManager fm,
                            @NonNull final Fragment f) {

        FragmentTransaction ft = fm.beginTransaction();
        action(ActionType.Hide, fm, ft, f, 0, false, false);
    }

    /**
     * 替换fragment
     *
     * @param fm          support包的FragmentManager
     * @param f           support包的Fragment
     * @param containerId 要加载到的layout id
     * @param override    true 则相同的fragment也要覆盖
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment f,
                               @IdRes final int containerId,
                               final boolean override) {

        FragmentTransaction ft = fm.beginTransaction();
        action(ActionType.Replace, fm, ft, f, containerId, false, override);
    }

    /**
     * 删除原来containerId上的fragment,再添加f
     *
     * @param fm          support包的FragmentManager
     * @param f           support包的Fragment
     * @param containerId 要加载到的layout id
     * @param isHidden    true时不显示fragment,但view还是会被inflate
     * @param override    true 则相同的fragment也要覆盖
     */
    public static void removeAdd(@NonNull final FragmentManager fm,
                                 @NonNull final Fragment f,
                                 @IdRes final int containerId,
                                 final boolean isHidden,
                                 final boolean override) {

        FragmentTransaction ft = fm.beginTransaction();
        action(ActionType.RemoveAdd, fm, ft, f, containerId, isHidden, override);
    }

    /**
     * 查找fragment
     *
     * @param fm  support包的FragmentManager
     * @param clz fragment类名
     * @return
     */
    public static Fragment findFragment(@NonNull final FragmentManager fm,
                                        @NonNull final Class<?> clz) {
        return fm.findFragmentByTag(clz.getName());
    }

    public static Fragment findFragment(@NonNull final FragmentManager fm,
                                        @NonNull final int id) {
        return fm.findFragmentById(id);
    }

    private static void action(final ActionType type,
                               final FragmentManager fm,
                               final FragmentTransaction ft,
                               final Fragment f,
                               @IdRes final int containerId,
                               final boolean isHidden,
                               final boolean override) {
        String name;
        switch (type) {
            case Add:
                if (!f.isAdded()) {
                    name = f.getClass().getName();
                    Fragment fragmentByTag = fm.findFragmentByTag(name);
                    if (fragmentByTag != null && fragmentByTag.isAdded()) {
                        if (!override) {
                            if (isHidden) {
                                ft.hide(f);
                            }
                            break;
                        }
                        ft.remove(fragmentByTag);
                    }
                    ft.add(containerId, f, name);
                }
                if (isHidden) {
                    ft.hide(f);
                }
                break;
            case Hide:
                ft.hide(f);
                break;
            case Show:
                ft.show(f);
                break;
            case Replace:
                name = f.getClass().getName();
                Fragment existFragment = findFragment(fm, containerId);
                if (existFragment != null
                        && existFragment.isAdded()
                        && f.getClass().equals(existFragment.getClass())) {
                    if (override) {
                        ft.replace(containerId, f, name);
                    }
                } else {
                    ft.replace(containerId, f, name);
                }
                break;
            case Remove:
                ft.remove(f);
                break;
            case RemoveAdd:
                Fragment existFragmentRA = findFragment(fm, containerId);
                if (existFragmentRA != null
                        && existFragmentRA.isAdded()) {
                    if (f.getClass().equals(existFragmentRA.getClass())) {
                        if (!override) {
                            if (isHidden) {
                                ft.hide(f);
                            }
                            break;
                        }
                    }
                    ft.remove(existFragmentRA);
                }

                if (!f.isAdded()) {
                    name = f.getClass().getName();
                    ft.add(containerId, f, name);
                }
                if (isHidden) {
                    ft.hide(f);
                }

                break;
            default:
                break;
        }
        ft.commitAllowingStateLoss();
    }

    public static void showDialogFragment(
            FragmentManager manager,
            DialogFragment fragment,
            String tag) {
        if (manager == null || fragment == null || tag == null) {
            Log.i(TAG, "[showDialogFragment] params == null");
            return;
        }
        try {
            manager.beginTransaction()
                    .add(fragment, tag)
                    .commitAllowingStateLoss();
            Log.i(TAG, "showDialogFragment");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "ex=" + e.getLocalizedMessage());
        }
    }

    public static <T extends Fragment> void addOrReplaceFragment(
            FragmentManager fragmentManager,
            @IdRes int layoutId, T targetFragment) {
        if (fragmentManager == null) {
            Log.i(TAG, "fragmentManager == null");
            return;
        }
        Fragment fragment = fragmentManager.findFragmentById(layoutId);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment != null) {
            transaction.replace(layoutId, targetFragment);
            Log.i(TAG, "replace fragment");
        } else {
            Log.i(TAG, "add fragment");
            transaction.add(layoutId, targetFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    public static <T extends Fragment> void removeFragment(
            FragmentManager fragmentManager,
            @IdRes int layoutId) {
        if (fragmentManager == null) {
            Log.i(TAG, "fragmentManager == null");
            return;
        }
        Fragment fragment = fragmentManager.findFragmentById(layoutId);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment != null) {
            transaction.remove(fragment);
            Log.i(TAG, "remove fragment");
        } else {
            Log.i(TAG, "remove fragment fail");
        }
        transaction.commitAllowingStateLoss();
    }

    public static <T extends Fragment> T newInstance(Class<T> clz, Bundle bundle) {
        if (clz == null) {
            return null;
        }
        try {
            T fragment = clz.newInstance();
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends Fragment> T newInstance(Class<T> clz) {
        if (clz == null) {
            return null;
        }
        try {
            T fragment = clz.newInstance();
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends Fragment> T newInstance(Class<T> clz, int intValue) {
        Bundle bundle = new Bundle();
        bundle.putInt(C.ARG_ITEM, intValue);
        return newInstance(clz, bundle);
    }

    public static <T extends Fragment> T newInstance(Class<T> clz, String stringValue) {
        Bundle bundle = new Bundle();
        bundle.putString(C.ARG_ITEM, stringValue);
        return newInstance(clz, bundle);
    }

    enum ActionType {
        Add, Remove, Show, Hide, Replace, RemoveAdd
    }
}
