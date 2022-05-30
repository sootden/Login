package kr.ac.green;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

public class LoginForm extends JFrame implements ActionListener{
	private JTextComponent tfId;
	private JTextComponent pfPw;
	
	private JButton btnLogin;
	private JButton btnJoin;
	
	private Vector<User> list;
	
	public LoginForm(){
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	
	private void init(){
		//가입된 사용자들의 정보들이 저장된 데이터
		list = new Vector<User>();
		
		tfId = LoginUtils.getTextComponent(LoginUtils.TEXT);
		pfPw = LoginUtils.getTextComponent(LoginUtils.PASSWORD);
		
		btnJoin = LoginUtils.getButton("Join");
		btnLogin = LoginUtils.getButton("Login");
	}
	private void setDisplay(){
		JPanel pnlText = new JPanel(new GridLayout(0,1));
		JPanel pnlInput = new JPanel(new GridLayout(0,1));
		
		pnlText.add(LoginUtils.getLabel("ID"));
		pnlText.add(LoginUtils.getLabel("Password"));
		
		JPanel pnlId = new JPanel();
		pnlId.add(tfId);
		JPanel pnlPw = new JPanel();
		pnlPw.add(pfPw);
		pnlInput.add(pnlId);
		pnlInput.add(pnlPw);
		
		JPanel pnlSouth =  new JPanel();
		pnlSouth.add(btnLogin);
		pnlSouth.add(btnJoin);
		
		JPanel pnlMain = new JPanel(new BorderLayout());
		pnlMain.add(pnlText, BorderLayout.WEST);
		pnlMain.add(pnlInput, BorderLayout.CENTER);
		pnlMain.add(pnlSouth, BorderLayout.SOUTH);
		
		pnlMain.setBorder(new EmptyBorder(5,10,5,10));
		
		add(pnlMain, BorderLayout.CENTER);
	}
	private void addListeners(){
		btnLogin.addActionListener(this);
		btnJoin.addActionListener(this);
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we){
				int result = JOptionPane.showConfirmDialog(
						LoginForm.this,
						"exit?",
						"question",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE
				);
				if(result == JOptionPane.YES_OPTION){
					System.exit(0);
				}
			}
		});
	}
	private void showFrame(){
		setTitle("Login");
		pack();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e){
		Object src = e.getSource();
		if(src == btnLogin){
			JTextComponent input = null;
			String msg = "welcom!!";
			User user = null;
			if(LoginUtils.isEmpty(tfId)){
				// 아이디 입력x
				msg = "input your ID";
				input = tfId;
			}else{
				if(LoginUtils.isEmpty(pfPw)){
					// 비번 입력x
					msg = "input your password";
					input = pfPw;
				}else{
					String uid = tfId.getText();
					String upw = pfPw.getText();
					user = findUser(uid);
					if(user == null){
						// list에 id를 가진 user가 없으면
						msg = "check your ID";
						input = tfId;
					}else{
						if(!upw.equals(user.getUpw())){
							// user의 비번도 동일하면
							msg = "check your password";
							input = pfPw;
						}
					}
				}
			}
			JOptionPane.showMessageDialog(
					this,
					msg,
					"Information",
					JOptionPane.INFORMATION_MESSAGE
			);
			if(input != null){ 
				// input에 focus
				input.requestFocus();
			}else{
				// input == null -> 로그인 성공 
				clear();
				setVisible(false);
				new InformationForm(this, user);
			}
		}else{
			//가입버튼
			clear();
			setVisible(false);
			new JoinForm(this);
		}
	}
	// 입력창 비우기
	private void clear(){
		tfId.setText("");
		pfPw.setText("");
	}
	// list에서 user찾기
	public User findUser(String userId){
		int idx = list.indexOf(new User(userId));
		if(idx >= 0){
			return list.get(idx);
		}else{
			return null;
		}
	}
	// list에 user 추가
	public void addUser(User user){
		if(findUser(user.getUid()) == null){
			// id 중복 x 면 추가
			list.add(user);
		}
	}
	// list에 user 제거
	public void removeUser(User user){
		list.remove(user);
	}
	
	public static void main(String[] args){
		new LoginForm();
	}
}
