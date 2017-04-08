/*
 * Copyright (c) AlphaHelix 2017
 */

package de.alphahelix.pinger;

import de.alphahelix.alphaapi.reflection.ReflectionUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class PingUtil {

    private static HashMap<String, ArrayList<Integer>> pingMap = new HashMap<>();

    public static int getPing(Player p) {

        ArrayList<Integer> pingList = new ArrayList<>();
        int currPing = ReflectionUtil.getPing(p);

        if (pingMap.containsKey(p.getName()))
            pingList = pingMap.get(p.getName());

        if (currPing < 1500)
            pingList.add(currPing);

        pingMap.put(p.getName(), pingList);

        return currPing;
    }

    public static int getAveragePing(Player p) {
        if (pingMap.containsKey(p.getName())) {
            int total = pingMap.get(p.getName()).size();
            int sum = 0;

            for (int ping : pingMap.get(p.getName())) {
                sum += ping;
            }

            return sum / total;
        } else {
            return getPing(p);
        }
    }
}
