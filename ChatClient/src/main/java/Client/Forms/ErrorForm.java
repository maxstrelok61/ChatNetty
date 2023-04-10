package Client.Forms;

import Client.LinksClient;
import Client.Network;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//Форма вывода информационного сообщения
public class ErrorForm {

    LinksClient linksClient;
    private JFrame frame;
    private JPanel panel1;
    private JLabel Label;


    private boolean backL = false;
    private boolean backC = false;
    public ErrorForm(LinksClient linksClient){
        this.linksClient = linksClient;
        frame = new JFrame("Login");
        //Зарузает координаты местоположения на экране из файла
        frame.setLocation(Integer.parseInt(linksClient.settingsClient.get("mainX")),Integer.parseInt(linksClient.settingsClient.get("mainY")));
        frame.add(panel1);
        frame.setSize(600,100);

        //При на жатии на кнопку  текущая форма закрывается и снова выводит на показ
        //форму которая была активна до вывода информационного окна
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                linksClient.chatForm.setViseble(backL);
                linksClient.loginForm.setViseble(backC);
                linksClient.network = new Network(linksClient);
                frame.setVisible(false);
            }
        });

    }

    //Передается сообщение для формы
    //запоминается какая форма  активна и скрывает все
    public void showError(String messege){
        frame.setLocation(Integer.parseInt(linksClient.settingsClient.get("mainX")),Integer.parseInt(linksClient.settingsClient.get("mainY")));
        backL = linksClient.chatForm.getViseble();
        backC = linksClient.loginForm.getViseble();

        linksClient.chatForm.setViseble(false);
        linksClient.loginForm.setViseble(false);

        Label.setText(messege);
        frame.setVisible(true);
    }
}
