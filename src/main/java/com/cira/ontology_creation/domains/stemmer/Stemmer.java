package com.cira.ontology_creation.domains.stemmer;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.snowball.SnowballStemmer;

public class Stemmer {

    public static AnalysisEngineDescription createSnowballStemmer() throws ResourceInitializationException {
        return createEngineDescription(SnowballStemmer.class);
    }
}
