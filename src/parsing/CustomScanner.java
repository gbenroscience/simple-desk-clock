/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsing;

/**
 *
 * @author JIBOYE, Oluwagbemiro Olaoluwa <gbenroscience@yahoo.com>
 */ 

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author JIBOYE Oluwagbemiro Olaoluwa
 * @since 2011
 *
 * Objects of this class scans a string
 * into tokens based on a list of tokenizer values.
 */
public class CustomScanner {
    /**
     * The input to be scanned into tokens.
     */
    private String input;
    /**
     * The tokens to be employed in splitting the
     * input.
     */
    private String[] tokens;

    /**
     * If true the tokens will be included in
     * the output.
     */
    private boolean includeTokensInOutput;
    /**
     *
     * @param input The input to scan.
     * @param includeTokensInOutput Will allow the splitting tokens to be added to the
     * final scan if this attribute is set to true.
     * @param splitterTokens An array of tokens..input as a
     * variable argument list... on which the input is to be split.
     */
    public CustomScanner( String input, boolean includeTokensInOutput, String... splitterTokens ) {
        this.input = input;
        this.tokens = splitterTokens;
        this.includeTokensInOutput = includeTokensInOutput;
    }
    public CustomScanner(String input, boolean includeTokensInOutput, String[] moreTokens, String... tokens) {
        this.input = input;
        this.includeTokensInOutput = includeTokensInOutput;
        List<String> copier = new ArrayList();
        copier.addAll(Arrays.asList(tokens));
        copier.addAll(Arrays.asList(moreTokens));

        this.tokens = copier.toArray(new String[]{});

    }

    /**
     * A convenience constructor used when there exists
     * more than one array containing the tokenizer data.
     * @param input The input to scan.
     * @param includeTokensInOutput Will allow the splitting tokens to be added to the
     * final scan if this attribute is set to true.
     * @param splitterTokens An array of tokens on which the input is to be split.
     * @param splitterTokens1  A second array of tokens on which the input is to be split.
     * @param splitterTokens2  A second array of tokens..input as a
     * variable argument list... on which the input is to be split.
     *
     */
    public CustomScanner( String input, boolean includeTokensInOutput, String[] splitterTokens,String[] splitterTokens1, String... splitterTokens2 ) {
        this.input = input;

        List<String> copier = new ArrayList();
        copier.addAll(Arrays.asList(splitterTokens));
        copier.addAll(Arrays.asList(splitterTokens1));
        copier.addAll(Arrays.asList(splitterTokens2));

        this.tokens = copier.toArray(new String[]{});
        this.includeTokensInOutput = includeTokensInOutput;
    }



    public List<String> scan() {

        String in = this.input;

        List<String> parse = new ArrayList<>();

        Arrays.sort(tokens, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.length() - o1.length();
            }
        });

        for (int i = 0; i < in.length(); i++) {

            for (String token : tokens) {
                int len = token.length();


                if (len > 0 && i + len <= in.length()) {
                    String portion = in.substring(i, i + len);

                    if (portion.equals(token)) {
                        if (i != 0) {//avoid empty spaces
                            parse.add(in.substring(0, i));
                        }
                        if (includeTokensInOutput) {
                            parse.add(token);
                        }
                        in = in.substring(i + len);
                        i = -1;
                        break;
                    }

                }

            }

        }
        if (!in.isEmpty()) {
            parse.add(in);
        }

        return parse;
    }

    



 








}