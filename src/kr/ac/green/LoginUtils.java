package kr.ac.green;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class LoginUtils {
	public static final int TEXT = 0;
	public static final int PASSWORD = 1;
	private static final Dimension LABEL_SIZE = new Dimension(70, 25);
	private static final Dimension FIELD_SIZE = new Dimension(110, 20);
	private static final Dimension BTN_SIZE = new Dimension(90, 23);
	
	//레이블 가져오기(사이즈 맞춤)
	public static JLabel getLabel(String str){
		JLabel lbl = new JLabel(str, JLabel.LEFT);
		lbl.setPreferredSize(LABEL_SIZE);
		return lbl;
	}
	//버튼 가져오기(사이즈 맞춤)
	public static JButton getButton(String str){
		JButton btn = new JButton(str);
		btn.setPreferredSize(BTN_SIZE);
		return btn;
	}
	//kind(TEXT,PASSWORD)에 따라 JTextComponent가져오기 (사이즈 맞춤)
	public static JTextComponent getTextComponent(int kind){
		JTextComponent text = null;
		if(kind == PASSWORD){
			text = new JPasswordField();
		}else{
			text = new JTextField();
		}
		text.setPreferredSize(FIELD_SIZE);
		return text;
	}
	//JTextComponent input이 비었는지 검사
	public static boolean isEmpty(JTextComponent input){
		String text = input.getText().trim();
		return (text.length() == 0) ? true : false;
	}
}
