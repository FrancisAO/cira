package com.cira.ontology_creation.util;

import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.component.JCasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.featurepath.FeaturePathException;
import de.tudarmstadt.ukp.dkpro.core.api.featurepath.FeaturePathFactory;
import de.tudarmstadt.ukp.dkpro.core.api.frequency.tfidf.type.Tfidf;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.keyphrases.core.candidate.CandidateAnnotator;

/**
 * This writer write to the console, but it could write elsewhere, e.g. to a file, database,
 * etc.
 */
public class ConsoleWriter extends JCasConsumer_ImplBase
{
    @Override
    public void process(JCas aJCas)
            throws AnalysisEngineProcessException
    {
        Map<String, String> tokenStrings = new HashMap<>();
        for (Token token : select(aJCas, Token.class)) {
            tokenStrings.put(text(token), text(token) + " " + stem(token) + " " + pos(token));
        }
        Map<String, String> tokenStrings2 = new HashMap<>();
        for(Tfidf tfidf : select(aJCas, Tfidf.class)){
            String token = tokenStrings.get(tfidf.getTerm());
            if(token != null){
                String oldLine = tokenStrings.get(tfidf.getTerm());
                tokenStrings2.put(token, oldLine + " " + tfidf(tfidf));
            }
        }
        try {
            for (Map.Entry<AnnotationFS, String> entry: FeaturePathFactory.select(aJCas.getCas(), Token.class.getName())) {
                System.out.println(entry.getValue());
                //candidates.add(new CandidateAnnotator.Candidate(entry.getValue(), entry.getKey().getBegin(), entry.getKey().getEnd()));
            }
        } catch (FeaturePathException e) {
            e.printStackTrace();
        }

        tokenStrings2.values().stream().forEach(System.out::println);
    }

    private double tfidf(Tfidf tfidf) {
        return tfidf.getTfidfValue();
    }

    private String pos(Token token) {
        return token.getPos().getPosValue();
    }

    private String stem(Token token) {
        return token.getStem().getValue();
    }

    private String text(Token t) {
        return t.getCoveredText();
    }

}
