/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vista;

import conexion.AforoJPA;
import conexion.CeldaJPA;
import conexion.VerticalJPA;
import entidad.Aforo;
import entidad.Celda;
import entidad.Vertical;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import static java.awt.SystemColor.text;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.WindowConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author ALVIS
 */

//http://www.jfree.org/jfreechart/api/javadoc/org/jfree/chart/annotations/XYTextAnnotation.html

public class Grafica extends javax.swing.JFrame {
        
        Aforo aforo;
        AforoJPA afojpa;
        
        Vertical vertical;
        VerticalJPA verjpa;
        List<Vertical> verticales;
        
        Celda celda;
        CeldaJPA celdajpa;
        List<Celda> celdas;
        
        DecimalFormat df = new DecimalFormat("0.0000"); 
        
    public Grafica(Aforo aforo) {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("MADECSOFT");
        
        //aforo = new Aforo();
        verjpa = new VerticalJPA();
        celdajpa = new CeldaJPA();
        verticales = new ArrayList<>();
        celdas = new ArrayList<>();
        
        verticales = verjpa.ListarVerticales(aforo.getIdaforo());
        celdas = celdajpa.ListarCeldas(aforo.getIdaforo());
        //verticales = (List<Vertical>) aforo.getVerticalCollection();
        //celdas = (List<Celda>) aforo.getCeldaCollection();
        
        XYDataset paresDeDatos = generarDatos();
        JFreeChart diagrama = crearDiagrama(paresDeDatos);
        diagrama.setTitle("Perfil de Aforo");
        ChartPanel chartPanel = new ChartPanel(diagrama);
        chartPanel.setPreferredSize(new Dimension(450,200));
        
        jInternalFrame1.setContentPane(chartPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        llenarMenuOpciones();
        
        DecimalFormat df = new DecimalFormat("0.0000"); 

        Calendar fechaIni = Calendar.getInstance();
        Calendar fechaFin = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yy");
        SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
        fechaIni.set(aforo.getAnho(), aforo.getMes()-1, aforo.getDia(),aforo.getHoraInicio(), aforo.getMinutoInicio(), aforo.getSegundoInicio());
        fechaFin.set(aforo.getAnho(), aforo.getMes()-1, aforo.getDia(),aforo.getHoraFin(), aforo.getMinutoFin(), aforo.getSegundoFin());
        
        jTextPane3.setText("Nombre: "+aforo.getNombre()+
                "\nConfiguracion: "+aforo.getTipoAforo()+
                "\nHelice: "+aforo.getHelice()+
                "\nFecha: "+date.format(fechaIni.getTime())+
                "\nHora inicio: "+hour.format(fechaIni.getTime())+"  Hora fin: "+hour.format(fechaFin.getTime())+
                "\nTiempo por medicion[s]: "+aforo.getTiempoAforo()+
                "\nNumero de verticales: "+aforo.getNumeroVerticales()+"  Numero de celdas: "+aforo.getNumeroCeldas()+
                "\nLongitud total[m]: "+df.format(aforo.getLongitudTotal())+
                "\nArea total[m2]: "+df.format(aforo.getAreaTotal())+
                "\nProfundidad media[m]: "+df.format(aforo.getProfundidadMedia())+
                "\nVelocidad media[m/s]: "+df.format(aforo.getVelocidadMedia())+
                "\nMira inicial[cm]: "+aforo.getMiraInicial()+"   Mira final[cm]: "+aforo.getMiraFinal()+
                "\nCaudal total[m3/s]: "+df.format(aforo.getCaudalTotal()));
     
    }
 
    private XYDataset generarDatos(){
        
        XYSeries datos1 = new XYSeries("Linea Funcion1");
        XYSeries datos2 = new XYSeries("Linea Funcion21");
        datos1.add(0,15);
        for(int x=0; x<verticales.size(); x++){
            datos2.add(x,-verticales.get(x).getProfundidad());
        }
        datos2.add(verticales.size(),0);
        
        XYSeriesCollection conjuntoDatos = new XYSeriesCollection();
        conjuntoDatos.addSeries(datos2);
        conjuntoDatos.addSeries(datos1);
        
        return conjuntoDatos;
    }
 
private JFreeChart crearDiagrama(XYDataset conjuntoDatos){
        JFreeChart diag = ChartFactory.createXYAreaChart(
        "Graficador", //Titulo Grafica
        "Vertical", // Leyenda eje X
        "Profundidad", // Leyenda eje Y
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

public void llenarMenuOpciones() {
    for(int i=0; i<verticales.size(); i++) {
        cboxVerticales.addItem("vertical "+ (i+1));
    }
    
    for(int i=0; i<celdas.size(); i++) {
        cboxCeldas.addItem("celda "+ (i+1)); 
    }
    
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane3 = new javax.swing.JTextPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        cboxVerticales = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel6 = new javax.swing.JPanel();
        cboxCeldas = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Perfil de AFORO"));
        jPanel2.setToolTipText("");

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 345, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 245, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalles AFORO"));

        jTextPane3.setEditable(false);
        jScrollPane3.setViewportView(jTextPane3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jInternalFrame1))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jInternalFrame1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Verticales"));

        cboxVerticales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxVerticalesActionPerformed(evt);
            }
        });

