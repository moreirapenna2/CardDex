/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdconnection;

import static java.sql.JDBCType.NULL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author 2019.1.08.041
 */
public class Lists_Cards {
    
    private int id;
    private int list_id;
    private int card_id;
    private int quantidade;
    Lists lista = null;
    
    public Lists_Cards(int id, int list_id, int card_id, int quantidade) {
        this.id = id;
        this.list_id = list_id;
        this.card_id = card_id;
        this.quantidade = quantidade;
    }
    
    public Lists_Cards(int list_id, int card_id, int quantidade) {
        this.list_id = list_id;
        this.card_id = card_id;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public int getList_id() {
        return list_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    
    /**
     * Função para inserir na tabela lista_carta
     * 
     * Essa função realiza uma consulta em listas para identificar o tipo de lista que esta sendo solicitada,
     * após a verificação faz o calculo para o deck para manter o limite de 60 cartas por deck
     * e se for válido faz a inserçãono bd
     *
     * @param lists_Cards é um objeto de Lists_Cards
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
     public void insertListCard(Lists_Cards lists_Cards) throws ClassNotFoundException, SQLException {
        BDConnection dbConnection = new BDConnection();
        
        
        /*dbConnection.stmt1.execute("Insert into cartas_lista (id_lista, id_carta, quantidade) "
                + "VALUES(" + this.list_id + ", " + this.card_id + ", " + this.quantidade + ");");
        */
        
        int quantidade = 0;
        
        boolean isDeck = false;
        
        List<Lists> lists = new Lists().searchBy(String.valueOf(lists_Cards.list_id));
        
        if(lists.get(0).getTipo().equals("deck")){
            isDeck=true;
            List<Lists_Cards> lista_card = this.getByListaId(lists_Cards.id);
            
            if(lista_card != null){
                for (Lists_Cards lists_Cards1 : lista_card) {
                    quantidade += lists_Cards1.getQuantidade();
                }
            }
            
        }
        
        if(isDeck == false || (isDeck == true && quantidade < 60) ){
        dbConnection.stmt1.execute("Insert into cartas_lista (id_lista, id_carta, quantidade) "
                + "VALUES(" + lists_Cards.list_id + ", " + lists_Cards.card_id + ", " + lists_Cards.quantidade + ");");
        }else{
            //Mensagem de erro
        }
        dbConnection.conn1.close();
    }

     /**
     * Função para remoção na tabela lista_carta
     * 
     *
     * @param id do objeto a ser deletado
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public void deleteListCard(int id) throws ClassNotFoundException, SQLException {
        BDConnection dbConnection = new BDConnection();

        dbConnection.stmt1.execute("Delete from lista_carta where id = " + id + ";");

        dbConnection.conn1.close();
    }
    
    /**
     * Função para busca na tabela lista_carta
     * 
     * Essa função realiza uma busca em listas_cartas linkando todos os possíveis dados.
     * Ex: Search = "1", ira retornar todos elementos que os ids, lista_id, carta_id e quantidade contenham 1
     *
     * @param search uma string sem tratamento
     * @return Uma lista de Lists_Cards
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public List<Lists_Cards> searchBy(String search) throws SQLException, ClassNotFoundException {
        BDConnection dbConnection = new BDConnection();
        ResultSet rs1 = dbConnection.stmt1.executeQuery("SELECT * FROM lista_carta WHERE id LIKE \"%" + search + "%\""
                                                        + " OR id_lista LIKE \"%" + search + "%\""
                                                        + " OR id_carta LIKE \"%" + search + "%\""
                                                        + " OR quantidade LIKE \"%" + search +"%\";");

        List<Lists_Cards> listas = null;

        while (rs1 != null && rs1.next()) {
            Lists_Cards lista_Cards = new Lists_Cards(rs1.getInt("id"), rs1.getInt("id_lista"),
                    rs1.getInt("id_carta"), rs1.getInt("quantidade"));
            
            listas.add(lista_Cards);
        }
        
        dbConnection.conn1.close();
        return listas;
    }

    /**
     * Função para busca na tabela lista_carta pelo lista_id
     * 
     * 
     * @param id da lista procurada
     * @return Uma lista de Lists_Cards
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public List<Lists_Cards> getByListaId(int id) throws SQLException, ClassNotFoundException {
        BDConnection dbConnection = new BDConnection();
        ResultSet rs1 = dbConnection.stmt1.executeQuery("SELECT * FROM cartas_lista WHERE id_lista = \"%" + id + "%\";");

        List<Lists_Cards> listas = null;

        while (rs1 != null && rs1.next()) {
            Lists_Cards lista_Cards = new Lists_Cards(rs1.getInt("id"), rs1.getInt("id_lista"),
                    rs1.getInt("id_carta"), rs1.getInt("quantidade"));
            
            listas.add(lista_Cards);
        }
        
        dbConnection.conn1.close();
        return listas;
    }
}
