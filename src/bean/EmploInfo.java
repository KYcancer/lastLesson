package bean;

import java.io.Serializable;
import java.util.List;

public class EmploInfo implements Serializable{
	// フィールドの定義
	private static final long serialVersionUID = 1L;
	Department department;
	private String emploID; //社員番号
    private String name; //名前
    private String age; //年齢
    private String gender; //性別
    private String pictureID; //写真ID
    private String streetAddress; //住所
    private String departmentID; //部署ID
    private String hireDate; //入社日
    private String leaveDate; //退社日
    private String picture; //写真
    private String postalCode; //郵便番号
    private String prefecture; //都道府県
    private String address; //都道府県以降の住所
    private List<Department> departList; //部署リスト
    private String error; //エラー
    private String mode; //モード

    // コンストラクタ（引数なし）
    public EmploInfo() {
        super();
    }
    // コンストラクタ（EmploinfoDAO用）
    public EmploInfo(String emploID, String name){
        this.emploID = emploID;
        this.name = name;
    }
 // コンストラクタ（EmploinfoDAO用）
    public EmploInfo(String emploID, String name, String departmentID){
        this.emploID = emploID;
        this.name = name;
        this.departmentID = departmentID;
    }

    // コンストラクタ (CSV用)
    public EmploInfo(
    		String emploID, String name, String age, String gender,
    		String pictureID, String streetAddress, String departmentID,
    		String hireDate, String leaveDate){
    	this.emploID = emploID;
    	this.name = name;
    	this.age = age;
    	this.gender = gender;
    	this.pictureID = pictureID;
    	this.streetAddress = streetAddress;
    	this.departmentID = departmentID;
    	this.hireDate = hireDate;
    	this.leaveDate = leaveDate;
    }
    // コンストラクタ (EmInEditLogic用)
    public EmploInfo(
    		String emploID, String name, String age, String gender,String picture,
    		String postalCode, String prefecture, String address,String departmentID,
    		String hireDate, String leaveDate, List<Department> departList, String error, String mode ){
    	this.emploID = emploID;
    	this.name = name;
    	this.age = age;
    	this.gender = gender;
    	this.picture = picture;
    	this.postalCode = postalCode;
    	this.prefecture = prefecture;
    	this.address = address;
    	this.departmentID = departmentID;
    	this.hireDate = hireDate;
    	this.leaveDate = leaveDate;
    	this.departList = departList;
    	this.error = error;
    	this.mode = mode;
    }
	//getterメソッド
    public String getEmploID() {
        return emploID;
    }
    public String getName() {
        return name;
    }
	public String getAge() {
		return age;
	}
	public String getGender() {
		return gender;
	}
	public String getPictureID() {
		return pictureID;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public String getDepartmentID() {
		return departmentID;
	}
	public String getHireDate() {
		return hireDate;
	}
	public String getLeaveDate() {
		return leaveDate;
	}
	public List<Department> getDepartList(){
		return departList;
	}
	public String getPicture() {
		return picture;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public String getPrefecture() {
		return prefecture;
	}
	public String getAddress() {
		return address;
	}
	public String getError() {
		return error;
	}
	public String getMode() {
		return mode;
	}
}
