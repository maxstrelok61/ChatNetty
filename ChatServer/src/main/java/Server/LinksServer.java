package Server;

import Server.ArrayMapToFile;
import Server.Form.Main;

//Класс через который передаются ссылки на основные модули программы
public class LinksServer {
    //Настройки cервера загружаемые из файла
    public ArrayMapToFile settingsServer;
    //Список подключенных клиентов
    public Connects connects = new Connects();
    //Сам сервер Netty
    public Server server;
    //GUI Форма Запуска/Остановки сервера
    public Main mainForm;
    // Класс обработки сообщений отк лиента и реакция на них
    public Message message = new Message(this);

    public LinksServer(){
    //Файл с настройками сервера
        settingsServer = new ArrayMapToFile("SettingsServer.DAT");
        server = new Server(this);
        mainForm = new Main(this);
        mainForm.setViseble(true);
    }

}
