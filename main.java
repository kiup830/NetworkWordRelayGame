import java.util.ArrayList;
import java.util.List;

//이거는 걍 테스트용 메인파일 신경쓰지 않으셔도 됩니다
public class main {

	public static void main(String[] args) {
		User user1 = new User("도연1");
		User user2 = new User("도연2");
		User user3 = new User("도연3");
		User user4 = new User("도연4");

		
		List<User> users = new ArrayList<>();
		users.add(user1);
		users.add(user2);
		users.add(user3);
		users.add(user4);

		
		//NPMainPage mp = new NPMainPage(user1);
		NPGamePage gp = new NPGamePage("title", users);
		
	}
	

}
