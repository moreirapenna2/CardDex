/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdconnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author 2019.1.08.041
 */
public class Cards {

    private int id;
    private int ps;
    private int recuo;
    private String nome;
    private String tipo;
    private String colecao;
    private String energia;
    private String descricao;
    private String fraqueza;

    public Cards(int id, int ps, int recuo, String nome, String tipo, String colecao, String energia, String descricao, String fraqueza) {
        this.id = id;
        this.ps = ps;
        this.recuo = recuo;
        this.nome = nome;
        this.tipo = tipo;
        this.colecao = colecao;
        this.energia = energia;
        this.descricao = descricao;
        this.fraqueza = fraqueza;
    }
    
    public Cards(){
        
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
     * @return the ps
     */
    public int getPs() {
        return ps;
    }

    /**
     * @param ps the ps to set
     */
    public void setPs(int ps) {
        this.ps = ps;
    }

    /**
     * @return the recuo
     */
    public int getRecuo() {
        return recuo;
    }

    /**
     * @param recuo the recuo to set
     */
    public void setRecuo(int recuo) {
        this.recuo = recuo;
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
    }

    /**
     * @return the colecao
     */
    public String getColecao() {
        return colecao;
    }

    /**
     * @param colecao the colecao to set
     */
    public void setColecao(String colecao) {
        this.colecao = colecao;
    }

    /**
     * @return the energia
     */
    public String getEnergia() {
        return energia;
    }

    /**
     * @param energia the energia to set
     */
    public void setEnergia(String energia) {
        this.energia = energia;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the fraqueza
     */
    public String getFraqueza() {
        return fraqueza;
    }

    /**
     * @param fraqueza the fraqueza to set
     */
    public void setFraqueza(String fraqueza) {
        this.fraqueza = fraqueza;
    }

    /**
     * Função para inserção na tabela carta
     * 
     *
     * @param card é um objeto Card
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public void insertCard(Cards card) throws ClassNotFoundException, SQLException {
        BDConnection dbConnection = new BDConnection();

        dbConnection.stmt1.execute("Insert into carta (nome, tipo, colecao, energia, ps, descricao, recuo, fraqueza) "
                + "VALUES('" + card.nome + "', '" + card.tipo + "', '" + card.tipo + "', '" + card.colecao + "', '" + card.energia + "', " + card.ps + ", '" + card.descricao
                + "', " + card.recuo + ", '" + card.fraqueza + "');");

        dbConnection.conn1.close();
    }

    /**
     * Função para deleção na tabela carta
     * 
     *
     * @param id do item a ser deletado
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public void deleteCard(int id) throws ClassNotFoundException, SQLException {
        BDConnection dbConnection = new BDConnection();

        dbConnection.stmt1.execute("Delete from lista where id = " + id + ";");

        dbConnection.conn1.close();
    }

    /**
     * Função para busca na tabela carta
     * 
     * Essa função realiza uma busca em cartas linkando todos os possíveis dados.
     * Ex: Search = "L", ira retornar todos elementos que o nome, tipo, ..., contenham L
     *
     * @param search uma string sem tratamento
     * @return Uma lista de Cards
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public List<Cards> searchBy(String search) throws SQLException, ClassNotFoundException {
        BDConnection dbConnection = new BDConnection();
        ResultSet rs1 = dbConnection.stmt1.executeQuery("SELECT * FROM carta WHERE id LIKE \"%" + search + "%\""
                                                        + " OR nome LIKE \"%" + search + "%\""
                                                        + " OR tipo LIKE \"%" + search + "%\""
                                                        + " OR colecao LIKE \"%" + search + "%\""
                                                        + " OR energia LIKE \"%" + search + "%\""
                                                        + " OR ps LIKE \"%" + search + "%\""
                                                        + " OR descricao LIKE \"%" + search + "%\""
                                                        + " OR recuo LIKE \"%" + search + "%\""
                                                        + " OR fraqueza LIKE \"%" + search + "%\";");

        List<Cards> cards = null;

        while (rs1 != null && rs1.next()) {
            Cards card = new Cards(rs1.getInt("id"), rs1.getInt("ps"),
                    rs1.getInt("recuo"), rs1.getString("nome"),
                    rs1.getString("tipo"), rs1.getString("colecao"),
                    rs1.getString("energia"), rs1.getString("descricao"),
                    rs1.getString("fraqueza"));
            
            cards.add(card);
        }
        dbConnection.conn1.close();
        return cards;
    }
    
    /**
     * Função para busca na tabela carta todos os elementos
     * 
     * 
     * @return Uma lista de Cards
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public List<Cards> getAll() throws SQLException, ClassNotFoundException {
        BDConnection dbConnection = new BDConnection();
        ResultSet rs1 = dbConnection.stmt1.executeQuery("SELECT * FROM carta WHERE 1 = 1;");

        List<Cards> cards = null;

        while (rs1 != null && rs1.next()) {
            Cards card = new Cards(rs1.getInt("id"), rs1.getInt("ps"),
                    rs1.getInt("recuo"), rs1.getString("nome"),
                    rs1.getString("tipo"), rs1.getString("colecao"),
                    rs1.getString("energia"), rs1.getString("descricao"),
                    rs1.getString("fraqueza"));
            
            cards.add(card);
        }
        dbConnection.conn1.close();
        return cards;
    }
    
    public String getNomeByID(int id) throws SQLException, ClassNotFoundException{
        BDConnection dbConnection = new BDConnection();
        ResultSet rs1 = dbConnection.stmt1.executeQuery("SELECT nome FROM carta WHERE id = "+id+";");

        String tmp=null;
        while (rs1 != null && rs1.next()) {
            tmp = rs1.getString("nome");
        }
        dbConnection.conn1.close();
        return tmp;
    }
    
    public String getTipoByID(int id) throws SQLException, ClassNotFoundException{
        BDConnection dbConnection = new BDConnection();
        ResultSet rs1 = dbConnection.stmt1.executeQuery("SELECT tipo FROM carta WHERE id = "+id+";");

        String tmp=null;
        while (rs1 != null && rs1.next()) {
            tmp = rs1.getString("tipo");
        }
        dbConnection.conn1.close();
        return tmp;
    }
    
    public String getEnergiaByID(int id) throws SQLException, ClassNotFoundException{
        BDConnection dbConnection = new BDConnection();
        ResultSet rs1 = dbConnection.stmt1.executeQuery("SELECT energia FROM carta WHERE id = "+id+";");

        String tmp=null;
        while (rs1 != null && rs1.next()) {
            tmp = rs1.getString("energia");
        }
        dbConnection.conn1.close();
        return tmp;
    }
}
