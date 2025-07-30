package com.codtech;

import org.apache.mahout.cf.taste.impl.model.*;
import org.apache.mahout.cf.taste.impl.neighborhood.*;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.*;
import org.apache.mahout.cf.taste.recommender.*;
import org.apache.mahout.cf.taste.similarity.*;
import org.apache.mahout.cf.taste.impl.common.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.*;

public class ExcelRecommender {

    private static final Map<Long, String> itemNames = Map.of(
        101L, "Wireless Mouse",
        102L, "Mechanical Keyboard",
        103L, "Laptop Stand",
        104L, "USB Hub",
        105L, "Webcam",
        106L, "Monitor Arm",
        107L, "Desk Lamp",
        108L, "Docking Station",
        109L, "External Hard Drive"
    );

    public static void main(String[] args) throws Exception {

        InputStream file = ExcelRecommender.class.getResourceAsStream("/sample_user_data.xlsx");

        if (file == null) {
            throw new FileNotFoundException("Excel file 'user_data.xlsx' not found.");
        }

        FastByIDMap<PreferenceArray> preferenceData = readExcel(file);

        DataModel model = new GenericDataModel(preferenceData);

        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

        NearestNUserNeighborhood neighborhood = new NearestNUserNeighborhood(4, similarity, model);

        Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

        LongPrimitiveIterator userIterator = preferenceData.keySetIterator();

        while (userIterator.hasNext()) {
            long userId = userIterator.nextLong();
            System.out.println("Recommendations for user " + userId + ":");

            List<RecommendedItem> recommendations = recommender.recommend(userId, 3);

            if (recommendations.isEmpty()) {
                System.out.println("No recommendations found");
            } else {
                for (RecommendedItem item : recommendations) {
                    String name = itemNames.getOrDefault(item.getItemID(), "Item " + item.getItemID());
                    System.out.println("Item Name: " + name);
                    System.out.println("Item ID: " + item.getItemID());
                    System.out.println("Predicted Score: " + item.getValue());
                    System.out.println();
                }
            }
        }
    }

    private static FastByIDMap<PreferenceArray> readExcel(InputStream inputStream) throws IOException {
    	
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        Map<Long, List<GenericPreference>> userRatings = new HashMap<>();

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }

            long userId = (long) row.getCell(0).getNumericCellValue();
            long itemId = (long) row.getCell(1).getNumericCellValue();
            float rating = (float) row.getCell(2).getNumericCellValue();

            userRatings
                .computeIfAbsent(userId, k -> new ArrayList<>())
                .add(new GenericPreference(userId, itemId, rating));
        }

        workbook.close();

        FastByIDMap<PreferenceArray> preferenceMap = new FastByIDMap<>();
        for (Map.Entry<Long, List<GenericPreference>> entry : userRatings.entrySet()) {
            PreferenceArray array = new GenericUserPreferenceArray(entry.getValue());
            preferenceMap.put(entry.getKey(), array);
        }

        return preferenceMap;
        
    }
}
