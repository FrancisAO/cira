package com.cira.ontology_creation.domains.pos_tagger;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;

public class PosTagger {

    public static AnalysisEngineDescription createOpenNlpPosTagger() throws ResourceInitializationException {
        return createEngineDescription(OpenNlpPosTagger.class);

    }
}
