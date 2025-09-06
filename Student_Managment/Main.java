package Student_Managment;
import java.util.*;
import java.io.*;
class Student implements Serializable{
    private static final long serialVersionUID = 1L;
    private transient Scanner sc = new Scanner(System.in);
    private int rollNo,total;
    private String Name,status,Grade;
    private int[] marks = new int[5];
    private float average;
    Student(int r,String n){
        this.rollNo = r;
        this.Name = n;
        this.setMarks();
        this.statusAndGrade();

    }
    public int getRollNo(){return this.rollNo;}
    public String getName(){return this.Name;}
    public String getMarks(){return Arrays.toString(this.marks);}
    public int getTotal(){return this.total;}
    public float getAvg(){return this.average;}
    public String getGrade(){return this.Grade;}
    public String getPass(){return this.status;}
    
    public void setRollNo(int r){this.rollNo=r;}
    public void setName(String n){this.Name=n;}
    public void setMarks(){
        sc = new Scanner(System.in);
        int sum=0,mark;
        System.out.print("Enter Marks(0<=marks<=100): ");
        for(int i=0;i<5;i++){
            mark = sc.nextInt();
            if(mark>=0 && mark<=100){
                this.marks[i] = mark;
                sum+=this.marks[i];
            }else{
                System.out.print("Invalid Mark,"+"\n"+"ReEnter the mark correctly: "+"\n");
                i--;
            }
        }
        this.total = sum;
        this.average = (float)sum/5;
        this.statusAndGrade();
    }
    public void setParticularMark(){
        sc = new Scanner(System.in);
        System.out.print("Enter the subject Number to update -> (0,1,2,3,4): ");
        int i = sc.nextInt();
        boolean flag = true;
        System.out.print("Enter the new Mark: ");
        while(flag){
            int mark = sc.nextInt();
            if(mark>=0 && mark<=100){
                this.total -= this.marks[i]; 
                this.marks[i] = mark;
                this.total+=this.marks[i];
                this.average = (float)this.total/5;
                this.statusAndGrade();
                flag = false;
            }else{
                System.out.print("Invalid Mark,\nReEnter the mark correctly: \n");
                flag = true;
            }
        }
    }

