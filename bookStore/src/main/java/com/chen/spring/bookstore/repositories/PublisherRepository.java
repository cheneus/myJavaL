package com.chen.spring.bookstore.repositories;

import com.chen.spring.bookstore.model.Publisher;
import org.springframework.data.repository.CrudRepository;

public interface PublisherRepository extends CrudRepository <Publisher, Long>{
}