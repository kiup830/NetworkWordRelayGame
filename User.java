import javax.swing.ImageIcon;
import java.io.File;

public class User {

	private String nickname;
	private String serverAddress;
	private int serverPort;

	private int score = 0;

	private File imgFile;

	// 기본 사용자 이미지 사용
	private ImageIcon profile_pic;
	public int left_time = 500; //타이머바 사이즈 가로가 500이라 50초s
	protected boolean status = false; //대기 여부


	public User(String serverAddress, int serverPort) {

		int num = (int)(Math.ceil(Math.random()*100));
		this.nickname = "user" + num;

		imgFile = new File("C:\\NetworkProgramming\\NetworkWordRelayGame\\images\\moremi.png");
		if (imgFile.exists()) {
			System.out.println("이미지 파일이 존재합니다.");
		} else {
			System.out.println("이미지 파일이 존재하지 않습니다.");
		}

		this.serverAddress = serverAddress;
		this.serverPort = serverPort;

		this.profile_pic = new ImageIcon(imgFile.getAbsolutePath());



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
	public void setServerAddress(String addr) {
		this.serverAddress = addr;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerPort(int port) {
		this.serverPort = port;
	}
	
	public int getServerPort() {
		return serverPort;
	}
}
