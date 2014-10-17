package de.hx.bokIQTablleAltona.util;

import java.util.ArrayList;
import java.util.Calendar;

import android.view.View;
import android.widget.LinearLayout;

import de.hx.bokIQTablleAltona.models.ArtikelBerichtModel;

public class Utils {

    public static final int VIEW_WITH_EDIT_TEXT = 1;
    public static final int VIEW_WITHOUT_EDIT_TEXT = 0;

    public ArtikelBerichtModel getABWithArtikelNameFromList(
            ArrayList<ArtikelBerichtModel> aBList, String artikelName) {
        for (ArtikelBerichtModel thisAB : aBList) {
            if (thisAB.getArtikelName().equals(artikelName))
                return thisAB;
        }
        return null;
    }

    public void setMarginsToViews(int isWithEditText, View... views) {
        if (isWithEditText == VIEW_WITHOUT_EDIT_TEXT) {
            for (int i = 0; i < views.length; i++) {

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) views[i]
                        .getLayoutParams();
                lp.topMargin = 5;
                lp.leftMargin = 3;
                lp.bottomMargin = 5;
                lp.rightMargin = 3;
            }
        } else {
            for (int i = 0; i < views.length; i++) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) views[i]
                        .getLayoutParams();
                lp.topMargin = -8;
                lp.leftMargin = 3;
                lp.bottomMargin = -8;
                lp.rightMargin = 3;
            }
        }
    }

    public int getVonDaysFrom1970OfFirstDay(int monat, int jahr) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 0);
        c.set(Calendar.MONTH, monat - 1);
        c.set(Calendar.YEAR, jahr);
        return (int) (c.getTimeInMillis() / (1000 * 3600 * 24));
    }

    public int getVonDaysFrom1970OfLastDay(int monat, int jahr) {
        Calendar c = Calendar.getInstance();
        int day = 0;
        if (monat == 1 || monat == 3 || monat == 5 || monat == 7 || monat == 8
                || monat == 10 || monat == 12)
            day = 31;
        else if (monat == 2) {
            if (jahr % 4 == 0)
                day = 29;
            else
                day = 28;
        } else
            day = 30;
        c.set(Calendar.DAY_OF_MONTH, day - 1);
        c.set(Calendar.MONTH, monat - 1);
        c.set(Calendar.YEAR, jahr);
        return (int) (c.getTimeInMillis() / (1000 * 3600 * 24));
    }
}
