package Server.Form;

import Server.LinksServer;
import Server.Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    private LinksServer linksServer;
    private JFrame frame;
    private JButton mainButton;
    private JPanel panel;

    private boolean status = false;
    public Main(LinksServer linksServer){
        this.linksServer = linksServer;

        frame = new JFrame("ServerChat");
        frame.add(panel);
        frame.setSize(200,100);

        //Действия при закрытии окна
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(1);
                frame.dispose();
            }
        });
        //Нажатие кнопки Запуска/Остановки сервера
        mainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!status){
                    linksServer.server.start();
                    status = true;
                    mainButton.setText("Stop");
                }else{
                    linksServer.server.colose();
                    linksServer.server = new Server(linksServer);
                    mainButton.setText("Start");
                    status = false;
                }
            }
        });
    }

    public void setViseble (boolean viseble){
        frame.setVisible(viseble);
    }
}
