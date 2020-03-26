# **UC1: Login**
### **Scope:**
Patient Monitoring System  
### **Level:**
User goal  
### **Primary Actor:**
Doctor  
### **Preconditions:**
Doctor has a valid e-mail address as username, an account has been created and a valid password has been sent by e-mail.  
### **Postconditions:**
The doctor is logged in and can use the functionalities of the system.  
### **Main success scenario:**  
1. Doctor enters his e-mail address as username and his own Password  
2. System checks if the username corresponds to an existing account and to the password.  
3. System stores the fact that the doctor is logged in.  

### **Extension:**  
a. Invalid username or password. System displays an error message "The username or password is not correct. Please check your credentials."  

<<<<<<< HEAD

## SSD (System Sequence Diagram)

![SSD: Login](../images/Login_SSD.jpg)  

## SD (Sequence Diagram)

![SD: login](../images/Login_SD.jpg)

## DCD (Design Class Diagram)
=======
## SSD (System Sequence Diagram)  

![SSD: Login](../images/Login_SSD.jpg)  

## SD (Sequence Diagram)  

![SD: login](../images/SD_login.jpg)

## DCD (Design Class Diagram)  
>>>>>>> test

![DCD: login](../images/DCDLoginHandler.jpg)
