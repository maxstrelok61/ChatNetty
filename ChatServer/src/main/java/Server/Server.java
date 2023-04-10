package Server;

import Server.Handlers.HandlerIn1;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{
    private LinksServer linksServer;
    private int port;
    private ChannelFuture future;
    //Все подключения сразу заносятся в этот список
    private  List<Channel> channels = new ArrayList<>();
    public Server(LinksServer linksServer){
        this.linksServer = linksServer;
        this.port = Integer.parseInt(linksServer.settingsServer.get("port"));

    }

    public void run(){
        //создаем два менеджера потоков для обработки задач в параллельных потоках

        //отвечает за подключающихся клиентов, поэтому достаточно 1 потока
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);

        //отвечает за обработку данных (по умолчанию создаст около 20-30 потоков)
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //создаем ServerBootstrap для преднастройки сервера (например для указания порта, для каналов и опций)
            ServerBootstrap b = new ServerBootstrap();
            System.out.println("Сервер запущен");
            b.group(bossGroup, workerGroup) //указываем серверу чтобы он использовал два менеджера потоков
                    .channel(NioServerSocketChannel.class) //используем канал для подключения клиентов
                    .childHandler(new ChannelInitializer<SocketChannel>() { //в SocketChannel лежит информация о соединении клиента
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new StringDecoder(),
                                            new StringEncoder(),
                                            new HandlerIn1(channels, linksServer)); //для каждого клиента свой конвеер
                        }
                    }); //при подлючении клиента настраиваем процесс общения

            ChannelFuture future = b.bind(port).sync(); //запуск сервера
            this.future = future;
            future.channel().closeFuture().sync(); //ожидание закрытия, чтобы сервер не закрывался сразу
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //закрываем пулы потоков при остановке сервера
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    //Отравка сообщения во все каналы сервера
    public void sendMessagesBordast(String mess){
        for (Channel c : channels) {
            c.writeAndFlush(mess);
        }
    }

    public void colose(){
        future.channel().close();
    }
}
