package com.sparta.ordermanagement.bootstrap.rest.pagination.cursor;

import com.sparta.ordermanagement.framework.persistence.vo.Cursor;
import com.sparta.ordermanagement.framework.persistence.vo.Sort;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.dao.InvalidDataAccessApiUsageException;

@Getter
@ToString
@NoArgsConstructor
public class CursorPagination {

    private static final List<Integer> AVAILABLE_SIZES = List.of(10, 30, 50);
    private static final int DEFAULT_SIZE = 10;

    int size;            // limit 절에 삽입되는 size
    String basedUuid;    // 커서가 되는 uuid
    String basedValue;   // 커서가 되는 컬럼의 value
    String sortedColumn; // 커서가 되는 컬럼, 정렬되어야 하는 컬럼의 명칭

    private CursorPagination(int size, String basedUuid, String basedValue, String sortedColumn) {
        this.size = validateSizeAndGet(size);
        this.sortedColumn = validateSortedColumAndGet(sortedColumn);
        this.basedValue = basedValue;
        this.basedUuid = basedUuid;
    }

    private String validateSortedColumAndGet(String sortedColumn) {
        if (Objects.isNull(sortedColumn) || sortedColumn.isBlank()) {
            return Sort.DEFAULT_SORT;
        }
        return sortedColumn;
    }

    private int validateSizeAndGet(int size) {
        for (int availableSize : AVAILABLE_SIZES) {
            if (size == availableSize) {
                return size;
            }
        }
        return DEFAULT_SIZE;
    }

    public static CursorPagination of(
        String requestSize, String basedUuid, String basedValue, String sortedColumn) {

        int size = getIntSize(requestSize);
        return new CursorPagination(size, basedUuid, basedValue, sortedColumn);
    }

    private static int getIntSize(String size) {
        try {
            return Integer.parseInt(size);
        } catch (NumberFormatException exception) {
            throw new InvalidDataAccessApiUsageException("사이즈 값은 정수형이어야 합니다.");
        }
    }

    public Cursor toCursor(Sort sort) {
        return new Cursor(size, basedUuid, basedValue, sort);
    }
}
