package kr.ac.green;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

public class JoinForm extends JDialog implements ActionListener{
	public static final int ID = 0;
	public static final int PW = 1;
	public static final int RE = 2;
	public static final int NAME = 3;
	public static final int NICK = 4;
	public String[] names = {"ID", "Password", "Retry", "Name", "NickName"};
	
	private JButton btnJoin;
	private JButton btnCancel;
	//inputs : 입력창 넣을 배열
	private JTextComponent[] inputs;
	private JRadioButton rbtnMale;
	private JRadioButton rbtnFemale;
	private LoginForm owner;
	
	public JoinForm(LoginForm owner){
		super(owner, "Join", true);
		this.owner = owner;
		init();
		setDisplay();
		addListener();
		showDlg();
	}
	private void init(){
		btnJoin = LoginUtils.getButton("Join");
		btnCancel = LoginUtils.getButton("Cancel");
		inputs = new JTextComponent[]{
			LoginUtils.getTextComponent(LoginUtils.TEXT),
			LoginUtils.getTextComponent(LoginUtils.PASSWORD),
			LoginUtils.getTextComponent(LoginUtils.PASSWORD),
			LoginUtils.getTextComponent(LoginUtils.TEXT),
			LoginUtils.getTextComponent(LoginUtils.TEXT)
		};
		ButtonGroup group = new ButtonGroup();
		rbtnMale = new JRadioButton("Male", true);
		rbtnFemale = new JRadioButton("Female");
		group.add(rbtnMale);
		group.add(rbtnFemale);
	}
	private void setDisplay(){
		JPanel pnlMain = new JPanel(new BorderLayout());
		
		JPanel pnlNorth = new JPanel(new GridLayout(0,1));
		for(int i = 0; i < inputs.length; i++){
			JPanel pnl =  new JPanel();
			//레이블 생성 후 추가
			pnl.add(LoginUtils.getLabel(names[i]));
			//입력창 추가
			pnl.add(inputs[i]);
			pnlNorth.add(pnl);
		}
		
		JPanel pnlGender = new JPanel();
		pnlGender.add(rbtnMale);
		pnlGender.add(rbtnFemale);
		pnlGender.setBorder(new TitledBorder("Gender"));
		
		JPanel pnlBtns = new JPanel();
		pnlBtns.add(btnJoin);
		pnlBtns.add(btnCancel);
		
		pnlMain.add(pnlNorth, BorderLayout.NORTH);
		pnlMain.add(pnlGender, BorderLayout.CENTER);
		pnlMain.add(pnlBtns, BorderLayout.SOUTH);
		
		TitledBorder tBorder = new TitledBorder(
				new EmptyBorder(5,10,5,10),
				"- Input your information"
		);
		tBorder.setTitleFont(new Font(Font.DIALOG, Font.BOLD, 13));
		pnlMain.setBorder(tBorder);
		
		add(pnlMain, BorderLayout.CENTER);
		
	}
	private void addListener(){
		btnJoin.addActionListener(this);
		btnCancel.addActionListener(this);
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we){
				closeDlg();
			}
		});
	}
	private void showDlg(){
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	private void closeDlg(){
		owner.setVisible(true);
		dispose();
	}
	@Override
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource() == btnJoin){
			boolean flag = true;
			String msg = "Join OK";
			//입력 확인
			for(int idx = 0; flag && idx < inputs.length; idx++){
				if(LoginUtils.isEmpty(inputs[idx])){
					flag = false;
					msg = "missing input : " + names[idx];
					inputs[idx].requestFocus();
				}
			}
			//아이디 체크
			if(flag){
				User user = owner.findUser(inputs[ID].getText());
				if(user != null){
					flag = false;
					msg = "invalid ID : already existed";
					inputs[ID].requestFocus();
				}
			}
			//비번 체크
			if(flag){
				String pw1 = inputs[PW].getText();
				String pw2 = inputs[RE].getText();
				if(!(pw1.equals(pw2))){
					flag = false;
					msg = "check your password";
					inputs[PW].requestFocus();
				}
			}
			
			if(flag){
				//user생성
				User user = new User(
						inputs[ID].getText(),
						inputs[PW].getText(),
						inputs[NAME].getText(),
						inputs[NICK].getText(),
						rbtnMale.isSelected() ? "male" : "female"
				); 
				// list 추가
				owner.addUser(user);
				closeDlg();
			}
			JOptionPane.showMessageDialog(
					this,
					msg,
					"Information",
					JOptionPane.INFORMATION_MESSAGE
			);
		}else{ // btnCancel
			closeDlg();
		}
	}
}
