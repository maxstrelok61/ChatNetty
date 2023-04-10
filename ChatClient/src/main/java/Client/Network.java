package Client;
import Client.Handlers.HandlerIn1;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Network extends Thread{
    private SocketChannel channel;
    LinksClient linksClient;
    public Network(LinksClient linksClient) {
        this.linksClient = linksClient;
    }
    public void run(){
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try{
                Bootstrap b = new Bootstrap();
                //указываем серверу пулы потоков дляработы
                b.group(workerGroup)
                        //используем стандартный сервер НИО
                        .channel(NioSocketChannel.class)
                        //Обработчик отвечает за установку соедеинеия с клиентом
                        .handler(new ChannelInitializer<SocketChannel>() {

                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                channel = socketChannel;
                                socketChannel.pipeline().addLast(new StringDecoder(), new StringEncoder(), new HandlerIn1(linksClient));
                            }
                        });
                //Запуск сервера
                ChannelFuture future = b.connect(linksClient.settingsClient.get("server"), Integer.parseInt(linksClient.settingsClient.get("port"))).sync();

                linksClient.loginForm.setViseble(false);
                linksClient.chatForm.setViseble(true);
                linksClient.settingsClient.saveToFile();
                future.channel().closeFuture().sync();
            } catch (Exception e) {
                linksClient.errorForm.showError("The problem with connecting to the server");
               // e.printStackTrace();
            } finally {
                workerGroup.shutdownGracefully();
            }
    }
    //Отправка сообщения на сервер
    public void sendMessage(String msg){
        channel.writeAndFlush(msg);
    }
    public void close(){
        channel.close();
    }
}
