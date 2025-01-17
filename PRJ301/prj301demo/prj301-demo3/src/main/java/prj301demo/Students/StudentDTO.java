/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prj301demo.Students;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import prj301demo.Users.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class StudentDTO {
    
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    
}
