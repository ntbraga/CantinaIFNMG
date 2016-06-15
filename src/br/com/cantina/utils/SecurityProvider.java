/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cantina.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author ntbra
 */
public class SecurityProvider {
    public static PublicKey getPublicKey(){
        PublicKey publicKey = null;
        
        try(ObjectInputStream oIn =
                new ObjectInputStream(
                SecurityProvider.class
                .getResourceAsStream("/br/com/cantina/utils/security/public.key"))){
            
            publicKey = (PublicKey)oIn.readObject();
            
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SecurityProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return publicKey;
    }
    
    public static PrivateKey getPrivateKey(){
        PrivateKey privateKey = null;
        
        try(ObjectInputStream oIn =
                new ObjectInputStream(
                SecurityProvider.class
                .getResourceAsStream("/br/com/cantina/utils/security/private.key"))){
            
            privateKey = (PrivateKey)oIn.readObject();
            
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SecurityProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return privateKey;
    }
    
    
    public static SecretKey getKey(){
        SecretKey key = null;
        
        try(ObjectInputStream oIn =
                new ObjectInputStream(
                SecurityProvider.class
                .getResourceAsStream("/br/com/cantina/utils/security/key.key"))){
            
            key = (SecretKey)oIn.readObject();
            
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SecurityProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return key;
    }
    
    public static void main(String[] args){
        try {
            
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
            keyGen.initialize(1024);
            KeyPair pair = keyGen.genKeyPair();
            
            FileOutputStream outPub = new FileOutputStream("/teste/public.key");
            FileOutputStream outPriv = new FileOutputStream("/teste/private.key");
            
            FileOutputStream outKey = new FileOutputStream("/teste/key.key");
            
            ObjectOutputStream out = new ObjectOutputStream(outPub);
            out.writeObject(pair.getPublic());
            out.close();
            
            out = new ObjectOutputStream(outPriv);
            out.writeObject(pair.getPrivate());
            out.close();
            
            
            KeyGenerator gen = KeyGenerator.getInstance("DES");
            
            out = new ObjectOutputStream(outKey);
            out.writeObject(gen.generateKey());
            out.close();
        } catch (NoSuchAlgorithmException | IOException ex) {
            Logger.getLogger(SecurityProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
