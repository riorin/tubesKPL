/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Frmpenjualan.java
 *
 * Created on 07 Des 11, 14:13:25
 */
package Forms;

import cls.ClassDB;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bagong
 */
public final class FrmPenjualan extends javax.swing.JInternalFrame {

    private DefaultTableModel model;
    public FrmPenjualan() {
        initComponents();
        loadcombodata();
        model=new DefaultTableModel();
        tblPenjualan.setModel(model);
        model.addColumn("No. Transaksi");
        model.addColumn("Tgl. Transaksi");  
        model.addColumn("Kode Barang");
        model.addColumn("Harga");
        model.addColumn("Jumlah");
        model.addColumn("Total Harga");
        loadData();
    }
    
public void loadcombodata(){
    try {        
        TxtKodeBarang.removeAllItems();
        
            Connection c=ClassDB.getkoneksi();
            Statement s= c.createStatement();
            String sql="Select * from barang";
            ResultSet r=s.executeQuery(sql);
            
            while (r.next()) {
                TxtKodeBarang.addItem(r.getString("kd_barang"));
            }
            r.close();
            s.close();
        }catch(SQLException e) {
            System.out.println("Terjadi kesalahan");
        }    
    
    
    
}
public void loadData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try {            
            Connection c=ClassDB.getkoneksi();
            Statement s= c.createStatement();
            String sql="Select * from penjualan where no_transaksi='" + this.TxtNoTransaksi.getText() + "'";
            ResultSet r=s.executeQuery(sql);
            
            while (r.next()) {
                Object[] o=new Object[6];
                o[0]=r.getString("no_transaksi");
                o[1]=r.getString("tgl_transaksi");               
                o[2]=r.getString("kd_barang");
                o[3]=r.getString("harga");
                o[4]=r.getString("jumlah");                
                o[5]=r.getString("totalharga");
                
                model.addRow(o);
            }
            r.close();
            s.close();
            
        }catch(SQLException e) {
            System.out.println("Terjadi kesalahan");
        }
    }
public void updatebarang(String jml,String kode) {
    String sql1="Update barang Set stok=stok - ? where kd_barang=?";
    try {        
    Connection c=ClassDB.getkoneksi();            
            PreparedStatement st=(PreparedStatement) c.prepareStatement(sql1);
            st.setString(1, jml);
            st.setString(2, kode);
            st.executeUpdate();
            st.close();
            
    }catch(SQLException e) {
        System.out.println(e);
    }
}

