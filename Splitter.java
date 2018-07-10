import java.io.*;

public class Splitter {

    public String tokenizer(String fileName) throws IOException {

        InputStream inputFile = getClass().getResourceAsStream(fileName);
        BufferedReader readFile = new BufferedReader(new InputStreamReader(inputFile));
        PrintWriter file = null;
        // file = new PrintWriter(new BufferedWriter(new  FileWriter("./"+fileName+"tokens")));
        file = new PrintWriter(new BufferedWriter(new  FileWriter("C:/Users/Heydari/IdeaProjects/sent/src/"+fileName+"clean")));
        String temp ="";
        String file_line;

        try {
            int count = 0;
            while ((file_line = readFile.readLine()) != null) {

                temp=file_line;
                String[] sentences = file_line.split("2004 |\\ 2005 |\\ 2006");

                //   for(int i =0 ; i< sentences.length ; i++){
                //     System.out.println(sentences[i]);}
                for(int i=0; i < sentences.length; i++){
                    file.println(sentences[i] );
                }
                //  file.println(" ");
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
        file.close();
        return fileName+"tokens";

    }





    //test of file splitter is here
    public static void main(String[] args) throws IOException {
        Splitter w = new Splitter();
   //     String s =  w.sentenceBreaker("fekr");
        String s2 =  w.tokenizer("kitchen_&_housewarespos.bin");
        String s3 =  w.tokenizer("kitchen_&_housewaresneg.bin");

//System.out.print(s);
    }

}