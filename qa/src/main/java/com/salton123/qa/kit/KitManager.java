package com.salton123.qa.kit;

import android.app.Application;
import android.os.Build;

import com.salton123.qa.kit.alignruler.AlignRuler;
import com.salton123.qa.kit.animationpreview.AnimationPreview;
import com.salton123.qa.kit.anr.AnrInfoKit;
import com.salton123.qa.kit.blockmonitor.BlockMonitorKit;
import com.salton123.qa.kit.colorpick.ColorPicker;
import com.salton123.qa.kit.crash.CrashCapture;
import com.salton123.qa.kit.dataclean.DataClean;
import com.salton123.qa.kit.fileexplorer.FileExplorer;
import com.salton123.qa.kit.gpsmock.GpsMock;
import com.salton123.qa.kit.gpsmock.GpsMockManager;
import com.salton123.qa.kit.infopanel.InfoPanelKit;
import com.salton123.qa.kit.layoutborder.LayoutBorder;
import com.salton123.qa.kit.logInfo.LogInfo;
import com.salton123.qa.kit.network.NetworkKit;
import com.salton123.qa.kit.parameter.cpu.Cpu;
import com.salton123.qa.kit.parameter.frameInfo.FrameInfo;
import com.salton123.qa.kit.parameter.ram.Ram;
import com.salton123.qa.kit.sysinfo.SysInfo;
import com.salton123.qa.kit.timecounter.TimeCounterKit;
import com.salton123.qa.kit.viewcheck.ViewChecker;
import com.salton123.qa.kit.weaknetwork.WeakNetwork;
import com.salton123.qa.kit.webdoor.WebDoor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: newSalton@outlook.com
 * Date: 2020/1/19 16:57
 * ModifyTime: 16:57
 * Description:
 */
public class KitManager {
    List<IKit> kitList = new ArrayList<>();

    private KitManager() {
    }

    static class HOLDER {
        private static final KitManager INSTANCE = new KitManager();
    }

    public static KitManager getInstance() {
        return HOLDER.INSTANCE;
    }

    public void install(final Application app, List<IKit> selfKits) {
        kitList.add(new SysInfo());
        kitList.add(new FileExplorer());
        if (GpsMockManager.getInstance().isMockEnable()) {
            kitList.add(new GpsMock());
        }
        kitList.add(new WebDoor());
        kitList.add(new CrashCapture());
        kitList.add(new LogInfo());
        kitList.add(new DataClean());
        kitList.add(new WeakNetwork());
        kitList.add(new InfoPanelKit());
        kitList.add(new FrameInfo());
        kitList.add(new Cpu());
        kitList.add(new Ram());
        kitList.add(new NetworkKit());
        kitList.add(new BlockMonitorKit());
        kitList.add(new TimeCounterKit());
        kitList.add(new AnrInfoKit());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            kitList.add(new ColorPicker());
        }
        kitList.add(new AlignRuler());
        kitList.add(new ViewChecker());
        kitList.add(new LayoutBorder());
        kitList.add(new AnimationPreview());
        kitList.addAll(selfKits);
        for (IKit kit : kitList) {
            kit.onAppInit(app);
        }
        Collections.sort(kitList, (o1, o2) -> o1.getCategory() - o2.getCategory());
    }

    public List<IKit> getKits(int catgory) {
        List<IKit> kitItems = new ArrayList<>();
        for (IKit item : kitList) {
            if (item.getCategory() == catgory) {
                kitItems.add(item);
            }
        }
        return kitItems;
    }
}
