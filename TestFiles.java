import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class TestFiles {
    public void test(HashMap<String, Double> polarityMap, String fileName) {
        InputStream inputFile = getClass().getResourceAsStream(fileName);
        BufferedReader readFile = new BufferedReader(new InputStreamReader(inputFile));
        String file_line = "";
        Set<Map.Entry<String, Double>> entrySet = null;
        entrySet = polarityMap.entrySet();
        try {

            int pos1 = 0;
            int neg1 = 0;

            while ((file_line = readFile.readLine()) != null) {
                double polar = 0;
                for (Map.Entry<String, Double> entry : entrySet) {
                    if (file_line.contains(entry.getKey())) {
                        polar = polar + entry.getValue();
                    }
                }
              //  if (fileName.equals("pos_test.binclean")) {
                    if(polar >= 0){
                        pos1++;
                    }
                    else
                        neg1++;

            }
            System.out.printf("#of positive comments for %s test is : %d  \n" , fileName, pos1);
            System.out.printf("#of negative comments for %s test is : %d  \n" , fileName, neg1);
   if (fileName.equals("pos_test.binclean")){
       float recall = (float)(pos1)/(float)(pos1 + neg1);
       float pre = (float)(pos1)/(float)(pos1 + 90);
       System.out.printf("recall of pos  test is : %f  \n" ,recall);
       System.out.printf("recall of pos  test is : %f  \n" ,pre);
   }
   else{
       float recall = (float)(neg1)/(float)(pos1 + neg1);
       float pre = (float)(pos1)/(float)(pos1 + 90);
       System.out.printf("prec of neg  test is : %f  \n" ,pre);
       System.out.printf("recall of pos  test is : %f  \n" ,recall);
   }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    }
