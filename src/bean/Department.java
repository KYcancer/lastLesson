package bean;

import java.io.Serializable;

public class Department implements Serializable{
	// フィールドの定義
	private static final long serialVersionUID = 1L;
	private String departID = null; //部署ID
	private String departName = null; //部署名
	private String error = null; //エラー
	private String mode = null; //モード

	// コンストラクタ（引数なし）
	public Department() {
		super();
	}
	// コンストラクタ（DepartmentDAO用）
	public Department(String departID, String departName) {
		this.departID = departID;
		this.departName = departName;
	}
	// コンストラクタ（DepartEditLogic用）
	public Department(String departID, String departName, String error) {
		this.departID = departID;
		this.departName = departName;
		this.error = error;
	}
	public Department(String departID, String departName, String error, String mode) {
		this.departID = departID;
		this.departName = departName;
		this.error = error;
		this.mode = mode;
	}
	//getter
	public String getDepartID() {
		return departID;
	}
	public String getDepartName() {
		return departName;
	}
	public String getError() {
		return error;
	}
	public String getMode() {
		return mode;
	}
}
