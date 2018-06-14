package gr.iot.iot.components.jdbc;

import gr.iot.iot.util.JsonUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class JdbcPage<T> implements Page<T> {

    private Pageable pageable;
    private int totalPages;
    private int totalElements;
    private List<T> content;


    public JdbcPage(Pageable pageable, int totalPages, int totalElements, List<T> content) {
        super();
        this.pageable = pageable;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.content = content;
    }

    @Override
    public int getNumber() {
        return pageable.getPageNumber();
    }

    @Override
    public int getSize() {
        return pageable.getPageSize();
    }

    @Override
    public int getTotalPages() {
        return this.totalPages;
    }

    @Override
    public int getNumberOfElements() {
        return this.getContent().size();
    }

    @Override
    public long getTotalElements() {
        return this.totalElements;
    }

    @Override
    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        throw new UnsupportedOperationException("Method is not implemented");
    }

    @Override
    public boolean hasPrevious() {
        return pageable.getPageNumber() != 0;
    }

    @Override
    public boolean isFirst() {
        return pageable.getPageNumber() == 0;
    }

    @Override
    public boolean hasNext() {
        return pageable.getPageNumber() != totalPages;
    }

    @Override
    public boolean isLast() {
        return pageable.getPageNumber() == totalPages;
    }

    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }

    @Override
    public List<T> getContent() {
        return content;
    }


    public String getContentAsJsonArrayString() {
        return JsonUtils.jdbcJsonRowsToJsonArrayString((List<Map<String, Object>>) this.content);
    }

    @Override
    public boolean hasContent() {
        return !content.isEmpty();
    }

    @Override
    public Sort getSort() {
        return this.pageable.getSort();
    }

    public <S> Page<S> map(Converter<? super T, ? extends S> converter) {
        throw new UnsupportedOperationException("Method is not implemented");
    }

    @Override
    public Pageable nextPageable() {
        throw new UnsupportedOperationException("Method is not implemented");
    }

    @Override
    public Pageable previousPageable() {
        throw new UnsupportedOperationException("Method is not implemented");
    }

}
