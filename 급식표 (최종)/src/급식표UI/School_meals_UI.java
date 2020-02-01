package 급식표UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import 크롤러.School_meals;
import java.util.*;
import java.time.*;

@SuppressWarnings("serial")
public class School_meals_UI extends JDialog{
	private Calendar cal = new GregorianCalendar(Locale.KOREA);
	private String result = null;
	private String[] years = {"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027",};
	private String[] month = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	private int[] max_day = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	private JComboBox<String> years_combo = new JComboBox<String>(years);
	private JComboBox<String> month_combo = new JComboBox<String>(month);
	private JComboBox<String> day_combo = new JComboBox<String>();
	
	private int index, index2, index3;
	private String y, m, d;
	private School_meals mealsData = new School_meals();
	
	public School_meals_UI(JFrame frame, String title, String year, String mon, String day) {
		super(frame, title, true);
		setLayout(new BorderLayout());
		
		y = year;
		m = mon;
		d = day;
		
		System.out.println("y = " + y + " m = " + m + " d = " + d);
		result = mealsData.getSchoolmealsData(y, m, d);
		
		add(new North(year, mon, day), BorderLayout.NORTH);
		add(new Center(), BorderLayout.CENTER);
		add(new South(), BorderLayout.SOUTH);
		
		setSize(400, 500);
	}
	
	//Center를 다시 불러온다.
	public void refreshCenter() {
		System.out.println("refeshCenter Call");
		
		result = mealsData.getSchoolmealsData(y, m, d);
		System.out.println("result = " + result);
		
		Container c = this.getContentPane();
		
		BorderLayout layout = (BorderLayout)c.getLayout();
		
		Center  center= (Center) layout.getLayoutComponent(BorderLayout.CENTER);
		if(center != null) {
			c.remove(center);
			c.revalidate();
		    c.repaint();
		}
	    
	    
		c.add(new Center(), BorderLayout.CENTER);
	}
	
	//메뉴를 보여준다.
	class Center extends JPanel{
		private Vector<String> v = new Vector<String>();
		private int start = 0;
		
		//result에 있는 공백 수 만큼 빼서 Vector에 저장한다.
		private void cut(int end, int gap) {
			v.add(result.substring(start, end));
			start = end + gap;
		}
		
		public Center() {
			v.removeAllElements();
			if(result==null) {
				return;
			}
			System.out.println("result length = " + result.length());
			
			//result로 부터 공백 찾아내기
			for(int i=0; i<result.length(); i++) {
				System.out.println("start = " + start + " i = " + i);
				
				try {
					if(result.charAt(i) == ' ' && result.charAt(i+1) == ' ') {
						System.out.println("1");
						cut(i, 2);
						i++;
					}
					else if(result.charAt(i) == ' ' && result.charAt(i+1) != ' '){
						System.out.println("2");
						cut(i, 1);
					}
				}catch(Exception e) {
					v.add(result.substring(start, i));
				}
			}
			
			//메뉴 개수 만큼 레이아웃 크기 설정
			setLayout(new GridLayout(v.size(),1));
			
			//Vector에 있는 메뉴 출력하기
			for(int i=0; i<v.size(); i++) {
				JLabel label = new JLabel(v.get(i), JLabel.CENTER);
				add(label);
			}
		}
	}
	
	//날짜를 보여준다.
	class North extends JPanel{
		private ImageIcon back = new ImageIcon("./src/image/back.png");
		private ImageIcon next = new ImageIcon("./src/image/next.png");
		private JButton backButton = new JButton(back);
		private JButton nextButton = new JButton(next);
		
