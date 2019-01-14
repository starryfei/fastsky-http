package server;

import com.starry.fastsky.util.URLUtil;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.junit.Test;

/**
 * ClassName: UriTest
 * Description: TODO
 *
 * @author: starryfei
 * @date: 2019-01-14 15:08
 **/
public class UriTest {
    @Test
    public void decodeUri(){
        QueryStringDecoder decoder = new QueryStringDecoder("/hello/des?recipient=world&x=1&y=2");
        assert decoder.path().equals("/hello/des");
        assert decoder.parameters().get("recipient").get(0).equals("world");
        assert decoder.parameters().get("x").get(0).equals("1");
        assert decoder.parameters().get("y").get(0).equals("2");
        assert URLUtil.getRoutePath(decoder.path()).equals("des");
    }
}
