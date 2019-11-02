package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import action.EmploInfoSearchLogic;
import bean.EmploInfo;

public class EmploInfoDAO {
    private final String DRIVER_NAME = "org.h2.Driver";
    private final String JDBC_URL = "jdbc:h2:~/codecamp";
    private final String DB_USER = "sa";
    private final String DB_PASS = "";
    //社員一覧表示と社員検索で使う社員情報をDBから取得するメソッド
    public List<EmploInfo> emploInfoList() {
        List<EmploInfo> sList = new ArrayList<EmploInfo>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql;
        try {
            // JDBCドライバを読み込み
            Class.forName(DRIVER_NAME);
            // データベースへ接続
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            // SELECT文を準備
            if(EmploInfoSearchLogic.check == 0) {
            	sql = "SELECT 社員番号,名前,部署ID FROM 社員情報";
            }else {
            	sql = EmploInfoSearchLogic.sql;
            }
            // 準備したSQLをデータベースに届けるPrepareStatementインスタンスを取得する
            pstmt = conn.prepareStatement(sql);
            //sqlの中身をクリア
            sql = "";
            //EmploInfoSearchLogicのcheckを0に戻す
            EmploInfoSearchLogic.check = 0;
            // SQLを実行し、結果はResultSetインスタンスに格納される
            rs = pstmt.executeQuery();
            // 結果を１レコードづつ取得する
            while (rs.next()) {
                // 取得したレコードから「社員番号」項目のデータを取得する
                String emploID = rs.getString("社員番号");
                // 取得したレコードから「名前」項目のデータを取得する
                String name = rs.getString("名前");
             // 取得したレコードから「名前」項目のデータを取得する
                String departmentID = rs.getString("部署ID");
                // EmploInfoインスタンスにデータを保存する
                EmploInfo ei = new EmploInfo(emploID, name, departmentID);
                // リストにEmploInfoインスタンスを追加する
                sList.add(ei);
            }
        } catch (SQLException e) {
            // データベース接続やSQL実行失敗時の処理
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            // JDBCドライバが見つからなかったときの処理
            e.printStackTrace();
            return null;
        } finally {
            // PrepareStatementインスタンス、ResultSetインスタンスのクローズ処理
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    // クローズ処理失敗時の処理
                    e.printStackTrace();
                    return null;
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    // クローズ処理失敗時の処理
                    e.printStackTrace();
                    return null;
                }
            }
            // データベース切断
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // データベース切断失敗時の処理
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return sList;
    }
    //CSV出力用の社員情報をDBから取得するメソッド
    public List<String> emploInfoCsv(List<String> lists) {
        List<String> sList = new ArrayList<String>();
        // デフォルトのSELECT文を準備
        String sql = "SELECT * FROM 社員情報 WHERE 社員番号=\'";
        StringBuilder sb = new StringBuilder();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // JDBCドライバを読み込み
            Class.forName(DRIVER_NAME);
            // データベースへ接続
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            // SELECT文に追加
            for(int i = 0; i < lists.size(); i++) {
            	sql += lists.get(i) + "\' or 社員番号=\'";
            }
            //SELECT文の余分な箇所を削除
            sb.append(sql);
            sb.delete(sb.length() - 10, sb.length());
            sql = String.valueOf(sb);
            sb.delete(0,sb.length());
            // 準備したSQLをデータベースに届けるPrepareStatementインスタンスを取得する
            pstmt = conn.prepareStatement(sql);
            // SQLを実行し、結果はResultSetインスタンスに格納される
            rs = pstmt.executeQuery();
            // 結果を１レコードづつ取得する
            while (rs.next()) {
                // 取得したレコードからそれぞれのデータを取得する
                String emploID = rs.getString("社員番号");
                String name = rs.getString("名前");
                String age = rs.getString("年齢");
                String gender = rs.getString("性別");
                String streetAddress = rs.getString("住所");
                String departmentID = rs.getString("部署ID");
                String hireDate = rs.getString("入社日");
                String leaveDate = rs.getString("退社日");
                // 取得したデータを,を付けて文字列にする
                String ei = emploID + "," + name + "," + age + "," + gender + "," + streetAddress + "," +
                							departmentID + "," + hireDate + "," + leaveDate + ",";
                // リストに文字列を追加する
                sList.add(ei);
            }
        } catch (SQLException e) {
            // データベース接続やSQL実行失敗時の処理
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            // JDBCドライバが見つからなかったときの処理
            e.printStackTrace();
            return null;
        } finally {
            // PrepareStatementインスタンス、ResultSetインスタンスのクローズ処理
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    // クローズ処理失敗時の処理
                    e.printStackTrace();
                    return null;
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    // クローズ処理失敗時の処理
                    e.printStackTrace();
                    return null;
                }
            }
            // データベース切断
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // データベース切断失敗時の処理
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return sList;
    }
    //社員追加及び修正メソッド
    public boolean add(String emploID, String name, String age, String gender,
    		String picture, String address, String departID, String hireDate) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // JDBCドライバを読み込み
            Class.forName(DRIVER_NAME);
            // データベースへ接続
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            // INSERT文を準備
            String sql = "INSERT INTO 社員情報 (社員番号,名前,年齢,性別,写真ID,住所,部署ID,入社日,退社日) VALUES (?,?,?,?,?,?,?,?,?)";
            // 準備したSQLをデータベースに届けるPrepareStatementインスタンスを取得する
            pstmt = conn.prepareStatement(sql);
            // INSERT文の？に使用する値を設定
            pstmt.setString(1, emploID);
            pstmt.setString(2, name);
            pstmt.setString(3, age);
            pstmt.setString(4, gender);
            pstmt.setString(5, picture);
            pstmt.setString(6, address);
            pstmt.setString(7, departID);
            pstmt.setString(8, hireDate);
            pstmt.setString(9, "null");
            // SQLを実行し、結果はresultに格納される
            int result = pstmt.executeUpdate();
            if (result != 1) {
                return false;
            }
        } catch (SQLException e) {
            // データベース接続やSQL実行失敗時の処理
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            // JDBCドライバが見つからなかったときの処理
            e.printStackTrace();
            return false;
        } finally {
            // PrepareStatementインスタンスのクローズ処理
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    // クローズ処理失敗時の処理
                    e.printStackTrace();
                    return false;
                }
            }
            // データベース切断
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // データベース切断失敗時の処理
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }
    //社員情報削除メソッド
    public boolean delete(String emploID) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // JDBCドライバを読み込み
            Class.forName(DRIVER_NAME);
            // データベースへ接続
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            // DELETE文を準備
            String sql = "DELETE FROM 社員情報 WHERE 社員番号=\'" + emploID + "\'";
            // 準備したSQLをデータベースに届けるPrepareStatementインスタンスを取得する
            pstmt = conn.prepareStatement(sql);
            // SQLを実行し、結果はResultSetインスタンスに格納される
            int result = pstmt.executeUpdate();
            if (result != 1) {
                return false;
            }
        } catch (SQLException e) {
            // データベース接続やSQL実行失敗時の処理
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            // JDBCドライバが見つからなかったときの処理
            e.printStackTrace();
            return false;
        } finally {
            // PrepareStatementインスタンスのクローズ処理
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    // クローズ処理失敗時の処理
                    e.printStackTrace();
                    return false;
                }
            }
            // データベース切断
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // データベース切断失敗時の処理
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }
    //社員情報編集用の一人分の社員情報を取得するメソッド
    public EmploInfo employee(String EmploID) {
        EmploInfo emploInfo;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String eisl = EmploInfoSearchLogic.sql;
        int check = EmploInfoSearchLogic.check;
        String sql;
        String name = null;
        String age = null;
        String gender = null;
        String pictureID = null;
        String streetAddress = null;
        String departmentID = null;
        String hireDate = null;
        String leaveDate = null;
        try {
            // JDBCドライバを読み込み
            Class.forName(DRIVER_NAME);
            // データベースへ接続
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            // SELECT文を準備
            if(check == 0) {
            	sql = "SELECT * FROM 社員情報 WHERE 社員番号=\'" + EmploID + "\'";
            }else {
            	sql = eisl;
            }
            // 準備したSQLをデータベースに届けるPrepareStatementインスタンスを取得する
            pstmt = conn.prepareStatement(sql);
            // SQLを実行し、結果はResultSetインスタンスに格納される
            rs = pstmt.executeQuery();
            // 結果を１レコードづつ取得する
                // 取得したレコードから「社員番号」項目のデータを取得する
                String emploID = EmploID;
                // 取得したレコードからそれぞれのデータを取得する
                while (rs.next()) {
	                name = rs.getString("名前");
	                age = rs.getString("年齢");
	                gender = rs.getString("性別");
	                pictureID = rs.getString("写真ID");
	                streetAddress = rs.getString("住所");
	                departmentID = rs.getString("部署ID");
	                hireDate = rs.getString("入社日");
	                leaveDate = rs.getString("退社日");
                }
                // EmploInfoインスタンスにデータを保存する
                emploInfo = new EmploInfo(emploID, name, age, gender, pictureID, streetAddress,
                		departmentID, hireDate, leaveDate);
                // EmploInfoインスタンスを返す
                return emploInfo;


        } catch (SQLException e) {
            // データベース接続やSQL実行失敗時の処理
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            // JDBCドライバが見つからなかったときの処理
            e.printStackTrace();
            return null;
        } finally {
            // PrepareStatementインスタンス、ResultSetインスタンスのクローズ処理
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    // クローズ処理失敗時の処理
                    e.printStackTrace();
                    return null;
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    // クローズ処理失敗時の処理
                    e.printStackTrace();
                    return null;
                }
            }
            // データベース切断
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // データベース切断失敗時の処理
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
    public boolean update(String emploID, String name, String age, String gender, String pictureID, String address, String departID, String hireDate, String leaveDate, String change) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String where = null;
        String sql = "UPDATE 社員情報 SET";
        try {
            // JDBCドライバを読み込み
            Class.forName(DRIVER_NAME);
            // データベースへ接続
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            // UPDATE文を準備
            Pattern p = Pattern.compile("'$");
            String[] split = change.split(",");
            for(String splits : split) {
            	if(splits.equals("emploID")) {
            		sql += " 社員番号 = \'" + emploID + "\'";
            	}else if(!(splits.equals("emploID")) && !(splits.equals("")) && where == null){
            		where = " WHERE 社員番号 = \'" + emploID + "\'";
            	}
            	if(splits.equals("name")) {
            		Matcher m = p.matcher(sql);
            		if(m.find()) {
            			sql += ", 名前 = \'" + name + "\'";
            		}else {
            			sql += " 名前 = \'" + name + "\'";
            		}
            	}else if(!(splits.equals("name")) && !(splits.equals("")) && where == null){
            		where = " WHERE 名前 = \'" + name + "\'";
            	}
            	if(splits.equals("age")) {
            		Matcher m = p.matcher(sql);
            		if(m.find()) {
            			sql += ", 年齢 = \'" + age + "\'";
            		}else {
            			sql += " 年齢 = \'" + age + "\'";
            		}
            	}
            	if(splits.equals("gender")) {
            		Matcher m = p.matcher(sql);
            		if(m.find()) {
            			sql += ", 性別 = \'" + gender + "\'";
            		}else {
            			sql += " 性別 = \'" + gender + "\'";
            		}
            	}
            	if(splits.equals("picture")) {
            		Matcher m = p.matcher(sql);
            		if(m.find()) {
            			sql += ", 写真ID = \'" + pictureID + "\'";
            		}else {
            			sql += " 写真ID = \'" + pictureID + "\'";
            		}
            	}else if(!(splits.equals("picture")) && !(splits.equals("")) && where == null){
            		where = " WHERE 写真ID = \'" + pictureID + "\'";
            	}
            	if(splits.equals("postalCode")) {
            		Matcher m = p.matcher(sql);
            		if(m.find()) {
            			sql += ", 住所 = \'" + address + "\'";
            		}else {
            			sql += " 住所 = \'" + address + "\'";
            		}
            	}
            	if(splits.equals("prefecture")) {
            		Matcher m = p.matcher(sql);
            		if(m.find()) {
            			sql += ", 住所 = \'" + address + "\'";
            		}else {
            			sql += " 住所 = \'" + address + "\'";
            		}
            	}
            	if(splits.equals("address")) {
            		Matcher m = p.matcher(sql);
            		if(m.find()) {
            			sql += ", 住所 = \'" + address + "\'";
            		}else {
            			sql += " 住所 = \'" + address + "\'";
            		}
            	}else if(!(splits.equals("address")) && !(splits.equals("")) && where == null){
            		where = " WHERE 住所 = \'" + address + "\'";
            	}
            	if(splits.equals("departID")) {
            		Matcher m = p.matcher(sql);
            		if(m.find()) {
            			sql += ", 部署ID = \'" + departID + "\'";
            		}else {
            			sql += " 部署ID = \'" + departID + "\'";
            		}
            	}
            	if(splits.equals("hireDate")) {
            		Matcher m = p.matcher(sql);
            		if(m.find()) {
            			sql += ", 入社日 = \'" + hireDate + "\'";
            		}else {
            			sql += " 入社日 = \'" + hireDate + "\'";
            		}
            	}
            	if(splits.equals("leaveDate")) {
            		Matcher m = p.matcher(sql);
            		if(m.find()) {
            			sql += ", 退社日 = \'" + leaveDate + "\'";
            		}else {
            			sql += " 退社日 = \'" + leaveDate + "\'";
            		}
            	}
            }
            sql += where;
            // 準備したSQLをデータベースに届けるPrepareStatementインスタンスを取得する
            pstmt = conn.prepareStatement(sql);
            // SQLを実行し、結果はResultSetインスタンスに格納される
            int result = pstmt.executeUpdate();
            if (result != 1) {
                return false;
            }
        } catch (SQLException e) {
            // データベース接続やSQL実行失敗時の処理
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            // JDBCドライバが見つからなかったときの処理
            e.printStackTrace();
            return false;
        } finally {
            // PrepareStatementインスタンスのクローズ処理
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    // クローズ処理失敗時の処理
                    e.printStackTrace();
                    return false;
                }
            }
            // データベース切断
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // データベース切断失敗時の処理
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }
}
