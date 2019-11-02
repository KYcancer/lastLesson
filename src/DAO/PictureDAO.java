package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PictureDAO {
	private final String DRIVER_NAME = "org.h2.Driver";
    private final String JDBC_URL = "jdbc:h2:~/codecamp";
    private final String DB_USER = "sa";
    private final String DB_PASS = "";
    StringBuilder sb = new StringBuilder();
    //社員情報作成時の証明写真追加メソッド
    public String add(String pictureDate) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String picture = pictureDate;
        String pictureID = null;
        String sql;
        try {
            // JDBCドライバを読み込み
            Class.forName(DRIVER_NAME);
            // データベースへ接続
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            // SELECT文を準備
            sql = "SELECT 写真ID FROM 社員情報";
            // 準備したSQLをデータベースに届けるPrepareStatementインスタンスを取得する
            pstmt = conn.prepareStatement(sql);
            // SQLを実行し、結果はResultSetインスタンスに格納される
            rs = pstmt.executeQuery();
            // 結果を１レコードづつ取得する
            while (rs.next()) {
                // 取得したレコードから「写真ID」項目のデータを取得する
            	pictureID = rs.getString("写真ID");
            }
            //新しいpictureIDを作成
            sb.append(pictureID);
            sb.delete(0, 1);
            pictureID = String.valueOf(sb);
            sb.delete(0, pictureID.length());
            int PID = Integer.parseInt(pictureID);
            PID++;
            pictureID = String.format("%05d", PID);
            sb.append(pictureID);
            sb.insert(0,"P");
            pictureID = String.valueOf(sb);
            // INSERT文を準備
            sql = "INSERT INTO 証明写真 VALUES (\'" + pictureID + "\',\'" + picture + "\')";
            // 準備したSQLをデータベースに届けるPrepareStatementインスタンスを取得する
            pstmt = conn.prepareStatement(sql);
            // SQLを実行し、結果はresultに格納される
            int result = pstmt.executeUpdate();
            if (result != 1) {
                return null;
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


        return pictureID;
    }
    //社員情報編集時の写真データ取得メソッド
    public String picserch(String PictureID) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String picture = null;
        String pictureID = PictureID;
        String sql;
        try {
            // JDBCドライバを読み込み
            Class.forName(DRIVER_NAME);
            // データベースへ接続
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            // SELECT文を準備
            sql = "SELECT * FROM 証明写真 WHERE 写真ID=\'" + pictureID + "\'";
            // 準備したSQLをデータベースに届けるPrepareStatementインスタンスを取得する
            pstmt = conn.prepareStatement(sql);
            // SQLを実行し、結果はResultSetインスタンスに格納される
            rs = pstmt.executeQuery();
            // 結果を１レコードづつ取得する
            while (rs.next()) {
                // 取得したレコードから「画像データ」項目のデータを取得する
            	picture = rs.getString("画像データ");
            }
            return picture;
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
    //社員情報削除時の該当する社員の証明写真を削除するメソッド
    public boolean delete(String PictureID) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result;
        String pictureID = PictureID;
        String sql;
        try {
            // JDBCドライバを読み込み
            Class.forName(DRIVER_NAME);
            // データベースへ接続
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            // DELETE文を準備
            sql = "DELETE FROM 証明写真 WHERE 写真ID=\'" + pictureID + "\'";
            // 準備したSQLをデータベースに届けるPrepareStatementインスタンスを取得する
            pstmt = conn.prepareStatement(sql);
            // SQLを実行し、結果はResultSetインスタンスに格納される
            result = pstmt.executeUpdate();
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
            // PrepareStatementインスタンス、ResultSetインスタンスのクローズ処理
            if (pstmt != null) {
                try {
                	pstmt.close();
                } catch (SQLException e) {
                    // クローズ処理失敗時の処理
                    e.printStackTrace();
                    return false;
                }
            }
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
    public boolean update(String picture, String originalPicture) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // JDBCドライバを読み込み
            Class.forName(DRIVER_NAME);
            // データベースへ接続
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            // UPDATE文を準備
            String sql = "UPDATE 証明写真 SET 画像データ = \'" + picture + "\' WHERE 画像データ = \'" + originalPicture + "\'";
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
    public String getPID(String picture) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String pictureID = "";
        try {
            // JDBCドライバを読み込み
            Class.forName(DRIVER_NAME);
            // データベースへ接続
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            // SELECT文を準備
            String sql = "SELECT 写真ID FROM 証明写真 WHERE 画像データ = \'" + picture + "\'";
            // 準備したSQLをデータベースに届けるPrepareStatementインスタンスを取得する
            pstmt = conn.prepareStatement(sql);
            // SQLを実行し、結果はResultSetインスタンスに格納される
            rs = pstmt.executeQuery();
            // 結果を１レコードづつ取得する
            while (rs.next()) {
                // 取得したレコードから「写真ID」項目のデータを取得する
                pictureID = rs.getString("写真ID");
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
        return pictureID;
    }
}
