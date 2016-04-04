/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package activejavademo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author omi
 */
public class Model {
    HashMap<String,String> columns;
    private String tableName;
    private Field[] fields;
    
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> types = new ArrayList<>();
    private ArrayList<Object> values = new ArrayList<>();
    
    private String databaseName;
    
    
    
    
    public final void save()
    { 
        this.initialize();
         
        String sql = "INSERT INTO"+" "+this.tableName +"(";

        String p = "";
        for(int i=0;i<names.size();i++)
        {
            p = p + "'"+ names.get(i)+"'"+",";
        }
        p = p.substring(0, p.length()-1)+")";
        sql = sql + p+"VALUES"+"(";
        
        
        String q = "";
        for(int i=0;i<values.size();i++)
        {
            String n = null;
            if(values.get(i) == null)
                n = null;
            else if(types.get(i).equals("TEXT"))
            {
                n = (String) values.get(i);
                n = "'"+n+"'";
            }
            else if(types.get(i).equals("INTEGER"))
            {
                n = String.valueOf((Integer)values.get(i));
            }
            else if(types.get(i).equals("REAL"))
            {
                if(values.get(i) instanceof Float)
                    n = String.valueOf((Float)values.get(i));
                else   
                    n = String.valueOf((Double)values.get(i));
                n = "'"+n+"'";
            }
            q = q + n+",";
        }
        q = q.substring(0, q.length()-1)+")";
        sql = sql + q + ";";
      
        System.out.println("final sql: "+sql);
        
        
        Connection con = null;
        Statement stmt = null;
       
        try 
        {
          Class.forName("org.sqlite.JDBC");
          con = DriverManager.getConnection("jdbc:sqlite:" + this.databaseName + ".db");
          
          stmt = con.createStatement();
          stmt.executeUpdate(sql);
          stmt.close();
          con.close();
        }
          
        catch ( Exception e ) 
        {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
        System.out.println("inserted successfully successfully");
        
            
    }
    
    
    private final void initialize()
    {
        
          
          Class<?> c = this.getClass();
          this.tableName = c.getSimpleName();
          System.out.println("table name: "+tableName);
          this.fields = c.getDeclaredFields();
          
          
          Annotation[] annotations = c.getAnnotations();

          for(Annotation annotation : annotations)
          {
            if(annotation instanceof DBSettings)
            {
                DBSettings myAnnotation = (DBSettings) annotation;
                this.databaseName = myAnnotation.databaseName();
            }
          }
          
          
          String sql = "CREATE TABLE IF NOT EXISTS "+this.tableName+" "+"(ID INTEGER PRIMARY KEY  AUTOINCREMENT ,";
          columns = new HashMap<>();
          for(Field f:fields)
          {
              f.setAccessible(true);
              
              String name = f.getName();
              Class<?> type = f.getType();
              String typeName = type.getSimpleName();
              
              if(typeName.equals("int") || typeName.equals("Integer") || typeName.equals("short") || typeName.equals("Short") || typeName.equals("long") || typeName.equals("Long") || typeName.equals("byte") || typeName.equals("Byte"))
              {
                  names.add(name);
                  types.add("INTEGER");
                  columns.put(name, "INTEGER");    
              }
              else if(typeName.equals("float") || typeName.equals("double") || typeName.equals("Float") || typeName.equals("Double"))
              {
                  names.add(name);
                  types.add("REAL");
                  columns.put(name, "REAL");
              }
              else
              {
                  names.add(name);
                  types.add("TEXT");
                  columns.put(name, "TEXT");
              }
              
              try 
              {
                  Object value = f.get(this);
                  values.add(value);
              } 
              catch (Exception ex) 
              {
                  Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
              }
              
              
                  
          }
        for(String name:columns.keySet())
        {
            String p = name+" "+columns.get(name)+",";
            sql = sql + p;
        }
        sql = sql.substring(0, sql.length()-1)+")";
        System.out.println("final sql: "+sql);
        
        Connection con = null;
        Statement stmt = null;
        
        try 
        {
          Class.forName("org.sqlite.JDBC");
          con = DriverManager.getConnection("jdbc:sqlite:" + this.databaseName + ".db");
          
          stmt = con.createStatement();
          stmt.executeUpdate(sql);
          stmt.close();
          con.close();
        }
          
        catch ( Exception e ) 
        {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
        System.out.println("table created successfully");
        
        
        
        
        
    }
    
}
