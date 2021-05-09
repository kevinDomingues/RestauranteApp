/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import restauranteapp.BLL.CodpostaisJpaController;
import restauranteapp.BLL.EmpresaJpaController;
import restauranteapp.BLL.EntidadeJpaController;
import restauranteapp.DAL.Codpostais;
import restauranteapp.DAL.Empresa;
import restauranteapp.DAL.Entidade;

/**
 *
 * @author kevin
 */
public class Login extends javax.swing.JFrame {

    EntityManagerFactory em;
    
    /**
     * Creates new form Login2
     */
    public Login() {
        this.em = Persistence.createEntityManagerFactory("RestauranteAppPU");
      //  this.setUndecorated(true);
        initComponents();        
        this.setLocationRelativeTo(null);
        SwitchPanel(1);
        populateCodPostais();
    }
       
    private void validateLogin(String username, String password){
        EntidadeJpaController ec = new EntidadeJpaController(this.em);
        
        List<Entidade> users = ec.findEntidadeEntities();
        Entidade temp = new Entidade();
        boolean loginStatus = false;
        
        for(Entidade e : users){
            if(e.getUsername().equals(username) && e.getPasswordp().equals(password)){
                if(e.getNivelpermissao()==2){
                    temp = e;
                    loginStatus = true;
                } else JOptionPane.showMessageDialog(null, "Não tem permissão para entrar!");
            }
        }
        
        if(loginStatus) {
            new Menu(temp).setVisible(true);
            this.dispose();
        } 
        
    }
    
    private void populateCodPostais(){
        CodpostaisJpaController postalControl = new CodpostaisJpaController(this.em);
        
        List<Codpostais> cods = postalControl.findCodpostaisEntities();
        
        for(Codpostais e : cods){
            this.criarContaCodPostalComboBox.addItem(e.toString());
        }
    }
    
    private void clearFields(){
            this.criarContaUsername.setText(null);
            this.criarContaNome.setText(null);
            this.criarContaEmail.setText(null);
            this.criarContaContacto.setText(null);
            this.criarContaNif.setText(null);
            this.criarContaRua.setText(null);
            this.criarContaNPorta.setText(null);
            this.criarContaPassword.setText(null);
            this.criarContaPassword2.setText(null);
    }
    
    private boolean usernameTaken(String username){
        EntidadeJpaController ec = new EntidadeJpaController(this.em);
        
        List<Entidade> users = ec.findEntidadeEntities();
        
        boolean alreadyexists = false;
        for(Entidade e: users){
            if(e.getUsername().equals(username)){
               alreadyexists = true;
               JOptionPane.showMessageDialog(null, "Username já utilizado!!");
            }
        }
        return alreadyexists;
    }
    
