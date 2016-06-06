# ActiveSQLiteJava
this is a simple java wrapper(ORM) for sqlite-jdbc application.


**Project Dependencies**:

   1)ActiveSQLiteJava.jar.
(No need to add sqlite-jdbc.jar)

**How to start**:

   1) Add ActiveSQLiteJava.jar to your project and include them to your classpath.

   2) The Class which will act as a Table for mysql and whose member will be saved as columns on that table must extend 
activejavademo.Model. No method is needed to override.Let, the Class is Sample.

   3) Sample Class must have one annotation: "database name". if database name is not provided, this library assumes that the database name is "test.db".So, in sqlite, it is not necessary that, there must be a database with name "test.db" prior running this application. This is not pre-requisite. When you run your application, this library automatically create the corresponding db file in the project root directory. Default value for database name is "test.db". The table name for Sample class will be the The name of the class itself. Column names will be the names of the fields(private,public,protected,default) of the class.

   4) Version 1.0.1 supports only 3 data types. They are: INTEGER,TEXT and REAL. Java bye,short,int,long will be converted to SQLite INTEGER type; Java String will be converted to SQLite TEXT type; Java floar,double will be converted to SQLite REAL type. Other data types are not supported.

   5) Sample class must have setter method strictly followed Java Conventions. Such as-

`private String myName;   `
`public void setMyName(String myName)`
`{`
   `this.myName = myName;`
`}`

   6) Sample class must have an empty constructor.

   7) Operations supported by this library is create,insert,select. More operations will be added on future versions.

   8) Create an object of your class, in this case, Sample. Sample object may have several constructors or may call several methods. Meanwhile,Save the object by calling sampleObject.save(). In sqlITE, the current value of the object will be saved.

   9) For selection through table, create an object of activejavademo.Select class. It has 2 constructors. First one takes column name as parameter which to select. if the column name provided by you is "*", then all columns will be selected.

   10)from() takes an class<?> argument. This is the class which will be act as a table. In this case, this is Sample.class.

   11)where() is optional. If not provided, every row will be returned. If provided, rows will be filtered. It takes 2 arguments. First one is an array of String named columns. Second one is another array of String names values. Both the 2 String array must have same length. Otherwise, ActiveJavaException may be thrown. This clause ensures that, rows returned by the final query must have columns[i]=values[i].

   12)limit() is optional. It takes an integer value for limiting the number of rows returned. 

   13)execute() is the last method which is mandatory. This method returns the all rows as a list of Object. These objects can be cast to get the actual Sample Object.

   14) The whole selection procedure supports fluent API. A simple scenario is explained below:

`List<Object> datas = new Select("*").from(Sample.class).where(new String[]{"columnName"},new String[]{"value"}).limit(10).execute();`
`for(Object data:datas)`
`{`
   `Sample sample = (Sample)data;`
   `// do anything with sample`
`}`

   15) Primary Key is maintained by the library itself. Every Model class is a table in SQLite and having a primary key name ID which is INT and auto incremented.So, it is not expected to have a field named ID in a Model class.

### Sample code

Model class:

`@DBSettings(databaseName="test")`
`public class Test extends Model`
`{`
    
    `public int a;`
    `public int b;`
    `int d;`
    `private int c;`
    `public Test()`
    `{`
        `this.a = 0;`
        `this.b = 1;`
        `this.c = 2;`
        `this.d = 3;`
    `}`

    `public void setA(int a) {`
        `this.a = a;`
    `}`

    `public void setB(int b) {`
        `this.b = b;`
    `}`

    `public void setD(int d) {`
        `this.d = d;`
    `}`

    `public void setC(int c) {`
        `this.c = c;`
    `}`
    
`}`

client program:

        `Test test = new Test();`
        `test.save();`
        `List<Object> datas = new Select("b").from(Test.class).where(new String[]{"a"},new String[]    {"5"}).limit(1).execute();`
        `for(Object data:datas)`
        `{`
            `Test t = (Test)data;`
            `System.out.println(t.a);`
            `System.out.println(t.b);`
            `System.out.println(t.d);`
        `}`

output: test.db will be created on the project root directory. This code will show the desired output.

