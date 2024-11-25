//멤버변수 : 스네이크케이스	: snake_case
//메서드들 : 카멜케이스	: camelCase
package Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class NPMainPage { //생성자로 유저아이디 받아서 세팅
    private User user;
    private JTextField t_userNickName;

    private JButton btn_changeImage; //프사변경 버튼 -> 버튼이벤트 연동해서 profile_pic 바꿔야함
    private JButton btn_createRoom; //방생성 버튼
    private JPanel room_list;

    private String serverAddress;
    private int serverPort;

    private ImageIcon profile_pic;

    // 서버주소와 서버포트를 넣는 텍스트 필드
    private JTextField t_serverAddress;
    private JTextField t_serverPort;


    // 소켓 및 객체 전송 스트림
    private Socket socket;
    private ObjectOutputStream out;
    private Thread receiveThread;


    public NPMainPage(String serverAddress, int serverPort) {

        this.serverAddress = serverAddress;
        this.serverPort = serverPort;

        // user객체를 하나 생성해줌.
        user = new User(serverAddress, serverPort);
        buildGUI();
    }


    public void buildGUI() {
        JFrame frame = new JFrame("게임제목");
        JPanel west = new JPanel(new GridLayout(1,2));
        frame.setLayout(new BorderLayout()); //프로필과 접속자는 west, 방목록은 center

        west.add(createProfile());
        west.add(createListandButton());
        west.setPreferredSize(new Dimension(400,600));
        frame.add(west,BorderLayout.WEST);

        frame.add(createRoomList(),BorderLayout.CENTER);


        frame.setBounds(100,200,1000,600); //위치&사이즈
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    public JPanel createProfile() { // 내 정보 패널
        JPanel panel = new JPanel(new BorderLayout());
        JPanel user_info = new JPanel(); // 유저 사진, 닉네임
        JLabel title = new JLabel("내 정보");

        panel.setBackground(new Color(255, 250, 240)); // 패널 배경색
        user_info.setBackground(new Color(255, 250, 240));

        // 제목 설정
        title.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setPreferredSize(new Dimension(600, 50)); // 높이 조정


        // 유저 정보 패널
        user_info.setLayout(new BoxLayout(user_info, BoxLayout.Y_AXIS));

        // 사진 크기 조절
        Image scaled_pic = user.getProfilePic().getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        user.setProfilePic(new ImageIcon(scaled_pic));
        JLabel profile_img = new JLabel(user.getProfilePic());
        profile_img.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 이미지 변경 버튼
        btn_changeImage = new JButton("프로필 이미지 변경");
        btn_changeImage.setMaximumSize(new Dimension(200, 30));
        btn_changeImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_changeImage.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF & PNG Images", "jpg", "gif", "png");
            chooser.setFileFilter(filter);

            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                if (selectedFile != null) {
                    ImageIcon newImage = new ImageIcon(selectedFile.getAbsolutePath());
                    Image scaledImage = newImage.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    user.setProfilePic(new ImageIcon(scaledImage));
                    profile_img.setIcon(user.getProfilePic()); // 변경된 이미지 업데이트
                }
            }
        });

        // 닉네임 설정
        JPanel nickNameAndServer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        nickNameAndServer.setBackground(new Color(255, 250, 240));
        JLabel userNickNameLabel = new JLabel("닉네임:");
        userNickNameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        t_userNickName = new JTextField(user.getNickname());
        t_userNickName.setPreferredSize(new Dimension(200, 30));
        nickNameAndServer.add(userNickNameLabel);
        nickNameAndServer.add(t_userNickName);

        JLabel serverLabel = new JLabel("서버주소:");
        serverLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        t_serverAddress = new JTextField(user.getServerAddress());
        t_serverAddress.setPreferredSize(new Dimension(200, 30));

        JLabel portLabel = new JLabel("포트번호:");
        portLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        t_serverPort = new JTextField(String.valueOf(user.getServerPort()));
        t_serverPort.setPreferredSize(new Dimension(200, 30));

        nickNameAndServer.add(serverLabel);
        nickNameAndServer.add(t_serverAddress);
        nickNameAndServer.add(portLabel);
        nickNameAndServer.add(t_serverPort);


        // 컴포넌트 간 간격 추가 및 조정
        user_info.add(Box.createRigidArea(new Dimension(0, 20))); // 사진 위 간격
        user_info.add(profile_img);
        user_info.add(Box.createRigidArea(new Dimension(0, 10))); // 버튼 위 간격
        user_info.add(btn_changeImage);
        user_info.add(Box.createRigidArea(new Dimension(0, 20))); // 닉네임 위 간격
        user_info.add(nickNameAndServer);

        // 서버 정보 바로 붙여 추가


        panel.add(title, BorderLayout.NORTH);
        // 중앙에 유저 정보 패널 추가
        panel.add(user_info, BorderLayout.CENTER);



        // 전체 패널 크기 설정
        panel.setPreferredSize(new Dimension(600, 600));

        return panel;
    }



    public JPanel createListandButton() { //접속자목록&생성버튼 패널
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

        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> new NPWaitPage(user));
            }
        });

        //여기까지

        room_list.add(room);

        title.setPreferredSize(new Dimension(200,100));
        panel.add(title, BorderLayout.NORTH);
        panel.add(room_list, BorderLayout.CENTER);


        return panel;
    }


    // 서버 연결 함수 (방 입장 혹은 방생성 시.. )
    private void createRoom() throws IOException {
        socket = new Socket();

        InetSocketAddress sa = new InetSocketAddress(serverAddress,serverPort);
        socket.connect(sa, 3000);

        out =new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        //sendUser();
        SwingUtilities.invokeLater(() -> new NPWaitPage(user));




    }

    // 방 입장 시 서버 연결 함수
    private void joinRoom() throws IOException {
        socket = new Socket();

        InetSocketAddress sa = new InetSocketAddress(serverAddress,serverPort);
        socket.connect(sa, 3000);

        out =new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        //sendUser();




    }


}
//상호작용 되야하는 것들만 멤버변수로 처리 : 프사변경버튼 / 방생성버튼 -> 누르면 방목록 뜨게? / 방목록(roomlist) / 접속자목록 -> 한번에 빼기
