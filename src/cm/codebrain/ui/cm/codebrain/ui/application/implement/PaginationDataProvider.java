package cm.codebrain.ui.application.implement;

import java.util.List;

public interface PaginationDataProvider<T> {
    int getTotalRowCount();
    List<T> getRows(int startIndex, int endIndex);
}