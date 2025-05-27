package view;
import model.StudentInfo;
import model.StudentUpdateDto;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import repository.StudentRepository;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private final static StudentRepository studentRepository = new StudentRepository();
    private final static Scanner scanner = new Scanner(System.in);

    private static void option(){
        System.out.println("=".repeat(98));
        System.out.println("*".repeat(24) + " 🥷🏾💫Welcome to Our Student Management System💫🥷🏾 " + "*".repeat(23));
        System.out.println("-".repeat(98));
        System.out.println("[+] 1 -> Add a new student👨🏾‍💻");
        System.out.println("[+] 2 -> View all students👨🏾‍💻");
        System.out.println("[+] 3 -> Update student by ID👨🏾‍💻");
        System.out.println("[+] 4 -> Delete a student by ID👨🏾‍💻");
        System.out.println("[+] 5 -> Search students by grade👨🏾‍💻");
        System.out.println("[+] 6 -> List top-performing students👨🏾‍💻");
        System.out.println("[+] 7 -> Calculate the average score👨🏾‍💻");
        System.out.println("[+] 8 -> Add multiple students into bulk insertion 👨🏾‍💻");
        System.out.println("[+] 9 -> Exit our program 👈🏾");
        System.out.println("-".repeat(98));
    }

    private static void processBulkInsertion() {
        System.out.println("*".repeat(32) + " 🥷🏾💫 Bulk Student Insertion 💫🥷🏾" + "*".repeat(33));
        System.out.println("Enter student information into bulk type 'done' when finished 👈🏾😊");

        List<StudentInfo> studentsToAdd = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- New Student Entry ---");
            System.out.print(" => Enter student ID or 'done' to finish : ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("done")) {
                break;
            }
            try {
                int id = Integer.parseInt(input);
                System.out.print(" => Enter the student's name : ");
                String stuName = scanner.nextLine();

                int stuAge = getPositiveIntInput(scanner, " => Enter the student's age : ");

                System.out.print(" => Enter the student's gender : ");
                String stuGender = scanner.nextLine();
                System.out.print(" => Enter the student's year : ");
                String stuYear = scanner.nextLine();
                System.out.print(" => Enter the Student's semester : ");
                String stuSemester = scanner.nextLine();

                String stuEmail;
                while(true) {
                    System.out.print(" => Enter the student's email : ");
                    stuEmail = scanner.nextLine();
                    if(studentRepository.validatedEmail(stuEmail)) {
                        break;
                    } else {
                        System.out.println("Invalid Student's email, Please try again 👈🏾😊");
                        System.out.println("for example -> student@domain.com)");
                    }
                }

                System.out.print(" => Enter the student's phone number : ");
                String stuPhone = scanner.nextLine();
                Date stuEnrollmentDate = Date.valueOf(LocalDate.now());
                System.out.print(" => Enter the student's current city : ");
                String stuCurrentCity = scanner.nextLine();
                System.out.print(" => Enter the student's hometown : ");
                String stuHometown = scanner.nextLine();
                System.out.print(" => Enter the student's department : ");
                String stuDepartment = scanner.nextLine();

                int stuLinuxScore = getPositiveIntInput(scanner, " => Enter the student's linux score : ");
                int stuJavaScore = getPositiveIntInput(scanner, " => Enter the student's java score : ");
                int stuPythonScore = getPositiveIntInput(scanner, " => Enter the student's python score : ");
                int stuJavascriptScore = getPositiveIntInput(scanner, " => Enter the student's javascript score : ");
                int stuReactjsScore = getPositiveIntInput(scanner, " => Enter the student's reactjs score : ");

                char stuGrade = studentRepository.findStudentGrade(stuLinuxScore, stuJavaScore, stuPythonScore, stuJavascriptScore, stuReactjsScore);
                float stuScore = studentRepository.findStudentScore(new int[] {stuLinuxScore, stuJavaScore, stuPythonScore, stuJavascriptScore, stuReactjsScore});
                double stuAverage = studentRepository.findStudentAverage(new int[] {stuLinuxScore, stuJavaScore, stuPythonScore, stuJavascriptScore, stuReactjsScore});

                StudentInfo studentInfo = new StudentInfo(id, stuName, stuAge, stuGender, stuYear, stuSemester, stuEmail, stuPhone, stuEnrollmentDate, stuCurrentCity, stuHometown, stuDepartment, stuLinuxScore, stuJavaScore, stuPythonScore, stuJavascriptScore, stuReactjsScore, stuGrade, stuScore, stuAverage);

                studentsToAdd.add(studentInfo);
                System.out.println("Student added to batch Current batch size : " + studentsToAdd.size());

            } catch (NumberFormatException e) {
                System.out.println("Invalid input Please enter a valid number or 'done' to finish");
            }
        }

        if (!studentsToAdd.isEmpty()) {
            System.out.println("\nProcessing batch insertion of " + studentsToAdd.size() + " students...");
            int[] results = studentRepository.addStudentsInToBatch(studentsToAdd);
            int successCount = 0;
            for (int result : results) {
                if (result >= 0) {
                    successCount++;
                }
            }
            System.out.println("Successfully inserted " + successCount + " out of " + studentsToAdd.size() + " students 😊🚀");
        } else {
            System.out.println("No students to insert Batch insertion canceled 🤦‍♂️🥹");
        }
    }

    private static int getPositiveIntInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value > 0) {
                    return value;
                } else {
                    System.out.println("Value must be positive number 👈🏾😊");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, Please try again");
            }
        }
    }

    public static void process(int opt){
        switch(opt){
            case 1 -> {
                System.out.println("*".repeat(12) + " 🥷🏾💫 Please complete all the forms below to add student information 💫🥷🏾" + "*".repeat(12));
                System.out.print(" => Enter the student's ID to add student information : ");
                int id = scanner.nextInt();
                scanner.nextLine();
                System.out.print(" => Enter the student's name to add student information : ");
                String stuName = scanner.nextLine();

                int stuAge = 0;
                while (true){
                    System.out.print(" => Enter the student's age to add student information : ");
                    try {
                        stuAge = Integer.parseInt(scanner.nextLine());
                        if(stuAge > 0){
                            break;
                        }else{
                            System.out.println("Student's Age must be positive number 👈🏾😊");
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Invalid Student's age input, Please try again");
                    }
                }
                System.out.print(" => Enter the student's gender to add student information : ");
                String stuGender = scanner.nextLine();
                System.out.print(" => Enter the student's year to add student information : ");
                String stuYear = scanner.nextLine();
                System.out.print(" => Enter the Student's semester to add student information : ");
                String stuSemester = scanner.nextLine();
                String stuEmail;
                while(true){
                    System.out.print(" => Enter the student's email to add student information : ");
                    stuEmail = scanner.nextLine();
                    if(studentRepository.validatedEmail(stuEmail)){
                        break;
                    }else{
                        System.out.println("Invalid Student's email, Please try again 👈🏾😊");
                        System.out.println("for example -> student@domain.com)");
                    }
                }

                System.out.print(" => Enter the student's phone number to add student information : ");
                String stuPhone = scanner.nextLine();
                Date stuEnrollmentDate = Date.valueOf(LocalDate.now());
                System.out.print(" => Enter the student's current city to add student information : ");
                String stuCurrentCity = scanner.nextLine();
                System.out.print(" => Enter the student's hometown to add student information : ");
                String stuHometown = scanner.nextLine();
                System.out.print(" => Enter the student's department to add student information : ");
                String stuDepartment = scanner.nextLine();
                int stuLinuxScore = 0;
                while (true){
                    System.out.print(" => Enter the student's linux score to add student information : ");
                    try {
                        stuLinuxScore = Integer.parseInt(scanner.nextLine());
                        if(stuLinuxScore > 0){
                            break;
                        }else{
                            System.out.println("Student's Linux score must be positive number 👈🏾😊");
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Invalid Student's Linux score input, Please try again");
                    }
                }

                int stuJavaScore = 0;
                while (true){
                    System.out.print(" => Enter the student's java score to add student information : ");
                    try {
                        stuJavaScore = Integer.parseInt(scanner.nextLine());
                        if(stuJavaScore > 0){
                            break;
                        }else{
                            System.out.println("Student's java score must be positive number 👈🏾😊");
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Invalid Student's java score input, Please try again");
                    }
                }

                int stuPythonScore = 0;
                while (true){
                    System.out.print(" => Enter the student's python score to add student information : ");
                    try {
                        stuPythonScore = Integer.parseInt(scanner.nextLine());
                        if(stuPythonScore > 0){
                            break;
                        }else{
                            System.out.println("Student's java python score must be positive number 👈🏾😊");
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Invalid Student's python score input, Please try again");
                    }
                }

                int stuJavascriptScore = 0;
                while (true){
                    System.out.print(" => Enter the student's javascript score to add student information : ");
                    try {
                        stuJavascriptScore = Integer.parseInt(scanner.nextLine());
                        if(stuJavascriptScore > 0){
                            break;
                        }else{
                            System.out.println("Student's java javascript score must be positive number 👈🏾😊");
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Invalid Student's javascript score input, Please try again");
                    }
                }

                int stuReactjsScore = 0;
                while (true){
                    System.out.print(" => Enter the student's reactjs score to add student information : ");
                    try {
                        stuReactjsScore = Integer.parseInt(scanner.nextLine());
                        if(stuReactjsScore > 0){
                            break;
                        }else{
                            System.out.println("Student's java reactjs score must be positive number 👈🏾😊");
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Invalid Student's reactjs score input, Please try again");
                    }
                }
                char stuGrade = studentRepository.findStudentGrade(stuLinuxScore,stuJavaScore,stuPythonScore,stuJavascriptScore,stuReactjsScore);
                float stuScore = studentRepository.findStudentScore(new int[] {stuLinuxScore,stuJavaScore,stuPythonScore,stuJavascriptScore,stuReactjsScore});
                double stuAverage = studentRepository.findStudentAverage(new int[] {stuLinuxScore,stuJavaScore,stuPythonScore,stuJavascriptScore,stuReactjsScore});
                StudentInfo studentInfo = new StudentInfo(id,stuName,stuAge,stuGender,stuYear,stuSemester,stuEmail,stuPhone,stuEnrollmentDate,stuCurrentCity,stuHometown,stuDepartment,stuLinuxScore,stuJavaScore,stuPythonScore,stuJavascriptScore,stuReactjsScore,stuGrade,stuScore,stuAverage);
                studentRepository.addStudentInfo(studentInfo);
            }
            case 2 -> {
                System.out.println("*".repeat(20) + " 🥷🏾💫 below is the list of all student information 💫🥷🏾 " + "*".repeat(21));
                List<StudentInfo> student = studentRepository.getAllStudentInfo();

                if(student == null || student.isEmpty()){
                    System.out.println("No student information found in the database 🤦‍♂️🥹");
                    break;
                }
                Table table =  new Table(20, BorderStyle.CLASSIC,ShownBorders.ALL);

                table.addCell("Student ID", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Name", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Age", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Gender", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Year", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Semester", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Email", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Phone Number", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Enrolled Date", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Current city", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Hometown", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Department", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Linux", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Java", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Python", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Javascript", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Reactjs", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Grade", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Score", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Average", new CellStyle(CellStyle.HorizontalAlign.center));

                table.setColumnWidth(0,10,20);
                table.setColumnWidth(1,10,20);
                table.setColumnWidth(3,10,20);
                table.setColumnWidth(4,10,20);
                table.setColumnWidth(5,10,20);
                table.setColumnWidth(6,10,20);
                table.setColumnWidth(7,10,20);
                table.setColumnWidth(8,10,20);
                table.setColumnWidth(9,10,20);
                table.setColumnWidth(10,10,20);
                table.setColumnWidth(11,10,20);
                table.setColumnWidth(12,10,20);
                table.setColumnWidth(13,10,20);
                table.setColumnWidth(14,10,20);
                table.setColumnWidth(15,10,20);
                table.setColumnWidth(16,10,20);
                table.setColumnWidth(17,10,20);
                table.setColumnWidth(18,10,20);
                table.setColumnWidth(19,10,20);

                for(int i =0; i<20; i++){
                    table.setColumnWidth(i,23,30);
                }
                for(StudentInfo students : student){
                    table.addCell(String.valueOf(students.getId()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getName(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getAge()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getGender(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getYear(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getSemester(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getEmail(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getPhone_number(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getEnrollment_date()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getCurrent_city(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getHometown(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getDepartment(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getLinux()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getJava()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getPython()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getJavascript()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getReactjs()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getGrade()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getScore()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getAverage()), new CellStyle(CellStyle.HorizontalAlign.center));
                }
                System.out.println(table.render());

            }
            case 3 -> {
                System.out.println("*".repeat(11) + " 🥷🏾💫 Please complete all the forms below to update student information 💫🥷🏾" +"*".repeat(10));
                System.out.print(" => Enter the student's ID to update student information : ");
                int id = scanner.nextInt();
                scanner.nextLine();
                System.out.print(" => Enter the student's name to update student information : ");
                String stuName = scanner.nextLine();

                int stuAge = 0;
                while (true){
                    System.out.print(" => Enter the student's age to add student information : ");
                    try {
                        stuAge = Integer.parseInt(scanner.nextLine());
                        if(stuAge > 0){
                            break;
                        }else{
                            System.out.println("Student's Age must be positive number 👈🏾😊");
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Invalid Student's age input, Please try again");
                    }
                }
                System.out.print(" => Enter the student's gender to add student information : ");
                String stuGender = scanner.nextLine();
                System.out.print(" => Enter the student's year to add student information : ");
                String stuYear = scanner.nextLine();
                System.out.print(" => Enter the Student's semester to add student information : ");
                String stuSemester = scanner.nextLine();
                String stuEmail;
                while(true){
                    System.out.print(" => Enter the student's email to add student information : ");
                    stuEmail = scanner.nextLine();
                    if(studentRepository.validatedEmail(stuEmail)){
                        break;
                    }else{
                        System.out.println("Invalid Student's email, Please try again 👈🏾😊");
                        System.out.println("for example -> student@domain.com)");
                    }
                }

                System.out.print(" => Enter the student's phone number to add student information : ");
                String stuPhone = scanner.nextLine();
                Date stuEnrollmentDate = Date.valueOf(LocalDate.now());
                System.out.print(" => Enter the student's current city to add student information : ");
                String stuCurrentCity = scanner.nextLine();
                System.out.print(" => Enter the student's hometown to add student information : ");
                String stuHometown = scanner.nextLine();
                System.out.print(" => Enter the student's department to add student information : ");
                String stuDepartment = scanner.nextLine();
                int stuLinuxScore = 0;
                while (true){
                    System.out.print(" => Enter the student's linux score to add student information : ");
                    try {
                        stuLinuxScore = Integer.parseInt(scanner.nextLine());
                        if(stuLinuxScore > 0){
                            break;
                        }else{
                            System.out.println("Student's Linux score must be positive number 👈🏾😊");
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Invalid Student's Linux score input, Please try again");
                    }
                }

                int stuJavaScore = 0;
                while (true){
                    System.out.print(" => Enter the student's java score to add student information : ");
                    try {
                        stuJavaScore = Integer.parseInt(scanner.nextLine());
                        if(stuJavaScore > 0){
                            break;
                        }else{
                            System.out.println("Student's java score must be positive number 👈🏾😊");
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Invalid Student's java score input, Please try again");
                    }
                }

                int stuPythonScore = 0;
                while (true){
                    System.out.print(" => Enter the student's python score to add student information : ");
                    try {
                        stuPythonScore = Integer.parseInt(scanner.nextLine());
                        if(stuPythonScore > 0){
                            break;
                        }else{
                            System.out.println("Student's java python score must be positive number 👈🏾😊");
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Invalid Student's python score input, Please try again");
                    }
                }

                int stuJavascriptScore = 0;
                while (true){
                    System.out.print(" => Enter the student's javascript score to add student information : ");
                    try {
                        stuJavascriptScore = Integer.parseInt(scanner.nextLine());
                        if(stuJavascriptScore > 0){
                            break;
                        }else{
                            System.out.println("Student's java javascript score must be positive number 👈🏾😊");
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Invalid Student's javascript score input, Please try again");
                    }
                }

                int stuReactjsScore = 0;
                while (true){
                    System.out.print(" => Enter the student's reactjs score to add student information : ");
                    try {
                        stuReactjsScore = Integer.parseInt(scanner.nextLine());
                        if(stuReactjsScore > 0){
                            break;
                        }else{
                            System.out.println("Student's java reactjs score must be positive number 👈🏾😊");
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Invalid Student's reactjs score input, Please try again");
                    }
                }
                char stuGrade = studentRepository.findStudentGrade(stuLinuxScore,stuJavaScore,stuPythonScore,stuJavascriptScore,stuReactjsScore);
                float stuScore = studentRepository.findStudentScore(new int[] {stuLinuxScore,stuJavaScore,stuPythonScore,stuJavascriptScore,stuReactjsScore});
                double stuAverage = studentRepository.findStudentAverage(new int[] {stuLinuxScore,stuJavaScore,stuPythonScore,stuJavascriptScore,stuReactjsScore});
                StudentUpdateDto studentUpdateDto = new StudentUpdateDto(stuName,stuAge,stuGender,stuYear,stuSemester,stuEmail,stuPhone,stuEnrollmentDate,stuCurrentCity,stuHometown,stuDepartment,stuLinuxScore,stuJavaScore,stuPythonScore,stuJavascriptScore,stuReactjsScore,stuGrade,stuScore,stuAverage);
                studentRepository.updateStudentInfo(id,studentUpdateDto);
            }
            case 4 -> {
                System.out.println("*".repeat(10) + " 🥷🏾💫 Please complete a forms below to delete student information by ID 💫🥷🏾 " + "*".repeat(10));
                System.out.print(" => Enter the student's ID to delete student information : ");
                int id = scanner.nextInt();
                studentRepository.deleteStudentInfo(id);
            }
            case 5 -> {
                System.out.println("*".repeat(9) +" 🥷🏾💫 Please complete a forms below to search student information by Grade 💫🥷🏾" + "*".repeat(9));
                System.out.print(" => Enter the student's grade to find student information : ");
                char grade = scanner.next().charAt(0);
                grade = Character.toUpperCase(grade);

                List<StudentInfo> results = studentRepository.searchStudentInfoByGrade(grade);
                System.out.println("*".repeat(18) + " 🥷🏾💫 below is the list of student information width " + grade + " 💫🥷🏾 " + "*".repeat(19));

                if(results == null || results.isEmpty()){
                    System.out.println("No student information with grade " + grade + " found in the database 🤦‍♂️🥹");
                    break;
                }
                Table table =  new Table(20, BorderStyle.CLASSIC,ShownBorders.ALL);

                table.addCell("Student ID", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Name", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Age", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Gender", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Year", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Semester", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Email", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Phone Number", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Enrolled Date", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Current city", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Hometown", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Department", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Linux", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Java", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Python", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Javascript", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Reactjs", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Grade", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Score", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Average", new CellStyle(CellStyle.HorizontalAlign.center));

                table.setColumnWidth(0,10,20);
                table.setColumnWidth(1,10,20);
                table.setColumnWidth(3,10,20);
                table.setColumnWidth(4,10,20);
                table.setColumnWidth(5,10,20);
                table.setColumnWidth(6,10,20);
                table.setColumnWidth(7,10,20);
                table.setColumnWidth(8,10,20);
                table.setColumnWidth(9,10,20);
                table.setColumnWidth(10,10,20);
                table.setColumnWidth(11,10,20);
                table.setColumnWidth(12,10,20);
                table.setColumnWidth(13,10,20);
                table.setColumnWidth(14,10,20);
                table.setColumnWidth(15,10,20);
                table.setColumnWidth(16,10,20);
                table.setColumnWidth(17,10,20);
                table.setColumnWidth(18,10,20);
                table.setColumnWidth(19,10,20);

                for(int i =0; i<20; i++){
                    table.setColumnWidth(i,23,30);
                }
                for(StudentInfo students : results){
                    table.addCell(String.valueOf(students.getId()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getName(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getAge()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getGender(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getYear(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getSemester(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getEmail(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getPhone_number(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getEnrollment_date()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getCurrent_city(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getHometown(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getDepartment(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getLinux()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getJava()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getPython()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getJavascript()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getReactjs()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getGrade()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getScore()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getAverage()), new CellStyle(CellStyle.HorizontalAlign.center));
                }
                System.out.println(table.render());
            }
            case 6 -> {
                System.out.println("*".repeat(20) + " 🥷🏾💫 below is the list of top student information 💫🥷🏾 " + "*".repeat(21));
                List<StudentInfo> resultTopStudent = studentRepository.listTopPerformingStudents();

                if(resultTopStudent == null || resultTopStudent.isEmpty()){
                    System.out.println("No student information found in the database 🤦‍♂️🥹");
                    break;
                }
                Table table =  new Table(20, BorderStyle.CLASSIC,ShownBorders.ALL);

                table.addCell("Student ID", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Name", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Age", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Gender", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Year", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Semester", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Email", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Phone Number", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Enrolled Date", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Current city", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Hometown", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Department", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Linux", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Java", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Python", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Javascript", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Reactjs", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Grade", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Score", new CellStyle(CellStyle.HorizontalAlign.center));
                table.addCell("Student Average", new CellStyle(CellStyle.HorizontalAlign.center));

                table.setColumnWidth(0,10,20);
                table.setColumnWidth(1,10,20);
                table.setColumnWidth(3,10,20);
                table.setColumnWidth(4,10,20);
                table.setColumnWidth(5,10,20);
                table.setColumnWidth(6,10,20);
                table.setColumnWidth(7,10,20);
                table.setColumnWidth(8,10,20);
                table.setColumnWidth(9,10,20);
                table.setColumnWidth(10,10,20);
                table.setColumnWidth(11,10,20);
                table.setColumnWidth(12,10,20);
                table.setColumnWidth(13,10,20);
                table.setColumnWidth(14,10,20);
                table.setColumnWidth(15,10,20);
                table.setColumnWidth(16,10,20);
                table.setColumnWidth(17,10,20);
                table.setColumnWidth(18,10,20);
                table.setColumnWidth(19,10,20);

                for(int i =0; i<20; i++){
                    table.setColumnWidth(i,23,30);
                }
                for(StudentInfo students : resultTopStudent){
                    table.addCell(String.valueOf(students.getId()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getName(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getAge()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getGender(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getYear(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getSemester(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getEmail(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getPhone_number(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getEnrollment_date()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getCurrent_city(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getHometown(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(students.getDepartment(), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getLinux()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getJava()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getPython()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getJavascript()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getReactjs()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getGrade()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getScore()), new CellStyle(CellStyle.HorizontalAlign.center));
                    table.addCell(String.valueOf(students.getAverage()), new CellStyle(CellStyle.HorizontalAlign.center));
                }
                System.out.println(table.render());
            }
            case 7 -> {
                var resultStudentAverage = studentRepository.listAllStudentWithAverage();
                if(resultStudentAverage !=null && !resultStudentAverage.isEmpty()){
                    System.out.println("*".repeat(22) + " 🥷🏾💫 Below is a list of all student's averages 💫🥷🏾 " + "*".repeat(22));
                    System.out.printf("%-10s %-30s %-10s%n","ID","Name","Average");
                    System.out.println("-".repeat(98));
                    for(var result : resultStudentAverage){
                        System.out.printf("%-10d %-30s %-10.2f%n",
                                result.getId(),
                                result.getName(),
                                result.getAverage());
                    }
                }else{
                    System.out.println("No, Student's average is found 🤦‍♂️🥹");
                }
            }
            case 8 -> {
                processBulkInsertion();
            }
            case 9 -> {
                System.out.println("Student Management System is closed....");
                System.exit(0);
            }
            default -> {
                System.out.println("Please Enter the option number from 1 -> 8 Only 👈🏾😊");
            }
        }
    }

    public static void home(){
        while (true){
            option();
            System.out.print("[+] Enter your option here : ");
            int option = scanner.nextInt();
            process(option);
        }

    }
}
