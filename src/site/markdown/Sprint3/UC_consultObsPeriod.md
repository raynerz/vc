# **UC1: consult observation period**
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
3. The corresponding observation period is returned

### **Extension:**  
a. Invalid patient idtificator : System displays an error message "The patient doesn't exist."  

## SSD (System Sequence Diagram)  

![SSD: consult obs. period](../images/SSD_consultOP.jpeg )

## SD (Sequence Diagram)  

![SD: consult obs. period](../images/SD_consultOP.jpg)

## DCD (Design Class Diagram)  

![DCD: consult obs. period](../images/DCD_consultOP.jpg)
