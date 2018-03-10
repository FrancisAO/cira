package com.cira.ontology_creation.business.recognition.pipelines;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cira.ontology_creation.util.ConsoleWriter;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.frequency.tfidf.TfidfAnnotator;
import de.tudarmstadt.ukp.dkpro.keyphrases.core.candidate.CandidateAnnotatorFactory;

@Component
public class PostprocessingPipeline {

    @Value("${domains.tfidf.model}")
    private String tfIdfModel;

    private static final Logger LOGGER = LoggerFactory.getLogger(PostprocessingPipeline.class);

    public void process(JCasIterable jCasIterable) {
        jCasIterable.spliterator().forEachRemaining(jCas -> {
            try {
                runPipeline(jCas,
                        createEngineDescription(TfidfAnnotator.class,
                                TfidfAnnotator.PARAM_FEATURE_PATH, Token.class,
                                TfidfAnnotator.PARAM_TFDF_PATH, tfIdfModel,
                                TfidfAnnotator.PARAM_IDF_MODE, TfidfAnnotator.WeightingModeIdf.LOG),
                        createEngineDescription(CandidateAnnotatorFactory.getKeyphraseCandidateAnnotator_token(false)),
                        createEngineDescription(ConsoleWriter.class));
            } catch (AnalysisEngineProcessException | ResourceInitializationException e) {
                LOGGER.error("Failed to process.", e);
            }
        });

    }
}
