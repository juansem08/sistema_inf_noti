package notificaciones;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AppWindow extends JFrame {
    private JComboBox<String> comboSituacion, comboMedio;
    private JTextField txtDestinatario, txtExtra;
    private JLabel lblExtra, lblStats;
    private JTextArea txtMensaje;
    private JTable tableRegistro;
    private DefaultTableModel tableModel;
    private NotificationService service;
    private List<Notification> log;

    // Colores Institucionales
    private final Color PRIMARY = new Color(26, 35, 126);
    private final Color ACCENT = new Color(0, 150, 136);
    private final Color BG_BODY = new Color(242, 244, 246);

    public AppWindow() {
        setupDefaults();
        service = new NotificationService();
        log = new ArrayList<>();
        
        setTitle("Sistema de Notificaciones Universitarias - Gestión Centralizada");
        setSize(1100, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));

        // 1. HEADER (Jerarquía 1)
        add(createHeader(), BorderLayout.NORTH);

        // 2. CONTENEDOR PRINCIPAL
        JPanel mainContent = new JPanel(new BorderLayout(0, 20));
        mainContent.setBackground(BG_BODY);
        mainContent.setBorder(new EmptyBorder(25, 30, 25, 30));

        // 2.1 PANEL DE ACCIÓN (Jerarquía 2 - El Formulario)
        mainContent.add(createActionPanel(), BorderLayout.NORTH);

        // 2.2 PANEL DE DATOS (Jerarquía 3 - El Registro)
        mainContent.add(createRegistryPanel(), BorderLayout.CENTER);

        add(mainContent, BorderLayout.CENTER);

        // 3. BARRA DE ESTADO (Jerarquía 4)
        add(createFooter(), BorderLayout.SOUTH);

        updateExtraLabel();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY);
        header.setPreferredSize(new Dimension(1100, 70));
        header.setBorder(new EmptyBorder(0, 30, 0, 30));

        JLabel title = new JLabel("PANEL DE GESTIÓN ACADÉMICA");
        title.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);

        JLabel date = new JLabel(new SimpleDateFormat("EEEE, dd 'de' MMMM").format(new java.util.Date()));
        date.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        date.setForeground(new Color(255, 255, 255, 180));
        header.add(date, BorderLayout.EAST);
        return header;
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            new EmptyBorder(25, 25, 25, 25)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // SECCIÓN A: DESTINATARIO Y MENSAJE
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.5;
        card.add(createSectionLabel("INFORMACIÓN DEL MENSAJE"), gbc);
        
        gbc.gridy = 1;
        card.add(createInputLabel("Nombre del Alumno / Destinatario:"), gbc);
        gbc.gridy = 2;
        txtDestinatario = new JTextField();
        card.add(txtDestinatario, gbc);

        gbc.gridy = 3;
        card.add(createInputLabel("Mensaje de la Notificación:"), gbc);
        gbc.gridy = 4; gbc.gridheight = 3; gbc.fill = GridBagConstraints.BOTH;
        txtMensaje = new JTextArea(4, 20);
        txtMensaje.setLineWrap(true);
        card.add(new JScrollPane(txtMensaje), gbc);

        // SECCIÓN B: CONFIGURACIÓN DE ENVÍO
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridheight = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(createSectionLabel("CONFIGURACIÓN DE ENVÍO"), gbc);

        gbc.gridy = 1;
        card.add(createInputLabel("Tipo de Situación:"), gbc);
        gbc.gridy = 2;
        comboSituacion = new JComboBox<>(new String[]{
            "Publicación de calificaciones", "Recordatorio de pago", "Aviso de cancelación", "Inscripción a eventos"
        });
        card.add(comboSituacion, gbc);

        gbc.gridy = 3;
        card.add(createInputLabel("Medio de Comunicación:"), gbc);
        gbc.gridy = 4;
        comboMedio = new JComboBox<>(new String[]{"Email", "SMS", "Push"});
        comboMedio.addActionListener(e -> updateExtraLabel());
        card.add(comboMedio, gbc);

        gbc.gridy = 5;
        lblExtra = createInputLabel("Detalle del Medio:");
        card.add(lblExtra, gbc);
        gbc.gridy = 6;
        txtExtra = new JTextField();
        card.add(txtExtra, gbc);

        // BOTÓN DE ACCIÓN (Abajo centrado entre las dos columnas)
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; gbc.insets = new Insets(25, 100, 0, 100);
        JButton btnSend = new JButton("REALIZAR ENVÍO REGISTRADO") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ACCENT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        btnSend.setContentAreaFilled(false); btnSend.setBorderPainted(false); btnSend.setFocusPainted(false);
        btnSend.setForeground(Color.WHITE); btnSend.setFont(new Font("Segoe UI Bold", Font.PLAIN, 15));
        btnSend.setPreferredSize(new Dimension(0, 50));
        btnSend.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSend.addActionListener(e -> handleSend());
        card.add(btnSend, gbc);

        panel.add(card, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createRegistryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        JLabel title = new JLabel("REGISTRO DE AUDITORÍA Y SEGUIMIENTO");
        title.setFont(new Font("Segoe UI Bold", Font.PLAIN, 13));
        title.setForeground(new Color(100, 100, 100));
        title.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.add(title, BorderLayout.NORTH);

        String[] cols = {"CÓDIGO", "FECHA", "SITUACIÓN", "DESTINATARIO", "MEDIO", "ESTADO"};
        tableModel = new DefaultTableModel(cols, 0);
        tableRegistro = new JTable(tableModel);
        styleTable();

        JScrollPane scroll = new JScrollPane(tableRegistro);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        scroll.getViewport().setBackground(Color.WHITE);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JLabel createSectionLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI Black", Font.PLAIN, 11));
        l.setForeground(PRIMARY);
        l.setBorder(new EmptyBorder(0, 0, 5, 0));
        return l;
    }

    private JLabel createInputLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        l.setForeground(new Color(80, 80, 80));
        return l;
    }

    private void styleTable() {
        tableRegistro.setRowHeight(40);
        tableRegistro.setShowGrid(false);
        tableRegistro.setIntercellSpacing(new Dimension(0, 0));
        tableRegistro.setSelectionBackground(new Color(BG_BODY.getRed(), BG_BODY.getGreen(), BG_BODY.getBlue()));
        
        JTableHeader h = tableRegistro.getTableHeader();
        h.setPreferredSize(new Dimension(0, 35));
        h.setBackground(Color.WHITE);
        h.setFont(new Font("Segoe UI Bold", Font.PLAIN, 12));
        h.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for(int i : new int[]{0, 1, 4, 5}) tableRegistro.getColumnModel().getColumn(i).setCellRenderer(center);
    }

    private JPanel createFooter() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 10));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)));
        lblStats = new JLabel("Envíos registrados en esta sesión: 0");
        lblStats.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        p.add(lblStats);
        return p;
    }

    private void setupDefaults() {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("ComboBox.font", new Font("Segoe UI", Font.PLAIN, 14));
    }

    private void updateExtraLabel() {
        String m = (String) comboMedio.getSelectedItem();
        lblExtra.setText(m.equals("Email") ? "Correo Electrónico:" : m.equals("SMS") ? "Número de Teléfono:" : "Token de Dispositivo:");
    }

    private void handleSend() {
        try {
            String sit = (String) comboSituacion.getSelectedItem();
            String med = ((String) comboMedio.getSelectedItem()).toLowerCase();
            String dst = txtDestinatario.getText();
            String msg = txtMensaje.getText();
            String ext = txtExtra.getText();
            
            if (dst.isEmpty() || msg.isEmpty() || ext.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe completar todos los datos para registrar el envío.", "Validación", 2);
                return;
            }

            String id = "REG-" + (log.size() + 200);
            Notification n = (med.equals("email")) ? 
                NotificationFactory.createNotification(med, id, dst, msg, ext, sit) : 
                NotificationFactory.createNotification(med, id, dst, msg, ext);

            service.sendNotification(n); log.add(n);
            
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
            tableModel.insertRow(0, new Object[]{n.getCode(), df.format(n.getSendDate()), sit, n.getRecipient(), med.toUpperCase(), "ENVIADO"});
            
            lblStats.setText("Envíos registrados en esta sesión: " + log.size());
            txtDestinatario.setText(""); txtMensaje.setText(""); txtExtra.setText("");
            
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", 0); }
    }
}
