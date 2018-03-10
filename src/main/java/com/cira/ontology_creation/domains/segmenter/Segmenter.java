package com.cira.ontology_creation.domains.segmenter;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;

public class Segmenter {

    public static AnalysisEngineDescription createOpenNlpSegmenter() throws ResourceInitializationException {
        return createEngineDescription(OpenNlpSegmenter.class);
    }
}
