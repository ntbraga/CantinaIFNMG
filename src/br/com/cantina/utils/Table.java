/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cantina.utils;


import java.awt.Component;
import java.util.List;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ntBraga
 */
public class Table extends JTable{
    private DefaultTableModel model;
    public Table(String columns[]) {
        super.getTableHeader().setReorderingAllowed(false);
        model = new DefaultTableModel(columns, 0);
        super.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        super.setModel(model);
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public void setModel(TableModel dataModel) {
        
    }
    
    public DefaultTableModel getDefaultTableModel(){
        return model;
    }

    public void clearAll(){
        this.model.getDataVector().removeAllElements();
        this.model.fireTableDataChanged();
    }
    
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component component = super.prepareRenderer(renderer, row, column);
        int rendererWidth = component.getPreferredSize().width;
        TableColumn tableColumn = getColumnModel().getColumn(column);
        tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
        return component;
     }
    
}
