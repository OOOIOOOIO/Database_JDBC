package dto;

public class UserDTO {
	//Alt + Shift + A > 커서 이동으로 영역 선택 : 여러줄 동시에 수정
	//끝났으면 다시 Alt + Shift + A로 빠져나오기
	public String userid;
	public String userpw;
	public String username;
	public int userage;
	public String userphone;
	public String useraddr;
	
	public UserDTO() {}
	
	public UserDTO(String userid, String userpw, String username, int userage, String userphone, String useraddr) {
		this.userid = userid;
		this.userpw = userpw;
		this.username = username;
		this.userage = userage;
		this.userphone = userphone;
		this.useraddr = useraddr;
	}
	public UserDTO(String[] datas) {
		this.userid = datas[0];
		this.userpw = datas[1];
		this.username = datas[2];
		this.userage = Integer.parseInt(datas[3]);
		this.userphone = datas[4];
		this.useraddr = datas[5];
	}
	
	@Override
	public String toString() {
		return userid+"\t"+userpw+"\t"+username
				+"\t"+userage+"\t"+userphone+"\t"+useraddr;
	}
}









