/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cantina.utils;

import br.com.cantina.modeldb.controller.CaixaJpaController;
import br.com.cantina.modeldb.controller.ClienteJpaController;
import br.com.cantina.modeldb.controller.EnderecoJpaController;
import br.com.cantina.modeldb.controller.LoginJpaController;
import br.com.cantina.modeldb.controller.TransacaoJpaController;
import static br.com.cantina.utils.Utils.getEntityManagerFactory;

/**
 *
 * @author Taty Braga
 */
public class ControllerProvider {    
    private static CaixaJpaController caixaJpaController;
    private static ClienteJpaController clienteJpaController;
    private static EnderecoJpaController enderecoJpaController;
    private static LoginJpaController loginJpaController;
    private static TransacaoJpaController transacaoJpaController;

    public static CaixaJpaController getCaixaJpaController() {
        if(caixaJpaController == null) caixaJpaController
                = new CaixaJpaController(getEntityManagerFactory());
        return caixaJpaController;
    }

    public static ClienteJpaController getClienteJpaController() {
        if(clienteJpaController == null) clienteJpaController
                = new ClienteJpaController(getEntityManagerFactory());
        return clienteJpaController;
    }

    public static EnderecoJpaController getEnderecoJpaController() {
        if(enderecoJpaController == null) enderecoJpaController
                = new EnderecoJpaController(getEntityManagerFactory());
        return enderecoJpaController;
    }

    public static LoginJpaController getLoginJpaController() {
        if(loginJpaController == null) loginJpaController
                = new LoginJpaController(getEntityManagerFactory());
        return loginJpaController;
    }

    public static TransacaoJpaController getTransacaoJpaController() {
        if(transacaoJpaController == null) transacaoJpaController
                = new TransacaoJpaController(getEntityManagerFactory());
        return transacaoJpaController;
    }
}
