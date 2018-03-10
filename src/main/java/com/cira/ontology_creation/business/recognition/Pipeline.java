package com.cira.ontology_creation.business.recognition;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.iteratePipeline;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.resource.ResourceInitializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cira.ontology_creation.domains.ner.NER;
import com.cira.ontology_creation.domains.ngram.NGram;
import com.cira.ontology_creation.domains.pos_tagger.PosTagger;
import com.cira.ontology_creation.domains.reader.InputReader;
import com.cira.ontology_creation.domains.segmenter.Segmenter;
import com.cira.ontology_creation.domains.stemmer.Stemmer;
import com.cira.ontology_creation.domains.tfidf.TfIdf;
import com.cira.ontology_creation.util.ConsoleWriter;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.frequency.tfidf.TfidfAnnotator;
import de.tudarmstadt.ukp.dkpro.keyphrases.core.candidate.CandidateAnnotatorFactory;


public class Pipeline {

    public static final String TF_IDF_MODEL = "tfidfmodel";
    public static final int NGRAM_LENGTH = 3;
    public static final String LANGUAGE_TAG = "de";
    @Value("${pipeline.input.documents}")
    private String inputDocumentsPath;

    public void run() throws UIMAException {

        JCasIterable jCasIterable = iteratePipeline(
                InputReader.createTextReaderDescription(LANGUAGE_TAG, inputDocumentsPath),
                Segmenter.createOpenNlpSegmenter(),
                PosTagger.createOpenNlpPosTagger(),
                Stemmer.createSnowballStemmer(),
                TfIdf.createTfIdfTermCounter(TF_IDF_MODEL),
                NGram.createNGramAnnotator(NGRAM_LENGTH),
                NER.createStanfordNamedEntityRecognizer()
        );
        jCasIterable.spliterator().forEachRemaining(jCas -> {
            try {
                runPipeline(jCas,
                        createEngineDescription(TfidfAnnotator.class,
                        TfidfAnnotator.PARAM_FEATURE_PATH, Token.class,
                        TfidfAnnotator.PARAM_TFDF_PATH, TF_IDF_MODEL,
                        TfidfAnnotator.PARAM_IDF_MODE, TfidfAnnotator.WeightingModeIdf.LOG),
                        createEngineDescription(CandidateAnnotatorFactory.getKeyphraseCandidateAnnotator_token(false)),
                        createEngineDescription(ConsoleWriter.class));
            } catch (AnalysisEngineProcessException | ResourceInitializationException e) {
                e.printStackTrace();
            }
        });
    }
/*
*  createEngineDescription(JsonWriter.class,
                        JsonWriter.PARAM_TARGET_LOCATION, "./out")*/

}
