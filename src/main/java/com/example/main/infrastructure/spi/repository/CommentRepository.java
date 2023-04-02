package com.example.main.infrastructure.spi.repository;

import com.example.main.infrastructure.spi.entity.CommentEntity;
import com.example.main.infrastructure.spi.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {
}