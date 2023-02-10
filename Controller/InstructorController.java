package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import Model.InstructorCourseModel;
import View.InstructorPanelView;

public class InstructorController {
    InstructorCourseModel instructorModel;
    InstructorPanelView instructorView;

    public InstructorController(String email, String pass) throws SQLException
    {
        instructorModel = new InstructorCourseModel();
        instructorView = new InstructorPanelView();
        logout();
        fillCoursesList(email,pass);
        fillInstructorInfo(email, pass);
        coursesListListener();
        tableListener();
        buttonListener();
    }

    public void logout()
    {
        instructorView.getLogoutButton().addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                instructorView.getInstructorFrame().dispose();
                new LoginController();   
            }
            
        });
    }

    public void fillCoursesList(String instEmail, String instPass) throws SQLException
    {   
            List<String> courses = instructorModel.getInstructorCourses(instEmail, instPass);
            courses.forEach(code -> instructorView.getCoursesList().addItem(code));

    }

    public void fillInstructorInfo(String instEmail, String instPass) throws SQLException
    {
            HashMap<String,String> instructorInfo = instructorModel.getInstructorInfo(instEmail, instPass);
            instructorView.getInstructorNameLabel().setText("Full Name: "+instructorInfo.get("fullName"));
            instructorView.getInstructorIdLabel().setText("ID: "+instructorInfo.get("ID"));

    }

    public void coursesListListener()
    {
        instructorView.getCoursesList().addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent e){
                String courseCode = instructorView.getCoursesList().getSelectedItem().toString();
                instructorView.getCourseCodeField().setText(courseCode);
                try{
                    fillTable(courseCode);
                }catch(SQLException ex){ex.printStackTrace();}
            }
            
        });
    }

    public void fillTable(String courseCode) throws SQLException
    {
        Object[][] students = instructorModel.getEnrolledStudents(courseCode);
        instructorView.getTableModel().setRowCount(0);
        for(int i=0; i<students.length;i++)
            instructorView.getTableModel().addRow(students[i]);
    }

    public void tableListener() throws SQLException
    {
        instructorView.getStudentsCourseGradesTable().addMouseListener(new MouseListener(){


            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                int selectedRow = instructorView.getStudentsCourseGradesTable().getSelectedRow();
                String id = instructorView.getStudentsCourseGradesTable().getValueAt(selectedRow, 0).toString();
                instructorView.getStudentIdField().setText(id);
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
            
        });
            
    }

    public void buttonListener()
    {
        instructorView.getAddButton().addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String studentId = instructorView.getStudentIdField().getText().toString();
                String courseCode = instructorView.getCourseCodeField().getText().toString();
                String grade = instructorView.getStudentGradeField().getText().toString();

                if(!studentId.equals("") && !courseCode.equals("") && !grade.equals(""))
                {   
                    float gradeCasted=-1;
                    try{
                            gradeCasted = Float.valueOf(grade);
                            if(gradeCasted>=0 && gradeCasted<=100)
                            {
                                try{
                                    if(instructorModel.updateStudentGrade(studentId,courseCode,grade))
                                    {
                                        fillTable(courseCode);
                                        instructorView.displayMessage("Successfully Updated"); 
                                    }
                                    else
                                        instructorView.displayMessage("Error in updating student grade");   
                                }catch(SQLException ex){ex.printStackTrace();}   
                                instructorView.getStudentIdField().setText("");
                                instructorView.getStudentGradeField().setText("");
                            }
                            else 
                                instructorView.displayMessage("Grade must be between 0 and 100");
                        }catch(NumberFormatException ex)
                        {   ex.printStackTrace();
                            instructorView.displayMessage("Grade must be a number");
                        }

                    
                }
                else
                    instructorView.displayMessage("You must fill in all fields");
                
            }
            
        });
    }
}
