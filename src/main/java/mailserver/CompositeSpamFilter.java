package mailserver;

import java.util.ArrayList;
import java.util.List;

public class CompositeSpamFilter implements SpamFilter {
    private final List<SpamFilter> filters;

    public CompositeSpamFilter() {
        this.filters = new ArrayList<>();
    }

    public void addFilter(SpamFilter filter) {
        filters.add(filter);
    }

    @Override
    public boolean isSpam(Message message) {
        for (SpamFilter filter : filters) {
            if (filter.isSpam(message)) {
                return true;
            }
        }
        return false;
    }
}