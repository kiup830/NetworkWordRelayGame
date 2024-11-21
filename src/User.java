import javax.swing.ImageIcon;

public class User {
    private String nickname;
    private int score = 0;

    // 기본 이미지 : 모레미
    private ImageIcon profile_pic;
    protected int left_time = 500; //타이머바 사이즈 가로가 500이라 50초

    public User(String nickname) {

        this.nickname = nickname;

        // 기본 이미지 설정
        profile_pic = new ImageIcon("C:\\NetworkProgramming\\NetworkWordRelayGame\\images\\moremi.png");

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