package com.example.a3mpe.androidlauncher;

/**
 * Created by Gaaraj on 15.05.2017.
 */

public class SortApps {
    public void exchange_sort(MainActivity.Pac[] pacs) {

        MainActivity.Pac temp;

        for (int i = 0; i < pacs.length; i++) {
            for (int j = i + 1; j < pacs.length; j++) {
                if (pacs[i].label.compareToIgnoreCase(pacs[j].label) > 0) {
                    temp = pacs[i];
                    pacs[i] = pacs[j];
                    pacs[j] = temp;
                }
            }
        }
    }
}
