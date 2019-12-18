/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfacegrafica;

import bdconnection.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

/**
 *
 * @author User
 */
public class TelaEditaLista extends javax.swing.JDialog {

    // variavel para alterar o eixo x a cada inserção
    int pos = 6;
    static int idLista;
    List<JLabel> labelsI = new ArrayList();

    List<JLabel> labelsS = new ArrayList();

    HashMap<String, Integer> cardsMap;

    static Lists lista;
    static ArrayList<Lists_Cards> cards;
    String lastPesquisa;
    Cards aux = new Cards();
    BDConnection bd;

    /**
     * Creates new form TelaEditaLista
     *
     * @param parent
     * @param modal
     */
    public TelaEditaLista(java.awt.Frame parent, boolean modal, Lists lista) throws ClassNotFoundException, SQLException {
        super(parent, modal);
        initComponents();
        this.lista = lista;
        cards = new ArrayList<>();
        inferior.setLayout(new FlowLayout(FlowLayout.LEFT));
        superior.setLayout(new FlowLayout(FlowLayout.LEFT));
        bd = new BDConnection();
        cardsMap = new HashMap<>();
        cards = (ArrayList<Lists_Cards>) lista.getCartas();
        cardsToLabels();
        getCartas("superior", "");
        addLabel();
    }

    void cardsToLabels() throws SQLException, ClassNotFoundException {
        for (int i = 0; i < cards.size(); i++) {
            for (int j = 0; j < cards.get(i).getQuantidade(); j++) {
                JLabel label = new JLabel();
                label.setVisible(true); // torna ela visível
                label.setForeground(java.awt.Color.black);
                label.setSize(150, 210); // tamanho
                label.setText(aux.getNomeByID(cards.get(i).getCard_id()));

                //separa por tipo de carta
                switch (aux.getTipoByID(cards.get(i).getCard_id())) {
                    //se for um pokemon
                    case "Pokemon":
                        label.setBackground(java.awt.Color.LIGHT_GRAY);
                        //muda a cor de acordo com o tipo de energia
                        switch (aux.getEnergiaByID(cards.get(i).getCard_id())) {
                            case "Fogo":
                                label.setBorder(new LineBorder(java.awt.Color.red, 2));
                                break;
                            case "Agua":
                                label.setBorder(new LineBorder(java.awt.Color.blue, 2));
                                break;
                            case "Planta":
                                label.setBorder(new LineBorder(java.awt.Color.GREEN, 2));
                                break;
                            case "Eletrica":
                                label.setBorder(new LineBorder(java.awt.Color.yellow, 2));
                                break;
                            case "Psiquica":
                                label.setBorder(new LineBorder(new java.awt.Color(130, 0, 115), 2));
                                break;
                            case "Luta":
                                label.setBorder(new LineBorder(new java.awt.Color(201, 101, 0), 2));
                                break;
                            case "Noturna":
                                label.setBorder(new LineBorder(java.awt.Color.DARK_GRAY, 2));
                                break;
                            case "Metal":
                                label.setBorder(new LineBorder(java.awt.Color.GRAY, 2));
                                break;
                            case "Fada":
                                label.setBorder(new LineBorder(new java.awt.Color(255, 80, 210), 2));
                                break;
                        }
                        break;
                    case "Apoiador":
                    case "Item":
                    case "Estadio":
                        label.setBorder(new LineBorder(java.awt.Color.BLACK, 1));
                        label.setBackground(java.awt.Color.LIGHT_GRAY);
                        break;
                    //se for uma carta de energia muda a cor de acordo com ela
                    case "Energia":
                        label.setBorder(new LineBorder(java.awt.Color.BLACK, 1));
                        switch (aux.getEnergiaByID(cards.get(i).getCard_id())) {
                            case "Fogo":
                                label.setForeground(java.awt.Color.red);
                                break;
                            case "Agua":
                                label.setForeground(java.awt.Color.blue);
                                break;
                            case "Planta":
                                label.setForeground(java.awt.Color.GREEN);
                                break;
                            case "Eletrica":
                                label.setForeground(java.awt.Color.yellow);
                                break;
                            case "Psiquica":
                                label.setForeground(new java.awt.Color(130, 0, 115));
                                break;
                            case "Luta":
                                label.setForeground(new java.awt.Color(201, 101, 0));
                                break;
                            case "Noturna":
                                label.setForeground(java.awt.Color.DARK_GRAY);
                                break;
                            case "Metal":
                                label.setForeground(java.awt.Color.GRAY);
                                break;
                            case "Fada":
                                label.setForeground(new java.awt.Color(255, 80, 210));
                                break;
                        }
                }
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        removeCarta(e);
                    }
                });
                labelsI.add(label);
                cardsMap.put(aux.getNomeByID(cards.get(i).getCard_id()), cards.get(i).getCard_id());
            }

        }    
}

