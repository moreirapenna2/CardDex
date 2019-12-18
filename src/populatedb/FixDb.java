/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package populatedb;

import bdconnection.BDConnection;
import java.sql.SQLException;

/**
 *
 * @author 2019.1.08.041
 */
public class FixDb {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        BDConnection db = new BDConnection();

        db.runQuery("CREATE SCHEMA IF NOT EXISTS `carddex` DEFAULT CHARACTER SET utf8;");
        db.runQuery("USE carddex;");
        db.runQuery("CREATE TABLE IF NOT EXISTS `carddex`.`lista` (`id` INT NOT NULL AUTO_INCREMENT,`nome` VARCHAR(45) NOT NULL,`tipo` VARCHAR(10) NOT NULL,`limite` INT UNSIGNED NULL,PRIMARY KEY (`id`))ENGINE = InnoDB;");
        db.runQuery("CREATE TABLE IF NOT EXISTS `carddex`.`carta` (`id` INT NOT NULL AUTO_INCREMENT,`nome` VARCHAR(45) NOT NULL,`tipo` VARCHAR(45) NOT NULL,`colecao` VARCHAR(45) NOT NULL,`energia` VARCHAR(45) NULL,`ps` INT UNSIGNED NULL,`descricao` VARCHAR(200) NULL,`recuo` INT UNSIGNED NULL,`fraqueza` VARCHAR(45) NULL,PRIMARY KEY (`id`),UNIQUE INDEX `colecao_UNIQUE` (`colecao` ASC))ENGINE = InnoDB;");
        db.runQuery("CREATE TABLE IF NOT EXISTS `carddex`.`cartas_lista` (`id` INT NOT NULL AUTO_INCREMENT,`id_lista` INT NOT NULL,`id_carta` INT NOT NULL,`quantidade` INT NOT NULL,PRIMARY KEY (`id`))ENGINE = InnoDB;");
    
        db.runQuery("Insert into lista (nome, tipo, limite) values (\"Lista 1\", \"deck\", 60);");
        db.runQuery("Insert into lista (nome, tipo) values (\"Lista 2\", \"pasta\");");
        db.runQuery("Insert into carta (nome, tipo, colecao, energia, ps, descricao, recuo, fraqueza) values (\"Reshiram e Charizard GX\", \"Pokemon\", \"20/214\", \"Fogo\", 270, \"Ultraje - 30\\nGolpe de Chamas - 230\\n\", 3, \"Agua\");");
        db.runQuery("Insert into carta (nome, tipo, colecao, energia, ps, descricao, recuo, fraqueza) values (\"Volcanion\", \"Pokemon\", \"25/214\", \"Fogo\", 120, \"Atear Fogo\\n Raio de Calor Intenso - 50+\\n\", 2,\"Agua\");");
        db.runQuery("Insert into carta(nome, tipo, colecao, descricao) values(\"Desbravamento da Green\", \"Apoiador\", \"175/214\", \"Você só pode jogar estacarta se não tiver Pokémon com Habilidades em jogo.\\n\\nProcure por até 2cartas de Treinador no seu baralho, revele-as e coloque-as na sua mão. Emseguida, embaralhe seu baralho\"); ");
        db.runQuery("Insert into carta(nome, tipo, colecao) values (\"Energia de Fogo\", \"Energia\", \"2019/1\");");
        db.runQuery("Insert into carta(nome,tipo, colecao, descricao) values (\"Mistura de Ervas\", \"Item\", \"184/214\",\"Remova 1 condição especial do seu pokémon ativo\");");
        db.runQuery("Insert into carta(nome,tipo, colecao, descricao) values (\"Santuário da Punição\", \"Estadio\",\"143/168\", \"Entre as vezes de jogar, coloque 1 contador de dano em cada pokémon GX e EX\");");  
        db.runQuery("Insert into carta(nome, tipo, colecao, energia) values (\"Energia de Agua\", \"Energia\", \"2019/1.1\", \"Agua\");");
        db.runQuery("Insert into carta(nome, tipo, colecao, energia) values (\"Energia de Planta\", \"Energia\", \"2019/1.3\", \"Planta\");");
        db.runQuery("Insert into carta(nome, tipo, colecao, energia) values (\"Energia Eletrica\", \"Energia\", \"2019/1.5\", \"Eletrica\");");
        db.runQuery("Insert into carta(nome, tipo, colecao, energia) values (\"Energia Psiquica\", \"Energia\", \"2019/1.6\", \"Psiquica\");");
        db.runQuery("Insert into carta(nome, tipo, colecao, energia) values (\"Energia de Luta\", \"Energia\", \"2019/1.7\", \"Luta\");");
        db.runQuery("Insert into carta(nome, tipo, colecao, energia) values (\"Energia Noturna\", \"Energia\", \"2019/1.8\", \"Noturna\");");
        db.runQuery("Insert into carta(nome, tipo, colecao, energia) values (\"Energia de Metal\", \"Energia\", \"2019/1.9\", \"Metal\");");
        db.runQuery("Insert into carta(nome, tipo, colecao, energia) values (\"Energia de Fada\", \"Energia\", \"2019/1.2\", \"Fada\");");
    }
}