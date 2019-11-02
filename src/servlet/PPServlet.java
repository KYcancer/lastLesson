package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.CommonLogic;
import action.DepartEditLogic;
import action.DepartmentLogic;
import action.EmInEditLogic;
import action.EmploInfoLogic;
import action.EmploInfoSearchLogic;
import action.OutputCsv;

@WebServlet("/PPServlet")
public class PPServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public PPServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    String action = request.getParameter("action");
	    CommonLogic logic = null;

	    String next = "/WEB-INF/jsp/";

	    switch(action) {
	    //社員情報一覧表示
	    case "a":
	    	logic = new EmploInfoLogic();
	    	break;
	    //社員情報検索
	    case "b":
	    	logic = new EmploInfoSearchLogic();
	    	break;
	    //社員情報新規追加及び編集
	    case "c":
	    	logic = new EmInEditLogic();
	    	break;
	    //部署一覧表示
	    case "d":
	    	logic = new DepartmentLogic();
	    	break;
	    //部署名編集
	    case "e":
	    	logic = new DepartEditLogic();
	    	break;
	    //CSVをダウンロード
	    case "f":
	    	logic = new OutputCsv();
	    	break;
		}
	    next += logic.execute(request, response);
	    RequestDispatcher dispatcher = request.getRequestDispatcher(next);
	    dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
