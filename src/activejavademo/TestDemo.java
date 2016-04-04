/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package activejavademo;

import java.util.List;

/**
 *
 * @author omi
 */
public class TestDemo {
    
    public static void main(String []args) throws ActiveJavaException
    {
        Test test = new Test();
        test.a = 5;
        test.save();
        
        List<Object> datas = new Select("b").from(Test.class).where(new String[]{"a"},new String[]{"5"}).limit(1).execute();
        for(Object data:datas)
        {
            Test t = (Test)data;
            System.out.println(t.a);
            System.out.println(t.b);
            System.out.println(t.d);
        }
    }
    
}
