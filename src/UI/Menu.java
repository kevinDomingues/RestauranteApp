/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import restauranteapp.BLL.MenuJpaController;
import restauranteapp.DAL.Encomenda;
import restauranteapp.DAL.Entidade;
import restauranteapp.DAL.Mesas;
import restauranteapp.DAL.Pedido;
import restauranteapp.DAL.Produtoementa;
import restauranteapp.DAL.Stockproduto;

/**
 *
 * @author kevin
 */
public class Menu extends javax.swing.JFrame {

    private Entidade temp;
    private MenuJpaController mc;
    /**
     * Creates new form Menu
     */
    public Menu(Entidade temp) {
        this.temp = temp;
        this.mc = new MenuJpaController();
       // this.setUndecorated(true);
        initComponents();
        setLocationRelativeTo(null);
        SwitchPanel(6);
        this.Username.setText(temp.getNome());
        this.Empresa.setText(temp.getIdEmpresa().getNome());
    }

    public void setButtonColor(javax.swing.JPanel panel){
        panel.setBackground(new Color(47,144,208));
    }
    
    public void resetButtonColor(javax.swing.JPanel panel){
        if(panel.equals(this.MesasButton) && !(this.verMesas.isVisible() || this.mesas.isVisible())) panel.setBackground(new Color(45,136,195));
        else if(panel.equals(this.PedidosButton) && !this.verPedidos.isVisible()) panel.setBackground(new Color(45,136,195));
        else if(panel.equals(this.ProdutosButton) && !this.verProdutos.isVisible()) panel.setBackground(new Color(45,136,195));
        else if(panel.equals(this.EncomendasButton) && !this.verEncomendas.isVisible()) panel.setBackground(new Color(45,136,195));
        else if(panel.equals(this.DesconectarButton)) panel.setBackground(new Color(45,136,195));
    }
    
    public void resetAllButtonColors(){
        this.EncomendasButton.setBackground(new Color(45,136,195));
        this.MesasButton.setBackground(new Color(45,136,195));
        this.PedidosButton.setBackground(new Color(45,136,195));
        this.ProdutosButton.setBackground(new Color(45,136,195));
    }
    
    public void clearPanels() {
        this.verMesas.setVisible(false);
        this.verPedidos.setVisible(false);
        this.verProdutos.setVisible(false);
        this.verEncomendas.setVisible(false);
        this.mesas.setVisible(false);
        this.defaultpanel.setVisible(false);
    }
    
    public void SwitchPanel(int panelNumber) {
        clearPanels();
        switch(panelNumber){
            case 1: this.verMesas.setVisible(true); break;
            case 2: this.verPedidos.setVisible(true); break;
            case 3: this.verProdutos.setVisible(true); break;
            case 4: this.verEncomendas.setVisible(true); break;
            case 5: this.mesas.setVisible(true); break;
            case 6: this.defaultpanel.setVisible(true); break;
        }
    }
    
    private void mostrarPedidos(){
        verPedidos.removeAll();
        List<Pedido> pedidos = mc.getPedidos();
        
        for(Pedido e: pedidos){
            java.awt.Color cor = new Color(255, 255, 255);
            if(e.getIdEstado().getIdEstado()==2){ // Pendente
                cor = new Color(255,235,44);
            }
            if(e.getIdEstado().getIdEstado()==3){ // Pago
                cor = new Color(60,147,232);
            }
            if(e.getIdEstado().getIdEstado()!=4){
                JPanel jpMain = new javax.swing.JPanel();
                JPanel jpPedido = new RoundedPanel(25, cor);
                JPanel backgroundPanel = new RoundedPanel(25, new Color(255,255,255));
                jpPedido.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
                jpMain.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
                
                jpPedido.setPreferredSize(new java.awt.Dimension(169, 40));
                backgroundPanel.setPreferredSize(new java.awt.Dimension(170, 250));
                
                javax.swing.JLabel jl = new javax.swing.JLabel();
                jl.setFont(new java.awt.Font("Segoe UI", 0, 18));
                jl.setText("Pedido ");
                
                javax.swing.JLabel jlNumber = new javax.swing.JLabel();
                jlNumber.setFont(new java.awt.Font("Segoe UI", 0, 18));
                jlNumber.setText(e.getCodpedido().toString());
                
                javax.swing.JLabel jlPedidoInfo = new javax.swing.JLabel("<html><div style='text-align: center;'>" 
                        +"<b>Nome:</b><br>" + e.getIdEntidade().getNome()+"<br><b>Rua:</b><br>" +e.getIdEntidade().getRua()+"<br><b>Valor total:</b><br>" +e.getValortotal()+ "€<br><b>Hora:</b><br>"
                        +e.getDatahora().getHours()+":"+e.getDatahora().getMinutes()+"h" + "</div></html>");
                jlPedidoInfo.setFont(new java.awt.Font("Segoe UI", 0, 12));
                jlPedidoInfo.setForeground(new Color(60,63,65));
              //  jlPedidoInfo.setText(e.toString());
                
                jpPedido.add(jl);
                jpPedido.add(jlNumber);
                backgroundPanel.add(jpPedido);
                backgroundPanel.add(jlPedidoInfo);
                jpMain.add(backgroundPanel);
                
                verPedidos.add(jpMain);
                
            }
        }
        verPedidos.validate();
        verPedidos.repaint();
    }
    
