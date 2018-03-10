package com.cira.ontology_creation.domains.tfidf;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.frequency.tfidf.TfidfConsumer;

public class TfIdf {

    public static AnalysisEngineDescription createTfIdfTermCounter(String modelPath) throws ResourceInitializationException {
        return createEngineDescription(TfidfConsumer.class,
                TfidfConsumer.PARAM_FEATURE_PATH, Token.class,
                TfidfConsumer.PARAM_TARGET_LOCATION, modelPath);
    }
}
