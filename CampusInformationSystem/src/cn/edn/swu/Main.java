package cn.edn.swu;

public class Main {
    public static void main(String[] args) {
        Student s1=new Student("S2024001","Oliver","male",18);
        Student s2=new Student("S2024002","Isla","female",19);
        Course c1=new Course("001","Math","Jack");
        Course c2=new Course("002","English","David");
        System.out.println("--学生信息--");
        s1.showInfo();
        s2.showInfo();
        System.out.println();
        System.out.println("--课程信息--");
        c1.showInfo();
        c2.showInfo();
        System.out.println();
        s1.setAge(19);
        s2.setId("S2024003");
        c1.setCourseId("4");
        c2.setTeacher("Tim");
        System.out.println();
        System.out.println("--学生信息--");
        s1.showInfo();
        s2.showInfo();
        System.out.println();
        System.out.println("--课程信息--");
        c1.showInfo();
        c2.showInfo();
    }
}
