package utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static utils.Constants.TEST_DATA_FILE;

public class TestUtils {

    static ExcelUtils excelUtils = new ExcelUtils(TEST_DATA_FILE);

    public static List<String> getProductIds(int rowNum) {
        List<String> productIds = new ArrayList<>();
        short lastCellNum = excelUtils.getLastCellNum(0, rowNum);
        for (int i = 4; i < lastCellNum; i++) {
            String productId = excelUtils.getData(0, rowNum, i);
            if (productId != null || productId.isEmpty()) {
                productIds.add(productId);
            }
        }
        return productIds;
    }

    public static Object[][] getProductBrowsingTestData() {
        int rows = excelUtils.getRowCount(0);
        Object[][] testData = new Object[rows - 1][5];

        for (int i = 0; i < rows - 1; i++) {
            testData[i][0] = excelUtils.getData(0, i + 1, 0);
            testData[i][1] = excelUtils.getData(0, i + 1, 1);
            testData[i][2] = excelUtils.getData(0, i + 1, 2);
            testData[i][3] = excelUtils.getData(0, i + 1, 3);
            testData[i][4] = TestUtils.getProductIds(i + 1);
        }
        return testData;
    }

    public static Object[][] getCategoryNavigationTestData(Object[][] excelData) {
        HashSet<String> l1Categories = new HashSet<String>();
        HashSet<String> l2Categories = new HashSet<String>();
        HashMap<String, HashSet<String>> firstItr = new HashMap<String, HashSet<String>>();
        HashMap<String, HashSet<String>> secondItr = new HashMap<String, HashSet<String>>();
        HashMap<String, HashSet<String>> finalSet = new HashMap<String, HashSet<String>>();
        for (Object[] excelDatum : excelData) {
            String l1Category = (String) excelDatum[0];
            String l2Category = (String) excelDatum[1];
            l1Categories.add(l1Category);
            l2Categories.add(l1Category + ":" + l2Category);
        }
        for (String l1Category : l1Categories) {
            HashSet<String> firstItrList = new HashSet<String>();
            for (Object[] excelDatum : excelData) {
                String value = (String) excelDatum[0];
                if (l1Category.equals(value)) {
                    firstItrList.add((String) excelDatum[1]);
                }
            }
            firstItr.put(l1Category, firstItrList);
        }
        for (String l2Category : l2Categories) {
            HashSet<String> secondItrList = new HashSet<String>();
            for (Object[] excelDatum : excelData) {
                String combinedValue = excelDatum[0] + ":" + excelDatum[1];
                if (l2Category.equals(combinedValue)) {
                    String thirdValue = (String) excelDatum[2];
                    if (thirdValue != null && !thirdValue.isEmpty()) {
                        secondItrList.add(thirdValue);
                    }
                }
            }
            if (secondItrList.size() > 0) secondItr.put(l2Category, secondItrList);
        }

        int rowCount = firstItr.size() + secondItr.size();
        int rowCounter = 0;
        Object[][] categoryNavigationTestData = new Object[rowCount][2];
        for (Map.Entry<String, HashSet<String>> entry : firstItr.entrySet()) {
            categoryNavigationTestData[rowCounter][0] = entry.getKey();
            categoryNavigationTestData[rowCounter][1] = entry.getValue();
            rowCounter++;
        }
        for (Map.Entry<String, HashSet<String>> entry : secondItr.entrySet()) {
            categoryNavigationTestData[rowCounter][0] = entry.getKey();
            categoryNavigationTestData[rowCounter][1] = entry.getValue();
            rowCounter++;
        }

        return categoryNavigationTestData;
    }

    public static List<String> split(String str, String splitBy) {
        return Stream.of(str.split(splitBy))
                .map(String::new)
                .collect(Collectors.toList());
    }
}
