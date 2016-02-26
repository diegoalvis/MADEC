/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vista;

import conexion.AforoJPA;
import conexion.CeldaJPA;
import conexion.VerticalJPA;
import entidad.Aforo;
import entidad.Celda;
import entidad.Vertical;
import gnu.io.*;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Communicator implements SerialPortEventListener
{
    //Instancia clases entidad y conexion JPA
    Aforo aforo;
    Vertical vertical;
    Celda celda;
    
    AforoJPA afoJPA;
    VerticalJPA verJPA;
    CeldaJPA celJPA;
    
    //passed from main GUI
    ComunicacionGUI window = null;

    //for containing the ports that will be found
    private Enumeration ports = null;
    //map the port names to CommPortIdentifiers
    private HashMap portMap = new HashMap();
    int i=0;
    
    //this is the object that contains the opened port
    private CommPortIdentifier selectedPortIdentifier = null;
    private SerialPort serialPort = null;

    //input and output streams for sending and receiving data
    private InputStream input = null;
    private OutputStream output = null;

    //just a boolean flag that i use for enabling
    //and disabling buttons depending on whether the program
    //is connected to a serial port or not
    private boolean bConnected = false;

    //the timeout value for connecting with the port
    final static int TIMEOUT = 2000;

    //some ascii values for for certain things
    final static int SPACE_ASCII = 32;
    final static int DASH_ASCII = 45;
    final static int NEW_LINE_ASCII = 10;

    //a string for recording what goes on in the program
    //this string is written to the GUI
    String logText = "";

    //Array to save data of the InputStream
    List<Byte> by = new ArrayList<>();
    
    //Banderas para finalizacion de trama
    boolean flag = true;
    boolean finish = true;
    
    public Communicator(ComunicacionGUI window)
    {
        this.window = window;
    }

    //search for all the serial ports
    //pre: none
    //post: adds all the found ports to a combo box on the GUI
    public void searchForPorts()
    {
        ports = CommPortIdentifier.getPortIdentifiers();

        while (ports.hasMoreElements())
        {
            CommPortIdentifier curPort = (CommPortIdentifier)ports.nextElement();

            //get only serial ports
            if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL)
            {
                window.cboxPorts.addItem(curPort.getName());
                portMap.put(curPort.getName(), curPort);
            }
        }
    }

    //connect to the selected port in the combo box
    //pre: ports are already found by using the searchForPorts method
    //post: the connected comm port is stored in commPort, otherwise,
    //an exception is generated
    public void connect()
    {
        String selectedPort = (String)window.cboxPorts.getSelectedItem();
        selectedPortIdentifier = (CommPortIdentifier)portMap.get(selectedPort);

        CommPort commPort = null;

        try
        {
            //the method below returns an object of type CommPort
            commPort = selectedPortIdentifier.open("TigerControlPanel", TIMEOUT);
            //the CommPort object can be casted to a SerialPort object
            serialPort = (SerialPort)commPort;

            //for controlling GUI elements
            setConnected(true);
            
            //logging
            //logText = selectedPort + " Conectado";
            window.txtLog.setForeground(Color.black);
            window.txtLog.setText(logText + "\n");

            //CODE ON SETTING BAUD RATE ETC OMITTED
            //XBEE PAIR ASSUMED TO HAVE SAME SETTINGS ALREADY
            
            //enables the controls on the GUI if a successful connection is made
            window.cboxPorts.setEnabled(false);
            
        }
        catch (PortInUseException e)
        {
            logText = selectedPort + " is in use. (" + e.toString() + ")";
            
            window.txtLog.setForeground(Color.red);
            window.txtLog.setText(logText + "\n");
            window.jToggleButton1.setSelected(false);
            window.cboxPorts.setEnabled(true);
        }
        catch (Exception e)
        {
            logText = "Fallo al conectar";
            window.txtLog.setText(logText + "\n");
            window.txtLog.setForeground(Color.red);
            window.jToggleButton1.setSelected(false);
            window.cboxPorts.setEnabled(true);
        }
    }

    //open the input and output streams
    //pre: an open port
    //post: initialized intput and output streams for use to communicate data
    public boolean initIOStream()
    {
        //return value for whather opening the streams is successful or not
        boolean successful = false;

        try {
            //
            input = serialPort.getInputStream();
            output = serialPort.getOutputStream();
            writeData(0);
            
            successful = true;
            return successful;
        }
        catch (IOException e) {
            logText = "I/O Streams failed to open";
            window.txtLog.setForeground(Color.red);
            window.txtLog.setText(logText + "\n");
            return successful;
        }
    }

    //starts the event listener that knows whenever data is available to be read
    //pre: an open serial port
    //post: an event listener for the serial port that knows when data is recieved
    public void initListener()
    {
        try
        {
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        }
        catch (TooManyListenersException e)
        {
            logText = "Too many listeners";
            window.txtLog.setForeground(Color.red);
            window.txtLog.setText(logText + "\n");
        }
    }

    //disconnect the serial port
    //pre: an open serial port
    //post: clsoed serial port
    public void disconnect()
    {
        //close the serial port
        try
        {
            writeData(0);

            serialPort.removeEventListener();
            serialPort.close();
            input.close();
            output.close();
            setConnected(false);
            window.cboxPorts.setEnabled(true);
            
            logText = " Desconectado";
            window.txtLog.setForeground(Color.black);
            window.txtLog.setText(logText + "\n");
        }
        catch (Exception e)
        {
            logText = "Failed to close " + serialPort.getName();
            window.txtLog.setForeground(Color.red);
            window.txtLog.setText(logText + "\n");
            window.cboxPorts.setEnabled(false);
            
        }
    }

    final public boolean getConnected()
    {
        return bConnected;
    }

    public void setConnected(boolean bConnected)
    {
        this.bConnected = bConnected;
    }

    //what happens when data is received
    //pre: serial event is triggered
    //post: processing on the data it reads
    public void serialEvent(SerialPortEvent evt) {
        if (evt.getEventType() == SerialPortEvent.DATA_AVAILABLE)
        {
            
            try
            {
                byte singleData = (byte)input.read();
                by.add(singleData);
                
                if (flag==true && singleData!=(byte)0x4f)
                {
                    window.txtLog.setText("MADEC NO CONECTADO");
                }
                else
                {
                    window.txtLog.setText("MADEC Conectado");
                    flag=false;
                }
                
                if(finish==true && (byte)singleData==(byte)0x3c) {
                    //i=0;
                    by.clear();
                    finish=false;
                    window.txtInfo.setForeground(Color.black);
                    window.txtInfo.setText("Recibiendo...");
                    window.jToggleButton1.setEnabled(false);
                    window.jButton1.setEnabled(false);
                    System.out.println("inicio");
                }
                
                else if(finish==false && singleData==(byte)0xff) {
                    if(by.get(by.size()-1)==by.get(by.size()-2)) 
                    {
                        finish=true;
                        window.jToggleButton1.setEnabled(true);
                        Guardar();
                        window.jButton1.setEnabled(true);
                        window.txtInfo.setText("Aforo recibido");
                        System.out.println("termino " + by.size());
                        
                    }
                }
           
            }
            catch (Exception e)
            {
                logText = "No se pudo recibir datos";
                window.txtLog.setForeground(Color.red);
                window.txtLog.setText(logText + "\n");
            }
        }
    }

    public void Guardar() 
    {
        aforo = new Aforo();
        vertical = new Vertical();
        celda = new Celda();
        afoJPA = new AforoJPA();
        verJPA = new VerticalJPA();
        celJPA = new CeldaJPA();
        
        try {
             int i = 0;
             byte[] nombre = new byte[11];
        
             while(by.get(i)!=(byte)0x00) {
                nombre[i] = by.get(i);
                i++;            
             }
             //Set nombre y valores del Aforo
        
             aforo.setNombre(new String(nombre, "UTF-8"));
             aforo.setTipoAforo(new Integer(by.get(11)));
             aforo.setHelice(new Integer(by.get(12)));
             aforo.setDia(new Integer(by.get(13)));
             aforo.setMes(new Integer(by.get(14)));
             aforo.setAnho((new Integer(by.get(15))) + 2000);
             aforo.setNumeroCeldas(new Integer(by.get(16)));
             aforo.setNumeroVerticales(new Integer(by.get(17)));
             aforo.setHoraInicio(new Integer(by.get(18)));
             aforo.setMinutoInicio(new Integer(by.get(19)));
             aforo.setSegundoInicio(new Integer(by.get(20)));
             aforo.setHoraFin(new Integer(by.get(21)));
             aforo.setMinutoFin(new Integer(by.get(22)));
             aforo.setSegundoFin(new Integer(by.get(23)));
             
             byte[] bytes = new byte[2];
             bytes[0]=by.get(24); bytes[1]=by.get(25);
             
             ByteBuffer buffer = ByteBuffer.wrap(bytes);
             buffer.order(ByteOrder.LITTLE_ENDIAN);  // if you want little-endian
             
             aforo.setTiempoAforo(new Integer(buffer.getShort()));
             
             //Almacenar valores flotantes del Aforo
             aforo.setMiraInicial(new Double(ConvertirToFloat(26)));
             aforo.setLongitudTotal(new Double(ConvertirToFloat(30)));
             aforo.setProfundidadMedia(new Double(ConvertirToFloat(34)));
             aforo.setVelocidadMedia(new Double(ConvertirToFloat(38)));
             aforo.setAreaTotal(new Double(ConvertirToFloat(42)));
             aforo.setCaudalTotal(new Double(ConvertirToFloat(46)));
             aforo.setMiraFinal(new Double(ConvertirToFloat(50)));
             aforo.setUbicacionVertical1(new Double(ConvertirToFloat(54)));
             
             //Almacenar en Base de Datos
             if(afoJPA.CrearAforo(aforo)) 
             {
                
                window.txtLog.setText(window.txtLog.getText()+"\nAforo recibido con exito");
             }
             else 
             {
                window.txtLog.setText(window.txtLog.getText()+"No se pudo guardar Aforo");
             }
        
             //Set valores enteros de las verticales y celdas
             int numVerticales = (int)aforo.getNumeroVerticales();
             int numCeldas = (int)aforo.getNumeroCeldas();
             int iteracion=-48;
             
             vertical.setIdaforo((Aforo)afoJPA.ListarAforos().get(afoJPA.ListarAforos().size()-1));
             while(numVerticales>0)
             {
                iteracion=iteracion+48;
                //para convertir a entero de 16 bits
                bytes[0]=by.get(58+iteracion); bytes[1]=by.get(59+iteracion);
                buffer = ByteBuffer.wrap(bytes);
                buffer.order(ByteOrder.LITTLE_ENDIAN);  // if you want little-endian
                vertical.setVueltas20(new Integer(buffer.getShort()));
                
                bytes[0]=by.get(60+iteracion); bytes[1]=by.get(61+iteracion);
                buffer = ByteBuffer.wrap(bytes);
                buffer.order(ByteOrder.LITTLE_ENDIAN);  // if you want little-endian
                vertical.setVueltas40(new Integer(buffer.getShort()));
                
                bytes[0]=by.get(62+iteracion); bytes[1]=by.get(63+iteracion);
                buffer = ByteBuffer.wrap(bytes);
                buffer.order(ByteOrder.LITTLE_ENDIAN);  // if you want little-endian
                vertical.setVueltas60(new Integer(buffer.getShort()));
                
                bytes[0]=by.get(64+iteracion); bytes[1]=by.get(65+iteracion);
                buffer = ByteBuffer.wrap(bytes);
                buffer.order(ByteOrder.LITTLE_ENDIAN);  // if you want little-endian
                vertical.setVueltas80(new Integer(buffer.getShort()));
                
                bytes[0]=by.get(66+iteracion); bytes[1]=by.get(67+iteracion);
                buffer = ByteBuffer.wrap(bytes);
                buffer.order(ByteOrder.LITTLE_ENDIAN);  // if you want little-endian
                vertical.setVueltasSup(new Integer(buffer.getShort()));
                
                bytes[0]=by.get(68+iteracion); bytes[1]=by.get(69+iteracion);
                buffer = ByteBuffer.wrap(bytes);
                buffer.order(ByteOrder.LITTLE_ENDIAN);  // if you want little-endian
                vertical.setVueltasFondo(new Integer(buffer.getShort()));
                
                vertical.setVelocidad(new Double(ConvertirToFloat(70+iteracion)));
                vertical.setVelocidadSup(new Double(ConvertirToFloat(74+iteracion)));
                vertical.setVelocidadFondo(new Double(ConvertirToFloat(78+iteracion)));
                vertical.setVelocidad20(new Double(ConvertirToFloat(82+iteracion)));
                vertical.setVelocidad40(new Double(ConvertirToFloat(86+iteracion)));
                vertical.setVelocidad60(new Double(ConvertirToFloat(90+iteracion)));
                vertical.setVelocidad80(new Double(ConvertirToFloat(94+iteracion)));
                vertical.setProfundidad(new Double(ConvertirToFloat(98+iteracion)));
                vertical.setDistOrilla(new Double(ConvertirToFloat(102+iteracion)));
                
                if(verJPA.CrearVertical(vertical))
                {
                    window.txtLog.setText("Guardado con exito\n");
                }
                else 
                {
                    window.txtLog.setText("No se pudo guardar\n");
                }
        
                numVerticales--;
                
             }
             
             System.out.println("---*---");
             System.out.println("numero de verticales "+ verJPA.ListarVerticales(afoJPA.ListarAforos().get(afoJPA.ListarAforos().size()-1).getIdaforo()).size());
             
             int index = iteracion + 106;
             iteracion=0;

             celda.setIdaforo((Aforo)afoJPA.ListarAforos().get(afoJPA.ListarAforos().size()-1));
             
             
             while(numCeldas>0)
             {
                celda.setAncho(new Double(ConvertirToFloat(index+iteracion)));
                celda.setProfundidad(new Double(ConvertirToFloat(index+4+iteracion)));
                celda.setArea(new Double(ConvertirToFloat(index+8+iteracion)));
                celda.setVelocidad(new Double(ConvertirToFloat(index+12+iteracion)));
                celda.setCaudal(new Double(ConvertirToFloat(index+16+iteracion)));
                
                if(celJPA.CrearCelda(celda))
                {
                    window.txtLog.setText("Guardado con exito\n");
                }
                else 
                {
                    window.txtLog.setText("No se pudo guardar\n");
                }
        
                numCeldas--;
                iteracion=iteracion+20;
             }
             
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, ex);
            window.txtLog.setForeground(Color.red);
            window.txtLog.setText("Faltan Datos, Vuelva a enviar desde el Madec\n");
        }
        
        
    }
    
    public float ConvertirToFloat(int index) 
    {
        byte[] bytes = new byte[4];
        for(int i =0; i<4;i++) 
        {
            bytes[i] = by.get(index + i);
        }
        
        float f = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
        return f;
        
    }
    //method that can be called to send data
    //pre: open serial port
    //post: data sent to the other device
    public void writeData(int leftThrottle)
    {
        try
        {
            output.write(leftThrottle);
            output.flush();
            //this is a delimiter for the data
            //output.write(DASH_ASCII);
            //output.flush();
            
            //output.write(rightThrottle);
            //output.flush();
            //will be read as a byte so it is a space key
            //output.write(SPACE_ASCII);
            //output.flush();
        }
        catch (Exception e)
        {
            logText = "Failed to write data. (" + e.toString() + ")";
            window.txtLog.setForeground(Color.red);
            window.txtLog.setText(logText + "\n");
        }
    }
    
}


