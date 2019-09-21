package 크롤러;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class School_meals {
	private String delete(String input, char[] target) {
		String sub1 = "", sub2 = "";
		
		for(int i=0; i<input.length(); i++) {
			for(int j=0; j<target.length; j++) {
				if(input.charAt(i) == target[j] ) {
					sub1 = input.substring(0, i);
					sub2 = input.substring(i+1, input.length());
					input = sub1 + " " + sub2;
					i--;
				}
			}
		}
		return input;
	}
	
	public String getSchoolmealsData(String year, String month, String day) {
		String menu = null;
		String url = new String();
		char[] target = {'/', '.'};
		
		url = "http://www.yeojufirst.hs.kr/meal/formList.do?menugrp=050702&searchYM=" + year + month + "&searchDay=" + day;
		System.out.println("url => "+url);
		
		try {
			Document doc = Jsoup.connect(url).get();
			
			Elements mealList = doc.select(".textBox");
			System.out.println(mealList.html());
			menu = mealList.get(1).text();
			menu = delete(menu, target);
		}catch(Exception e) {
			System.out.println("year => "+year +" month => "+month + " day => "+day );
			System.out.println("메뉴 불러오기 실패");
			
		}
		
		return menu;
	}
}