    public void display(){
        System.out.println("Roll No: "+this.rollNo
                            +"\nName: "+this.Name
                            +"\nMarks: "+Arrays.toString(this.marks)
                            +"\nTotal Mark: "+this.total
                            +"\nAverage: "+this.average
                            +"\nStatus: "+this.status
                            +"\nGrade: "+this.Grade);
    }
    public void statusAndGrade(){
           this.status = this.total>=200 ? "PASS" : "FAIL";
           if(this.total<200){
                this.Grade="D";
           }else if(this.total<250){
                this.Grade="C";
           }else if(this.total<300){
                this.Grade="C+";
           }else if(this.total<350){
                this.Grade="B";
           }else if(this.total<400){
                this.Grade="B+";
           }else if(this.total<450){
                this.Grade="A";
           }else if(this.total<475){
                this.Grade="A+";
           }else{
                this.Grade="O";
           }
    }
}
public class Main{
    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        loadData();
        while(true){
            System.out.print("||=======Student Managment System=======||");
            System.out.print("\n1. Add student");
            System.out.print("\n2. View all students");
            System.out.print("\n3. Top Scorer");
            System.out.print("\n4. Sort Students By Mark");
            System.out.print("\n5. Search student by Roll No");
            System.out.print("\n6. Search student by Name");
            System.out.print("\n7. Update student");
            System.out.print("\n8. Delete a student details");
            System.out.print("\n9. Exit");
            System.out.print("\nEnter your choice: ");
            int choice = sc.nextInt();
            switch(choice){
                case 1:
                    addStudentDetails();
                    saveData();
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    topScorer();
                    break;
                case 4:
                    sortByMarks();
                    break;
                case 5:
                    searchStudentByRollNo();
                    break;
                case 6:
                    searchStudentByName();
                    break;
                case 7:
                    updateStudent();
                    saveData();
                    break;
                case 8:
                    deleteStudent();
                    saveData();
                    break;
                case 9:
                    System.out.println("Exiting....");
                    return;
                default:
                    System.out.println("Invalid Choice, Try again");
            }

        }
    }
    public static void addStudentDetails(){
        System.out.print("Enter Student Roll No: ");
        int rollNo = sc.nextInt();
        for(Student s : students ){
            if(s.getRollNo() == rollNo){
                System.out.println("Roll No already exist, Duplicates are not allowed");
                return;
            }
        }
        sc.nextLine();
        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();
        students.add(new Student(rollNo,name));
        System.out.println("\nStudent Details Succesfully Added\n");
    }
    public static void viewStudents(){
        if(students.isEmpty()){
            System.out.println("No Students Found");
            return;
        }
        System.out.println("|_____Student List_____|\n");
        for(Student s : students){
            s.display();
            System.out.println("______________________");
        }
    }
    public static void topScorer(){
        if(students.isEmpty()){
            System.out.println("No Student Found");
            return;
        }
        Student topper = students.get(0);
        for(Student s : students){
            if(s.getTotal()>topper.getTotal()){
                topper = s;
            }
        }
        System.out.println("||TOPPER OF SCHOOL||");
        topper.display();
    }
    public static void searchStudentByName(){
        sc.nextLine();
        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();
        for(Student s : students){
            if(s.getName().equalsIgnoreCase(name)){
                System.out.println("Student Found\n");
                s.display();
                return;
            }
        }
        System.out.println("Student not Found");
    }
    public static void searchStudentByRollNo(){
        System.out.print("Enter the Student Roll No: ");
        for(Student s : students){
            if(s.getRollNo()==sc.nextInt()){
                System.out.println("\nStudent Found\n");
                s.display();
                return;
            }
        }
        System.out.println("Student not Found");
    }
    public static void updateStudent(){
        System.out.print("Enter the Student Roll No to Update: ");
        int r = sc.nextInt();
        for(Student s : students){
            if(s.getRollNo() == r){
                System.out.println("Enter which detail to update\n\n1. Roll No\n2. Name\n3. Marks\n4. All");
                int choice = sc.nextInt();
                String Name;
                int n;
                switch(choice){
                    case 1:
                        System.out.print("Enter new Roll No: ");
                        n = sc.nextInt();
                        for(Student a : students ){
                            if(a.getRollNo() == n){
                            System.out.println("Roll No already exist, Duplicates are not allowed");
                            return;
                            }
                        }
                        s.setRollNo(n);
                        System.out.println("\nUpdated Successfully");
                        break;
                    case 2:
                        sc.nextLine();
                        System.out.print("Enter the new Name: ");
                        Name = sc.nextLine();
                        s.setName(Name);
                        System.out.println("\nUpdated Successfully");
                        break;
                    case 3:
                        updateMarks(s);
                        System.out.println("\nUpdated Successfully");
                        break;
                    case 4:
                        System.out.print("Enter new Roll No: ");
                        n = sc.nextInt();
                        for(Student a : students ){
                            if(a.getRollNo() == n){
                            System.out.println("Roll No already exist, Duplicates are not allowed");
                            return;
                            }
                        }
                        s.setRollNo(n);
                        sc.nextLine();
                        System.out.print("Enter new name: ");
                        Name = sc.nextLine();
                        s.setName(Name);
                        updateMarks(s);
                        System.out.println("\nUpdated Successfully");
                        break;
                    default:
                        System.out.println("Invalid choice, Try again");
                        break;
                }
                return;
            }
        }
        System.out.println("Student not Found");
    }
    public static void deleteStudent(){
        System.out.print("Enter the Student Roll No: ");
        int r = sc.nextInt();
        boolean removed = students.removeIf(s -> s.getRollNo()==r);
        if(removed){
            System.out.println("Deleted Successfully");
            return;
        }
        System.out.println("Not Found");
    }
    public static void updateMarks(Student s){
        System.out.print("Do you want to update all Marks?(1 for YES, 0 for NO): ");
        if(sc.nextInt()==1){
            s.setMarks();
        }else{
            s.setParticularMark();
        }
    }
    public static void sortByMarks(){
        students.sort((s1,s2) -> Integer.compare(s2.getTotal(),s1.getTotal()));
        System.out.println("||Students Sorted according to Total Marks||\n");
        for(Student s : students){
            s.display();
            System.out.println("______________________");
        }
    }
    public static void saveData(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("students.dat"))){
            out.writeObject(students);
        }catch(IOException e){
            System.out.println("Error Saving Data: "+e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    public static void loadData(){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("students.dat"))){
            students = (ArrayList<Student>) in.readObject();
        }catch(IOException | ClassNotFoundException e){
            students = new ArrayList<>();
        }
    }
}