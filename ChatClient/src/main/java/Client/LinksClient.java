package Client;


import Client.Forms.ChatForm;
import Client.Forms.ErrorForm;
import Client.Forms.LoginForm;

import java.util.ArrayList;
import java.util.List;

//Класс со ссылками во все основные части программы
public class LinksClient {
    //Ник пользователя
    public String userName;
    //Параметры клиента загружемые из файла
    public ArrayMapToFile settingsClient;
    //Форма подлючения(Адресс сервера и ник пользователя)
    public LoginForm loginForm;
    //Форма сообщений чата
    public ChatForm chatForm;
    //Форма вывода информационного сообщения
    public ErrorForm errorForm;
    //Класс клиента Netty
    public Network network;
    public LinksClient(){
        //Имя файла параметров клиента
        settingsClient = new ArrayMapToFile("SettingsClient.DAT");

        loginForm = new LoginForm(this);
        chatForm = new ChatForm(this);
        errorForm = new ErrorForm(this);

        network = new Network(this);
        //network.start();
    }
}
