package cn.edn.swu;

public class Course {
    private String courseId;
    private String courseName;
    private String teacher;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        System.out.println(this.courseName+"课程更换了编号");
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        System.out.println("更换了课程");
        this.courseName = courseName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        System.out.println(this.courseName+"课程更换了教师");
        this.teacher = teacher;
    }

    public Course(){} //无参构造函数

    public Course(String courseId,String courseName,String teacher){
        this.courseId=courseId;
        this.courseName=courseName;
        this.teacher=teacher;
    }  //带有参数的构造函数

    public void showInfo(){
        System.out.println("课程编号:"+courseId);
        System.out.println("课程名称："+courseId);
        System.out.println("任课教师："+teacher);
        System.out.println("--------------");
    }
}
