# **UC2: Add Patient**
### **Primary Actor:** 
Doctor
### **Pre-condition:** 
Patient must be in the database (or securely stored locally).
### **Post-condition:** 
A success message/Notification (and patient profile displayed if possible) must be shown upon correct addition of *Patient*.
### **Happy Path:**
  1. *System* checks patient (name, id) does not already exist in database.
  2. Create new patient (name, id).
  3. Link *Patient* with *Doctor*.
  4. Persists information either locally or remotely. (To be determined.)


## SSD (System Sequence Diagram)

This diagram represents the interaction the *Doctor* adds a new *Patient* into the system.

![SSD: addPtient](../images/SSD_AddPatient.png)

## Sequence of operation
This diagram provides a quick _'behind the scenes'_ view of what the structure when the *Doctor* logs in.

![Sequence](../images/Sequence_addPatient.png)

## SD: Sequence Diagram
This diagram represents the sequence initiated when the *Doctor* adds a new *Patient* into the system.

![SD: addPatient](../images/SD_addPatient.jpg)

## DCD: Design Class Diagram
This diagram shows the relations between the classes

![DCD: addPatient](../images/DCD_PatientHandler.jpg)
