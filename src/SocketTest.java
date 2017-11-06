import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class SocketTest {
    public static void main(String[] args) throws UnknownHostException, MalformedURLException {

        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println(inetAddress.getHostName());
        System.out.println(inetAddress.getHostAddress());

        //创建一个URL的实例
        URL BaiDu =new URL("http://www.baidu.com");
        URL url =new URL(BaiDu,"/index.html?username=tom#test");//？表示参数，#表示锚点
        System.out.println(url.getProtocol());//获取协议
        System.out.println(url.getHost()); // 获取主机
        System.out.println(url.getPort()); // 如果没有指定端口号，根据协议不同使用默认端口。此时getPort()方法的返回值为 -1
        System.out.println(url.getPath()); // 获取文件路径
        System.out.println(url.getFile()); // 文件名，包括文件路径+参数
        System.out.println(url.getRef()); // 相对路径，就是锚点，即#号后面的内容
        System.out.println(url.getQuery()); // 查询字符串，即参数

    }
}
