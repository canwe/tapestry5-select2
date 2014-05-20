package org.lazan.t5.stitch.select2;

import java.util.Collection;

/**
 * A simple {@code TextChoiceProvider} for Strings.
 *
 * @author Tom Götz (tom@decoded.de)
 */
public abstract class StringTextChoiceProvider extends TextChoiceProvider<String> {

    @Override
    protected String getDisplayText(String choice) {
        return choice;
    }

    @Override
    protected Object getId(String choice) {
        return choice;
    }

    @Override
    public Collection<String> toChoices(Collection<String> ids) {
        return ids;
    }

	@Override
	public Collection<String> toIds(Collection<String> choices) {
		return choices;
	}
}