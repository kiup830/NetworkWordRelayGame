import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class NPGamePage {
	//초록색 배경 색상 = new Color(139, 200, 100)
	private String room_title;
	private List<User> user_list; // -> 대기화면에서 받아오기
	private JTextField chatting_input;
	private JTextField word_input; //입력창 ,  엔터누르면 단어 전송 + 타이머 멈추고 user.setTimer(잔여시간)하기
	
	//시간관련
	private JProgressBar timer_bar;
	private Timer timer;
	private int typing_user_idx = -1;
	private User current_player;
	
	
	private JLabel last_word; //끝말잇기 단어 
	
	private JPanel createTitle() {
		JPanel panel = new JPanel();
		JLabel title = new JLabel(room_title);
		
		panel.setBackground(new Color(228, 227, 231));
        title.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		
		panel.add(title);
		return panel;
	}
	
	private JPanel createCenter() {
		JPanel panel = new JPanel(new GridLayout(2,1));  //위가 게임창, 아래는 유저들
	
		panel.add(createGameArea());
		panel.add(createUserProfiles());
		return panel;
	}
	
	private JPanel createGameArea() {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel game = new JPanel(new BorderLayout());
		game.setBackground(new Color(250, 234, 154));
		
		last_word = new JLabel("끝말잇기 시작");
        last_word.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
        
        last_word.setPreferredSize(new Dimension(500,200));
        last_word.setHorizontalAlignment(SwingConstants.CENTER);
        last_word.setVerticalAlignment(SwingConstants.CENTER); 
        
		JPanel south = new JPanel(new BorderLayout());
		
		timer_bar = new JProgressBar();
		timer_bar.setStringPainted(false);
		timer_bar.setPreferredSize(new Dimension(500, 30));
		
		word_input = new JTextField(); //이거는 엔터로 입력
		word_input.setBackground(new Color(250,234,154));
		word_input.setHorizontalAlignment(SwingConstants.CENTER); 
        word_input.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		
		south.add(timer_bar, BorderLayout.NORTH);
		south.add(word_input, BorderLayout.CENTER);			
		
		game.add(last_word, BorderLayout.CENTER);
		game.add(south, BorderLayout.SOUTH);
		south.setPreferredSize(new Dimension(500,80));
		
		panel.add(game, BorderLayout.CENTER);
		
		//옆에 테두리
		panel.add(createGap(200, 225, new Color(139, 200, 100)),BorderLayout.WEST);
		panel.add(createGap(200, 225, new Color(139, 200, 100)),BorderLayout.EAST);
		panel.add(createGap(1000, 20, new Color(139, 200, 100)), BorderLayout.NORTH);
		panel.add(createGap(1000, 20, new Color(139, 200, 100)), BorderLayout.SOUTH);
		
		/*-------------------액션리스너--------------------*/
		word_input.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateUser();
			}
		});

		return panel;	
	}
	
	

	
	private JPanel createGap(int width, int height, Color col) {
		JPanel gap = new JPanel();
		gap.setPreferredSize(new Dimension(width,height));
		gap.setBackground(col);
	
		return gap;
	}
	
	private JPanel createUserProfiles() { //개인유저창 : 사진/ 닉네임 / 점수
		int size = user_list.size();
		int left_count = 6-size;
		JPanel panel = new JPanel(new GridLayout(1,6));

		for(User user : user_list) {
			JPanel user_panel = new JPanel(new BorderLayout());
			JPanel user_info = new JPanel();
			user_info.setLayout(new BoxLayout(user_info, BoxLayout.Y_AXIS));
			user_info.setBackground(new Color(230, 230, 230)); //프로필란 bgColor
			
			//사진관련
			Image scaled_pic = user.getProfilePic().getImage().getScaledInstance(110,110,Image.SCALE_SMOOTH); //사이즈조절
			user.setProfilePic(new ImageIcon(scaled_pic)); //사이즈 조절 된 프로필사진
			
			
			JLabel profile_img = new JLabel(user.getProfilePic());
			profile_img.setAlignmentX(Component.CENTER_ALIGNMENT); //가운데정렬	
			user_info.add(Box.createRigidArea(new Dimension(0, 10))); //간격
			user_info.add(profile_img);

			JLabel name = new JLabel(user.getNickname());
			name.setAlignmentX(Component.CENTER_ALIGNMENT);
			name.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
			user_info.add(Box.createRigidArea(new Dimension(0, 5))); //간격
			user_info.add(name);
			
			JLabel score = new JLabel(String.valueOf(user.getScore()));
			score.setAlignmentX(Component.CENTER_ALIGNMENT);
			score.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
			user_info.add(Box.createRigidArea(new Dimension(0, 5))); //간격 
			user_info.add(score);
			
			user_panel.add(user_info, BorderLayout.CENTER);
			
			//테두리 생성
			user_panel.add(createGap(10, 225, new Color(139, 200, 100)),BorderLayout.WEST);
			user_panel.add(createGap(10, 225, new Color(139, 200, 100)),BorderLayout.EAST);
			user_panel.add(createGap(100, 5, new Color(139, 200, 100)), BorderLayout.NORTH);
			user_panel.add(createGap(100, 5, new Color(139, 200, 100)), BorderLayout.SOUTH);
			
			size--;
			panel.add(user_panel);
		}
		
		while(left_count != 0) { //빈공간 
			JPanel user_panel = new JPanel();
			user_panel.setBackground(new Color(139, 200, 100));
			panel.add(user_panel);
			left_count--;
			if(left_count == 0) break;
		}
		
		
		return panel;
		
	}
	
	private JPanel createChatting() {  //체팅창
		JPanel panel = new JPanel(new BorderLayout());
		JTextPane chatting_log = new JTextPane();
		chatting_log.setEditable(false);
		
		JButton b_send = new JButton("전송");
		JPanel input_panel = new JPanel(new BorderLayout());
		
		JScrollPane scrollpane = new JScrollPane(chatting_log);
		
		chatting_input = new JTextField();
		input_panel.add(chatting_input, BorderLayout.CENTER);
		input_panel.add(b_send, BorderLayout.EAST);
		
		panel.add(scrollpane,BorderLayout.CENTER);
		panel.add(input_panel,BorderLayout.SOUTH);
		
		panel.setPreferredSize(new Dimension(1000,130));
		return panel;
	}
	
	
	
 	public void buildGUI() {
		JFrame frame = new JFrame("게임제목"); 
		frame.setLayout(new BorderLayout()); //즁간 끝말잇기 부분과 캐릭터부분이 center, 채팅은 south, 방제는 north 
		
		frame.add(createTitle(),BorderLayout.NORTH);
		frame.add(createCenter(), BorderLayout.CENTER);
		frame.add(createChatting(),BorderLayout.SOUTH);
		
		timer = new Timer(100, new TimerAction());
		
		frame.setBounds(100,200,1000,600); //위치&사이즈
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		updateUser();
		timer.start();
	}
	
	public NPGamePage(String title, List<User> user_list) {
		this.room_title = title;
		this.user_list = user_list;
		buildGUI();
	}
	
	/*----타이머 관련 부분---*/
	private void updateUser() {
		if(typing_user_idx == -1) typing_user_idx = 0;
		current_player = user_list.get(typing_user_idx % user_list.size());

		if(current_player.left_time > 0) { //시간 남으면 타이머 흐름
			timer_bar.setMaximum(current_player.left_time);
			timer_bar.setValue(current_player.left_time);
		}
		typing_user_idx ++;
	}
	
	private class TimerAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(typing_user_idx % user_list.size() < user_list.size()) {
				if(current_player.left_time > 0) {
					current_player.left_time--;
					timer_bar.setValue(current_player.left_time);
					
					//이거 시간 계속 초기화되는 문제. 직접 변경이 안되는 것 같음
				}
			}
		}
		
	}
}
