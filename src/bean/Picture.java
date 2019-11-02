package bean;

import java.io.Serializable;

public class Picture implements Serializable{
	// フィールドの定義
	private static final long serialVersionUID = 1L;
	private String pictureID = null; //写真ID
	private String picture = null; //写真

	// コンストラクタ（引数なし）
	public Picture() {
		super();
	}
	// コンストラクタ（DepartmentDAO用）
	public Picture(String pictureID) {
		this.pictureID = pictureID;
	}
	//getter
	public String getDepartID() {
		return pictureID;
	}
	public String getDepartName() {
		return picture;
	}
}
