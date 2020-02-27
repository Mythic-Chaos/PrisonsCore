package com.mythicchaos.utils.nms;

import com.mythicchaos.utils.nms.v1_8.ItemUtil_v1_8_R2;
import org.bukkit.Bukkit;

/**
 * Copy Right ©
 * This code is private
 * Owner: PerryPlaysMC
 * From: 11/3/19-2200
 * Package: com.mythicchaos.utils.nms
 * Class: NMSMain
 * <p>
 * Path: com.mythicchaos.utils.nms.NMSMain
 * <p>
 * Any attempts to use these program(s) may result in a penalty of up to $1,000 USD
 **/

@SuppressWarnings("all")
public class NMSMain {

    public static ItemDataUtilHelper getItemDataUtilHelper() {
        if(itemDataUtilHelper == null) load();
        return itemDataUtilHelper;
    }

    private static ItemDataUtilHelper itemDataUtilHelper;

    public static void load() {
        String pack = Bukkit.getServer().getClass().getPackage().getName();
        String version = pack.substring(pack.lastIndexOf('.') + 1).replaceFirst("v", "");
        boolean isSupported = true;
        long start = System.currentTimeMillis();
        System.out.println("Loading PacketData for version " + version);
        if (true) {
            try {
                Class handler = Class.forName("com.mythicchaos.utils.nms.v" +
                        version.substring(0, version.lastIndexOf('_'))
                        + ".ItemUtil_v" + version);
                if (handler != null)
                    itemDataUtilHelper = (ItemDataUtilHelper) handler.newInstance();
                Bukkit.getLogger().info("Loaded ItemDataUtil for " + version);
            } catch (Exception e) {
                e.printStackTrace();
                Bukkit.getLogger().warning("Could not load ItemDataUtil for version " + version);
            }
            return;
        }
    }

}
