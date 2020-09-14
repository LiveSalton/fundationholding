package com.salton123.model.manager.lifecycle;


import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salton123.log.XLog;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * User: newSalton@outlook.com
 * Date: 2018/3/7 18:16
 * ModifyTime: 18:16
 * Description:
 */

public class FragmentLifeCycleManager extends FragmentManager.FragmentLifecycleCallbacks implements IFragmentLifeCycle {
    private static final String TAG = "FragmentLifeCycleManager";
    private static final SparseArray<Fragment> sActivityEventBusScopePool = new SparseArray<>();

    private volatile static FragmentLifeCycleManager mInstance;

    private FragmentLifeCycleManager() {

    }

    public static FragmentLifeCycleManager INSTANCE() {
        if (mInstance == null) {
            synchronized (FragmentLifeCycleManager.class) {
                if (mInstance == null) {
                    mInstance = new FragmentLifeCycleManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * Called right before the fragment's {@link Fragment#onAttach(Context)} method is called.
     * This is a good time to inject any required dependencies or perform other configuration
     * for the fragment before any of the fragment's lifecycle methods are invoked.
     *
     * @param fm      Host FragmentManager
     * @param f       Fragment changing state
     * @param context Context that the Fragment is being attached to
     */
    public void onFragmentPreAttached(FragmentManager fm, Fragment f, Context context) {
    }

    /**
     * Called after the fragment has been attached to its host. Its host will have had
     * <code>onAttachFragment</code> called before this call happens.
     *
     * @param fm      Host FragmentManager
     * @param f       Fragment changing state
     * @param context Context that the Fragment was attached to
     */
    public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
    }

    /**
     * Called right before the fragment's {@link Fragment#onCreate(Bundle)} method is called.
     * This is a good time to inject any required dependencies or perform other configuration
     * for the fragment.
     *
     * @param fm                 Host FragmentManager
     * @param f                  Fragment changing state
     * @param savedInstanceState Saved instance bundle from a previous instance
     */
    public void onFragmentPreCreated(FragmentManager fm, Fragment f,
                                     Bundle savedInstanceState) {
    }

    /**
     * Called after the fragment has returned from the FragmentManager's call to
     * {@link Fragment#onCreate(Bundle)}. This will only happen once for any given
     * fragment instance, though the fragment may be attached and detached multiple times.
     *
     * @param fm                 Host FragmentManager
     * @param f                  Fragment changing state
     * @param savedInstanceState Saved instance bundle from a previous instance
     */
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        sActivityEventBusScopePool.put(f.hashCode(), f);
        XLog.i(TAG, "[onFragmentCreated]  -> " + f.getClass().getName());
    }

    /**
     * Called after the fragment has returned from the FragmentManager's call to
     * {@link Fragment#onActivityCreated(Bundle)}. This will only happen once for any given
     * fragment instance, though the fragment may be attached and detached multiple times.
     *
     * @param fm                 Host FragmentManager
     * @param f                  Fragment changing state
     * @param savedInstanceState Saved instance bundle from a previous instance
     */
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f,
                                          Bundle savedInstanceState) {
    }

    /**
     * Called after the fragment has returned a non-null view from the FragmentManager's
     * request to {@link Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     *
     * @param fm                 Host FragmentManager
     * @param f                  Fragment that created and owns the view
     * @param v                  View returned by the fragment
     * @param savedInstanceState Saved instance bundle from a previous instance
     */
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v,
                                      Bundle savedInstanceState) {
    }

    /**
     * Called after the fragment has returned from the FragmentManager's call to
     * {@link Fragment#onStart()}.
     *
     * @param fm Host FragmentManager
     * @param f  Fragment changing state
     */
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
    }

    /**
     * Called after the fragment has returned from the FragmentManager's call to
     * {@link Fragment#onResume()}.
     *
     * @param fm Host FragmentManager
     * @param f  Fragment changing state
     */
    public void onFragmentResumed(FragmentManager fm, Fragment f) {
    }

    /**
     * Called after the fragment has returned from the FragmentManager's call to
     * {@link Fragment#onPause()}.
     *
     * @param fm Host FragmentManager
     * @param f  Fragment changing state
     */
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
    }

    /**
     * Called after the fragment has returned from the FragmentManager's call to
     * {@link Fragment#onStop()}.
     *
     * @param fm Host FragmentManager
     * @param f  Fragment changing state
     */
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
    }

    /**
     * Called after the fragment has returned from the FragmentManager's call to
     * {@link Fragment#onSaveInstanceState(Bundle)}.
     *
     * @param fm       Host FragmentManager
     * @param f        Fragment changing state
     * @param outState Saved state bundle for the fragment
     */
    public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
    }

    /**
     * Called after the fragment has returned from the FragmentManager's call to
     * {@link Fragment#onDestroyView()}.
     *
     * @param fm Host FragmentManager
     * @param f  Fragment changing state
     */
    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
    }

    /**
     * Called after the fragment has returned from the FragmentManager's call to
     * {@link Fragment#onDestroy()}.
     *
     * @param fm Host FragmentManager
     * @param f  Fragment changing state
     */
    public void onFragmentDestroyed(FragmentManager fm, final Fragment f) {

        if (sActivityEventBusScopePool.get(f.hashCode()) == f) {
            sActivityEventBusScopePool.remove(f.hashCode());
            XLog.i(TAG, "[onFragmentDestroyed]  -> " + f.getClass().getName());
        }
    }

    /**
     * Called after the fragment has returned from the FragmentManager's call to
     * {@link Fragment#onDetach()}.
     *
     * @param fm Host FragmentManager
     * @param f  Fragment changing state
     */
    public void onFragmentDetached(FragmentManager fm, Fragment f) {

    }

    @Override
    public void init(FragmentManager fragmentManager) {
        fragmentManager.registerFragmentLifecycleCallbacks(this, true);

    }

    @Override
    public void unInit(FragmentManager fragmentManager) {
        fragmentManager.unregisterFragmentLifecycleCallbacks(this);
    }

    @Override
    public SparseArray<Fragment> getComponents() {
        return sActivityEventBusScopePool;
    }
}
