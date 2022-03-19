package com.example.managementsystem.search;

import lombok.*;
import org.apache.cxf.common.util.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProPageable {
    private int pageNumber = 0;
    private int pageSize = 10;
    private List<ProSort> sort = new ArrayList<>();

    public ProPageable(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public ProPageable(Pageable pageable) {
        if (pageable != null) {
            this.pageSize = pageable.getPageSize();
            this.pageNumber = pageable.getPageNumber();

            for (Sort.Order order : pageable.getSort()) {
                sort.add(new ProSort(order.getDirection(), order.getProperty()));
            }
        }
    }

    public Pageable toPageable() {
        if (!CollectionUtils.isEmpty(sort)) {
            List<Sort.Order> orders = sort.stream()
                    .map(item -> new Sort.Order(item.getDirection(), item.getProperty()))
                    .collect(Collectors.toList());
            return PageRequest.of(pageNumber, pageSize, Sort.by(orders));
        }

        return PageRequest.of(pageNumber, pageSize);
    }

    public Optional<ProPageable> toOptional() {
        return Optional.of(this);
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProSort {
        private Sort.Direction direction;
        private String property;
    }
}
