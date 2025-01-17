/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prj301demo.Student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 *
 * @author DUNGHUYNH
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    
    private int id;
    private String firstname;
    private String lastname;
    private int age;

}
