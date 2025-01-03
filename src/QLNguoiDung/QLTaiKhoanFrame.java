package QLNguoiDung;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


import LoginAndSignUp.SignUp;
import Home.TrangChu;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Admin
 */
public class QLTaiKhoanFrame extends javax.swing.JFrame {
private String loaiTaiKhoan;
    /**
     * Creates new form QLTaiKhoanFrame
     */
    public QLTaiKhoanFrame(String loaiTaiKhoan) {
         this.loaiTaiKhoan = loaiTaiKhoan;
        initComponents();
        fetchData();
        
        // Đặt hành động khi nhấn X là đóng cửa sổ này mà không thoát ứng dụng
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        //Tim kiem
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                fetchData(); // Call fetchData when text is inserted
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fetchData(); // Call fetchData when text is removed
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not needed for plain text fields
            }
        });
    }

    private void fetchData() {
        var server = "localhost";
        var user = "sa";
        var password = "123456";
        var db = "QUANLY";
        var port = 1433;

        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser(user);
        ds.setPassword(password);
        ds.setDatabaseName(db);
        ds.setPortNumber(port);
        ds.setServerName(server);
        ds.setEncrypt(false);

        // Lấy cột tìm kiếm và từ khóa
        String selectedColumn = cbxTimKiem.getSelectedItem().toString();
        String keyword = txtTimKiem.getText().trim();

        // Xác định tên cột trong CSDL tương ứng với lựa chọn
        String dbColumn = switch (selectedColumn) {
            case "Mã tài khoản" ->
                "MaTaiKhoan";
            case "Tên đăng nhập" ->
                "TenDangNhap";
            case "Mật khẩu" ->
                "MatKhau";
            case "Email" ->
                "Email";
            case "Mã người dùng" ->
                "MaNguoiDung";
            case "Trạng thái" ->
                "TrangThai";
            case "Ngày tạo" ->
                "NgayTao";
            case "Loại tài khoản" ->
                "LoaiTaiKhoan";
            default ->
                "";
        };

        // Câu truy vấn SQL, thêm điều kiện LIKE nếu có từ khóa
        String query = "SELECT * FROM TAIKHOAN";
        if (!keyword.isEmpty() && !dbColumn.isEmpty()) {
            query += " WHERE " + dbColumn + " LIKE ?";
        }

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            if (!keyword.isEmpty() && !dbColumn.isEmpty()) {
                ps.setString(1, "%" + keyword + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) tableTaiKhoan.getModel();
                model.setRowCount(0);  // Xóa các hàng cũ

                // Duyệt qua từng dòng kết quả và thêm vào table model
                while (rs.next()) {
                    String maTaiKhoan = rs.getString("MaTaiKhoan");
                    String tenDangNhap = rs.getString("TenDangNhap");
                    String matKhau = rs.getString("MatKhau");
                    String email = rs.getString("Email");
                    String maNguoiDung = rs.getString("MaNguoiDung");
                    String trangThai = rs.getString("TrangThai");
                    Date ngayTao = rs.getDate("NgayTao");
                    String loaiTaiKhoan = rs.getString("LoaiTaiKhoan");

                    model.addRow(new Object[]{maNguoiDung, tenDangNhap, matKhau, email, maNguoiDung, trangThai, ngayTao, loaiTaiKhoan});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối CSDL!", "Error", JOptionPane.ERROR_MESSAGE);
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
        jLabel1 = new javax.swing.JLabel();
        btnDangKy = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        cbxTimKiem = new javax.swing.JComboBox<>();
        txtTimKiem = new javax.swing.JTextField();
        btnLamMoi = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTaiKhoan = new javax.swing.JTable();
        btnQLNguoiDung = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Quản lý tài khoản");

        btnDangKy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_add_40px.png"))); // NOI18N
        btnDangKy.setText("Đăng Ký");
        btnDangKy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangKyActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_delete_40px.png"))); // NOI18N
        btnXoa.setText("Xoá");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cbxTimKiem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã tài khoản", "Tên đăng nhập", "Mật khẩu", "Email", "Mã người dùng", "Trạng thái", "Ngày tạo", "Loại tài khoản" }));
        cbxTimKiem.setPreferredSize(new java.awt.Dimension(121, 22));

        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_reset_25px_1.png"))); // NOI18N
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(cbxTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLamMoi)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbxTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
        );

        tableTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã tài khoản", "Tên đăng nhập", "Mật khẩu", "Email", "Mã người dùng", "Trạng thái", "Ngày tạo", "Loại tài khoản"
            }
        ));
        jScrollPane1.setViewportView(tableTaiKhoan);

        btnQLNguoiDung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_edit_25px.png"))); // NOI18N
        btnQLNguoiDung.setText("Người dùng");
        btnQLNguoiDung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLNguoiDungActionPerformed(evt);
            }
        });

        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/rsz_back.png"))); // NOI18N
        btnBack.setText("Quay về");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnDangKy)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa)
                        .addGap(18, 18, 18)
                        .addComponent(btnQLNguoiDung)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(219, 219, 219)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 6, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBack)
                        .addGap(15, 15, 15))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel1)
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDangKy)
                            .addComponent(btnXoa)
                            .addComponent(btnQLNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBack)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDangKyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangKyActionPerformed
        SignUp signup = new SignUp();
        signup.setVisible(true);
    }//GEN-LAST:event_btnDangKyActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        xoaNguoiDung();

    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        txtTimKiem.setText(""); // Xóa nội dung tìm kiếm
        cbxTimKiem.setSelectedIndex(0); // Đặt lại lựa chọn tìm kiếm
        fetchData(); // Tải lại dữ liệu từ CSDL
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnQLNguoiDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLNguoiDungActionPerformed
        // TODO add your handling code here:
        // Mở cửa sổ mới để hiển thị chi tiết đơn hàng
        NguoiDungFrame nguoiDungFrame = new NguoiDungFrame();
        nguoiDungFrame.setVisible(true);
    }//GEN-LAST:event_btnQLNguoiDungActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        new TrangChu(loaiTaiKhoan).setVisible(true); // Mở trang chủ
                dispose(); // Đóng cửa sổ hiện tại
    }//GEN-LAST:event_btnBackActionPerformed

    // Phương thức kết nối CSDL
    Connection getConnection() throws SQLException {
        var server = "localhost";  // Hoặc tên server của bạn
        var user = "sa";
        var password = "123456";
        var db = "QUANLY";
        var port = 1433;

        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser(user);
        ds.setPassword(password);
        ds.setDatabaseName(db);
        ds.setPortNumber(port);
        ds.setServerName(server);
        ds.setEncrypt(false);
        return ds.getConnection();
    }

