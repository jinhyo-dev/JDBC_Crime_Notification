package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import java.awt.*;

public class Main {

	static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";

	public static void main(String[] args) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		Scanner in = new Scanner(System.in);

		File file = new File("data/web.html");

		try {
			FileOutputStream fos = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);

			Class.forName(JDBC_DRIVER);

			String url = "jdbc:mariadb://158.247.211.247/Crime_Status";
			String id = "jinhyo";
			String pw = "";
			conn = DriverManager.getConnection(url, id, pw);

			for (int i = 1; i < 80; i++)
				System.out.print("-");
			System.out.println();
			System.out.println("DB 연결 성공");

			bw.write("<!DOCTYPE html>\r\n"
					+ "<html lang=\"ko\">\r\n"
					+ "<head>\r\n"
					+    "<meta charset=\"UTF-8/\">\r\n"
					+    "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
					+    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
					+    "<title>범죄 시간&요일 알리미</title>\r\n"
					+"</head>\r\n"
					+ "<link rel=\"stylesheet\" href=\"style.css\">"
					+"<body>\r\n"
					+ 	"<div class=\"title\"> <img src=\"./img/siren.png\" class=\"siren\"> 범죄 시간&요일 알리미 <img src=\"./img/siren.png\" class=\"siren\"></div>\r\n"
					+ "<br>\r\n"
					+ "<div class=\"box\">"
					);

			stmt = conn.createStatement();
			System.out.println("범죄대분류 : 강력범죄/절도범죄/폭력범죄/지능범죄/풍속범죄/특별경제범죄/마약범죄/보건범죄/환경범죄/교통범죄/노동범죄/안보범죄/선거범죄/병역범죄/기타범죄 중 하나를 선택해주세요");
			String BigCategories = in.nextLine();
			String where = null;
			String DayAverageSQL = null;
			String TimeAverageSQL = null;
			double Average = 0;
			String TorD = null;
			String variable = null;
			
			switch(BigCategories) {
			case "강력범죄":
				System.out.println("강력범죄를 선택하셨습니다.");
				System.out.println("범죄중분류 : 살인기수/살인미수등/강도/강간/유사강간/강제추행/기타강간/강제추행등/방화 중 하나를 선택해주세요.");
				where = in.nextLine();
				break;
				
			case "절도범죄":
				System.out.println("절도범죄를 선택하셨습니다.");
				where = "절도";
				break;
				
			case "폭력범죄":
				System.out.println("폭력범죄를 선택하셨습니다.");
				System.out.println("범죄중분류 : 상해/폭행/체포감금/협박/약취유인/폭력행위등/공갈/손괴 중 하나를 선택해주세요.");
				where = in.nextLine();
				break;
				
			case "지능범죄":
				System.out.println("지능범죄를 선택하셨습니다.");
				System.out.println("범죄중분류 : 직무유기/직권남용/증수뢰/통화/문서인장/유가증권인지/사기/횡령/배임 중 하나를 선택해주세요.");
				where = in.nextLine();
				break;
				
			case "풍속범죄":
				System.out.println("풍속범죄를 선택하셨습니다.");
				System.out.println("범죄중분류 : 성풍속범죄/도박범죄 중 하나를 선택해주세요.");
				where = in.nextLine();
				break;
				
			case "특별경제범죄":
				System.out.println("특별경제범죄를 선택하셨습니다.");
				where = "특별경제범죄";
				break;
				
			case "마약범죄":
				System.out.println("마약범죄를 선택하셨습니다.");
				where = "마약범죄";
				break;
				
			case "보건범죄":
				System.out.println("보건범죄를 선택하셨습니다.");
				where = "보건범죄";
				break;
				
			case "환경범죄":
				System.out.println("환경범죄를 선택하셨습니다.");
				where = "환경범죄";
				break;
				
			case "교통범죄":
				System.out.println("교통범죄를 선택하셨습니다.");
				where = "교통범죄";
				break;
				
			case "노동범죄":
				System.out.println("노동범죄를 선택하셨습니다.");
				where = "노동범죄";
				break;
				
			case "안보범죄":
				System.out.println("안보범죄를 선택하셨습니다.");
				where = "안보범죄";
				break;
				
			case "선거범죄":
				System.out.println("선거범죄를 선택하셨습니다.");
				where = "선거범죄";
				break;
				
			case "병역범죄":
				System.out.println("병역범죄를 선택하셨습니다.");
				where = "병역범죄";
				break;
				
			case "기타범죄":
				System.out.println("기타범죄를 선택하셨습니다.");
				where = "기타범죄";
				break;
			
			default:
				System.out.println("입력한 값이 잘못되었습니다");
				break;
			} 
			
			System.out.println("요일 또는 시간을 선택하세요.");
			String answer = in.nextLine();
			
