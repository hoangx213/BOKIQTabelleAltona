package de.hx.bokIQTablleAltona.fleischActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import de.hx.bokIQTablleAltona.R;
import de.hx.bokIQTablleAltona.getraenkeActivity.GetraenkeActivity;
import de.hx.bokIQTablleAltona.models.fleisch.FleischBestellungModel;
import de.hx.bokIQTablleAltona.models.fleisch.FleischModel;
import de.hx.bokIQTablleAltona.util.DatePickerFragment;
import de.hx.bokIQTablleAltona.util.Utils;
import de.hx.bokIQTablleAltona.xml.fleisch.FleischBestellungenXmlWriter;
import de.hx.bokIQTablleAltona.xml.fleisch.FleischEinkaufPreisXmlWriter;
import de.hx.bokIQTablleAltona.xml.fleisch.FleischModelXmlParser;

@SuppressLint("SimpleDateFormat")
public class FleischActivity extends FragmentActivity implements
        OnClickListener {

    ArrayList<FleischModel> fleischList;
    ArrayList<FleischBestellungModel> fleischBestellungenList;
    HashSet<Integer> indexList;
    Map<String, Double> fleischEinkaufPreisMap;
    HashSet<FleischBestellungModel> fleischBestellungenSet;
    FleischModelXmlParser fleischXP;
    TableLayout table;
    TextView datumFleischBestellungView;
    Button datumBtn, zuGetraenkeBtn;
    static final int PROBESTELLUNGID = 1111;
    static final int TOTALID = 2222;
    static final int NETTOUMSATZID = 3333;
    static final int NETTOEINKAUFID = 4444;
    static final int BRUTTOEINKAUFID = 5555;
    static final int BRUTTOUMSATZID = 6666;
    static final int WARENEINSATZID = 7777;
    static final int ANTEILID = 8888;
    static final int BESTELLUNGENID = 9999;
    static final int EINKAUFSPREISID = 1234;
    static final int VERKAUFSPREISID = 4321;
    DecimalFormat df = new DecimalFormat("#.##");
    DecimalFormat prozentZahl = new DecimalFormat("#.#");
    Button fleischSaveBtn;
    String bestellungID = "";
    double nettoUmsatzsumme = 0;
    double nettoEinkaufssumme = 0;
    double portionSumme = 0;
    int daysFrom1970;
    static Calendar calendar;
    String bestellungsdatum;
    Utils utils = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fleisch_bestellung);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Fleischbestellung");

        fleischSaveBtn = (Button) findViewById(R.id.fleischSaveBtn);
        fleischSaveBtn.setOnClickListener(this);
        table = (TableLayout) findViewById(R.id.TableLayout1);
        datumFleischBestellungView = (TextView) findViewById(R.id.datumFleischBestellungTextView);
        datumBtn = (Button) findViewById(R.id.datumBtn);
        zuGetraenkeBtn = (Button) findViewById(R.id.zuGetraenkeButton);
        fleischList = new ArrayList<FleischModel>();
        fleischBestellungenSet = new HashSet<FleischBestellungModel>();
        fleischEinkaufPreisMap = new HashMap<String, Double>();
        fleischXP = new FleischModelXmlParser(getApplicationContext());
        indexList = new HashSet<Integer>();

        try {
            fleischList = fleischXP.fleischParsen();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        datumBtn.setOnClickListener(this);
        zuGetraenkeBtn.setOnClickListener(this);

        for (FleischModel i : fleischList) {
            TableRow tr = new TableRow(this);
            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            tableRowParams.setMargins(2, 2, 2, 2);

            tr.setLayoutParams(tableRowParams);
            tr.setBackgroundResource(R.color.Brown);

            TextView artikelName = new TextView(this);
            artikelName.setText(i.getArtikelName());
            artikelName.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            artikelName.setBackgroundResource(R.color.White);
            tr.addView(artikelName);

            TextView proBestell = new TextView(this);
            proBestell.setText(df.format(i.getEinheitProBestellung()) + " "
                    + i.getEinheit());
            proBestell.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            proBestell.setBackgroundResource(R.color.White);
            proBestell.setId(PROBESTELLUNGID);
            tr.addView(proBestell);

            EditText bestellungen = new EditText(this);
            bestellungen.setInputType(InputType.TYPE_CLASS_NUMBER);
            bestellungen.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            bestellungen.setBackgroundResource(R.color.AntiqueWhite);
            bestellungen.setId(BESTELLUNGENID);
            tr.addView(bestellungen);

            TextView total = new TextView(this);
            total.setTextAppearance(this, android.R.style.TextAppearance_Large);
            total.setBackgroundResource(R.color.White);
            total.setId(TOTALID);
            tr.addView(total);

            EditText einkaufspreis = new EditText(this);
            einkaufspreis.setInputType(InputType.TYPE_CLASS_PHONE);
            einkaufspreis.setText(String.valueOf(i.getEinkaufspreis()));
            einkaufspreis.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            einkaufspreis.setBackgroundResource(R.color.AntiqueWhite);
            einkaufspreis.setId(EINKAUFSPREISID);
            tr.addView(einkaufspreis);

            TextView nettoEinkauf = new TextView(this);
            nettoEinkauf.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            nettoEinkauf.setBackgroundResource(R.color.White);
            nettoEinkauf.setId(NETTOEINKAUFID);
            tr.addView(nettoEinkauf);

            TextView bruttoEinkauf = new TextView(this);
            bruttoEinkauf.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            bruttoEinkauf.setBackgroundResource(R.color.White);
            bruttoEinkauf.setId(BRUTTOEINKAUFID);
            tr.addView(bruttoEinkauf);

            EditText verkaufspreis = new EditText(this);
            verkaufspreis.setInputType(InputType.TYPE_CLASS_PHONE);
            verkaufspreis.setText(String.valueOf(i.getNettoPreis()));
            verkaufspreis.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            verkaufspreis.setBackgroundResource(R.color.AntiqueWhite);
            verkaufspreis.setId(VERKAUFSPREISID);
            tr.addView(verkaufspreis);

            TextView nettoUmsatz = new TextView(this);
            nettoUmsatz.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            nettoUmsatz.setBackgroundResource(R.color.White);
            nettoUmsatz.setId(NETTOUMSATZID);
            tr.addView(nettoUmsatz);

            TextView anteil = new TextView(this);
            anteil.setTextAppearance(this, android.R.style.TextAppearance_Large);
            anteil.setBackgroundResource(R.color.White);
            anteil.setId(ANTEILID);
            tr.addView(anteil);

            TextView bruttoUmsatz = new TextView(this);
            bruttoUmsatz.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            bruttoUmsatz.setBackgroundResource(R.color.White);
            bruttoUmsatz.setId(BRUTTOUMSATZID);
            tr.addView(bruttoUmsatz);

            TextView wareneinsatz = new TextView(this);
            wareneinsatz.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            wareneinsatz.setBackgroundResource(R.color.White);
            wareneinsatz.setId(WARENEINSATZID);
            tr.addView(wareneinsatz);

            table.addView(tr);
            TableLayout.LayoutParams tlp = (TableLayout.LayoutParams) tr
                    .getLayoutParams();
            tlp.topMargin = 5;
            tlp.bottomMargin = 5;
            tlp.leftMargin = 2;
            tlp.rightMargin = 2;

            utils.setMarginsToViews(Utils.VIEW_WITH_EDIT_TEXT, artikelName,
                    proBestell, bestellungen, total, verkaufspreis,
                    nettoUmsatz, einkaufspreis, nettoEinkauf, bruttoEinkauf,
                    bruttoUmsatz, wareneinsatz, anteil);

            bestellungen.addTextChangedListener(new MyTextWatcher(bestellungen,
                    fleischList.indexOf(i)));
            einkaufspreis.addTextChangedListener(new MyTextWatcher(
                    einkaufspreis, fleischList.indexOf(i)));
            verkaufspreis.addTextChangedListener(new MyTextWatcher(
                    verkaufspreis, fleischList.indexOf(i)));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Wollen Sie den Tabelle wirklich verlassen?")
                .setMessage("Die Daten werden nicht gespeichert.")
                .setNegativeButton("Nein", null)
                .setPositiveButton("JA", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        FleischActivity.super.onBackPressed();
                    }

                }).create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    class MyTextWatcher implements TextWatcher {

        EditText view;
        int index;
        int bestellungBefore;

        public MyTextWatcher(EditText view, int index) {
            this.view = view;
            this.index = index;
        }

        @Override
        public void afterTextChanged(Editable s) {
            TableRow tr = (TableRow) view.getParent();
            EditText bestellungenView = (EditText) tr
                    .findViewById(BESTELLUNGENID);
            EditText einkaufspreisView = (EditText) tr
                    .findViewById(EINKAUFSPREISID);
            EditText verkaufspreisView = (EditText) tr
                    .findViewById(VERKAUFSPREISID);
            int bestellungen = 0;
            double einkaufspreis = 0, verkaufspreis = 0;
            bestellungen = ((bestellungenView.getText().toString()).equals("") ? 0
                    : Integer.valueOf(bestellungenView.getText().toString()));
            einkaufspreis = ((einkaufspreisView.getText().toString())
                    .equals("") ? 0 : Double.valueOf(einkaufspreisView
                    .getText().toString()));
            verkaufspreis = ((verkaufspreisView.getText().toString())
                    .equals("") ? 0 : Double.valueOf(verkaufspreisView
                    .getText().toString()));

            if (bestellungen > 500 || einkaufspreis > 30 || verkaufspreis > 30) {
                Toast.makeText(
                        getApplicationContext(),
                        "Bestellungen,  Einkaufspreis oder Verkaufspreis zu hoch!!!",
                        Toast.LENGTH_LONG).show();
            }

            // Khi Bestellung leer thi cac Felder khac ko can phai thay doi
            else if (!bestellungenView.getText().toString().equals("")
                    || view.getId() == BESTELLUNGENID) {
                if (view.getId() == BESTELLUNGENID
                        && !bestellungenView.getText().toString().equals(""))
                    indexList.add(index);
                TextView totalView = (TextView) tr.findViewById(TOTALID);
                TextView nettoUmsatzView = (TextView) tr
                        .findViewById(NETTOUMSATZID);
                TextView nettoEinkaufView = (TextView) tr
                        .findViewById(NETTOEINKAUFID);
                TextView bruttoEinkaufView = (TextView) tr
                        .findViewById(BRUTTOEINKAUFID);
                TextView bruttoUmsatzView = (TextView) tr
                        .findViewById(BRUTTOUMSATZID);
                TextView wareneinsatzView = (TextView) tr
                        .findViewById(WARENEINSATZID);
                TextView nettoUmsatzssummeView = (TextView) findViewById(R.id.nettoUmsatzSumme);
                TextView einkaufssummeView = (TextView) findViewById(R.id.nettoEinkaufssumme);

                FleischModel fleisch = fleischList.get(index);

                double totalBestellung = (bestellungen * fleisch
                        .getEinheitProBestellung());
                totalView.setText(String.valueOf(totalBestellung));

                double verkaufsmenge = totalBestellung
                        - (totalBestellung * fleisch.getSchwund());

                double portion = verkaufsmenge * fleisch.getVerkaufsfaktor();

                double nettoUmsatz = portion * verkaufspreis;
                nettoUmsatzView.setText(df.format(nettoUmsatz));

                double nettoEinkauf = totalBestellung * einkaufspreis;
                nettoEinkaufView.setText(df.format(nettoEinkauf));

                double bruttoEinkauf = nettoEinkauf * 1.12;
                bruttoEinkaufView.setText(df.format(bruttoEinkauf));

                double bruttoUmsatz = nettoUmsatz * 1.12;
                bruttoUmsatzView.setText(df.format(bruttoUmsatz));

                double wareneinsatz = nettoEinkauf / nettoUmsatz;
                wareneinsatzView.setText(df.format(wareneinsatz * 100) + "%");

                TableLayout tl = (TableLayout) tr.getParent();
                nettoUmsatzsumme = 0;
                nettoEinkaufssumme = 0;
                for (int j : indexList) {
                    TableRow thisTr = (TableRow) tl.getChildAt(j + 1);
                    if (((TextView) thisTr.findViewById(NETTOUMSATZID))
                            .getText() != "") {
                        double thisNettoUmsatz = Double
                                .valueOf(((String) ((TextView) thisTr
                                        .findViewById(NETTOUMSATZID)).getText())
                                        .replace(",", "."));
                        nettoUmsatzsumme += thisNettoUmsatz;
                    }
                    if (((TextView) thisTr.findViewById(NETTOEINKAUFID))
                            .getText() != "") {
                        double thisEinkaufTotal = Double
                                .valueOf(((String) ((TextView) thisTr
                                        .findViewById(NETTOEINKAUFID))
                                        .getText()).replace(",", "."));
                        nettoEinkaufssumme += thisEinkaufTotal;
                    }
                }

                for (int j : indexList) {
                    TableRow thisTr = (TableRow) tl.getChildAt(j + 1);
                    TextView thisAnteilView = (TextView) thisTr
                            .findViewById(ANTEILID);
                    if (((TextView) thisTr.findViewById(NETTOUMSATZID))
                            .getText() != "") {
                        double thisNettoUmsatz = Double
                                .valueOf(((String) ((TextView) thisTr
                                        .findViewById(NETTOUMSATZID)).getText())
                                        .replace(",", "."));
                        double thisAnteil = thisNettoUmsatz / nettoUmsatzsumme;
                        String gerundeteAnteil = prozentZahl
                                .format(thisAnteil * 100);
                        thisAnteilView.setText(gerundeteAnteil + "%");
                    }
                }

                if (this.bestellungBefore != 0) {
                    double totalBestellungBefore = (bestellungBefore * fleisch
                            .getEinheitProBestellung());

                    double verkaufsmengeBefore = totalBestellungBefore
                            - (totalBestellungBefore * fleisch.getSchwund());

                    double portionBefore = verkaufsmengeBefore
                            * fleisch.getVerkaufsfaktor();
                    portionSumme -= portionBefore;
                }
                portionSumme += portion;

                nettoUmsatzssummeView
                        .setText(df.format(nettoUmsatzsumme) + "€");
                einkaufssummeView.setText(df.format(nettoEinkaufssumme) + "€");
                String proBestellung = ((TextView) tr
                        .findViewById(PROBESTELLUNGID)).getText().toString();
                FleischBestellungModel fb = new FleischBestellungModel(fleisch,
                        proBestellung, bestellungen, totalBestellung, portion,
                        einkaufspreis, nettoEinkauf, bruttoEinkauf,
                        verkaufspreis, nettoUmsatz, bruttoUmsatz, wareneinsatz);

                if (!fleischBestellungenSet.add(fb)) {
                    fleischBestellungenSet.remove(fb);
                    fleischBestellungenSet.add(fb);
                }
            }
            // Khi Bestellung bi xoa thanh leer thi xoa luon fb trong Set
            if (bestellungenView.getText().toString().equals("")
                    && view.getId() == BESTELLUNGENID) {
                FleischModel fleisch = fleischList.get(index);
                FleischBestellungModel fb = new FleischBestellungModel(fleisch);
                fleischBestellungenSet.remove(fb);
                indexList.remove(index);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            if (this.view.getId() == BESTELLUNGENID) {
                if (!s.toString().equals("")) {
                    this.bestellungBefore = Integer.valueOf(s.toString());
                } else {
                    this.bestellungBefore = 0;
                }
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fleischSaveBtn: {
                if (datumFleischBestellungView.getText().equals("")) {
                    Toast.makeText(this, "Bitte Datum auswählen", Toast.LENGTH_LONG)
                            .show();
                } else {

                    bestellungsdatum = datumFleischBestellungView.getText()
                            .toString();
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                    Date d = null;
                    try {
                        d = format.parse(bestellungsdatum);
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    calendar = Calendar.getInstance();
                    calendar.setTime(d);
                    daysFrom1970 = (int) (calendar.getTimeInMillis() / (1000 * 3600 * 24));

                    fleischBestellungenList = new ArrayList<FleischBestellungModel>(
                            fleischBestellungenSet);
                    Collections.sort(fleischBestellungenList, fleischComparator);
                    if (!fleischBestellungenList.isEmpty()) {
                        new AlertDialog.Builder(this)
                                .setTitle("Wollen Sie die Daten speichern und den Tabelle verlassen?")
                                .setNegativeButton("Nein", null)
                                .setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        saveData();
                                        Intent returnIntent = new Intent();
                                        returnIntent.putExtra("bestellungID", bestellungID);
                                        returnIntent.putExtra("datum", bestellungsdatum);
                                        returnIntent.putExtra("daysFrom1970", daysFrom1970);
                                        setResult(RESULT_OK, returnIntent);
                                        finish();
                                    }
                                }).create().show();
                    } else {
                        Toast.makeText(this, "Nichts zu speichern",
                                Toast.LENGTH_LONG).show();
                    }
                }
                break;
            }
            case R.id.datumBtn: {
                DialogFragment newFragment = new DatePickerFragment(
                        datumFleischBestellungView);
                newFragment.show(getSupportFragmentManager(), "datePicker");
                break;
            }
            case R.id.zuGetraenkeButton: {
                new AlertDialog.Builder(this)
                        .setTitle("Wollen Sie den Tabelle wirklich verlassen?")
                        .setMessage("Die Daten werden nicht gespeichert.")
                        .setNegativeButton("Nein", null)
                        .setPositiveButton("JA", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                startActivity(new Intent(getApplicationContext(), GetraenkeActivity.class));
                            }

                        }).create().show();
            }
            default:
                break;
        }
    }

    private void saveData() {
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        bestellungID = UUID.randomUUID().toString();
        for (FleischBestellungModel i : fleischBestellungenList) {
            fleischEinkaufPreisMap.put(i.getFleischModel()
                    .getArtikelName(), i.getEinkaufspreis());
        }
        FleischEinkaufPreisXmlWriter fepw = new FleischEinkaufPreisXmlWriter();
        FleischBestellungenXmlWriter fbw = new FleischBestellungenXmlWriter();
        try {
            fbw.writeFleischBestellungenXml(
                    fleischBestellungenList, bestellungID, portionSumme,
                    nettoUmsatzsumme, nettoEinkaufssumme,
                    bestellungsdatum, daysFrom1970);
            fepw.writeFleischEinkaufpreisXml(
                    fleischEinkaufPreisMap, month, year);
        } catch (TransformerFactoryConfigurationError e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Comparator<FleischBestellungModel> fleischComparator = new Comparator<FleischBestellungModel>() {
        @Override
        public int compare(FleischBestellungModel lhs,
                           FleischBestellungModel rhs) {
            return Integer.valueOf(lhs.getFleischModel().getOrder()).compareTo(
                    Integer.valueOf(rhs.getFleischModel().getOrder()));
        }
    };

}
