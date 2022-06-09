import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.text.Position;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;


public class OrderManagementFrame extends JFrame implements ActionListener {
    ArrayList<Client> clients;
    ArrayList<Offer> offers;
    Client tempClient;
    Offer tempOffer;
    JPanel topPanel;
    JPanel centerPanel;
    JPanel westPanel;
    JPanel clientManagePanel;
    JPanel offerManagePanel;
    JButton buttonAddClient;
    JButton buttonRemoveClient;
    JButton buttonAddOffer;
    JButton buttonRemoveOffer;
    JButton buttonEditOffer;
    double screenWidth;
    double screenHeight;
    Dimension screenSize;
    ///
    JTree clientsTree;
    DefaultMutableTreeNode selectedNode;
    DefaultMutableTreeNode rootNode;
    DefaultMutableTreeNode RootNode;
    DefaultTreeModel model;
    TreePath clientsTreePath;
    MutableTreeNode tempNode;
    /// JTable
    JTable offersTable;
    DefaultTableModel defaultTableModel;
    int indexOfSelectedRow;
    /////////Create components for AddClientFrame
    JFrame addClientFrame;
    JPanel panel1;
    JPanel panel2;
    JLabel companyName;
    JLabel companyNip;
    JLabel cityName;
    JLabel companyPostCode;
    JTextField companyNameTextField;
    JTextField companyNipTextField;
    JTextField cityNameTextField;
    JFormattedTextField companyPostCodeFTextField;

    JButton createAndExtButton;
    JButton createButton;
    JButton exitButton;

    Dimension FrameSize;
    /////////Create components for AddOfferFrame
    JFrame addOfferFrame;
    Dimension addOfferFrameSize;
    JPanel panel1AddOffer;
    JPanel panel11AddOffer;
    JPanel panel12AddOffer;
    JPanel panel2AddOffer;
    JPanel panel3AddOffer;

    JLabel offerNrLabelAddOffer;
    JLabel clientLabelAddOffer;
    JLabel titleLabelAddOffer;
    JLabel dateOfOfferLabelAddOffer;
    JLabel timeToExecutionLabelAddOffer;
    JLabel priceLabelAddOffer;
    JLabel descriptionLabelAddOffer;