			switch (answer) {
			case "요일": 
				System.out.println("요일을 선택하셨습니다.");
				System.out.println("월요일/화요일/수요일/목요일/금요일/토요일/일요일 중 하나를 선택해주세요.");
				TorD = in.nextLine();
				variable = TorD;
				break;
			
			
			case "시간":
				System.out.println("시간을 선택하셨습니다.");
				System.out.println("오전 또는 오후를 선택하여 주세요.");
				String var = in.nextLine();
				
				switch(var) {
				case "오전":
					System.out.println("오전을 선택하였습니다.");
					System.out.println("0시 - 2시 59분은 '1', 3시 - 5시 59분은 '2', 6시 - 8시 59분은 '3', 9시 - 11시 59분은 '4'를 입력해주세요.");
					int n = in.nextInt();
				
					if(n == 1) {
						System.out.println("0시 - 2시 59분을 선택하셨습니다.");
						TorD = "firstTime";
						variable = "0시 - 2시 59분";
					}
					
					else if(n == 2) {
						System.out.println("3시 - 5시 59분을 선택하셨습니다.");
						TorD = "secondTime";
						variable = "3시 - 5시 59분";
					}
					
					else if(n == 3) {
						System.out.println("6시 - 8시 59분을 선택하셨습니다.");
						TorD = "thirdTime";
						variable = "6시 - 8시 59분";
					}
					
					else if(n == 4) {
						System.out.println("9시 - 11시 59분을 선택하셨습니다.");
						TorD = "fourthTime";
						variable = "9시 - 11시 59분";
					}
					
					else {
						System.out.println("잘못된 값을 입력하였습니다.");
						break;
					}
					break;
				
					
				case "오후":
					System.out.println("오후을 선택하였습니다.");
					System.out.println("12시 - 14시 59분은 '1', 15시 - 17시 59분은 '2', 18시 - 20시 59분은 '3', 21시 - 23시 59분은 '4'를 입력해주세요.");
					n = in.nextInt();
					
					if(n == 1) {
						System.out.println("12시 - 14시 59분을 선택하셨습니다.");
						TorD = "fifthTime";
						variable = "12시 - 14시 59분";
					}
					
					else if(n == 2) {
						System.out.println("15시 - 17시 59분을 선택하셨습니다.");
						TorD = "sixthTime";
						variable = "15시 - 17시 59분";
					}
					
					else if(n == 3) {
						System.out.println("18시 - 20시 59분을 선택하셨습니다.");
						TorD = "seventhTime";
						variable = " 18시 - 20시 59분";
					}
					
					else if(n == 4) {
						System.out.println("21시 - 23시 59분을 선택하셨습니다.");
						TorD = "eightTime";
						variable = "21시 - 23시 59분";
					}
					
					else {
						System.out.println("잘못된 값을 입력하였습니다.");
						break;
					}
					break;
					
				default:
						System.out.println("잘못된 값을 입력하였습니다.");
						break;
				}
				
			default:
				break;
			}
		
			String str = null;
			
			switch(answer) {
			case "요일":
				DayAverageSQL = "SELECT 일요일,월요일,화요일,수요일,목요일,금요일,토요일 FROM crime_status WHERE middle_class =" + "'" + where + "'";
				ResultSet dayAvg = stmt.executeQuery(DayAverageSQL);
				str = "각 요일";
				while(dayAvg.next()) {
					for	(int i = 1; i <= 7; i++) {
						Average += dayAvg.getDouble(i);
					}
					Average /= 7;
				}
				break;
				
			case "시간":
				TimeAverageSQL = "SELECT firstTime,secondTime,thirdTime,fourthTime,fifthTime,sixthTime,seventhTime,eightTime FROM crime_status WHERE middle_class =" + "'" + where + "'";
				ResultSet TimeAvg = stmt.executeQuery(TimeAverageSQL);
				str = "24시간";
					while(TimeAvg.next()) {
						for	(int i = 1; i <= 8; i++) {
							Average += TimeAvg.getDouble(i);
						}
					Average /= 8;
					}
					break;
					
			
			default:
				break;
			}
			
			String sql = "select " + TorD + " FROM crime_status WHERE middle_class =" + "'" + where + "'";
			rs = stmt.executeQuery(sql);
			String alert = null;
			while (rs.next()) {
				
				if (rs.getInt(1) < Average) {
					alert = where + "이(가) 발생할 확률이 평균 이하입니다!";
					bw.write("<h2>" + where + "이(가) " + variable + "에 발생한 횟수 <br> <h2 class=\"greencolor\">" + rs.getString(1) + "회 </h2> </h2>" + "<img src=\"./img/safe.png\" class=\"img\">" + "<h2>" + alert + "</h2>");
				} else if (rs.getInt(1) > Average) {
					alert = "주의 : " + where + "이(가) 발생할 확률이 평균 이상입니다!";
					bw.write("<h2>" + where + "이(가) " + variable + "에 발생한 횟수 <br> <h2 class=\"redcolor\">" + rs.getString(1) + "회 </h2> </h2>" + "<img src=\"./img/warning.png\" class=\"img\">" + "<h2>" + alert + "</h2>");
				} else if(rs.getInt(1) == Average) {
					alert = where + " 이(가) 발생할 확률이 평균값입니다!";
					bw.write("<h2>" + where + "이(가) 발생한 횟수 : " + rs.getString(1) + "회" + "</h2>" + "<h2>" + alert + "</h2>");
				}
			}
			
			int DoubleToAverage;
			DoubleToAverage = (int) Average;
			
			bw.write( 
			 "<label>평균값 확인하기 ➜ </label> <button id=\"popup_open_btn\">AVERAGE</button>"
			+ " </div> "
			+ "</div>"
			+ "<div class=\"background\"></div>\r\n"
			+ "</body>\r\n"
			+ "<div id=\"my_modal\">\r\n"
			+ 		"선택하신 " + where + "의 " + str + " 평균은 약 " + DoubleToAverage +"회 입니다."
			+ "     <a class=\"modal_close_btn\">×</a>\r\n"
			+ "<script src=\"scrip.js\"></script>\r\n"
			+ "</html>"); 	

			System.out.println("파일 쓰기 완료!");

			bw.close();
			osw.close();
			fos.close();

			if (file.exists())
				Desktop.getDesktop().open(file);

		} catch (IOException e) {
			System.out.println("파일 입출력 오류");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null && !stmt.isClosed()) {
					stmt.close();
				}
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