		public North(String year, String mon, String day) {
			//처음 날짜를 띄울 때
			day_combo.removeAllItems();
			for(int i=1; i<=Month.of(Integer.parseInt(mon)).length(Year.isLeap(Integer.parseInt(year))); i++) {
				day_combo.addItem(Integer.toString(i));
			}
			
			//Main_UI로 부터 받아온 날짜를 보여준다.
			years_combo.setSelectedIndex(Integer.parseInt(year)-2018);
			month_combo.setSelectedIndex(Integer.parseInt(mon)-1);
			day_combo.setSelectedIndex(Integer.parseInt(day)-1);
			
			//여기서부터는 이벤트 처리 여기도 역시 날짜가 수시로 업데이트되고 Center를 계속 다시 불러온다.
			
			//뒤로 버튼을 누르면 날짜가 줄어든다.
			backButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String year = (String) years_combo.getSelectedItem();
					String month = (String)  month_combo.getSelectedItem();
					String date = (String) day_combo.getSelectedItem();
					
					if(date.length()==1) {
						date = "0"+date;
					}
					if(month.length()==1) {
						month = "0"+month;
					}
					
					Date dateCombo =null;
					try {
						SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd"); 
						dateCombo = dt.parse(year+month+date);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					
					cal.setTime(dateCombo);
					
					cal.add(Calendar.DAY_OF_WEEK, -1);
					
					SimpleDateFormat y_format = new SimpleDateFormat("yyyy");
					SimpleDateFormat m_format = new SimpleDateFormat("MM");
					SimpleDateFormat d_format = new SimpleDateFormat("dd");
					
					int ye = Integer.parseInt(y_format.format(cal.getTime()));
					int mo = Integer.parseInt(m_format.format(cal.getTime()));
					int da = Integer.parseInt(d_format.format(cal.getTime()));
					
					day_combo.removeAllItems();
					for(int i=1; i<=Month.of(mo).length(Year.isLeap(ye)); i++) {
						day_combo.addItem(Integer.toString(i));
					}
					
					years_combo.setSelectedIndex(ye - 2018);
					month_combo.setSelectedIndex(mo - 1);
					day_combo.setSelectedIndex(da - 1);
					
					
					
					refreshCenter();
				}
			});
			
			//년도 이벤트
			years_combo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					@SuppressWarnings("unchecked")
					JComboBox<String> cb = (JComboBox<String>)e.getSource();
					
					index = cb.getSelectedIndex();
					y = Integer.toString(Integer.parseInt(years[index]));
					
					result = mealsData.getSchoolmealsData(y, m, d);
					refreshCenter();
				}
			});
			
			//월 이벤트
			month_combo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					@SuppressWarnings("unchecked")
					JComboBox<String> cb2 = (JComboBox<String>)e.getSource();
					
					index2 = cb2.getSelectedIndex();
					m = Integer.toString(index2+1);
					
					result = mealsData.getSchoolmealsData(y, m, d);
					refreshCenter();
				}
			});
			
			//일 이벤트
			day_combo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					@SuppressWarnings("unchecked")
					JComboBox<String> cb3 = (JComboBox<String>)e.getSource();
					
					index3 = cb3.getSelectedIndex();
					d = Integer.toString(index3+1);
					
					result = mealsData.getSchoolmealsData(y, m, d);
					refreshCenter();
				}
			});
			
			//다음 버튼 
			nextButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String year = (String) years_combo.getSelectedItem();
					String month = (String)  month_combo.getSelectedItem();
					String date = (String) day_combo.getSelectedItem();
					
					if(date.length()==1) {
						date = "0"+date;
					}
					if(month.length()==1) {
						month = "0"+month;
					}
					
					Date dateCombo =null;
					try {
						SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd"); 
						dateCombo = dt.parse(year+month+date);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					
					cal.setTime(dateCombo);
					
					cal.add(Calendar.DAY_OF_WEEK, 1);
					
					SimpleDateFormat y_format = new SimpleDateFormat("yyyy");
					SimpleDateFormat m_format = new SimpleDateFormat("MM");
					SimpleDateFormat d_format = new SimpleDateFormat("dd");
					
					int ye = Integer.parseInt(y_format.format(cal.getTime()));
					int mo = Integer.parseInt(m_format.format(cal.getTime()));
					int da = Integer.parseInt(d_format.format(cal.getTime()));
					
					day_combo.removeAllItems();
					for(int i=1; i<=Month.of(mo).length(Year.isLeap(ye)); i++) {
						day_combo.addItem(Integer.toString(i));
					}
					
					years_combo.setSelectedIndex(ye - 2018);
					month_combo.setSelectedIndex(mo - 1);
					day_combo.setSelectedIndex(da - 1);
					
					refreshCenter();
				}
			});
			
			add(backButton);
			add(years_combo);
			add(month_combo);
			add(day_combo);
			add(nextButton);
		}
	}
	class South extends JPanel{
		private ImageIcon mainMenu = new ImageIcon("./src/image/main menu.png");
		private JButton mainMenuButton = new JButton(mainMenu);
			
		public South() {
			mainMenuButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					School_meals_UI dialog = (School_meals_UI) SwingUtilities.getWindowAncestor((Component) e.getSource());
					dialog.setVisible(false);
				}
			});
			add(mainMenuButton);
		}
	}
}