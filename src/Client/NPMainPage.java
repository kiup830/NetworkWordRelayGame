package Client;//멤버변수 : 스네이크케이스	: snake_case
//메서드들 : 카멜케이스	: camelCase

import Client.User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class NPMainPage { //생성자로 유저아이디 받아서 세팅
	private final User user;

	// 서버 주소와 서버 포트를 연결
	private String serverAddress;
	private int serverPort;

	private JButton btn_changeImage; //프사변경 버튼 -> 버튼이벤트 연동해서 profile_pic 바꿔야함
	private JButton btn_createRoom; //방생성 버튼
	private JPanel room_list;

	public NPMainPage(String serverAddress, int serverPort) {

		this.serverAddress = serverAddress;
		this.serverPort = serverPort;

		user = new User();
		buildGUI();

	}

	public void buildGUI() {
		JFrame frame = new JFrame("게임제목");
		JPanel west = new JPanel(new GridLayout(1,2));
		frame.setLayout(new BorderLayout()); //프로필과 접속자는 west, 방목록은 center

		west.add(createProfile());
		west.add(createListAndButton());
		west.setPreferredSize(new Dimension(1400,600));
		frame.add(west,BorderLayout.WEST);

		frame.add(createRoomList(),BorderLayout.CENTER);


		frame.setBounds(100,200,1000,600); //위치&사이즈
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}


	// 이미지 선택 버튼,
	public JPanel createProfile() { 
		//내정보패널 (프로필 패널)
		JPanel panel = new JPanel(new BorderLayout());
		JPanel user_info = new JPanel(); //유저사진, 닉네임
		JLabel title= new JLabel("내 정보");
		JTextField nickname = new JTextField(user.getNickname(), 20); // 20은 텍스트 필드의 길이


		panel.setBackground(new Color(255,250,240));  //하나 색 바꾸면 밑에꺼도 같이 바꿔주기
		user_info.setBackground(new Color(255,250,240));
		
		//제일 위에 제목 표시부분
		title.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
		title.setHorizontalAlignment(SwingConstants.CENTER); //가운데정렬
        title.setVerticalAlignment(SwingConstants.CENTER); //가운데정렬

        //사진크기 조절부분
		Image scaled_pic = user.getProfilePic().getImage().getScaledInstance(150,150,Image.SCALE_SMOOTH); //사이즈조절
		user.setProfilePic(new ImageIcon(scaled_pic)); //사이즈 조절 된 프로필사진
		JLabel profile_img = new JLabel(user.getProfilePic());
		
		
		//버튼 크기 조절
		btn_changeImage = new JButton("프로필 이미지 변경");
		btn_changeImage.setMaximumSize(new Dimension(150, 30));

		btn_changeImage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		
		//닉네임 설정
		nickname.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
		nickname.setHorizontalAlignment(SwingConstants.CENTER); //가운데정렬



		// 유저 정보 판넬 설정 (제목, 이미지, 닉네임, 버튼)
		user_info.setLayout(new BoxLayout(user_info, BoxLayout.Y_AXIS));
		user_info.add(Box.createRigidArea(new Dimension(0, 50))); //간격 추가
		
		user_info.add(profile_img);
		profile_img.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		user_info.add(Box.createRigidArea(new Dimension(0, 10))); //간격 추가
		user_info.add(btn_changeImage);
		btn_changeImage.setAlignmentX(Component.CENTER_ALIGNMENT);

		user_info.add(Box.createRigidArea(new Dimension(0, 10))); //간격 추가
		user_info.add(nickname);
		nickname.setAlignmentX(Component.CENTER_ALIGNMENT);


		title.setPreferredSize(new Dimension(200,100)); //제목부분 영역크기 설정 (모든부분 동일하게)
		panel.add(title,BorderLayout.NORTH);
		panel.add(user_info, BorderLayout.CENTER);
		
		return panel;
	}
	
	public JPanel createListAndButton() { //접속자목록&생성버튼 패널
		JPanel panel = new JPanel(new BorderLayout());
		JLabel title = new JLabel("접속자 목록");
		
		
		panel.setBackground(new Color(255,228,225));  

		title.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
		title.setHorizontalAlignment(SwingConstants.CENTER); //가운데정렬
        title.setVerticalAlignment(SwingConstants.CENTER); //가운데정렬
		
        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
		list.setBackground(new Color(255,255,255));  

        //접속자 목록 dummy data
        String[] users = {"User 1 (Online)", "User 2 (Offline)", "User 3 (Busy)", "User 4 (Online)",
        		"User 1 (Online)", "User 2 (Offline)", "User 3 (Busy)", "User 4 (Online)",
        		"User 1 (Online)", "User 2 (Offline)", "User 3 (Busy)", "User 4 (Online)",
        		"User 1 (Online)", "User 2 (Offline)", "User 3 (Busy)", "User 4 (Online)"
        	}; //유저목록 나중에 맴버변수로 빼고 접속시 add로 추가하기
        for (String user : users) {
            JLabel userLabel = new JLabel(user);
            userLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
            userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            list.add(userLabel);
    		list.add(Box.createRigidArea(new Dimension(0, 10))); //간격 추가
        }
        JScrollPane list_scroll = new JScrollPane(list);
        
        
        JPanel temp = new JPanel();
        temp.setBackground(new Color(255,228,225));
        
        //버튼 크기 조절 + 가운데 정렬
        btn_createRoom = new JButton("방 만들기");
		btn_createRoom.setPreferredSize(new Dimension(150, 40));
		btn_createRoom.setHorizontalAlignment(SwingConstants.CENTER); //가운데정렬
        btn_createRoom.setVerticalAlignment(SwingConstants.CENTER); //가운데정렬
		temp.add(btn_createRoom);

		btn_createRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});


        title.setPreferredSize(new Dimension(200,100));
        panel.add(title, BorderLayout.NORTH);
        panel.add(list_scroll, BorderLayout.CENTER);
        temp.setPreferredSize(new Dimension(200,100));
        panel.add(temp, BorderLayout.SOUTH);
        
		return panel;
	}
	
	public JPanel createRoomList() {
		JPanel panel = new JPanel();
		JLabel title= new JLabel("방 목록");
		
		panel.setBackground(new Color(240,248,255));
		
		
		title.setFont(new Font("맑은고딕", Font.PLAIN, 24));
		title.setHorizontalAlignment(SwingConstants.CENTER); //가운데정렬
        title.setVerticalAlignment(SwingConstants.CENTER); //가운데정렬
        
        //여긴 제목 제외부분
        room_list = new JPanel();
        room_list.setLayout(new BoxLayout(room_list, BoxLayout.Y_AXIS));
        
        
        //이거 개별 방모양 생성
        JPanel room = new JPanel(new BorderLayout());
        room.setPreferredSize(new Dimension(400, 100));
        
        JLabel room_title = new JLabel("방 1");
        room_title.setHorizontalAlignment(SwingConstants.CENTER); //가운데정렬
        room_title.setVerticalAlignment(SwingConstants.CENTER); 
        room_title.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        
        JButton enter = new JButton(" 입장 ");
        room.add(room_title, BorderLayout.CENTER);
        room.add(enter, BorderLayout.SOUTH);
        //여기까지 
        
		room_list.add(room);
        
        title.setPreferredSize(new Dimension(200,100));
		panel.add(title, BorderLayout.NORTH);
		panel.add(room_list, BorderLayout.CENTER);
		
		
		return panel;
	}
	


	
}
//상호작용 되야하는 것들만 멤버변수로 처리 : 프사변경버튼 / 방생성버튼 -> 누르면 방목록 뜨게? / 방목록(roomlist) / 접속자목록 -> 한번에 빼기

