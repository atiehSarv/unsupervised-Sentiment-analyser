import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Polarity {
    private HashMap<String, Double> polarityMap = new HashMap<String, Double>();

    private POSTagger postager = new POSTagger();

    public HashMap<String, Double> getPolarityMap() {
        return polarityMap;
    }

    public void filepostagger() {

        int hitsPhraseNearExcellent = 0;
        int hitsPhraseNearPoor = 0;
        int hitExcellent = 0;
        int hitPoor = 0;
        InputStream posCm = getClass().getResourceAsStream("kitchen_&_housewarespos.binclean");
        BufferedReader readFile = new BufferedReader(new InputStreamReader(posCm));
        String inputLine = "";
        try {
            while ((inputLine = readFile.readLine()) != null) {
                postager.POStagger(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream negCm = getClass().getResourceAsStream("kitchen_&_housewaresneg.binclean");
        BufferedReader readFile2 = new BufferedReader(new InputStreamReader(negCm));
        String inputLine2 = "";

        try {
            while ((inputLine2 = readFile2.readLine()) != null) {
                postager.POStagger(inputLine2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
calculator();

    }

    public void calculator() {
       // HashMap<String, Double> polarityMap = new HashMap<String, Double>();

        InputStream posCm = getClass().getResourceAsStream("bothComments.binclean");
        BufferedReader readFile = new BufferedReader(new InputStreamReader(posCm));
        String inputLine = "";
        try {

            for (int i = 0; i < postager.getPhrases().size(); i++) {
                int hitsPhraseNearExcellent = 0;
                int hitsPhraseNearPoor = 0;
                int hitExcellent = 0;
                int hitPoor = 0;
                while ((inputLine = readFile.readLine()) != null) {
                    if (inputLine.contains("excellent")) {
                        hitExcellent++;
                    }
                    if (inputLine.contains("poor")) {
                        hitPoor++;
                    }
                    if (inputLine.contains(postager.getPhrases().get(i))) {
                        if (inputLine.contains("excellent"))
                            hitsPhraseNearExcellent++;
                        else if (inputLine.contains("poor"))
                            hitsPhraseNearPoor++;

                    }

                }

                double polarity = (double) (Math.log(hitsPhraseNearExcellent * hitPoor))
                        - (double) (Math.log(hitsPhraseNearPoor * hitExcellent));
                polarityMap.put(postager.getPhrases().get(i),polarity);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args){
        Polarity p = new Polarity();
        p.filepostagger();
TestFiles test1 = new TestFiles();
test1.test(p.getPolarityMap() , "pos_test.binclean");
test1.test(p.getPolarityMap() , "neg_test.binclean");
    }
}
