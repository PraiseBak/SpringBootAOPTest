package com.study.allinonestudy.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PageService {


    private static final int PAGE_SIZE = 10;


    public Pageable getPageable(int page) {
        return PageRequest.of(page,PAGE_SIZE, Sort.by(Sort.Order.desc("createdAt")));
    }
}