    private boolean passwordCheck(String password, String password2){
        if(password.equals(password2)){
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "A password não é igual!!");
            return false;
        }
        
    }
    
    private String formatText(String nomeCompleto) throws IllegalArgumentException {
        
        String[] nomes = nomeCompleto.trim().split("\\s+");
        

        if(nomes.length <= 0) {
            throw new IllegalArgumentException("O nome não pode ser vazio");
        }
        

        for (int i = 0; i < nomes.length; i++) {
            StringBuilder sb = new StringBuilder(nomes[i].toLowerCase());

            // altera o primeiro caráter para letra maiúscula
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));

            // atualiza a string com o nome formatado
            nomes[i] = sb.toString();
        }

        // junta todos os nomes
        return String.join(" ", nomes);
    
    }
    
    private boolean NoEmptyFields(){
        return !this.criarContaUsername.getText().isEmpty() && !this.criarContaNome.getText().isEmpty() 
                && !this.criarContaEmail.getText().isEmpty() && !this.criarContaContacto.getText().isEmpty()
                && !this.criarContaNif.getText().isEmpty() && !this.criarContaRua.getText().isEmpty()
                && !this.criarContaNPorta.getText().isEmpty() && !this.criarContaPassword.getText().isEmpty()
                && !this.criarContaPassword2.getText().isEmpty();
    }
    
     private boolean checkNifAndContacto() {
        if(this.criarContaNif.getText().length()==9 && this.criarContaContacto.getText().length()==9){
            return true;
        } else{
            JOptionPane.showMessageDialog(null, "Nif e Contacto devem ter 9 dígitos!");
            return false;
        }
    }
    
    private void criarConta(){
        CodpostaisJpaController postalControl = new CodpostaisJpaController(this.em);
        EmpresaJpaController empControl = new EmpresaJpaController(this.em);
        EntidadeJpaController ec = new EntidadeJpaController(this.em);
        
        
        if(NoEmptyFields()){
            
            String username = this.criarContaUsername.getText().trim();
            String nomeCompleto = formatText(this.criarContaNome.getText());
            String email = this.criarContaEmail.getText().trim();
            int telefone = Integer.parseInt(this.criarContaContacto.getText().trim());
            int nif = Integer.parseInt(this.criarContaNif.getText().trim());
            String rua = this.criarContaRua.getText();
            int nPorta = Integer.parseInt(this.criarContaNPorta.getText().trim());

            String codPostaltemp = this.criarContaCodPostalComboBox.getSelectedItem().toString();
            Codpostais codPostal = postalControl.findCodpostais(codPostaltemp);

            String password = this.criarContaPassword.getText();
            String password2 = this.criarContaPassword2.getText();



            if(!usernameTaken(username) && passwordCheck(password, password2)){
                if(checkNifAndContacto()){
                    Entidade temp = new Entidade();
                    temp.setIdEntidade(null);
                    temp.setCodpostal(codPostal);
                    temp.setEmail(email);
                    temp.setIdEmpresa(empControl.findEmpresa(1));
                    temp.setNif(nif);
                    temp.setNivelpermissao(2);
                    temp.setNome(nomeCompleto);
                    temp.setNporta(nPorta);
                    temp.setPasswordp(password);
                    temp.setRua(rua);
                    temp.setTelefone(telefone);
                    temp.setUsername(username);
         
                    try {
                        ec.create(temp);
                        JOptionPane.showMessageDialog(null, "Conta criada!");
                        SwitchPanel(1);
                        clearFields();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao criar conta!!");
                    }
                }
            }
        } else JOptionPane.showMessageDialog(null, "Preencha todos os dados!");
        
    }
    
    private void clearPanels() {
        this.LoginPanel.setVisible(false);
        this.CriarContaPanel.setVisible(false);
    }
    
    private void SwitchPanel(int panelNumber) {
        clearPanels();
        switch(panelNumber){
            case 1: this.LoginPanel.setVisible(true); break;//this.LayeredPanel.moveToFront(this.LoginPanel);
            case 2: this.CriarContaPanel.setVisible(true); break;  //this.LayeredPanel.moveToFront(this.CriarContaPanel);
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
        LayeredPanel = new javax.swing.JLayeredPane();
        LoginPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        criarContaButton = new javax.swing.JLabel();
        jUsernameField = new javax.swing.JTextField();
        jPasswordField = new javax.swing.JPasswordField();
        LoginButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        CriarContaPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        criarContaUsername = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        criarContaPassword = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        criarContaPassword2 = new javax.swing.JPasswordField();
        jLabel9 = new javax.swing.JLabel();
        criarContaNome = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        criarContaNif = new javax.swing.JTextField();
        criarContaRua = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        criarContaNPorta = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        criarConta = new javax.swing.JButton();
        CancelarButton = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        criarContaEmail = new javax.swing.JTextField();
        criarContaCodPostalComboBox = new javax.swing.JComboBox<>();
        criarContaContacto = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(41, 128, 185));
        jPanel1.setPreferredSize(new java.awt.Dimension(250, 530));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel1, java.awt.BorderLayout.LINE_START);

        LayeredPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LoginPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Password:");
        LoginPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 270, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Login");
        LoginPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(226, 62, -1, -1));

        criarContaButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        criarContaButton.setForeground(new java.awt.Color(102, 204, 255));
        criarContaButton.setText("Criar conta");
        criarContaButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                criarContaButtonMouseClicked(evt);
            }
        });
        LoginPanel.add(criarContaButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(239, 451, -1, -1));

        jUsernameField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jUsernameField.setForeground(new java.awt.Color(102, 102, 102));
        jUsernameField.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4));
        jUsernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUsernameFieldActionPerformed(evt);
            }
        });
        LoginPanel.add(jUsernameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 226, 365, 26));

        jPasswordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPasswordField.setForeground(new java.awt.Color(102, 102, 102));
        jPasswordField.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4));
        LoginPanel.add(jPasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 301, 365, 26));

        LoginButton.setBackground(new java.awt.Color(255, 255, 255));
        LoginButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        LoginButton.setForeground(new java.awt.Color(102, 102, 102));
        LoginButton.setText("Entrar");
        LoginButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        LoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginButtonActionPerformed(evt);
            }
        });
        LoginPanel.add(LoginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 403, 142, 42));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Username:");
        LoginPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 195, -1, -1));

        LayeredPanel.add(LoginPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, 530));

        CriarContaPanel.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBar(null);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Criar Conta");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Username:");

        criarContaUsername.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        criarContaUsername.setForeground(new java.awt.Color(102, 102, 102));
        criarContaUsername.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4));
        criarContaUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                criarContaUsernameActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Password:");

        criarContaPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        criarContaPassword.setForeground(new java.awt.Color(102, 102, 102));
        criarContaPassword.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Confirmar password:");

        criarContaPassword2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        criarContaPassword2.setForeground(new java.awt.Color(102, 102, 102));
        criarContaPassword2.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Nome Completo:");

        criarContaNome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        criarContaNome.setForeground(new java.awt.Color(102, 102, 102));
        criarContaNome.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4));
        criarContaNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                criarContaNomeActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("NIF:");

        criarContaNif.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        criarContaNif.setForeground(new java.awt.Color(102, 102, 102));
        criarContaNif.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4));
        criarContaNif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                criarContaNifActionPerformed(evt);
            }
        });

        criarContaRua.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        criarContaRua.setForeground(new java.awt.Color(102, 102, 102));
        criarContaRua.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4));
        criarContaRua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                criarContaRuaActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("Rua:");

        criarContaNPorta.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        criarContaNPorta.setForeground(new java.awt.Color(102, 102, 102));
        criarContaNPorta.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4));
        criarContaNPorta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                criarContaNPortaActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setText("Número porta:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setText("Código Postal:");

        criarConta.setBackground(new java.awt.Color(255, 255, 255));
        criarConta.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        criarConta.setForeground(new java.awt.Color(102, 102, 102));
        criarConta.setText("Criar conta");
        criarConta.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        criarConta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                criarContaActionPerformed(evt);
            }
        });

        CancelarButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        CancelarButton.setForeground(new java.awt.Color(102, 204, 255));
        CancelarButton.setText("Cancelar");
        CancelarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CancelarButtonMouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 102, 102));
        jLabel15.setText("Email:");

        criarContaEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        criarContaEmail.setForeground(new java.awt.Color(102, 102, 102));
        criarContaEmail.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4));
        criarContaEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                criarContaEmailActionPerformed(evt);
            }
        });

        criarContaCodPostalComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        criarContaCodPostalComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        criarContaCodPostalComboBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        criarContaContacto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        criarContaContacto.setForeground(new java.awt.Color(102, 102, 102));
        criarContaContacto.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4));
        criarContaContacto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                criarContaContactoActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 102, 102));
        jLabel16.setText("Contacto:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(180, 180, 180)
                        .addComponent(jLabel5))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jLabel6))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(criarContaUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jLabel9))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(criarContaNome, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jLabel15))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(criarContaEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(criarContaNif, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jLabel11))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(criarContaRua, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jLabel12))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(criarContaNPorta, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jLabel13))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(criarContaCodPostalComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jLabel7))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(criarContaPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jLabel8))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(criarContaPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(criarConta, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(CancelarButton)))
                        .addGap(95, 95, 95))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(criarContaContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))))
                .addGap(91, 91, 91))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel5)
                .addGap(72, 72, 72)
                .addComponent(jLabel6)
                .addGap(15, 15, 15)
                .addComponent(criarContaUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel9)
                .addGap(5, 5, 5)
                .addComponent(criarContaNome, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel15)
                .addGap(5, 5, 5)
                .addComponent(criarContaEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addGap(5, 5, 5)
                .addComponent(criarContaContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(5, 5, 5)
                .addComponent(criarContaNif, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel11)
                .addGap(5, 5, 5)
                .addComponent(criarContaRua, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel12)
                .addGap(15, 15, 15)
                .addComponent(criarContaNPorta, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel13)
                .addGap(5, 5, 5)
                .addComponent(criarContaCodPostalComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel7)
                .addGap(5, 5, 5)
                .addComponent(criarContaPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel8)
                .addGap(5, 5, 5)
                .addComponent(criarContaPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(criarConta, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(CancelarButton)
                .addGap(29, 29, 29))
        );

        jScrollPane1.setViewportView(jPanel4);

        CriarContaPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        LayeredPanel.add(CriarContaPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, 530));

        getContentPane().add(LayeredPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void criarContaButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_criarContaButtonMouseClicked
        // TODO add your handling code here:
        SwitchPanel(2);
    }//GEN-LAST:event_criarContaButtonMouseClicked

    private void jUsernameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUsernameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jUsernameFieldActionPerformed

    private void LoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginButtonActionPerformed
        // TODO add your handling code here:
        
        //Retirar os comentários em baixo caso seja necessário utilizar uma conta teste 
        
//        Entidade teste = new Entidade();
//        teste.setNome("Entidade teste");
//        Empresa temp = new Empresa(1, "Restaurante teste LDA", 1234);
//        teste.setIdEmpresa(temp);
//        new Menu(teste).setVisible(true);
        
        String username = this.jUsernameField.getText();
        String password = this.jPasswordField.getText();

        validateLogin(username, password);
    }//GEN-LAST:event_LoginButtonActionPerformed

    private void criarContaUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_criarContaUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_criarContaUsernameActionPerformed

    private void criarContaNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_criarContaNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_criarContaNomeActionPerformed

    private void criarContaNifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_criarContaNifActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_criarContaNifActionPerformed

    private void criarContaRuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_criarContaRuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_criarContaRuaActionPerformed

    private void criarContaNPortaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_criarContaNPortaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_criarContaNPortaActionPerformed

    private void CancelarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CancelarButtonMouseClicked
        SwitchPanel(1);
        clearFields();
    }//GEN-LAST:event_CancelarButtonMouseClicked

    private void criarContaEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_criarContaEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_criarContaEmailActionPerformed

    private void criarContaContactoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_criarContaContactoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_criarContaContactoActionPerformed

    private void criarContaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_criarContaActionPerformed
        // TODO add your handling code here:
        criarConta();
    }//GEN-LAST:event_criarContaActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CancelarButton;
    private javax.swing.JPanel CriarContaPanel;
    private javax.swing.JLayeredPane LayeredPanel;
    private javax.swing.JButton LoginButton;
    private javax.swing.JPanel LoginPanel;
    private javax.swing.JButton criarConta;
    private javax.swing.JLabel criarContaButton;
    private javax.swing.JComboBox<String> criarContaCodPostalComboBox;
    private javax.swing.JTextField criarContaContacto;
    private javax.swing.JTextField criarContaEmail;
    private javax.swing.JTextField criarContaNPorta;
    private javax.swing.JTextField criarContaNif;
    private javax.swing.JTextField criarContaNome;
    private javax.swing.JPasswordField criarContaPassword;
    private javax.swing.JPasswordField criarContaPassword2;
    private javax.swing.JTextField criarContaRua;
    private javax.swing.JTextField criarContaUsername;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jUsernameField;
    // End of variables declaration//GEN-END:variables

}
