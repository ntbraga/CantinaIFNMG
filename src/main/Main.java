/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import br.com.cantina.modeldb.Login;
import br.com.cantina.modeldb.controller.LoginJpaController;
import br.com.cantina.telas.LoginFrame;
import br.com.cantina.telas.Splash;
import br.com.cantina.telas.admin.RootAdminFrame;
import br.com.cantina.utils.AsyncTask;
import br.com.cantina.utils.Utils;
import br.com.cantina.utils.ControllerProvider;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.TypedQuery;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author ntBraga
 */
public class Main {
   
    public static void dbCheck(){
        if(!Utils.getDbPathAsFile().exists()){
            Utils.getEntityManagerFactory().createEntityManager();
        }
        
        if(Utils.getDbPathAsFile().exists()){
            LoginJpaController controller = ControllerProvider.getLoginJpaController();
            if(controller.findLoginEntities().isEmpty()){
                Login login = new Login();
                login.setLogin("admin");
                login.setSenha("admin");
                Login superUser = new Login();
                superUser.setLogin("root");
                superUser.setSenha("rootadmindb");
                try {
                    controller.create(superUser);
                    controller.create(login);
                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("\nUSUÁRIO PADRÃO CRIADO\n\tLogin: admin\n\tSenha: admin\n");
            }
        }
    }
    
    private static void showHelpMenu(){
        System.out.println("\n\tHELP MENU");
        System.out.println("\t\thelp ou ?: exibe ajuda");
        System.out.println("\t\t-drop database: exclui a database do programa\n\t\t\tCuidado, esta ação não pode ser desfeita!");
        System.out.println("\t\t-remake database: recria a database do programa\n\t\t\tCuidado, esta ação não pode ser desfeita!");
        System.out.println("\t\t-login root [senha]: realiza login com superuser root");
        System.out.println("\t----------\n");
    }
    
    private static boolean argsCheck(String args[]){
        if(args.length == 1){
            if(args[0].equals("help") || args[0].equals("?")){
                showHelpMenu();
                return false;
            }
        }
        
        if(args.length == 2){
            if(args[0].equals("-drop") && args[1].equals("database")){
                if(loginContinue()){
                    Utils.getDbPathAsFile().delete();
                    System.out.println("DATABASE DELETADA COM SUCESSO!");
                }else System.out.println("Usuário e/ou senha incorretos.");
                return false;
            }
            
            if(args[0].equals("-remake") && args[1].equals("database")){
                if(loginContinue()){
                    Utils.getDbPathAsFile().delete();
                    System.out.println("DATABASE DELETADA COM SUCESSO!");
                    if(!Utils.getDbPathAsFile().exists()){
                        Utils.getEntityManagerFactory().createEntityManager();
                        System.out.println("DATABASE RECRIADA COM SUCESSO!");
                    }
                }else System.out.println("Usuário e/ou senha incorretos.");
                return false;
            }
        }
        
        if(args.length == 3){
            if(args[0].equals("-login") && args[1].equals("root") && !args[2].isEmpty()){
                Login login = new Login();
                login.setLogin(args[1]);
                login.setSenha(args[2]);
                new RootAdminFrame(login).setVisible(true);
                return false;
            }
        }
        return true;
    }
    
    public static void main(String args[]) {
        System.setProperty("derby.system.home", Utils.getDbPath());
        System.setProperty("derby.language.sequence.preallocator", "1");
        if(argsCheck(args)){
            new AsyncTask<Void, String, Void>(){
                private Splash splash;
                private LoginFrame frame;
                @Override
                protected void onPreExecute() {
                    splash = new Splash();
                    splash.setVisible(true);
                }

                @Override
                protected Void doInBackground(Void... params) {
                    publishProgress("Conectando ao banco de dados...");
                    dbCheck();
                    /* Set the Nimbus look and feel */
                    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
                    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
                     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
                     */
                    try {
                        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                            if ("Windows".equals(info.getName())) {
                                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                                break;
                            }
                        }
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //</editor-fold>
                    publishProgress("Iniciando...");
                    //</editor-fold>
                    frame = new LoginFrame();
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    frame.setVisible(true);
                    splash.setVisible(false);
                    splash.dispose();
                }

                @Override
                protected void onProgressUpdate(String... values) {
                    if(splash != null){
                        if(values.length == 1){
                            splash.doUpdate(values[0]);
                        }
                    }
                }
            }.start();
        }
    }
    
    public static boolean loginContinue(){
        System.out.println("É necessário ter permição para executar a ação!\n");
        Scanner sca = new Scanner(System.in);
        System.out.print("Digite o usuário: ");
        String user = sca.nextLine();
        System.out.print("Digite a senha: ");
        String senha = sca.nextLine();
        LoginJpaController controller = ControllerProvider.getLoginJpaController();
        TypedQuery<Login> query = controller.getEntityManager().createNamedQuery("Login.doLogin", Login.class);
        query.setParameter("login", user);
        query.setParameter("senha", Utils.getMd5String(senha));
        query.setMaxResults(1);
        boolean res = !query.getResultList().isEmpty();
        controller.getEntityManager().close();
        return res;
    }
    
}