public void TambahData() {
    int q1=Integer.parseInt(this.TxtJumlah.getText());
        int q2=Integer.parseInt(this.TxtStok.getText());
        if (q1 <= q2)
        {
   String kode=this.TxtNoTransaksi.getText();
    java.util.Date tgl=(java.util.Date) this.TxtTglTransaksi.getDate();

    String KodeBar=(String) this.TxtKodeBarang.getSelectedItem();
    String Harga=this.TxtHarga.getText();
    String Jml=this.TxtJumlah.getText();
    String total=this.TxtTotalHarga.getText();
        try {
            String sql1="Select * from penjualan where no_transaksi='" + kode + "' AND kd_barang='" + KodeBar + "'";
            Connection c=ClassDB.getkoneksi();            
            PreparedStatement st=(PreparedStatement) c.prepareStatement(sql1);
            ResultSet rs=st.executeQuery(sql1);
            
            
            if (rs.next())
            {
            String sql = "UPDATE penjualan Set jumlah=jumlah + ?,totalharga= totalharga + ? where no_transaksi='" + kode + "' AND kd_barang='" + KodeBar + "'";
            PreparedStatement p2=(PreparedStatement) c.prepareStatement(sql);
            p2.setString(1, Jml);
            p2.setString(2, total);           
            p2.executeUpdate();
            p2.close();
            this.updatebarang(Jml, KodeBar);
            

            }else{
            String sql = "Insert into penjualan values (?,?,?,?,?,?)";
            PreparedStatement p=(PreparedStatement) c.prepareStatement(sql);
            p.setString(1, kode);
            p.setDate(2,  new java.sql.Date(tgl.getTime()));          
            p.setString(3, KodeBar);                        
            p.setString(4, Harga);                        
            p.setString(5, Jml);                        
            p.setString(6, total);                        
            p.executeUpdate();
            p.close();
            this.updatebarang(Jml, KodeBar);
            
            }
        }catch(SQLException e){
            System.out.println(e);
        }finally{
            loadData();
        }
        //////////////////////////////////////
     
         try {
           
            Connection c=ClassDB.getkoneksi();
            Statement s= c.createStatement();
            String sql = "SELECT Sum(penjualan.totalharga) AS subtotal FROM penjualan WHERE no_transaksi=?";
            PreparedStatement p=(PreparedStatement) c.prepareStatement(sql);
            p.setString(1, kode);
            ResultSet result=p.executeQuery();
            result.next();
           Txtsub.setText(result.getString("subtotal"));
            
            }catch(SQLException e){
            System.out.println(e);        
         }
         
         
        }else{
          TxtJumlah.setText(null);
          notifikasiLabel.setText("Jumlah stok dari barang "+this.TxtKodeBarang.getSelectedItem()+" tinggal tersisa "+TxtStok.getText()+" unit!");
        }
    
}
public void HapusText() {
    this.TxtNoTransaksi.setText(null);
    this.TxtHarga.setText(null);
    this.TxtJumlah.setText(null);
    this.TxtTotalHarga.setText(null);
    this.Txtsub.setText(null);
    this.TxtStok.setText(null);
    this.Txtbayar.setText(null);
    this.tblPenjualan.columnRemoved(null);
}
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblPenjualan = new javax.swing.JTable();
        panelWarnaTransparan21 = new Tampilan.PanelWarnaTransparan2();
        jLabel9 = new javax.swing.JLabel();
        Txtsub = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        Txtkembalian = new javax.swing.JLabel();
        Txtbayar = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        panelWarnaTransparan22 = new Tampilan.PanelWarnaTransparan2();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        TxtNoTransaksi = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        TxtTglTransaksi = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        TxtKodeBarang = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        TxtJumlah = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        TxtTotalHarga = new javax.swing.JLabel();
        TxtStok = new java.awt.Label();
        notifikasiLabel = new java.awt.Label();
        TxtHarga = new java.awt.Label();

        setTitle("TRANSAKSI PENJUALAN");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblPenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblPenjualan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPenjualanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPenjualan);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 830, 190));

        panelWarnaTransparan21.setBackground(new java.awt.Color(51, 51, 255));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Sub Total");

        Txtsub.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Txtsub.setText("Rp.");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Bayar");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Kembali");

        Txtkembalian.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Txtkembalian.setText("Rp.");

        Txtbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtbayarActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Print.png"))); // NOI18N
        jButton2.setText("Print");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Tampilan/Close.png"))); // NOI18N
        jButton5.setText("Exit");
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelWarnaTransparan21Layout = new javax.swing.GroupLayout(panelWarnaTransparan21);
        panelWarnaTransparan21.setLayout(panelWarnaTransparan21Layout);
        panelWarnaTransparan21Layout.setHorizontalGroup(
            panelWarnaTransparan21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelWarnaTransparan21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Txtsub, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(panelWarnaTransparan21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelWarnaTransparan21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Txtkembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Txtbayar, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(28, 28, 28)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );
        panelWarnaTransparan21Layout.setVerticalGroup(
            panelWarnaTransparan21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelWarnaTransparan21Layout.createSequentialGroup()
                .addGroup(panelWarnaTransparan21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelWarnaTransparan21Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(Txtsub))
                    .addGroup(panelWarnaTransparan21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panelWarnaTransparan21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelWarnaTransparan21Layout.createSequentialGroup()
                            .addGroup(panelWarnaTransparan21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Txtbayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelWarnaTransparan21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Txtkembalian)))))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        getContentPane().add(panelWarnaTransparan21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 850, 70));

        panelWarnaTransparan22.setBackground(new java.awt.Color(51, 51, 255));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("TRANSAKSI PENJUALAN BARANG");

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Shopping_Cart_Add.png"))); // NOI18N
        jLabel13.setText("jLabel8");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel14.setText("TOKO HAJI MUHIDIN");

        javax.swing.GroupLayout panelWarnaTransparan22Layout = new javax.swing.GroupLayout(panelWarnaTransparan22);
        panelWarnaTransparan22.setLayout(panelWarnaTransparan22Layout);
        panelWarnaTransparan22Layout.setHorizontalGroup(
            panelWarnaTransparan22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelWarnaTransparan22Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelWarnaTransparan22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                    .addGroup(panelWarnaTransparan22Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelWarnaTransparan22Layout.setVerticalGroup(
            panelWarnaTransparan22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelWarnaTransparan22Layout.createSequentialGroup()
                .addGroup(panelWarnaTransparan22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelWarnaTransparan22Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14))
                    .addGroup(panelWarnaTransparan22Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        getContentPane().add(panelWarnaTransparan22, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, -1));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/New.png"))); // NOI18N
        jButton3.setText("New");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 90, 90, 32));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Add.png"))); // NOI18N
        jButton4.setText("Add");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 130, 90, 32));

        jLabel3.setText("No Transaksi");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, 23));

        TxtNoTransaksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtNoTransaksiKeyPressed(evt);
            }
        });
        getContentPane().add(TxtNoTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 112, -1));

        jLabel1.setText("Tgl Transaksi");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 90, 23));
        getContentPane().add(TxtTglTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 110, -1));

        jLabel4.setText("Kode Barang");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, 72, 23));

        TxtKodeBarang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        TxtKodeBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtKodeBarangActionPerformed(evt);
            }
        });
        getContentPane().add(TxtKodeBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, 80, -1));

        jLabel6.setText("Jumlah");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, 63, 23));

        TxtJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtJumlahKeyReleased(evt);
            }
        });
        getContentPane().add(TxtJumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 130, 80, -1));

        jLabel5.setText("Harga");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 100, 63, 23));

        jLabel7.setText("Total Harga");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 130, 90, 23));
        getContentPane().add(TxtTotalHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 130, 130, 20));
        getContentPane().add(TxtStok, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 120, 20, -1));

        notifikasiLabel.setForeground(new java.awt.Color(255, 51, 51));
        getContentPane().add(notifikasiLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 150, 400, -1));
        getContentPane().add(TxtHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 100, 130, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       
    }//GEN-LAST:event_jButton2ActionPerformed

    private void TxtKodeBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtKodeBarangActionPerformed
        int i=TxtKodeBarang.getSelectedIndex();
        if (i==-1)
        {
            return;
        }
         try {
             String nm=(String) TxtKodeBarang.getSelectedItem();
            Connection c=ClassDB.getkoneksi();
            Statement s= c.createStatement();
            String sql = "Select harga,stok from barang WHERE kd_barang=?";
            PreparedStatement p=(PreparedStatement) c.prepareStatement(sql);
            p.setString(1, nm);
            ResultSet result=p.executeQuery();
            result.next();
            TxtHarga.setText(result.getString("harga"));
           TxtStok.setText(result.getString("stok"));
            
            }catch(SQLException e){
            System.out.println(e);        
         }
    }//GEN-LAST:event_TxtKodeBarangActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
