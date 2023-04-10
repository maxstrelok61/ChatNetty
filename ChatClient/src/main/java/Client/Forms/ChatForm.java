package Client.Forms;

import Client.LinksClient;
import Client.Network;

import javax.swing.*;
import java.awt.event.*;

public class ChatForm {
    LinksClient linksClient;
    private JFrame frame;
    private JPanel panel1;
    private JTextArea textArea1;
    private JTextField textField1;
    private JButton sendButton;

    public ChatForm(LinksClient linksClient){
        this.linksClient = linksClient;
        frame = new JFrame("Chat");
        //загрузка из файла кординат последнего местоположения окна
        frame.setLocation(Integer.parseInt(linksClient.settingsClient.get("mainX")),Integer.parseInt(linksClient.settingsClient.get("mainY")));
        frame.add(panel1);
        frame.setSize(400,700);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);

        //При закрытии окна на крестик
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //Сохраняет текущие кординаты положения окна на экране в файл
                    linksClient.settingsClient.correct("mainX", String.valueOf(frame.getLocation().x));
                    linksClient.settingsClient.correct("mainY", String.valueOf(frame.getLocation().y));
                    linksClient.settingsClient.saveToFile();

                    System.exit(1);
                    frame.dispose();
            }
        });

        //действие при отправке сообщения
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!textField1.getText().equals("")){
                    //отправка сообщения
                    sendMessage(textField1.getText());
                    //отчистка поля сообщения
                    textField1.setText("");
                }
            }
        });
        //Нажатие Enter так же делает отправку сообщения
        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10){
                    if (!textField1.getText().equals("")){
                        sendMessage(textField1.getText());
                        textField1.setText("");
                    }
                }
            }
        });
    }
    //Установка видимости окна
    public void setViseble(boolean viseble) {
        frame.setLocation(Integer.parseInt(linksClient.settingsClient.get("mainX")),Integer.parseInt(linksClient.settingsClient.get("mainY")));

        frame.setTitle("Chat(" + linksClient.userName + ")");
        frame.setVisible(viseble);
    }
    public boolean getViseble(){return frame.isVisible();}

    //Отправка сообщения  на сервер
    public void sendMessage(String str){
        linksClient.network.sendMessage(str);
    }

    //добавление приходящего сообщения в окно чата
    public void addMessege(String str){
        textArea1.append(str + "\n");
    }
}
