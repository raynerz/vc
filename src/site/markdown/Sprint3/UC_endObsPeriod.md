# **UC1: end observation period**
### **Scope:**
 Patient Monitoring System  
### **Level:**
 User goal  
### **Primary Actor:**
Doctor  
### **Preconditions:**
Doctor has a valid e-mail address as username, an account has been created and a valid password has been sent by e-mail.  
The doctor is logged in, and an observation period is started.  
### **Main success scenario:**  
1. Doctor enters the identitfication of a patient
2. The patient is found in the DataBase
3. The observation period corressponding to the patient is ended
### **Extension:**  
a. Invalid patient idtificator : System displays an error message "The patient doesn't exist."  

## SSD (System Sequence Diagram)  

![SSD: end obs. period](../images/SSD_endOP.jpeg)  

## SD (Sequence Diagram)  

![SD: end obs. period](../images/SD_endOP.jpg)  

## DCD (Design Class Diagram)  

![DCD: end obs. period](../images/DCD_endOP.jpg)  
