/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package activejavademo;

/**
 *
 * @author omi
 */
@DBSettings(databaseName="test")
public class Test extends Model{
    
    public int a;
    public int b;
    int d;
    private int c;
    public Test()
    {
        this.a = 0;
        this.b = 1;
        this.c = 2;
        this.d = 3;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void setD(int d) {
        this.d = d;
    }

    public void setC(int c) {
        this.c = c;
    }
    
}
