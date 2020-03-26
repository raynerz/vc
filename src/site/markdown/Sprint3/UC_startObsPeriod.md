# **UC1: start observation period**
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
1. Doctor enters the dates and the patient identificator
2. The patient and corressponding device are search in the Database.
3. The new observation period is created
4. The observation period is assigned to the device and the patient and device are linked

### **Extension:**  
a. Invalid device id or patient id displays an error message "The device or patient doesn't exist."  

## SSD (System Sequence Diagram)  

![SSD: start obs. period](../images/SSD_startOP.jpeg)

## SD (Sequence Diagram)  

![SD: start obs. period](../images/SD_startOP.jpg)

## DCD (Design Class Diagram)  

![DCD: start obs. period](../images/DCD_startOP.jpg)
