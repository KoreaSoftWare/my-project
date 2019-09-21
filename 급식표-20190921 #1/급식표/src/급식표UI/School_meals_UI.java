package 급식표UI;

import javax.swing.*;

import 달력.Main_UI;

import java.awt.*;
import java.awt.event.*;
import 크롤러.School_meals;
import java.util.*;

@SuppressWarnings("serial")
public class School_meals_UI extends JDialog{
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
		
		System.out.println("result = " + result);
		
		add(new North(year, mon, day), BorderLayout.NORTH);
		add(new Center(), BorderLayout.CENTER);
		add(new South(), BorderLayout.SOUTH);
		
		setSize(400, 500);
	}
	
	public void refreshCenter() {
		System.out.println("refeshCenter Call");
		
		Container c = getContentPane();
		BorderLayout layout = (BorderLayout)c.getLayout();
		remove(layout.getLayoutComponent(BorderLayout.CENTER));
		revalidate();
	    repaint();
		add(new Center(), BorderLayout.CENTER);
	}
	
	class Center extends JPanel{
		private JList<String> mealsList = new JList<String>();
		private Vector<String> v = new Vector<String>();
		private int start = 0;
		
		public Center() {
//			for(int i=0; i<result.length(); i++) {
//				if(result.charAt(i) == ' ') {
//					v.add(result.substring(start, i-1));
//					start = i+1;
//					mealsList.setListData(v);
//				}
//			}
		}
	}
	
	class North extends JPanel{
		private ImageIcon back = new ImageIcon("C:\\Users\\학생용\\Desktop\\급식표-20190825T040258Z-001\\image\\back.png");
		private ImageIcon next = new ImageIcon("C:\\Users\\학생용\\Desktop\\급식표-20190825T040258Z-001\\image\\next.png");
		
		private JButton backButton = new JButton(back);
		private JButton nextButton = new JButton(next);
		
		public North(String year, String mon, String day) {
			for(int i=1; i<=max_day[index2]; i++) {
				day_combo.addItem(Integer.toString(i));
			}
			
			years_combo.setSelectedIndex(Integer.parseInt(year)-2018);
			month_combo.setSelectedIndex(Integer.parseInt(mon));
			day_combo.setSelectedIndex(Integer.parseInt(day));
			
			backButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(Integer.parseInt(d)-1 == 0) {
						int setMonth = Integer.parseInt(m)-1;
						
						if(setMonth == 0) {
							int setYears = Integer.parseInt(y)-2018;
							month_combo.setSelectedIndex(11);
							years_combo.setSelectedIndex(setYears);
						}
						else month_combo.setSelectedIndex(setMonth);
						
						for(int i=1; i<=max_day[setMonth]; i++) {
							day_combo.addItem(Integer.toString(i));
						}
						
						day_combo.setSelectedIndex(max_day[setMonth]-1);
					}
					else day_combo.setSelectedIndex(Integer.parseInt(d)-1);
					
					refreshCenter();
				}
			});
			
			years_combo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					@SuppressWarnings("unchecked")
					JComboBox<String> cb = (JComboBox<String>)e.getSource();
					
					index = cb.getSelectedIndex();
					y = Integer.toString(Integer.parseInt(years[index])+1);
					
					result = mealsData.getSchoolmealsData(y, m, d);
					refreshCenter();
				}
			});
			
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
			
			nextButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int setDay = Integer.parseInt(d)+1;
					if(setDay > max_day[Integer.parseInt(m)]) {
						int setMonth = Integer.parseInt(m)+1;
						
						if(setMonth == 13) {
							int setYears = Integer.parseInt(y)+1;
							month_combo.setSelectedIndex(0);
							years_combo.setSelectedIndex(setYears);
						}
						else month_combo.setSelectedIndex(setMonth);
						
						for(int i=1; i<=max_day[setMonth]; i++) {
							day_combo.addItem(Integer.toString(i));
						}
						
						day_combo.setSelectedIndex(max_day[setMonth]-1);
					}
					else day_combo.setSelectedIndex(0);
					
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
		private ImageIcon mainMenu = new ImageIcon("C:\\Users\\학생용\\Desktop\\급식표-20190825T040258Z-001\\image\\main menu.png");
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
