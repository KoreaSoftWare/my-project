package �޷�;

import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import �޽�ǥUI.School_meals_UI;

public class Main_UI extends JFrame{
	private String[] dayofweek = {"��","��", "ȭ", "��", "��", "��", "��"};
	private String[] years = {"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027",};
	private String[] month = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	private int[] max_day = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	private JComboBox<String> years_combo = new JComboBox<String>(years);
	private JComboBox<String> month_combo = new JComboBox<String>(month);
	
	private int index, index2;
	//������ �˾Ƴ��� ����� �� �ڸ� �����
	private int getDayOfweek(String date) {
		System.out.println(date);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		Date getDate;
		int w = 0;
		
		try {
			getDate = format.parse(date);
			cal.setTime(getDate);
			System.out.println(cal.getTime());
			w = cal.get(Calendar.DAY_OF_WEEK) - 1;//0���� �����̴� -1����
		}catch(ParseException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return w;
	}
	
	public Main_UI() {
		setTitle("�������ϰ���б� �޽�ǥ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		c.add(new North(), BorderLayout.NORTH);
		c.add(new Center(), BorderLayout.CENTER);
		
		setSize(500, 400);
		setVisible(true);
	}
	
	//Center�� �ٽ� �ҷ��´�.
	public void refreshCenter() {
		Container c = getContentPane();
		
		BorderLayout layout = (BorderLayout)c.getLayout();
		
		c.remove(layout.getLayoutComponent(BorderLayout.CENTER));
		c.revalidate();
		c.repaint();
		
		c.add(new Center(), BorderLayout.CENTER);
	}
	
	//Center���� �޷��� �����.
	class Center extends JPanel{
		private int count = 1;
		private int result;
		private int object_count = 0;
		
		public Center(){
			setLayout(new GridLayout(7, 7));
			//ó�� ���� �� ���̱�
			for(int i=0; i<7; i++) {
				JLabel label = new JLabel(dayofweek[i]);
				label.setHorizontalAlignment(JLabel.CENTER);
				
				EtchedBorder eborder = new EtchedBorder(EtchedBorder.RAISED);
				
				label.setBorder(eborder);
				label.setOpaque(true);
				label.setBackground(Color.white);
				
				add(label);
				System.out.println("i = " + i);
				object_count++;
			}
			String day = Integer.toString(count);
			System.out.println("day = " + day);
				
			//ù��° ������ ã�Ƴ���
			if(count < 10) 
				result = getDayOfweek(years[index] + month[index2] + "0" + day);
			else 
				result = getDayOfweek(years[index] + month[index2] + day);
				
			System.out.println("result = " + result);
			
			//���� ������� �� �󺧷� �����.
			for(int j=0; j<result; j++) {
				object_count++;
				add(new JLabel());
			}
				
			//��¥ ��ư�� ����ϰ�
			for(int j=0 ; j < max_day[index2]; j++) {
					
				JButton button = new JButton();
					
				button = new JButton(Integer.toString(count));
					
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Main_UI ui = (Main_UI) SwingUtilities.getWindowAncestor((Component) e.getSource());
						
						JButton  btn = (JButton) e.getSource();
						
						School_meals_UI mealsUI = new School_meals_UI(ui, "�޽�ǥ", years[index], month[index2], btn.getText());
						mealsUI.setVisible(true);
					}
				});
				add(button);
				count++;
				object_count++;
			}
			
			//���� ������ ä���.(�� �׷��� �ڱ� �ڴ�� ����)
			for(int i=0; i<49-object_count; i++){
				add(new JLabel());
			}
		}
	}
	
	//��¥�� �����.
	class North extends JPanel{
		public North() {
			//��¥�� �ٲ� ������ ���÷� ������Ʈ�� �ؾ� �Ѵ�.(index, index2�� �ٲ� ��¥ ����)
			Calendar cal = new GregorianCalendar(Locale.KOREA);
			
			index = cal.get(Calendar.YEAR) - 2018;
			index2 = cal.get(Calendar.MONTH);
			
			System.out.println("index = " + index);
			System.out.println("index2 = " + index2);
			
			years_combo.setSelectedIndex(index);
			month_combo.setSelectedIndex(index2);
			
			add(years_combo);
			add(month_combo);
			
			years_combo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JComboBox<String> cb = (JComboBox<String>)e.getSource();
					
					index = cb.getSelectedIndex();
					
					refreshCenter();
				}
			});
			month_combo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JComboBox<String> cb2 = (JComboBox<String>)e.getSource();
					
					index2 = cb2.getSelectedIndex();
					
					refreshCenter();
				}
			});
		}
	}
	public static void main(String[]args) {
		new Main_UI();
	}
}