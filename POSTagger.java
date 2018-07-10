import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class POSTagger {

   private Vector<String> tokensVector  = new Vector<>();
   private  Vector<String>  tagsVector = new Vector<>();
   private  Vector<Double> probsVector = new Vector<>();
   private Vector<String>  phrases = new Vector<>();

    public Vector<String> getPhrases() {
        return phrases;
    }

    public void POStagger(String input) {

        InputStream tokenModelIn = null;
        InputStream posModelIn = null;
        Vector<String> inputtokens = new Vector<>();
        Vector<String> inputtags = new Vector<>();
        Vector<Double> inputprobs = new Vector<>();

        try {

            String sentence = input;
            // tokenize the sentence
            tokenModelIn = new FileInputStream("en-token.bin");
            TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
            Tokenizer tokenizer = new TokenizerME(tokenModel);
            String tokens[] = tokenizer.tokenize(sentence);
            posModelIn = new FileInputStream("en-pos-maxent.bin");
            // loading the parts-of-speech model from stream
            POSModel posModel = new POSModel(posModelIn);
            // initializing the parts-of-speech tagger with model
            POSTaggerME posTagger = new POSTaggerME(posModel);
            // Tagger tagging the tokens
            String tags[] = posTagger.tag(tokens);
            // Getting the probabilities of the tags given to the tokens
            double probs[] = posTagger.probs();
         //  System.out.println("Token\t:\tTag\t:\tProbability\n---------------------------------------------");
           for(int i=0;i<tokens.length;i++){
              System.out.println(tokens[i]+"\t:\t"+tags[i]+"\t:\t"+probs[i]);
           }
              for(int i = 0 ; i < tags.length  ; i++){
                inputprobs.add(probs[i]);
                inputtags.add(tags[i]);
                inputtokens.add(tokens[i]);
               probsVector.add(probs[i]);
               tagsVector.add(tags[i]);
               tokensVector.add(tokens[i]);

              }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (tokenModelIn != null) {
                try {
                    tokenModelIn.close();
                }
                catch (IOException e) {
                }
            }
            if (posModelIn != null) {
                try {
                    posModelIn.close();
                }
                catch (IOException e) {
                }
            }
        }

        String tag1 = " ";
        String tag2 = " ";
        String tag3 = " ";
        for(int i = 0 ; i < inputtags.size()-1  ; i++){
            if ( i == inputtags.size() -2){
                 tag1 = inputtags.get(i);
                 tag2 = inputtags.get(i+1);
                 tag3 = " ";
            }else {
             tag1 = inputtags.get(i);
             tag2 = inputtags.get(i+1);
             tag3 = inputtags.get(i+2);}

         //   System.out.print(tag1);
            if (tag1.equals("JJ") && (tag2.equals("NN")|| tag2.equals("NNS"))){
                phrases.add(inputtokens.get(i) +" "+ inputtokens.get(i+1));



            }
            else if(tag1.equals("JJ") && tag2.equals("JJ")){
                if(!tag3.equals("NN") || !tag3.equals("NNS")){
                    phrases.add(inputtokens.get(i)+" "+inputtokens.get(i+1));


                }
            }
            else if(tag1.equals("RB") || tag1.equals("RBR") || tag1.equals("RBS")){
                if(tag2.equals("JJ") && (!tag3.equals("NN") ||!tag3.equals("NNS"))){
                    phrases.add((inputtokens.get(i) + " " + inputtokens.get(i+1)));
                }else if(tag2.equals("VB") || tag2.equals("VBD") || tag2.equals("VBN")
                        || tag2.equals("VBG")){
                    phrases.add(inputtokens.get(i) + " " + inputtokens.get(i+1));
                }
            }
            else if(tag2.equals("JJ") && ( tag1.equals("NN") || tag1.equals("NNS"))){
                if(!tag3.equals("NN") &&  !tag3.equals("NNS")){
                    phrases.add(inputtokens.get(i) + " " + inputtokens.get(i+1));
                }
            }


        }

    }



    public static void main(String args[]){
        POSTagger test = new POSTagger();
        test.POStagger("i am 25 years old");
        for ( int i = 0 ; i< test.tagsVector.size() ; i++){
            String objs = test.tagsVector.get(i);
           // Object[] objs = test.tagsVector.get(i);

  // System.out.println( objs);


        }


        for ( int i = 0 ; i< test.phrases.size() ; i++){
            String objs = test.phrases.get(i);
            // Object[] objs = test.tagsVector.get(i);

           System.out.print( objs+" ");


        }
    }
    }
