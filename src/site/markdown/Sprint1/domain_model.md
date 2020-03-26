## Domain model

This diagram represent the different entities of the app to be develop and there relationships. The goal is to have a simple overview of the project.

![Domain model](../images/DomainModel.jpg)

## Entity description

**Doctor** : this entity represent a doctor in the hospital. A doctor is represented by a name and credential. He can assign devices to patients to monitor there temperature.

**Device** : the Device entity represent the physical device used to measure a patient’s temperature.

**Patient** : it represent a patient from the hospital. A patient is represented by a name and a insurance number.

**Observation** period : this is the period in which measurements will be made from the device.

**Measurement** : the measurements are the temperatures of a patient. Measurements will be saved in the device.

**SysAdmin** : this entity will only be implemented if the project is finished earlier than expected. It represent the administrator of the system.
