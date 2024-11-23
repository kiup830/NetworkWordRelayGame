package Client;

import javax.swing.ImageIcon;

public class User {

	private String nickname;
	private int score = 0;

	private String ImgPath = "images/moremi.png";

	// 기본 사용자 이미지 사용
	private ImageIcon profile_pic = new ImageIcon(ImgPath);
	public int left_time = 500; //타이머바 사이즈 가로가 500이라 50초s
	protected boolean status = false; //대기 여부


	public User(String nickname) {
		this.nickname = nickname;
	}


	// 점수 세팅
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	// 프로필 이미지 세팅
	public ImageIcon getProfilePic() {
		return profile_pic;
	}
	public void setProfilePic(ImageIcon pic) {
		this.profile_pic = pic;
	}

	// 닉네임 설정
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
