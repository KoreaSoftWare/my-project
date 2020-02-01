package 크롤러;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class School_meals {
	private String delete(String input, char[] target) {
		String sub1 = "", sub2 = "";
		
		for(int i=0; i<input.length(); i++) {
			for(int j=0; j<target.length; j++) {
				if(input.charAt(i) == target[j] ) {
					sub1 = input.substring(0, i);
					sub2 = input.substring(i+1, input.length());
					input = sub1 + sub2;
					i--;
					System.out.println(input);
				}
			}
		}
		return input;
	}
	
	public String getSchoolmealsData(final String year, final String month, final String day) {
		String menu = null;
		String url = new String();
		char[] target = {'/', '.', '<', '>', 'b', 'r', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
		
		if(month.length() == 1) url = "http://www.yeojufirst.hs.kr/meal/formList.do?menugrp=050702&searchYM=" + year + '0' + month + "&searchDay=" + day;
		else url = "http://www.yeojufirst.hs.kr/meal/formList.do?menugrp=050702&searchYM=" + year +  month + "&searchDay=" + day;
		System.out.println("url => "+url);
		
		try {
			Document doc = Jsoup.connect(url).get();
			
			Elements mealList = doc.select(".menuBox");
			
			
			
			
			for (Element element : mealList) {
				
				String strMenu = element.text();
				if(strMenu.indexOf("중식")>-1) {
					System.out.println("==============");
					System.out.println(strMenu);
					System.out.println("==============end");
					menu = strMenu;
					break;
				}
				
				
			}
			
			menu = delete(menu, target); 
		}catch(Exception e) {
			System.out.println("year => "+year +" month => "+month + " day => "+day );
			System.out.println("메뉴 불러오기 실패");
			
		}
		
		return menu;
	}
}