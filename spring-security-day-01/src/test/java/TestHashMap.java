import org.junit.Test;

/**
 * @author xiangjin.kong
 * @date 2020/5/29 12:10
 * @desc
 */
public class TestHashMap {

    @Test
    public void test() {
        int n = 23 - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        int i = (n < 0) ? 1 : (n >= (1 << 30)) ? (1 << 30) : n + 1;
        System.out.println(i);


        System.out.println(3<<1);
        System.out.println(3<<2);
        System.out.println(3 >> 1);
        System.out.println(3 >> 2);

    }
}
