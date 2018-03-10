package com.cira.ontology_creation.domains.ngram;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.ngrams.NGramAnnotator;

public class NGram {

    public static AnalysisEngineDescription createNGramAnnotator(int nGramLength) throws ResourceInitializationException {
        return createEngineDescription(NGramAnnotator.class,
                NGramAnnotator.PARAM_N, nGramLength);
    }
}
