package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.DepartmentDAO;
import DAO.EmploInfoDAO;
import bean.Department;
import bean.EmploInfo;


public class EmploInfoSearchLogic implements CommonLogic{

	public static String sql = null;
	//社員検索から来た事を知らせるチェック
	public static int check = 0;
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//リクエストスコープからそれぞれ取得
		String pref = request.getParameter("pref");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String mode = request.getParameter("mode");
		//社員情報取得用のDAOをインスタンス化
		EmploInfoDAO dao = new EmploInfoDAO();
		//部署名プルダウンメニュー用にDAOから部署名を取得しListに格納
		DepartmentDAO dao2 = new DepartmentDAO();
		List<Department> departList = dao2.DepartmentList();
		//最初にこのメソッドに入ってきた場合
		if((pref == null || pref.equals("")) && (mode == null || mode.equals(""))) {
			//リクエストスコープに部署名Listを持たせる
			request.setAttribute("departList", departList);
			return "emploInfoSearch.jsp";
		//社員検索のSQL作成
		}else if(pref != null || !(pref.equals("")) || id != null || !(id.equals("")) || name != null || !(name.equals(""))) {
			//部署名、社員番号、名前が渡された場合
			if(pref != null && !(pref.equals("")) && id != null && !(id.equals("")) && name != null && !(name.equals(""))) {
				//SQL文を作成
				sql = "SELECT * FROM 社員情報 WHERE 部署ID = \'" + pref + "\' AND 社員番号 = \'" + id + "\' AND 名前 LIKE \'%" + name + "%\'";
			//部署名、社員番号が渡された場合
			}else if(pref != null && !(pref.equals("")) && id != null && !(id.equals(""))) {
				//SQL文を作成
				sql = "SELECT * FROM 社員情報 WHERE 部署ID = \'" + pref + "\' AND 社員番号 = \'" + id + "\'";
			//部署名、名前が渡された場合
			}else if(pref != null && !(pref.equals("")) && name != null && !(name.equals(""))) {
				//SQL文を作成
				sql = "SELECT * FROM 社員情報 WHERE 部署ID = \'" + pref + "\' AND 名前 LIKE \'%" + name + "%\'";
			//部署名だけが渡された場合
			}else if(pref != null && !(pref.equals(""))){
				//SQL文を作成
				sql = "SELECT * FROM 社員情報 WHERE 部署ID = \'" + pref + "\'";
			}
			check = 1;
			//DAOにて社員検索一覧に必要な社員情報を取得しbeanに格納
			List<EmploInfo> emploInfoList = dao.emploInfoList();
			//beanをリクエストスコープに持たせる
	        request.setAttribute("emploInfoList", emploInfoList);
	        return "emploInfo.jsp";
		}
		//リクエストスコープに部署名Listを持たせる
		request.setAttribute("departList", departList);
        return "emploInfoSearch.jsp";
    }
}