        jTextPane1.setEditable(false);
        jTextPane1.setBorder(null);
        jScrollPane2.setViewportView(jTextPane1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(cboxVerticales, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane2)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(cboxVerticales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Celdas"));

        cboxCeldas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxCeldasActionPerformed(evt);
            }
        });

        jTextPane2.setEditable(false);
        jScrollPane1.setViewportView(jTextPane2);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(cboxCeldas, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 193, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(cboxCeldas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboxVerticalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboxVerticalesActionPerformed
        // TODO add your handling code here:
        vertical = new Vertical();
        
        vertical = verticales.get(cboxVerticales.getSelectedIndex());
        jTextPane1.setText("Profundidad[cm]: "+vertical.getProfundidad()+"\nDistancia Orilla[m]: "+vertical.getDistOrilla()+"\nVelocidad[m/s]: "+df.format(vertical.getVelocidad())+
                "\n\nVelocidad super[m/s]: "+df.format(vertical.getVelocidadSup())+
                "\nVelocidad fondo[m/s]: "+df.format(vertical.getVelocidadFondo())+
                "\n\nVueltas fondo: "+vertical.getVueltasFondo()+
                "\nVueltas super: "+vertical.getVueltasSup()+
                "\n\nVelocidad 20%: "+df.format(vertical.getVelocidad20())+"   Vueltas 20%: "+vertical.getVueltas20()+
                "\nVelocidad 40%: "+df.format(vertical.getVelocidad40())+"   Vueltas 40%: "+vertical.getVueltas40()+
                "\nVelocidad 60%: "+df.format(vertical.getVelocidad60())+"   Vueltas 60%: "+vertical.getVueltas60()+
                "\nVelocidad 80%: "+df.format(vertical.getVelocidad80())+"   Vueltas 80%: "+vertical.getVueltas80());
    }//GEN-LAST:event_cboxVerticalesActionPerformed

    private void cboxCeldasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboxCeldasActionPerformed
        // TODO add your handling code here:
        celda = new Celda();
        celda = celdas.get(cboxCeldas.getSelectedIndex());
        jTextPane2.setText("Profundidad[cm]: "+df.format(celda.getProfundidad())+"\nAncho[m]: "+df.format(celda.getAncho())+
                            "\nArea[m2]: "+df.format(celda.getArea())+"\nCaudal[m3/s]: "+df.format(celda.getCaudal())+
                            "\nVelocidad[m/s]: "+df.format(celda.getVelocidad()));
    }//GEN-LAST:event_cboxCeldasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Grafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Grafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Grafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Grafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Aforo af = new Aforo();
                new Grafica(af).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboxCeldas;
    private javax.swing.JComboBox cboxVerticales;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JTextPane jTextPane3;
    // End of variables declaration//GEN-END:variables
}
