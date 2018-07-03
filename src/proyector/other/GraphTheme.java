/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyector.other;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.util.Random;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.util.DefaultShadowGenerator;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.VerticalAlignment;

/**
 *
 * @author JuanGS
 */
public class graphTheme {

    public void themeBarChart(JFreeChart ch) {
        StandardChartTheme theme = (StandardChartTheme) org.jfree.chart.StandardChartTheme.createJFreeTheme();
        String fontName = "Lucida Sans";
        theme.setTitlePaint(Color.decode("#4572a7"));
        theme.setExtraLargeFont(new Font(fontName, Font.BOLD, 17)); //title
        theme.setLargeFont(new Font(fontName, Font.BOLD, 15)); //axis-title
        theme.setRegularFont(new Font(fontName, Font.PLAIN, 11));
        theme.setRangeGridlinePaint(Color.decode("#C0C0C0"));
        theme.setPlotBackgroundPaint(Color.white);
        theme.setChartBackgroundPaint(Color.white);
        theme.setGridBandPaint(Color.red);
        theme.setAxisOffset(new RectangleInsets(0, 0, 0, 0));
        theme.setBarPainter(new StandardBarPainter());
        theme.setAxisLabelPaint(Color.decode("#666666"));
        theme.apply(ch);

        ch.getCategoryPlot().setOutlineVisible(false);
        ch.getCategoryPlot().getRangeAxis().setAxisLineVisible(false);
        ch.getCategoryPlot().getRangeAxis().setTickMarksVisible(false);
        ch.getCategoryPlot().setRangeGridlineStroke(new java.awt.BasicStroke());
        ch.getCategoryPlot().getRangeAxis().setTickLabelPaint(Color.decode("#666666"));
        ch.getCategoryPlot().getDomainAxis().setTickLabelPaint(Color.decode("#666666"));
        ch.setTextAntiAlias(true);
        ch.setAntiAlias(true);

        String[] colorM = {"#2196F3", "#FFC107", "#FF5722", "#8BC34A", "#E91E63", "#26A69A", "#96492D", "#FFEB3B", "#673AB7", "#00BCD4", "#8BC34A", "#F44336"};
        int i = 0;
        for (String color : colorM) {
            ch.getCategoryPlot().getRenderer().setSeriesPaint(i, Color.decode(color));
            i++;
        }

        BarRenderer rend = (BarRenderer) ch.getCategoryPlot().getRenderer();
        rend.setShadowVisible(true);
        rend.setShadowXOffset(4);
        rend.setShadowYOffset(2);
        rend.setShadowPaint(Color.decode("#C0C0C0"));
        rend.setMaximumBarWidth(0.1);
        rend.setItemMargin(-7);
    }

    public void themePieChart(JFreeChart ch, PiePlot p, int cantReg) {
        //theme
        StandardChartTheme theme = (StandardChartTheme) org.jfree.chart.StandardChartTheme.createJFreeTheme();
        theme.setTitlePaint(Color.decode("#4572a7"));
        theme.setChartBackgroundPaint(Color.white);
        theme.setPlotBackgroundPaint(Color.white);
        theme.setPlotOutlinePaint(Color.white);
        theme.setLabelLinkPaint(Color.red);
        theme.setExtraLargeFont(new Font("Lucida Sans", Font.BOLD, 17)); //title
        theme.setLargeFont(new Font("Lucida Sans", Font.BOLD, 15)); //axis-title
        theme.setRegularFont(new Font("Lucida Sans", Font.PLAIN, 11));
        theme.apply(ch);

        //Format Label
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        p.setLabelGenerator(labelGenerator);
        p.setShadowGenerator(new DefaultShadowGenerator());
        p.setLabelLinkStroke(new BasicStroke(2.0f));
        p.setBaseSectionOutlinePaint(Color.white);
        p.setBaseSectionOutlineStroke(new BasicStroke(1.5f));
        p.setLegendItemShape(new Rectangle(8, 6));
        p.setLabelBackgroundPaint(Color.white);

        //p.setLabelOutlinePaint(null);//Color.lightGray);
        p.setInteriorGap(0.12);
        p.setLabelLinkStyle(PieLabelLinkStyle.CUBIC_CURVE);

        String[] colorM = {"#2196F3", "#FFC107", "#FF5722", "#8BC34A", "#E91E63", "#26A69A", "#96492D", "#FFEB3B", "#673AB7", "#00BCD4", "#8BC34A", "#F44336"};
        for (int i = 0; i < cantReg; i++) {
            p.setSectionPaint(i, Color.decode(i < colorM.length ? colorM[i] : generateColor()));
        }

        ch.setAntiAlias(true);
        ch.getTitle().setPadding(2, 2, 15, 2);
        LegendTitle legend = ch.getLegend();
        legend.setPosition(RectangleEdge.RIGHT);
        legend.setVerticalAlignment(VerticalAlignment.CENTER);
    }

    public static String generateColor() {
        Color color;
        Random random = new Random();
        final float hue = random.nextFloat();
        final float saturation = 1.0f; //1.0 for brilliant, 0.0 for dull
        final float luminance = 0.6f; //1.0 for brighter, 0.0 for black
        color = Color.getHSBColor(hue, saturation, luminance);

        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        red = (red + color.getRed()) / 2;
        green = (green + color.getGreen()) / 2;
        blue = (blue + color.getBlue()) / 2;
        return String.format("#%02x%02x%02x", red, green, blue);
    }
}
