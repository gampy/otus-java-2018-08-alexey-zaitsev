package ru.otus.l12.servlet;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;


public class TemplateProcessor {

    private final Configuration configuration;
    private final Properties properties;

    public TemplateProcessor(Properties properties) throws IOException {
        this.properties = properties;
        this.configuration = new Configuration(Configuration.VERSION_2_3_28);
        this.configuration.setDirectoryForTemplateLoading(new File(properties.getProperty("templates_catalog")));
        this.configuration.setDefaultEncoding(properties.getProperty("default_encoding"));
        this.configuration.setLocale(Locale.getDefault());
    }

    String getPage(String filename, Map<String, Object> data) throws IOException {
        try (Writer stream = new StringWriter()) {
            Template template = configuration.getTemplate(filename);
            template.process(data, stream);
            return stream.toString();
        } catch (TemplateException e) {
            throw new IOException(e);
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
