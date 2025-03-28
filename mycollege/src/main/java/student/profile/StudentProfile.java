package student.profile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class StudentProfile {
 
int id;
String name;
int age;

public int getId()
{
return id;
}
public void setId(int id)
{
this.id=id;
}
public String getName()
{
return name;
}
public void setName(String name)
{
this.name=name;
}
public int getAge()
{
return age;
}
public void setAge(int age)
{
this.age=age;
}


public void setRequestParam(HttpServletRequest request) {

this.setId(null!=request.getParameter("id")&&!request.getParameter("id").equals("")?Integer.parseInt((String)request.getParameter("id")):0);
this.setName(null!=request.getParameter("name")?request.getParameter("name"):"");
this.setAge(null!=request.getParameter("age")&&!request.getParameter("age").equals("")?Integer.parseInt((String)request.getParameter("age")):0);

}

public void displayReqParam(HttpServletRequest request) {


System.out.println("------Begin:Request Param Values---------");
System.out.println("id = "+request.getParameter("id"));
System.out.println("name = "+request.getParameter("name"));
System.out.println("age = "+request.getParameter("age"));

System.out.println("------End:Request Param Values---------");
}

public void displayValues() {

System.out.println("Id = "+this.getId());
System.out.println("Name = "+this.getName());
System.out.println("Age = "+this.getAge());

}

public void setDefaultValues() {

this.setName("");
this.setAge(0);

}
}