/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cantina.utils;

import br.com.cantina.modeldb.Caixa;
import br.com.cantina.modeldb.Cliente;
import br.com.cantina.modeldb.Endereco;
import br.com.cantina.modeldb.Login;
import br.com.cantina.modeldb.Transacao;
import br.com.cantina.modeldb.controller.ClienteJpaController;
import br.com.cantina.modeldb.controller.TransacaoJpaController;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import main.Main;

/**
 *
 * @author ntBraga
 */
public class Utils {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public static final SimpleDateFormat FILE_DATE_FORMAT = new SimpleDateFormat("ddMMyyyy");
    public static final NumberFormat FORMATTER = NumberFormat.getCurrencyInstance();
    private static EntityManagerFactory factory;
    public static final String LOCATION = "";
    public static String EXEC_PATH;
    static{
        try {
            EXEC_PATH = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static EntityManagerFactory getEntityManagerFactory(){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory("CantinaIFNMGPU");
        return factory;
    }
    
    public static void CloseEntityManagerFactory(){
        if(factory != null && factory.isOpen()){
            factory.close();
            factory = null;
        }
    }
    
    public static String getMd5String(String t){
        try {
            MessageDigest m= MessageDigest.getInstance("MD5");
            m.update(t.getBytes(),0,t.length());
            return new BigInteger(1,m.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String getDbPath(){
        new File(EXEC_PATH+File.separator+"DB").mkdirs();
        return EXEC_PATH+File.separator+"DB";
    }
    
    public static String getBackupPath(){
        new File(EXEC_PATH+File.separator+"backup").mkdirs();
        return EXEC_PATH+File.separator+"backup";
    }
    
    public static File getDbPathAsFile(){
        return new File(getDbPath());
    }
    
    public static void registerEcape(JDialog dialog){
        dialog.getRootPane().registerKeyboardAction(e -> {
            dialog.dispose();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
    
    public static String[] getTransacaoAsRow(Transacao transacao, ClienteJpaController controller){
        Cliente cliente = controller.findCliente(transacao.getIdcliente());
        return new String[]{cliente == null?"-":cliente.getNome(), FORMATTER.format(transacao.getValue()), DATE_TIME_FORMAT.format(transacao.getTimestamp())};
    }
    
    public static String[] getTransacaoAsRow(Transacao transacao){
        return getTransacaoAsRow(transacao, ControllerProvider.getClienteJpaController());
    }
    
    public static String[] getTransacaoAVistaAsRow(Transacao transacao){
        return new String[]{FORMATTER.format(transacao.getValue()), DATE_TIME_FORMAT.format(transacao.getTimestamp())};
    }
    
    public static double getSumDept(Cliente c, TransacaoJpaController controller){
        List<Transacao> transactions;
        TypedQuery<Transacao> query = controller.getEntityManager().createNamedQuery("Transacao.findVendasByIdcliente", Transacao.class);
        query.setParameter("idcliente", c.getIdcliente());
        transactions = query.getResultList();
        double value = 0;
        value = transactions.stream()
                .map((transacao) -> transacao.getValue())
                .reduce(value, (accumulator, _item) -> accumulator + _item);
        query = controller.getEntityManager().createNamedQuery("Transacao.findPayByIdcliente", Transacao.class);
        query.setParameter("idcliente", c.getIdcliente());
        transactions = query.getResultList();
        for(Transacao transacao: transactions){
            value-=transacao.getValue();
        }
        return value;
    }
    
    public static String getSumDeptAsString(Cliente c, TransacaoJpaController controller){
        return FORMATTER.format(getSumDept(c, controller));
    }
    
    public static String getSumAllTransactions(List<Transacao> transactions){
        double value = 0;
        
        value = transactions.stream().map((t) -> t.getValue()).reduce(value, (accumulator, _item) -> accumulator + _item);
        return FORMATTER.format(value);
    }
    
    public static String getTransacaoSum(List<Transacao> transactions){
        double value = 0;
        
        for(Transacao t: transactions){
            switch(t.getType()){
                case "venda":{
                    value+=t.getValue();
                    break;
                }
                
                case "pay":{
                    value-=t.getValue();
                    break;
                }
            }
        }
        return FORMATTER.format(value);
    }    
    
    public static BufferedImage colorImage(BufferedImage image, Color target) {
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();
        int[] pixelColor = {target.getRed(), target.getGreen(), target.getBlue(), 0};
        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                pixelColor[3] = raster.getPixel(xx, yy, (int[]) null)[3];
                raster.setPixel(xx, yy, pixelColor);
            }
        }
        return image;
    }
    
    public static List<Image> getIcons(){
        String ico = "logo0%d.png";
        List<Image> icons = new ArrayList<>();
        for(int i = 1; i<=4; i++){
            icons.add(new ImageIcon(Utils.class
                    .getResource("/br/com/cantina/icons/logo/"+String.format(ico, i)))
                    .getImage());
        }
        return icons;
    }
    
    public static String getBackupFileName(){
        return "backup-"+FILE_DATE_FORMAT.format(new Date())+".bck";
    }
    
    private static EntityManager getManager(){
        return getEntityManagerFactory()
                .createEntityManager();
    }
    
    public static File pack(String sourceDirPath, String zipFilePath) throws IOException {
        Path p = Files.createFile(Paths.get(zipFilePath));
        AtomicInteger i = new AtomicInteger(0);
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
            Path pp = Paths.get(sourceDirPath);
            Files.walk(pp)
              .filter(path -> !Files.isDirectory(path))
              .forEach(path -> {
                  String sp = path.toAbsolutePath().toString().replace(pp.toAbsolutePath().toString(), "").replace(path.getFileName().toString(), "");
                  ZipEntry zipEntry = new ZipEntry(sp + "/" + path.getFileName().toString());
                  try {
                      zs.putNextEntry(zipEntry);
                      zs.write(Files.readAllBytes(path));
                      zs.closeEntry();
                      i.addAndGet(1);
                } catch (IOException e) {
                    System.err.println(e.getMessage()+"<<<<<<<<<<<<<<");
                }
              });
        }
        return p.toFile();
    }
    
    public static boolean deletePath(File dir){
        try {
            Files.walkFileTree(dir.toPath(), new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.deleteIfExists(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.deleteIfExists(dir);
                    return FileVisitResult.CONTINUE;
                }                
            });
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
}
