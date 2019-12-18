package bdconnection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * instalar e configurar servidor mysql 8.0 corretamente (nome = root, senha =
 * root, schema name = carddex) 
 * Instalar o Connector/J 8.0 
 * Encontrar a pasta do Connector/J (C:\Program Files (x86)\MySQL\Connector J 8.0) 
 * Botão direito no projeto no netbeans -> propriedades -> bibliotecas -> adicionar JAR 
 * Adicionar o Connector/J 
 * Adicionar cartas ao BD (só copiar os códigos e executar UMA VEZ: 
 * Insert into lista (nome, tipo, limite) values ("Lista 1", "deck", 60);
 * Insert into lista (nome, tipo) values ("Lista 2", "pasta"); 
 * Insert into carta (nome, tipo, colecao, energia, ps, descricao, recuo, fraqueza) values ("Reshiram e Charizard GX", "Pokemon", "20/214", "Fogo", 270, "Ultraje - 30\nGolpe de Chamas - 230\n", 3, "Agua");
 * Insert into carta (nome, tipo, colecao, energia, ps, descricao, recuo, fraqueza) values ("Volcanion", "Pokemon", "25/214", "Fogo", 120, "Atear Fogo\n Raio de Calor Intenso - 50+\n", 2,"Agua");
 * Insert into carta(nome, tipo, colecao, descricao) values("Desbravamento da Green", "Apoiador", "175/214", "Você só pode jogar estacarta se não tiver Pokémon com Habilidades em jogo.\n\nProcure por até 2cartas de Treinador no seu baralho, revele-as e coloque-as na sua mão. Emseguida, embaralhe seu baralho"); 
 * Insert into carta(nome, tipo, colecao) values ("Energia de Fogo", "Energia", "2019/1");
 * Insert into carta(nome,tipo, colecao, descricao) values ("Mistura de Ervas", "Item", "184/214","Remova 1 condição especial do seu pokémon ativo");
 * Insert into carta(nome,tipo, colecao, descricao) values ("Santuário da Punição", "Estadio","143/168", "Entre as vezes de jogar, coloque 1 contador de dano em cada pokémon GX e EX");
 * Insert into carta(nome, tipo, colecao, energia) values ("Energia de Agua", "Energia", "2019/1.1", "Agua");
 * Insert into carta(nome, tipo, colecao, energia) values ("Energia de Planta", "Energia", "2019/1.3", "Planta");
 * Insert into carta(nome, tipo, colecao, energia) values ("Energia Eletrica", "Energia", "2019/1.5", "Eletrica");
 * Insert into carta(nome, tipo, colecao, energia) values ("Energia Psiquica", "Energia", "2019/1.6", "Psiquica");
 * Insert into carta(nome, tipo, colecao, energia) values ("Energia de Luta", "Energia", "2019/1.7", "Luta");
 * Insert into carta(nome, tipo, colecao, energia) values ("Energia Noturna", "Energia", "2019/1.8", "Noturna");
 * Insert into carta(nome, tipo, colecao, energia) values ("Energia de Metal", "Energia", "2019/1.9", "Metal");
 * Insert into carta(nome, tipo, colecao, energia) values ("Energia de Fada", "Energia", "2019/1.2", "Fada");
 **/
public class BDConnection {

    Connection conn1;
    Statement stmt1;
    ResultSet rs1;

    public BDConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        // cria a conexao com o bd
        conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/carddex?useTimezone=true&serverTimezone=UTC", "root", "root");
        //cria o objeto de query
        stmt1 = conn1.createStatement();
    }

    public ResultSet searchBy(String search) throws SQLException {
        //cria o query de busca
        rs1 = stmt1.executeQuery("SELECT * FROM carta WHERE id LIKE \"%" + search + "%\""
                + " OR nome LIKE \"%" + search + "%\""
                + " OR tipo LIKE \"%" + search + "%\""
                + " OR colecao LIKE \"%" + search + "%\""
                + " OR energia LIKE \"%" + search + "%\""
                + " OR ps LIKE \"%" + search + "%\""
                + " OR descricao LIKE \"%" + search + "%\""
                + " OR recuo LIKE \"%" + search + "%\""
                + " OR fraqueza LIKE \"%" + search + "%\";");
        //displayResult(rs1);
        return rs1;
    }

    public String getFormatedResult(String search) throws SQLException {
        //faz a pesquisa
        rs1 = searchBy(search);
        String res = "";

        //formata o resultado
        while (rs1 != null && rs1.next()) {
            res = res.concat("id: " + rs1.getInt("id"));
            res = res.concat("\nnome: " + rs1.getString("nome"));
            res = res.concat("\ntipo: " + rs1.getString("tipo"));
            res = res.concat("\ncolecao: " + rs1.getString("colecao"));
            res = res.concat("\nenergia: " + rs1.getString("energia"));
            res = res.concat("\nps: " + rs1.getInt("ps"));
            res = res.concat("\ndescricao: " + rs1.getString("descricao"));
            res = res.concat("\nrecuo: " + rs1.getInt("recuo"));
            res = res.concat("\nfraqueza: " + rs1.getString("fraqueza"));
            res = res.concat("\n\n");
        }
        //retorna o resultado formatado
        return res;
    }

    public void displayResult(ResultSet rs1) throws SQLException {
        //para cada resultado em rs1
        while (rs1.next()) {
            //pegar o valor do tipo int da coluna id
            System.out.println("id: " + rs1.getInt("id"));
            //pegar o valor do tipo string da coluna nome
            System.out.println("nome: " + rs1.getString("nome"));
            //....
            System.out.println("tipo: " + rs1.getString("tipo"));
            System.out.println("colecao: " + rs1.getString("colecao"));
            System.out.println("energia: " + rs1.getString("energia"));
            System.out.println("ps: " + rs1.getInt("ps"));
            System.out.println("descricao: " + rs1.getString("descricao"));
            System.out.println("recuo: " + rs1.getInt("recuo"));
            System.out.println("fraqueza: " + rs1.getString("fraqueza"));
            System.out.println("\n\n");
        }
    }
    
    public void addDeck(String name, String tipo) throws SQLException{
        //se o tipo for deck
        if(tipo.equals("deck")){
            //adiciona o deck com o nome passado e limite de 60 cartas
            stmt1.execute("insert into lista (nome, tipo, limite) values (\""+name+"\", \"deck\", 60)");
        }else if(tipo.equals("pasta")){
            //se for pasta adiciona sem limite
            stmt1.execute("insert into lista (nome, tipo) values (\""+name+"\", \"pasta\")");
        }
    }
    
    public ResultSet checkLists() throws SQLException{
        ResultSet rs = stmt1.executeQuery("select * from lista;");
        return rs;
    }
    
    /**
     * So pode ser usada se tiver a ctz que a query nao ira quebrar o DB!!!!!
     *
     * @param query Query executa sem nenhum tratamento
     */
    public void runQuery(String query) throws SQLException {
        stmt1.execute(query);
    }


}
