/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cantina.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author ntbra
 */
public class Backup<T> implements Serializable{
    public List<T> list;

    public Backup(List<T> object) {
        this.list = object;
    }
    
    public String getHash(){
        byte b[] = null;
        try(ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bout)){
            out.writeObject(this);
            bout.flush();
            b = bout.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(Backup.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(b != null)
            return DigestUtils.md5Hex(b);
        return null;
    }
}
