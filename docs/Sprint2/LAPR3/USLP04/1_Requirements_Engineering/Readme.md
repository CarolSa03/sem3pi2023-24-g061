# USLP04 - Sowing Operation

## 1. Requirements Engineering

### 1.1. User Story Description

As an Agricultural Manager, I want to record a operationSowing operation.

### 1.2. Customer Specifications and Clarifications

**From the specifications document:**

> From USLP04 to USLP08 - As a Product Owner, I intend for features to be developed that allow the invocation of USBD11 to USBD15. The development of a graphical interface for the 'Farm Coordinator' application is not required; this interface can be in text mode.
>> Agricultural Operation - is any action developed within the context of agricultural activity, namely: operationSowing, planting, pruning, weeding, defoliating, staking, irrigating, fertilizing, and harvesting. Operations are usually carried out by operators, often assisted by agricultural machinery and instruments (such as tractors or pruning shears). They may involve the use of production factors. Agricultural operations are carried out throughout the year, typically based on the phenological stage of the crop and following a logical sequence. It should be noted that there are operations performed on plots where crops may not be established (e.g., soil preparation before seeding).

**From the client clarifications:**

> 2023/10/14
>
> **Question 1:** 

### 1.3. Acceptance Criteria

* **AC1:** The shown list must be valid.
* **AC2:** The data selected should be valid.
* 

### 1.4. Found out Dependencies

* **D1:** The system must have a list of plots.
* **D2:** The system must have a list of crop types.

### 1.5 Input and Output Data

**Input Data:**

* Selected data:
    * Plot

* Typed data:
    * Crop Type
    * Crop Quantity
    * Date/time slot
    * Climate data

**Output Data:**

* The system's status for success or failure of the registration.

### 1.6. System Sequence Diagram (SSD)

![uslp04-system-sequence-diagram.svg](svg%2Fuslp04-system-sequence-diagram.svg)