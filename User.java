import javax.swing.ImageIcon;

public class User {
	private String nickname;
	private int score = 0;
	private ImageIcon profile_pic = new ImageIcon(NPMainPage.class.getResource("images/characterImg.png"));
	protected int left_time = 500; //타이머바 사이즈 가로가 500이라 50초
	
	public User(String nickname) {
		this.nickname = nickname;
	}
	

	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	
	public ImageIcon getProfilePic() {
		return profile_pic;
	}
	public void setProfilePic(ImageIcon pic) {
		this.profile_pic = pic;
	}

	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
