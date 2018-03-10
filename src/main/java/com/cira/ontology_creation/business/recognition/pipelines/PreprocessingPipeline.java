package com.cira.ontology_creation.business.recognition.pipelines;

import static org.apache.uima.fit.pipeline.SimplePipeline.iteratePipeline;

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

@Component
public class PreprocessingPipeline {

    private static final int NGRAM_LENGTH = 3;
    private static final String LANGUAGE_TAG = "de";
    @Value("${domains.tfidf.model}")
    private String tfIdfModel;
    @Value("${pipeline.input.documents}")
    private String inputDocumentsPath;

    public JCasIterable process() throws ResourceInitializationException {
        return iteratePipeline(
                InputReader.createTextReaderDescription(LANGUAGE_TAG, inputDocumentsPath),
                Segmenter.createOpenNlpSegmenter(),
                PosTagger.createOpenNlpPosTagger(),
                Stemmer.createSnowballStemmer(),
                TfIdf.createTfIdfTermCounter(tfIdfModel),
                NGram.createNGramAnnotator(NGRAM_LENGTH),
                NER.createStanfordNamedEntityRecognizer()
        );
    }

}