HapusText();        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void TxtJumlahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtJumlahKeyReleased
        int q1=Integer.parseInt(this.TxtJumlah.getText());
        int q2=Integer.parseInt(this.TxtStok.getText());
        if (q1 <= q2)
        {
       String qtys=TxtJumlah.getText();       
       String totals=TxtHarga.getText();
        double qtysd=Double.parseDouble(qtys);
        double totalsd=Double.parseDouble(totals);
        DecimalFormat df = new DecimalFormat("#.##");
        TxtTotalHarga.setText(df.format(qtysd * totalsd));
        }else{
          TxtJumlah.setText(null);
          notifikasiLabel.setText("Jumlah stok dari barang"+this.TxtKodeBarang.getSelectedItem()+" tinggal tersisa "+TxtStok.getText()+" unit!");
        }
    }//GEN-LAST:event_TxtJumlahKeyReleased

    private void tblPenjualanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPenjualanMouseClicked
        int i=tblPenjualan.getSelectedRow();
        
        if(i==-1)
        {
            return;
        }
        String kode=(String) model.getValueAt(i, 0);        
               
        String kodebar=(String) model.getValueAt(i, 2);
        String jml=(String) model.getValueAt(i, 4);
        
        String sql1="Update barang Set stok=stok + ? where kd_barang=?";
        String sql="Delete from penjualan where no_transaksi=? AND kd_barang=?";
    try {        
    Connection c=ClassDB.getkoneksi();            
            PreparedStatement st=(PreparedStatement) c.prepareStatement(sql1);
            st.setString(1, jml);
            st.setString(2, kodebar);
            st.executeUpdate();
            st.close();
            
            PreparedStatement st2=(PreparedStatement) c.prepareStatement(sql);
            st2.setString(1, kode);
            st2.setString(2, kodebar);
            st2.executeUpdate();
            st2.close();
            
    }catch(SQLException e) {
        System.out.println(e);
    }finally{
        loadData();
    }
    }//GEN-LAST:event_tblPenjualanMouseClicked

    private void TxtNoTransaksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtNoTransaksiKeyPressed
        loadData();
    }//GEN-LAST:event_TxtNoTransaksiKeyPressed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        TambahData();
        loadData();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
         this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void TxtbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtbayarActionPerformed
int q2=Integer.parseInt(this.Txtbayar.getText());
        int q1=Integer.parseInt(this.Txtsub.getText());
        if (q1 <= q2)
        {
       String byr=Txtbayar.getText();       
       String sub=Txtsub.getText();
        double bayar=Double.parseDouble(byr);
        double subtotal=Double.parseDouble(sub);
        DecimalFormat df = new DecimalFormat("#.##");
        Txtkembalian.setText(df.format(bayar - subtotal));
        }else{
          TxtJumlah.setText(null);
          notifikasiLabel.setText("Uang pembayaranya kurang, minimal "+Txtsub.getText());
        }
    }//GEN-LAST:event_TxtbayarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Label TxtHarga;
    private javax.swing.JTextField TxtJumlah;
    private javax.swing.JComboBox TxtKodeBarang;
    private javax.swing.JTextField TxtNoTransaksi;
    private java.awt.Label TxtStok;
    private com.toedter.calendar.JDateChooser TxtTglTransaksi;
    private javax.swing.JLabel TxtTotalHarga;
    private javax.swing.JTextField Txtbayar;
    private javax.swing.JLabel Txtkembalian;
    private javax.swing.JLabel Txtsub;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.Label notifikasiLabel;
    private Tampilan.PanelWarnaTransparan2 panelWarnaTransparan21;
    private Tampilan.PanelWarnaTransparan2 panelWarnaTransparan22;
    private javax.swing.JTable tblPenjualan;
    // End of variables declaration//GEN-END:variables
}
