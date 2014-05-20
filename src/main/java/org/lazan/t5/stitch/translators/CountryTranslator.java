package org.lazan.t5.stitch.translators;

import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.Translator;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.services.FormSupport;
import org.lazan.t5.stitch.select2.Country;

/**
 * @author Victor Kanopelko
 */
public class CountryTranslator implements Translator<Country> {
    @Override
    public String getName() {
        return "countryTranslator";
    }

    @Override
    public String toClient(Country value) {
        return null;
    }

    @Override
    public Class<Country> getType() {
        return Country.class;
    }

    @Override
    public String getMessageKey() {
        return null;
    }

    @Override
    public Country parseClient(Field field, String clientValue, String message) throws ValidationException {
        return null;
    }

    @Override
    public void render(Field field, String message, MarkupWriter writer, FormSupport formSupport) {
        // Do nothing; we don't yet support client-side validation.
        // formSupport.addValidation(field, name, message, null);
    }
}
