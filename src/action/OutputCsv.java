package action;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.EmploInfoDAO;

public class OutputCsv implements CommonLogic{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//それぞれのクラスをインスタンス化
		StringBuilder sb = new StringBuilder();
		EmploInfoDAO dao = new EmploInfoDAO();
		List<String> empList = new ArrayList<String>();
		List<String> emploList = new ArrayList<String>();
		//送られてきた社員番号のリストをlistに格納
		String list = request.getParameter("emploList");
		//listの中で不要な箇所を削除
		sb.append(list);
		sb.delete(0,1);
		sb.delete(sb.length() - 1, sb.length());
		list = String.valueOf(sb);
		//sbを空にする
		sb.delete(0, sb.length());
		//listを,区切りごとに配列listsに格納
		String[] lists = list.split(", ");
		//listsの内容をListに格納
		for(int i = 0; i < lists.length; i++) {
			empList.add(lists[i]);
		}
		//daoにて社員番号を元に社員情報を取得しemploListに格納
		emploList = dao.emploInfoCsv(empList);

		//以下でCSVダウウンロードする
		try (
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(baos));
				){
			for(String emList : emploList) {
				bw.write(emList);
				bw.write("\n");
			}
			bw.flush();
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=\"lastLesson.csv\"");
			response.setContentLength(baos.size());
			ServletOutputStream os = response.getOutputStream();
			os.write(baos.toByteArray());
			os.flush();
			os.close();
		}catch(Exception a){
			a.printStackTrace();
		}

		//結果を表示
		return "emploInfo.jsp";
	}
}