package cn.edu.swu.miniwebsrv.core;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final Config config =Config.getInstance();
    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public void start()  {
        try (ServerSocket serverSocket = new ServerSocket(config.getPort())) {//创建serversocket并开始监听指定端口
            System.out.println("MiniWebSvr 启动成功，监听端口 " + config.getPort() + "...");

            while (true){
                //阻塞，等待客户端连接
                Socket clientSocket = serverSocket.accept();
                //接收客户端请求
                System.out.println("接收到新的连接："  + clientSocket.getInetAddress());
                //创建线程
                threadPool.execute(new RequestProcessor(clientSocket));

                //获取客户端的输入流
                InputStream inputStream = clientSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                //读取客户端发送的数据
                String message = reader.readLine();
                System.out.println("收到客户端消息：" + message);

                //向客户端回馈信息
                OutputStream outputStream = clientSocket.getOutputStream();
                PrintWriter writer = new PrintWriter(outputStream, true);
                writer.println("服务器收到信息：" + message);

                //关闭客户端连接
                clientSocket.close();
            }
        }catch (IOException e){
            System.err.println("服务器启动失败或运行异常：" + e.getMessage());
            threadPool.shutdown();
        }
    }

    private static class RequestProcessor implements Runnable {
        private final Socket socket;

        public RequestProcessor(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            //完善请求处理流程，确保异常安全和资源关闭
            try {
                //解析请求
                Parameter request = HttpParser.parse(socket.getInputStream());

                //路由器处理请求
                Router router = new Router();
                router.route(request, socket.getOutputStream());

            } catch (Exception e) {
                System.err.println("请求处理异常: " + e.getMessage());
                try {
                    String errorResponse = "HTTP/1.1 500 Internal Server Error\r\n" + "Content-Type: text/html\r\n" + "\r\n" +  "<html><body><h1>500 Internal Server Error</h1></body></html>";
                    socket.getOutputStream().write(errorResponse.getBytes());
                } catch (IOException ex) {
                    System.err.println("发送错误响应时发生异常：" + ex.getMessage());
                }
            } finally {
                try {
                    if (socket != null) socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