public void getCartas(String s, String searchTerm) throws SQLException{
        
        
        ResultSet rs = bd.searchBy(searchTerm);
        //limpa os arraylists
        if(s.equals("superior")){
            labelsS = new ArrayList();
            lastPesquisa=searchTerm;
        }
        else
            labelsI = new ArrayList();
        
        while(rs.next()){
            JLabel label = new JLabel();
            label.setVisible(true); // torna ela visível
            label.setForeground(java.awt.Color.black);
            label.setSize(150,210); // tamanho
            label.setText(rs.getString("nome"));
            
           
            //separa por tipo de carta
            switch(rs.getString("tipo")){
                //se for um pokemon
                case "Pokemon":
                    label.setBackground(java.awt.Color.LIGHT_GRAY);
                    //muda a cor de acordo com o tipo de energia
                    switch(rs.getString("energia")){
                        case "Fogo":
                            label.setBorder(new LineBorder(java.awt.Color.red,2));
                            break;
                        case "Agua":
                            label.setBorder(new LineBorder(java.awt.Color.blue,2));
                            break;
                        case "Planta":
                            label.setBorder(new LineBorder(java.awt.Color.GREEN,2));
                            break;
                        case "Eletrica":
                            label.setBorder(new LineBorder(java.awt.Color.yellow,2));
                            break;
                        case "Psiquica":
                            label.setBorder(new LineBorder(new java.awt.Color(130, 0, 115),2));
                            break;
                        case "Luta":
                            label.setBorder(new LineBorder(new java.awt.Color(201, 101, 0),2));
                            break;
                        case "Noturna":
                            label.setBorder(new LineBorder(java.awt.Color.DARK_GRAY,2));
                            break;
                        case "Metal":
                            label.setBorder(new LineBorder(java.awt.Color.GRAY,2));
                            break;
                        case "Fada":
                            label.setBorder(new LineBorder(new java.awt.Color(255, 80, 210),2));
                            break;
                    }
                    break;
                case "Apoiador":
                case "Item":
                case "Estadio":
                    label.setBorder(new LineBorder(java.awt.Color.BLACK,1));
                    label.setBackground(java.awt.Color.LIGHT_GRAY);
                    break;
                //se for uma carta de energia muda a cor de acordo com ela
                case "Energia":
                    label.setBorder(new LineBorder(java.awt.Color.BLACK,1));
                    switch(rs.getString("energia")){
                        case "Fogo":
                            label.setForeground(java.awt.Color.red);
                            break;
                        case "Agua":
                            label.setForeground(java.awt.Color.blue);
                            break;
                        case "Planta":
                            label.setForeground(java.awt.Color.GREEN);
                            break;
                        case "Eletrica":
                            label.setForeground(java.awt.Color.yellow);
                            break;
                        case "Psiquica":
                            label.setForeground(new java.awt.Color(130, 0, 115));
                            break;
                        case "Luta":
                            label.setForeground(new java.awt.Color(201, 101, 0));
                            break;
                        case "Noturna":
                            label.setForeground(java.awt.Color.DARK_GRAY);
                            break;
                        case "Metal":
                            label.setForeground(java.awt.Color.GRAY);
                            break;
                        case "Fada":
                            label.setForeground(new java.awt.Color(255, 80, 210));
                            break;
                    }
            }
            
            if(s.equals("superior")){
                label.addMouseListener(new MouseAdapter() {
                    @Override
        public void mouseClicked(MouseEvent e) {
                        addCarta(e);
                    }
                });
                labelsS.add(label);
            }else{
                label.addMouseListener(new MouseAdapter() {
                    @Override
        public void mouseClicked(MouseEvent e) {
                        removeCarta(e);
                    }
                });
                labelsI.add(label);
            }
            
            cardsMap.put(rs.getString("nome"), rs.getInt("id"));
        }
        addLabel();
    }
    
    public void addCarta(MouseEvent e){
        JLabel label = (JLabel) e.getSource();
        labelsI.add(label);
        System.out.println("added "+label.getText());
        int tmpid = cardsMap.get(label.getText());
        boolean is=false;
            for(int i=0; i < cards.size(); i++){
                if(cards.get(i).getCard_id() == tmpid){
                    cards.get(i).setQuantidade(cards.get(i).getQuantidade()+1);
                    is=true;
                    break;
                }
            }
            if(is==false)
                cards.add(new Lists_Cards(lista.getId(), tmpid, 1));
        RefreshActionPerformed(null);
    }
    
    public void removeCarta(MouseEvent e){
        JLabel label = (JLabel) e.getSource();
        labelsI.remove(label);
        int tmpid = cardsMap.get(label.getText());
        for(int i=0; i < cards.size(); i++){
            if(cards.get(i).getCard_id() == tmpid){
                    cards.remove(i);
                    break;
                }
            }
        System.out.println("removed "+label.getText());
        RefreshActionPerformed(null);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        save_list = new javax.swing.JButton();
        search_cards = new javax.swing.JTextField();
        search_button = new javax.swing.JButton();
        filtro_energia = new javax.swing.JLabel();
        filtro_pokemon = new javax.swing.JLabel();
        filtro_treinador = new javax.swing.JLabel();
        Refresh = new javax.swing.JButton();
        inferior = new javax.swing.JPanel();
        superior = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editação da lista");

        background.setBackground(new java.awt.Color(225, 225, 225));

        save_list.setText("Salvar");
        save_list.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_listActionPerformed(evt);
            }
        });

        search_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        search_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_buttonActionPerformed(evt);
            }
        });

        filtro_energia.setBackground(new java.awt.Color(225, 112, 85));
        filtro_energia.setForeground(new java.awt.Color(225, 112, 85));
        filtro_energia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        filtro_energia.setText("Energias");
        filtro_energia.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(225, 112, 85), 1, true));
        filtro_energia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filtro_energiaMouseClicked(evt);
            }
        });

        filtro_pokemon.setBackground(new java.awt.Color(116, 185, 255));
        filtro_pokemon.setForeground(new java.awt.Color(116, 185, 255));
        filtro_pokemon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        filtro_pokemon.setText(" Pokémons");
        filtro_pokemon.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(116, 185, 255), 1, true));
        filtro_pokemon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filtro_pokemonMouseClicked(evt);
            }
        });

        filtro_treinador.setBackground(new java.awt.Color(253, 203, 110));
        filtro_treinador.setForeground(new java.awt.Color(253, 203, 110));
        filtro_treinador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        filtro_treinador.setText("Treinadores");
        filtro_treinador.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(253, 203, 110), 1, true));
        filtro_treinador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filtro_treinadorMouseClicked(evt);
            }
        });

        Refresh.setText("Atualizar");
        Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshActionPerformed(evt);
            }
        });

        inferior.setPreferredSize(new java.awt.Dimension(100, 215));

        javax.swing.GroupLayout inferiorLayout = new javax.swing.GroupLayout(inferior);
        inferior.setLayout(inferiorLayout);
        inferiorLayout.setHorizontalGroup(
            inferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        inferiorLayout.setVerticalGroup(
            inferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout superiorLayout = new javax.swing.GroupLayout(superior);
        superior.setLayout(superiorLayout);
        superiorLayout.setHorizontalGroup(
            superiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        superiorLayout.setVerticalGroup(
            superiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 249, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(inferior, javax.swing.GroupLayout.DEFAULT_SIZE, 1338, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, backgroundLayout.createSequentialGroup()
                        .addGap(0, 205, Short.MAX_VALUE)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                                .addComponent(filtro_pokemon, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(filtro_treinador, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(filtro_energia, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(478, 478, 478)
                                .addComponent(search_cards, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(search_button, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(save_list, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Refresh, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(superior, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(filtro_treinador, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(filtro_pokemon, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(filtro_energia, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 9, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(search_cards, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(search_button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(superior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Refresh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inferior, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(save_list)
                .addContainerGap())
        );

        filtro_energia.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // função de adicionar label
    private void addLabel(){
        pos += 16; // att x da label
        /*filtro_energia.setBackground(new java.awt.Color(225, 112, 85));
        filtro_energia.setForeground(new java.awt.Color(225, 112, 85));
        filtro_energia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        filtro_energia.setText("Energias");
        filtro_energia.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(225, 112, 85), 1, true));
        */
        
        /*
        JLabel label = new JLabel(); // instancia nova label
        
        
        label.setVisible(true); // torna ela visível
        label.setBackground(java.awt.Color.white);
        label.setForeground(java.awt.Color.black);
        //label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setBorder(new javax.swing.border.LineBorder(java.awt.Color.black, 1, true));
        label.setText(String.valueOf(labels.size())); // seta o conteúdo
        label.setSize(150,210); // tamanho
        labels.add(label);*/
        //label.setLocation(return_location.getVerticalAlignment(),return_location.getHorizontalAlignment()); //localização
        inferior.removeAll();
        for(int i=0; i < labelsI.size(); i++){
            inferior.add(labelsI.get(i), FlowLayout.LEFT);
        }
        
        superior.removeAll();
        for(int i=0; i < labelsS.size(); i++){
            superior.add(labelsS.get(i));
        }
        pack();
        //getContentPane().add(label); // colocando o label no frame
        //initComponents(); // att frame
        background.repaint();
        inferior.repaint();
        superior.repaint();
    }
    private void save_listActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_listActionPerformed
        for(int i=0; i < cards.size(); i++){
            try {
                cards.get(i).insertListCard(cards.get(i));
            

} catch (ClassNotFoundException ex) {
                Logger.getLogger(TelaEditaLista.class
.getName()).log(Level.SEVERE, null, ex);
            

} catch (SQLException ex) {
                Logger.getLogger(TelaEditaLista.class
.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_save_listActionPerformed

    private void search_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_buttonActionPerformed
        String txt = search_cards.getText();
        try{
            getCartas("superior", txt);
        }catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_search_buttonActionPerformed

    private void RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshActionPerformed
        try {
            getCartas("superior", lastPesquisa);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }//GEN-LAST:event_RefreshActionPerformed

    private void filtro_pokemonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filtro_pokemonMouseClicked
        try {
            getCartas("superior", "pokemon");
        } catch (SQLException ex) {
            Logger.getLogger(TelaEditaLista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_filtro_pokemonMouseClicked

    private void filtro_treinadorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filtro_treinadorMouseClicked
        try {
            getCartas("superior", "apoiador");
        } catch (SQLException ex) {
            Logger.getLogger(TelaEditaLista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_filtro_treinadorMouseClicked

    private void filtro_energiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filtro_energiaMouseClicked
        try {
            getCartas("superior", "energia");
        } catch (SQLException ex) {
            Logger.getLogger(TelaEditaLista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_filtro_energiaMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                

}
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaEditaLista.class
.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        

} catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaEditaLista.class
.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        

} catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaEditaLista.class
.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        

} catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaEditaLista.class
.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaEditaLista dialog;
                try {
                    dialog = new TelaEditaLista(new javax.swing.JFrame(), true, lista);
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                        }
                    });
                    dialog.setVisible(true);
                } catch (Exception e){
                    System.out.println(e);
                }
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Refresh;
    private javax.swing.JPanel background;
    private javax.swing.JLabel filtro_energia;
    private javax.swing.JLabel filtro_pokemon;
    private javax.swing.JLabel filtro_treinador;
    private javax.swing.JPanel inferior;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton save_list;
    private javax.swing.JButton search_button;
    private javax.swing.JTextField search_cards;
    private javax.swing.JPanel superior;
    // End of variables declaration//GEN-END:variables
}
