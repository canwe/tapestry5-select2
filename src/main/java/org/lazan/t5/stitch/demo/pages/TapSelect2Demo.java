package org.lazan.t5.stitch.demo.pages;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.lazan.t5.stitch.select2.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Victor Kanopelko
 */
public class TapSelect2Demo {

    @InjectComponent
    private Form form4;

    @InjectComponent
    private Form form2;

    @Property
    private Settings singleSelectSettings;

    @Property
    private Settings settings2;

    @Property
    private Settings settings3;

    @Property
    private Settings emailSettings;

    @Inject
    private Request request;

    @Property
    private Country country = Country.US;

    @Property
    private List<Country> countries1 = new ArrayList<Country>(Arrays.asList(new Country[]{Country.US, Country.CA}));

    @Property
    private List<Country> countries2 = new ArrayList<Country>(Arrays.asList(new Country[]{Country.US, Country.PA}));

    // The first state to render a component: perform initialization here
    @SetupRender
    boolean setupRender() {

        singleSelectSettings = new Settings();
        singleSelectSettings.setAllowClear(true);
        singleSelectSettings.setPlaceholder("Select an option");
        singleSelectSettings.setFormatResult("function(item){return \"<div class='select2-user-result'>\" + item.term + \"</div>\";}");
        singleSelectSettings.setFormatSelection("function(item){return item.term;}");
        singleSelectSettings.setInitSelection("function (element, callback) {\n" +
                "  var elementText = jQuery(element).attr('data-init-text');\n" +
                "  callback({\"term\":elementText});\n" +
                "}");
        singleSelectSettings.setMinimumInputLength(2);
        singleSelectSettings.setWidth("300px");

        AjaxSettings ajaxSettings = new AjaxSettings();
        ajaxSettings.setDataType("jsonp"); //!important
        ajaxSettings.setUrl("http://www.weighttraining.com/sm/search");
        ajaxSettings.setQuietMillis(100);
        ajaxSettings.setResults("function(data,page){return{results:data.results.exercise}}");
        ajaxSettings.setData("function(term, page) {\n" +
                             "  return {\n" +
                             "    types: [\"exercise\"],\n" +
                             "    limit: -1,\n" +
                             "    term: term\n" +
                             "  };\n" +
                             "}");
        singleSelectSettings.setAjax(ajaxSettings);

        settings2 = new Settings();
        settings2.setWidth("300px");

        settings3 = new Settings();
        settings3.setWidth("300px");
        settings3.setCreateSearchChoice("function (term) {\n" +
                "        var text = term + (lastResults.some(function(r) { return r.text == term }) ? \"\" : \" (new)\");\n" +
                "        return { id: term, text: text };\n" +
                "    }");
        settings3.setCreateSearchChoice("function(term, data) {\n" +
                "  if (jQuery(data).filter(function() {\n" +
                "    return this.text.localeCompare(term)===0;\n" +
                "  }).length===0) {\n" +
                "    return {id:term.replace(/\\,/g, '&comma;'), text:term, new: true};\n" +
                "  }\n" +
                "}");
        settings3.setFormatSelection("function(item){\n" +
                "  return item.new ? \"<span class='new choice'>\" + item.text + \"</span>\" : item.text;\n" +
                "}");
        settings3.setTokenSeparators(new String[] {" ", ","});

        emailSettings = new Settings();
        emailSettings.setWidth("300px");
        emailSettings.setCreateSearchChoice("function (term) {\n" +
                "        var text = term + (lastResults.some(function(r) { return r.text == term }) ? \"\" : \" (new)\");\n" +
                "        return { id: term, text: text };\n" +
                "    }");
        emailSettings.setCreateSearchChoice("function(term, data) {\n" +
                "  if (jQuery(data).filter(function() {\n" +
                "    return this.text.localeCompare(term)===0;\n" +
                "  }).length===0) {\n" +
                "    return {id:term.replace(/\\,/g, '&comma;'), text:term, new: true};\n" +
                "  }\n" +
                "}");
        emailSettings.setFormatSelection("function(item){\n" +
                "  return item.new ? \"<span class='new choice'>\" + item.text + \"</span>\" : item.text;\n" +
                "}");
        emailSettings.setTokenSeparators(new String[] {" ", ","});

        return true;
    }

    @Cached
    public ChoiceProvider<Country> getCountriesProvider() {
        return new CountriesProvider();
    }

    void onSubmitFromForm4() {
        String fClientId = form4.getClientId();
        System.out.println("onSubmitFromForm4");
    }

    /**
     * Validation passed, so we'll go to the "PostLogin" page
     */
    Object onSuccessFromForm4() {
        String fClientId = form4.getClientId();
        System.out.println("onSuccessFromForm4");
        return null;
    }

    void onSubmitFromForm2() {
        String fClientId = form2.getClientId();
        System.out.println("onSubmitFromForm2");
    }

    /**
     * Validation passed, so we'll go to the "PostLogin" page
     */
    Object onSuccessFromForm2() {
        String fClientId = form2.getClientId();
        System.out.println("onSuccessFromForm2");
        return null;
    }

    /**
     * {@link Country} based choice provider for Select2 components. Demonstrates integration between Select2 components
     * and a domain object (in this case an enum).
     *
     * @author igor
     *
     */
    public class CountriesProvider extends TextChoiceProvider<Country> {

        @Override
        protected String getDisplayText(Country choice) {
            return choice.getDisplayName();
        }

        @Override
        protected Object getId(Country choice) {
            return choice.name();
        }

        @Override
        public void query(String term, int page, Response<Country> response) {
            response.addAll(queryMatches(term, page, 10));
            response.setHasMore(response.size() == 10);

        }

        @Override
        public Collection<Country> toChoices(Collection<String> ids) {
            ArrayList<Country> countries = new ArrayList<Country>();
            for (String id : ids) {
                try {
                    countries.add(Country.valueOf(id));
                } catch (IllegalArgumentException iae) {
                }
            }
            return countries;
        }

	    @Override
	    public Collection<String> toIds(Collection<Country> choices) {
		    return CollectionUtils.collect(choices, new Transformer() {
			    @Override
			    public String transform(Object input) {
				    return ((Country) input).name();
			    }
		    });
	    }
    }

    /**
     * Queries {@code pageSize} worth of countries from the {@link Country} enum, starting with {@code page * pageSize}
     * offset. Countries are matched on their {@code displayName} containing {@code term}
     *
     * @param term
     *            search term
     * @param page
     *            starting page
     * @param pageSize
     *            items per page
     * @return list of matches
     */
    private static List<Country> queryMatches(String term, int page, int pageSize) {

        List<Country> result = new ArrayList<Country>();

        term = term.toUpperCase();

        final int offset = page * pageSize;

        int matched = 0;
        for (Country country : Country.values()) {
            if (result.size() == pageSize) {
                break;
            }

            if (country.getDisplayName().toUpperCase().contains(term)) {
                matched++;
                if (matched > offset) {
                    result.add(country);
                }
            }
        }
        return result;
    }
}
