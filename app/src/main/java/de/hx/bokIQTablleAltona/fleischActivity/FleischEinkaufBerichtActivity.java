package de.hx.bokIQTablleAltona.fleischActivity;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import de.hx.bokIQTablleAltona.R;
import de.hx.bokIQTablleAltona.util.Utils;
import de.hx.bokIQTablleAltona.xml.fleisch.FleischEinkaufPreisXmlParser;

public class FleischEinkaufBerichtActivity extends Activity {

    FleischEinkaufPreisXmlParser fEPXP = new FleischEinkaufPreisXmlParser();
    int selectedMonat, selectedJahr, lastMonat, lastJahr;
    Map<String, Double> selectedMonatEinkaufspreisMap = null,
            lastMonatEinkaufspreisMap = null;
    DecimalFormat prozentZahl = new DecimalFormat("#.#");
    DecimalFormat df = new DecimalFormat("#.##");
    TableLayout table;
    Utils utils = new Utils();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fleisch_einkaufbericht);
        table = (TableLayout) findViewById(R.id.einkaufBerichtTableLayout);
        Intent intent = getIntent();
        selectedMonat = intent.getIntExtra("monat", 0);
        selectedJahr = intent.getIntExtra("jahr", 0);
        if (selectedMonat == 1) {
            lastJahr = selectedJahr - 1;
            lastMonat = 12;
        } else {
            lastJahr = selectedJahr;
            lastMonat = selectedMonat - 1;
        }
        try {
            selectedMonatEinkaufspreisMap = fEPXP.getMonatEinkaufspreisMap(
                    selectedMonat, selectedJahr);
            lastMonatEinkaufspreisMap = fEPXP.getMonatEinkaufspreisMap(
                    lastMonat, lastJahr);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.set(selectedJahr, selectedMonat, 0);


        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Einkaufsbericht von " + DateFormat.format("MMMM yyyy ", c.getTime()));

        if (selectedMonatEinkaufspreisMap != null) {
            for (String key : selectedMonatEinkaufspreisMap.keySet()) {
                TableRow tr = new TableRow(this);
                TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);

                tableRowParams.setMargins(2, 2, 2, 2);

                tr.setLayoutParams(tableRowParams);
                tr.setBackgroundResource(R.color.Brown);

                TextView artikelName = new TextView(this);
                artikelName.setText(key);
                artikelName.setTextAppearance(this,
                        android.R.style.TextAppearance_Large);
                artikelName.setBackgroundResource(R.color.White);
                tr.addView(artikelName);

                TextView lastMonatEinkaufspreis = new TextView(this);
                lastMonatEinkaufspreis
                        .setText(lastMonatEinkaufspreisMap != null ? (df
                                .format(lastMonatEinkaufspreisMap.get(key)) + "€")
                                : "N/A");
                lastMonatEinkaufspreis.setTextAppearance(this,
                        android.R.style.TextAppearance_Large);
                lastMonatEinkaufspreis.setBackgroundResource(R.color.White);
                tr.addView(lastMonatEinkaufspreis);

                TextView selectedMonatEinkaufspreis = new TextView(this);
                selectedMonatEinkaufspreis.setText(df
                        .format(selectedMonatEinkaufspreisMap.get(key))
                        + "€");
                selectedMonatEinkaufspreis.setTextAppearance(this,
                        android.R.style.TextAppearance_Large);
                selectedMonatEinkaufspreis.setBackgroundResource(R.color.White);
                tr.addView(selectedMonatEinkaufspreis);

                TextView einkaufspreisDifferenz = new TextView(this);
                einkaufspreisDifferenz
                        .setText(lastMonatEinkaufspreisMap != null ? (df
                                .format(selectedMonatEinkaufspreisMap.get(key)
                                        - lastMonatEinkaufspreisMap.get(key))
                                + "€") : "N/A");
                einkaufspreisDifferenz.setTextAppearance(this,
                        android.R.style.TextAppearance_Large);
                einkaufspreisDifferenz.setBackgroundResource(R.color.White);
                tr.addView(einkaufspreisDifferenz);

                TextView einkaufspreisDifferenzInProzent = new TextView(this);
                einkaufspreisDifferenzInProzent
                        .setText(lastMonatEinkaufspreisMap != null ? (prozentZahl
                                .format((selectedMonatEinkaufspreisMap.get(key)
                                        / lastMonatEinkaufspreisMap.get(key)) * 100) + "%")
                                : "N/A");
                einkaufspreisDifferenzInProzent.setTextAppearance(this,
                        android.R.style.TextAppearance_Large);
                einkaufspreisDifferenzInProzent.setBackgroundResource(R.color.White);
                tr.addView(einkaufspreisDifferenzInProzent);

                table.addView(tr);
                TableLayout.LayoutParams tlp = (TableLayout.LayoutParams) tr
                        .getLayoutParams();
                tlp.topMargin = 5;
                tlp.bottomMargin = 5;
                tlp.leftMargin = 2;
                tlp.rightMargin = 2;

                utils.setMarginsToViews(Utils.VIEW_WITHOUT_EDIT_TEXT,
                        artikelName, lastMonatEinkaufspreis,
                        selectedMonatEinkaufspreis, einkaufspreisDifferenz,
                        einkaufspreisDifferenzInProzent);
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        finish();
        return true;
    }

}
