package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;


public class NPWaitPage {
    private String room_title;
    private List<User> user_list;


    private JTextField chatting_input;

    private List<JPanel> users_panel = new ArrayList<>();

    private JPanel createTitle() {
        JPanel panel = new JPanel();
        JLabel title = new JLabel(room_title);

        panel.setBackground(new Color(228, 227, 231));
        title.setFont(new Font("맑은 고딕", Font.BOLD, 15));

        panel.add(title);
        return panel;
    }

    private JPanel createUserProfiles() { //개인유저창 : 사진/ 닉네임 / 점수
        int size = user_list.size();
        int left_count = 6-size;
        JPanel panel = new JPanel(new GridLayout(1,6));

        for(User user : user_list) { //유저프로필 생성
            JPanel user_panel = new JPanel(new BorderLayout());
            JPanel user_info = new JPanel();
            user_info.setLayout(new BoxLayout(user_info, BoxLayout.Y_AXIS));
            user_info.setBackground(new Color(230, 230, 230)); //프로필란 bgColor

            //사진관련
            Image scaled_pic = user.getProfilePic().getImage().getScaledInstance(110,110,Image.SCALE_SMOOTH); //사이즈조절
            user.setProfilePic(new ImageIcon(scaled_pic)); //사이즈 조절 된 프로필사진

            JLabel status = new JLabel("대기");
            status.setAlignmentX(Component.CENTER_ALIGNMENT); //가운데정렬
            user_info.add(Box.createRigidArea(new Dimension(0, 20))); //간격
            user_info.add(status); //이거는 패널 누르면 변경되게 이벤트 처리해야함

            JLabel profile_img = new JLabel(user.getProfilePic());
            profile_img.setAlignmentX(Component.CENTER_ALIGNMENT); //가운데정렬
            user_info.add(Box.createRigidArea(new Dimension(0, 15))); //간격
            user_info.add(profile_img);

            JLabel name = new JLabel(user.getNickname());
            name.setAlignmentX(Component.CENTER_ALIGNMENT);
            name.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
            user_info.add(Box.createRigidArea(new Dimension(0, 15))); //간격
            user_info.add(name);

            user_panel.add(user_info, BorderLayout.CENTER);

            //테두리 생성
            user_panel.add(createGap(10, 225, new Color(139, 200, 100)),BorderLayout.WEST);
            user_panel.add(createGap(10, 225, new Color(139, 200, 100)),BorderLayout.EAST);
            user_panel.add(createGap(100, 50, new Color(139, 200, 100)), BorderLayout.NORTH);
            user_panel.add(createGap(100, 50, new Color(139, 200, 100)), BorderLayout.SOUTH);

            users_panel.add(user_info); //패널리스트에 추가
            panel.add(user_panel);

            /*-------------------액션리스너--------------------*/
            user_panel.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    if(user.status == false) {
                        user.status = true;
                        status.setText("준비");
                    }
                    else if(user.status ==true) {
                        user.status = false;
                        status.setText("대기");
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {}
                @Override
                public void mouseReleased(MouseEvent e) {}
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {	}
            });
            size--;
        }

        while(left_count != 0) { //빈공간
            JPanel user_empty_panel = new JPanel();
            user_empty_panel.setBackground(new Color(139, 200, 100));
            panel.add(user_empty_panel);
            left_count--;
            if(left_count == 0) break;
        }

        return panel;

    }

    private JPanel createGap(int width, int height, Color col) {
        JPanel gap = new JPanel();
        gap.setPreferredSize(new Dimension(width,height));
        gap.setBackground(col);

        return gap;
    }

    private JPanel createChatting() {  //체팅창(게임창에서 그대로 가져왔음)
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
        JPanel chat = new JPanel();
        frame.setLayout(new BorderLayout()); //캐릭터부분이 center, 채팅은 south, 방제는 north

        frame.add(createTitle(),BorderLayout.NORTH);

        chat = createChatting();
        chat.setPreferredSize(new Dimension(1000,200));
        frame.add(chat,BorderLayout.SOUTH);
        frame.add(createUserProfiles(), BorderLayout.CENTER);

        frame.setBounds(100,200,1000,600); //위치&사이즈
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public NPWaitPage(String title, List<User> user_list) {
        this.room_title = title;
        this.user_list = user_list;
        buildGUI();
    }
}
