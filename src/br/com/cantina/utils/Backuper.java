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
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ntbra
 */
public class Backuper implements Serializable{
    private final Date date;
    
    private Backup<Caixa> bCaixa;
    private Backup<Cliente> bCliente;
    private Backup<Endereco> bEndereco;
    private Backup<Login> bLogin;
    private Backup<Transacao> bTransacao;
    private byte[] backup;
    
    public Backuper() {
        this.date = new Date();
    }

    public void setBackup(byte[] backup) {
        this.backup = backup;
    }

    public byte[] getBackup() {
        return backup;
    }
    
    public Date getDate() {
        return date;
    }
     
    public Backup<Caixa> getbCaixa() {
        if(bCaixa == null)
            bCaixa = new Backup<>(getCaixas());
        return bCaixa;
    }

    public Backup<Cliente> getbCliente() {
        if(bCliente == null)
            bCliente = new Backup<>(getClientes());
        return bCliente;
    }

    public Backup<Endereco> getbEndereco() {
        if(bEndereco == null)
            bEndereco = new Backup<>(getEnderecos());
        return bEndereco;
    }

    public Backup<Login> getbLogin() {
        if(bLogin == null)
            bLogin = new Backup<>(getLogins());
        return bLogin;
    }

    public Backup<Transacao> getbTransacao() {
        if(bTransacao == null)
            bTransacao = new Backup<>(getTransacoes());
        return bTransacao;
    }
    
    public static List<Caixa> getCaixas() {
        return ControllerProvider.getCaixaJpaController().findCaixaEntities();
    }

    public static List<Cliente> getClientes() {
        return ControllerProvider.getClienteJpaController().findClienteEntities();
    }

    public static List<Endereco> getEnderecos() {
        return ControllerProvider.getEnderecoJpaController().findEnderecoEntities();
    }

    public static List<Login> getLogins() {
        return ControllerProvider.getLoginJpaController().findLoginEntities();
    }

    public static List<Transacao> getTransacoes() {
        return ControllerProvider.getTransacaoJpaController().findTransacaoEntities();
    }
}
