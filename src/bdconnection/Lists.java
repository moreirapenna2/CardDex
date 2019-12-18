/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdconnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author 2019.1.08.041
 */
public class Lists {

    private int id;
    private int limite;
    private String nome;
    private String tipo;

    public Lists(){
    }
    
    public Lists(int id, String nome, String tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        if(tipo.equals("deck"))
            this.limite=60;
    }
    
    public Lists(String nome, String tipo) throws SQLException, ClassNotFoundException {
        this.nome = nome;
        this.tipo = tipo;
        this.id = getListId();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the limite
     */
    public int getLimite() {
        return limite;
    }

    /**
     * @param limite the limite to set
     */
    public void setLimite(int limite) {
        this.limite = limite;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
        if(tipo.equals("deck"))
            this.limite = 60;
    }

    /**
     * Função para inserção na tabela lista
     * 
     *
     * @param lista é um objeto Lists
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public void insertList(Lists lista) throws SQLException, ClassNotFoundException {
        BDConnection dbConnection = new BDConnection();

        if (lista.limite > 0) {
            dbConnection.stmt1.execute("Insert into lista (nome, tipo, limite) VALUES('" + lista.nome + "','" + lista.tipo + "'," + lista.limite + ");");
        } else {
            dbConnection.stmt1.execute("Insert into lista (nome, tipo) VALUES('" + lista.nome + "','" + lista.tipo + "';");
        }

        dbConnection.conn1.close();
    }

     /**
     * Função para deleção na tabela lista
     * 
     *
     * @param id do item a ser deletado
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public void deleteList(int id) throws ClassNotFoundException, SQLException {
        BDConnection dbConnection = new BDConnection();

        dbConnection.stmt1.execute("Delete from lista where id = " + id + ";");

        dbConnection.conn1.close();
    }
    
    /**
     * Função para atualizar um elemento na tabela lista
     * 
     *
     * @param lista é um objeto Lists
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public void updateList(Lists lista) throws ClassNotFoundException, SQLException {
        BDConnection dbConnection = new BDConnection();

        dbConnection.stmt1.execute("Update lista set nome = '" + lista.nome + "' ,tipo = '" + lista.tipo + "' ,limite =" + lista.limite + " ;");

        dbConnection.conn1.close();
    }
    
    /**
     * Função para busca na tabela lista
     * 
     * Essa função realiza uma busca em lista linkando todos os possíveis dados.
     * Ex: Search = "L", ira retornar todos elementos que o nome, tipo, ..., contenham L
     *
     * @param search uma string sem tratamento
     * @return Uma Lista de Lists
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public List<Lists> searchBy(String search) throws SQLException, ClassNotFoundException {
        BDConnection dbConnection = new BDConnection();
        ResultSet rs1 = dbConnection.stmt1.executeQuery("SELECT * FROM lista WHERE id LIKE \"%" + search + "%\""
                                                        + " OR nome LIKE \"%" + search + "%\""
                                                        + " OR tipo LIKE \"%" + search + "%\""
                                                        + " OR limite LIKE \"%" + search +"%\";");

        List<Lists> listas = new ArrayList<>();

        while (rs1 != null && rs1.next()) {
            Lists lista = new Lists(rs1.getInt("id"),
                    rs1.getString("nome"), rs1.getString("tipo"));
            
            listas.add(lista);
        }
        
        dbConnection.conn1.close();
        return listas;
    }
    
    /**
     * Função para busca na tabela lista todos os elementos
     * 
     * 
     * @return Uma Lista de Lists
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public List<Lists> getAll() throws SQLException, ClassNotFoundException {
        BDConnection dbConnection = new BDConnection();
        ResultSet rs1 = dbConnection.stmt1.executeQuery("SELECT * FROM lista WHERE 1 = 1;");

        List<Lists> listas = new ArrayList<>();

        while (rs1 != null && rs1.next()) {
            Lists lista = new Lists(rs1.getInt("id"),
                    rs1.getString("nome"), rs1.getString("tipo"));
            
            listas.add(lista);
        }
        
        dbConnection.conn1.close();
        return listas;
    }
    
    public List<Lists_Cards> getCartas() throws SQLException, ClassNotFoundException{
        BDConnection dbConnection = new BDConnection();
        ResultSet rs1 = dbConnection.stmt1.executeQuery("SELECT * FROM cartas_lista where id_lista="+this.id+";");

        List<Lists_Cards> cartas = new ArrayList<>();

        while (rs1 != null && rs1.next()) {
            Lists_Cards carta = new Lists_Cards(rs1.getInt("id_lista"),
                    rs1.getInt("id_carta"), rs1.getInt("quantidade"));
            
            cartas.add(carta);
        }
        
        dbConnection.conn1.close();
        return cartas;
    }
    
    public int getListId() throws SQLException, ClassNotFoundException{
        BDConnection dbConnection = new BDConnection();
        ResultSet rs1 = dbConnection.stmt1.executeQuery("SELECT id FROM lista WHERE nome = \""+this.nome+"\";");
        int newid=-1;
        while (rs1 != null && rs1.next()) {
            newid=rs1.getInt("id");
        }
        
        dbConnection.conn1.close();
        return newid;
    }
}
