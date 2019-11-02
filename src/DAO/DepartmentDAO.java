package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Department;

public class DepartmentDAO {

    private final String DRIVER_NAME = "org.h2.Driver";
    private final String JDBC_URL = "jdbc:h2:~/codecamp";
    private final String DB_USER = "sa";
    private final String DB_PASS = "";
    //部署一覧表示、部署検索用及び部署名プルダウン用メソッド
    public List<Department> DepartmentList() {
        List<Department> sList = new ArrayList<Department>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // JDBCドライバを読み込み
            Class.forName(DRIVER_NAME);
            // データベースへ接続
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            // SELECT文を準備
            String sql = "SELECT * FROM 部署";
            // 準備したSQLをデータベースに届けるPrepareStatementインスタンスを取得する
            pstmt = conn.prepareStatement(sql);
            // SQLを実行し、結果はResultSetインスタンスに格納される
            rs = pstmt.executeQuery();
            // 結果を１レコードづつ取得する
            while (rs.next()) {
                // 取得したレコードから「部署ID」項目のデータを取得する
                String departID = rs.getString("部署ID");
                // 取得したレコードから「部署名」項目のデータを取得する
                String departName = rs.getString("部署名");
                // Departmentインスタンスにデータを保存する
                Department ei = new Department(departID, departName);
                // リストにDepartmentインスタンスを追加する
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
    //部署追加メソッド
    public boolean add(Department department) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // JDBCドライバを読み込み
            Class.forName(DRIVER_NAME);
            // データベースへ接続
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            String sql = "SELECT * FROM 部署";
            // 準備したSQLをデータベースに届けるPrepareStatementインスタンスを取得する
            pstmt = conn.prepareStatement(sql);
            // SQLを実行し、結果はResultSetインスタンスに格納される
            rs = pstmt.executeQuery();
            //新しい部署ID作成
            int count = 1;
            while(rs.next()) {
            	count++;
            }
            String departID = String.valueOf(count);
            departID = "D0" + departID;
            // INSERT文を準備
            sql = "INSERT INTO 部署 (部署ID,部署名) VALUES (?,?)";
            // 準備したSQLをデータベースに届けるPrepareStatementインスタンスを取得する
            pstmt = conn.prepareStatement(sql);
            // INSERT文の？に使用する値を設定
            pstmt.setString(1, departID);
            pstmt.setString(2, department.getDepartName());
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
    //	部署編集メソッド
    public boolean update(String newName, String departName) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // JDBCドライバを読み込み
            Class.forName(DRIVER_NAME);
            // データベースへ接続
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            // UPDATE文を準備
            String sql = "UPDATE 部署 SET 部署名 = \'" + newName + "\' WHERE 部署名 = \'" + departName + "\'";
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
    //部署削除メソッド
    public boolean delete(String departName) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // JDBCドライバを読み込み
            Class.forName(DRIVER_NAME);
            // データベースへ接続
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            // DELETE文を準備
            String sql = "DELETE FROM 部署 WHERE 部署名=\'" + departName + "\'";
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
