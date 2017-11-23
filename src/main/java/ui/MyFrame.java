package ui;

import job.Compare;
import job.HighLight;
import utils.ReadFileUtil;

import javax.swing.*;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

/**
 * created by xdCao on 2017/11/23
 */

public class MyFrame extends JFrame{

    private static final long serialVersionUID = 1L;

    Panel flowLayoutPanel1;
    Panel flowLayoutPanel2;

    private File smallFile;
    private File biggerFile;

    private int smallCol;
    private int bigCol;

    private void generateFlowLayoutPanel(final int num) {

        if (num==1){
            flowLayoutPanel1 = new Panel();
            flowLayoutPanel1.setLayout(new FlowLayout());
        }else {
            flowLayoutPanel2=new Panel();
            flowLayoutPanel2.setLayout(new FlowLayout());
        }

        JLabel jLabel=new JLabel("比较第几列（从0开始数）");
        final JTextField column=new JTextField(5);
        JButton confirm=new JButton("确认");
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (num==1){
                    smallCol=Integer.parseInt(column.getText());
                }else if (num==2){
                    bigCol=Integer.parseInt(column.getText());
                }
            }
        });

        final JTextField jTextField=new JTextField(20);
        JButton select = null;
        if (num==1){
            select=new JButton("选择文件较小的那一个");
        }else if (num==2){
            select=new JButton("选择文件较大的那一个");
        }

        select.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc=new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
                jfc.showDialog(new JLabel(), "确认");
                File file=jfc.getSelectedFile();
                if(file.isDirectory()){
                    JOptionPane.showMessageDialog(null,"请选择一个文件，在目录上双击即可进入");
                }else if(file.isFile()){
                    jTextField.setText(file.getAbsolutePath());
                    if (num==1){
                        smallFile=file;
                    }else if (num==2){
                        biggerFile=file;
                    }
                }
            }
        });


        if (num==1){
            flowLayoutPanel1.add(jLabel);
            flowLayoutPanel1.add(column);
            flowLayoutPanel1.add(confirm);
            flowLayoutPanel1.add(jTextField);
            flowLayoutPanel1.add(select);
        }else {
            flowLayoutPanel2.add(jLabel);
            flowLayoutPanel2.add(column);
            flowLayoutPanel2.add(confirm);
            flowLayoutPanel2.add(jTextField);
            flowLayoutPanel2.add(select);
        }


    }

    public MyFrame(String panelName) {
        super(panelName);
        generateFlowLayoutPanel(1);
        generateFlowLayoutPanel(2);
        setLayout(new GridLayout(3,1));
        add(flowLayoutPanel1);
        add(flowLayoutPanel2);
        JButton start=new JButton("开始比对");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (smallFile!=null&&biggerFile!=null){
                    Compare compare=new Compare(smallFile,biggerFile);
                    ArrayList<Integer> rowNums = compare.compareFiles(smallCol, bigCol);
                    HighLight highlight=new HighLight(rowNums,biggerFile);
                    highlight.highLight();
                }else {
                    JOptionPane.showMessageDialog(null,"请选择好文件再开始比对!!");
                }

            }
        });
        add(start);
        setSize(800, 180);
        setLocation(400,400);
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent arg0) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        MyFrame yourFrame = new MyFrame("欢迎");
        yourFrame.setVisible(true);
    }

}