    private void mostrarMesas(){
        jPanel2.removeAll();
        List<Mesas> mesas = mc.getMesas();
        
        for(Mesas e : mesas){
            JPanel jpMain = new javax.swing.JPanel();
            jpMain.setPreferredSize(new java.awt.Dimension(200, 200));
            JPanel jp = new RoundedPanel(25, new java.awt.Color(51, 153, 255));
            jp.setLayout(new GridBagLayout());
            jp.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
            jp.setPreferredSize(new java.awt.Dimension(200, 100));
            javax.swing.JLabel jl = new javax.swing.JLabel("Mesa "+e.getIdMesa());
            jp.add(jl);
            jpMain.add(jp);
            jPanel2.add(jpMain);
        }
        jPanel2.validate();
        jPanel2.repaint();
    }
    
    private void populateProdutos(){
        List<Stockproduto> Stockprodutos = this.mc.getStockProdutos();
        DefaultTableModel tableProdutos = (DefaultTableModel) this.jTableProdutos.getModel();
        tableProdutos.setRowCount(0);
        
        
        for(Stockproduto e : Stockprodutos){
            tableProdutos.insertRow(0, new Object[] { e.getNome() });
        }    
    }
    
    private void populateEncomendas(){
        List<Encomenda> encomendas = this.mc.getEncomendas();
        DefaultTableModel tableEncomendas = (DefaultTableModel) this.jTableEncomendas.getModel();
        tableEncomendas.setRowCount(0);
        System.out.println("Teste");
 
        
        for(Encomenda e : encomendas){
            tableEncomendas.insertRow(0, new Object[] { e.getDescricao() });
        }  
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        EncomendasButton = new javax.swing.JPanel();
        EncomendasButtonLabel = new javax.swing.JLabel();
        PedidosButton = new javax.swing.JPanel();
        PedidosButtonLabel = new javax.swing.JLabel();
        MesasButton = new javax.swing.JPanel();
        MesasButtonLabel = new javax.swing.JLabel();
        ProdutosButton = new javax.swing.JPanel();
        ProdutosButtonLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        DesconectarButton = new javax.swing.JPanel();
        Desconectar = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        Username = new javax.swing.JLabel();
        Empresa = new javax.swing.JLabel();
        jPanel52 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        defaultpanel = new javax.swing.JPanel();
        verProdutos = new javax.swing.JPanel();
        jTextField4 = new javax.swing.JTextField();
        jPanel53 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextProdutos = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableProdutos = new javax.swing.JTable();
        jPanel5 = new RoundedPanel(50, new Color(85, 167, 219));
        verMesas = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        verPedidos = new javax.swing.JPanel();
        verEncomendas = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jPanel55 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEncomendas = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();
        jPanel6 = new RoundedPanel(50, new Color(85, 167, 219));
        mesas = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Entradas = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        Comida = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        Sobremesa = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList<>();
        Bebida = new javax.swing.JScrollPane();
        jList4 = new javax.swing.JList<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListProdutosAdicionar = new javax.swing.JList<>();
        jLabel5 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListProdutosAdicionados = new javax.swing.JList<>();
        jPanel24 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(41, 128, 185));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EncomendasButton.setBackground(new java.awt.Color(45, 136, 195));
        EncomendasButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EncomendasButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                EncomendasButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                EncomendasButtonMouseExited(evt);
            }
        });
        EncomendasButton.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EncomendasButtonLabel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        EncomendasButtonLabel.setForeground(new java.awt.Color(255, 255, 255));
        EncomendasButtonLabel.setText("Encomendas");
        EncomendasButtonLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EncomendasButtonLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                EncomendasButtonLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                EncomendasButtonLabelMouseExited(evt);
            }
        });
        EncomendasButton.add(EncomendasButtonLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 120, -1));

        jPanel1.add(EncomendasButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 310, 40));

        PedidosButton.setBackground(new java.awt.Color(45, 136, 195));
        PedidosButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PedidosButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PedidosButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PedidosButtonMouseExited(evt);
            }
        });
        PedidosButton.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PedidosButtonLabel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        PedidosButtonLabel.setForeground(new java.awt.Color(255, 255, 255));
        PedidosButtonLabel.setText("Pedidos");
        PedidosButtonLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PedidosButtonLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PedidosButtonLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PedidosButtonLabelMouseExited(evt);
            }
        });
        PedidosButton.add(PedidosButtonLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 80, -1));

        jPanel1.add(PedidosButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 310, 40));

        MesasButton.setBackground(new java.awt.Color(45, 136, 195));
        MesasButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MesasButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                MesasButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                MesasButtonMouseExited(evt);
            }
        });
        MesasButton.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MesasButtonLabel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        MesasButtonLabel.setForeground(new java.awt.Color(255, 255, 255));
        MesasButtonLabel.setText("Mesas");
        MesasButtonLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MesasButtonLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                MesasButtonLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                MesasButtonLabelMouseExited(evt);
            }
        });
        MesasButton.add(MesasButtonLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 60, -1));

        jPanel1.add(MesasButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 310, 40));

        ProdutosButton.setBackground(new java.awt.Color(45, 136, 195));
        ProdutosButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProdutosButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ProdutosButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ProdutosButtonMouseExited(evt);
            }
        });
        ProdutosButton.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ProdutosButtonLabel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ProdutosButtonLabel.setForeground(new java.awt.Color(255, 255, 255));
        ProdutosButtonLabel.setText("Produtos");
        ProdutosButtonLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProdutosButtonLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ProdutosButtonLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ProdutosButtonLabelMouseExited(evt);
            }
        });
        ProdutosButton.add(ProdutosButtonLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 90, -1));

        jPanel1.add(ProdutosButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 310, 40));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("RestauranteApp");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        DesconectarButton.setBackground(new java.awt.Color(45, 136, 195));
        DesconectarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DesconectarButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                DesconectarButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                DesconectarButtonMouseExited(evt);
            }
        });
        DesconectarButton.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Desconectar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Desconectar.setForeground(new java.awt.Color(255, 255, 255));
        Desconectar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Desconectar.setText("Desconectar");
        Desconectar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DesconectarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                DesconectarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                DesconectarMouseExited(evt);
            }
        });
        DesconectarButton.add(Desconectar, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 10, 310, -1));

        jPanel1.add(DesconectarButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 630, 310, 40));

        jPanel26.setBackground(new java.awt.Color(41, 128, 185));
        jPanel26.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        Username.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Username.setForeground(new java.awt.Color(255, 255, 255));
        Username.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Username.setText("jLabel45");

        Empresa.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        Empresa.setForeground(new java.awt.Color(255, 255, 255));
        Empresa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Empresa.setText("jLabel45");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Empresa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Username, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(97, Short.MAX_VALUE)
                .addComponent(Username)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Empresa)
                .addContainerGap())
        );

        jPanel1.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 460, 310, 150));

        jPanel52.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 260, 2));

        defaultpanel.setMinimumSize(new java.awt.Dimension(10, 11));
        defaultpanel.setPreferredSize(new java.awt.Dimension(1090, 642));
        defaultpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        verProdutos.setMinimumSize(new java.awt.Dimension(1093, 645));
        verProdutos.setPreferredSize(new java.awt.Dimension(1093, 645));
        verProdutos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField4.setText("Pesquisar");
        verProdutos.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 220, -1));

        jPanel53.setBackground(new java.awt.Color(255, 255, 255));
        jPanel53.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        verProdutos.add(jPanel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, 20, 20));

        jTextProdutos.setColumns(20);
        jTextProdutos.setRows(5);
        jScrollPane8.setViewportView(jTextProdutos);

        verProdutos.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 150, 580, 420));

        jTableProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Produto"
            }
        ));
        jTableProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableProdutosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableProdutos);

        verProdutos.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 250, 420));

        jPanel5.setBackground(new java.awt.Color(87, 167, 219));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );

        verProdutos.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 960, 530));

        verMesas.setMinimumSize(new java.awt.Dimension(1093, 645));
        verMesas.setPreferredSize(new java.awt.Dimension(1093, 645));

        jPanel2.setPreferredSize(new java.awt.Dimension(1080, 636));
        jPanel2.setSize(1093, 645);
        jPanel2.setLayout(new java.awt.GridLayout());

        jPanel2.setLayout(new java.awt.GridLayout(5, 5, 10, 10));

        verMesas.add(jPanel2);

        verPedidos.setMinimumSize(new java.awt.Dimension(1093, 645));
        verPedidos.setPreferredSize(new java.awt.Dimension(1093, 645));
        verPedidos.setLayout(new java.awt.GridLayout());
        verPedidos.setLayout(new java.awt.GridLayout(2, 4, 10, 10));

        verEncomendas.setMinimumSize(new java.awt.Dimension(1093, 645));
        verEncomendas.setPreferredSize(new java.awt.Dimension(1093, 645));
        verEncomendas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField5.setText("Pesquisar");
        verEncomendas.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 220, -1));

        jPanel55.setBackground(new java.awt.Color(255, 255, 255));
        jPanel55.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        verEncomendas.add(jPanel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, 20, 20));

        jTableEncomendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Encomendas"
            }
        ));
        jScrollPane1.setViewportView(jTableEncomendas);

        verEncomendas.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 250, 420));

        jTextArea5.setColumns(20);
        jTextArea5.setRows(5);
        jScrollPane10.setViewportView(jTextArea5);

        verEncomendas.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 150, 580, 420));

        jPanel6.setBackground(new java.awt.Color(87, 167, 219));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );

        verEncomendas.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 960, 530));

        mesas.setMinimumSize(new java.awt.Dimension(10, 11));
        mesas.setPreferredSize(new java.awt.Dimension(1090, 642));
        mesas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        Entradas.setViewportView(jList1);

        jTabbedPane1.addTab("Entradas", Entradas);

        jList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        Comida.setViewportView(jList2);

        jTabbedPane1.addTab("Comida", Comida);

        jList3.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        Sobremesa.setViewportView(jList3);

        jTabbedPane1.addTab("Sobremesa", Sobremesa);

        jList4.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        Bebida.setViewportView(jList4);

        jTabbedPane1.addTab("Bebida", Bebida);

        mesas.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 970, 280));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jListProdutosAdicionar.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jListProdutosAdicionar);

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Quantidade:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(176, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        mesas.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 340, 360, 250));

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));

        jListProdutosAdicionados.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(jListProdutosAdicionados);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 370, Short.MAX_VALUE)
            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
        );

        mesas.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 340, -1, 250));

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Adicionar");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );

        mesas.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 360, 70, 70));

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Remover");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );

        mesas.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 500, -1, -1));

        jLayeredPane1.setLayer(defaultpanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(verProdutos, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(verMesas, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(verPedidos, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(verEncomendas, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(mesas, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(verMesas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1113, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(verPedidos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(verEncomendas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(verProdutos, javax.swing.GroupLayout.DEFAULT_SIZE, 1113, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(defaultpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(mesas, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)
                    .addGap(324, 324, 324)))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(verMesas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(verPedidos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(verEncomendas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(verProdutos, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(defaultpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(mesas, javax.swing.GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
                    .addGap(15, 15, 15)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MesasButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MesasButtonMouseClicked
        // TODO add your handling code here:
        SwitchPanel(1);
        resetAllButtonColors();
        setButtonColor(this.MesasButton);
        mostrarMesas();
    }//GEN-LAST:event_MesasButtonMouseClicked

    private void PedidosButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PedidosButtonMouseClicked
        // TODO add your handling code here:
        SwitchPanel(2);
        resetAllButtonColors();
        setButtonColor(this.PedidosButton);
        mostrarPedidos();
    }//GEN-LAST:event_PedidosButtonMouseClicked

    private void ProdutosButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProdutosButtonMouseClicked
        // TODO add your handling code here:
        SwitchPanel(3);
        resetAllButtonColors();
        setButtonColor(this.ProdutosButton);
        populateProdutos();
        this.jTextProdutos.setText("");
    }//GEN-LAST:event_ProdutosButtonMouseClicked

    private void EncomendasButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EncomendasButtonMouseClicked
        // TODO add your handling code here:
        SwitchPanel(4);
        resetAllButtonColors();
        setButtonColor(this.EncomendasButton);
        populateEncomendas();
    }//GEN-LAST:event_EncomendasButtonMouseClicked

    private void PedidosButtonLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PedidosButtonLabelMouseEntered
        // TODO add your handling code here:
        setButtonColor(this.PedidosButton);
    }//GEN-LAST:event_PedidosButtonLabelMouseEntered

    private void PedidosButtonLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PedidosButtonLabelMouseClicked
        // TODO add your handling code here:
        SwitchPanel(2);
        resetAllButtonColors();
        setButtonColor(this.PedidosButton);
        mostrarPedidos();
    }//GEN-LAST:event_PedidosButtonLabelMouseClicked

    private void MesasButtonLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MesasButtonLabelMouseClicked
        // TODO add your handling code here:
        SwitchPanel(1);
        resetAllButtonColors();
        setButtonColor(this.MesasButton);
    }//GEN-LAST:event_MesasButtonLabelMouseClicked

    private void ProdutosButtonLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProdutosButtonLabelMouseClicked
        // TODO add your handling code here:
        SwitchPanel(3);
        resetAllButtonColors();
        setButtonColor(this.ProdutosButton);
        populateProdutos();
        this.jTextProdutos.setText("");
    }//GEN-LAST:event_ProdutosButtonLabelMouseClicked

    private void EncomendasButtonLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EncomendasButtonLabelMouseClicked
        // TODO add your handling code here:
        SwitchPanel(4);
        resetAllButtonColors();
        setButtonColor(this.EncomendasButton);
        populateEncomendas();
    }//GEN-LAST:event_EncomendasButtonLabelMouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel8MouseClicked

    private void MesasButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MesasButtonMouseEntered
        // TODO add your handling code here:
        setButtonColor(this.MesasButton);
    }//GEN-LAST:event_MesasButtonMouseEntered

    private void MesasButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MesasButtonMouseExited
        // TODO add your handling code here:
        resetButtonColor(this.MesasButton);
    }//GEN-LAST:event_MesasButtonMouseExited

    private void PedidosButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PedidosButtonMouseEntered
        // TODO add your handling code here:
        setButtonColor(this.PedidosButton);
    }//GEN-LAST:event_PedidosButtonMouseEntered

    private void PedidosButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PedidosButtonMouseExited
        // TODO add your handling code here:
        resetButtonColor(this.PedidosButton);
    }//GEN-LAST:event_PedidosButtonMouseExited

    private void ProdutosButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProdutosButtonMouseEntered
        // TODO add your handling code here:
        setButtonColor(this.ProdutosButton);
    }//GEN-LAST:event_ProdutosButtonMouseEntered

    private void ProdutosButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProdutosButtonMouseExited
        // TODO add your handling code here:
        resetButtonColor(this.ProdutosButton);
    }//GEN-LAST:event_ProdutosButtonMouseExited

    private void EncomendasButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EncomendasButtonMouseEntered
        // TODO add your handling code here:
        setButtonColor(this.EncomendasButton);
    }//GEN-LAST:event_EncomendasButtonMouseEntered

    private void EncomendasButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EncomendasButtonMouseExited
        // TODO add your handling code here:
        resetButtonColor(this.EncomendasButton);
    }//GEN-LAST:event_EncomendasButtonMouseExited

    private void MesasButtonLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MesasButtonLabelMouseEntered
        // TODO add your handling code here:
        setButtonColor(this.MesasButton);                                             
    }//GEN-LAST:event_MesasButtonLabelMouseEntered

    private void MesasButtonLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MesasButtonLabelMouseExited
        // TODO add your handling code here:
        resetButtonColor(this.MesasButton);
    }//GEN-LAST:event_MesasButtonLabelMouseExited

    private void ProdutosButtonLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProdutosButtonLabelMouseEntered
        // TODO add your handling code here:
        setButtonColor(this.ProdutosButton);
    }//GEN-LAST:event_ProdutosButtonLabelMouseEntered

    private void ProdutosButtonLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProdutosButtonLabelMouseExited
        // TODO add your handling code here:
        resetButtonColor(this.ProdutosButton);
    }//GEN-LAST:event_ProdutosButtonLabelMouseExited

    private void EncomendasButtonLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EncomendasButtonLabelMouseEntered
        // TODO add your handling code here:
        setButtonColor(this.EncomendasButton);
    }//GEN-LAST:event_EncomendasButtonLabelMouseEntered

    private void EncomendasButtonLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EncomendasButtonLabelMouseExited
        // TODO add your handling code here:
        resetButtonColor(this.EncomendasButton);
    }//GEN-LAST:event_EncomendasButtonLabelMouseExited

    private void PedidosButtonLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PedidosButtonLabelMouseExited
        // TODO add your handling code here:
        resetButtonColor(this.PedidosButton);
    }//GEN-LAST:event_PedidosButtonLabelMouseExited

    private void DesconectarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DesconectarButtonMouseClicked
        // TODO add your handling code here:
        new Login().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_DesconectarButtonMouseClicked

    private void DesconectarButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DesconectarButtonMouseEntered
        // TODO add your handling code here:
        setButtonColor(this.DesconectarButton);
    }//GEN-LAST:event_DesconectarButtonMouseEntered

    private void DesconectarButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DesconectarButtonMouseExited
        // TODO add your handling code here:
        resetButtonColor(this.DesconectarButton);
    }//GEN-LAST:event_DesconectarButtonMouseExited

    private void DesconectarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DesconectarMouseClicked
        // TODO add your handling code here:
        new Login().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_DesconectarMouseClicked

    private void DesconectarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DesconectarMouseEntered
        // TODO add your handling code here:
        setButtonColor(this.DesconectarButton);
    }//GEN-LAST:event_DesconectarMouseEntered

    private void DesconectarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DesconectarMouseExited
        // TODO add your handling code here:
        resetButtonColor(this.DesconectarButton);
    }//GEN-LAST:event_DesconectarMouseExited

    private void jTableProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableProdutosMouseClicked
        // TODO add your handling code here:
        String nome = this.jTableProdutos.getValueAt(this.jTableProdutos.getSelectedRow(), 0).toString();
        Stockproduto temp = this.mc.findStockProdutoNome(nome);
        this.jTextProdutos.setText(temp.toString());
    }//GEN-LAST:event_jTableProdutosMouseClicked

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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu(new Entidade()).setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane Bebida;
    private javax.swing.JScrollPane Comida;
    private javax.swing.JLabel Desconectar;
    private javax.swing.JPanel DesconectarButton;
    private javax.swing.JLabel Empresa;
    private javax.swing.JPanel EncomendasButton;
    private javax.swing.JLabel EncomendasButtonLabel;
    private javax.swing.JScrollPane Entradas;
    private javax.swing.JPanel MesasButton;
    private javax.swing.JLabel MesasButtonLabel;
    private javax.swing.JPanel PedidosButton;
    private javax.swing.JLabel PedidosButtonLabel;
    private javax.swing.JPanel ProdutosButton;
    private javax.swing.JLabel ProdutosButtonLabel;
    private javax.swing.JScrollPane Sobremesa;
    private javax.swing.JLabel Username;
    private javax.swing.JPanel defaultpanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JList<String> jList3;
    private javax.swing.JList<String> jList4;
    private javax.swing.JList<String> jListProdutosAdicionados;
    private javax.swing.JList<String> jListProdutosAdicionar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableEncomendas;
    private javax.swing.JTable jTableProdutos;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextArea jTextProdutos;
    private javax.swing.JPanel mesas;
    private javax.swing.JPanel verEncomendas;
    private javax.swing.JPanel verMesas;
    private javax.swing.JPanel verPedidos;
    private javax.swing.JPanel verProdutos;
    // End of variables declaration//GEN-END:variables

class RoundedPanel extends JPanel
    {
        private Color backgroundColor;
        private int cornerRadius = 15;

        public RoundedPanel(LayoutManager layout, int radius) {
            super(layout);
            cornerRadius = radius;
        }

        public RoundedPanel(LayoutManager layout, int radius, Color bgColor) {
            super(layout);
            cornerRadius = radius;
            backgroundColor = bgColor;
        }

        public RoundedPanel(int radius) {
            super();
            cornerRadius = radius;
        }

        public RoundedPanel(int radius, Color bgColor) {
            super();
            cornerRadius = radius;
            backgroundColor = bgColor;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(cornerRadius, cornerRadius);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (backgroundColor != null) {
                graphics.setColor(backgroundColor);
            } else {
                graphics.setColor(getBackground());
            }
            graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
            graphics.setColor(getForeground());
        }
    }

}
