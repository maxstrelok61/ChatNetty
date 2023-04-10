package Server;

// Класс обработки сообщений отк лиента и реакция на них
public class Message {
    //Класс со ссылками на основные части программы
    LinksServer linksServer;

    Message(LinksServer linksServer){
        this.linksServer = linksServer;
    }

    //Самый простой обработчик сообщений
    //Если пришло сообщение со структурой "reg(разделитель)user"
    //регистрирует ник пользователя на данном канале
    //если структура другая то это просто сообщение и отправляется просто в общий чат
    public void message(String str, String idChenel){
        String[] arr = str.split(":112234>");

        if ((arr.length > 0)&&(arr[0].equals("reg"))){
            linksServer.connects.add(idChenel, arr[1]);
        }else {
            linksServer.server.sendMessagesBordast(linksServer.connects.getUsername(idChenel) + " > " + str);
        }

    }

}
