package me.study.excelpractice.common.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DefaultPageDto<T> {
    private long totalCount;
    private long searchCount;
    private int totalPages;
    private List<T> result;
    private int pageSize;
    private int pageNumber;

    public DefaultPageDto(Page<T> page, long totalCount) {
        this.totalCount = totalCount;

        this.searchCount = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.result = page.getContent();

        Pageable pageable = page.getPageable();
        this.pageSize = page.getPageable().getPageSize();
        this.pageNumber = pageable.getPageNumber() + 1;
    }

    public DefaultPageDto(List<T> result) {
        this.result = result;
    }
}