    JTextField offerNrTextFieldAddOffer;
    JTextField clientTextFieldAddOffer;
    JTextField titleTextFieldAddOffer;
    JFormattedTextField dateOfOfferTextFieldAddOffer;
    JTextField timeToExecutionTextFieldAddOffer;
    JTextField priceTextFieldAddOffer;
    JTextArea descriptionTextAreaAddOffer;
    JButton saveButtonAddOffer;
    JButton exitButtonAddOffer;
    static int offerCounter = 0;
    DecimalFormat nrOfeFormat = new DecimalFormat("0000");
    //////////////
    OrderManagementFrame() throws ParseException {
        this.setPreferredSize(new Dimension(1000, 1000));
        this.setMinimumSize(new Dimension(800, 400));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        //Top panel

        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setPreferredSize(new Dimension(2000, 70));

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.getWidth();
        screenHeight = screenSize.getHeight();

        clientManagePanel = new JPanel();
        clientManagePanel.setLayout(new BoxLayout(clientManagePanel, BoxLayout.X_AXIS));
        clientManagePanel.setPreferredSize(new Dimension(300, 70));
        clientManagePanel.setMaximumSize(new Dimension(300, 70));


        offerManagePanel = new JPanel();
        offerManagePanel.setLayout(new BoxLayout(offerManagePanel, BoxLayout.X_AXIS));
        offerManagePanel.setPreferredSize(new Dimension(84, 70));
        offerManagePanel.setMaximumSize(new Dimension((int) (screenWidth - 300), 70));
        offerManagePanel.setBackground(new Color(242, 242, 242));


        buttonAddClient = new JButton("Add Client");
        buttonAddClient.setPreferredSize(new Dimension(150, 70));
        buttonAddClient.setMaximumSize(new Dimension(150, 70));
        buttonAddClient.addActionListener(this);

        buttonRemoveClient = new JButton("Remove Client");
        buttonRemoveClient.setPreferredSize(new Dimension(150, 70));
        buttonRemoveClient.setMaximumSize(new Dimension(150, 70));
        buttonRemoveClient.addActionListener(this);

        buttonAddOffer = new JButton("Add Offer");
        buttonAddOffer.setPreferredSize(new Dimension(150, 70));
        buttonAddOffer.setMaximumSize(new Dimension(150, 70));
        buttonAddOffer.addActionListener(this);

        buttonEditOffer = new JButton("Edit Offer");
        buttonEditOffer.setPreferredSize(new Dimension(150, 70));
        buttonEditOffer.setMaximumSize(new Dimension(150, 70));
        buttonEditOffer.addActionListener(this);

        buttonRemoveOffer = new JButton("Remove Offer");
        buttonRemoveOffer.setPreferredSize(new Dimension(150, 70));
        buttonRemoveOffer.setMaximumSize(new Dimension(150, 70));
        buttonRemoveOffer.addActionListener(this);

        clientManagePanel.add(buttonAddClient);
        clientManagePanel.add(buttonRemoveClient);

        offerManagePanel.add(Box.createRigidArea(new Dimension(50, 70)));
        offerManagePanel.add(buttonAddOffer);
        offerManagePanel.add(buttonEditOffer);
        offerManagePanel.add(buttonRemoveOffer);

        topPanel.add(clientManagePanel);
        topPanel.add(offerManagePanel);
        topPanel.setBackground(Color.blue);
        //West panel
        westPanel = new JPanel();
        westPanel.setPreferredSize(new Dimension(300, (int) screenHeight - 70));
        westPanel.setLayout(new GridLayout());

        rootNode = new DefaultMutableTreeNode("All Clients");
        clientsTree = new JTree(rootNode);
        clientsTree.addTreeSelectionListener(new TreeSelectionListener(){
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                selectedNode = (DefaultMutableTreeNode)
                        clientsTree.getLastSelectedPathComponent();
            }
        });
        westPanel.add(clientsTree);
        ///CenterPanel
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        defaultTableModel= new DefaultTableModel();
        defaultTableModel.addColumn("Nr OFE");
        defaultTableModel.addColumn("Client");
        defaultTableModel.addColumn("Title");
        defaultTableModel.addColumn("Date of offer");
        defaultTableModel.addColumn("Date of execution");
        defaultTableModel.addColumn("Price");
        offersTable = new JTable(defaultTableModel);
        offersTable.setDefaultEditor(Object.class, null);
        offersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                indexOfSelectedRow=offersTable.getSelectedRow();
            }
        });
        offersTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(offersTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(topPanel, BorderLayout.NORTH);
        this.add(westPanel, BorderLayout.WEST);
        this.add(centerPanel, BorderLayout.CENTER);

        this.setVisible(true);
        ///////////////////AddClientFrame//////////////////////
        addClientFrame = new JFrame();
        addClientFrame.setLayout(new BorderLayout());
        addClientFrame.setMinimumSize(new Dimension(400, 400));
        FrameSize = addClientFrame.getSize();
        addClientFrame.setResizable(true);
        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(4, 2));
        panel1.setPreferredSize(new Dimension((int) FrameSize.getWidth(), (int) (0.85 * FrameSize.getHeight())));
        panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension((int) FrameSize.getWidth(), (int) (0.15 * FrameSize.getHeight())));
        panel2.setBackground(Color.red);
        panel2.setLayout(new GridLayout(1, 3));

        companyName = new JLabel("Company name:    ", SwingConstants.RIGHT);
        companyNameTextField = new JTextField();

        companyNip = new JLabel("Client  nip:    ", SwingConstants.RIGHT);
        MaskFormatter maskFormatterNiP = new MaskFormatter("### ### ## ##");
        companyNipTextField = new JFormattedTextField(maskFormatterNiP);
        companyNipTextField.setColumns(10);
        cityName = new JLabel("City :    ", SwingConstants.RIGHT);
        cityNameTextField = new JTextField();

        companyPostCode = new JLabel("Company postcode:    ", SwingConstants.RIGHT);
        MaskFormatter maskFormatterPostCode = new MaskFormatter("##-###");
        companyPostCodeFTextField = new JFormattedTextField(maskFormatterPostCode);
        companyPostCodeFTextField.setColumns(5);

        createAndExtButton = new JButton("Create & Exit");
        createAndExtButton.addActionListener(this);
        createButton = new JButton("Create");
        createButton.addActionListener(this);
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        panel1.add(companyName);
        panel1.add(companyNameTextField);
        panel1.add(companyNip);
        panel1.add(companyNipTextField);
        panel1.add(cityName);
        panel1.add(cityNameTextField);
        panel1.add(companyPostCode);
        panel1.add(companyPostCodeFTextField);

        panel2.add(createButton);
        panel2.add(createAndExtButton);
        panel2.add(exitButton);

        addClientFrame.add(panel1, BorderLayout.CENTER);
        addClientFrame.add(panel2, BorderLayout.SOUTH);
        addClientFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        clients = new ArrayList<>();
        //////////////////////AddOfferFrame///////////////////////
        addOfferFrame = new JFrame();
        addOfferFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addOfferFrame.setLayout(new BorderLayout());
        addOfferFrame.setMinimumSize(new Dimension(600, 600));
        addOfferFrameSize = addOfferFrame.getSize();
        addOfferFrame.setResizable(true);

        panel1AddOffer = new JPanel();
        panel1AddOffer.setLayout(new BorderLayout());
        panel1AddOffer.setPreferredSize(new Dimension((int) addOfferFrameSize.getWidth(), (int) (0.5 * addOfferFrameSize.getHeight())));
        panel1AddOffer.setBackground(Color.red);

        panel11AddOffer = new JPanel();
        panel11AddOffer.setLayout(new GridLayout(6,1));
        panel11AddOffer.setPreferredSize(new Dimension((int)(0.3* addOfferFrameSize.getWidth()),panel1AddOffer.getHeight()));

        panel12AddOffer = new JPanel();
        panel12AddOffer.setLayout(new GridLayout(6,1));
        panel12AddOffer.setPreferredSize(new Dimension((int)(0.7* addOfferFrameSize.getWidth()),panel1AddOffer.getHeight()));

        panel2AddOffer = new JPanel();
        panel2AddOffer.setLayout(new BorderLayout());
        panel2AddOffer.setPreferredSize(new Dimension((int) addOfferFrameSize.getWidth(), (int) (0.45 * addOfferFrameSize.getHeight())));

        panel3AddOffer = new JPanel();
        panel3AddOffer.setLayout(new GridLayout(1,2));
        panel3AddOffer.setPreferredSize(new Dimension((int) addOfferFrameSize.getWidth(), (int) (0.05 * addOfferFrameSize.getHeight())));
        panel3AddOffer.setBackground(Color.yellow);

        offerNrLabelAddOffer = new JLabel("Offer nr: ",SwingConstants.RIGHT);
        offerNrTextFieldAddOffer = new JTextField();
        offerNrTextFieldAddOffer.setEditable(false);

        clientLabelAddOffer = new JLabel("Client: ",SwingConstants.RIGHT);
        clientTextFieldAddOffer = new JTextField();
        clientTextFieldAddOffer.setEditable(false);
        titleLabelAddOffer = new JLabel(" Title:",SwingConstants.RIGHT);
        titleTextFieldAddOffer = new JTextField();

        dateOfOfferLabelAddOffer = new JLabel ("Offer date: ",SwingConstants.RIGHT);
        MaskFormatter maskFormatterDateFormat = new MaskFormatter("####-##-##");
        dateOfOfferTextFieldAddOffer = new JFormattedTextField(maskFormatterDateFormat);
        dateOfOfferTextFieldAddOffer.setColumns(12);

        timeToExecutionLabelAddOffer = new JLabel(" Execution time (weeks) : ",SwingConstants.RIGHT);
        timeToExecutionTextFieldAddOffer = new JTextField();
        timeToExecutionTextFieldAddOffer.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });
        priceLabelAddOffer = new JLabel("Price($):",SwingConstants.RIGHT);
        priceTextFieldAddOffer = new JTextField();
        priceTextFieldAddOffer.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });
        descriptionLabelAddOffer = new JLabel("Description:");
        descriptionLabelAddOffer.setPreferredSize(new Dimension((int)addOfferFrameSize.getWidth(),30));
        descriptionTextAreaAddOffer = new JTextArea();

        saveButtonAddOffer = new JButton("Save");
        saveButtonAddOffer.addActionListener(this);
        exitButtonAddOffer = new JButton("Exit");
        exitButtonAddOffer.addActionListener(this);
        addOfferFrame.add(panel1AddOffer,BorderLayout.NORTH);
        addOfferFrame.add(panel2AddOffer,BorderLayout.CENTER);
        addOfferFrame.add(panel3AddOffer,BorderLayout.SOUTH);
        panel1AddOffer.add(panel11AddOffer,BorderLayout.WEST);
        panel11AddOffer.add(offerNrLabelAddOffer);
        panel11AddOffer.add(clientLabelAddOffer);
        panel11AddOffer.add(titleLabelAddOffer);
        panel11AddOffer.add(dateOfOfferLabelAddOffer);
        panel11AddOffer.add(timeToExecutionLabelAddOffer);
        panel11AddOffer.add(priceLabelAddOffer);
        panel12AddOffer.add(offerNrTextFieldAddOffer);
        panel12AddOffer.add(clientTextFieldAddOffer);
        panel12AddOffer.add(titleTextFieldAddOffer);
        panel12AddOffer.add(dateOfOfferTextFieldAddOffer);
        panel12AddOffer.add(timeToExecutionTextFieldAddOffer);
        panel12AddOffer.add(priceTextFieldAddOffer);
        panel1AddOffer.add(panel12AddOffer,BorderLayout.CENTER);
        panel2AddOffer.add(descriptionLabelAddOffer,BorderLayout.NORTH);
        panel2AddOffer.add(descriptionTextAreaAddOffer,BorderLayout.CENTER);
        panel3AddOffer.add(saveButtonAddOffer);
        panel3AddOffer.add(exitButtonAddOffer);

        offers = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // OrderManagementFrame
        if (e.getSource() == buttonAddClient) {
            addClientFrame.setVisible(true);
            this.setEnabled(false);
            setDefaultAddClientFrame();
        }
        if(e.getSource()== buttonRemoveClient){
            if(selectedNode == rootNode|| selectedNode == null){
                JOptionPane.showMessageDialog(null,"Select client");
            }else{
            clients.remove(rootNode.getIndex(selectedNode));
            rootNode.remove(rootNode.getIndex(selectedNode));
            model.reload();
            }
        }
        if(e.getSource()== buttonAddOffer){
            if(clients.size() == 0){
                JOptionPane.showMessageDialog(null,"Add first client!","Message",JOptionPane.WARNING_MESSAGE);
            }
            else if(selectedNode == null || selectedNode == rootNode){
                JOptionPane.showMessageDialog(null,"Select correct client from tree","Message",JOptionPane.WARNING_MESSAGE);
            }else {
                addOfferFrame.setVisible(true);
                this.setEnabled(false);
                System.out.println(selectedNode);
                clientTextFieldAddOffer.setText(selectedNode.toString());
                offerNrTextFieldAddOffer.setText("OFE "+ nrOfeFormat.format(offerCounter));
                setDefaultAddOfferFrame();

            }
        }
        if (e.getSource() == buttonEditOffer) {
            // In the future, I will do it.
        }
        if(e.getSource()== buttonRemoveOffer){
            if(indexOfSelectedRow == -1){
                JOptionPane.showMessageDialog(null,"Select offer.");
            }else{
                offers.remove(indexOfSelectedRow);
                System.out.println(indexOfSelectedRow);
                defaultTableModel.removeRow(indexOfSelectedRow);
            }
        }

        //***********************************************//
        //AddClientFrameEvents//
        if (e.getSource() == createButton) {
            if(!(companyNameTextField.getText().equals("Name")||companyNipTextField.getText().equals("000 000 00 00")||cityNameTextField.getText().equals("City")||companyPostCodeFTextField.getText().equals("00-000"))) {
                if(!(clientExist((companyNameTextField.getText()),companyNip.getText()))) {
                    tempClient = new Client();
                    tempClient.setCompanyName(companyNameTextField.getText());
                    tempClient.setCompanyNip(companyNipTextField.getText());
                    tempClient.setCityName(cityNameTextField.getText());
                    tempClient.setPostCode(companyPostCodeFTextField.getText());
                    System.out.println("Added");
                    clients.add(tempClient);
                    addNode(tempClient.getCompanyName());
                    setDefaultAddClientFrame();
                }else{
                    JOptionPane.showMessageDialog(null,"Client already exists","Message",JOptionPane.WARNING_MESSAGE);
                }
            }else if ((companyNameTextField.getText().equals("Name")||companyNipTextField.getText().equals("000 000 00 00")||cityNameTextField.getText().equals("City")||companyPostCodeFTextField.getText().equals("00-000"))){
                JOptionPane.showMessageDialog(null,"Enter correct data","Message",JOptionPane.WARNING_MESSAGE);
            }
        }
        if (e.getSource() == createAndExtButton) {
            if(!(companyNameTextField.getText().equals("Name")||companyNipTextField.getText().equals("000 000 00 00")||cityNameTextField.getText().equals("City")||companyPostCodeFTextField.getText().equals("00-000"))) {
                if(!(clientExist((companyNameTextField.getText()),companyNip.getText()))) {
                    tempClient = new Client();
                    tempClient.setCompanyName(companyNameTextField.getText());
                    tempClient.setCompanyNip(companyNipTextField.getText());
                    tempClient.setCityName(cityNameTextField.getText());
                    tempClient.setPostCode(companyPostCodeFTextField.getText());
                    System.out.println("Added");
                    clients.add(tempClient);
                    addNode(tempClient.getCompanyName());
                    addClientFrame.dispose();
                    Collections.sort(clients);
                    this.setEnabled(true);
                    this.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null,"Client already exists.","Message",JOptionPane.WARNING_MESSAGE);
                }

            }else if ((companyNameTextField.getText().equals("Name")||companyNipTextField.getText().equals("000 000 00 00")||cityNameTextField.getText().equals("City")||companyPostCodeFTextField.getText().equals("00-000"))){
                JOptionPane.showMessageDialog(null,"Enter correct data","Message",JOptionPane.WARNING_MESSAGE);
            }
        }
        if (e.getSource() == exitButton) {
            Collections.sort(clients);
            addClientFrame.dispose();
            this.setEnabled(true);
            this.setVisible(true);
        }
        /// AddOfferFrameEvents
        if(e.getSource() == saveButtonAddOffer){

            if(!(titleTextFieldAddOffer.getText().equals("Title")|| titleTextFieldAddOffer.getText().equals("") || Integer.parseInt(timeToExecutionTextFieldAddOffer.getText())<=0 || Integer.parseInt(priceTextFieldAddOffer.getText())<=0 )){
                tempOffer = new Offer(selectedNode.toString());
                try {
                    tempOffer.setDateOfOffer(LocalDate.parse(dateOfOfferTextFieldAddOffer.getText()),Integer.parseInt(timeToExecutionTextFieldAddOffer.getText()));
                    tempOffer.setNrOfe(offerNrTextFieldAddOffer.getText());
                    tempOffer.setTitle(titleTextFieldAddOffer.getText());
                    tempOffer.setPrice(Double.parseDouble(priceTextFieldAddOffer.getText()));
                    tempOffer.setDescription(descriptionTextAreaAddOffer.getText());
                    offers.add(tempOffer);
                    offerCounter++;
                    offerNrTextFieldAddOffer.setText("OFE "+ nrOfeFormat.format(offerCounter));
                    setDefaultAddOfferFrame();
                }catch (Exception e1){
                    JOptionPane.showMessageDialog(null,e1);
                }

            }else{
                JOptionPane.showMessageDialog(null,"Enter correct data","Message",JOptionPane.WARNING_MESSAGE);
            }

        }
        if(e.getSource() == exitButtonAddOffer){
            addOfferFrame.dispose();
            this.setEnabled(true);
            this.setVisible(true);
            createTableData(offers);
            offersTable.repaint();
        }
    }

    // setting default values in textFields
    public void setDefaultAddClientFrame(){
        companyNameTextField.setText("Name");
        companyNipTextField.setText("0000000000");
        cityNameTextField.setText("City");
        companyPostCodeFTextField.setText("00000");
    }
    // Check that entered data are already created
    public boolean clientExist (String companyName, String companyNip){
        int l = clients.size();
        boolean clientExist = false;
        for (int i =0;i<l;i++){
            if(clients.get(i).getCompanyName().equals(companyName)|| clients.get(i).getCompanyNip().equals(companyNip)){
                clientExist = true;
            }
        }
        return clientExist;
    }
    // temp method to show actual clients- only for tests
    public void showClients(){
        int l = clients.size();
        for (int i =0;i<l;i++){
            System.out.println("???????????????????????????????/n");
            System.out.println(clients.get(i).getCompanyName());
            System.out.println(clients.get(i).getCompanyNip());
            System.out.println(clients.get(i).getCityName());
            System.out.println(clients.get(i).getPostCode());
        }
        System.out.println(clients.size());
        System.out.println("End");
    }
    // add and sort nodes to JTree
    public void addNode(String tempName){
        model = (DefaultTreeModel) clientsTree.getModel();
        RootNode = new DefaultMutableTreeNode(tempName);
        clientsTreePath = clientsTree.getNextMatch("A", 0, Position.Bias.Forward);
        tempNode = (MutableTreeNode)clientsTreePath.getLastPathComponent();
        model.insertNodeInto(RootNode, tempNode, tempNode.getChildCount());
        clientsTree.expandPath(clientsTreePath);
        //Sort nodes by name
        int nrOfNodes = rootNode.getChildCount();
        if(nrOfNodes>1){
            Map<String, DefaultMutableTreeNode> nameNodeMap = new HashMap(nrOfNodes);
            for (int i = 0; i < nrOfNodes; i++) {
                DefaultMutableTreeNode child = (DefaultMutableTreeNode) rootNode.getChildAt(i);
                nameNodeMap.put(child.toString().toUpperCase(Locale.ROOT), child);
            }
            ArrayList<String> names = new ArrayList(nameNodeMap.keySet());
            Collections.sort(names);
            for (DefaultMutableTreeNode node : nameNodeMap.values()) {
                model.removeNodeFromParent(node);
            }
            for (int i = 0; i < names.size(); i++) {
                model.insertNodeInto(nameNodeMap.get(names.get(i)), rootNode, i);
            }
            model.reload();
        }
    }
    //set default text in addOfferFrame
    public void setDefaultAddOfferFrame(){
        titleTextFieldAddOffer.setText("Title");
        dateOfOfferTextFieldAddOffer.setText("20220101");
        descriptionTextAreaAddOffer.setText("Add description");
        timeToExecutionTextFieldAddOffer.setText("1");
        priceTextFieldAddOffer.setText("0000");
    }
    // Add offers to table
    public void createTableData(ArrayList<Offer> offersTemp){
        int offerCounter = offersTemp.size()-1;
        int rowCount = defaultTableModel.getRowCount();
        for( int i = rowCount; i<=offerCounter;i++){
            defaultTableModel.addRow(new Object[]{offersTemp.get(i).getNrOfe(),offersTemp.get(i).getClient(),offersTemp.get(i).getTitle(),offersTemp.get(i).getDateOfOffer().toString(),offersTemp.get(i).getDateOfExecution().toString(),Double.toString(offersTemp.get(i).getPrice())});
        }
    }
}