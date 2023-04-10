package Client.Handlers;

import Client.LinksClient;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

//Обработчик входящих сообщений
public class HandlerIn1 extends SimpleChannelInboundHandler<String> {

    LinksClient linksClient;
    public HandlerIn1(LinksClient linksClient) {
        this.linksClient = linksClient;
    }


    @Override
    public  void  channelActive(ChannelHandlerContext ctx) throws Exception{
        //Подключение клиента
       // System.out.println("(Class)HandlerIn1 -> channelActive | Connecting to the server");

        //При подключении к серверу отправляет сообщение на сервер для регистрации пользователя
        sendReg(ctx);

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        //System.out.println("Входящее сообщение: " + msg);
        linksClient.chatForm.addMessege(msg);
    }

    @Override
    //Возникновение исключения
    public  void  exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        //закрытие соединения
        ctx.close();

    }
    //отправляет сообщение на сервер для регистрации пользователя
    //Сервер связывает отправленный ник пользователя с текущим каналом этого клиента
    public void sendReg(ChannelHandlerContext ctx){
        ctx.writeAndFlush("reg" + ":112234>" + linksClient.userName);
    }
}