private void xoaNguoiDung() {
    int row = tableTaiKhoan.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một người dùng để xóa!");
        return;
    }

    String maNguoiDung = (String) tableTaiKhoan.getValueAt(row, 4); // Lấy mã người dùng từ cột thứ 5 (the correct column for MaNguoiDung)
    String sqlCheckAdmin = "SELECT LoaiNguoiDung FROM NGUOIDUNG WHERE MaNguoiDung = ?"; // Giả sử có cột 'LoaiNguoiDung' để xác định quyền

    try (Connection conn = getConnection()) {
        // Kiểm tra xem người dùng có phải là admin hay không
        PreparedStatement psCheckAdmin = conn.prepareStatement(sqlCheckAdmin);
        psCheckAdmin.setString(1, maNguoiDung);
        ResultSet rs = psCheckAdmin.executeQuery();

        if (rs.next()) {
            String loaiNguoiDung = rs.getString("LoaiNguoiDung");

            // Kiểm tra nếu người dùng là admin
            if ("admin".equalsIgnoreCase(loaiNguoiDung)) {
                JOptionPane.showMessageDialog(this, "Không thể xóa tài khoản admin!");
                return;
            }
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa người dùng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String sqlDeleteAccount = "DELETE FROM TAIKHOAN WHERE MaNguoiDung = ?";
            String sqlDeleteUser = "DELETE FROM NGUOIDUNG WHERE MaNguoiDung = ?";
            try {
                conn.setAutoCommit(false); // Bắt đầu transaction

                try (PreparedStatement psDeleteAccount = conn.prepareStatement(sqlDeleteAccount); PreparedStatement psDeleteUser = conn.prepareStatement(sqlDeleteUser)) {

                    // Xóa tài khoản của người dùng
                    psDeleteAccount.setString(1, maNguoiDung);
                    psDeleteAccount.executeUpdate();

                    // Xóa người dùng
                    psDeleteUser.setString(1, maNguoiDung);
                    psDeleteUser.executeUpdate();

                    conn.commit(); // Xác nhận transaction
                    JOptionPane.showMessageDialog(this, "Xóa người dùng và tài khoản thành công!");
                    fetchData(); // Cập nhật bảng sau khi xóa
                } catch (SQLException e) {
                    conn.rollback(); // Hoàn tác nếu có lỗi
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa người dùng hoặc tài khoản!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


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
            java.util.logging.Logger.getLogger(QLTaiKhoanFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLTaiKhoanFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLTaiKhoanFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLTaiKhoanFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLTaiKhoanFrame("a").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDangKy;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnQLNguoiDung;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbxTimKiem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableTaiKhoan;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
