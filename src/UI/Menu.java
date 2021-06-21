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
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
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
import restauranteapp.DAL.Estado;
import restauranteapp.DAL.Linhapedido;
import restauranteapp.DAL.Mesas;
import restauranteapp.DAL.Pedido;
import restauranteapp.DAL.Produtoementa;
import restauranteapp.DAL.Reserva;
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
        populateEstados();
        this.Reservas.setVisible(true);
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
        this.Reservas.setVisible(false);
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
 
                int CodPedido = Integer.parseInt(e.getCodpedido().toString());
                
                jpMain.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        PedidosButtonMouseClicked(evt, CodPedido);
                    }
                    public void mouseEntered(java.awt.event.MouseEvent evt) {}
                    public void mouseExited(java.awt.event.MouseEvent evt) {}
                });
                jpMain.add(backgroundPanel);
                
                verPedidos.add(jpMain);
                 
            }
           
            
        }
        verPedidos.validate();
        verPedidos.repaint();
    }
    
    private void populatePedidosInfo(int CodPedido){
        DefaultTableModel PedidosInfoTable = (DefaultTableModel) this.PedidosInfoTable.getModel();
        PedidosInfoTable.setRowCount(0);
                
        Pedido temp = this.mc.findPedidoId(CodPedido);
        
        List<Linhapedido> linhaPedidos = temp.getLinhapedidoList();
        
        
        for(Linhapedido e : linhaPedidos){

                PedidosInfoTable.insertRow(0, new Object[] {(e.getProdutoementa().getPreco() + e.getProdutoementa().getPreco()*e.getProdutoementa().getTaxa())*e.getQuantidade(), e.getQuantidade(), e.getProdutoementa().getNome()});
            

        }
        
    }
   

    private void PedidosButtonMouseClicked(java.awt.event.MouseEvent evt, int CodPedido){
        clearPanels();
        this.pedidosInfoPanel.setVisible(true);
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
            
            int idMesa = Integer.parseInt(e.getIdMesa().toString());
            
            jpMain.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    MesasReservasButtonMouseClicked(evt, idMesa);
                }
                public void mouseEntered(java.awt.event.MouseEvent evt) {}
                public void mouseExited(java.awt.event.MouseEvent evt) {}
            });
            jPanel2.add(jpMain);
        }
        jPanel2.validate();
        jPanel2.repaint();
    }
    
    private void MesasReservasButtonMouseClicked(java.awt.event.MouseEvent evt, int idMesa){
        clearPanels();
        this.Reservas.setVisible(true);
        populateReservas(idMesa);
    }
    
    private void mostrarInfoStockProduto(Stockproduto temp){
        double precoPlusIva = temp.getPreco() + (temp.getPreco()*temp.getTaxa());
        
        this.produtoNomeField.setText(temp.getNome());
        this.precoSemIva.setText(String.valueOf(temp.getPreco()));
        this.Taxa.setText(String.valueOf(temp.getTaxa()));
        this.precoComIva.setText(String.valueOf(precoPlusIva));
        this.Descricao.setText(temp.getDescricao());
        this.jSpinner2.setValue(Integer.valueOf(temp.getQuantidade()));
    }
    
    private void mostrarInfoEncomenda(Encomenda temp){
        this.nomeFornecedor.setText(temp.getIdFornecedor().getNome());
        this.valorTotal.setText(String.valueOf(temp.getValortotal()));
        this.dataHora1.setText(String.valueOf(temp.getDatahora().toGMTString()));
        this.Descricao1.setText(temp.getDescricao());
        this.estadoComboBox.setSelectedItem(temp.getIdEstado().getEstado());
        
    }
    
    private void populateReservas(int idMesa){
        DefaultTableModel reservasPorAceitar = (DefaultTableModel) this.ReservasPorAceitar.getModel();
        DefaultTableModel reservasAceites = (DefaultTableModel) this.ReservasAceites.getModel();
        reservasPorAceitar.setRowCount(0);
        reservasAceites.setRowCount(0);
        
        Mesas temp = this.mc.findMesaId(idMesa);
        List<Reserva> reservasMesa = temp.getReservaList();
        
        for(Reserva e : reservasMesa){
            if(e.getIdEstado().getIdEstado()==2){
                reservasPorAceitar.insertRow(0, new Object[] { e.getIdReserva(), e.getIdEntidade().getNome(), e.getNpessoas(), e.getDatahora().toGMTString()});
            }
            if(e.getIdEstado().getIdEstado()==5){
                reservasAceites.insertRow(0, new Object[] { e.getIdReserva(), e.getIdEntidade().getNome(), e.getNpessoas(), e.getDatahora().toGMTString()});
            }
        }
        
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
 
        
        for(Encomenda e : encomendas){
            tableEncomendas.insertRow(0, new Object[] { e.getIdEncomenda() ,e.getDescricao() });
        }  
    }
    
    private void populateEstados(){
        List<Estado> estados = this.mc.getEstados();
        
        for(Estado e : estados){
            this.estadoComboBox.addItem(e.getEstado());
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
        Reservas = new javax.swing.JPanel();
        VoltarDeReservas = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        ReservasPorAceitar = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        ReservasAceites = new javax.swing.JTable();
        GuardarStockProduto1 = new javax.swing.JButton();
        RecusarReserva = new javax.swing.JButton();
        defaultpanel = new javax.swing.JPanel();
        verProdutos = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        precoSemIva = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        produtoNomeField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        Taxa = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        precoComIva = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        ApagarStockProduto = new javax.swing.JButton();
        GuardarStockProduto = new javax.swing.JButton();
        Descricao = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jSpinner2 = new javax.swing.JSpinner();
        jTextField4 = new javax.swing.JTextField();
        jPanel53 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableProdutos = new javax.swing.JTable();
        jPanel5 = new RoundedPanel(50, new Color(85, 167, 219));
        verMesas = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        verPedidos = new javax.swing.JPanel();
        verEncomendas = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        valorTotal = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        nomeFornecedor = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        ApagarEncomenda = new javax.swing.JButton();
        GuardarEncomenda = new javax.swing.JButton();
        Descricao1 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        dataHora1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        estadoComboBox = new javax.swing.JComboBox<>();
        jTextField5 = new javax.swing.JTextField();
        jPanel55 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEncomendas = new javax.swing.JTable();
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
        pedidosInfoPanel = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        PedidosInfoTable = new javax.swing.JTable();

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

        Reservas.setMinimumSize(new java.awt.Dimension(1093, 645));
        Reservas.setPreferredSize(new java.awt.Dimension(1093, 645));
        Reservas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        VoltarDeReservas.setText("Voltar");
        VoltarDeReservas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VoltarDeReservasActionPerformed(evt);
            }
        });
        Reservas.add(VoltarDeReservas, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 17, -1, -1));

        ReservasPorAceitar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id", "Cliente", "Número Pessoas", "Data e Hora"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane6.setViewportView(ReservasPorAceitar);
        if (ReservasPorAceitar.getColumnModel().getColumnCount() > 0) {
            ReservasPorAceitar.getColumnModel().getColumn(0).setHeaderValue("id");
            ReservasPorAceitar.getColumnModel().getColumn(1).setResizable(false);
            ReservasPorAceitar.getColumnModel().getColumn(1).setHeaderValue("Cliente");
            ReservasPorAceitar.getColumnModel().getColumn(2).setHeaderValue("Número Pessoas");
            ReservasPorAceitar.getColumnModel().getColumn(3).setResizable(false);
            ReservasPorAceitar.getColumnModel().getColumn(3).setHeaderValue("Data e Hora");
        }

        Reservas.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 440, 510));

        ReservasAceites.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id", "Cliente", "Número Pessoas", "Data e Hora"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane7.setViewportView(ReservasAceites);

        Reservas.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 80, 440, 510));

        GuardarStockProduto1.setBackground(new java.awt.Color(228, 255, 201));
        GuardarStockProduto1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        GuardarStockProduto1.setForeground(new java.awt.Color(102, 102, 102));
        GuardarStockProduto1.setText("Aceitar");
        GuardarStockProduto1.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        GuardarStockProduto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarStockProduto1ActionPerformed(evt);
            }
        });
        Reservas.add(GuardarStockProduto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 240, 142, 42));

        RecusarReserva.setBackground(new java.awt.Color(255, 202, 193));
        RecusarReserva.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        RecusarReserva.setForeground(new java.awt.Color(102, 102, 102));
        RecusarReserva.setText("Recusar");
        RecusarReserva.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        RecusarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RecusarReservaActionPerformed(evt);
            }
        });
        Reservas.add(RecusarReserva, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 390, 142, 42));

        defaultpanel.setMinimumSize(new java.awt.Dimension(10, 11));
        defaultpanel.setPreferredSize(new java.awt.Dimension(1090, 642));
        defaultpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        verProdutos.setMinimumSize(new java.awt.Dimension(1093, 645));
        verProdutos.setPreferredSize(new java.awt.Dimension(1093, 645));
        verProdutos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Preço s/ Iva");

        precoSemIva.setBackground(new java.awt.Color(240, 240, 240));
        precoSemIva.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        precoSemIva.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4));
        precoSemIva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precoSemIvaActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Nome produto");

        produtoNomeField.setBackground(new java.awt.Color(240, 240, 240));
        produtoNomeField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        produtoNomeField.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4));
        produtoNomeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                produtoNomeFieldActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Taxa");

        Taxa.setBackground(new java.awt.Color(240, 240, 240));
        Taxa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Taxa.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4));
        Taxa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TaxaMouseExited(evt);
            }
        });
        Taxa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TaxaActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Preço c/ Iva");

        precoComIva.setBackground(new java.awt.Color(240, 240, 240));
        precoComIva.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        precoComIva.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4));
        precoComIva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precoComIvaActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Descrição");

        ApagarStockProduto.setBackground(new java.awt.Color(255, 202, 193));
        ApagarStockProduto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ApagarStockProduto.setForeground(new java.awt.Color(102, 102, 102));
        ApagarStockProduto.setText("Apagar");
        ApagarStockProduto.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        ApagarStockProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApagarStockProdutoActionPerformed(evt);
            }
        });

        GuardarStockProduto.setBackground(new java.awt.Color(228, 255, 201));
        GuardarStockProduto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        GuardarStockProduto.setForeground(new java.awt.Color(102, 102, 102));
        GuardarStockProduto.setText("Guardar");
        GuardarStockProduto.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        GuardarStockProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarStockProdutoActionPerformed(evt);
            }
        });

        Descricao.setBackground(new java.awt.Color(240, 240, 240));
        Descricao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Descricao.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4));
        Descricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DescricaoActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Quantidade");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(109, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GuardarStockProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(116, 116, 116)
                                .addComponent(jLabel6))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(precoSemIva, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(Taxa)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(precoComIva, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addComponent(produtoNomeField)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ApagarStockProduto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Descricao))))
                .addGap(106, 106, 106))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(produtoNomeField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(precoSemIva, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Taxa, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(precoComIva, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Descricao, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ApagarStockProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GuardarStockProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        verProdutos.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 150, 580, 420));

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
        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jPanel2.setLayout(new java.awt.GridLayout(5, 5, 10, 10));

        verMesas.add(jPanel2);

        verPedidos.setMinimumSize(new java.awt.Dimension(1093, 645));
        verPedidos.setPreferredSize(new java.awt.Dimension(1093, 645));
        verPedidos.setLayout(new java.awt.GridLayout(1, 0));
        verPedidos.setLayout(new java.awt.GridLayout(2, 4, 10, 10));

        verEncomendas.setMinimumSize(new java.awt.Dimension(1093, 645));
        verEncomendas.setPreferredSize(new java.awt.Dimension(1093, 645));
        verEncomendas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setText("Valor total");

        valorTotal.setBackground(new java.awt.Color(240, 240, 240));
        valorTotal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        valorTotal.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4));
        valorTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valorTotalActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setText("Fornecedor");

        nomeFornecedor.setBackground(new java.awt.Color(240, 240, 240));
        nomeFornecedor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        nomeFornecedor.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4));
        nomeFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomeFornecedorActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Descrição");

        ApagarEncomenda.setBackground(new java.awt.Color(255, 202, 193));
        ApagarEncomenda.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ApagarEncomenda.setForeground(new java.awt.Color(102, 102, 102));
        ApagarEncomenda.setText("Apagar");
        ApagarEncomenda.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        ApagarEncomenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApagarEncomendaActionPerformed(evt);
            }
        });

        GuardarEncomenda.setBackground(new java.awt.Color(228, 255, 201));
        GuardarEncomenda.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        GuardarEncomenda.setForeground(new java.awt.Color(102, 102, 102));
        GuardarEncomenda.setText("Guardar");
        GuardarEncomenda.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        GuardarEncomenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarEncomendaActionPerformed(evt);
            }
        });

        Descricao1.setBackground(new java.awt.Color(240, 240, 240));
        Descricao1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Descricao1.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4));
        Descricao1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Descricao1ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Data");

        dataHora1.setBackground(new java.awt.Color(240, 240, 240));
        dataHora1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        dataHora1.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4));
        dataHora1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataHora1ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Estado");

        estadoComboBox.setBackground(new java.awt.Color(240, 240, 240));
        estadoComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        estadoComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        estadoComboBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(109, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(GuardarEncomenda, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nomeFornecedor)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                            .addComponent(ApagarEncomenda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Descricao1)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(valorTotal)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(42, 42, 42))
                                    .addComponent(dataHora1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(estadoComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(106, 106, 106))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(238, 238, 238)
                        .addComponent(jLabel13))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(205, 205, 205)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomeFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(valorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataHora1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Descricao1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(estadoComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ApagarEncomenda, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GuardarEncomenda, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        verEncomendas.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 150, 580, 420));

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
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "id", "Encomendas"
            }
        ));
        jTableEncomendas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableEncomendasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableEncomendas);
        if (jTableEncomendas.getColumnModel().getColumnCount() > 0) {
            jTableEncomendas.getColumnModel().getColumn(0).setMaxWidth(35);
        }

        verEncomendas.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 250, 420));

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

        pedidosInfoPanel.setBackground(new java.awt.Color(153, 204, 255));
        pedidosInfoPanel.setMinimumSize(new java.awt.Dimension(1093, 645));
        pedidosInfoPanel.setPreferredSize(new java.awt.Dimension(1093, 645));
        pedidosInfoPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PedidosInfoTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ValorTotal", "Quantidade", "NomeProduto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Float.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(PedidosInfoTable);

        pedidosInfoPanel.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 440, 510));

        jLayeredPane1.setLayer(Reservas, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(defaultpanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(verProdutos, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(verMesas, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(verPedidos, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(verEncomendas, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(mesas, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(pedidosInfoPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(verMesas, javax.swing.GroupLayout.PREFERRED_SIZE, 1259, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(verPedidos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1259, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(verEncomendas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1259, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(verProdutos, javax.swing.GroupLayout.DEFAULT_SIZE, 1259, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(0, 56, Short.MAX_VALUE)
                    .addComponent(defaultpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1146, Short.MAX_VALUE)
                    .addGap(0, 57, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(mesas, javax.swing.GroupLayout.DEFAULT_SIZE, 929, Short.MAX_VALUE)
                    .addGap(324, 324, 324)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(Reservas, javax.swing.GroupLayout.PREFERRED_SIZE, 1230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(23, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap(20, Short.MAX_VALUE)
                    .addComponent(pedidosInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(9, 9, 9)))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(verMesas, javax.swing.GroupLayout.PREFERRED_SIZE, 676, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(verPedidos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(verEncomendas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(verProdutos, javax.swing.GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(defaultpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(mesas, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
                    .addGap(15, 15, 15)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap(15, Short.MAX_VALUE)
                    .addComponent(Reservas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(16, Short.MAX_VALUE)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap(28, Short.MAX_VALUE)
                    .addComponent(pedidosInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(8, 8, 8)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        mostrarInfoStockProduto(temp);
    }//GEN-LAST:event_jTableProdutosMouseClicked

    private void precoSemIvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_precoSemIvaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_precoSemIvaActionPerformed

    private void produtoNomeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_produtoNomeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_produtoNomeFieldActionPerformed

    private void TaxaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TaxaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TaxaActionPerformed

    private void precoComIvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_precoComIvaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_precoComIvaActionPerformed

    private void TaxaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TaxaMouseExited
        // TODO add your handling code here:
        double preco = Double.parseDouble(this.precoSemIva.getText());
        double taxa = Double.parseDouble(this.Taxa.getText());
        
        this.precoComIva.setText(String.valueOf( preco + preco*taxa ));
    }//GEN-LAST:event_TaxaMouseExited

    private void ApagarStockProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApagarStockProdutoActionPerformed
 
    }//GEN-LAST:event_ApagarStockProdutoActionPerformed

    private void GuardarStockProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarStockProdutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GuardarStockProdutoActionPerformed

    private void DescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DescricaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DescricaoActionPerformed

    private void valorTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valorTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_valorTotalActionPerformed

    private void ApagarEncomendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApagarEncomendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ApagarEncomendaActionPerformed

    private void GuardarEncomendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarEncomendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GuardarEncomendaActionPerformed

    private void Descricao1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Descricao1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Descricao1ActionPerformed

    private void nomeFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomeFornecedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nomeFornecedorActionPerformed

    private void dataHora1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataHora1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dataHora1ActionPerformed

    private void jTableEncomendasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableEncomendasMouseClicked
        // TODO add your handling code here:
        int id = Integer.parseInt(this.jTableEncomendas.getValueAt(this.jTableEncomendas.getSelectedRow(), 0).toString());
        
        Encomenda temp = this.mc.findEncomendaId(id);
        mostrarInfoEncomenda(temp);
    }//GEN-LAST:event_jTableEncomendasMouseClicked

    private void VoltarDeReservasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VoltarDeReservasActionPerformed
        // TODO add your handling code here:
        SwitchPanel(1);
        setButtonColor(this.MesasButton);
    }//GEN-LAST:event_VoltarDeReservasActionPerformed

    private void GuardarStockProduto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarStockProduto1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GuardarStockProduto1ActionPerformed

    private void RecusarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RecusarReservaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RecusarReservaActionPerformed

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
    private javax.swing.JButton ApagarEncomenda;
    private javax.swing.JButton ApagarStockProduto;
    private javax.swing.JScrollPane Bebida;
    private javax.swing.JScrollPane Comida;
    private javax.swing.JLabel Desconectar;
    private javax.swing.JPanel DesconectarButton;
    private javax.swing.JTextField Descricao;
    private javax.swing.JTextField Descricao1;
    private javax.swing.JLabel Empresa;
    private javax.swing.JPanel EncomendasButton;
    private javax.swing.JLabel EncomendasButtonLabel;
    private javax.swing.JScrollPane Entradas;
    private javax.swing.JButton GuardarEncomenda;
    private javax.swing.JButton GuardarStockProduto;
    private javax.swing.JButton GuardarStockProduto1;
    private javax.swing.JPanel MesasButton;
    private javax.swing.JLabel MesasButtonLabel;
    private javax.swing.JPanel PedidosButton;
    private javax.swing.JLabel PedidosButtonLabel;
    private javax.swing.JTable PedidosInfoTable;
    private javax.swing.JPanel ProdutosButton;
    private javax.swing.JLabel ProdutosButtonLabel;
    private javax.swing.JButton RecusarReserva;
    private javax.swing.JPanel Reservas;
    private javax.swing.JTable ReservasAceites;
    private javax.swing.JTable ReservasPorAceitar;
    private javax.swing.JScrollPane Sobremesa;
    private javax.swing.JTextField Taxa;
    private javax.swing.JLabel Username;
    private javax.swing.JButton VoltarDeReservas;
    private javax.swing.JTextField dataHora1;
    private javax.swing.JPanel defaultpanel;
    private javax.swing.JComboBox<String> estadoComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableEncomendas;
    private javax.swing.JTable jTableProdutos;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JPanel mesas;
    private javax.swing.JTextField nomeFornecedor;
    private javax.swing.JPanel pedidosInfoPanel;
    private javax.swing.JTextField precoComIva;
    private javax.swing.JTextField precoSemIva;
    private javax.swing.JTextField produtoNomeField;
    private javax.swing.JTextField valorTotal;
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
