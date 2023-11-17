import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;
public class OJDBC extends JFrame implements ActionListener
{
    GridBagConstraints gc;
    JButton b1,b2,b3,b4,b5,b6,b7,b8;
    JLabel l1,l2,l3;
    JTextField id, name, dept;
    Statement st;
    ResultSet r;
    JComboBox jcb;
    OJDBC(){
        setSize(600,500);
        setLocation(370,150);
        setVisible(true);
        //Instantiation
        b1 =new JButton("First");
        b2 = new JButton("Next");
        b3 = new JButton("Previous");
        b4 = new JButton("Last");
        b5 = new JButton("Insert Data");
        b6 = new JButton("Modify");
        b7 = new JButton("Delete");
        b8 = new JButton("Exit");
        id = new JTextField(10);
        name = new JTextField(10);
        dept = new JTextField(10);
        l1 = new JLabel("ID");
        l2 = new JLabel("Name");
        l3 = new JLabel("Department");
        jcb = new JComboBox();
        //grid
        setLayout(new GridBagLayout());
        gc = new GridBagConstraints();
        addGrid(l1,0,0,2,1);
        addGrid(id,0,2,2,1);
        addGrid(l2,1,0,2,1);
        addGrid(name,1,2,2,1);
        addGrid(l3,2,0,2,1);
        addGrid(dept,2,2,2,1);
        addGrid(b1,3,0,1,1);
        addGrid(b2,3,1,1,1);
        addGrid(b3,3,2,1,1);
        addGrid(b4,3,3,1,1);
        addGrid(b5,4,0,1,1);
        addGrid(b6,4,1,1,1);
        addGrid(b7,4,2,1,1);
        addGrid(b8,4,3,1,1);
        id.setEditable(false);
        //Data connection
        try {
            st = DataCon.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            r =st.executeQuery("select EID,ENAME,DNAME from employee join dept ON employee.DID = dept.DID order by employee.EID");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        //Action Listerners
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        b8.addActionListener(this);
    }
    public void addGrid(Component c, int x,int y ,int w,int h){
        gc.gridx = y;
        gc.gridy = x;
        gc.gridwidth = w;
        gc.gridheight = h;
        gc.fill = gc.BOTH;
        c.setFont(new Font("comic sans MS",Font.PLAIN,20));
        add(c,gc);
    }
    public void clearTextFields(){
        name.setText("");
        dept.setText("");
    }
    public static void main(String[] args) {
        new OJDBC();
    }
    public void setData() throws Exception{
        id.setText(Integer.toString(r.getInt(1)));
        name.setText(r.getString(2));
        dept.setText(r.getString(3));
    }
    public void editable(boolean b){
        name.setEditable(b);
        dept.setEditable(b);
    }
    public void btEnabled(boolean b){
        b1.setEnabled(b);
        b2.setEnabled(b);
        b3.setEnabled(b);
        b4.setEnabled(b);
        b6.setEnabled(b);
        b7.setEnabled(b);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == b1) {
                editable(false);
                r.first();
                setData();
            }
            if (e.getSource() == b2) {
                editable(false);
                r.next();
                setData();
            }
            if (e.getSource() == b3) {
                editable(false);
                r.previous();
                setData();
            }
            if (e.getSource() == b4) {
                editable(false);
                r.last();
                setData();
            }
            if (e.getActionCommand().equals("Insert Data")) {
                PreparedStatement ps = DataCon.con.prepareStatement("select max(EID) from employee");
                ResultSet r1 = ps.executeQuery();
                r1.next();
                int maxEID = r1.getInt(1);
                maxEID++;
                id.setText(Integer.toString(maxEID));
                clearTextFields();
                b5.setText("Save");
                btEnabled(false);
                editable(true);
                remove(dept);
                addGrid(jcb, 2, 2, 2, 1);
                PreparedStatement ps1 = DataCon.con.prepareStatement("select DNAME from dept");
                ResultSet r2 = ps1.executeQuery();
                while (r2.next()) {
                    jcb.addItem(r2.getString(1));
                }
                b8.setText("GO back");
            }
            if (e.getActionCommand().equals("Save")) {
                PreparedStatement ps3 = DataCon.con.prepareStatement("insert into employee values(?,?,?)");
                ps3.setInt(1, Integer.parseInt(id.getText()));
                ps3.setString(2, name.getText());
                ps3.setInt(3, jcb.getSelectedIndex() + 1);
                int r3 = ps3.executeUpdate();
                b5.setText("Insert Data");
                remove(jcb);
                addGrid(dept, 2, 2, 2, 1);
                btEnabled(true);
                editable(false);
                clearTextFields();
                jcb.removeAllItems();
                r = st.executeQuery("select EID,ENAME,DNAME from employee join dept ON employee.DID = dept.DID order by employee.EID");
                r.last();
                setData();
                b8.setText("Exit");
                DataCon.con.commit();
                JOptionPane.showMessageDialog(this, "Inserted data Successfully", "information", JOptionPane.INFORMATION_MESSAGE);
            }
            if (e.getActionCommand().equals("GO back")) {
                b5.setText("Insert Data");
                remove(jcb);
                addGrid(dept, 2, 2, 2, 1);
                btEnabled(true);
                b5.setEnabled(true);
                clearTextFields();
                jcb.removeAllItems();
                r = st.executeQuery("select EID,ENAME,DNAME from employee join dept ON employee.DID = dept.DID order by employee.EID");
                r.last();
                setData();
                b8.setText("Exit");
                b6.setText("Modify");
                DataCon.con.commit();
            }
            if (e.getActionCommand().equals("Modify")) {
                b6.setText("Update");
                remove(dept);
                addGrid(jcb, 2, 2, 2, 1);
                btEnabled(false);
                b6.setEnabled(true);
                b5.setEnabled(false);
                b8.setText("GO back");
                name.setEditable(true);
                PreparedStatement ps4 = DataCon.con.prepareStatement("select DNAME from dept");
                ResultSet r5 = ps4.executeQuery();
                while (r5.next()) {
                    jcb.addItem(r5.getString(1));
                }

            }
            if (e.getActionCommand().equals("Update")) {
                int index = Integer.parseInt(id.getText());
                PreparedStatement ps5 = DataCon.con.prepareStatement("update employee set ENAME = ?,DID = ? where EID = ?");
                ps5.setString(1, name.getText());
                ps5.setInt(2, jcb.getSelectedIndex() + 1);
                ps5.setInt(3, Integer.parseInt(id.getText()));
                int r4 = ps5.executeUpdate();
                jcb.removeAllItems();
                remove(jcb);
                addGrid(dept, 2, 2, 2, 1);
                b6.setText("Modify");
                b8.setText("Exit");
                btEnabled(true);
                b5.setEnabled(true);
                r = st.executeQuery("select EID,ENAME,DNAME from employee join dept ON employee.DID = dept.DID order by employee.EID");
                r.next();
                while (r.getInt(1) != index) {
                    r.next();
                }
                setData();
                JOptionPane.showMessageDialog(this, "Updated data Successfully", "information", JOptionPane.INFORMATION_MESSAGE);
            }
            if (e.getActionCommand().equals("Delete")) {
                int index = Integer.parseInt(id.getText());
                PreparedStatement ps7 = DataCon.con.prepareStatement("select max(EID) from employee");
                ResultSet r6 = ps7.executeQuery();
                r6.next();
                int maxEID = r6.getInt(1);
                PreparedStatement ps6 = DataCon.con.prepareStatement("delete from employee where EID = ?");
                ps6.setInt(1, Integer.parseInt(id.getText()));
                int rs5 = ps6.executeUpdate();
                try {
                if (maxEID == 1) {
                    clearTextFields();
                    id.setText("");
                    editable(false);
                    r = st.executeQuery("select EID,ENAME,DNAME from employee join dept ON employee.DID = dept.DID order by employee.EID");
                    setData();
                }
                }catch (Exception eee){
                    JOptionPane.showMessageDialog(this, "Deleted data Successfully", "information", JOptionPane.INFORMATION_MESSAGE);
                }
                r = st.executeQuery("select EID,ENAME,DNAME from employee join dept ON employee.DID = dept.DID order by employee.EID");
                r.next();
                setData();
                JOptionPane.showMessageDialog(this, "Deleted data Successfully", "information", JOptionPane.INFORMATION_MESSAGE);
            }
            if(e.getActionCommand().equals("Exit")) {
                DataCon.con.commit();
                DataCon.con.close();
                System.exit(0);
            }
        }catch (Exception ee){
            JOptionPane.showMessageDialog(this,"NO MORE DATA IN DATABASE","Alert",JOptionPane.ERROR_MESSAGE);
        }
    }
}