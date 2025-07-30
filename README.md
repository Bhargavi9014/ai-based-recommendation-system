# AI-BASED-RECOMMENDATION-SYSTEM

*COMPANY: CODTECH IT SOLUTIONS

*NAME: KOLIPAKA BHARGAVI

*INTER-ID: CT04DH1600

*DOMAIN: JAVA PROGRAMMING

*DURATION: 4 WEEKS

*MENTOR: NEELA SANTHOSH

## Description
This is a Java-based user-based collaborative filtering recommendation system that reads user-item-rating data from an Excel file using Apache POI and generates personalized item recommendations using Apache Mahout. It calculates user similarity via Pearson correlation and recommends top items based on neighbors’ preferences.

## Features
- Reads user-item-rating data from Excel (`.xlsx`) files  
- Uses Pearson correlation similarity metric for user similarity  
- Finds nearest user neighborhoods to base recommendations on  
- Generates top-N personalized recommendations per user  
- Maps item IDs to human-readable names for easy understanding  
- Handles cases where no recommendations are available  

## Topics Covered
- Apache Mahout collaborative filtering (UserSimilarity, Recommender)  
- Apache POI for reading Excel data  
- Java Collections and Generics  
- Stream and file input handling  
- Exception handling and resource management  
- Basic recommendation algorithm concepts  

## Requirements
- Java 8 or higher  
- Apache Mahout and Apache POI dependencies  
- Java IDE (Eclipse)  

## Execution Flow
- ✅ Ensure `sample_user_data.xlsx` is available in the resource path  
- 🔁 Run `RecommendationSystem.java` main class  
  - The program reads user ratings from Excel, builds the Mahout model, and prints recommendations for each user  
