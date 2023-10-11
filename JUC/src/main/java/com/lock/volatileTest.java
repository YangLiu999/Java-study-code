package com.lock;

/**
 * @author YL
 * @date 2023/08/22
 **/
public class volatileTest {

    private static volatile volatileTest m_penguin = null;

    /**
     * 避免通过new初始化对象
     */
    private void penguin() {}
    public void beating() {
        System.out.println("打豆豆");
    };
    public static volatileTest getInstance() {      //1
        if (null == m_penguin) {               //2
            synchronized(volatileTest.class) {      //3
                if (null == m_penguin) {       //4
                    m_penguin = new volatileTest(); //5

            }
        }
        return m_penguin;                      //6
    }
     return null;
    }
}
