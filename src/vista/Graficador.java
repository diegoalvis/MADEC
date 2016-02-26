
package vista;


import conexion.AforoJPA;
import conexion.VerticalJPA;
import entidad.Aforo;
import entidad.Vertical;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.ui.RectangleInsets;
 

public class Graficador extends ApplicationFrame{
    
    Aforo aforo;
    AforoJPA afojpa;
    Vertical vertical;
    VerticalJPA verjpa;
    List<Vertical> verticales;
        
    
    public Graficador(Aforo aforo){
        super("Graficador");
        //aforo = new Aforo();
        verticales = new ArrayList<>();
        verjpa = new VerticalJPA();
        
        verticales = verjpa.ListarVerticales(aforo.getIdaforo());
        
        XYDataset paresDeDatos = generarDatos();
        JFreeChart diagrama = crearDiagrama(paresDeDatos);
        ChartPanel chartPanel = new ChartPanel(diagrama);
        chartPanel.setPreferredSize(new Dimension(700,300));
        setContentPane(chartPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
 
    private XYDataset generarDatos(){
        
        XYSeries datos1 = new XYSeries("Linea Funcion1");
        XYSeries datos2 = new XYSeries("Linea Funcion21");
        for(int x=0; x<verticales.size(); x++){
            datos2.add(x,-verticales.get(x).getProfundidad());
            //datos2.add(x,verticales.get(x));
        }
        datos1.add(verticales.size(),0);
        
        
        XYSeriesCollection conjuntoDatos = new XYSeriesCollection();
        conjuntoDatos.addSeries(datos2);
        conjuntoDatos.addSeries(datos1);
        
        return conjuntoDatos;
    }
 
private JFreeChart crearDiagrama(XYDataset conjuntoDatos){
        JFreeChart diag = ChartFactory.createXYAreaChart(
        "Graficador", //Titulo Grafica
        "X", // Leyenda eje X
        "Y", // Leyenda eje Y
        conjuntoDatos, // Los datos
        PlotOrientation.VERTICAL, //orientacion
        false, // ver titulo de linea
        true, //tooltips
        false //URL
        );
        
        diag.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) diag.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        

        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
            renderer.setDrawSeriesLineAsPath(true);
            renderer.setSeriesLinesVisible(0,true);
            renderer.setSeriesLinesVisible(1,false);
        }
return diag;
}
 
//aqui definimos la funcion que desees, en esta caso la f(x) = 4sen(x)
private double f(double x){
return 4*Math.sin(x);
}

private double g(double x){
    return 10*Math.cos(x);
}


}
