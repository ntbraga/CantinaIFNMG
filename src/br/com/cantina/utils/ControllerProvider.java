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

    public static CaixaJpaController getCaixaJpaController() {
        return new CaixaJpaController(getEntityManagerFactory());
    }

    public static ClienteJpaController getClienteJpaController() {
        return new ClienteJpaController(getEntityManagerFactory());
    }

    public static EnderecoJpaController getEnderecoJpaController() {
        return new EnderecoJpaController(getEntityManagerFactory());
    }

    public static LoginJpaController getLoginJpaController() {
        return new LoginJpaController(getEntityManagerFactory());
    }

    public static TransacaoJpaController getTransacaoJpaController() {
        return new TransacaoJpaController(getEntityManagerFactory());
    }
}
