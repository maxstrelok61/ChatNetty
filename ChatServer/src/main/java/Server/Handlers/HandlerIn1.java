package Server.Handlers;

import Server.LinksServer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

//Обработчик входящих сообщений
public class HandlerIn1 extends SimpleChannelInboundHandler<String> {
    LinksServer linksServer;
    List<Channel> channels;
    public HandlerIn1(List<Channel> channels, LinksServer linksServer){this.channels = channels; this.linksServer = linksServer;}

    @Override
    public  void  channelActive(ChannelHandlerContext ctx) throws Exception{
        //Подключение клиента
        channels.add(ctx.channel());
        //System.out.println("(Class)HandlerIn1 -> channelActive | The client is connected");

    }

    @Override
    public  void  channelRead0(ChannelHandlerContext ctx, String msg) throws Exception{
        //отправляет входящее сообщение на обработку в Message
        linksServer.message.message(msg, String.valueOf(ctx.channel().id()));
        //System.out.println("(Class)HandlerIn1 -> channelRead |(incoming data) > " + msg);


    }

    @Override
    //Возникновение исключения
    public  void  exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        //закрытие соединения
        ctx.close();

    }
}