package Client.Forms;

import Client.LinksClient;
import com.sun.org.apache.bcel.internal.generic.ARETURN;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginForm {
    LinksClient linksClient;
    private JFrame frame;
    private JPanel panel1;
    private JTextField ServerConnnect;
    private JTextField Login;
    private JButton GOButton;

    public LoginForm(LinksClient linksClient){
        this.linksClient = linksClient;
        frame = new JFrame("Login");
        //загрузка из файла кординат последнего местоположения окна
        frame.setLocation(Integer.parseInt(linksClient.settingsClient.get("mainX")),Integer.parseInt(linksClient.settingsClient.get("mainY")));
        frame.add(panel1);

        frame.setSize(300,100);
        //загрузка из файла поледнего использованного ника
        Login.setText(linksClient.settingsClient.get("user"));
        //загрузка из файла последнего использованного адреса сервера
        ServerConnnect.setText(linksClient.settingsClient.get("server") + ":" + linksClient.settingsClient.get("port"));
        //Вывод окна подключения
        frame.setVisible(true);

        //Призакрытии окна на крестик сохранет в файл местоположение на экране текущего окна
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                linksClient.settingsClient.correct("mainX", String.valueOf(frame.getLocation().x));
                linksClient.settingsClient.correct("mainY", String.valueOf(frame.getLocation().y));
                linksClient.settingsClient.saveToFile();

                System.exit(1);
                frame.dispose();
            }
        });
        //Действие при нажатии кнопки подключения к серверу
        GOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] arr = ServerConnnect.getText().split(":");


                if (checkLogin() && checkServer()){
                    linksClient.settingsClient.correct("server", arr[0]);
                    linksClient.settingsClient.correct("port", arr[1]);
                    linksClient.settingsClient.correct("user", Login.getText());

                    linksClient.userName = Login.getText();
                    linksClient.network.start();
                }else {
                    if (!checkLogin()) linksClient.errorForm.showError("unsuitable login");
                    if (!checkServer()) linksClient.errorForm.showError("unsuitable server");
                }
            }
        });
    }
    //Установка видимости окна
    public void setViseble(boolean viseble) {
        frame.setVisible(viseble);
    }
    public boolean getViseble(){return frame.isVisible();}
    //Проверка логина на корректность
    private boolean checkLogin(){
        boolean res =  false;
        if (!Login.getText().equals("") && Login.getText().length()>3){
            Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(Login.getText());

            if (!m.find()) res = true;
        }
        return res;
    }
    //Проверка строки подключения к серверу на корректность
    private boolean checkServer(){
        boolean res = false;
        String[] arr = ServerConnnect.getText().split(":");
        if (arr.length > 0) res = true;
        return res;
    }
}
