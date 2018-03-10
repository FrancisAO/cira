package com.cira.ontology_creation.domains.ner;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;

public class NER {

    public static AnalysisEngineDescription createStanfordNamedEntityRecognizer() throws ResourceInitializationException {
        return createEngineDescription(StanfordNamedEntityRecognizer.class);
    }
}
