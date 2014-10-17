package de.hx.bokIQTablleAltona.getraenkeActivity;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;

import de.hx.bokIQTablleAltona.R;

public class GetraenkeDiagrammActivity extends Activity {

    PieChart diagrammView;
    private Segment premixUndWasserSegment, bierSegment, weinSegment, kaffeeUndTeeSegment, sonstigeSegment;
    private double premixUndWasserUmsatz = 0, bierUmsatz = 0, weinUmsatz = 0, kaffeeUndTeeUmsatz = 0, sonstigeUmsatz = 0;
    private double totalUmsatz = 0;
    LinearLayout getraenkeInfos;
    TextView premixUndWasserUmsatzTextView, bierUmsatzTextView, weinUmsatzTextView, kaffeeUndTeeUmsatzTextView, sonstigeUmsatzTextView, totalUmsatzTextView;
    TextView premixUndWasserAnteilTextView, bierAnteilTextView, weinAnteilTextView, kaffeeUndTeeAnteilTextView, sonstigeAnteilTextView;
    DecimalFormat prozentZahl = new DecimalFormat("#.#");
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pie_chart);
        diagrammView = (PieChart) findViewById(R.id.diagrammView);
        getraenkeInfos = (LinearLayout) findViewById(R.id.getraenkeInfos);
        getraenkeInfos.setVisibility(View.VISIBLE);
        premixUndWasserUmsatzTextView = (TextView) findViewById(R.id.diagrammPremixUndWasserNettoUmsatz);
        bierUmsatzTextView = (TextView) findViewById(R.id.diagrammBierNettoUmsatz);
        weinUmsatzTextView = (TextView) findViewById(R.id.diagrammWeinNettoUmsatz);
        kaffeeUndTeeUmsatzTextView = (TextView) findViewById(R.id.diagrammKaffeeUndTeeNettoUmsatz);
        sonstigeUmsatzTextView = (TextView) findViewById(R.id.diagrammGetraenkeSonstigeNettoUmsatz);
        totalUmsatzTextView = (TextView) findViewById(R.id.diagrammGetraenkeTotalNettoUmsatz);
        premixUndWasserAnteilTextView = (TextView) findViewById(R.id.diagrammPremixUndWasserNettoUmsatzAnteil);
        bierAnteilTextView = (TextView) findViewById(R.id.diagrammBierNettoUmsatzAnteil);
        weinAnteilTextView = (TextView) findViewById(R.id.diagrammWeinNettoUmsatzAnteil);
        kaffeeUndTeeAnteilTextView = (TextView) findViewById(R.id.diagrammKaffeeUndTeeNettoUmsatzAnteil);
        sonstigeAnteilTextView = (TextView) findViewById(R.id.diagrammGetraenkeSonstigeNettoUmsatzAnteil);

        diagrammView.setTitle("Getränke Diagramm");
        Intent intent = getIntent();
        premixUndWasserUmsatz = intent.getDoubleExtra("premixUndWasserNettoUmsatz", 0);
        bierUmsatz = intent.getDoubleExtra("bierNettoUmsatz", 0);
        weinUmsatz = intent.getDoubleExtra("weinNettoUmsatz", 0);
        kaffeeUndTeeUmsatz = intent.getDoubleExtra("kaffeeUndTeeNettoUmsatz", 0);
        sonstigeUmsatz = intent.getDoubleExtra("sonstigeNettoUmsatz", 0);
        totalUmsatz = premixUndWasserUmsatz + bierUmsatz + weinUmsatz + kaffeeUndTeeUmsatz + sonstigeUmsatz;

        premixUndWasserSegment = new Segment("Premix und Wasser", premixUndWasserUmsatz);
        bierSegment = new Segment("Bier", bierUmsatz);
        weinSegment = new Segment("Wein", weinUmsatz);
        kaffeeUndTeeSegment = new Segment("Kaffee und Tee", kaffeeUndTeeUmsatz);
        sonstigeSegment = new Segment("Sonstige", sonstigeUmsatz);

        EmbossMaskFilter emf = new EmbossMaskFilter(
                new float[]{1, 1, 1}, 0.4f, 10, 8.2f);

        SegmentFormatter sf1 = new SegmentFormatter();
        sf1.configure(getApplicationContext(), R.xml.pie_segment_premix_und_wasser);

        sf1.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf2 = new SegmentFormatter();
        sf2.configure(getApplicationContext(), R.xml.pie_segment_bier);

        sf2.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf3 = new SegmentFormatter();
        sf3.configure(getApplicationContext(), R.xml.pie_segment_wein);

        sf3.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf4 = new SegmentFormatter();
        sf4.configure(getApplicationContext(), R.xml.pie_segment_kaffee_und_tee);

        sf4.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf5 = new SegmentFormatter();
        sf5.configure(getApplicationContext(), R.xml.pie_segment_sonstige);

        sf5.getFillPaint().setMaskFilter(emf);

        if (premixUndWasserUmsatz > 0)
            diagrammView.addSeries(premixUndWasserSegment, sf1);
        if (bierUmsatz > 0)
            diagrammView.addSeries(bierSegment, sf2);
        if (weinUmsatz > 0)
            diagrammView.addSeries(weinSegment, sf3);
        if (kaffeeUndTeeUmsatz > 0)
            diagrammView.addSeries(kaffeeUndTeeSegment, sf4);
        if (sonstigeUmsatz > 0)
            diagrammView.addSeries(sonstigeSegment, sf5);

        diagrammView.getBorderPaint().setColor(Color.TRANSPARENT);
        diagrammView.getBackgroundPaint().setColor(Color.TRANSPARENT);

        premixUndWasserUmsatzTextView.setText(df.format(premixUndWasserUmsatz) + "€");
        bierUmsatzTextView.setText(df.format(bierUmsatz) + "€");
        weinUmsatzTextView.setText(df.format(weinUmsatz) + "€");
        kaffeeUndTeeUmsatzTextView.setText(df.format(kaffeeUndTeeUmsatz) + "€");
        sonstigeUmsatzTextView.setText(df.format(sonstigeUmsatz) + "€");
        totalUmsatzTextView.setText(df.format(totalUmsatz) + "€");

        premixUndWasserAnteilTextView.setText(prozentZahl.format(100 * premixUndWasserUmsatz / totalUmsatz) + "%");
        bierAnteilTextView.setText(prozentZahl.format(100 * bierUmsatz / totalUmsatz) + "%");
        weinAnteilTextView.setText(prozentZahl.format(100 * weinUmsatz / totalUmsatz) + "%");
        kaffeeUndTeeAnteilTextView.setText(prozentZahl.format(100 * kaffeeUndTeeUmsatz / totalUmsatz) + "%");
        sonstigeAnteilTextView.setText(prozentZahl.format(100 * sonstigeUmsatz / totalUmsatz) + "%");
    }

}
