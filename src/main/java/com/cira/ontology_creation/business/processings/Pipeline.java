package com.cira.ontology_creation.business.processings;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cira.ontology_creation.util.ConsoleWriter;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.frequency.tfidf.TfidfAnnotator;
import de.tudarmstadt.ukp.dkpro.core.frequency.tfidf.TfidfConsumer;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.ngrams.NGramAnnotator;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;
import de.tudarmstadt.ukp.dkpro.core.snowball.SnowballStemmer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser;
import de.tudarmstadt.ukp.dkpro.keyphrases.core.candidate.CandidateAnnotatorFactory;

@Component
public class Pipeline {

    public static final String TF_IDF_MODEL = "tfidfmodel";
    public static final int NGRAM_LENGTH = 3;
    public static final String LANGUAGE_TAG = "de";
    @Value("${pipeline.input.documents}")
    private String inputDocumentsPath;

    public void run() throws UIMAException, IOException {
        runPipeline(
                createReaderDescription(TextReader.class,
                        TextReader.PARAM_SOURCE_LOCATION, inputDocumentsPath,
                        TextReader.PARAM_LANGUAGE, LANGUAGE_TAG),
                createEngineDescription(OpenNlpSegmenter.class),
                createEngineDescription(OpenNlpPosTagger.class),
                createEngineDescription(SnowballStemmer.class),
                createEngineDescription(TfidfConsumer.class,
                        TfidfConsumer.PARAM_FEATURE_PATH, Token.class,
                        TfidfConsumer.PARAM_TARGET_LOCATION, TF_IDF_MODEL),
                createEngineDescription(NGramAnnotator.class,
                        NGramAnnotator.PARAM_N, NGRAM_LENGTH),
                createEngineDescription(StanfordNamedEntityRecognizer.class)
        );

        runPipeline(createReaderDescription(TextReader.class,
                TextReader.PARAM_SOURCE_LOCATION, inputDocumentsPath,
                TextReader.PARAM_LANGUAGE, LANGUAGE_TAG),
                createEngineDescription(OpenNlpSegmenter.class),
                createEngineDescription(OpenNlpPosTagger.class),
                createEngineDescription(SnowballStemmer.class),
                createEngineDescription(SnowballStemmer.class),
                /*createEngineDescription(PosFilter.class,
                        PosFilter.PARAM_ADJ, true,
                        PosFilter.PARAM_NOUN, true,
                        PosFilter.PARAM_ADV, true,
                        PosFilter.PARAM_AUX, true,
                        PosFilter.PARAM_NOUN, true,
                        PosFilter.PARAM_PROPN, true,
                        PosFilter.PARAM_VERB, true,
                        PosFilter.PARAM_X, true,
                        PosFilter.PARAM_TYPE_TO_REMOVE, Token.class),*/
                createEngineDescription(TfidfAnnotator.class,
                TfidfAnnotator.PARAM_FEATURE_PATH, Token.class,
                TfidfAnnotator.PARAM_TFDF_PATH, TF_IDF_MODEL,
                        TfidfAnnotator.PARAM_IDF_MODE, TfidfAnnotator.WeightingModeIdf.LOG),
                createEngineDescription(CandidateAnnotatorFactory.getKeyphraseCandidateAnnotator_token(false)),
                createEngineDescription(ConsoleWriter.class));
    }
/*
*  createEngineDescription(JsonWriter.class,
                        JsonWriter.PARAM_TARGET_LOCATION, "./out")*/

}
