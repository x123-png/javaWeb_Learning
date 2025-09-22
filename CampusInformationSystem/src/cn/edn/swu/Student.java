package cn.edn.swu;

public class Student {
    private String id; //学号
    private String name; //姓名
    private String gender; //性别
    private int age; //年龄

    public String getId() {
        return id;
    }

    public void setId(String id) {
        System.out.println(this.name+"更换了学号");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println(this.name+"更换了姓名");
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        System.out.println(this.name+"更换了性别");
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        System.out.println(this.name+"改变了年龄");
        this.age = age;
    }

    public Student(){}  //无参构造函数
    public Student(String id,String name,String gender,int age){
        this.id=id;
        this.name=name;
        this.gender=gender;
        this.age=age;
    }  //带参数的初始化构造函数

    public void showInfo(){  //用于显示该学生的信息
        System.out.println("学号： "+id);
        System.out.println("姓名："+name);
        System.out.println("性别："+gender);
        System.out.println("年龄："+age);
        System.out.println("--------------");
    }
}
